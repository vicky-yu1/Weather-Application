package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import radiusSearch.OpenWeatherConnectorRadius;

/**
 * Servlet implementation class ActivityServlet
 */
@WebServlet("/ActivityServlet")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Initialize global array lists of activities
	private static Set<String> coldActivitiesSet = new HashSet<String>(new ArrayList<String>(Arrays.asList("Ice Skating",
			"Skiing", "Sledding", "Snowboarding", "Snowmobiling", "Tobogganing", "Ice Fishing", "Ice Hockey", "Curling",
			"Snow Volleyball", "Figure Skating", "Snow Shoeing", "Ski Biking", "Mountaineering", "Snow Tubing")));
	private static Set<String> outdoorActivitiesSet = new HashSet<String>(
			new ArrayList<String>(Arrays.asList("Hiking", "Camping", "Soccer", "Biking", "Rock Climbing", "Archery",
					"Cycling", "Volleyball", "Basketball", "Frisbee", "Golf", "Football", "Lacrosse", "Tennis", "Handball",
					"Dodgeball", "Bungee Jumping", "Ziplining", "Fishing", "Picnic", "Horseback Riding", "Go-Kart", "Cycling",
					"Running", "Jogging", "Hunting", "Paragliding", "Ballooning", "Hang Gliding", "Parachuting")));
	private static Set<String> waterActivitiesSet = new HashSet<String>(
			new ArrayList<String>(Arrays.asList("Swimming", "Surfing", "Wind Surfing", "Kayaking", "Canoeing", "Sailing",
					"Paddleboarding", "Water Skiing", "Jet Skiing", "Parasailing", "Rafting", "Rowing", "Wakeboarding",
					"Snorkeling", "Diving", "Water Polo", "Water Aerobics", "Water Rafting", "Aquajogging", "Kitesurfing")));

	// Process AJAX requests from front end.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parse out requested action from front end
		String requestedAction = request.getParameter("method");
		if(requestedAction.equals("addToFav")) {
			requestedAction = "isFav";
		}
		String responseText = "";
		switch (requestedAction) {
		case "initialize":
			responseText = SessionData.getInitialTempUnit(request);
			break;
		case "toggleTempUnit":
			responseText = "Toggled Temperature Unit Successfully.";
			SessionData.toggleTempUnit(request);
			break;
		case "search":
			responseText = search(request);
			break;
		case "addToFav":
			String addNameStr = request.getParameter("name");
			String addCountryStr = request.getParameter("country");
			String addLatitudeStr = request.getParameter("lat");
			String addLongitudeStr = request.getParameter("long");
			String username = request.getParameter("username");
			username = "donald";
			CityObject c = new CityObject(addNameStr, addCountryStr, addLatitudeStr, addLongitudeStr);
			SessionData.addToFav(c, username);
			responseText = "Added" + addNameStr + "to favorites successfuly";
			break;
		case "remFromFav":
			String removeNameStr = request.getParameter("name");
			String removeLatitudeStr = request.getParameter("lat");
			String removeLongitudeStr = request.getParameter("long");
			String usernameR = request.getParameter("username");
			CityObject c1 = new CityObject(removeNameStr, null, removeLatitudeStr, removeLongitudeStr);
			//usernameR = "donald";
			SessionData.removeFromFav(c1, usernameR);
			responseText = "Removed" + removeNameStr + "to favorites successfuly";
			break;
		case "isFav":
			String isFavNameStr = request.getParameter("name");
			//String isFavLatitudeStr = request.getParameter("lat");
			//String isFavLongitudeStr = request.getParameter("long");
			String usernameF = request.getParameter("username");
			//usernameR = "donald";
			boolean fav = SessionData.isFavorite(usernameF, isFavNameStr);
			responseText = "Checked if Fav: result->" + fav;
			System.out.println(responseText);
			break;
		case "getLikes":
			String usernameLikes = request.getParameter("username");
			String cityName = request.getParameter("name");
			//usernameLikes = "donald";
			int numLikes = SessionData.getLikes(cityName, usernameLikes);
			responseText = "Number of likes: " + numLikes;
			System.out.println(responseText);
			break;
		case "incrementLikes": 
			String usernameIncr = request.getParameter("username");
			String cityIncr = request.getParameter("name");
			int numLike = Integer.parseInt(request.getParameter("likes"));
			//usernameLikes = "donald";
			int likesIncr = SessionData.incrementLikes(cityIncr, usernameIncr, numLike);
			responseText = "Number of likes: " + likesIncr;
			System.out.println(responseText);
			break;
		default:
			responseText = "Bad AJAX Call (Servlet couldn't read the request properly).";
			break;
		}

		// Return the appropriate response text to front end AJAX request
		PrintWriter printWriter = response.getWriter();
		printWriter.print(responseText);
		printWriter.flush();
	}

	// Called when requested action from front end is "search"
	public String search(HttpServletRequest request) {
		// UPDATE - Parse out activity, browser location coordinates,
		// and radius from request URL
		String activityStr = request.getParameter("activity");
		String browserLatitudeStr = request.getParameter("browserLocationLatitude");
		String browserLongitudeStr = request.getParameter("browserLocationLongitude");
		String radiusStr = request.getParameter("radius");

		// Identify temperature range for radius weather call
		String minTempStr = "0";
		String maxTempStr = "0";
		if (coldActivitiesSet.contains(activityStr)) {
			minTempStr = "-1000";
			maxTempStr = "40";
		} else if (outdoorActivitiesSet.contains(activityStr)) {
			minTempStr = "40.01";
			maxTempStr = "79.99";
		} else if (waterActivitiesSet.contains(activityStr)) {
			minTempStr = "80";
			maxTempStr = "1000";
		} else {
			return "Error:Activity";
		}

		// Capture API response string
		String apiRadiusResponse;
		try {
			// UPDATE - radius weather call returns default MAX of 50 values using browser
			// location coordinates
			apiRadiusResponse = OpenWeatherConnectorRadius.radiusWeatherCall(radiusStr, browserLatitudeStr,
					browserLongitudeStr, minTempStr, maxTempStr, "50", request);
		} catch (Exception e) {
			return "No Weather Data Found";
		}

		// Error handling - API call to OpenWeatherConnectorRadius returns no data
		if (apiRadiusResponse.equals("None")) {
			apiRadiusResponse = "No Weather Data Found";
		}
		// Return API response
		return apiRadiusResponse;
	}
}