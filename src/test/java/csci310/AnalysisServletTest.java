package csci310;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.*;


public class AnalysisServletTest extends Mockito {
	
	AnalysisServlet as;
	HttpServletRequest req;
	HttpServletResponse res;
	HttpSession session;
	ArrayList<CityObject> dummyFaves;

	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);

//	@Before
//	public void AnalysisServletTestSetup() {
//		req = mock(HttpServletRequest.class);
//		res = mock(HttpServletResponse.class);
//		session = new MockSession();	//testing class that mimics HttpSession
//		when(req.getSession()).thenReturn(session);
//		as = new AnalysisServlet();
//
//		dummyFaves = new ArrayList<CityObject>();
//		CityObject dummyCity1 = new CityObject("Miami", "US", "25.77", "-80.19");
//		CityObject dummyCity2 = new CityObject("Chicago", "US", "41.88", "-87.62");
//		CityObject dummyCity3 = new CityObject("Washington DC", "US", "38.91", "-77.04");
//		SessionData.addToFav(req, dummyCity1);
//		SessionData.addToFav(req, dummyCity2);
//		SessionData.addToFav(req, dummyCity3);
//	}
	
//	//Make a post request for the initialize() function
//	@Test
//	public void initializeTest() throws Exception {
//		when(req.getParameter("method")).thenReturn("initialize");
//		when(res.getWriter()).thenReturn(pw);
//		as.doPost(req, res);
//        pw.flush();
//        
//        //assert that the information passed to front end matches whats store in backend
//        
//        //check to see that response is formatted properly
//        String[] data = sw.toString().trim().split("@");
//        Assert.assertEquals(data[0], SessionData.getInitialTempUnit(req));
//        Assert.assertTrue(data.length <= 3);
//        if (data[1].equals("None")) {
//        	int cityListSize = SessionData.getFavoriteCities(req).size();
//        	Assert.assertEquals(0, cityListSize);
//        }
//        else {
//        	int cityListSize = SessionData.getFavoriteCities(req).size();
//        	Assert.assertTrue(cityListSize > 0);
//        	String[] cityList = data[1].split("&");
//        	for (String city : cityList) {
//        		String info[] = city.split("_");
//        		String name = info[0];
//        		String lat = info[1];
//        		String lng = info[2];
//        		Assert.assertTrue(Utility.isAlpha(name));
//        		Assert.assertTrue(Utility.isFloat(lat));
//        		Assert.assertTrue(Utility.isFloat(lng));
//        	}
//        }
//	}
//	
//	@Test
//	public void emptyFavList() throws Exception{
//		SessionData.removeFromFav(req, "Miami", "25.77", "-80.19");
//		SessionData.removeFromFav(req, "Chicago", "41.88", "-87.62");
//		SessionData.removeFromFav(req, "Washington DC", "38.91", "-77.04");
//		when(req.getParameter("method")).thenReturn("initialize");
//		when(res.getWriter()).thenReturn(pw);
//		as.doPost(req, res);
//        pw.flush();
//        
//        //assert that the information passed to front end matches whats store in backend
//        
//        //check to see that response is formatted properly
//        String[] data = sw.toString().trim().split("@");
//        Assert.assertTrue(data.length == 2);
//        Assert.assertEquals("None", data[1]);
//	}

	//Make a post request for the toggleTempUnit() function 
	//This will toggle four times to see if things are working properly
	// @Test
	public void toggleTempUnitTest() throws Exception {

		when(req.getParameter("method")).thenReturn("toggleTempUnit");
		when(req.getSession()).thenReturn(session);
		when(res.getWriter()).thenReturn(pw);
		
		//here are the two possible states of session
		String initialState = "F";
		String toggledState = "C";
		
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));
		
		//call toggle method
		as.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));
		
		//call toggle method
		as.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));
		
		//call toggle method
		as.doPost(req, res);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(req));
		
		//call toggle method
		as.doPost(req, res);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(req));
	}

