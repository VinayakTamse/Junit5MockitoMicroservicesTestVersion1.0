package com.myapp.Titan;

import com.myapp.Titan.TestData.MockData;
import com.myapp.Titan.model.Country;
import com.myapp.Titan.repositories.CountryRepository;
import com.myapp.Titan.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CountryServiceMockitoTest.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryServiceMockitoTest {

    @Mock
    CountryRepository countryRepo;

    @InjectMocks
    CountryService countryServ;

    @Test
    @Order(1)
    public void test_getCountries()
    {

        when(countryRepo.findAll()).thenReturn(MockData.listsOfCountries());
       List<Country> lists = countryServ.getCountries();
       assertEquals(2, lists.size());
    }

    @Test
    @Order(2)
    public void test_getCountryById()
    {
        List<Country> countryLists = MockData.listsOfCountries();
       Country country = countryLists.get(0);
        when(countryRepo.findById(country.getId())).thenReturn(Optional.of(country));
        Country testCountry = countryServ.getCountryById(country.getId());
        System.out.println("Value is "+testCountry.getName());
        assertEquals(testCountry.getName(), country.getName());
    }

    @Test
    @Order(3)
    public void test_getCountryByName()
    {
       Country country = MockData.listsOfCountries().get(1);
       when(countryRepo.findAll()).thenReturn(MockData.listsOfCountries());
       Country testCountry = countryServ.getCountryByName(country.getName());
       assertEquals(country.getName(), testCountry.getName());
    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        Country country = new Country(3, "France", "Italy");
        when(countryRepo.save(country)).thenReturn(country);
        Country added_country = countryServ.addCountry(country);
        assertEquals(country.getName(), added_country.getName());
    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        int country_id = 3;
        Country country = new Country(country_id, "Thailand", "Bangkok");
        when(countryRepo.save(country)).thenReturn(country);
        Country updatedCountry = countryServ.updateCountry(country);
        assertEquals(country.getName(), updatedCountry.getName());
    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        int country_id = 3;
        Country country = new Country(country_id, "Thailand", "Bangkok");
        countryServ.deleteCountry(country_id);
        verify(countryRepo, times(1)).deleteById(country_id);

    }
}
