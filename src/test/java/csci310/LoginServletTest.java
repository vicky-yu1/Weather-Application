package csci310;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

/*
 * TESTS LoginServlet.java
 */
public class LoginServletTest extends Mockito {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void loginServlet_passwordReenterPasswordTest() throws Exception {
		// Check if appropriate response string returned for mismatching passwords
		Assert.assertTrue(LoginServlet.registerUser("registerTestUser", "registerTestPassword", "ReGiStErPaSsWoRd")
				.matches("ERROR: password and reentered password do not match"));
	}

	@Test
	public void loginServlet_registrationUsernameTakenTest() throws Exception {
		// Check if appropriate response string returned taken username
		Assert.assertTrue(LoginServlet.registerUser("existanceTestUser", "password", "password")
				.matches("ERROR: username already exists"));
	}

	@Test
	public void loginServlet_successRegistrationTest() throws Exception {
		// Retrieve boolean response from database for successful registration
		Assert.assertTrue(
				LoginServlet.registerUser("registerTestUser", "registerTestUserPassword", "registerTestUserPassword")
						.matches("user registered successfully"));

		// Make sure to clean up and remove the user so test can be rerun
		Database.deleteUser("registerTestUser");
	}

	@Test
	public void loginServlet_successLoginTest() throws Exception {
		// Check if appropriate response string returned for failed login
		Assert.assertTrue(
				LoginServlet.loginUser("existanceTestUser", "existanceTestUserPassword").matches("user logged in successfully"));
	}

	@Test
	public void loginServlet_failLoginTest() throws Exception {
		// Check if appropriate response string returned for failed login
		Assert.assertTrue(LoginServlet.loginUser("existanceTestUser", "incorrectPassword")
				.matches("ERROR: username and password combination invalid"));
	}

}
