package com.excilys.computerdb.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ComputerdbServices;
import com.excilys.computerdb.utils.DateUtils;
/**
 * Maps a ComputerDTO into any other objects
 * @author Steven Fougeron
 *
 */
public class ComputerMapperDTO {
	private final static Logger LOGGER = LoggerFactory.getLogger(ComputerMapperDTO.class);

	/**
	 * Map a ComputerDTO to a Computer
	 * 
	 * @param dto to map
	 * @return Computer mapped, or null is something went wrong
	 */
	public static Computer toModel(ComputerDTO dto) {
		Computer computer = new Computer(dto.getId(), dto.getName());

		// Validate format and Intro<Discontinued using computerSetter
		computer.setIntroduced(DateUtils.convertStringToDate(dto.getIntroduced()));

		// Validate format and Intro<Discontinued using computerSetter
		computer.setDiscontinued(DateUtils.convertStringToDate(dto.getDiscontinued()));

		// get company full details from DB
		Company company = null;
		if (dto.getCompany() != null) {
			company = ComputerdbServices.getInstance().findCompanyById(dto.getCompany().getId());
		}
		if (dto.getCompany().getId() < 0 || company == null) {
			String msg = "InputControl : Company not found, company set to null ";
			LOGGER.debug(msg);
		} else {
			computer.setCompany(company);
		}

		return computer;
	}

	/**
	 * Map an entire list of ComputerDTO in a list a Computer using toModel
	 * 
	 * @param dtoList list to map
	 * @return a list of mapped computer
	 */
	public static List<Computer> toModelList(List<ComputerDTO> dtoList) {
		List<Computer> computerList = new ArrayList<>();
		for (ComputerDTO dto : dtoList) {
			Computer c = toModel(dto);
			if (c != null) {
				computerList.add(c);
			}
		}
		return computerList;
	}
}
