package company.project.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtility {

	
	public static Connection connection = null;

	public static ArrayList<String> executeSQLQuery(String sqlQuery) throws SQLException {

		ArrayList<String> resultValues = new ArrayList<String>();
		ResultSet rs;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
																
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			verifyConnection();
			System.out.println("Connected to the database...");
			System.out.println("Connection string is " + connection.toString());
		} catch (SQLException sqlEx) {
			System.out.println("Database connection failed.");
			System.out.println("SQL Exception:" + sqlEx.getStackTrace());
			sqlEx.printStackTrace();
		}

		System.out.println("Executing query... " + sqlQuery);
		Statement stmt = connection.createStatement();
		rs = stmt.executeQuery(sqlQuery);

		// convert Result Set into an Array called resultValues - each element
		// of the array represents a row of the result set table
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				resultValues.add(rs.getMetaData().getColumnName(i) + ":" + rs.getString(i) + " "); 
				 if (i > 1)
				 System.out.print(", ");
				 String columnValue = rs.getString(i);
				 System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			 System.out.println("");
		}
		return resultValues;
	}

	/**
	 * Method returns values for the column name which is passed as list.
	 * 
	 * @param
	 * 
	 * @return Column values.
	 */
	public static ArrayList<String> dataFromDbResponse(List<String> listOfDBValues, String colName) {
		ArrayList<String> columnValues = new ArrayList<String>();
		for (int i = 0; i < listOfDBValues.size(); i++) {
			if (listOfDBValues.get(i).contains(colName)) {
				columnValues.add(listOfDBValues.get(i).split(":")[1].toString().trim());
			}
		}
		System.out.println("\nValues for " + colName + " column are:");
		for (String value : columnValues) {
			System.out.println(value);
		}
		System.out.println("");

		return columnValues;
	}

	public static void verifyConnection() throws SQLException {

		if (connection == null || connection.isClosed()) {
			System.out.println("Creating connection .... ");
			connection = DriverManager.getConnection(Config.dbconnectionUrl, Config.dbusername, Config.dbpassword);
		} else {
			// Checking if connection is valid.
			if (connection.isValid(5000)) {
				System.out.println("Found valid database connection..");
			} else {
				System.out.println("Found invalid database connection.. Creating new connection ");
				connection = DriverManager.getConnection(Config.dbconnectionUrl, Config.dbusername, Config.dbpassword);
			}
		}

	}

	public static void closeConnection() throws SQLException {
		System.out.println("Closing db connection...");
		connection.close();
	}

}
