package com.excilys.computerdb.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.impl.CompanyDAOImpl;

/**
 * Class that defines a computer by an an id and a name You need at least a name
 * to create one, It contains some information about the computer like the
 * company that made it, the introduced Date and the discontinued date
 * 
 * @author excilys
 *
 */
public class Computer {

	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		if (checkDate(introduced, discontinued)) {
			this.introduced = introduced;
		} else {
			LOGGER.debug("Invalid date -> introduced date didn't change");
		}

	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDateTime discontinued) {
		if (checkDate(introduced, discontinued)) {
			this.discontinued = discontinued;
		} else {
			LOGGER.debug("Invalid date -> discontinued date didn't change");
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}

	// Constructors
	public Computer(String name, LocalDateTime introduced, LocalDateTime discontinued, Company company) {
		super();
		this.name = name;
		if (checkDate(introduced, discontinued)) {
			this.introduced = introduced;
			this.discontinued = discontinued;
		} else {
			this.introduced = null;
			this.discontinued = null;
			LOGGER.debug(
					"Introduced and/or discontinued date not valid (null ? discontinued before introduced ? ...) --> set to null");
		}

		this.company = company;
	}

	@Override
	public String toString() {
		if (introduced == null && discontinued == null && company == null)
			return "Computer [ id = " + id + ", name = " + name + " ]";
		return "Computer [ id = " + id + ", name = " + name + ", introduced = " + introduced + ", discontinued = "
				+ discontinued + ", company = " + company + " ]";

	}

	public Computer(String name) {
		super();
		this.name = name;
	}

	public Computer(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * This method allow you to know if it is possible to add a date to the
	 * computer, it is always called on every set of LocalDateTime setting
	 * 
	 * @param introduced
	 *            introduced date
	 * @param discontinued
	 *            introduced date
	 * @return true if introducted < discontinued false otherwise
	 */
	private boolean checkDate(LocalDateTime introduced, LocalDateTime discontinued) {
		if (introduced == null) {
			return false;
		} else if (discontinued == null) {
			return true;
		}
		if (introduced.isBefore(discontinued)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
