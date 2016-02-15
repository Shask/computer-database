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

public class ComputerdbServices {

	
	private static ComputerdbServices instance = new ComputerdbServices();
	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	private  CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	private final Page page = new Page();
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerdbServices.class);	

	private ComputerdbServices()
	{
		
	}
	public static ComputerdbServices getInstance()
	{
		return instance;
	}
	
	public List<Computer> findAllComputer() {
		LOGGER.trace("Finding all computer ...");
		return computerDAO.findAll(page);
	}

	public  List<Company> findAllCompany() {
		LOGGER.trace("Finding all company ...");

		return companyDAO.findAll(page);
	}

	public  List<Computer> findComputerByName(String name) {
		LOGGER.trace("Finding computer by name...");
		return computerDAO.findByName(name);
	}

	public  void deleteComputer(int id) {
		LOGGER.trace("Deleting computer ...");
		computerDAO.deleteComputer(id);
	}
	public int getCountComputer()
	{
		LOGGER.trace("Counting number of computer in db");
		return computerDAO.countComputer();
	}

	public  Computer findComputerById(int id) {
		return computerDAO.findById(id);
	}

	public void updateComputer(Computer c) {
		computerDAO.updateComputer(c);
	}

	public Company findCompanyById(int id) {
		return companyDAO.findById(id);
	}

	public void insertComputer(Computer c) {
		computerDAO.insertComputer(c);
	}
	public void incPage()
	{
		page.incPage();
	}
	public void decPage()
	{
		page.decPage();
	}
	public void resetPage()
	{
		page.resetPage();
	}
	public Page getPage()
	{
		return page;
	}
}
