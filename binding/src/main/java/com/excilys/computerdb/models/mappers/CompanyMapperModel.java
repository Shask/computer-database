package com.excilys.computerdb.models.mappers;

import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Company ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that allow you to convert a Company Model into any type of Company.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyMapperModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMapperModel.class);

  /**
   * Parse a company into a companyDTO.
   * 
   * @param company
   *          Company to parse
   * @return CompanyDTo corresponding, null if company was null
   */
  public static CompanyDto toDto(Company company) {
    if ( company == null ) {
      LOGGER.debug("Failed to map Company Model to DTO : company to map is null");
      return null;
    }
    CompanyDto dto;
    if ( company.getId() > 0 ) {
      dto = new CompanyDto(company.getId());
    } else {
      LOGGER.debug("Failed to map Company Model to DTO : name is null");
      throw new MappingException();
    }
    dto.setName(company.getName());
    return dto;
  }

  /**
   * Map an entire list of Computer to DTO using ModelToDTO.
   * 
   * @param companyList
   *          to map
   * @return a list of mapped dto
   */
  public static List<CompanyDto> toDtoList(List<Company> companyList) {
    List<CompanyDto> dtoList = new ArrayList<>();
    for ( Company company : companyList ) {
      CompanyDto cdto = toDto(company);
      if ( cdto != null ) {
        dtoList.add(cdto);
      }
    }
    return dtoList;
  }
}
