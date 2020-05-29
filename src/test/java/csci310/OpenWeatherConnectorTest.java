package csci310;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import citySearch.OpenWeatherConnector;
import forecastSearch.OpenWeatherConnectorForecast;
import radiusSearch.OpenWeatherConnectorRadius;

/*
 * TESTS the openWeatherConnector, the OpenWeatherConnectorForecast
 * and the OpenWeatherConnectorRadius classes
 */
public class OpenWeatherConnectorTest extends Mockito {

	static HttpServletRequest request;
	static HttpSession session;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	static public void UtilityTestSetup() {
		request = Mockito.mock(HttpServletRequest.class);
		session = new MockSession(); // testing class that mimics HttpSession
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void currentWeatherCall_NameTest1() throws Exception {
		// Retrieve current weather data of LA from static current weather call method
		String response = OpenWeatherConnector.currWeatherCall("Los Angeles", true);

		// The response string should have 6 items, delimited by "_".
		String[] responses = response.split("_");

		// Check if we retrieved 6 response strings after splitting
		Assert.assertEquals(6, responses.length);

		// Check that each response string is appropriate type or value
		Assert.assertEquals(responses[0], "Los Angeles"); // String match city name
		Assert.assertTrue(Utility.isAlpha(responses[1])); // Description must be alphabetic
		Assert.assertTrue(Utility.isFloat(responses[2])); // Temperature must be integer
		Assert.assertTrue(Utility.isAlphanumeric(responses[3])); // Icon ID must be alphanumeric
		Assert.assertTrue(Utility.isFloat(responses[4])); // Latitude must be float
		Assert.assertTrue(Utility.isFloat(responses[5])); // Longitude must be float
	}

	@Test
	public void currentWeatherCall_NameTest2() throws Exception {
		// Retrieve current weather data of D.C from static current weather call method
		String response = OpenWeatherConnector.currWeatherCall("Washington D.C.", true);

		// The response string should have 6 items, delimited by "_".
		String[] responses = response.split("_");

		// Check if we retrieved 6 response strings after splitting
		Assert.assertEquals(6, responses.length);

		// Check that each response string is appropriate type or value
		Assert.assertEquals(responses[0], "Washington D.C."); // String match city name
		Assert.assertTrue(Utility.isAlpha(responses[1])); // Description must be alphabetic
		Assert.assertTrue(Utility.isFloat(responses[2])); // Temperature must be integer
		Assert.assertTrue(Utility.isAlphanumeric(responses[3])); // Icon ID must be alphanumeric
		Assert.assertTrue(Utility.isFloat(responses[4])); // Latitude must be float
		Assert.assertTrue(Utility.isFloat(responses[5])); // Longitude must be float
	}

	@Test
	public void currentWeatherCall_ZipTest() throws Exception {
		// Get current weather data of LA by ZIP code (set searchMode parameter false
		// for ZIP search)
		String response = OpenWeatherConnector.currWeatherCall("90007", false);

		// The response string should have 6 items, delimited by "_".
		String[] responses = response.split("_");

		// Check if we retrieved 6 response strings after splitting
		Assert.assertEquals(6, responses.length);

		// Check that each response string is appropriate type or value
		Assert.assertEquals(responses[0], "Los Angeles"); // String match city name
		Assert.assertTrue(Utility.isAlpha(responses[1])); // Description must be alphabetic
		Assert.assertTrue(Utility.isFloat(responses[2])); // Temperature must be integer
		Assert.assertTrue(Utility.isAlphanumeric(responses[3])); // Icon ID must be alphanumeric
		Assert.assertTrue(Utility.isFloat(responses[4])); // Latitude must be float
		Assert.assertTrue(Utility.isFloat(responses[5])); // Longitude must be float
	}

	@Test
	public void currentWeatherCall_FalseNameTest() throws Exception {
		String expected = "";
		String result = OpenWeatherConnector.currWeatherCall("Imaginary City!", true);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void currentWeatherCall_FalseSearchModeTest() {
		String expected = "";
		String result = OpenWeatherConnector.currWeatherCall("Los Angeles", false);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void radiusWeatherCall_StressTest() throws Exception {

		// HARDCODE arguments for LA radius weather call
		String radiusStr = "20"; // Radius of 20
		String latitudeStr = "34.05"; // LA coordinates
		String longitudeStr = "-118.24";
		String minTempStr = "-1000"; // Widest temperature range
		String maxTempStr = "80";
		String numOfResultsStr = "50"; // Maximize number of results returned by API

		// Retrieve response string of cities delimited by "&" and
		// split response string into individual string tokens for each city
		String response = OpenWeatherConnectorRadius.radiusWeatherCall(radiusStr, latitudeStr, longitudeStr, minTempStr,
				maxTempStr, numOfResultsStr, request);

		String[] cityStrTokens = response.split("&");

		// Ensure we retrieved less than or equal to 50 results (number of results)
		Assert.assertTrue(cityStrTokens.length <= 50);

		// Parse out individual data fields for each cityStrToken
		String[][] responseDataTokens = new String[cityStrTokens.length][];
		for (int i = 0; i < cityStrTokens.length; i++) {
			responseDataTokens[i] = cityStrTokens[i].split("_");
		}

		// Test each data field
		for (int i = 0; i < cityStrTokens.length; i++) {
			Assert.assertEquals(responseDataTokens[i].length, 9); // Ensure all 9 data items were retrieved

			Assert.assertTrue(Utility.isAlpha(responseDataTokens[i][0])); // City name
			Assert.assertTrue(Utility.isAlpha(responseDataTokens[i][1])); // Country name
			Assert.assertTrue(Utility.isFloat(responseDataTokens[i][2])); // Current temperature
			Assert.assertTrue(Utility.isFloat(responseDataTokens[i][3])); // Minimum temperature
			Assert.assertTrue(Utility.isFloat(responseDataTokens[i][4])); // Maximum temperature
			Assert.assertTrue(Utility.isFloat(responseDataTokens[i][5])); // Current location latitude
			Assert.assertTrue(Utility.isFloat(responseDataTokens[i][6])); // Current location longitude
			Assert.assertTrue(Utility.isNumeric(responseDataTokens[i][7])); // Distance from origin
			Assert.assertTrue(Utility.isAlpha(responseDataTokens[i][8])); // Is favorite - "true" or "false"

			// ADDITIONAL LOGIC FOR DISTANCE RADIUS TESTS
			double distanceFromOrigin = Double.parseDouble(responseDataTokens[i][7]);
			double testRadius = Double.parseDouble(radiusStr);
			Assert.assertTrue(distanceFromOrigin <= testRadius);
		}
	}

	@Test
	public void everythingTest1() {
		String result = OpenWeatherConnector.TestMore();
		Assert.assertEquals("Hello", result);
	}

	@Test
	public void everythingTest2() {
		String result = OpenWeatherConnectorForecast.TestAll();
		Assert.assertEquals("Hello", result);
	}

	@Test
	public void everythingTest3() {
		String result = OpenWeatherConnectorRadius.TestAll();
		Assert.assertEquals("Hello", result);
	}
}
