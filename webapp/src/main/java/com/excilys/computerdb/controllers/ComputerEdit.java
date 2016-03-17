package com.excilys.computerdb.controllers;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ComputerDtoValidator;
import com.excilys.computerdb.dto.validation.ValidatorDto;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.mappers.CompanyMapperModel;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/editcomputer")
public class ComputerEdit {
  @Autowired
  private ComputerServices computerServices;
  @Autowired
  private CompanyServices companyServices;
  @Autowired
  private ComputerMapperDto computerMapperDto;
  @Autowired
  private ComputerDtoValidator dtoValidator;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerEdit.class);

  private int currentId = 0;

  /**
   * Method get of the edition page, prefill the page with computer specs using it's id
   * 
   * @param id
   *          id of the computer to edit
   * @param model
   *          ModelMap
   * @return to dashboard if correct
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getMethod(@RequestParam(required = false) Integer id, ModelMap model) {
    if ( id != null && id > 0 ) {
      try {
        LOGGER.info("Retreiving computer with id : " + id);
        fillFields(id, model);
        currentId = id;

      } catch ( MappingException e ) {
        LOGGER.error("Error during mapping, redirection to dashboard...");
        return "redirect:/dashboard";
      }
    } else {
      // If there is something wrong with the id in parameters
      LOGGER
          .error("there is something wrong with the id in parameters, redirection to dashboard...");
      return "redirect:/dashboard";
    }
    model.addAttribute("id", id);
    return "editcomputer";
  }

  /**
   * Post method to the edit page allowing you to modify a computer using its id.
   * 
   * @param computerName
   *          name of the computer
   * @param introduced
   *          date
   * @param discontinued
   *          date
   * @param company
   *          company
   * @param model
   *          modelmap
   * @return to dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  public String postMethod(@ModelAttribute("computerdto") ComputerDto computerdto, ModelMap model,
      BindingResult result) {
    dtoValidator.validate(computerdto, result);

    System.err.println(result);
    if ( result.hasErrors() ) {
      System.err.println("error");
      model.addAttribute("id", currentId);
      List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());

      model.addAttribute("companies", companies);
      return "editcomputer";
    }
    computerdto.setId(currentId);

    Computer computer;
    try {
      ValidatorDto.validate(computerdto);
      computer = computerMapperDto.toModel(computerdto);
      computerServices.updateComputer(computer);
    } catch ( ValidationException | NullPointerException e ) {
      LOGGER.debug("Error during update : Computer was not added");
      e.printStackTrace();
      model.addAttribute("id", currentId);
      return "editcomputer";
    }
    currentId = -1;
    return "redirect:/dashboard";
  }

  public void fillFields(Integer id, ModelMap model) {
    currentId = id;
    ComputerDto dto = ComputerMapperModel.toDto(computerServices.findComputerById(id));
    List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
    model.addAttribute("computerdto", dto);
    model.addAttribute("companies", companies);
  }

}
