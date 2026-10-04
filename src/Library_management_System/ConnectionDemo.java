
package Library_management_System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
	

	    public static void main(String[] args) {

	        String url = "jdbc:mysql://localhost:3306/jdbc_practice";
	        String username = "root";
	        String password = "Suhani@2003";

	        try {
	            Connection con = DriverManager.getConnection(url, username, password);

	            System.out.println("MySQL connection successful!");

	            con.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	

}

