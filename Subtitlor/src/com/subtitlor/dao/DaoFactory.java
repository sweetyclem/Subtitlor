package com.subtitlor.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class DaoFactory {
	private String url;
	private String username;
	private String password;
	
	public DaoFactory(String url, String username, String password) {
		this.url = url;
		this.password = password;
		this.username = username;
	}
	
	public static DaoFactory getInstance() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Class not found line 23");
		}
		
		DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/subtitlor", "root", "pass");
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		Connection connection = (Connection) DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);
		return connection;
	}
	
	public daoUser getDaoUser() {
		return new daoUserImpl(this);
	}
}
