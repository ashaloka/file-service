package com.file.service.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.service.model.EmployeeData;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;

@Slf4j
public class EmployeeDataConversionProcessor implements Processor {

	private ObjectMapper mapper;

	public EmployeeDataConversionProcessor(ObjectMapper mapper) {
	    this.mapper = mapper;
    }
	
	@Override
	public void process(Exchange exchange) throws Exception {

		List<EmployeeData> employeeData = (List<EmployeeData>) exchange.getIn().getBody();

		List<Map<String, Object>> employeeDataList = mapper.convertValue(employeeData, new TypeReference<List<Map<String, Object>>>(){});

		log.debug("Inserting/Updating records : "+employeeDataList.size());
		exchange.getOut().setBody(employeeDataList);
	}

}
