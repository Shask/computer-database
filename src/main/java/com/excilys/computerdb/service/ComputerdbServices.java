package com.excilys.computerdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.CompanyDAO;
import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.dao.exception.FailedRequestException;
import com.excilys.computerdb.dao.impl.CompanyDAOImpl;
import com.excilys.computerdb.dao.impl.ComputerDAOImpl;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.validation.ValidatorModel;
import com.excilys.computerdb.utils.exception.ValidationException;

/**
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerdbServices {

	private static ComputerdbServices instance = new ComputerdbServices();
	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	private final Page page = new Page();
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerdbServices.class);

	private ComputerdbServices() {

	}

	public static ComputerdbServices getInstance() {
		return instance;
	}

	public List<Computer> findAllComputer() {
		LOGGER.trace("Finding all computer ...");
		List<Computer> listComp = new ArrayList<>();
		try {
			listComp = computerDAO.findAll(page);
			for (Computer c : listComp) {
				ValidatorModel.validate(c);
			}
		} catch (CriticalDatabaseException e) {
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e) {
			LOGGER.error(e.getMessage());
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
		}
		return listComp;
	}

	public List<Company> findAllCompany() {
		LOGGER.trace("Finding all company ...");
		List<Company> listComp = new ArrayList<>();
		try {
			listComp = companyDAO.findAll(page);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		}
		return listComp;
	}

	public List<Computer> findComputerByName(String name) {
		LOGGER.trace("Finding computer by name...");
		List<Computer> listComp = new ArrayList<>();
		try {
			listComp = computerDAO.findByName(name);
			for (Computer c : listComp) {
				ValidatorModel.validate(c);
			}
		} catch (CriticalDatabaseException e) {
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e) {
			LOGGER.error(e.getMessage());
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
		}
		return listComp;
	}

	public void deleteComputer(int id) {
		LOGGER.trace("Deleting computer ...");
		try {
			computerDAO.deleteComputer(id);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		}
	}
	public void deleteComputer(List<Integer> ids) {
	
		for(Integer id : ids)
		{
			deleteComputer(id);
		}
	}

	public int getCountComputer() {
		LOGGER.trace("Counting number of computer in db");
		int count = -1;
		try {
			count = computerDAO.countComputer();
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		}
		return count;
	}

	public Computer findComputerById(int id) {
		Computer computer =null;
		try {
			computer = computerDAO.findById(id);
			ValidatorModel.validate(computer);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		} catch (ValidationException e) {
			LOGGER.error(e.getMessage());
		}
		return computer;
	}

	public void updateComputer(Computer c) {
		try {
			computerDAO.updateComputer(c);
			ValidatorModel.validate(c);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}

	public Company findCompanyById(int id) {
		Company company =null;
		try {
			company = companyDAO.findById(id);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		}
		return company;
	}

	public void insertComputer(Computer c) {
		try {
			computerDAO.insertComputer(c);
			ValidatorModel.validate(c);
		} catch (CriticalDatabaseException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} catch (FailedRequestException e1) {
			LOGGER.error(e1.getMessage());
		}catch (ValidationException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
	}

	public void incPage() {
		page.incPage();
	}

	public void decPage() {
		page.decPage();
	}

	public void resetPage() {
		page.resetPage();
	}

	public Page getPage() {
		return page;
	}
}
