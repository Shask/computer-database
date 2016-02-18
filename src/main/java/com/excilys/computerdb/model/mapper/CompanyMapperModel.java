package com.excilys.computerdb.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;
/**
 * Class that allow you to convert a Company Model into any type of Company 
 * @author Steven Fougeron
 *
 */
public class CompanyMapperModel {
	private final static Logger LOGGER = LoggerFactory.getLogger(CompanyMapperModel.class);
	/**
	 * Parse a company into a companyDTO
	 * 
	 * @param company  Company to parse
	 * @return CompanyDTo corresponding, null if company was null
	 */
	public static CompanyDTO toDTO(Company company) {
		if (company == null) {
			LOGGER.debug("Failed to map Company Model to DTO : company to map is null");
			return null;
		}
		CompanyDTO dto;
		if (company.getId() > 0) {
			dto = new CompanyDTO(company.getId());
		} else {
			LOGGER.debug("Failed to map Company Model to DTO : name is null");
			throw new MappingException();
		}
		dto.setName(company.getName());
		return dto;
	}

	
	/**
	 * Map an entire list of Computer to DTO using ModelToDTO
	 * @param companyList to map
	 * @return a list of mapped dto
	 */
	public static List<CompanyDTO> toDTOList(List<Company> companyList) {
		List<CompanyDTO> dtoList = new ArrayList<>();
		for (Company company : companyList) {
			CompanyDTO c = toDTO(company);
			if (c != null) {
				dtoList.add(c);
			}
		}
		return dtoList;
	}
}
