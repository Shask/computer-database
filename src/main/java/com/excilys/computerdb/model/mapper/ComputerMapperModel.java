package com.excilys.computerdb.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Computer;
/**
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerMapperModel {
	private final static Logger LOGGER = LoggerFactory.getLogger(ComputerMapperModel.class);

	/**
	 * Map a computer to a ComputerDTO throws a MappingException if computer or computer name is null
	 * @param computer to map
	 * @return a dto from the computer in params
	 */
	public static ComputerDTO toDTO(Computer computer) {
		if (computer == null) {
			LOGGER.debug("Failed to map Computer Model to DTO : computer is null");
			throw new MappingException();
		}
		ComputerDTO dto;
		if (computer.getName() != null) {
			dto = new ComputerDTO(computer.getName());
		} else {
			LOGGER.debug("Failed to map Computer Model to DTO : name is null");
			throw new MappingException();
		}
		if (computer.getId() > 0) {
			dto.setId(computer.getId());
		}
		if (computer.getIntroduced() != null) {
			dto.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() != null) {
			dto.setDiscontinued(computer.getDiscontinued().toString());
		}
		if (computer.getCompany() != null) {
			dto.setCompany(CompanyMapperModel.toDTO(computer.getCompany()));
		}

		return dto;
	}
	
	/**
	 * List an entire list of Computer model to a list of DTO using ModelToDTO
	 * @param computerList list to map
	 * @return list of mapped dto
	 */
	public static List<ComputerDTO> toDTOList(List<Computer> computerList) {
		List<ComputerDTO> dtoList = new ArrayList<>();
		for (Computer computer : computerList) {
			ComputerDTO c = toDTO(computer);
			if (c != null) {
				dtoList.add(c);
			}
		}
		return dtoList;
	}
}
