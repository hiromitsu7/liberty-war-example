package com.example.system;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;

public class PropertiesEndpointIT {

    @Test
    public void testGetProperties() {
        String url = "http://localhost:9080/liberty-war-example/";
        RestAssured.get(url + "system/properties").then().statusCode(200).assertThat()
                .body("'java.specification.version'", Matchers.equalTo("17"));
    }
}