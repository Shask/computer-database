package com.excilys.computerdb.dto.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.utils.exception.ValidationException;
/**
 * Allow you to validate if a object of DTO type is valid
 * @author Steven Fougeron
 *
 */
public class ValidatorDTO {
	private final static Logger LOGGER = LoggerFactory.getLogger(ValidatorDTO.class);

	/**
	 * Validate a ComputerDTO, if every field is properly filled in, convert it
	 * to Computer, send a ValidationException if conversion not possible
	 * 
	 * @param computerdto to validate and convert
	 * @return a computer
	 * @throws ValidationException
	 */
	public static boolean validate(ComputerDTO computerdto) throws ValidationException {

		if (computerdto == null) {
			String msg = "InputControl : computerDto is null";
			LOGGER.debug(msg);
			throw new ValidationException(msg);
		}
		String computerDtoName = computerdto.getName();
		if (computerDtoName == null || computerDtoName.trim().length() < 3) {
			String msg = "InputControl : computerDto name is too short";
			LOGGER.debug(msg);
			throw new ValidationException(msg);
		}
		if (computerDtoName.contains("%") || computerDtoName.contains("/") || computerDtoName.contains("\"") || computerDtoName.contains("\\") || computerDtoName.contains("<") || computerDtoName.contains(">")) {
			String msg = "InputControl : computerDto name contains invalid charactere ( %, /, \", | ) \n computer not added";
			LOGGER.debug(msg);
			throw new ValidationException(msg);
		}

		return true;
	}
}
