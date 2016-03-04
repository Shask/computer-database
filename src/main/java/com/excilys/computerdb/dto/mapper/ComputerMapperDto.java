package com.excilys.computerdb.dto.mapper;

import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.models.Company ;
import com.excilys.computerdb.models.Computer ;
import com.excilys.computerdb.services.CompanyServices;
import com.excilys.computerdb.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component ;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps a ComputerDTO into any other objects.
 * 
 * @author Steven Fougeron
 *
 */
@Component
public class ComputerMapperDto {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapperDto.class);

  @Autowired
  private CompanyServices companyServices;

  /**
   * Map a ComputerDTO to a Computer.
   * 
   * @param dto
   *          to map
   * @return Computer mapped, or null is something went wrong
   */
  public Computer toModel(ComputerDto dto) {
    Computer computer = new Computer(dto.getId(), dto.getName());

    // Validate format and Intro<Discontinued using computerSetter
    computer.setIntroduced(DateUtils.convertStringToDate(dto.getIntroduced()));

    // Validate format and Intro<Discontinued using computerSetter
    computer.setDiscontinued(DateUtils.convertStringToDate(dto.getDiscontinued()));

    // get company full details from DB
    Company company = null;
    if ( dto.getCompany() != null && dto.getCompany().getId() > 0 ) {
      company = companyServices.findCompanyById(dto.getCompany().getId());
    }
    if ( company == null ) {
      String msg = "InputControl : Company not found, company set to null ";
      LOGGER.debug(msg);
    } else {
      computer.setCompany(company);
    }

    return computer;
  }

  /**
   * Map an entire list of ComputerDTO in a list a Computer using toModel.
   * 
   * @param dtoList
   *          list to map
   * @return a list of mapped computer
   */
  public List<Computer> toModelList(List<ComputerDto> dtoList) {
    List<Computer> computerList = new ArrayList<>();
    for ( ComputerDto dto : dtoList ) {
      Computer computer = toModel(dto);
      if ( computer != null ) {
        computerList.add(computer);
      }
    }
    return computerList;
  }
}
