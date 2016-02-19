package com.excilys.computerdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.dto.mapper.ComputerMapperDTO;
import com.excilys.computerdb.dto.validation.ValidatorDTO;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.mapper.CompanyMapperModel;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

/**
 * Servlet implementation class ComputerAdd
 * 
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerAdd extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);
	private ComputerdbServices Services = ComputerdbServices.getInstance();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerAdd() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<CompanyDTO> listCompanies = CompanyMapperModel.toDTOList(Services.findAllCompany());
		request.setAttribute("companies", listCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

		String pageParam = request.getParameter("page");
		if (pageParam != null || "".equals(pageParam) || !InputControl.testInt(pageParam)) {
			request.setAttribute("currentpage", pageParam);
		}
		String nbElemParam = request.getParameter("nbElements");
		if (nbElemParam != null || "".equals(nbElemParam) || !InputControl.testInt(pageParam)) {
			request.setAttribute("pagesize", nbElemParam);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		if (name != null) {
			ComputerDTO computerdto = new ComputerDTO(name);
			computerdto.setIntroduced(request.getParameter("introduced"));
			computerdto.setDiscontinued(request.getParameter("discontinued"));
			if (request.getParameter("company") != null) {
				computerdto.setCompany(new CompanyDTO(Integer.parseInt(request.getParameter("company"))));
			}
			Computer computer;
			try {
				ValidatorDTO.validate(computerdto);
				computer = ComputerMapperDTO.toModel(computerdto);
				Services.insertComputer(computer);
			} catch (ValidationException e) {
				LOGGER.debug("Error during validation or insertion : Computer was not added");
				e.printStackTrace();
			}
		}
		doGet(request, response);
	}

}
