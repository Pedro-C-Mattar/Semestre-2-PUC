package dao;

import java.sql.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "learnindbd.postgres.database.azure.com";
		String mydatabase = "learnind";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "adminld";
		String password = "LlL12345";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao != null);
			if (status) {
				System.out.println("Conexão efetuada com o postgres! URL=" + url + " user=" + username);
			} else {
				System.err.println("Conexão retornou nula, verifique as configurações: URL=" + url);
			}
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
			e.printStackTrace();
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			if (conexao != null) conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}
