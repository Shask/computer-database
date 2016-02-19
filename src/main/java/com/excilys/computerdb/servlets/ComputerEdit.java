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
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.mapper.CompanyMapperModel;
import com.excilys.computerdb.model.mapper.ComputerMapperModel;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

public class ComputerEdit extends HttpServlet {
	private static final long serialVersionUID = -961736017555718259L;
	private static ComputerdbServices Services = ComputerdbServices.getInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);

	private int currentID =0;
	
	public ComputerEdit() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ComputerId = request.getParameter("id");
		ComputerDTO dto = null;
		if (ComputerId != null && InputControl.testInt(ComputerId)) {
			try {
				dto = ComputerMapperModel.toDTO(Services.findComputerById(Integer.parseInt(ComputerId)));
				List<CompanyDTO> companies = CompanyMapperModel.toDTOList(Services.findAllCompany());
				request.setAttribute("computer", dto);
				request.setAttribute("companies", companies);
				currentID=Integer.parseInt(ComputerId);
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);

			} catch (MappingException e) {
				response.sendRedirect("/computerdb/dashboard");
			}
		} else {
			//If there is something wrong with the id in parameters
			response.sendRedirect("/computerdb/dashboard");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("computerName");
			ComputerDTO computerdto = new ComputerDTO(name);
			computerdto.setId(currentID);
			computerdto.setIntroduced(request.getParameter("introduced"));
			computerdto.setDiscontinued(request.getParameter("discontinued"));
			if (request.getParameter("company") != null && !"null".equals(request.getParameter("company"))) {
				computerdto.setCompany(new CompanyDTO(Integer.parseInt(request.getParameter("company"))));
			}
			else
			{
				computerdto.setCompany(null);
			}
			Computer computer;
			try {
				ValidatorDTO.validate(computerdto);
				computer = ComputerMapperDTO.toModel(computerdto);
				Services.updateComputer(computer);
			} catch (ValidationException | NullPointerException e) {
				LOGGER.debug("Error during update : Computer was not added");
				e.printStackTrace();
				request.setAttribute("id", currentID);
				doGet(request, response);
			}
			currentID=-1;
			response.sendRedirect("/computerdb/dashboard");
		}
		
		
	
	

}
