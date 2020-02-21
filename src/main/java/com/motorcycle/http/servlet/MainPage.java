package com.motorcycle.http.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main-page")
public class MainPage extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
    view.forward(request, response);

//    Test print HTML
//    response.setContentType("text/html");
//    try (PrintWriter out = response.getWriter()) {
//      out.println("<!DOCTYPE html>");
//      out.println("<html><head>");
//      out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
//      out.println("<title>Main page Java Servlet</title></head>");
//      out.println("<body>");
//      out.println("<h1>Main page Java Servlet</h1>");
//      out.println("</body>");
//      out.println("</html>");
//    }
  }
}