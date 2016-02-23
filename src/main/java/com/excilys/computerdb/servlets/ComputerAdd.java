package com.excilys.computerdb.servlets;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ValidatorDto;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.mapper.CompanyMapperModel;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComputerAdd.
 * 
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerAdd extends HttpServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);
  private ComputerdbServices services = ComputerdbServices.getInstance();
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet().
   */
  public ComputerAdd() {
    super();

  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<CompanyDto> listCompanies = CompanyMapperModel.toDtoList(services.findAllCompany());
    request.setAttribute("companies", listCompanies);
    this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,
        response);

    String pageParam = request.getParameter("page");
    if ( pageParam != null || "".equals(pageParam) || !InputControl.testInt(pageParam) ) {
      request.setAttribute("currentpage", pageParam);
    }
    String nbElemParam = request.getParameter("nbElements");
    if ( nbElemParam != null || "".equals(nbElemParam) || !InputControl.testInt(pageParam) ) {
      request.setAttribute("pagesize", nbElemParam);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String name = request.getParameter("computerName");
    if ( name != null ) {
      ComputerDto computerdto = new ComputerDto(name);
      computerdto.setIntroduced(request.getParameter("introduced"));
      computerdto.setDiscontinued(request.getParameter("discontinued"));
      if ( request.getParameter("company") != null ) {
        computerdto.setCompany(new CompanyDto(Integer.parseInt(request.getParameter("company"))));
      }
      Computer computer;
      try {
        ValidatorDto.validate(computerdto);
        computer = ComputerMapperDto.toModel(computerdto);
        services.insertComputer(computer);
      } catch ( ValidationException e ) {
        LOGGER.debug("Error during validation or insertion : Computer was not added");
        e.printStackTrace();
      }
    }
    doGet(request, response);
  }

}