//	@Test
//	public void removeFromFavoritesTest() throws Exception{
//		when(req.getParameter("method")).thenReturn("remFromFav");
//		when(req.getParameter("name")).thenReturn("Los Angeles");
//		when(req.getParameter("lat")).thenReturn("34.05");
//		when(req.getParameter("long")).thenReturn("-118.24");
//		when(res.getWriter()).thenReturn(pw);
//		as.doPost(req, res);
//		
//		pw.flush();
//		String cityName = req.getParameter("name");
//		String lat = req.getParameter("lat");
//		String lng = req.getParameter("long");
////		Assert.assertTrue(SessionData.isFavorite(req, cityName, lat, lng));
////		SessionData.removeFromFav(req, cityName, lat, lng);
//		Assert.assertFalse(SessionData.isFavorite(req, cityName, lat, lng));
//	}

	// TODO
	// Make a post request for the search() function using CityName.
	// @Test
	public void getSelectedCityInfoTest() throws Exception {

		when(req.getParameter("method")).thenReturn("citySearch");
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("name")).thenReturn("Miami");
		when(req.getParameter("lat")).thenReturn("25.77");
		when(req.getParameter("long")).thenReturn("-80.19");
		when(res.getWriter()).thenReturn(pw);
		
		as.doPost(req, res);
		pw.flush();        
        
        //check to see that response is formatted properly
        String[] parts = sw.toString().trim().split("@");
		Assert.assertEquals(3, parts.length);
		// checking current weather data is correct
		String[] currData = parts[0].split("_");
		Assert.assertEquals(6, currData.length);
		// checking forecast is correct
		String[] forecastData = parts[1].split("&");
		Assert.assertTrue(forecastData.length >= 5);
		for (String day : forecastData) {
			String[] dData = day.split("_");
			String min = dData[0];
			String max = dData[1];
			String icon = dData[2];
			Assert.assertTrue(Utility.isNumeric(min));
			Assert.assertTrue(Utility.isNumeric(max));
			Assert.assertTrue(Utility.isAlphanumeric(icon));
		}
		// checking photolinks are correct
		String[] picList = parts[2].split("#");
		Assert.assertTrue(picList.length <= 6);
	}
	
//	@Test
//	public void getSelectedCityInfo1CityTest() throws Exception {
//		SessionData.removeFromFav(req, "Chicago", "41.88", "-87.62");
//		SessionData.removeFromFav(req, "Washington DC", "38.91", "-77.04");
//		when(req.getParameter("method")).thenReturn("citySearch");
//		when(req.getSession()).thenReturn(session);
//		when(req.getParameter("name")).thenReturn("Miami");
//		when(req.getParameter("lat")).thenReturn("25.77");
//		when(req.getParameter("long")).thenReturn("-80.19");
//		when(res.getWriter()).thenReturn(pw);
//		
//		as.doPost(req, res);
//		pw.flush();        
//        
//        //check to see that response is formatted properly
//        String[] parts = sw.toString().trim().split("@");
//		Assert.assertEquals(3, parts.length);
//		// checking current weather data is correct
//		String[] currData = parts[0].split("_");
//		Assert.assertEquals(6, currData.length);
//		// checking forecast is correct
//		String[] forecastData = parts[1].split("&");
//		Assert.assertTrue(forecastData.length >= 5);
//		for (String day : forecastData) {
//			String[] dData = day.split("_");
//			String min = dData[0];
//			String max = dData[1];
//			String icon = dData[2];
//			Assert.assertTrue(Utility.isNumeric(min));
//			Assert.assertTrue(Utility.isNumeric(max));
//			Assert.assertTrue(Utility.isAlphanumeric(icon));
//		}
//		// checking photolinks are correct
//		String[] picList = parts[2].split("#");
//		Assert.assertTrue(picList.length <= 6);
//	}
//	
	// @Test
	public void methodInvalidCityTest() throws Exception{
		when(req.getParameter("method")).thenReturn("citySearch");
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("name")).thenReturn("OMG WHAT THAT IS CRAZY");
		when(req.getParameter("lat")).thenReturn("25.77");
		when(req.getParameter("long")).thenReturn("-80.19");
		when(res.getWriter()).thenReturn(pw);
		
		as.doPost(req, res);
		pw.flush();        
        
        //check to see that response is formatted properly
        String[] parts = sw.toString().trim().split("@");
        Assert.assertEquals(3, parts.length);
        String expected = "No Weather Data Found.";
        Assert.assertEquals(expected, parts[0]);
	}
	
	// @Test
	public void methodInvalidCoordTest() throws Exception{
		when(req.getParameter("method")).thenReturn("citySearch");
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("name")).thenReturn("Miami");
		when(req.getParameter("lat")).thenReturn("25.e77");
		when(req.getParameter("long")).thenReturn("-802321.9");
		when(res.getWriter()).thenReturn(pw);
		
		as.doPost(req, res);
		pw.flush();        
        
        //check to see that response is formatted properly
        String[] parts = sw.toString().trim().split("@");
        Assert.assertEquals(3, parts.length);
        String expected = "No Weather Data Found.";
        Assert.assertEquals(expected, parts[1]);
	}

	// invalid method
	// @Test
	public void methodInvalidTest() throws Exception{
		when(req.getParameter("method")).thenReturn("hello");
		when(req.getParameter("input")).thenReturn("90089");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(res.getWriter()).thenReturn(pw);
		
		as.doPost(req, res);
        pw.flush();
        
        //check to see that response is formatted properly
        String res = sw.toString().trim();
        String expected = "Bad Ajax Call; Servlet couldn't read the request properly.";
        Assert.assertEquals(expected, res);
	}
	
