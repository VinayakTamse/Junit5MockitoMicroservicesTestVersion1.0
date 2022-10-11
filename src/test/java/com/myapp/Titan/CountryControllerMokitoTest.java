package com.myapp.Titan;

import com.myapp.Titan.TestData.MockData;
import com.myapp.Titan.controller.CountryController;
import com.myapp.Titan.model.Country;
import com.myapp.Titan.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CountryServiceMockitoTest.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryControllerMokitoTest {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @Test
    @Order(1)
    public void test_getAllCountries()
    {
        List<Country> lists = MockData.listsOfCountries();

        when(countryService.getCountries()).thenReturn(lists);
        ResponseEntity<List<Country>> response = countryController.getAllCountries();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lists.size(), response.getBody().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById()
    {
        List<Country> lists = MockData.listsOfCountries();
        Country country = lists.get(1);
        int countryId = country.getId();
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryById(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(country.getName(), response.getBody().getName());
    }

    @Test
    @Order(3)
    public void test_getCountryByName()
    {
           Country country =  MockData.listsOfCountries().get(0);
           String countryName = country.getName();
           when(countryService.getCountryByName(countryName)).thenReturn(country);
           ResponseEntity<Country> response = countryController.getCountryByName(countryName);
           assertEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(countryName, response.getBody().getName());
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        Country country = new Country(3, "France", "Paris");
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(country.getName(), response.getBody().getName());

    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        Country country = new Country(3, "Germany", "Berlin");
        int countryId = country.getId();
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.updateCountry(countryId, country);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(country.getName(), response.getBody().getName());

    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        Country country = new Country(3, "Germany", "Berlin");
        int countryId = country.getId();
        ResponseEntity<?> response = countryController.deleteCountry(countryId);
        verify(countryService, times(1)).deleteCountry(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
    }

}
