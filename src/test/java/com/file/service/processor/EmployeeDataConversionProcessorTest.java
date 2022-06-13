package com.file.service.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.service.model.EmployeeData;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class EmployeeDataConversionProcessorTest {

    @Test
    public void testOperationFailedExceptionProcess() throws Exception {
        CamelContext ctx = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(ctx);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<EmployeeData> employeeDataList = new ArrayList<>();
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeId("123");
        employeeData.setName("test");
        employeeDataList.add(employeeData);

        exchange.getIn().setBody(employeeDataList);

        EmployeeDataConversionProcessor processor = new EmployeeDataConversionProcessor(mapper);

        processor.process(exchange);

        assertNotNull(exchange.getOut().getBody());
    }

}
