package com.excilys.computerdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * This class is used to access the connection to the database. This is a
 * singleton type. You can get a reference of the connection by using the
 * getInstance() methode
 * 
 * @author Steven Fougeron
 *
 */
public class JDBCConnection {

	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCConnection.class);
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL;

	// Database credentials
	private static String LOGIN;
	private static String PASS;
	
	private static BoneCP connectionPool = null;

	private static JDBCConnection instance = new JDBCConnection();

	public static JDBCConnection getInstance() {
		return instance;
	}

	/**
	 * Send back a new Connection to the user
	 * 
	 * @return new Connection
	 * @throws CriticalDatabaseException
	 */
	public static Connection getConnection() throws CriticalDatabaseException {
		try {
			LOGGER.trace("Retriving a connection from the spawning pool...");
			 return connectionPool.getConnection();
		} catch (SQLException e) {
			LOGGER.error("Error Connecting to the database");
			e.printStackTrace();
			throw new CriticalDatabaseException("Error Connecting to the database");
		}

	}

	private JDBCConnection() {
		Properties prop;
		prop = readPropertyFile();
		DB_URL = prop.getProperty("DB_URL");
		LOGIN = prop.getProperty("LOGIN");
		PASS = prop.getProperty("PASS");

		// Register JDBC driver
		try {
			LOGGER.trace("Registering DB...");
			Class.forName(JDBC_DRIVER);
			BoneCPConfig config =new BoneCPConfig();
			config.setJdbcUrl(DB_URL);
			config.setUsername(LOGIN);
			config.setPassword(PASS);
			
			config.setMinConnectionsPerPartition(1);
			config.setMaxConnectionsPerPartition(5);
			
			config.setPartitionCount(100);
			
			connectionPool = new BoneCP(config);
			
		} catch (ClassNotFoundException e) {
			LOGGER.error("Error Registering DB");
			e.printStackTrace();
		} catch (SQLException e) {
			LOGGER.error("Creating connection pool");
			e.printStackTrace();
		}

	}

	/**
	 * Read a config.properties file to get DB url and credentials
	 * 
	 * @return credentials and url
	 * @throws CriticalDatabaseException
	 */
	private Properties readPropertyFile() {
		LOGGER.trace("loading config.properties...");
		Properties prop = new Properties();

		// InputStream input = new FileInputStream("config.properties");
		// InputStream input =
		// JDBCConnection.class.getClassLoader().getResourceAsStream("config.properties");
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			prop.load(input);
		} catch (IOException e) {
			LOGGER.error("Error reading propertyFile");
			e.printStackTrace();

		}
		return prop;
	}

}
