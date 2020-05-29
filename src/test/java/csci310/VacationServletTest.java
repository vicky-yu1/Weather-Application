package csci310;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.*;

public class VacationServletTest extends Mockito {

	VacationServlet vs;
	HttpServletRequest req;
	HttpServletResponse res;
	HttpSession session;

	@Before
	public void VacationServletTestSetup() {
		req = Mockito.mock(HttpServletRequest.class);
		res = Mockito.mock(HttpServletResponse.class);
		session = new MockSession(); // testing class that mimics HttpSession
		when(req.getSession()).thenReturn(session);
		vs = new VacationServlet();
	}

	// Make a post request for the initialize() function
	@Test
	public void initalizeTest() throws Exception {
		when(req.getParameter("method")).thenReturn("initialize");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);
		vs.doPost(req, res);
		pw.flush();

		// assert that the information passed to front end matches whats store in
		// backend
		Assert.assertEquals(sw.toString(), SessionData.getInitialTempUnit(req));
	}

	// Make a post request for the toggleTempUnit() function
	// This will toggle four times to see if things are working properly
	@Test
	public void toggleTempUnitTest() throws Exception {

		when(req.getParameter("method")).thenReturn("toggleTempUnit");
		when(req.getSession()).thenReturn(session);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		// here are the two possible states of session
		String initialState = "F";
		String toggledState = "C";

		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));

		// call toggle method
		vs.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));

		// call toggle method
		vs.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));

		// call toggle method
		vs.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));

		// call toggle method
		vs.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));
	}

	// Make a post request for the search() function using CityName.
	public void searchTestCityName() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("40");
		when(req.getParameter("max")).thenReturn("80");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Los Angeles");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();

		String[] results = responseText.split("&");

		// Assert that the response text has the correct number of results;
		Assert.assertTrue(results.length <= 5);

		for (String result : results) {
			String[] data = result.split("_");

			// Assert that each result has the correct number or arguments
			Assert.assertEquals(9, data.length);

			String cityName = data[0];
			String country = data[1];
			String currTemp = data[2];
			String tempMin = data[3];
			String tempMax = data[4];
			String lattitude = data[5];
			String longitude = data[6];
			String distance = data[7];
			String isFav = data[8];

			// assert that all of the data is in the proper format.
			// Note that we can't check specifics here because it changes with every API
			// call.
			Assert.assertTrue(Utility.isAlpha(cityName));
			Assert.assertTrue(Utility.isAlpha(country));
			Assert.assertTrue(Utility.isNumeric(currTemp));
			Assert.assertTrue(Utility.isNumeric(tempMin));
			Assert.assertTrue(Utility.isNumeric(tempMax));
			Assert.assertTrue(Utility.isFloat(lattitude));
			Assert.assertTrue(Utility.isFloat(longitude));
			Assert.assertTrue(Utility.isFloat(distance));
			Assert.assertTrue(Utility.isAlpha(isFav));
		}
	}

	// Make a post request for the search() function using ZipCode.
	public void searchTestCityZipcode() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("40");
		when(req.getParameter("max")).thenReturn("80");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("90007");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();

		String[] results = responseText.split("&");

		// Assert that the response text has the correct number of results;
		Assert.assertTrue(results.length <= 5);

		for (String result : results) {
			String[] data = result.split("_");

			// Assert that each result has the correct number or arguments
			Assert.assertTrue(data.length == 9);

			String cityName = data[0];
			String country = data[1];
			String currTemp = data[2];
			String tempMin = data[3];
			String tempMax = data[4];
			String lattitude = data[5];
			String longitude = data[6];
			String distance = data[7];
			String isFav = data[8]; // either true or false

			// assert that all of the data is in the proper format.
			// Note that we can't check specifics here because it changes with every API
			// call.
			Assert.assertTrue(Utility.isAlpha(cityName));
			Assert.assertTrue(Utility.isAlpha(country));
			Assert.assertTrue(Utility.isNumeric(currTemp));
			Assert.assertTrue(Utility.isNumeric(tempMin));
			Assert.assertTrue(Utility.isNumeric(tempMax));
			Assert.assertTrue(Utility.isFloat(lattitude));
			Assert.assertTrue(Utility.isFloat(longitude));
			Assert.assertTrue(Utility.isFloat(distance));
			Assert.assertTrue(Utility.isAlpha(isFav));
		}
	}

