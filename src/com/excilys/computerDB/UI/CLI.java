package com.excilys.computerDB.UI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import com.excilys.computerDB.BusinessObject.Company;
import com.excilys.computerDB.BusinessObject.Computer;
import com.excilys.computerDB.JDBC.DAO.CompanyDAOImpl;
import com.excilys.computerDB.JDBC.DAO.ComputerDAOImpl;

public class CLI {

	public static void main(String args[])
	{
		Scanner scanner = new Scanner(System.in);
		String res = "";
		
		while(!res.equals("quit"))
		{
				displayMenu();
				res= scanner.next();
				if(res.trim().equals("1"))
				{
					displayAllComputer();
				} else if(res.trim().equals("2"))
				{
					displayAllCompanies();
				} else if (res.trim().equals("3"))
				{
					displayComputerDetail();
				}
				else if(res.trim().equals("4"))
				{
					createComputer();
				}else if(res.trim().equals("5"))
				{
					updateComputer();
				}
				else if(res.trim().equals("6"))
				{
					deleteComputer();
				}
				else if(res.trim().equals("quit"))
				{
					res="quit";
				}else 
				{
					System.out.println("Wrong input");
				}
				
		}
	}
	/**
	 * Display a simple menu
	 */
	public static void displayMenu()
	{
		System.out.println("======================================");
	    System.out.println("|        COMPUTER DATABASE           |");
	    System.out.println("======================================");
	    System.out.println("| Options:                           |");
	    System.out.println("|        1. List all computer        |");
	    System.out.println("|        2. List all companies       |");
	    System.out.println("|        3. Get Computer details     |");
	    System.out.println("|        4. Create a computer        |");
	    System.out.println("|        5. Update a computer        |");
	    System.out.println("|        6. Delete a computer        |");
	    System.out.println("|                       quit.To quit |");
	    System.out.println("======================================");
	   
	    
	}
	/**
	 * Display all computer (only name and id)
	 */
	public static void displayAllComputer()
	{
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findAllShort();
		for(Computer c : listComputer)
		{
			System.out.println(c);
		}

	}
	/**
	 * Display all companies with ids and names
	 */
	public static void displayAllCompanies()
	{
		CompanyDAOImpl compDAO= new CompanyDAOImpl();
		List<Company> listCompany = compDAO.findAll();
		for(Company c : listCompany)
		{
			System.out.println(c);
		}

	}
	/**
	 * Allow the user to display all the detail of a computer 
	 * He can look for a computer using it's name, id.
	 * and have the possibility get a list of them before
	 */
	public static void displayComputerDetail()
	{
			
		    Scanner scanner = new Scanner(System.in);
			String res = "";
			
			while(!res.equals("quit"))
			{
				System.out.println("======================================");
				System.out.println("|     COMPUTER DETAIL OPTIONS        |");
				System.out.println("======================================");
			 	System.out.println("| Options:                           |");
			    System.out.println("|   1. Display using computer id     |");
			    System.out.println("|   2. Display using computer name   |");
			    System.out.println("|   3. List all computer and chose   |");
			    System.out.println("|   quit.To quit                     |");
			    System.out.println("======================================");
			
				res= scanner.next();
				if(res.equals("1"))
				{
					 System.out.print("Enter id : ");
					 res= scanner.next();
					 displayById(Integer.parseInt(res));
					 
				} else if(res.equals("2"))
				{
					System.out.print("Enter name (or part of the name) : ");
					 res= scanner.next();
					 displayByName(res);
				} else if (res.equals("3"))
				{
					displayAllComputer();
					System.out.print("Enter id : ");
					res= scanner.next();
					displayById(Integer.parseInt(res));
				}
			}
	}
	/**
	 * display all computer with the id corresponding to the parameter "id"
	 * @param id
	 */
	public static void displayById(int id)
	{
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findById(id);
		if(listComputer.isEmpty())System.out.println("No Computer found with this id");
		for(Computer c : listComputer)
		{
			System.out.println(c);
		}
	}
	/**
	 * Display all computer starting by the parameter name
	 * @param name string 
	 */
	public static void displayByName(String name)
	{
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findByName(name);
		if(listComputer.isEmpty())System.out.println("No Computer found with this id");
		for(Computer c : listComputer)
		{
			System.out.println(c);
		}
	}
	/**
	 * Delete a computer in DB using id
	 */
	public static void deleteComputer()
	{
		 System.out.print("Enter id of the computer to delete (or type list to get a list): ");
		 Scanner scanner = new Scanner(System.in);
		 String res = "";
		 res= scanner.next();
		 if(res.equals("list"))
		 {
			 displayAllComputer();
			 System.out.print("Enter id of the computer to delete");
			 res= scanner.next();
		 }
		 else if(Integer.parseInt(res)> 0 && Integer.parseInt(res)< 99999)
		 {
			 ComputerDAOImpl compDAO= new ComputerDAOImpl();
			 if(compDAO.deleteComputer(Integer.parseInt(res)))
				 System.out.println("Delete successfull");
			 else
				 System.out.println("Delete failed");
			
		 }
		 
		 
	}
	
