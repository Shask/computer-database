package com.excilys.computerDB.JDBC.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerDB.BusinessObject.Company;
import com.excilys.computerDB.BusinessObject.Computer;
import com.excilys.computerDB.DAO.ComputerDAO;
import com.excilys.computerDB.JDBC.JDBCConnection;

public class ComputerDAOImpl implements ComputerDAO{

	//Reference to the database connection
		private JDBCConnection DB;
		
		public ComputerDAOImpl() {
			DB = JDBCConnection.getInstance();
		}
	/**
	* @return a list of all the computer names and id from the database
	*/
	@Override
	public List<Computer> findAllShort() {
		ResultSet rs = DB.sendRequest("SELECT id,name FROM computer LIMIT 1000");
		 List<Computer> computerList = new ArrayList<Computer>();
		 try {
			while(rs.next())
			 {
				//Add a new Computer in the array from the database
				Computer c = new Computer(rs.getInt("id"),rs.getString("name"));
				
				computerList.add(c);
			 }
		} catch (SQLException e) {
			System.err.println("Error executing request : findAllShort : ComputerDAOImpl");
			e.printStackTrace();
		}
	return computerList;
	}
	
	@Override
	public List<Computer> findAll() 
	{
		ResultSet rs = DB.sendRequest("SELECT comput.id, comput.name, comput.introduced,comput.discontinued, c.id  AS cid, c.name AS cname FROM computer comput INNER join company c on comput.company_id=c.id ");
		 List<Computer> computerList = new ArrayList<Computer>();
		 try {
			while(rs.next())
			 {
				//Add a new Computer in the array from the database
				Computer c = new Computer(rs.getInt("id"),rs.getString("name"));
				
				c.setIntroduced(rs.getTimestamp("introduced"));
				c.setDiscontinued(rs.getTimestamp("discontinued"));
				c.setCompany(new Company(rs.getInt("cid"),rs.getString("cname")));
				computerList.add(c);
			 }
		} catch (SQLException e) {
			System.err.println("Error executing request : findAll : ComputerDAOImpl");
			e.printStackTrace();
		}
	return computerList;
		
	}

	@Override
	public List<Computer> findById(int id) {
		ResultSet rs = DB.sendRequest("SELECT comput.id, comput.name, comput.introduced,comput.discontinued, c.id  AS cid, c.name AS cname FROM computer comput LEFT join company c on comput.company_id=c.id WHERE comput.id ="+ id );
		 List<Computer> computerList = new ArrayList<Computer>();
		 try {
			while(rs.next())
			 {
				Computer c = new Computer(rs.getInt("id"),rs.getString("name"));
				c.setIntroduced(rs.getTimestamp("introduced"));
				c.setDiscontinued(rs.getTimestamp("discontinued"));
				c.setCompany(new Company(rs.getInt("cid"),rs.getString("cname")));
				computerList.add(c);
			 }
		} catch (SQLException e) {
			System.err.println("Error executing request : findById : ComputerDAOImpl");
			e.printStackTrace();
		}
	return computerList;
	}

	@Override
	public List<Computer> findByName(String name) {
		ResultSet rs = DB.sendRequest("SELECT comput.id, comput.name, comput.introduced,comput.discontinued, c.id AS cid, c.name AS cname FROM computer comput "
										+"left join company c on comput.company_id=c.id "
										+"WHERE comput.name LIKE '"+name+"%'" );
		 List<Computer> computerList = new ArrayList<Computer>();
		 try {
			while(rs.next())
			 {
				Computer c = new Computer(rs.getString("name"));
				c.setIntroduced(rs.getTimestamp("introduced"));
				c.setDiscontinued(rs.getTimestamp("discontinued"));
				c.setCompany(new Company(rs.getInt("cid"),rs.getString("cname")));
				computerList.add(c);
			 }
		} catch (SQLException e) {
			System.err.println("Error executing request : findById : ComputerDAOImpl");
			e.printStackTrace();
		}
	return computerList;
	}

	@Override
	public boolean insertComputer(Computer computer) {
		PreparedStatement ps;
		try {
			ps = JDBCConnection.getConnection().prepareStatement("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)");
			ps.setString(1,computer.getName());
			ps.setTimestamp(2,computer.getIntroduced());
			ps.setTimestamp(3,computer.getDiscontinued());
			ps.setInt(4, computer.getCompany().getId());
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			System.err.println("Error Inserting Company into DB");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateComputer(int id,Computer computer) {
		String request = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id= ? WHERE id=?";
		PreparedStatement ps;
		try {
			ps = JDBCConnection.getConnection().prepareStatement(request);
			ps.setString(1,computer.getName());
			ps.setTimestamp(2,computer.getIntroduced());
			ps.setTimestamp(3,computer.getDiscontinued());
			ps.setInt(4, computer.getCompany().getId());
			ps.setInt(5,id);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			System.err.println("Error Updating Company into DB");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteComputer(int id) {
		String request = "DELETE FROM computer WHERE id = ?";
		PreparedStatement ps;
		try
		{
			ps = JDBCConnection.getConnection().prepareStatement(request);
			ps.setInt(1, id);
			ps.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			System.err.println("Error Deleting Company into DB");
			e.printStackTrace();
		}
		return false;
	}

}
