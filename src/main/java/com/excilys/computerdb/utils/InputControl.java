package com.excilys.computerdb.utils;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.mapper.ComputerMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.exception.ValidationException;

/**
 * Interface with static method allowing you to test input from user
 * 
 * @author excilys
 *
 */
public interface InputControl {

	static final Logger LOGGER = LoggerFactory.getLogger(InputControl.class);

	/**
	 * Method you can use to test if the string in parameters is a number
	 * 
	 * @param s string to test
	 * @return true if it is a number, false if not
	 */
	static boolean testInt(String s) {
		if (s == null) {
			return false;
		}
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LOGGER.warn("Input not valid : not an integer");
			return false;
		}
		return true;
	}

	

	/**
	 * Validate a ComputerDTO, if every field is properly filled in, convert it
	 * to Computer, send a ValidationException if conversion not possible
	 * 
	 * @param computerdto to validate and convert
	 * @return a computer
	 * @throws ValidationException
	 */
	static Computer validation(ComputerDTO computerdto) throws ValidationException {
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
		if (computerDtoName.contains("%") || computerDtoName.contains("/") || computerDtoName.contains("\"")
				|| computerDtoName.contains("\\")) {
			String msg = "InputControl : computerDto name contains invalid charactere ( %, /, \", | ) \n computer not added";
			LOGGER.debug(msg);
			throw new ValidationException(msg);
		}
		Computer computer = new Computer(computerdto.getId(), computerdto.getName());

		// Validate format and Intro<Discontinued using computerSetter
		computer.setIntroduced(ComputerMapper.convertStringToDate(computerdto.getIntroduced()));

		// Validate format and Intro<Discontinued using computerSetter
		computer.setDiscontinued(ComputerMapper.convertStringToDate(computerdto.getDiscontinued()));

		// get company full details from DB
		Company company=null;
		if (computerdto.getCompany() != null) {
			company = ComputerdbServices.getInstance().findCompanyById(computerdto.getCompany().getId());
		}
		if (computerdto.getCompany().getId() < 0 || company == null) {
			String msg = "InputControl : Company not found, company set to null ";
			LOGGER.debug(msg);
		} else {
			computer.setCompany(company);
		}

		return computer;
	}

}
