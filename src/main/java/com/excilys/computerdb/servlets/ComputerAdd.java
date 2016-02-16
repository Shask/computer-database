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
import com.excilys.computerdb.mapper.CompanyMapper;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

/**
 * Servlet implementation class ComputerAdd
 */
//@WebServlet("/addcomputer")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<CompanyDTO> listCompanies = CompanyMapper.ModeltoDTOList(Services.findAllCompany());
		request.setAttribute("companies", listCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
				computer = InputControl.validation(computerdto);
				Services.insertComputer(computer);
			} catch (ValidationException e) {
				LOGGER.debug("Error during validation or insertion : Computer was not added");
				e.printStackTrace();
			}
		}
		doGet(request, response);
	}

}
