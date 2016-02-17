package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dto.ComputerDTO;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.utils.InputControl;
import com.excilys.computerdb.utils.exception.ValidationException;

/**
 * Implemented interface that allow you to map options for Computer ( Resultset
 * -> Model, DTO -> Model, Model -> DTo, ...)
 * 
 * @author excilys
 *
 */
public interface ComputerMapper {
	static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);

	/**
	 * Method that map a ResultSet into a List of Computer
	 * 
	 * @param rs : resultSet from request
	 * @return a list of Computer
	 * @throws MappingException
	 */
	public static List<Computer> mapList(ResultSet rs) throws MappingException {
		List<Computer> listComputer = new ArrayList<>();
		if (rs == null) {
			return listComputer;
		}
		try {
			while (rs.next()) {
				Computer c = new Computer(rs.getInt("id"), rs.getString("name"));
				if (rs.getTimestamp("introduced") != null) {
					c.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime());
				}
				if (rs.getTimestamp("discontinued") != null) {
					c.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime());
				}
				if (rs.getString("cname") != null) {
					c.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
				}
				listComputer.add(c);
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping list of Computer");
			e.printStackTrace();
			throw new MappingException();
		}
		return listComputer;
	}

	/**
	 * Method that transform a ResultSet into a since Computer (get you the
	 * first of the resultatSet, null otherwise)
	 * 
	 * @param rs : resultSet from request
	 * @return a computer (with id and name) or null
	 * @throws MappingException
	 */
	public static Computer mapOne(ResultSet rs) throws MappingException {
		try {
			if (rs != null && rs.next()) { // if there is a computer returning
											// from database, map it

				Computer c = new Computer(rs.getInt("id"), rs.getString("name"));
				if (rs.getTimestamp("introduced") != null) {
					c.setIntroduced(rs.getTimestamp("introduced").toLocalDateTime());
				}
				if (rs.getTimestamp("discontinued") != null) {
					c.setDiscontinued(rs.getTimestamp("discontinued").toLocalDateTime());
				}
				if (rs.getString("cname") != null) {
					c.setCompany(new Company(rs.getInt("cid"), rs.getString("cname")));
				}
				return c;
			} else { // if there is no computer returning from the request, send
						// null
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error("Error mapping one Company");
			e.printStackTrace();
			throw new MappingException();
		} catch (NullPointerException e) {
			LOGGER.error("NullptrException while mapping one Computer(ResultSetEmpty ?)");
			e.printStackTrace();
			throw new MappingException();
		}
	}
	/**
	 * Map a computer to a ComputerDTO throws a MappingException if computer or computer name is null
	 * @param computer
	 * @return
	 */
	public static ComputerDTO ModelToDTO(Computer computer) {
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
			dto.setCompany(CompanyMapper.ModelToDTO(computer.getCompany()));
		}

		return dto;
	}
	/**
	 * Map a ComputerDTO to a Computer
	 * @param dto
	 * @return Computer mapped, or null is something went wrong
	 */
	public static Computer DTOToModel(ComputerDTO dto) {
		try {
			return InputControl.validation(dto);
		} catch (ValidationException e) {
			LOGGER.debug("Failed to map Computer DTO to model : Validation didn't pass");
			e.printStackTrace();
			throw new MappingException();
		}
	}
	/**
	 * Map an entire list of ComputerDTO in a list a Computer using DTOToModel
	 * @param dtoList
	 * @return
	 */
	public static List<Computer> DTOToModelList(List<ComputerDTO> dtoList) {
		List<Computer> computerList = new ArrayList<>();
		for (ComputerDTO dto : dtoList) {
			Computer c = DTOToModel(dto);
			if (c != null) {
				computerList.add(c);
			}
		}
		return computerList;
	}
	/**
	 * List an entire list of Computer model to a list of DTO using ModelToDTO
	 * @param computerList
	 * @return
	 */
	public static List<ComputerDTO> ModeltoDTOList(List<Computer> computerList) {
		List<ComputerDTO> dtoList = new ArrayList<>();
		for (Computer computer : computerList) {
			ComputerDTO c = ModelToDTO(computer);
			if (c != null) {
				dtoList.add(c);
			}
		}
		return dtoList;
	}

	/**
	 * Convert a String to a LocalDateTime is the String is correctly formated,
	 * return null is not
	 * 
	 * @param s String to change to a date
	 * @return a LocalDateTime
	 */
	static LocalDateTime convertStringToDate(String s) {

		String year = "";
		String month = "";
		String day = "";
		LocalDateTime returnDate = LocalDateTime.now();
		LOGGER.trace("parsing string to date format");
		if (s == null || "anObject".equals(s)) {
			LOGGER.debug("Parsing failed : received : " + s);
			return null;
		}
		try {
			year = s.substring(0, 4);
			month = s.substring(5, 7);
			day = s.substring(8, 10);
			String formated = year + "-" + month + "-" + day + " 00:00";

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			returnDate = LocalDateTime.parse(formated, formatter);
		} catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
			// e.printStackTrace();
			LOGGER.debug("Parsing failed : received : " + s + "parsed to : " + year + "-" + month + "-" + day);
			return null;
		}
		return returnDate;
	}
}
