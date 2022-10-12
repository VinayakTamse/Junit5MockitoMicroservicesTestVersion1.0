package com.myapp.Titan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.Titan.TestData.MockData;
import com.myapp.Titan.controller.CountryController;
import com.myapp.Titan.model.Country;
import com.myapp.Titan.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ComponentScan(basePackages = "com.myapp.Titan")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {CountryControllerMockMVCTest.class})
public class CountryControllerMockMVCTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        List<Country> lists =  MockData.listsOfCountries();
        when(this.countryService.getCountries()).thenReturn(lists);

        this.mockMvc.perform(get("/country/all")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    public void test_getCountryById() throws Exception
    {
           Country country = MockData.listsOfCountries().get(0);
           int countryId = country.getId();
           when(this.countryService.getCountryById(countryId)).thenReturn(country);

           this.mockMvc.perform(get("/country/{id}", countryId)).andExpect(status().isOk())
                   .andExpect(MockMvcResultMatchers.jsonPath(".id").value(country.getId()))
                   .andExpect(MockMvcResultMatchers.jsonPath(".name").value(country.getName()))
                   .andExpect(MockMvcResultMatchers.jsonPath(".capital").value(country.getCapital()))
                   .andDo(print());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception
    {
        Country country = MockData.listsOfCountries().get(1);
        String countryName = country.getName();

        when(this.countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(get("/country/name").param("q", countryName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value(countryName))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception
    {
            Country country = new Country(3, "Germany", "Berlin");
            when(this.countryService.addCountry(country)).thenReturn(country);
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(country);
            this.mockMvc.perform(post("/country/add")
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated())
                    .andDo(print());
    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception
    {
        Country country = new Country(3, "France", "Paris");
        int countryId = country.getId();
        when(this.countryService.getCountryById(countryId)).thenReturn(country);
        when(this.countryService.updateCountry(country)).thenReturn(country);
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);
        this.mockMvc.perform(put("/country/update/{id}", countryId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andDo(print());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception
    {
        Country country = new Country(3, "France", "Paris");
        int countryId = country.getId();
        this.mockMvc.perform(delete("/country/delete/{id}", countryId))
                .andExpect(status().isOk()).andDo(print());
        verify(this.countryService, times(1)).deleteCountry(countryId);
    }

}
