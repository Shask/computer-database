package com.excilys.computerdb.models;

import org.hibernate.annotations.Type ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType ;
import javax.persistence.Id;
import javax.persistence.OneToOne ;
import java.io.Serializable ;
import java.time.LocalDate;

/**
 * Class that defines a computer by an an id and a name You need at least a name to create one, It
 * contains some information about the computer like the company that made it, the introduced Date
 * and the discontinued date.
 * 
 * @author Steven Fougeron
 *
 */
@Entity(name = "computer")
public class Computer implements Serializable{

  
  private static final long serialVersionUID = 5779547528998777494L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;


  @Column(name = "name")
  private String name;

  @Type(type = "com.excilys.computerdb.dao.mapper.LocalDateUserType")
  private LocalDate introduced;

  @Type(type = "com.excilys.computerdb.dao.mapper.LocalDateUserType")
  private LocalDate discontinued;

 @OneToOne
  private Company company;

  private static final Logger LOGGER = LoggerFactory.getLogger(Computer.class);

  // Getters and Setters
  public Computer() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  /**
   * Set an Introduced date (only if its inferior to discontinued date).
   * 
   * @param introduced
   *          date to set
   */
  public void setIntroduced(LocalDate introduced) {
      this.introduced = introduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  /**
   * Set an Discontinued date (only if its superior to introduced date).
   * 
   * @param discontinued
   *          date to set
   */
  public void setDiscontinued(LocalDate discontinued) {
      this.discontinued = discontinued;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public long getId() {
    return id;
  }

  // Constructors
  /**
   * Constructor of computer.
   * 
   * @param name
   *          of computer
   * @param introduced
   *          date
   * @param discontinued
   *          date
   * @param company
   *          company
   */
  public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
    super();
    this.name = name;
    if ( checkDate(introduced, discontinued) ) {
      this.introduced = introduced;
      this.discontinued = discontinued;
    } else {
      this.introduced = null;
      this.discontinued = null;
      LOGGER.debug("Introduced and/or discontinued date not valid (null ? discontinued befor"
          + "e introduced ? ...) --> set to null");
    }

    this.company = company;
  }

  @Override
  public String toString() {
    if ( introduced == null && discontinued == null && company == null ) {
      return "Computer [ id = " + id + ", name = " + name + " ]";
    }
    return "Computer [ id = " + id + ", name = " + name + ", introduced = " + introduced
        + ", discontinued = " + discontinued + ", company = " + company + " ]";

  }

  public Computer(String name) {
    super();
    this.name = name;
  }

  /**
   * Constructor using id and name.
   * 
   * @param id
   *          id
   * @param name
   *          name
   */
  public Computer(long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  /**
   * This method allow you to know if it is possible to add a date to the computer, it is always
   * called on every set of LocalDateTime setting.
   * 
   * @param introduced
   *          introduced date
   * @param discontinued
   *          introduced date
   * @return true if introducted inferior discontinued false otherwise
   */
  private boolean checkDate(LocalDate introduced, LocalDate discontinued) {
    if ( introduced == null ) {
      return false;
    } else if ( discontinued == null ) {
      return true;
    }
    if ( introduced.isBefore(discontinued) || introduced.isEqual(discontinued) ) {
      return true;
    } else {
      LOGGER.trace(introduced + " " + discontinued);
      LOGGER.info("discontinued date of " + this + " is not before introduced date");
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null ) {
      return false;
    }
    if ( getClass() != obj.getClass() ) {
      return false;
    }
    Computer other = (Computer) obj;
    if ( company == null ) {
      if ( other.company != null ) {
        return false;
      }
    } else if ( !company.equals(other.company) ) {
      return false;
    }
    if ( discontinued == null ) {
      if ( other.discontinued != null ) {
        return false;
      }
    } else if ( !discontinued.equals(other.discontinued) ) {
      return false;
    }
    if ( id != other.id ) {
      return false;
    }
    if ( introduced == null ) {
      if ( other.introduced != null ) {
        return false;
      }
    } else if ( !introduced.equals(other.introduced) ) {
      return false;
    }
    if ( name == null ) {
      if ( other.name != null ) {
        return false;
      }
    } else if ( !name.equals(other.name) ) {
      return false;
    }
    return true;
  }

}
