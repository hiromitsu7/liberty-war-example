package com.example.system;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/properties")
public class SystemResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperties() {
        return Response.ok(System.getProperties()).build();
    }
}
