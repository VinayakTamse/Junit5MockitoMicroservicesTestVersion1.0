package com.myapp.Titan.restassured.tests;

import com.myapp.Titan.model.Country;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.serializer.SerializeException;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
public class SampleTest2 {

    @Test
    public void  test_002()
    {
            given()
                    .when()

                    .get("http://localhost:8080/country/1")
                    .then().statusCode(200).and().body("id",equalTo(1))
                    .and().assertThat().body("name", containsString("India"))
                    .log().all();
    }

    @Test
    public void test_post_request()  {
        Country country = new Country(13, "Demozyu", "Demm");


        given()
                .header("Content-Type", "application/json")
                .body(country)
                .when().post("http://localhost:8080/country/add")
                .then().statusCode(201).log().all();

    }

    @Test
    public void test_delete_request()
    {
        given()
                .when()
                .delete("http://localhost:8080/country/delete/10")
                .then().statusCode(200).log().all();
    }
}
