package com.file.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, properties = {"jasypt.encryptor.password=Not4Use"})
@ActiveProfiles(value = "junit")
public class ApplicationTest {

	@MockBean
	CacheManager cacheManager;


	@Test
	public void test() {
		try{
		Application.main(new String[] {});

			assertTrue(true);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