	/**
	 * Allow the user to update a computer using an id in database
	 * with the possibility to display a list of computer
	 */
	public static void updateComputer()
	{
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		 System.out.print("Enter id of the computer to update (or type list to get a list): ");
		 Scanner scanner = new Scanner(System.in);
		 String res = "";
		 res= scanner.next();
		 if(res.equals("list"))
		 {
			 displayAllComputer();
			 System.out.print("Enter id of the computer to update");
			 res= scanner.next();
		 }
 	
		List<Computer> listComputer = compDAO.findById(Integer.parseInt(res));
		if(listComputer.isEmpty())
			{	
				System.out.println("No Computer found with this id");
				return;
			}
		for(Computer c : listComputer)
		{
			System.out.println("Computer to modify :");
			System.out.println(c);
		}
		Computer c = computerUserInput();
		if(compDAO.updateComputer(Integer.parseInt(res),c))
		{
			System.out.println("Update successful");
		}
		else {
			System.out.println("Update failed");
		}	 
	}
	/**
	 * Allow the user to create a computer using CLI and add it in DB
	 */
	public static void createComputer()
	{
		ComputerDAOImpl compDAO= new ComputerDAOImpl();
		Computer c = computerUserInput();
		if(compDAO.insertComputer(c))
			System.out.println("Insert successful");
		else
			System.out.println("Insert Failed");
	}
	
	/**
	 * Allow the user to create a computer using CLI without puting it in DB
	 * @return computer newly created 
	 * 
	 */
	public static Computer computerUserInput()
	{
		Scanner scanner = new Scanner(System.in);
		String res = "";
		System.out.print("Enter computer name : ");
		res= scanner.next();
		Computer computer = new Computer(res);
		System.out.println();
		System.out.println("Introducing date : ");
		computer.setIntroduced(dateUserInput());
		System.out.println("Discontinued date Y/N: ");
		res= scanner.next();
		if(res.equals("Y"))
			computer.setDiscontinued(dateUserInput());
		else 
			computer.setDiscontinued(null);
	
		boolean doesCompanyExist = false;
		while(!doesCompanyExist)
		{
			System.out.println("Enter Company id (or list if you want a list of companies) :");
			res= scanner.next();
			if(res.equals("list"))
			{
				displayAllCompanies();
				System.out.println("Enter Company id :");
				res= scanner.next();
			}
			else 
			{
				CompanyDAOImpl company = new CompanyDAOImpl();
				List<Company> listCompany = company.findById(Integer.parseInt(res));
				if(listCompany.isEmpty()) 
					doesCompanyExist=false;
				else 
				{
					doesCompanyExist=true;
					computer.setCompany(new Company(Integer.parseInt(res),"CompanyTest"));
				}
					
			}
		}
		
		return computer;
	}
	/**
	 * Allow a user to write down a date, and send it back in Timestamp format
	 * @return a date in TimeStamp format
	 */
	public static Timestamp dateUserInput()
	{
		Scanner scanner = new Scanner(System.in);
		int year = 0;
		int month = 0;
		int day=0;
	
		
		String res = "";
		while(year<1970 || month>2015)
		{
			System.out.print("Enter year (after 1970): ");
			year = Integer.parseInt(scanner.nextLine());
		}
		
		
		while(month<1 || month>12)
		{
			System.out.print("Enter month : ");
			month = Integer.parseInt(scanner.nextLine());
		}
		while(day<1 || day>31)
		{
			System.out.print("Enter day : ");
			day = Integer.parseInt(scanner.nextLine());
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try{
			String date =year+"-"+month+"-"+day+" 12:00:00.000";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date parsedDate = dateFormat.parse(date);
			ts=new Timestamp(parsedDate.getTime());
		}
		 catch (ParseException e) {
				// TODO Auto-generated catch block
			 System.err.println("Error parsing date");
				e.printStackTrace();
			}
		 return ts;
		
	}
}
