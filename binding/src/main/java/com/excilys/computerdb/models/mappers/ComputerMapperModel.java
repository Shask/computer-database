package com.excilys.computerdb.models.mappers;

import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.DateTimeException ;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Computer Mapper (model to anything else).
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerMapperModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapperModel.class);

  /**
   * Map a computer to a ComputerDTO throws a MappingException if computer or computer name is null.
   * 
   * @param computer
   *          to map
   * @return a dto from the computer in params
   */
  public static ComputerDto toDto(Computer computer) {
    if ( computer == null ) {
      LOGGER.debug("Failed to map Computer Model to DTO : computer is null");
      throw new MappingException();
    }
    ComputerDto dto;
    if ( computer.getName() != null ) {
      dto = new ComputerDto(computer.getName());
    } else {
      LOGGER.debug("Failed to map Computer Model to DTO : name is null");
      throw new MappingException();
    }
    if ( computer.getId() > 0 ) {
      dto.setId(computer.getId());
    }
    if ( computer.getIntroduced() != null ) {
      dto.setIntroduced(convertLocalDateToString(computer.getIntroduced()));
    }
    if ( computer.getDiscontinued() != null ) {
      dto.setDiscontinued(convertLocalDateToString(computer.getDiscontinued()));
    }
    if ( computer.getCompany() != null ) {
      dto.setCompanyId(computer.getCompany().getId());
      dto.setCompanyName(computer.getCompany().getName());
    }

    return dto;
  }

  /**
   * List an entire list of Computer model to a list of DTO using ModelToDTO.
   * 
   * @param computerList
   *          list to map
   * @return list of mapped dto
   */
  public static List<ComputerDto> toDtoList(List<Computer> computerList) {
    List<ComputerDto> dtoList = new ArrayList<>();
    for ( Computer computer : computerList ) {
      ComputerDto cdto = toDto(computer);
      if ( cdto != null ) {
        dtoList.add(cdto);
      }
    }
    return dtoList;
  }

  /**
   * Return a String formated for the country contained in LocalContextHoler
   * 
   * @param date
   *          to parse
   * @return formated string or null
   */
  static String convertLocalDateToString(LocalDate date) {
    if ( date != null ) {
      String parseDate = null;
      //default format ISO
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      
      //test if the current langage is french 
      if ( LocaleContextHolder.getLocale().getLanguage().equals(new Locale("fr").getLanguage()) ) {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      } else {
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      }
      try {
        parseDate = date.format(formatter);
      } catch ( DateTimeException e ) {
        LOGGER.debug("wrong date format : parsed to null");
      }
     return parseDate;
    }
    return null;
  }

}
