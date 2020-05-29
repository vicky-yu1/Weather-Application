package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Global successful login boolean value
	static Boolean userLoggedIn = false;
	static String loggedInUsername = "";

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Obtain username and password data from post request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String reenterPassword = request.getParameter("reenterPassword");

		// Parse out requested action from front end
		String requestedAction = request.getParameter("requestedAction");

		// System.out.println("requested action in server: " + requestedAction);
		// System.out.println("username string in server: " + username);
		// System.out.println("password string in server: " + password);

		String responseText = "";
		// Use if-else rather than switch to process because of large blocks
		if (requestedAction.matches("register")) {
			try {
				responseText = registerUser(username, password, reenterPassword);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (requestedAction.matches("login")) {
			try {
				// System.out.println("Control should not reach here");
				responseText = loginUser(username, password);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		// Return the appropriate response text to front end AJAX request
		PrintWriter printWriter = response.getWriter();
		// System.out.println("Response text from servlet: " + responseText);
		printWriter.print(responseText);
		printWriter.flush();

	}

	/** Produces response text depending on success of user registration **/
	public static String registerUser(String usernameStr, String passwordStr, String reenterPasswordStr)
			throws ClassNotFoundException {
		// First check if password and reentered password match
		if (!passwordStr.matches(reenterPasswordStr)) {
			return "ERROR: password and reentered password do not match";
		}
		// Next check if username already exists in database
		else if (Database.usernameExists(usernameStr)) {
			return "ERROR: username already exists";
		}
		// Proceed to register the user into the database
		else {
			// System.out.println("Control in else branch of register user");
			Database.registerUser(usernameStr, passwordStr);
			// Set userLoggedIn boolean that will become a session attribute
			userLoggedIn = true;
			return "user registered successfully";
		}
	}

	/** Produces response text depending on success of user login **/
	public static String loginUser(String usernameStr, String passwordStr) throws ClassNotFoundException {

		// Check if usernameStr and passwordStr exist in the database
		if (Database.usernamePasswordExists(usernameStr, passwordStr)) {
			return "user logged in successfully";
		} else {
			return "ERROR: username and password combination invalid";
		}
	}
}
