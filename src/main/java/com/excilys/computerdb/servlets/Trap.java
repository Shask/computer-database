package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Trap extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2239463894007749840L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType( "text/html" );
		
		PrintWriter out = response.getWriter();

		out.println( "<HTML>" );
		out.println( "<HEAD>");		
		out.println( "<TITLE>coucou</TITLE>" );	
		out.println( "</HEAD>" );
		out.println( "<BODY>" );
		while(true)
		{
			
			out.println( "<H1>coucou</H1>" );	
			
		}
	}
}
