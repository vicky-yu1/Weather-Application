package csci310;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import citySearch.OpenWeatherConnector;
import forecastSearch.OpenWeatherConnectorForecast;

/**
 * Servlet implementation class VacationServlet
 */
@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	boolean favCitiesPopulated = false;
	
	//process AJAX requests from front end.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//figure out what information is being requested
		String method = request.getParameter("method");
		//Get the tools neccessary to reply to the AJAX request.
		PrintWriter pw = response.getWriter();

		String responseText = "";

		// return the appropriate response
		// TWO POSSIBLE RESPONSES
		// F@None = no cities in favorite list
		// F@Los Angeles_35.05_-184.3&Miami... = cities exist (and need to parse)
		if(method.equals("initialize")) {
			System.out.println("Initializing");
			responseText = SessionData.getInitialTempUnit(request);
			String favCityList = getFavCities(request);
			System.out.println(favCityList);
			responseText += "@" + favCityList;
		}

		// toggle temp unit
		else if(method.equals("toggleTempUnit")) {
			responseText = "Toggled Temperature Unit Successfully.";
			SessionData.toggleTempUnit(request);
		}
		// remove from favorites - returns the same thing as initialize
		// TWO POSSIBLE RESPONSES
		// F@None = no cities in favorite list
		// F@Los Angeles_35.05_-184.3&Miami... = cities exist (and need to parse)
		else if(method.equals("remFromFav")) {
			String name = request.getParameter("name");
			//String lat = request.getParameter("lat");
			//String lng = request.getParameter("long");
			//SessionData.removeFromFav(request, name, lat, lng);
			responseText = "Success";
			// also adds the response
			String favCityList = getFavCities(request);
			responseText += "@" + favCityList;
		}
		// citySearch (when someone clicks on a city)
		// Only one response (for the most part since everything should be set)
		// Los Angeles_broken clouds_57_04n_34.05_-118.24@57_69_03n&55_62_02n&57_61_01d&56_62_02d&55_63_10d@link1#link2#link3...
		else if(method.equals("citySearch")) {
			String name = request.getParameter("name");
			//String lat = request.getParameter("lat");
			//String lng = request.getParameter("long");
			String currweather = getCurrentWeather(name);
			
			if (currweather.equals("No Weather Data Found.")) {
				responseText = "No Weather Data Found." + "@None@None";
			}
			else {
				String data[] = currweather.split("_");
				System.out.println("Current Weather is: "+currweather);
				String lat = data[4];
				String lng = data[5];
				System.out.println("lat: "+ lat);
				System.out.println("lng: "+ lng);
				responseText += currweather;
				String forecast = getForecast(lat, lng);
				if (forecast.equals("No Weather Data Found.")) {
					responseText = "No Weather Data Found." + "@No Weather Data Found.@None";
				}
				else {
					responseText += "@" + forecast;
					CityObject currCity = new CityObject(name, "US", lat, lng);
					if (currCity == null) {
						responseText = "None@None@No Weather Data Found.";
					}
					else {
						String pics = getPhotos(currCity);
						responseText += "@" + pics;
					}
				}
			}
		}
		// error
		else {
			responseText = "Bad Ajax Call; Servlet couldn't read the request properly.";
		}

		//return the response.
		pw.print(responseText);
		pw.flush();


	}

	private String getFavCities(HttpServletRequest request) {
		String username = request.getParameter("username");
		//username = "donald";
//		if (SessionData.cityListIsEmpty(request)) {
//			return "None";
//		}
		ArrayList<CityObject> allCities = SessionData.getFavoriteCities(username);
		System.out.println(allCities);
		ArrayList<String> cityList = new ArrayList<String>();
		String returnStr = "";
		String eachCity = "";
		for (int i = 0; i < allCities.size(); i++) {
			CityObject currCity = allCities.get(i);
			String cityName = currCity.getName();
			String lat = currCity.getLat();
			String lng = currCity.getLong();
			eachCity = cityName + "_" + lat + "_" + lng;
			cityList.add(eachCity);
		}
		Collections.sort(cityList);
		for (int i = 0; i < cityList.size(); i++) {
			if (i == cityList.size()-1) {
				returnStr += cityList.get(i);
			}
			else {
				returnStr += cityList.get(i) + "&";
			}
		}
		return returnStr;
	}
	
	private String getCurrentWeather(String name) {
		String notFound = "No Weather Data Found.";
		String returnStr = "";
		returnStr = OpenWeatherConnector.currWeatherCall(name, true);
		if(returnStr.equals(""))
			return notFound;
		return returnStr;
	}
	
	private String getForecast(String lat, String lng) {
		String notFound = "No Weather Data Found.";
		String returnStr = "";
		try {
			returnStr = OpenWeatherConnectorForecast.fiveDayForecastCall(lat, lng);
			System.out.println(returnStr);
		}
		catch (Exception e) {
			return notFound;
		}
		return returnStr;
	}
	
// 	private CityObject getCityObj(String name, String lat, String lng, HttpServletRequest request) {
// 		String username = request.getParameter("username");
// 		ArrayList<CityObject> favCities = SessionData.getFavoriteCities(username);
// 		CityObject currCity = null;
// //		if (favCities.size() == 1) {
// //			currCity = favCities.get(0);
// //		}
// 		//else {
// 			for (int i = 0; i < favCities.size(); i++) {
// 				String cname = favCities.get(i).getName();
// 				String clat = favCities.get(i).getLat();
// 				String clng = favCities.get(i).getLong();
// 				if (cname.equals(name) && clat.equals(lat) && clng.equals(lng)) {
// 					currCity = favCities.get(i);
// 					break;
// 				}
// 			}
// 		//}
// 		return currCity;
// 	}
	
	private String getPhotos(CityObject city) {
		String returnStr = "";
		ArrayList<String> links = city.getPhotoLinks();
		for (int i = 0; i < links.size(); i++) {
			returnStr += "#" + links.get(i);
		}
		return returnStr;
	}

}

