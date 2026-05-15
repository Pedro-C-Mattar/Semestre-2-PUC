package app; // Ajuste o pacote se necessário

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    public Connection recuperaConexao() {
        // Dados do seu Azure PostgreSQL
        String url = "jdbc:postgresql://learnindbd.postgres.database.azure.com:5432/learnind";
        String usuario = "adminld";
        String senha = "LlL12345"; // <--- COLOQUE A SENHA AQUI

        Properties props = new Properties();
        props.setProperty("user", usuario);
        props.setProperty("password", senha);
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "require");

        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no Banco: " + e.getMessage());
        }
    }
}