package com.excilys.computerdb.servlets;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ValidatorDto;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.mappers.CompanyMapperModel;
import com.excilys.computerdb.services.CompanyServices;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.utils.exception.ValidationException;

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
 * Servlet implementation class ComputerAdd.
 * 
 * 
 * @author Steven Fougeron
 *
 */
@Controller
@RequestMapping("/addcomputer")
public class ComputerAdd {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);
  @Autowired
  private ComputerServices computerServices;
  @Autowired
  private CompanyServices companyServices;
  @Autowired
  private ComputerMapperDto computerMapperDto;

  @RequestMapping(method = RequestMethod.GET)
  public String getMethod(ModelMap model) {

    List<CompanyDto> listCompanies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
    model.addAttribute("companies", listCompanies);

    /*
     * String pageParam = request.getParameter("page"); if ( pageParam != null ||
     * "".equals(pageParam) || !InputControl.testInt(pageParam) ) {
     * request.setAttribute("currentpage", pageParam); } String nbElemParam =
     * request.getParameter("nbElements"); if ( nbElemParam != null || "".equals(nbElemParam) ||
     * !InputControl.testInt(pageParam) ) { request.setAttribute("pagesize", nbElemParam); }
     */
    return "addcomputer";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@RequestParam String computerName,
      @RequestParam(required = false) String introduced,
      @RequestParam(required = false) String discontinued,
      @RequestParam Integer company,
      ModelMap model) {
    ComputerDto computerdto = new ComputerDto(computerName);
    computerdto.setIntroduced(introduced);
    computerdto.setDiscontinued(discontinued);
    if ( company != null ) {
      computerdto.setCompany(new CompanyDto(company));
    }
    Computer computer;
    try {
      ValidatorDto.validate(computerdto);
      computer = computerMapperDto.toModel(computerdto);
      computerServices.insertComputer(computer);
    } catch ( ValidationException e ) {
      LOGGER.debug("Error during validation or insertion : Computer was not added");
      e.printStackTrace();
      return "addcomputer";
    }

    return "redirect:/dashboard";
  }

}
