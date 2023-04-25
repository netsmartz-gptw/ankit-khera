package com.g1app.engine.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getPart("bookingReference");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Hello India!</h3>");
    }
}