//	@Test
//	public void extensiveCityObjTest1() throws Exception{
//		SessionData.removeFromFav(req, "Miami", "25.77", "-80.19");
//		CityObject city = new CityObject("Miamim", "US", "25.77", "-80.19");
//		SessionData.addToFav(req, city);
//		when(req.getParameter("method")).thenReturn("citySearch");
//		when(req.getSession()).thenReturn(session);
//		when(req.getParameter("name")).thenReturn("Miami");
//		when(req.getParameter("lat")).thenReturn("25.77");
//		when(req.getParameter("long")).thenReturn("-80.19");
//		when(res.getWriter()).thenReturn(pw);
//		
//		as.doPost(req, res);
//		pw.flush();        
//        
//        //check to see that response is formatted properly
//        String[] parts = sw.toString().trim().split("@");
//        Assert.assertEquals(3, parts.length);
//        String expected = "No Weather Data Found.";
//        Assert.assertEquals(expected, parts[2]);
//	}
//	
//	@Test
//	public void extensiveCityObjTest2() throws Exception{
//		SessionData.removeFromFav(req, "Miami", "25.77", "-80.19");
//		CityObject city = new CityObject("Miami", "US", "24.77", "-80.19");
//		SessionData.addToFav(req, city);
//		when(req.getParameter("method")).thenReturn("citySearch");
//		when(req.getSession()).thenReturn(session);
//		when(req.getParameter("name")).thenReturn("Miami");
//		when(req.getParameter("lat")).thenReturn("25.77");
//		when(req.getParameter("long")).thenReturn("-80.19");
//		when(res.getWriter()).thenReturn(pw);
//		
//		as.doPost(req, res);
//		pw.flush();        
//        
//        //check to see that response is formatted properly
//        String[] parts = sw.toString().trim().split("@");
//        Assert.assertEquals(3, parts.length);
//        String expected = "No Weather Data Found.";
//        Assert.assertEquals(expected, parts[2]);
//	}
//	
//	@Test
//	public void extensiveCityObjTest3() throws Exception{
//		SessionData.removeFromFav(req, "Miami", "25.77", "-80.19");
//		CityObject city = new CityObject("Miami", "US", "25.77", "-80.9");
//		SessionData.addToFav(req, city);
//		when(req.getParameter("method")).thenReturn("citySearch");
//		when(req.getSession()).thenReturn(session);
//		when(req.getParameter("name")).thenReturn("Miami");
//		when(req.getParameter("lat")).thenReturn("25.77");
//		when(req.getParameter("long")).thenReturn("-80.19");
//		when(res.getWriter()).thenReturn(pw);
//		
//		as.doPost(req, res);
//		pw.flush();        
//        
//        //check to see that response is formatted properly
//        String[] parts = sw.toString().trim().split("@");
//        Assert.assertEquals(3, parts.length);
//        String expected = "No Weather Data Found.";
//        Assert.assertEquals(expected, parts[2]);
//	}
	
	@AfterClass
	public static void AnalysisServletTestCleanup() {
	}
}