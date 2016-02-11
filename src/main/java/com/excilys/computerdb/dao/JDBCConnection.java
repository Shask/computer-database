package com.excilys.computerdb.dao;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used to access the connection to the database. This is a
 * singleton type. You can get a reference of the connection by using the
 * getInstance() methode
 * 
 * @author excilys
 *
 */
public class JDBCConnection {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCConnection.class);
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";

	// Database credentials
	private static final String LOGIN = "admincdb";
	private static final String PASS = "qwerty1234";

	
	private static JDBCConnection instance = new JDBCConnection();

	public static JDBCConnection getInstance() {
		return instance;
	}

	public static Connection getConnection() {
		try {
			LOGGER.trace("Connecting to database...");
			return DriverManager.getConnection(DB_URL, LOGIN, PASS);
		} catch (SQLException e) {
			LOGGER.error("Error Connecting to the database");
			e.printStackTrace();
		}
		return null;
	}

	private JDBCConnection() {

		// Register JDBC driver
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Error Registering DB");
			e.printStackTrace();
		}

	}

}
