package com.example.system;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@RequestScoped
@Path("/properties")
public class SystemResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getPropertiesTime", description = "Time needed to get the JVM system properties")
    @Counted(absolute = true, description = "Number of times the JVM system properties are requested")
    public Response getProperties() {
        return Response.ok(System.getProperties()).build();
    }
}
