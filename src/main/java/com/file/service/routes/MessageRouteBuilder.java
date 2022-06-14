package com.file.service.routes;

import com.file.service.configuration.EnvironmentConfiguration;
import com.file.service.constants.Constants;
import com.file.service.model.EmployeeData;
import com.file.service.processor.EmployeeDataConversionProcessor;
import com.file.service.processor.FileStatusAuditsProcessor;
import com.file.service.processor.FileProcessor;
import com.file.service.processor.HeartbeatProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MessageRouteBuilder extends RouteBuilder {

	private CamelContext camelContext;

	private EnvironmentConfiguration environmentConfiguration;

	private EmployeeDataConversionProcessor employeeDataConversionProcessor;
	private FileStatusAuditsProcessor fileStatusAuditsProcessor;
	private HeartbeatProcessor heartbeatProcessor;

	@Autowired
	public MessageRouteBuilder(CamelContext camelContext,
							   EnvironmentConfiguration environmentConfiguration,
							   EmployeeDataConversionProcessor employeeDataConversionProcessor,
							   FileStatusAuditsProcessor fileStatusAuditsProcessor,
							   HeartbeatProcessor heartbeatProcessor) {
		super(camelContext);
		this.camelContext = camelContext;
		this.environmentConfiguration = environmentConfiguration;
		this.employeeDataConversionProcessor = employeeDataConversionProcessor;
		this.fileStatusAuditsProcessor = fileStatusAuditsProcessor;
		this.heartbeatProcessor = heartbeatProcessor;
	}

	
	@Override
	public void configure() throws Exception {

		errorHandler(deadLetterChannel("direct:globalExceptionHandler").useOriginalMessage());
		from("direct:globalExceptionHandler").process(exchange -> {
			Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
			log.error("Uncaught exception occurred", cause);
		});

		restConfiguration().component("servlet")
				.bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true")
				.port("8080")
				.contextPath("/api")
				.apiProperty("api.title", "File Process Service")
				.apiProperty("api.version", "0.1.0")
				.apiProperty("cors", "true")
				.apiProperty("host", "")
				.apiProperty("schemes", "http,https");

		//service health check API
		rest("/heartbeat").get().route().routeId("heartbeatRoute").process(heartbeatProcessor);

		/**
		 * Poller configuration
		 filePollerQuery: "?include=.*.txt&sortBy=reverse:file:modified&maxMessagesPerPoll=60000&preMove=staging&move=.completed"

		 Poler interval is maxMessagesPerPoll=60000
		 To pick only text files: include=.*.txt
		 Pick last modified files: sortBy=reverse:file:modified
		 Moving file stage after completion only poler will read : preMove=staging&move=.completed

		 */

		/**
		 * 1. Poller starts polling files based on configuration
		 * 2. Reads multiple files based on threads configuration
		 * 3. Added 2 routes one for backup file and next one will start the file processing
		 * 4. Unmarshalling file using bindy and streaming the file content each stream 10k records
		 * 5. Added DB routes first employee table will update/insert
		 * 6. Next other tables - all DB queries paralle to update other respective tables
		 * 7. FileStatusAudits table updating with details
		 */
		//Poller starts every 60 seconds and polls directory for updated files
		from("file:"+environmentConfiguration.getFileServiceDir()+environmentConfiguration.getFilePollerQuery()+ //Configured in dev YML
				"&maxMessagesPerPoll="+ Constants.MAX_THREADS) //Parallel Threads - Fast Performance
				.routeId("startFilePollerRoute")
				.threads(Constants.MAX_THREADS, Constants.MAX_THREADS, "FileServiceThreads") //Parallel Threads - Fast Performance
				.log(LoggingLevel.INFO,"Inside File Poller")
				.setProperty(Exchange.FILE_NAME_CONSUMED, simple(Constants.ONLY_NAME))
				.to("direct:bacupEmployeeFile", "direct:startFileProcessor")
				.to("direct:updateFileStatusAudits");

		from("direct:bacupEmployeeFile")
				.routeId("backupEmployeeRoute")
				.log(LoggingLevel.DEBUG,"Inside Backup Employee Route")
				.to("file:"+environmentConfiguration.getBackupFileDir()) //Configured in dev YML
				.log(LoggingLevel.INFO,"Backup File Completed")
				.process(new FileProcessor(environmentConfiguration));

		from("direct:startFileProcessor")
				.routeId("startFileProcessorRoute")
				.log(LoggingLevel.DEBUG,"Started File Reading")
				.unmarshal(new BindyCsvDataFormat(EmployeeData.class)) //File Header not matches with CSVHeader defined in EmployeeData it will throw error
				.split(body()).streaming()
				.aggregate(AggregationStrategies.groupedBody())
				.constant(Constants.AGGREGATE_CONSTANTS)
				.completionSize(Constants.SPLIT_RECORD_SIZE)
				.completionTimeout(Constants.TIME_OUT)
				.process(employeeDataConversionProcessor)
				.to("direct:employeeDataUpdate")
				.log(LoggingLevel.DEBUG,"Inserted/Updated Employee Data in Employee Table Next start Sub Process")
				.parallelProcessing()
				.to("direct:employeeDepartmentUpdate", "direct:employeeSalaryUpdate", "direct:employeeLeavesUpdate", "direct:employeeFamilyUpdate")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {

						log.info(exchange.getProperty(Constants.FILE_NAME) + " - Update Completed Successfully : "+ Instant.now());
					}
				})
				.end();

		from("direct:employeeDataUpdate")
				.routeId("employeeDataUpdateRoute")
				.log(LoggingLevel.DEBUG,"Inside Employee Data Update into DB")
				.to("sql:classpath:sql/InsertEmployeeTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.DEBUG,"Inserted/Updated Employee Successfully");

		from("direct:employeeDepartmentUpdate")
				.routeId("employeeDepartmentUpdateRoute")
				.log(LoggingLevel.DEBUG,"Inside EmployeeDepartment Data Update into DB")
				.to("sql:classpath:sql/InsertEmployeeDepartmentTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.DEBUG,"Inserted/Updated EmployeeDepartment Successfully");

		from("direct:employeeSalaryUpdate")
				.routeId("employeeSalaryUpdateRoute")
				.log(LoggingLevel.DEBUG,"Inside EmployeeSalary Data Update into DB")
				.to("sql:classpath:sql/InsertEmployeeSalaryTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.DEBUG,"Inserted/Updated EmployeeSalary Successfully");

		from("direct:employeeLeavesUpdate")
				.routeId("employeeLeavesUpdateRoute")
				.log(LoggingLevel.DEBUG,"Inside EmployeeLeaves Data Update into DB")
				.to("sql:classpath:sql/InsertEmployeeLeavesTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.DEBUG,"Inserted/Updated EmployeeLeaves Successfully");

		from("direct:employeeFamilyUpdate")
				.routeId("employeeFamilyUpdateRoute")
				.log(LoggingLevel.DEBUG,"Inside EmployeeFamily Data Update into DB")
				.to("sql:classpath:sql/InsertEmployeeFamilyTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.DEBUG,"Inserted/Updated EmployeeFamily Successfully : ");

		from("direct:updateFileStatusAudits")
				.routeId("updateFileStatusAuditsRoute")
				.process(fileStatusAuditsProcessor)
				.log(LoggingLevel.DEBUG,"Inside FileStatusAudits Data Update into DB")
				.to("sql:classpath:sql/InsertFileStatusAuditsTable.sql?dataSource=#fileServiceDS&?batch=true")
				.log(LoggingLevel.INFO,"${property.fileName} - Inserted/Updated FileStatusAudits Successfully");


	}

}
