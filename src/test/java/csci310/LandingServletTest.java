package csci310;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.*;


public class LandingServletTest extends Mockito{
	
	LandingServlet ls;

	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;

	
	
	
	@Before
	public void VacationServletTestSetup() {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = new MockSession();

		ls = new LandingServlet();
		when(request.getSession()).thenReturn(session);

	}
	
	//Make a post request for the initialize() function
	@Test
	public void initalizeTest() throws Exception {
		when(request.getParameter("method")).thenReturn("initialize");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
		
        pw.flush();
        
        //assert that the proper things happened
        Assert.assertEquals(sw.toString(),SessionData.getInitialTempUnit(request));
	}

	
	
	//Make a post request for the toggleTempUnit() function 
	//This will toggle four times to see if things are working properly
	@Test
	public void toggleTempUnitTest() throws Exception {

		when(request.getParameter("method")).thenReturn("toggleTempUnit");
		when(request.getSession()).thenReturn(session);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		String initialState = "F";
		String toggledState = "C";
		
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(request));
		
		//call toggle method
		ls.doPost(request, response);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(request));
		
		//call toggle method
		ls.doPost(request, response);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(request));
		
		//call toggle method
		ls.doPost(request, response);
		Assert.assertEquals(toggledState, SessionData.getInitialTempUnit(request));
		
		//call toggle method
		ls.doPost(request, response);
		Assert.assertEquals(initialState, SessionData.getInitialTempUnit(request));
	}
	
	//Make a post request for the search() function
	@Test
	public void searchCityTest() throws Exception{
		when(request.getParameter("method")).thenReturn("search");
		when(request.getParameter("input")).thenReturn("Los Angeles");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
        pw.flush();
        
        //check to see that response is formatted properly
        String[] data = sw.toString().split("_");
        Assert.assertTrue(data.length == 6);  
        for(int i = 0; i < data.length; i++)
        {
        	// Los Angeles_broken clouds_63.82_04d_34.05_-118.24
        	Assert.assertTrue(data[0].equals("Los Angeles"));
        	Assert.assertTrue(Utility.isAlpha(data[1]));
        	Assert.assertTrue(Utility.isFloat(data[2]));
        	Assert.assertTrue(Utility.isAlphanumeric(data[3]));
        	Assert.assertTrue(Utility.isFloat(data[4]));
        	Assert.assertTrue(Utility.isFloat(data[5]));
        }
	}
	
	//Make a post request for the search() function
	@Test
	public void searchZipTest() throws Exception{
		when(request.getParameter("method")).thenReturn("search");
		when(request.getParameter("input")).thenReturn("90089");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
        pw.flush();
        
        //check to see that response is formatted properly
        String[] data = sw.toString().split("_");
        Assert.assertTrue(data.length == 6);  
        for(int i = 0; i < data.length; i++)
        {
        	// Los Angeles_broken clouds_63.82_04d_34.05_-118.24
        	Assert.assertTrue(data[0].equals("Los Angeles"));
        	Assert.assertTrue(Utility.isAlpha(data[1]));
        	Assert.assertTrue(Utility.isFloat(data[2]));
        	Assert.assertTrue(Utility.isAlphanumeric(data[3]));
        	Assert.assertTrue(Utility.isFloat(data[4]));
        	Assert.assertTrue(Utility.isFloat(data[5]));
        }
	}
	
	//Make a post request for the search() function
	@Test
	public void searchInvalidTest() throws Exception{
		when(request.getParameter("method")).thenReturn("search");
		when(request.getParameter("input")).thenReturn("900de89");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
        pw.flush();
        
        //check to see that response is formatted properly
        String res = sw.toString().trim();
        Assert.assertEquals("No Weather Data Found.", res);
	}
	
	@Test
	public void searchInvalidTest2() throws Exception{
		when(request.getParameter("method")).thenReturn("search");
		when(request.getParameter("input")).thenReturn("WHY YOU GOTTA BE LIKE THIS");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
        pw.flush();
        
        //check to see that response is formatted properly
        String res = sw.toString().trim();
        Assert.assertEquals("No Weather Data Found.", res);
	}
	
	// invalid methd
	@Test
	public void methodInvalidTest() throws Exception{
		when(request.getParameter("method")).thenReturn("hello");
		when(request.getParameter("input")).thenReturn("90089");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		
		ls.doPost(request, response);
        pw.flush();
        
        //check to see that response is formatted properly
        String res = sw.toString().trim();
        String expected = "Bad Ajax Call; Servlet couldn't read the request properly.";
        Assert.assertEquals(expected, res);
	}
	
	@Test
	public void addToSearchHistoryTest() throws Exception{
		when(request.getParameter("username")).thenReturn("tester");
		when(request.getParameter("city")).thenReturn("Los Angeles");
		boolean insertWorked = ls.addToSearchHistory(request.getParameter("username"), request.getParameter("city"), "102", "https://google.com");

        Assert.assertTrue(insertWorked);
	}
	
	@Test
	public void getSearchHistorySomeResultTest() throws Exception{
		when(request.getParameter("username")).thenReturn("donald");
		when(request.getParameter("city")).thenReturn("Los Angeles");
		String resultCities = ls.getSearchHistory(request.getParameter("username"));

        Assert.assertTrue(resultCities.length() > 0);
	}
	
	@Test
	public void getSearchHistoryNoResultTest() throws Exception{
		when(request.getParameter("username")).thenReturn("invalidUser");
		when(request.getParameter("city")).thenReturn("Los Angeles");
		String resultCities = ls.getSearchHistory(request.getParameter("username"));

        Assert.assertTrue(resultCities.length() == 0);
	}
	
	@AfterClass
	public static void VacationServletTestCleanup() {
	
	}
}
