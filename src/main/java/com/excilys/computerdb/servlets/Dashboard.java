package com.excilys.computerdb.servlets;

import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.services.Page;
import com.excilys.computerdb.services.Page.OrderBy;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.Parser;

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

/**
 * Servlet implementation class Dashboard.
 * 
 * @author Steven Fougeron
 *
 */
@Controller
public class Dashboard extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
  @Autowired
  private ComputerServices computerServices;
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet().
   */
  public Dashboard() {
    super();
  }

  /**
   *Methode to load Spring context in servlet.
   * 
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer pageNeeded = 1;
    Page page = computerServices.getPage();
    List<ComputerDto> listComp;

    // Get number of the page and check is it is not null and >0
    String pageParam = request.getParameter("page");
    if ( pageParam != null && !"".equals(pageParam) && InputControl.testInt(pageParam) ) {
      pageNeeded = Integer.parseInt(pageParam);
      if ( pageNeeded < 1 ) {
        pageNeeded = 1;
      }
    }
    // Get number of elements per pages and set it for the Service
    String nbElements = request.getParameter("nbElements");
    if ( nbElements != null && !"".equals(nbElements) && InputControl.testInt(pageParam) ) {
      switch ( nbElements ) {
        case "10" :
          page.setPageSize(10);
          break;
        case "50" :
          page.setPageSize(50);
          break;
        case "100" :
          page.setPageSize(100);
          break;
        default :
      }
    }
    page.setCurrentPage(pageNeeded);

    orderBy(request);

    // Search by name
    String searchName = request.getParameter("search");
    int nbTotalComputer = 0;
    if ( searchName == null || "".equals(searchName) ) {
      listComp = ComputerMapperModel.toDtoList(computerServices.findAllComputer());
      nbTotalComputer = computerServices.getCountComputer();
    } else {
      listComp = ComputerMapperModel.toDtoList(computerServices.findComputerByName(searchName));
      nbTotalComputer = listComp.size();
    }

    request.setAttribute("nbComputer", nbTotalComputer);
    request.setAttribute("currentpage", pageNeeded);
    request.setAttribute("computers", listComp);

    this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,
        response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Get Checked boxes from .jsp view and parse them to an array of Int
    String selection = request.getParameter("selection");
    List<Integer> listToDelete = Parser.stringToIntList(selection);
    LOGGER.debug("deleting " + listToDelete.toString());
    computerServices.deleteComputer(listToDelete);

    // selection

    doGet(request, response);
  }

  private void orderBy(HttpServletRequest request) {
    // Order by
    String order = request.getParameter("order");
    if ( order != null && !"".equals(order) ) {
      switch ( order ) {
        case "name" :
          computerServices.getPage().setOrder(OrderBy.NAME);
          break;
        case "introduced" :
          computerServices.getPage().setOrder(OrderBy.INTRODUCED);
          break;
        case "discontinued" :
          computerServices.getPage().setOrder(OrderBy.DISCONTINUED);
          break;
        case "company_name" :
          computerServices.getPage().setOrder(OrderBy.COMPANY_NAME);
          break;
        default :
      }
    }
  }

}
