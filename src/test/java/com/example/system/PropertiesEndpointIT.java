package com.example.system;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

public class PropertiesEndpointIT {

    @Test
    public void testGetProperties() {
        // system properties
        String hostname = System.getProperty("liberty.hostname", "localhost");
        String port = System.getProperty("liberty.http.port", "9080");
        String url = "http://" + hostname + ":" + port + "/liberty-war-example/";

        // client setup
        Client client = ClientBuilder.newClient();

        client.register(JsrJsonpProvider.class);

        // request
        WebTarget target = client.target(url + "system/properties");
        Response response = target.request().get();

        // response
        assertEquals("Incorrect response code from " + url, 200, response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals("The system property for the local and remote JVM should match", System.getProperty("os.name"),
                obj.getString("os.name"));

        response.close();
    }
}