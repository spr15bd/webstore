/* Connector.java - class to create database connections as needed by the application*/

package store;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
	
	public static Connection getConnection() {
		Connection con=null;
		String driver="com.mysql.jdbc.Driver";
		String dbURL="jdbc:mysql://127.8.226.2:3306/webstore";
		String username="D!*vU719c";
		String password="8uGhiKlqA";
		try {
			DriverManager.registerDriver((java.sql.Driver) Class.forName(driver).newInstance());
			con=DriverManager.getConnection(dbURL, username, password);
			
		} catch (ClassNotFoundException cnf) {
			System.out.println("Class Not found exception raised");
			cnf.printStackTrace();
			return null;
		} catch (SQLException sql) {
			sql.printStackTrace();
			System.out.println("Sql exception raised");
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}