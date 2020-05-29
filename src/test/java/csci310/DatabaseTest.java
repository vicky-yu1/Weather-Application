package csci310;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * TESTS Database.java
 */
public class DatabaseTest extends Mockito {

	static HttpServletRequest request;
	static HttpSession session;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	static public void UtilityTestSetup() {
		// Testing class that mimics HttpSession and servlet requests
		request = Mockito.mock(HttpServletRequest.class);
		session = new MockSession();
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void database_UsernameExistsTest() throws Exception {
		// Retrieve boolean response from database whether "existanceTestUser" exists
		Assert.assertTrue(Database.usernameExists("existanceTestUser"));
	}

	@Test
	public void database_UsernamePasswordHashedExistsTest() throws Exception {
		// Retrieve boolean response from database whether
		// "existanceTestUser":"existanceTestUserPassword"
		Assert.assertTrue(Database.usernamePasswordExists("existanceTestUser", "existanceTestUserPassword"));
	}

	@Test
	public void database_RegisterUserTest() throws Exception {
		// Retrieve boolean response from database for successful registration
		Assert.assertTrue(Database.registerUser("registerTestUser", "registerTestUserPassword"));
	}

	@Test
	public void database_DeleteUserTest() throws Exception {
		// Remove the test user from database so register test can be run again
		Assert.assertTrue(Database.deleteUser("registerTestUser"));
	}

	@Test
	public void database_CheckPasswordHashed() throws Exception {
		// Capture password from database as string
		String databasePasswordStr = Database.getUsersPassword("existanceTestUser");

		// Check if parsed databasePasswordStr is a parsed integer (Hashes are returned as integers)
		Assert.assertTrue(Utility.isNumeric(databasePasswordStr));
	}
}
