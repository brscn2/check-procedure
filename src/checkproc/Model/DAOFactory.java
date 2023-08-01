package checkproc.Model;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
	public static DAOFactory getInstance(String driverClassName, String url, String username, String password) throws DAOConfigurationException {
		DAOFactory instance;
		
		try {	
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {// TODO Handle exception
			throw new DAOConfigurationException(
				"Driver class " + driverClassName + " bulunamadi. Ismi kontrol edin.");
		}
		
		instance = new DriverManagerDAOFactory(url, username, password);

		return instance;
	}
	
	abstract Connection getConnection() throws SQLException;
}
