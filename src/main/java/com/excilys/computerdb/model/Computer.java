package com.excilys.computerdb.model;

import java.sql.Timestamp;

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
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public void setIntroduced(long introduced) {
		this.introduced = new Timestamp(introduced);
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public void setDiscontinued(long discontinued) {
		this.introduced = new Timestamp(discontinued);
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
	public Computer(String name, Timestamp introduced, Timestamp discontinued, Company company) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	@Override
	public String toString() {
		if (introduced == null && discontinued == null && company == null)
			return "Computer [ id = " + id + ", name = " + name + " ]";
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";

	}

	public Computer(Timestamp introduced, Timestamp discontinued, Company company) {
		super();
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Computer(String name) {
		this.name = name;
	}

	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	// public bool checkDates(Date )

}
