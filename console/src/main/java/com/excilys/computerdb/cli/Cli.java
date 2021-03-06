package com.excilys.computerdb.cli;

import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.services.CompanyServices;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.utils.InputControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * console interface.
 * 
 * @author Steven Fougeron
 *
 */
public class Cli {

  private static final Logger LOGGER = LoggerFactory.getLogger(Cli.class);
  private static Scanner scanner = new Scanner(System.in);
  private static ComputerServices computerServices;
  private static CompanyServices companyServices;

  /**
   * main.
   * 
   * @param args
   *          args
   */
  public static void main(String[] args) {
    // Load Spring context
    ConfigurableApplicationContext ctx =
        new ClassPathXmlApplicationContext(new String[] { "console-context.xml" });
    // Manually get Service bean
    computerServices = (ComputerServices) ctx.getBean(ComputerServices.class);
    companyServices = (CompanyServices) ctx.getBean(CompanyServices.class);
    String res = "";

    while (!"quit".equals(res)) {
      displayMenu();
      res = scanner.next();
      switch ( res ) {
        case "1" :
          displayAllComputer();
          break;
        case "2" :
          displayAllCompanies();
          break;
        case "3" :
          displayComputerDetail();
          break;
        case "4" :
          createComputer();
          break;
        case "5" :
          updateComputer();
          break;
        case "6" :
          deleteComputer();
          break;
        case "7" :
          deleteCompany();
          break;
        case "quit" :
          LOGGER.trace("Exiting the application");
          return;
        default :
          LOGGER.trace("Wrong input");
      }
    }

    if ( scanner != null ) {
      scanner.close();
    }
    ctx.registerShutdownHook();
    ctx.close();
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
    System.out.println("|        7. Delete a company         |");
    System.out.println("|                       quit.To quit |");
    System.out.println("======================================");

  }

  /**
   * Display all computer (only name and id).
   */
  public static void displayAllComputer() {
    boolean display = true;
    while (display) {
      for ( Computer c : computerServices.findAllComputer() ) {
        System.out.println(c);
      }
      System.out.println(
          "Previous page : prev            quit : quit                   Next page : next");
      String res = scanner.next();
      switch ( res ) {
        case "prev" :
          computerServices.decPage();
          break;
        case "next" :
          computerServices.incPage();
          break;
        case "quit" :
          display = false;
          computerServices.resetPage();
          break;
        default :
          System.out.println("Wrong input");

      }
    }

  }

  /**
   * Display all companies with ids and names.
   */
  public static void displayAllCompanies() {
    boolean display = true;
    while (display) {
      for ( Company c : companyServices.findAllCompany() ) {
        System.out.println(c);
      }
      System.out.println(
          "Previous page : prev            quit : quit                   Next page : next");
      String res = scanner.next();
      switch ( res ) {
        case "prev" :
          computerServices.decPage();
          break;
        case "next" :
          computerServices.incPage();
          break;
        case "quit" :
          display = false;
          computerServices.resetPage();
          break;
        default :
          System.out.println("Wrong input");

      }
    }
  }

  /**
   * Allow the user to display all the detail of a computer He can look for a computer using it's
   * name, id. and have the possibility get a list of them before
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
      switch ( res ) {
        case "1" :
          System.out.print("Enter id : ");
          res = scanner.next();
          if ( InputControl.testInt(res) ) {
            displayById(Integer.parseInt(res));
          } else {
            System.out.println("Wrong input");
            LOGGER.debug("Wrong input");
          }
          break;
        case "2" :
          System.out.print("Enter name (or part of the name) : ");
          res = scanner.next();
          displayByName(res);
          break;
        case "3" :
          displayAllComputer();
          System.out.print("Enter id : ");
          res = scanner.next();
          if ( InputControl.testInt(res) ) {
            displayById(Integer.parseInt(res));
          } else {
            System.out.println("Wrong input");
            LOGGER.debug("Wrong input");
          }
          break;
        case "quit" :
          return;
        default :
          LOGGER.trace("Wrong input");
      }

    }

  }

  /**
   * display all computer with the id corresponding to the parameter "id".
   * 
   * @param id
   *          : Computer id
   */
  public static void displayById(long id) {

    Computer computer = computerServices.findComputerById(id);
    if ( computer == null ) {
      LOGGER.debug("No Computer found with this id");
    } else {
      System.out.println(computer);
    }
  }

  /**
   * Display all computer starting by the parameter name.
   * 
   * @param name
   *          string
   */
  public static void displayByName(String name) {
    boolean display = true;
    while (display) {
      List<Computer> listComputer = computerServices.findComputerByName(name);
      if ( listComputer.isEmpty() ) {
        LOGGER.info("No Computer found with this id");
      }
      for ( Computer c : listComputer ) {
        System.out.println(c);
      }
      System.out.println(
          "Previous page : prev            quit : quit                   Next page : next");
      String res = scanner.next();
      switch ( res ) {
        case "prev" :
          computerServices.decPage();
          break;
        case "next" :
          computerServices.incPage();
          break;
        case "quit" :
          display = false;
          computerServices.resetPage();
          break;
        default :
          System.out.println("Wrong input");

      }
    }
  }

