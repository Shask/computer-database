package com.excilys.computerdb.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.model.Company;
/**
 * Maps a DTO object into others types
 * @author Steven Fougeron
 *
 */
public class CompanyMapperDTO {
	private final static Logger LOGGER = LoggerFactory.getLogger(CompanyMapperDTO.class);
	
	/**
	 * Map a company dto to a Company model
	 * 
	 * @param dto to map
	 * @return a company, or null if the dto is null
	 */
	public static Company toModel(CompanyDTO dto) {
		if (dto == null) {
			LOGGER.debug("Failed to map Company DTO to Model : DTO to map is null");
			return null;
		}
		if (dto.getId() <= 0) {
			LOGGER.debug("Failed to map Company DTO to Model : DTO Id no valid");
			return null;
		}
		Company company = new Company(dto.getId(), dto.getName());
		return company;
	}
	/**
	 * Map an entire List of Company DTO using DTOToModel
	 * @param dtoList to map
	 * @return a list of mapped companies
	 */
	public static List<Company> toModelList(List<CompanyDTO> dtoList) {
		List<Company> companyList = new ArrayList<>();
		for (CompanyDTO dto : dtoList) {
			Company c = toModel(dto);
			if (c != null) {
				companyList.add(c);
			}
		}
		return companyList;
	}
}
