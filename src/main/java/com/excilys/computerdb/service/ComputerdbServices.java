package com.excilys.computerdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.CompanyDAO;
import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.dao.impl.CompanyDAOImpl;
import com.excilys.computerdb.dao.impl.ComputerDAOImpl;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

public interface ComputerdbServices {

	static final Logger LOGGER = LoggerFactory.getLogger(ComputerdbServices.class);
	static final ComputerDAO computerDAO = new ComputerDAOImpl();
	static final CompanyDAO companyDAO = new CompanyDAOImpl();
	final Page page = new Page();

	public static List<Computer> findAllComputer() {
		LOGGER.trace("Finding all computer ...");
		return computerDAO.findAll(page);
	}

	public static List<Company> findAllCompany() {
		LOGGER.trace("Finding all company ...");

		return companyDAO.findAll(page);
	}

	public static List<Computer> findComputerByName(String name) {
		LOGGER.trace("Finding computer by name...");
		return computerDAO.findByName(name);
	}

	public static void deleteComputer(int id) {
		LOGGER.trace("Deleting computer ...");
		computerDAO.deleteComputer(id);
	}

	public static Computer findComputerById(int id) {
		return computerDAO.findById(id);
	}

	public static void updateComputer(Computer c) {
		computerDAO.updateComputer(c);
	}

	public static Company findCompanyById(int id) {
		return companyDAO.findById(id);
	}

	public static void insertComputer(Computer c) {
		computerDAO.insertComputer(c);
	}
	public static void incPage()
	{
		page.incPage();
	}
	public static void decPage()
	{
		page.decPage();
	}
	public static void resetPage()
	{
		page.resetPage();
	}
}
