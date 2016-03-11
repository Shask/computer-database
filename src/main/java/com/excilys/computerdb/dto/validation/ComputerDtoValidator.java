package com.excilys.computerdb.dto.validation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.utils.DateUtils;

@Component
public class ComputerDtoValidator implements Validator {

  @Autowired
  private DateUtils dateUtils;

  public boolean supports(Class<?> clazz) {
    return ComputerDto.class.equals(clazz);
  }

  public void validate(Object obj, Errors e) {

    ComputerDto dto = (ComputerDto) obj;
    if ( dto.getName().length() < 3 ) {
      e.rejectValue("name", "name.too.short", "Name is too short ( at least 2 characteres )");
    }
    LocalDate intro = null;
    LocalDate disco = null;

    if ( dto.getIntroduced().length() > 0 ) {// if there is an intro date
      intro = dateUtils.convertStringToDate(dto.getIntroduced()); // Try to parse the String to a
                                                                  // Localdate
      if ( intro == null ) {
        e.rejectValue("introduced", "wrong.date.format", "Wrong date format (yyyy-MM-dd expected)");
      } else {

        if ( dto.getDiscontinued().length() > 0 ) {// if there is a discontinued date
          disco = dateUtils.convertStringToDate(dto.getDiscontinued()); // Try to parse String to a
                                                                        // Localdate
          if ( disco == null ) {
            e.rejectValue("discontinued", "wrong.date.format",
                "Wrong date format (yyyy-MM-dd expected)");
          } else {
            if ( intro.isAfter(disco) ) {
              e.rejectValue("discontinued", "wrong.date.order");
              e.rejectValue("introduced", "wrong.date.order");
            }
          }
        }

      }
    } else {
      if ( dto.getDiscontinued().length() > 0 ) {
        e.rejectValue("discontinued", "wrong.disco.nointro");
      }
    }
  }
}
