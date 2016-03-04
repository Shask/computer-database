package com.excilys.computerdb.servlets;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ValidatorDto;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.mappers.CompanyMapperModel;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
import com.excilys.computerdb.services.CompanyServices;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ComputerEdit extends HttpServlet {
  private static final long serialVersionUID = -961736017555718259L;
  @Autowired
  private ComputerServices computerServices;
  @Autowired
  private CompanyServices companyServices;
  @Autowired
  private ComputerMapperDto computerMapperDto;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);

  private int currentId = 0;

  public ComputerEdit() {
    super();
  }

  /**
   * Methode to load Spring context in servlet.
   * 
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String computerId = request.getParameter("id");
    ComputerDto dto = null;
    if ( computerId != null && InputControl.testInt(computerId) ) {
      try {
        dto = ComputerMapperModel
            .toDto(computerServices.findComputerById(Integer.parseInt(computerId)));
        List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
        request.setAttribute("computer", dto);
        request.setAttribute("companies", companies);
        currentId = Integer.parseInt(computerId);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
            .forward(request, response);

      } catch ( MappingException e ) {
        response.sendRedirect("/computerdb/dashboard");
      }
    } else {
      // If there is something wrong with the id in parameters
      response.sendRedirect("/computerdb/dashboard");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String name = request.getParameter("computerName");
    ComputerDto computerdto = new ComputerDto(name);
    computerdto.setId(currentId);
    computerdto.setIntroduced(request.getParameter("introduced"));
    computerdto.setDiscontinued(request.getParameter("discontinued"));
    if ( request.getParameter("company") != null
        && !"null".equals(request.getParameter("company")) ) {
      computerdto.setCompany(new CompanyDto(Integer.parseInt(request.getParameter("company"))));
    } else {
      computerdto.setCompany(null);
    }
    Computer computer;
    try {
      ValidatorDto.validate(computerdto);
      computer = computerMapperDto.toModel(computerdto);
      computerServices.updateComputer(computer);
    } catch ( ValidationException | NullPointerException e ) {
      LOGGER.debug("Error during update : Computer was not added");
      e.printStackTrace();
      request.setAttribute("id", currentId);
      doGet(request, response);
    }
    currentId = -1;
    response.sendRedirect("/computerdb/dashboard");
  }

}
