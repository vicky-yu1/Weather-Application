package csci310;

import java.sql.*;

public class Database {
	// Global database URL string
	public static final String databaseURLStr = "jdbc:mysql://google/weather?cloudSqlInstance=weatherapp-272900:us-west2:maindb&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&requireSSL=false&user=grouph&password=password";

	/** Check whether the username exists in database **/
	public static boolean usernameExists(String usernameStr) throws ClassNotFoundException {
		// Establish a connection to the database (try-with-resources syntax)
		try (Connection connnection = DriverManager.getConnection(databaseURLStr)) {

			// Create a prepared statement
			PreparedStatement preparedStatement = connnection
					.prepareStatement("SELECT username FROM Users WHERE username = ?");
			preparedStatement.setString(1, usernameStr);

			// Capture database results in a resultSet
			ResultSet resultSet = preparedStatement.executeQuery();

			// Check if given usernameStr exists in database and return appropriate boolean
			if (resultSet.next() == false) {
				return false;
			} else if (resultSet.getString("username").matches(usernameStr)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Check whether the username and password exist in database **/
	public static boolean usernamePasswordExists(String usernameStr, String passwordStr) throws ClassNotFoundException {
		// Establish a connection to the database (try-with-resources syntax)
		try (Connection connnection = DriverManager.getConnection(databaseURLStr)) {
			// Create a prepared statement
			PreparedStatement preparedStatement = connnection
					.prepareStatement("SELECT username, password FROM Users WHERE username = ? AND password = ?");
			preparedStatement.setString(1, usernameStr);
			preparedStatement.setString(2, Integer.toString(passwordStr.hashCode())); // Hash password before checking
																																								// database
			// Capture database results in a resultSet
			ResultSet resultSet = preparedStatement.executeQuery();

			// Check if given usernameStr & passwordStr exists in database
			if (resultSet.next() == false) {
				return false;
			} else if (resultSet.getString("username").toLowerCase().matches(usernameStr.toLowerCase())
					&& resultSet.getString("password").matches(Integer.toString(passwordStr.hashCode()))) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Register a username and password into the database **/
	public static boolean registerUser(String usernameStr, String passwordStr) throws ClassNotFoundException {
		// Establish a connection to the database (try-with-resources syntax)
		try (Connection connection = DriverManager.getConnection(databaseURLStr)) {
			// Create a prepared statement
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Users (username, password) values (?, ?)");
			preparedStatement.setString(1, usernameStr);
			preparedStatement.setString(2, Integer.toString(passwordStr.hashCode()));

			// Store the user in the database
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// If control reaches here, then an SQL exception was caught
		return false;
	}

	/** Delete a user (by username) from the database **/
	public static boolean deleteUser(String usernameStr) throws ClassNotFoundException {
		// Establish a connection to the database (try-with-resources syntax)
		try (Connection connection = DriverManager.getConnection(databaseURLStr)) {
			// Create a prepared statement
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE username = ?");
			preparedStatement.setString(1, usernameStr);

			// Delete the user from the database. Note at least one user should exist after
			// deletion so return value of prepared statement is not 0
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// If control reaches here, then an SQL exception was caught
		return false;
	}

	/** Retrieve the username's hashed password for testing **/
	public static String getUsersPassword(String usernameStr) throws ClassNotFoundException {
		// Establish a connection to the database (try-with-resources syntax)
		try (Connection connection = DriverManager.getConnection(databaseURLStr)) {
			// Create a prepared statement
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT username, password FROM Users WHERE username = ?");
			preparedStatement.setString(1, usernameStr);

			// Capture database results in a resultSet
			ResultSet resultSet = preparedStatement.executeQuery();

			// Check if password exists for given username in database
			if (!resultSet.next() == false) {
				return resultSet.getString("password");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
