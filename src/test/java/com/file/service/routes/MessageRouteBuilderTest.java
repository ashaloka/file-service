package com.file.service.routes;

import com.file.service.configuration.EnvironmentConfiguration;
import com.file.service.processor.FileStatusAuditsProcessor;
import com.file.service.processor.HeartbeatProcessor;
import com.file.service.processor.EmployeeDataConversionProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;


import javax.naming.Context;

@ActiveProfiles("default")
public class MessageRouteBuilderTest extends CamelTestSupport {

	private CamelContext camelContext;
	private EnvironmentConfiguration configuration;
	private EmployeeDataConversionProcessor employeeDataConversionProcessor;
	private FileStatusAuditsProcessor fileStatusAuditsProcessor;
	private HeartbeatProcessor heartbeatProcessor;;

	public MessageRouteBuilderTest() {

		//Processors
		camelContext = Mockito.mock(CamelContext.class);
		configuration = Mockito.mock(EnvironmentConfiguration.class);
		employeeDataConversionProcessor = Mockito.mock(EmployeeDataConversionProcessor.class);
		fileStatusAuditsProcessor = Mockito.mock(FileStatusAuditsProcessor.class);
		heartbeatProcessor = Mockito.mock(HeartbeatProcessor.class);
	}

	/*@Test
	public void testConfiguration() {
		EnvironmentConfiguration environmentConfiguration = new EnvironmentConfiguration();
		environmentConfiguration.setApplicationName("ftm");
		environmentConfiguration.setFilePollerQuery("./src/test/resources/files");
		environmentConfiguration.setBackupFileDir("./src/test/resources/backup");
		environmentConfiguration.setPasswordKey("Not4Use");
		environmentConfiguration.setEncryptionAlgorithm("PBEWithHMACSHA256AndAES_256");
		environmentConfiguration.setEncryptionOutputType("base64");
		environmentConfiguration.setEncryptionPoolSize("1");

		assertEquals("ftm", environmentConfiguration.getApplicationName());
	}*/

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new MessageRouteBuilder(camelContext, configuration, employeeDataConversionProcessor, fileStatusAuditsProcessor, heartbeatProcessor);
	}

	@Override
	protected Context createJndiContext() throws Exception {
		return null;
	}

	@Override
	public boolean isUseAdviceWith() {
		return true;
	}

	@Before
	public void mockEndpoints() throws Exception {

		AdviceWithRouteBuilder mockToFacade = new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint(
						"direct:startFileProcessor")
						.skipSendToOriginalEndpoint()
						.to("mock:startFileProcessor");
			}
		};

	}

	@Before
	public void before() throws Exception {
		context.start();
	}

	@After
	public void after() throws Exception {
		context.stop();
	}
}
