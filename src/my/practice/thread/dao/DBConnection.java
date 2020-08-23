package my.practice.thread.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", "SYSTEM", "admin");
		return connection;
	}
}
