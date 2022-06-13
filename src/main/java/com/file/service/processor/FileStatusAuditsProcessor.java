package com.file.service.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FileStatusAuditsProcessor implements Processor {

	private ObjectMapper mapper;

	public FileStatusAuditsProcessor(ObjectMapper mapper) {
	    this.mapper = mapper;
    }
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String fileName = exchange.getProperty(Exchange.FILE_NAME_CONSUMED, String.class);
		String fileSize = exchange.getProperty(Constants.FILE_SIZE, String.class);

		List<Map<String, Object>> fileStatusAudits = new ArrayList<>();

		Map<String, Object> fileStatusAudit = new HashMap<>();
		fileStatusAudit.put("fileName", fileName);
		fileStatusAudit.put("fileSize", fileSize);
		fileStatusAudit.put("status", "Success");
		fileStatusAudit.put("createdBy", "System");
		fileStatusAudit.put("updatedBy", "Systen");

		fileStatusAudits.add(fileStatusAudit);

		log.debug("Inserting/Updating fileStatusAudits records : "+fileStatusAudits.size());
		exchange.getOut().setBody(fileStatusAudits);
	}

}
