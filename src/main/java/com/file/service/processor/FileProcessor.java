package com.file.service.processor;

import com.file.service.configuration.EnvironmentConfiguration;
import com.file.service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;
import java.time.Instant;

@Slf4j
public class FileProcessor implements Processor {

	private EnvironmentConfiguration environmentConfiguration;

	public FileProcessor(EnvironmentConfiguration environmentConfiguration) {
		this.environmentConfiguration = environmentConfiguration;
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		String fileName = exchange.getProperty(Exchange.FILE_NAME_CONSUMED, String.class);
		exchange.setProperty(Constants.FILE_NAME, fileName);
		File file = new File(environmentConfiguration.getBackupFileDir() + File.separator +fileName);
		exchange.setProperty(Constants.FILE_SIZE, file.length());

		log.info("File process started for : {} {}", fileName, Instant.now());

	}

}
