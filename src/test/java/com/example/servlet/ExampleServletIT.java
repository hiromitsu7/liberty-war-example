package com.example.servlet;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class ExampleServletIT {

    @Test
    public void testDoGet() {
        String url = "http://localhost:9080/liberty-war-example/";
        RestAssured.get(url + "ExampleServlet").then().statusCode(200);
    }
}
