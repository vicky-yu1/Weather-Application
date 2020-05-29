package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import radiusSearch.OpenWeatherConnectorRadius;

/**
 * Servlet implementation class VacationServlet
 */
@WebServlet("/VacationServlet")
public class VacationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Process AJAX requests from front end.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Parse out requested action from front end
		String requestedAction = request.getParameter("method");
		String responseText = "";
		if(requestedAction.equals("addToFav")) {
			requestedAction = "isFav";
		}
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
			case "addFav":
				String addNameStr = request.getParameter("name");
				String addCountryStr = request.getParameter("country");
				String addLatitudeStr = request.getParameter("lat");
				String addLongitudeStr = request.getParameter("long");
				String username = request.getParameter("username");
				CityObject c = new CityObject(addNameStr, addCountryStr, addLatitudeStr, addLongitudeStr);
				SessionData.addToFav(c, username);
				responseText = "Added" + addNameStr + "to favorites successfuly for "+username;
				System.out.println(responseText);
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
				String usernameisFav = request.getParameter("username");
				//usernameR = "donald";
				boolean fav = SessionData.isFavorite(usernameisFav, isFavNameStr);
				responseText = "Checked if Fav: result->" + fav;
				System.out.println(responseText);
				break;
			case "getLikes":
				String usernameLikes = request.getParameter("username");
				String cityName = request.getParameter("name");
				//usernameLikes = "donald";
				int numLikes = SessionData.getLikes(cityName, usernameLikes);
				responseText = String.valueOf(numLikes);
				System.out.println(responseText);
				break;
			case "incrementLikes": 
				String usernameIncr = request.getParameter("username");
				String cityIncr = request.getParameter("name");
				int numLike = Integer.parseInt(request.getParameter("likes"));
				//usernameLikes = "donald";
				int likesIncr = SessionData.incrementLikes(cityIncr, usernameIncr, numLike);
				responseText = String.valueOf(likesIncr);
				System.out.println(cityIncr+ " update to :"+responseText +" likes");
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

  // Called when requested action from vacation page frontend is "search"
	public String search(HttpServletRequest request) {
		// UPDATE - Parse out temperature range, browser location coordinates, and
		// radius from AJAX request URL from front end
		String minTempStr = request.getParameter("minTemp");
		String maxTempStr = request.getParameter("maxTemp");
		String browserLatitudeStr = request.getParameter("browserLocationLatitude");
		String browserLongitudeStr = request.getParameter("browserLocationLongitude");
		String radiusStr = request.getParameter("radius");

		// ERROR CHECKING - temperature range
		if (Double.parseDouble(minTempStr) > Double.parseDouble(maxTempStr)) {
			// Return error message to response text
			return "Error:TempRange";
		}

		// Capture API response string
		String apiRadiusResponse;
		try {
			// UPDATE - radius weather call returns default MAX of 50 values using browser
			// location coordinates
			apiRadiusResponse = OpenWeatherConnectorRadius.radiusWeatherCall(radiusStr, browserLatitudeStr, browserLongitudeStr, minTempStr, maxTempStr, "50", request);
		} catch (Exception e) {
			return "No Weather Data Found.";
		}

		// Error handling - API call to OpenWeatherConnectorRadius returns no data
		if (apiRadiusResponse.equals("None")) {
			return "No Weather Data Found.";
		}
		// Return API response
		return apiRadiusResponse;

	}

}