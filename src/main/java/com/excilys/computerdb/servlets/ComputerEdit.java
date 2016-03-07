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

@Controller
@RequestMapping("/editcomputer")
public class ComputerEdit {
  @Autowired
  private ComputerServices computerServices;
  @Autowired
  private CompanyServices companyServices;
  @Autowired
  private ComputerMapperDto computerMapperDto;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerAdd.class);

  private int currentId = 0;

  @RequestMapping(method = RequestMethod.GET)
  public String getMethod(@RequestParam Integer id, ModelMap model) {
    ComputerDto dto = null;
    if ( id != null ) {
      try {
        LOGGER.info("Retreiving computer with id : " + id);
        currentId = id;
        dto = ComputerMapperModel.toDto(computerServices.findComputerById(id));
        List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
        model.addAttribute("computer", dto);
        model.addAttribute("companies", companies);

      } catch ( MappingException e ) {
        LOGGER.error("Error during mapping, redirection to dashboard...");
        return "dashboard";
      }
    } else {
      // If there is something wrong with the id in parameters
      LOGGER.error(
          "there is something wrong with the id in parameters, redirection to dashboard...");
      return "dashboard";
    }
    model.addAttribute("id",id);
    return "editcomputer";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String postMethod(@RequestParam String computerName,
      @RequestParam(required = false) String introduced,
      @RequestParam(required = false) String discontinued, @RequestParam Integer company,
      ModelMap model) {

    ComputerDto computerdto = new ComputerDto(computerName);
    computerdto.setId(currentId);
    computerdto.setIntroduced(introduced);
    computerdto.setDiscontinued(discontinued);
    if ( company != null && !"null".equals(company) && company > 0 ) {
      computerdto.setCompany(new CompanyDto(company));
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
      model.addAttribute("id", currentId);
      return "editcomputer";
    }
    currentId = -1;
    return "dashboard";
  }

}
