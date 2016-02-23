package com.excilys.computerdb.dto;

/**
 * Transferable object for Computer.
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerDto {
  int id;
  String name;
  String introduced;
  String discontinued;
  CompanyDto company;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIntroduced() {
    return introduced;
  }

  /**
   * Constructor all params.
   * 
   * @param id
   *          id
   * @param name
   *          name
   * @param introduced
   *          date as String
   * @param discontinued
   *          date as String
   * @param company
   *          companyDto
   */
  public ComputerDto(int id, String name, String introduced, String discontinued,
      CompanyDto company) {
    super();
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
  }

  /**
   * Constructor id and name.
   * 
   * @param id
   *          id
   * @param name
   *          name
   */
  public ComputerDto(int id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public ComputerDto(String name) {
    super();
    this.name = name;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public CompanyDto getCompany() {
    return company;
  }

  public void setCompany(CompanyDto computerDto) {
    this.company = computerDto;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + id;
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
    ComputerDto other = (ComputerDto) obj;
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
