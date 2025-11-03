package com.hawk.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// password: estoquehawk

public class ConnectionFactory {
	
	private static final String DB_URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres?sslmode=require";
	
//	private static final String DB_NAME = "sistema_estoque";
	
	private static final String DB_USER = "postgres.sypqcogxwmplwfgwgiii";
	
	private static final String DB_PASS = "estoquehawk";
	
	private static final String DB_DRIVER = "org.postgresql.Driver";
	
	public static Connection getConnection() {
		
		try {
			
			Class.forName(DB_DRIVER);
			
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
		} catch (ClassNotFoundException e) {
			System.err.println("ERRO: Driver JDBC do PostgreSQL nao encontrado");
			throw new RuntimeException("Driver nao encontrado", e);
		} catch (SQLException e) {
			System.err.println("ERRO: Falha ao conectar ao banco de dados Supabase");
			e.printStackTrace();
			throw new RuntimeException("Falha na conexao com o banco de dados", e);
		}
	}

}
