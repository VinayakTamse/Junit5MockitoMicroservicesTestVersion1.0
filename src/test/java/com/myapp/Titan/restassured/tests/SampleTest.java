package com.myapp.Titan.restassured.tests;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = {SampleTest.class})
public class SampleTest {

    @Test
    public void test_001()
    {
        RestAssured.baseURI = "http://localhost:8080/";
        RequestSpecification request = given();

        Response response = request.request(Method.GET, "country/all");

        System.out.println(response.getBody().asPrettyString());
    }

}
