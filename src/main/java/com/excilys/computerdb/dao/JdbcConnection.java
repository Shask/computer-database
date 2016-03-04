package com.excilys.computerdb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * This class is used to access the connection to the database. This is a singleton type. You can
 * get a reference of the connection by using the getInstance() method.
 * 
 * @author Steven Fougeron
 *
 */
@Component
public class JdbcConnection {

  @Autowired
  private DataSource dataSource;

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

}
