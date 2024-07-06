package com.az.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private Connection connection;
	private String url = "jdbc:mysql://localhost:3306/DB_ClienteFactura";
	private String username = "root";
	private String password = "indigo";
	
	private DatabaseConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException exception) {
			System.out.println(exception.getMessage());
			throw new SQLException(exception.getMessage());
			
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static DatabaseConnection getInstance() throws SQLException {
		if(instance == null) {
			instance = new DatabaseConnection();
		} else if(instance.getConnection().isClosed()) {
			instance = new DatabaseConnection();
		}
		
		return instance;
	}
	
	
}