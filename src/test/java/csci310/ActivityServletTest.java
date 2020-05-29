package csci310;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.*;

public class ActivityServletTest extends Mockito {

	ActivityServlet as;
	HttpServletRequest req;
	HttpServletResponse res;
	HttpSession session;
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);

	@Before
	public void ActivityServletTestSetup() {
		req = mock(HttpServletRequest.class);
		res = mock(HttpServletResponse.class);
		session = new MockSession();
		when(req.getSession()).thenReturn(session);
		as = new ActivityServlet();
	}

	@Test
	public void initializeTest() throws Exception {
		when(req.getParameter("method")).thenReturn("initialize");
		when(res.getWriter()).thenReturn(pw);
		as.doPost(req, res);
		pw.flush();

		Assert.assertEquals(sw.toString(), SessionData.getInitialTempUnit(req));

	}

	@Test
	public void toggleTempUnitTest() throws Exception {

		when(req.getParameter("method")).thenReturn("toggleTempUnit");
		when(req.getSession()).thenReturn(session);

		when(res.getWriter()).thenReturn(pw);

		String initialState = "F";
		String toggledState = "C";

		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));

		as.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));

		as.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));

		as.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));

		as.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));
	}

	@Test
	public void searchTestSnowIce() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Skiing");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Aspen");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();
		String responseText = sw.toString().trim();

		String[] results = responseText.split("&");

		// Verify total # results
		Assert.assertTrue(results.length <= 5);
		if (results.length == 1 && results[0].equals("No Weather Data Found")) {
			Assert.assertEquals("No Weather Data Found", results[0]);
		} else {
			for (String result : results) {
				String[] data = result.split("_");

				Assert.assertEquals(9, data.length);

				String cityName = data[0];
				String country = data[1];
				String currTemp = data[2];
				String tempMin = data[3];
				String tempMax = data[4];
				String latitude = data[5];
				String longitude = data[6];
				String distance = data[7];
				String isFav = data[8];

				Assert.assertTrue(Utility.isAlpha(cityName));
				Assert.assertTrue(Utility.isAlpha(country));
				Assert.assertTrue(Utility.isNumeric(currTemp));
				Assert.assertTrue(Utility.isNumeric(tempMin));
				Assert.assertTrue(Utility.isNumeric(tempMax));
				Assert.assertTrue(Utility.isFloat(latitude));
				Assert.assertTrue(Utility.isFloat(longitude));
				Assert.assertTrue(Utility.isFloat(distance));
				Assert.assertTrue(Utility.isAlpha(isFav));
			}
		}
	}

	@Test
	public void searchTestOutdoor() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Soccer");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("90089");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();

		String[] results = responseText.split("&");

		// Verify total # results
		Assert.assertTrue(results.length <= 5);
		if (results.length == 1 && results[0].equals("No Weather Data Found")) {
			Assert.assertEquals("No Weather Data Found", results[0]);
		} else {
			for (String result : results) {
				String[] data = result.split("_");
				Assert.assertEquals(9, data.length);

				String cityName = data[0];
				String country = data[1];
				String currTemp = data[2];
				String tempMin = data[3];
				String tempMax = data[4];
				String latitude = data[5];
				String longitude = data[6];
				String distance = data[7];
				String isFav = data[8];

				Assert.assertTrue(Utility.isAlpha(cityName));
				Assert.assertTrue(Utility.isAlpha(country));
				Assert.assertTrue(Utility.isNumeric(currTemp));
				Assert.assertTrue(Utility.isNumeric(tempMin));
				Assert.assertTrue(Utility.isNumeric(tempMax));
				Assert.assertTrue(Utility.isFloat(latitude));
				Assert.assertTrue(Utility.isFloat(longitude));
				Assert.assertTrue(Utility.isFloat(distance));
				Assert.assertTrue(Utility.isAlpha(isFav));
			}
		}

	}

	@Test
	public void searchTestWater() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Surfing");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Miami");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();

		String[] results = responseText.split("&");

		// Verify total # results
		Assert.assertTrue(results.length <= 5);

		if (results.length == 1 && results[0].equals("No Weather Data Found")) {
			Assert.assertEquals("No Weather Data Found", results[0]);
		} else {
			for (String result : results) {
				String[] data = result.split("_");
				Assert.assertEquals(9, data.length);

				String cityName = data[0];
				String country = data[1];
				String currTemp = data[2];
				String tempMin = data[3];
				String tempMax = data[4];
				String latitude = data[5];
				String longitude = data[6];
				String distance = data[7];
				String isFav = data[8]; // either true or false

				Assert.assertTrue(Utility.isAlpha(cityName));
				Assert.assertTrue(Utility.isAlpha(country));
				Assert.assertTrue(Utility.isNumeric(currTemp));
				Assert.assertTrue(Utility.isNumeric(tempMin));
				Assert.assertTrue(Utility.isNumeric(tempMax));
				Assert.assertTrue(Utility.isFloat(latitude));
				Assert.assertTrue(Utility.isFloat(longitude));
				Assert.assertTrue(Utility.isFloat(distance));
				Assert.assertTrue(Utility.isAlpha(isFav));
			}
		}
	}

