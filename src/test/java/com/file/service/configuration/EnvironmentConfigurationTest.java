package com.file.service.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentConfigurationTest {

    @InjectMocks
    EnvironmentConfiguration environmentConfiguration;

	@Test
	public void testModel() {

        try {
            EnvironmentConfiguration configuration = new EnvironmentConfiguration();
            configuration.setApplicationName("FTM");

            assertEquals("FTM", configuration.getApplicationName());


        }catch (Exception e){}
	}

}
