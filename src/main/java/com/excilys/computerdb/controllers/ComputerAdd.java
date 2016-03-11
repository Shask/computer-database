package com.excilys.computerdb.controllers;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ComputerDtoValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  @Autowired
  private ComputerDtoValidator dtoValidator;

  @RequestMapping(method = RequestMethod.GET)
  public String getMethod(ModelMap model) {

    List<CompanyDto> listCompanies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
    model.addAttribute("companies", listCompanies);
    model.addAttribute("computerdto", new ComputerDto());
    return "addcomputer";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String postMethod(@ModelAttribute("computerdto") ComputerDto computerdto, ModelMap model,
      BindingResult result ) {

    dtoValidator.validate(computerdto, result);

    if ( result.hasErrors() ) {
      List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
      model.addAttribute("companies", companies);
      return "addcomputer";
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
