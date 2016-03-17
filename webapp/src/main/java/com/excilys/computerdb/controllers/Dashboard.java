package com.excilys.computerdb.controllers;

import com.excilys.computerdb.dao.utils.Page;
import com.excilys.computerdb.dao.utils.Page.OrderBy;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.utils.Parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Servlet implementation class Dashboard.
 * 
 * @author Steven Fougeron
 *
 */
@Controller
@RequestMapping("/dashboard")
public class Dashboard {
  private static final Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
  @Autowired
  private ComputerServices computerServices;

  /**
   * Called when GET method is called on the dashboard.
   * 
   * @param page
   *          number of pages
   * @param nbElements
   *          number of elements per page
   * @param search
   *          element to search
   * @param order
   *          orderby ...
   * @param model
   * 
   * @return .
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getMethod(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer nbElements,
      @RequestParam(required = false) String search, @RequestParam(required = false) String order,
      ModelMap model) {

    Page pageObject = computerServices.getPage();

    List<ComputerDto> listComp;

    // Get number of the page and check is it is not null and <1
    if ( page == null || page < 1 ) {
      page = 1;
    }
    // Get number of elements per pages and set it for the Service
    if ( nbElements != null ) {
      switch ( nbElements ) {
        case 10 :
          pageObject.setPageSize(10);
          break;
        case 50 :
          pageObject.setPageSize(50);
          break;
        case 100 :
          pageObject.setPageSize(100);
          break;
        default :
      }

    }
    pageObject.setCurrentPage(page);

    orderBy(order);

    // Search by name
    long nbTotalComputer ;
    if ( search == null || "".equals(search) ) {
      listComp = ComputerMapperModel.toDtoList(computerServices.findAllComputer());
      nbTotalComputer = computerServices.getCountComputer();
    } else {
      listComp = ComputerMapperModel.toDtoList(computerServices.findComputerByName(search));
      nbTotalComputer =  listComp.size();
    }

    model.addAttribute("nbComputer", nbTotalComputer);
    model.addAttribute("currentpage", page);
    model.addAttribute("computers", listComp);
    return "dashboard";
  }

  /**
   * Method Post of Dashboard Servlet, allowing you to delete computersS
   * 
   * @param selection
   *          of computer id to delete
   * @return to dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String postMethod(@RequestParam(required = false) String selection) {
    // Get Checked boxes from .jsp view and parse them to an array of Int
    if ( selection != null ) {
      List<Integer> listToDelete = Parser.stringToIntList(selection);
      LOGGER.debug("deleting " + listToDelete.toString());
      computerServices.deleteComputer(listToDelete);
    }
    return "dashboard";
  }

  private void orderBy(String order) {
    // Order by
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