//	@Test
//	public void addToFavorites() throws Exception {
//		when(req.getParameter("method")).thenReturn("addToFav");
//		when(req.getParameter("name")).thenReturn("Sydney");
//		// TODO shoudl the country be an abbreviation?
////        when(req.getParameter("country")).thenReturn("Australia");
//		when(req.getParameter("country")).thenReturn("AU");
//		// TODO: are we rounding Lat and Lng?
//		when(req.getParameter("lat")).thenReturn("-33.87");
//		when(req.getParameter("long")).thenReturn("151.21");
//
//		when(res.getWriter()).thenReturn(pw);
//
//		as.doPost(req, res);
//		pw.flush();
//
//		ArrayList<CityObject> after = SessionData.getFavoriteCities(req);
//		boolean foundCity = false;
//		for (CityObject c : after) {
//			if (c.getName().equals("Sydney") && c.getCountry().equals("AU")) {
//				foundCity = true;
//				break;
//			}
//		}
//		Assert.assertTrue(foundCity);
//	}

//	@Test
//	public void removeFromFavorites() throws Exception {
//		CityObject city = new CityObject("Sydney", "AU", "-33.87", "151.21");
//		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
//		SessionData.addToFav(req, city);
//		SessionData.addToFav(req, city1);
//
//		when(req.getParameter("method")).thenReturn("remFromFav");
//		when(req.getParameter("name")).thenReturn("Sydney");
//		when(req.getParameter("country")).thenReturn("AU");
//		when(req.getParameter("lat")).thenReturn("-33.87");
//		when(req.getParameter("long")).thenReturn("151.21");
//
//		when(res.getWriter()).thenReturn(pw);
//
//		as.doPost(req, res);
//		pw.flush();
//
//		ArrayList<CityObject> after = SessionData.getFavoriteCities(req);
//		boolean foundCity = false;
//		for (CityObject c : after) {
//			if (c.getName().equals("Sydney") && c.getCountry().equals("AU")) {
//				foundCity = true;
//				break;
//			}
//		}
//		Assert.assertTrue(!foundCity);
//	}

	@Test
	public void methodInvalidTest() throws Exception {
		when(req.getParameter("method")).thenReturn("hello");
		when(req.getParameter("input")).thenReturn("90089");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		// check to see that response is formatted properly
		String res = sw.toString().trim();
		String expected = "Bad AJAX Call (Servlet couldn't read the request properly).";
		Assert.assertEquals(expected, res);
	}

	@Test
	public void searchInvalidLocationTest1() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Surfing");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("jdfa8324*&@$&@");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();
		String expected = "No Weather Data Found";
		Assert.assertEquals(expected, responseText);
	}

	@Test
	public void searchInvalidLocationTest2() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Surfing");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("WHAATSFLEWE UFPDF");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();
		String expected = "No Weather Data Found";
		Assert.assertEquals(expected, responseText);
	}

	@Test
	public void searchInvalidActivityTest() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Walking in the Park");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Miami");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();
		String expected = "Error:Activity";
		Assert.assertEquals(expected, responseText);
	}

	@Test
	public void invalidCount() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Surfing");
		when(req.getParameter("count")).thenReturn("3428te2");
		when(req.getParameter("location")).thenReturn("Miami");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();
		String expected = "No Weather Data Found";
		Assert.assertEquals(expected, responseText);
	}

	@Test
	public void coveringRadiusNoneTest() throws Exception {
		when(req.getParameter("method")).thenReturn("search");
		when(req.getParameter("activity")).thenReturn("Surfing");
		when(req.getParameter("count")).thenReturn("5");
		when(req.getParameter("location")).thenReturn("Oymyakon");

		when(res.getWriter()).thenReturn(pw);

		as.doPost(req, res);
		pw.flush();

		String responseText = sw.toString().trim();
		String expected = "No Weather Data Found";
		Assert.assertEquals(expected, responseText);
	}

	@AfterClass
	public static void ActivityServletTestCleanup() {

	}
}
