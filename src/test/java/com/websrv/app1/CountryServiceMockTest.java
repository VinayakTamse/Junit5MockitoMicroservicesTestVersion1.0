package com.websrv.app1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.websrv.app1.bean.Country;
import com.websrv.app1.repository.CountryRepository;
import com.websrv.app1.services.CountryService;

import mockdata.CountryMockData;

@SpringBootTest(classes= {CountryServiceMockTest.class})
@TestMethodOrder(OrderAnnotation.class)
public class CountryServiceMockTest {
	
	@Mock
	CountryRepository countryRepo;
	
	@InjectMocks
	CountryService service;
	
	@Test
	@Order(1)
	void test_getAllCountryLists()
	{
		when(this.countryRepo.findAll()).thenReturn(CountryMockData.getCountryDataLists());
		List<Country> lists = this.service.getAllCountryLists();
		assertEquals(lists.size(), CountryMockData.getCountryDataLists().size());
		assertEquals(lists.get(0).getName(), CountryMockData.getCountryDataLists().get(0).getName());
	}
	
	@Test
	@Order(2)
	void test_getCountryById()
	{
		Country country = CountryMockData.getCountryDataLists().get(1);
		int countryId = country.getId();
		when(this.countryRepo.findById(countryId)).thenReturn(Optional.of(country));
		assertEquals(this.service.getCountryById(countryId).getName(), country.getName());
	}
	
	@Test
	@Order(3)
	void test_getCountryByName()
	{
		
		when(this.countryRepo.findAll()).thenReturn(CountryMockData.getCountryDataLists());
		Country country = this.service.getCountryByName("India");
		assertEquals("Delhi", country.getCapital());
		
	}
	
	@Test
	@Order(4)
	void test_addCountry()
	{
		Country country = CountryMockData.countryData();
		when(this.countryRepo.save(country)).thenReturn(country);
		Country addedCountry = this.service.addCountry(country);
		assertEquals(addedCountry.getName(), country.getName());
	}
	
	@Test
	@Order(5)
	void test_updateCountry()
	{
		Country country = CountryMockData.countryData();
		country.setName("Cuba");
		country.setCapital("Havana");
		when(this.countryRepo.save(country)).thenReturn(country);
		Country updatedCountry = this.service.updateCountry(country);
		assertEquals("Cuba", updatedCountry.getName());
	}
	
	@Test
	@Order(6)
	void test_deleteCountry()
	{
		Country country = new Country(4, "France", "Paris");
		this.service.deleteCountry(country.getId());
		verify(this.countryRepo, times(1)).deleteById(country.getId());
	}
	

}
