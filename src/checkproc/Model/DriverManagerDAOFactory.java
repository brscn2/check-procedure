package checkproc.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerDAOFactory extends DAOFactory {
	private String url;
	private String username;
	private String password;
	
	DriverManagerDAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
