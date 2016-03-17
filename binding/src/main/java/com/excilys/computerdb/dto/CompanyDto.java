package com.excilys.computerdb.dto;

import javax.validation.constraints.NotNull ;

/**
 * Transferable object for Company.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyDto {
  long id;
  @NotNull
  String name;

  public CompanyDto(long id) {
    super();
    this.id = id;
  }

  /**
   * Constructor.
   * 
   * @param id
   *          id
   * @param name
   *          name
   */
  public CompanyDto(long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
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
    CompanyDto other = (CompanyDto) obj;
    if ( id != other.id ) {
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
