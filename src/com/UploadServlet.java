package com;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// In order to upload a file, the receiving servlet needs to be declared in its own
// class to receive the HttpRequest. It is not possible to directly call
// the Database, otherwise the request parameters will be null.

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet{
	private Database database = new Database();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		   database.uploadData(request, response);
	}
}