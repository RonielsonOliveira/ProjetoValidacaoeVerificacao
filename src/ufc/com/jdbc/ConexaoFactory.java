package ufc.com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
	private final String ip = "localhost"; 
	private final Integer port = 5432;
	private final String user = "postgres";
	private final String password = "13081997fron";
	private final String database = "postgres";
    
	public Connection conectarBanco() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://"+ip+":"+port+"/"+database, user, password); 
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}