  /**
   * Delete a computer in DB using id.
   */
  public static void deleteComputer() {
    System.out.print("Enter id of the computer to delete (or type list to get a list): ");

    String res = "";
    res = scanner.next();
    if ( res.equals("list") ) {
      displayAllComputer();
      System.out.print("Enter id of the computer to delete");
      res = scanner.next();
    } else if ( InputControl.testInt(res) ) {
      computerServices.deleteComputer(Integer.parseInt(res));
    }

  }

  /**
   * delete Company.
   */
  public static void deleteCompany() {
    System.out.print("Enter id of the company to delete (or type list to get a list): ");

    String res = "";
    res = scanner.next();
    if ( res.equals("list") ) {
      displayAllCompanies();
      System.out.print("Enter id of the company to delete");
      res = scanner.next();
    } else if ( InputControl.testInt(res) ) {
      companyServices.deleteCompany(Integer.parseInt(res));
    }

  }

  /**
   * Allow the user to update a computer using an id in database with the possibility to display a
   * list of computer.
   */
  public static void updateComputer() {
    System.out.print("Enter id of the computer to update (or type list to get a list): ");
    String res = "";
    res = scanner.next();
    if ( res.equals("list") ) {
      displayAllComputer();
      System.out.print("Enter id of the computer to update");
      res = scanner.next();
    }

    Computer computer = computerServices.findComputerById(Integer.parseInt(res));
    if ( computer == null ) {
      LOGGER.info("No Computer found with this id");
      return;
    }

    System.out.println("Computer to modify :");
    System.out.println(computer);

    Computer comp = computerUserInput(computer.getId());
    computerServices.updateComputer(comp);
  }

  /**
   * Allow the user to create a computer using CLI and add it in DB.
   */
  public static void createComputer() {
    Computer computer = computerUserInput(0);
    computerServices.insertComputer(computer);
    System.out.println(computer);
  }

  /**
   * Allow the user to create a computer using CLI without putting it in DB.
   * 
   * @return computer newly created
   * 
   */
  public static Computer computerUserInput(long id) {
    String res = "";
    System.out.print("Enter computer name : ");
    res = scanner.next();
    Computer computer = new Computer(id, res);
    System.out.println();
    System.out.println("Introducing date Y/N: ");
    res = scanner.next();
    if ( res.equals("Y") || res.equals("y") ) {
      System.out.println("Introducing date : ");
      computer.setIntroduced(dateUserInput());

      System.out.println("Discontinued date Y/N: ");
      res = scanner.next();
      if ( res.equals("Y") || res.equals("y") ) {
        computer.setDiscontinued(dateUserInput());
      } else {
        computer.setDiscontinued(null);
      }
    } else {
      computer.setIntroduced(null);
      computer.setDiscontinued(null);
    }

    boolean doesCompanyExist = false;
    while (!doesCompanyExist) {
      System.out.println("Enter Company id (or list if you want a list of companies) :");
      res = scanner.next();
      if ( InputControl.testInt(res) ) {
        if ( res.equals("list") ) {
          displayAllCompanies();
        } else {
          Company company = companyServices.findCompanyById(Integer.parseInt(res));
          if ( company == null ) {
            System.out.println("Company do not exist in database");
            LOGGER.info("Company do not exist in database");
          } else {
            doesCompanyExist = true;
            computer.setCompany(new Company(Integer.parseInt(res), "CompanyTest"));
          }

        }
      }
    }

    return computer;
  }

  /**
   * Allow a user to write down a date, and send it back in Timestamp format.
   * 
   * @return a date in TimeStamp format
   */
  public static LocalDate dateUserInput() {
    int year = 00;
    int month = 0;
    int day = 0;

    while (year < 1970 || year > 2050) {
      System.out.print("Enter year (after 1970): ");
      String res = scanner.next();
      if ( InputControl.testInt(res) ) {
        year = Integer.parseInt(res);
      }

    }

    while (month < 1 || month > 12) {
      System.out.print("Enter month : ");
      String res = scanner.next();
      if ( InputControl.testInt(res) ) {
        month = Integer.parseInt(res);
      }
    }
    while (day < 1 || day > 31) {
      System.out.print("Enter day : ");
      String res = scanner.next();
      if ( InputControl.testInt(res) ) {
        day = Integer.parseInt(res);
      }
    }
    LocalDate date = LocalDate.of(year, month, day);

    return date;

  }
}
