package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.mapper.ComputerMapper;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.service.Page;
import com.excilys.computerdb.utils.InputControl;

/**
 * Servlet implementation class Dashboard
 * @author Steven Fougeron
 *
 */
public class Dashboard extends HttpServlet {

	private ComputerdbServices Services = ComputerdbServices.getInstance();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer pageNeeded = 1;
		Page page = Services.getPage();
		List<ComputerDTO> listComp;

		// Get number of the page and check is it is not null and >0
		String pageParam = request.getParameter("page");
		if (pageParam == null ||  !InputControl.testInt(pageParam)) {
		} else {
			pageNeeded = Integer.parseInt(pageParam);
			if (pageNeeded < 1) {
				pageNeeded = 1;
			}
		}

		String nbElements = request.getParameter("nbElements");
		if (nbElements == null || !InputControl.testInt(pageParam)) {

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
		int nbTotalComputer = 0;
		if (Computername == null) {
			listComp = ComputerMapper.ModeltoDTOList(Services.findAllComputer());
			nbTotalComputer = Services.getCountComputer();
		} else {
			listComp = ComputerMapper.ModeltoDTOList(Services.findComputerByName(Computername));
			nbTotalComputer = listComp.size();
		}

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
		doGet(request, response);
	}

}
