package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/ExampleServlet" })
public class ExampleServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static Logger logger = LoggerFactory.getLogger(ExampleServlet.class);

  public ExampleServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.info("ExampleServlet#doGet()");

    PrintWriter w = response.getWriter();
    w.println("ExampleServlet#doGet()");
    w.close();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.info("ExampleServlet#doPost()");

    PrintWriter w = response.getWriter();
    w.println("ExampleServlet#doPost()");
    w.close();
  }

}
