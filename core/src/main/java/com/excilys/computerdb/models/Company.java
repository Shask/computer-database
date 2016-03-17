package com.excilys.computerdb.models;

import java.io.Serializable ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType ;
import javax.persistence.Id;

/**
 * Class that defines a company by an id and its name.
 * 
 * @author Steven Fougeron
 *
 */
@Entity(name = "company")
public class Company implements Serializable{
  
  private static final long serialVersionUID = -7107213213665131762L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  public Company(long id, String name) {
    this.name = name;
    this.id = id;
  }

  public Company() {

  }

  // Getters and Setters
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
    Company other = (Company) obj;
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

  @Override
  public String toString() {
    return "Company [id=" + id + ", name=" + name + "]";
  }

}
