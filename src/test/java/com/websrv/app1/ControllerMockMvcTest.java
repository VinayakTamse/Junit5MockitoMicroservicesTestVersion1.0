package com.websrv.app1;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websrv.app1.Data.MVCMockData;
import com.websrv.app1.bean.Country;
import com.websrv.app1.controller.CountryController;
import com.websrv.app1.services.CountryService;

@ComponentScan(basePackages = "com.websrv.app1")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMvcTest.class })
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test @Order(1)
	public void test_getAllCountries() throws Exception {
		List<Country> lists = MVCMockData.getCountryDataLists();
		when(countryService.getAllCountryLists()).thenReturn(lists);

		this.mockMvc.perform(get("/country/all")).andExpect(status().isOk()).andDo(print());
	}

	@Test @Order(2)
	public void test_getAllCountryById() throws Exception {
		Country country = MVCMockData.getCountryDataLists().get(0);
		int countryId = country.getId();
		when(countryService.getCountryById(countryId)).thenReturn(country);
		this.mockMvc.perform(get("/country/{id}", countryId)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath(".name").value("India")).andDo(print());

	}

	@Test @Order(3)
	public void test_getCountryByName() throws Exception {
		Country country = MVCMockData.getCountryDataLists().get(1);
		String name = country.getName();
		when(countryService.getCountryByName(name)).thenReturn(country);

		this.mockMvc.perform(get("/country/name").param("qname", name)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(".name").value(name)).andDo(print());
	}

	@Test @Order(4)
	public void test_addCountry() throws Exception {
		Country country = MVCMockData.countryData();

		when(countryService.addCountry(country)).thenReturn(country);
		ObjectMapper body = new ObjectMapper();
		String jsonBody = body.writeValueAsString(country);
		this.mockMvc.perform(post("/country/create").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test @Order(5)
	public void test_updateCountry() throws Exception {
		int countryId = 3;
		Country country = new Country(countryId, "France", "Paris");
		when(countryService.updateCountry(country)).thenReturn(country);
		ObjectMapper body = new ObjectMapper();
		String jsonBody = body.writeValueAsString(country);
		this.mockMvc.perform(
				put("/country/update/{id}", countryId).contentType(MediaType.APPLICATION_JSON).content(jsonBody))
				.andExpect(status().isCreated()).andDo(print());
	}
	
	@Test @Order(6)
	public void test_deleteCountry() throws Exception
	{
		int countryId = 3;
		this.mockMvc.perform(delete("/country/delete/{id}", countryId))
		.andExpect(status().isOk()).andDo(print());
		verify(this.countryService, times(1)).deleteCountry(countryId);
	}

}
