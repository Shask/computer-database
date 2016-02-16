package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.CompanyDTO;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;

/**
 * Implemented interface that allow you to map options for Company ( Resultset
 * -> Model, DTO -> Model, Model -> DTo, ...)
 * 
 * @author excilys
 *
 */
public interface CompanyMapper {

	static final Logger LOGGER = LoggerFactory.getLogger(CompanyMapper.class);

	/**
	 * Method that map a ResultSet into a List of Company
	 * 
	 * @param rs : resultSet from request
	 * @return a list of Company
	 * @throws MappingException
	 */
	public static List<Company> mapList(ResultSet rs) throws MappingException {
		List<Company> listCompany = new ArrayList<>();
		if (rs == null) {
			return listCompany;
		}
		try {
			while (rs.next()) {
				listCompany.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping list of Company");
			e.printStackTrace();
			throw new MappingException();
		}
		return listCompany;
	}

	/**
	 * Method that transform a ResultSet into a since company (get you the first
	 * of the resultatSet, null otherwise)
	 * 
	 * @param rs : resultSet from request
	 * @return a company (with id and name) or null
	 * @throws MappingException
	 */
	public static Company mapOne(ResultSet rs) throws MappingException {
		try {
			if (rs != null && rs.next()) {
				return new Company(rs.getInt("id"), rs.getString("name"));
			} else
				return null;
		} catch (SQLException e) {
			LOGGER.error("Error mapping one Company");
			e.printStackTrace();
			throw new MappingException();
		} catch (NullPointerException e) {
			LOGGER.error("NullptrException while mapping one Company(ResultSetEmpty ?)");
			e.printStackTrace();
			throw new MappingException();
		}
	}

	/**
	 * Parse a company into a companyDTO
	 * 
	 * @param company  Company to parse
	 * @return CompanyDTo corresponding, null if company was null
	 */
	public static CompanyDTO ModelToDTO(Company company) {
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
	 * Map a company dto to a Company model
	 * 
	 * @param dto to map
	 * @return a company, or null if the dto is null
	 */
	public static Company DTOToModel(CompanyDTO dto) {
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
	 * @param dtoList
	 * @return
	 */
	public static List<Company> DTOToModelList(List<CompanyDTO> dtoList) {
		List<Company> companyList = new ArrayList<>();
		for (CompanyDTO dto : dtoList) {
			Company c = DTOToModel(dto);
			if (c != null) {
				companyList.add(c);
			}
		}
		return companyList;
	}
	/**
	 * Map an entire list of Computer to DTO using ModelToDTO
	 * @param companyList
	 * @return
	 */
	public static List<CompanyDTO> ModeltoDTOList(List<Company> companyList) {
		List<CompanyDTO> dtoList = new ArrayList<>();
		for (Company company : companyList) {
			CompanyDTO c = ModelToDTO(company);
			if (c != null) {
				dtoList.add(c);
			}
		}
		return dtoList;
	}

}
