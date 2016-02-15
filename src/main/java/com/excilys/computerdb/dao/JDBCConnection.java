package com.excilys.computerdb.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.exception.CriticalDatabaseException;

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
	private static String DB_URL;

	// Database credentials
	private static String LOGIN;
	private static String PASS;

	private static JDBCConnection instance = new JDBCConnection();

	public static JDBCConnection getInstance() {
		return instance;
	}

	/**
	 * Send back a new Connection to the user
	 * 
	 * @return new Connection
	 */
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
		Properties prop;
		try {
			prop = readPropertyFile();
			DB_URL = prop.getProperty("DB_URL");
			LOGIN = prop.getProperty("LOGIN");
			PASS = prop.getProperty("PASS");
		} catch (CriticalDatabaseException e1) {
			LOGGER.error("Error loading config.properties file");
			e1.printStackTrace();
			return;
		}

		// Register JDBC driver
		try {
			LOGGER.trace("Registering DB...");
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Error Registering DB");
			e.printStackTrace();
		}

	}

	/**
	 * Read a config.properties file to get DB url and credentials
	 * 
	 * @return credentials and url
	 * @throws CriticalDatabaseException
	 */
	private Properties readPropertyFile() throws CriticalDatabaseException {
		LOGGER.trace("loading config.properties...");
		Properties prop = new Properties();
		
		//InputStream input = new FileInputStream("config.properties");
		//InputStream input = JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties");
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			prop.load(input);
		} catch (FileNotFoundException e) {
			LOGGER.error("Error reading propertyFile");
			e.printStackTrace();
			throw new CriticalDatabaseException();
		} catch (IOException e) {
			LOGGER.error("Error reading propertyFile");
			e.printStackTrace();
			throw new CriticalDatabaseException();
		}
		return prop;
	}

}
