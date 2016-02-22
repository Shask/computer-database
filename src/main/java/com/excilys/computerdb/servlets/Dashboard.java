package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.model.mapper.ComputerMapperModel;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.service.Page;
import com.excilys.computerdb.service.Page.ORDER_BY;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.Parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class Dashboard
 * 
 * @author Steven Fougeron
 *
 */
public class Dashboard extends HttpServlet {
	private final static Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer pageNeeded = 1;
		Page page = Services.getPage();
		List<ComputerDTO> listComp;

		// Get number of the page and check is it is not null and >0
		String pageParam = request.getParameter("page");
		if (pageParam == null || "".equals(pageParam) || !InputControl.testInt(pageParam)) {
		} else {
			pageNeeded = Integer.parseInt(pageParam);
			if (pageNeeded < 1) {
				pageNeeded = 1;
			}
		}
		// Get number of elements per pages and set it for the Service
		String nbElements = request.getParameter("nbElements");
		if (nbElements == null || "".equals(nbElements) || !InputControl.testInt(pageParam)) {

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

		orderBy(request);

		// Search by name
		String searchName = request.getParameter("search");
		int nbTotalComputer = 0;
		if (searchName == null || "".equals(searchName)) {
			listComp = ComputerMapperModel.toDTOList(Services.findAllComputer());
			nbTotalComputer = Services.getCountComputer();
		} else {
			listComp = ComputerMapperModel.toDTOList(Services.findComputerByName(searchName));
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get Checked boxes from .jsp view and parse them to an array of Int
		String selection = request.getParameter("selection");
		List<Integer> listToDelete = Parser.StringToIntList(selection);
		LOGGER.debug("deleting " + listToDelete.toString());
		Services.deleteComputer(listToDelete);

		// selection

		doGet(request, response);
	}

	private void orderBy(HttpServletRequest request) {
		// Order by
		String order = request.getParameter("order");
		if (order != null && !"".equals(order)) {
			switch (order) {
			case "name":
				Services.getPage().setOrder(ORDER_BY.NAME);
				break;
			case "introduced":
				Services.getPage().setOrder(ORDER_BY.INTRODUCED);
				break;
			case "discontinued":
				Services.getPage().setOrder(ORDER_BY.DISCONTINUED);
				break;
			case "company_name":
				Services.getPage().setOrder(ORDER_BY.COMPANY_NAME);
				break;
			default:
			}
		}
	}

}
