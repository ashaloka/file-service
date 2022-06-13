package com.file.service.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "fileservice")
public class EnvironmentConfiguration {

	@Value("${profile.name}")
	private String profile;

	@Value("${jasypt.encryptor.password}")
	private String passwordKey;

	private String applicationName;

	private String encryptionAlgorithm;
	private String encryptionPoolSize;
	private String encryptionOutputType;

	private String fileServiceDir;
	private String filePollerQuery;
	private String backupFileDir;


	@Bean(name = "ftmPasswordEncryptor")
	public StringEncryptor ftmPasswordEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(passwordKey);
		config.setAlgorithm(encryptionAlgorithm);
		config.setPoolSize(encryptionPoolSize);
		config.setStringOutputType(encryptionOutputType);
		encryptor.setConfig(config);
		return encryptor;
	}
}