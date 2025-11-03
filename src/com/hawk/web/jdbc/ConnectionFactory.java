package com.hawk.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_estoque?useSSL=false&serverTimezone=UTC";
	
//	private static final String DB_NAME = "sistema_estoque";
	
	private static final String DB_USER = "root";
	
	private static final String DB_PASS = "admin";
	
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static Connection getConnection() {
		
		try {
			
			Class.forName(DB_DRIVER);
			
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
		} catch (ClassNotFoundException e) {
			System.err.println("ERRO: Driver JDBC do MySQL nao encontrado");
			throw new RuntimeException("Driver nao encontrado", e);
		} catch (SQLException e) {
			System.err.println("ERRO: Falha ao conectar ao banco de dados");
			e.printStackTrace();
			throw new RuntimeException("Falha na conexao com o banco de dados");
		}
	}

}
