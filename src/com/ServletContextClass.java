package com;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class ServletContextClass {
	ServletContext context;
	    public void contextInitialized(ServletContextEvent contextEvent) {
	        System.out.println("Context Created");
	        context = contextEvent.getServletContext();
	        // set variable to servlet context
	        context.setAttribute("TEST", "TEST_VALUE");
	    }
	    public void contextDestroyed(ServletContextEvent contextEvent) {
	    	// This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
	        Enumeration<Driver> drivers = DriverManager.getDrivers();
	        while (drivers.hasMoreElements()) {
	            Driver driver = drivers.nextElement();
	            try {
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException e) {
	            }

	        }
	        
	        context = contextEvent.getServletContext();
	        System.out.println("Context Destroyed");
	    }

}
