package com.file.service.configuration;

import com.file.service.processor.EmployeeDataConversionProcessor;
import com.file.service.processor.FileStatusAuditsProcessor;
import com.file.service.processor.HeartbeatProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ServiceConfiguration extends BeanConfigurationBase {


    public ServiceConfiguration(AutowireCapableBeanFactory beanFactory) {
        super(beanFactory);
    }


    @Bean
    public EmployeeDataConversionProcessor readEmployeeDataConversionProcessor() {
        return (EmployeeDataConversionProcessor) this.create(EmployeeDataConversionProcessor.class);
    }

    @Bean
    public FileStatusAuditsProcessor readFileStatusAuditsProcessor() {
        return (FileStatusAuditsProcessor) this.create(FileStatusAuditsProcessor.class);
    }

    @Bean
    public HeartbeatProcessor readHeartBeatProcessor() {
        return (HeartbeatProcessor) this.create(HeartbeatProcessor.class);
    }

    @Bean(name="fileServiceDS")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());

        return dataSource;
    }


}
