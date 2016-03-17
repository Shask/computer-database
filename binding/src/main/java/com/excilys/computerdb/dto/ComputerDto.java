package com.excilys.computerdb.dto;

import javax.validation.constraints.NotNull ;
import javax.validation.constraints.Size ;

/**
 * Transferable object for Computer.
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerDto {
  
  long id;
  
  @Size(min = 3, max = 30)
  @NotNull
  String name;

  String introduced;

  String discontinued;

  long companyId;
  
  String companyName;

  

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
   * @param companyid
   *          companyid
   */
  public ComputerDto(long id, String name, String introduced, String discontinued, long companyId) {
    super();
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyId = companyId;

  }

  /**
   * 
   */
  public ComputerDto() {
    super();
  }

  public ComputerDto(long id, String name, String introduced, String discontinued, long companyId,
      String companyName) {
    super();
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.companyId = companyId;
    this.companyName = companyName;
  }

  /**
   * Constructor id and name.
   * 
   * @param id
   *          id
   * @param name
   *          name
   */
  public ComputerDto(long id, String name) {
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

  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (companyId ^ (companyId >>> 32));
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
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
    ComputerDto other = (ComputerDto) obj;
    if ( companyId != other.companyId ) {
      return false;
    }
    if ( companyName == null ) {
      if ( other.companyName != null ) {
        return false;
      }
    } else if ( !companyName.equals(other.companyName) ) {
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

  @Override
  public String toString() {
    return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", companyId=" + companyId + ", companyName="
        + companyName + "]";
  }

}
