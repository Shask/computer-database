package com.excilys.computerdb.servlets;



import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.model.mapper.ComputerMapperModel;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.service.Page;
import com.excilys.computerdb.service.Page.OrderBy;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.Parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
public class Dashboard extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
  private ComputerdbServices services = ComputerdbServices.getInstance();
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet().
   */
  public Dashboard() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response).
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer pageNeeded = 1;
    Page page = services.getPage();
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
      listComp = ComputerMapperModel.toDtoList(services.findAllComputer());
      nbTotalComputer = services.getCountComputer();
    } else {
      listComp = ComputerMapperModel.toDtoList(services.findComputerByName(searchName));
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
    services.deleteComputer(listToDelete);

    // selection

    doGet(request, response);
  }

  private void orderBy(HttpServletRequest request) {
    // Order by
    String order = request.getParameter("order");
    if ( order != null && !"".equals(order) ) {
      switch ( order ) {
        case "name" :
          services.getPage().setOrder(OrderBy.NAME);
          break;
        case "introduced" :
          services.getPage().setOrder(OrderBy.INTRODUCED);
          break;
        case "discontinued" :
          services.getPage().setOrder(OrderBy.DISCONTINUED);
          break;
        case "company_name" :
          services.getPage().setOrder(OrderBy.COMPANY_NAME);
          break;
        default :
      }
    }
  }

}
