package com.excilys.computerdb.dao;

import java.sql.*;

/**
 * This class is used to access the connection to the database. This is a
 * singleton type. You can get a reference of the connection by using the
 * getInstance() methode
 * 
 * @author excilys
 *
 */
public class JDBCConnection {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";

	// Database credentials
	private static final String LOGIN = "admincdb";
	private static final String PASS = "qwerty1234";

	private static Connection conn;
	private static Statement stmt;

	private static JDBCConnection instance = new JDBCConnection();

	public static JDBCConnection getInstance() {
		return instance;
	}

	public static Connection getConnection() {
		return conn;
	}

	private JDBCConnection() {

		// Register JDBC driver
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Error Registering DB");
			e.printStackTrace();
		}

		// Open a connection
		try {
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, LOGIN, PASS);
		} catch (SQLException e) {
			System.err.println("Error Connecting to the database");
			e.printStackTrace();
		}
		// Creating Statement
		try {
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.err.println("Error Creating Statement");
			e.printStackTrace();
		}

	}

	/**
	 * Take a sql request in Params(String type) and execute it.
	 * 
	 * @param sql
	 *            SQL request
	 * @return
	 */
	public ResultSet sendRequest(String sql) {

		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("Error Executing the request");
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public void finalize() {
		try {
			System.out.println("Closing database");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Error Closing the database");
			e.printStackTrace();
		}

	}
}
