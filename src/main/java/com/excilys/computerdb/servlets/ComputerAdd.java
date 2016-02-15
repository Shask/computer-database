package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.InputControl;

/**
 * Servlet implementation class ComputerAdd
 */
//@WebServlet("/addcomputer")
public class ComputerAdd extends HttpServlet {
	private ComputerdbServices Services = ComputerdbServices.getInstance();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Company> listCompanies = Services.findAllCompany();
		request.setAttribute("companies", listCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("computerName");
		if (name != null) {
			Computer computer = new Computer(name);
			computer.setIntroduced(InputControl.convertStringToDate( request.getParameter("introduced")));
			computer.setDiscontinued(InputControl.convertStringToDate( request.getParameter("discontinued")));
			computer.setCompany(new Company(Integer.parseInt(request.getParameter("company")),"AutocreatedName"));
			Services.insertComputer(computer);
		}
		
		doGet(request, response);
	}

}
