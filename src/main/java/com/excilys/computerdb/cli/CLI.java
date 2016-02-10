package com.excilys.computerdb.cli;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.excilys.computerdb.dao.impl.CompanyDAOImpl;
import com.excilys.computerdb.dao.impl.ComputerDAOImpl;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

public class CLI {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String args[]) {

		String res = "";

		while (!"quit".equals(res)) {
			displayMenu();
			res = scanner.next();
			switch (res) {
			case "1":
				displayAllComputer();
				break;
			case "2":
				displayAllCompanies();
				break;
			case "3":
				displayComputerDetail();
				break;
			case "4":
				createComputer();
				break;
			case "5":
				updateComputer();
				break;
			case "6":
				deleteComputer();
				break;
			case "quit":
				System.out.println("Bye ...");
				return;
			default:
				System.out.println("Wrong input");
			}
		}
		if (scanner != null) {
			scanner.close();
		}
	}

	/**
	 * Display a simple menu on System.out
	 */
	public static void displayMenu() {
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
	public static void displayAllComputer() {
		ComputerDAOImpl compDAO = new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findAllShort();
		for (Computer c : listComputer) {
			System.out.println(c);
		}

	}

	/**
	 * Display all companies with ids and names
	 */
	public static void displayAllCompanies() {
		CompanyDAOImpl compDAO = new CompanyDAOImpl();
		List<Company> listCompany = compDAO.findAll();
		for (Company c : listCompany) {
			System.out.println(c);
		}

	}

	/**
	 * Allow the user to display all the detail of a computer He can look for a
	 * computer using it's name, id. and have the possibility get a list of them
	 * before
	 */
	public static void displayComputerDetail() {
		while (true) {
			System.out.println("======================================");
			System.out.println("|     COMPUTER DETAIL OPTIONS        |");
			System.out.println("======================================");
			System.out.println("| Options:                           |");
			System.out.println("|   1. Display using computer id     |");
			System.out.println("|   2. Display using computer name   |");
			System.out.println("|   3. List all computer and chose   |");
			System.out.println("|                      quit.To quit  |");
			System.out.println("======================================");

			String res = scanner.next();
			switch(res)
			{
			case "1":
				System.out.print("Enter id : ");
				res = scanner.next();
				displayById(Integer.parseInt(res));
				break;
			case "2":
				System.out.print("Enter name (or part of the name) : ");
				res = scanner.next();
				displayByName(res);
				break;
			case "3":
				displayAllComputer();
				System.out.print("Enter id : ");
				res = scanner.next();
				displayById(Integer.parseInt(res));
				break;
			case "quit":
				return;
				default:
					System.out.println("Wrong input");
			}
			
		}

	}

	/**
	 * display all computer with the id corresponding to the parameter "id"
	 * 
	 * @param id
	 *            : Computer id
	 */
	public static void displayById(int id) {
		ComputerDAOImpl compDAO = new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findById(id);
		if (listComputer.isEmpty())
			System.out.println("No Computer found with this id");
		for (Computer c : listComputer) {
			System.out.println(c);
		}
	}

	/**
	 * Display all computer starting by the parameter name
	 * 
	 * @param name
	 *            string
	 */
	public static void displayByName(String name) {
		ComputerDAOImpl compDAO = new ComputerDAOImpl();
		List<Computer> listComputer = compDAO.findByName(name);
		if (listComputer.isEmpty())
			System.out.println("No Computer found with this id");
		for (Computer c : listComputer) {
			System.out.println(c);
		}
	}

	/**
	 * Delete a computer in DB using id
	 */
	public static void deleteComputer() {
		System.out.print("Enter id of the computer to delete (or type list to get a list): ");

		String res = "";
		res = scanner.next();
		if (res.equals("list")) {
			displayAllComputer();
			System.out.print("Enter id of the computer to delete");
			res = scanner.next();
		} else if (Integer.parseInt(res) > 0 && Integer.parseInt(res) < 99999) {
			ComputerDAOImpl compDAO = new ComputerDAOImpl();
			if (compDAO.deleteComputer(Integer.parseInt(res)))
				System.out.println("Delete successfull");
			else
				System.out.println("Delete failed");

		}

	}

	/**
	 * Allow the user to update a computer using an id in database with the
	 * possibility to display a list of computer
	 */
	public static void updateComputer() {
		ComputerDAOImpl compDAO = new ComputerDAOImpl();
		System.out.print("Enter id of the computer to update (or type list to get a list): ");
		String res = "";
		res = scanner.next();
		if (res.equals("list")) {
			displayAllComputer();
			System.out.print("Enter id of the computer to update");
			res = scanner.next();
		}

		List<Computer> listComputer = compDAO.findById(Integer.parseInt(res));
		if (listComputer.isEmpty()) {
			System.out.println("No Computer found with this id");

			return;
		}
		for (Computer c : listComputer) {
			System.out.println("Computer to modify :");
			System.out.println(c);
		}
		Computer c = computerUserInput();
		if (compDAO.updateComputer(Integer.parseInt(res), c)) {
			System.out.println("Update successful");
		} else {
			System.out.println("Update failed");
		}

	}

	/**
	 * Allow the user to create a computer using CLI and add it in DB
	 */
	public static void createComputer() {
		ComputerDAOImpl compDAO = new ComputerDAOImpl();
		Computer c = computerUserInput();
		if (compDAO.insertComputer(c))
			System.out.println("Insert successful");
		else
			System.out.println("Insert Failed");
	}

	/**
	 * Allow the user to create a computer using CLI without putting it in DB
	 * 
	 * @return computer newly created
	 * 
	 */
	public static Computer computerUserInput() {
		String res = "";
		System.out.print("Enter computer name : ");
		res = scanner.next();
		Computer computer = new Computer(res);
		System.out.println();
		System.out.println("Discontinued date Y/N: ");
		res = scanner.next();
		if (res.equals("Y")) {
			System.out.println("Introducing date : ");
			computer.setIntroduced(dateUserInput());

			System.out.println("Discontinued date Y/N: ");
			res = scanner.next();
			if (res.equals("Y"))
				computer.setDiscontinued(dateUserInput());
			else
				computer.setDiscontinued(null);
		} else {
			computer.setIntroduced(null);
			computer.setDiscontinued(null);
		}

		boolean doesCompanyExist = false;
		while (!doesCompanyExist) {
			System.out.println("Enter Company id (or list if you want a list of companies) :");
			res = scanner.next();
			if (res.equals("list")) {
				displayAllCompanies();
			} else {
				CompanyDAOImpl company = new CompanyDAOImpl();
				List<Company> listCompany = company.findById(Integer.parseInt(res));
				if (listCompany.isEmpty())
					doesCompanyExist = false;
				else {
					doesCompanyExist = true;
					computer.setCompany(new Company(Integer.parseInt(res), "CompanyTest"));
				}

			}
		}

		return computer;
	}

	/**
	 * Allow a user to write down a date, and send it back in Timestamp format
	 * 
	 * @return a date in TimeStamp format
	 */
	public static Timestamp dateUserInput() {
		int year = 00;
		int month = 0;
		int day = 0;

		while (year < 1970 || year > 2050) {
			System.out.print("Enter year (after 1970): ");
			year = Integer.parseInt(scanner.nextLine());
		}

		while (month < 1 || month > 12) {
			System.out.print("Enter month : ");
			month = Integer.parseInt(scanner.nextLine());
		}
		while (day < 1 || day > 31) {
			System.out.print("Enter day : ");
			day = Integer.parseInt(scanner.nextLine());
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			String date = year + "-" + month + "-" + day + " 12:00:00.000";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date parsedDate = dateFormat.parse(date);
			ts = new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Error parsing date");
			e.printStackTrace();
		}

		return ts;

	}
}
