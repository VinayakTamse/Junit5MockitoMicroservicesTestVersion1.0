package com.websrv.app1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.juneau.json.JsonParser;
import org.apache.juneau.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.websrv.app1.bean.Country;



@SpringBootTest
public class ControllerH2IntegrationTest {
	
	RestTemplate templates;
	
	@BeforeEach
	void setUp() {
		
		templates = new RestTemplate();
	}
	
	@Test
	void test_getAllCountries()
	{
		ResponseEntity<String> response = templates.getForEntity("http://localhost:8080/country/all", String.class);
		String content = response.getBody();
		System.out.println(content);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void test_getCountryByName() throws ParseException
	{
		ResponseEntity<String> response = templates.getForEntity("http://localhost:8080/country/1", String.class);
		String responseBody = response.getBody();
		JsonParser parser = JsonParser.DEFAULT;
		Country countryData = parser.parse(responseBody, Country.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("India", countryData.getName());
		
	}

}
