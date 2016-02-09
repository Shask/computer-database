package com.excilys.computerDB.JDBC.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerDB.BusinessObject.Company;
import com.excilys.computerDB.DAO.CompanyDAO;
import com.excilys.computerDB.JDBC.JDBCConnection;
/**
 * Implements CompanyDAO
 * This class allow you to access companies in the database use its methods
 * @author excilys
 *
 */
public class CompanyDAOImpl implements CompanyDAO {
	
	//Reference to the database connection
	private JDBCConnection DB;
	
	public CompanyDAOImpl() {
		DB = JDBCConnection.getInstance();
	}
	/**
	 * @return a limport java.sql.Statement;ist of all the company names and id from the database
	 * 
	 */
	@Override
	public List<Company> findAll() {
	 		 ResultSet rs = DB.sendRequest("SELECT * FROM company LIMIT 1000");
	 		 List<Company> companyList = new ArrayList<Company>();
	 		 try {
				while(rs.next())
				 {
					//Add a new Company in the array from the database
					companyList.add(new Company(rs.getInt("id"),rs.getString("name")));
				 }
			} catch (SQLException e) {
				System.err.println("Error executing request : findAll : CompanyDAOImpl");
				e.printStackTrace();
			}
		return companyList;
	}

	
	@Override
	public List<Company> findById(int id) {
		ResultSet rs = DB.sendRequest("SELECT * FROM company WHERE id ="+ id );
		 List<Company> companyList = new ArrayList<Company>();
		 try {
			while(rs.next())
			 {
				//Add a new Company in the array from the database
				companyList.add(new Company(rs.getInt("id"),rs.getString("name")));
			 }
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error executing request : findById : CompanyDAOImpl");
			e.printStackTrace();
		}
		 
	return companyList;
	}

	@Override
	public List<Company> findByName(String name) {
		ResultSet rs = DB.sendRequest("SELECT * FROM company WHERE name ="+ name );
		 List<Company> companyList = new ArrayList<Company>();
		 try {
			while(rs.next())
			 {
				//Add a new Company in the array from the database
				companyList.add(new Company(rs.getInt("id"),rs.getString("name")));
			 }
		} catch (SQLException e) {
			System.err.println("Error executing request : findByName: CompanyDAOImpl");
			e.printStackTrace();
		}
	return companyList;
	}

	/**
	 * @return Boolean corresponding at the success or not of the INSERT INTO request
	 * @param Company to insert uses only the name of the company(Ignore the id and let the DB apply one)
	 */
	@Override
	public boolean insertCompany(Company company) {
		PreparedStatement ps;
		try {
			ps = JDBCConnection.getConnection().prepareStatement("INSERT INTO company (name) VALUES (?)");
			ps.setString(1,company.getName());
			ps.executeUpdate();
			//ps.close();
			return true;
		} catch (SQLException e) {
			System.err.println("Error Inserting Company into DB");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateCompany(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCompany(Company company) {
		// TODO Auto-generated method stub
		return false;
	}

}
