package com.file.service.processor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeartbeatProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse("{\"Status\":\"Ok\"}").getAsJsonObject();
        exchange.getIn().setBody(jsonObject.toString());
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

        log.info("Health Check is Ok!");

    }
}
