package com.myapp.Titan;


import com.myapp.Titan.model.Country;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class H2ControllerIntegrationTest {

    RestTemplate template;

    @BeforeEach
    public void set_up()
    {
        template = new RestTemplate();
    }

    @Test
    @Order(1)
    public void test_getAllCountries() throws JSONException {
        String expected = "[{\"id\":1,\"name\":\"India\",\"capital\":\"Delhi\"}]";
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/country/all",String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void test_getCountryById()
    {
        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/country/1", String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void test_getCountryByName()
    {

        ResponseEntity<String> response = template.getForEntity("http://localhost:8080/country/name?q=India", String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(4)
    public void test_addCountry()
    {
        Country country = new Country(2, "Japan", "Tokyo");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> body = new HttpEntity<Country>(country, headers);
        ResponseEntity<String> response = template.postForEntity("http://localhost:8080/country/add", body, String.class );
        System.out.println(response.getStatusCode());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void test_updateCountry()
    {
        Country country = new Country(2, "France", "Paris");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> body = new HttpEntity<Country>(country, headers);
        ResponseEntity<String> response = template.exchange("http://localhost:8080/country/update/2", HttpMethod.PUT, body, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void test_deleteCountry()
    {
        Country country = new Country(2, "France", "Paris");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> body = new HttpEntity<Country>(country, headers);
        ResponseEntity<String> response = template.exchange("http://localhost:8080/country/delete/2", HttpMethod.DELETE, body, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