//	@Test
//	public void addToFavorites() throws Exception {
//
//		when(req.getParameter("method")).thenReturn("addToFav");
//		when(req.getParameter("name")).thenReturn("Chicago");
//		when(req.getParameter("country")).thenReturn("US");
//		when(req.getParameter("lat")).thenReturn("34.05");
//		when(req.getParameter("long")).thenReturn("118.24");
//
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw);
//		when(res.getWriter()).thenReturn(pw);
//
//		vs.doPost(req, res);
//		pw.flush();
//
//		ArrayList<CityObject> after = SessionData.getFavoriteCities(req);
//		boolean foundLA = false;
//		for (CityObject c : after) {
//			if (c.getName().equals("Chicago") && c.getCountry().equals("US")) {
//				foundLA = true;
//				break;
//			}
//		}
//		Assert.assertTrue(foundLA);
//	}
//
//	@Test
//	public void removeFromFavorites() throws Exception {
//		CityObject city = new CityObject("Sydney", "AU", "-33.87", "151.21");
//		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
//		SessionData.addToFav(req, city);
//		SessionData.addToFav(req, city1);
//
//		when(req.getParameter("method")).thenReturn("remFromFav");
//		when(req.getParameter("name")).thenReturn("Los Angeles");
//		when(req.getParameter("country")).thenReturn("US");
//		when(req.getParameter("lat")).thenReturn("34.05");
//		when(req.getParameter("long")).thenReturn("-118.24");
//
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw);
//		when(res.getWriter()).thenReturn(pw);
//		vs.doPost(req, res);
//		pw.flush();
//
//		ArrayList<CityObject> after = SessionData.getFavoriteCities(req);
//		boolean foundLA = false;
//		for (CityObject c : after) {
//			if (c.getName().equals("Los Angeles") && c.getCountry().equals("US")) {
//				foundLA = true;
//				break;
//			}
//		}
//		Assert.assertTrue(!foundLA);
//	}

	public void methodInvalidTest() throws Exception {
		when(req.getParameter("method")).thenReturn("hello");
		when(req.getParameter("input")).thenReturn("90089");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		// check to see that response is formatted properly
		String res = sw.toString().trim();
		String expected = "Bad Ajax Call; Servlet couldn't read the request properly.";
		Assert.assertEquals(expected, res);
	}

	public void invalidRangeTest() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("50");
		when(req.getParameter("max")).thenReturn("20");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("90007");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();
		String expected = "Error:TempRange";
		Assert.assertEquals(expected, responseText);
	}

	public void invalidCityTest1() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("40");
		when(req.getParameter("max")).thenReturn("70");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("903dsg7");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();
		String expected = "Error:Location";
		Assert.assertEquals(expected, responseText);
	}

	public void invalidCityTest2() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("40");
		when(req.getParameter("max")).thenReturn("70");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("HELLO HAWRAER");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();
		String expected = "No Weather Data Found.";
		Assert.assertEquals(expected, responseText);
	}

	public void invalidCountTest() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("40");
		when(req.getParameter("max")).thenReturn("70");
		when(req.getParameter("count")).thenReturn("5ewtd8");
		when(req.getParameter("location")).thenReturn("Los Angeles");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();
		String expected = "No Weather Data Found.";
		Assert.assertEquals(expected, responseText);
	}

	public void noCitiesFoundTest() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("min")).thenReturn("100");
		when(req.getParameter("max")).thenReturn("140");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Los Angeles");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		vs.doPost(req, res);
		pw.flush();

		String responseText = sw.toString();
		String expected = "No Weather Data Found.";
		Assert.assertEquals(expected, responseText);
	}

	@AfterClass
	public static void VacationServletTestCleanup() {

	}
}
