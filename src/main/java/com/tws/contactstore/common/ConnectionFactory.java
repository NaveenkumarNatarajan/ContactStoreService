package com.tws.contactstore.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tws.contactstore.config.DataSource;

/** 
 * @version 1.0 20-08-2017
 * @author Naveen
 * create Connection method creates new connection.
 * close Connection method close the connection passed to it.
 */
public class ConnectionFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	DataSource dataSource;
	
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	public  final String URL = dataSource.getUrl();
	public  final String USER = dataSource.getUserName();
	public  final String PASSWORD = dataSource.getPassword();

	// private constructor
	private ConnectionFactory() {
		String DRIVER_CLASS = dataSource.getDriver();
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Unable to load driver class.");
		}
	}

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to create connection to database.");
		}
		return connection;
	}

	public static Connection getConnection() {
		return connectionFactory.createConnection();
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Unable to close connection");
		}
	}

}
