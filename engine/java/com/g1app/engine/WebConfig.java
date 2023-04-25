package com.g1app.engine;

import com.g1app.engine.servlets.PaymentServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

//@Configuration
public class WebConfig {
    @Bean
    public ServletRegistrationBean<HttpServlet> countryServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new PaymentServlet());
        servRegBean.addUrlMappings("/bookandpay/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
}
