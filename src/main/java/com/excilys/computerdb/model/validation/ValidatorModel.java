package com.excilys.computerdb.model.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.utils.exception.ValidationException;

public class ValidatorModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorModel.class);

	public static boolean validate(Computer computer) throws ValidationException {
		if (computer == null) {
			LOGGER.debug("Validation failed : Computer null");
			throw new ValidationException();
		}
		String computerName = computer.getName();
		if (computerName == null || "".equals(computerName) || computerName.contains("%") || computerName.contains("\"") || computerName.contains("\\") || computerName.contains("<") || computerName.contains(">")) {
			LOGGER.debug("Validation failed : Name not valid (null | too short or illegal char) : index ="+computer.getId());
			throw new ValidationException();
		}

		return true;
	}

}
