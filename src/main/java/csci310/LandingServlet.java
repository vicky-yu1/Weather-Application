package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import citySearch.OpenWeatherConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Servlet implementation class LandingServlet
 */
@WebServlet("/LandingServlet")
public class LandingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String username;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/weather?"
			+ "cloudSqlInstance=weatherapp-272900:us-west2:maindb&socketFactory=com.google.cloud.sql.mysql.SocketFactory"
			+ "&useSSL=false&requireSSL=false&user=grouph&password=password";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//figure out what information is being requested
		String method = request.getParameter("method");

		//Get the tools neccessary to reply to the AJAX request.
		PrintWriter pw = response.getWriter();
		
		String responseText;
		//return the appropriate response
		if(method.equals("initialize"))
		{
			responseText = SessionData.getInitialTempUnit(request);
			username = request.getParameter("username");
		}
		else if(method.equals("toggleTempUnit"))
		{
			responseText = "Toggled Temperature Unit Successfully.";
			SessionData.toggleTempUnit(request);
		}
		else if (method.equals("search")) {
			String input = request.getParameter("input");
			responseText = search(input);
		} else if (method.equals("getHistory")) {
			responseText = getSearchHistory(username);

			// System.out.println("RESPONSETEXT: " + responseText);
		}
		else
		{
			responseText = "Bad Ajax Call; Servlet couldn't read the request properly.";
		}
		
		//return the response.
		pw.print(responseText);
		pw.flush();
	}
	
	private String search(String input) {
		String notFound = "No Weather Data Found.";
		String returnStr = "";
		if (Utility.isAlpha(input)) {
			returnStr = OpenWeatherConnector.currWeatherCall(input, true);
		}
		else if (Utility.isNumeric(input)) {
			returnStr = OpenWeatherConnector.currWeatherCall(input, false);
		}
		else {
			return notFound;
		}
		if(returnStr.equals(""))
			return notFound;
		
		String data[] = returnStr.split("_");
		addToSearchHistory(username, data[0], data[2], "http://openweathermap.org/img/wn/" + data[3] + "@2x.png");
		return returnStr;
	}
	
	protected boolean addToSearchHistory(String username, String cityName, String fahrenheit, String iconURL) {
		// System.out.println("Adding to search history: username-> " + username + " with city-> " + cityName + " temperature in F = " + fahrenheit + ", iconURL = " + iconURL);
		Connection conn = null;
		Statement st = null;

		try {
			conn = DriverManager.getConnection(CREDENTIALS_STRING);
			st = conn.createStatement();
			st.executeUpdate("INSERT into SearchHistory(username, cityName, temperature, iconURL) values ('" + username + "','" + cityName + "', '" + fahrenheit + "','" + iconURL +  "')");
			return true;
		} catch (SQLException sqle) {
			// System.out.println(sqle.getMessage());
			return false;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				// System.out.println(sqle.getMessage());
			}
		}
	}
	
	protected String getSearchHistory(String username) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String ans = "";
		try {
			conn = DriverManager.getConnection(CREDENTIALS_STRING);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM SearchHistory WHERE username='" + username + "'");
			ArrayList<String> cities = new ArrayList<String>();
			while(rs.next()) {
				String currCity = rs.getString("cityName") + "_" + rs.getString("temperature") + "_" + rs.getString("iconURL") + "&";
				cities.add(currCity);
			}
			Collections.reverse(cities);
			for(int i = 0; i < 4; i++) {
				if(i >= cities.size()) {
					break;
				}
				ans += cities.get(i);
			}
			System.out.println(ans);
			return ans;
			
		} catch (SQLException sqle) {
			// System.out.println(sqle.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				// System.out.println(sqle.getMessage());
			}
		}
		return "";
	}

}