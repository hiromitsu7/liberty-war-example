package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "/ExampleServlet" })
public class ExampleServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static Logger logger = LoggerFactory.getLogger(ExampleServlet.class);

  public ExampleServlet() {
    super();
  }

  /**
   * 応答ボディのStreamへの書き出しをWrapperでトレースします。
   */
  @Override
  @Timed(name = "doGet", description = "ExampleServlet")
  @Counted(absolute = true, description = "ExampleServlet")
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
