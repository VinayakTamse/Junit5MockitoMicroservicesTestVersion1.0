package com.websrv.app1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.websrv.app1.bean.Country;
import com.websrv.app1.controller.CountryController;
import com.websrv.app1.services.CountryService;

import mockdata.CountryMockData;

@SpringBootTest(classes = { CountryControllerMockTest.class })

@TestMethodOrder(OrderAnnotation.class)
public class CountryControllerMockTest {

	@Mock
	CountryService countryServ;

	@InjectMocks
	CountryController controller;

	@Test
	@Order(1)
	void test_getAllCountries() {
		when(this.countryServ.getAllCountryLists()).thenReturn(CountryMockData.getCountryDataLists());
		ResponseEntity<List<Country>> lists = this.controller.getAllCountries();
		assertEquals(HttpStatus.OK, lists.getStatusCode());
		assertEquals(CountryMockData.getCountryDataLists().size(), lists.getBody().size());
	}

	@Test
	@Order(2)
	void test_getCountryById() {
		Country country = CountryMockData.getCountryDataLists().get(0);
		when(this.countryServ.getCountryById(country.getId())).thenReturn(country);
		ResponseEntity<Country> response = this.controller.getCountryById(country.getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(country.getName(), response.getBody().getName());
	}

	@Test
	@Order(3)
	void test_getCountryByName() {
		Country country = CountryMockData.getCountryDataLists().get(0);
		when(this.countryServ.getCountryByName(country.getName())).thenReturn(country);
		ResponseEntity<Country> response = this.controller.getCountryByName(country.getName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(4)
	void test_addCountry() {
		Country country = CountryMockData.countryData();
		when(this.countryServ.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> response = this.controller.addCountry(country);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(country.getName(), response.getBody().getName());

	}

	@Test
	@Order(5)
	void test_updateCountry() {
		List<Country> lists = CountryMockData.getCountryDataLists();
		int countryId = lists.get(0).getId();
		Country country = new Country(countryId, "Cuba", "Havana");
		when(this.countryServ.updateCountry(country)).thenReturn(country);
		ResponseEntity<Country> response = this.controller.updateCountry(countryId, country);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}
	
	@Test
	@Order(6)
	void test_deleteCountry()
	{
		int countryId = 2;
		ResponseEntity<?> response = this.controller.deleteCountry(countryId);
		verify(this.countryServ, times(1)).deleteCountry(countryId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
