package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.service.Page;

/**
 * Servlet implementation class Dashboard
 */
// @WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private ComputerdbServices Services = ComputerdbServices.getInstance();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer pageNeeded = 1;
		Page page = Services.getPage();
		List<Computer> listComp;
		
		//Get number of the page and check is it is not null and >0
		String pageParam = request.getParameter("page");
		if (pageParam == null) {
		} else {
			pageNeeded = Integer.parseInt(pageParam);
			if (pageNeeded < 1) {
				pageNeeded = 1;
			}
		}

		String nbElements = request.getParameter("nbElements");
		if (nbElements == null) {

		} else
			switch (nbElements) {
			case "10":
				page.setPageSize(10);
				break;
			case "50":
				page.setPageSize(50);
				break;
			case "100":
				page.setPageSize(100);
				break;
			}
		page.setCurrentPage(pageNeeded);
		String Computername = request.getParameter("search");
		if(Computername==null)
		{
			listComp = Services.findAllComputer();
		}
		else
		{
			listComp = Services.findComputerByName(Computername);
		}
		int nbTotalComputer = Services.getCountComputer();
		
		request.setAttribute("nbComputer", nbTotalComputer);
		request.setAttribute("currentpage", pageNeeded);
		request.setAttribute("computers", listComp);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
