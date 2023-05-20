package com.example.db2sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/employee")
public class Db2EmployeeResouce {

    @Resource(name = "jdbc/SampleDS")
    private DataSource ds;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees() {

        List<String> result = new ArrayList<>();

        try (Connection con = ds.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT EMPNO, FIRSTNME FROM EMPLOYEE");) {
            while (rs.next()) {
                String empNo = rs.getString(1);
                String firstName = rs.getString(2);
                result.add(empNo + ": " + firstName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Response.ok(result).build();
    }
}
