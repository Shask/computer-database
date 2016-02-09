package com.excilys.computerDB.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.excilys.computerDB.BusinessObject.Company;
import com.excilys.computerDB.BusinessObject.Computer;
import com.excilys.computerDB.JDBC.JDBCConnection;
import com.excilys.computerDB.JDBC.DAO.CompanyDAOImpl;
import com.excilys.computerDB.JDBC.DAO.ComputerDAOImpl;
@Deprecated
public class testmain {
	@Deprecated
	public static void main(String args[])
	{
		/*
		CompanyDAOImpl company = new CompanyDAOImpl();
		List<Company> listCompany = company.findAll();
		
		
		for(Company c : listCompany)
		{
			System.out.println(c);
		}
		
		List<Company> listCompany2 = company.findById(36);
		
		for(Company c : listCompany2)
		{
			System.out.println(c);
		}
		*/
			
		Computer computer1 = new Computer("Assus IUZFPOQUZFIOh",new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),new Company(1,"Apple Inc"));
		
		
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		
		compDAO.updateComputer(576,computer1);
		List<Computer> listComputer = compDAO.findAll();

		for(Computer c : listComputer)
		{
			System.out.println(c);
		}
		
		
		
	}
}
