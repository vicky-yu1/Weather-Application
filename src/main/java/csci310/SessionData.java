package csci310;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class SessionData {
	public static Fav f;

	public SessionData() {
		f = new Fav();
	}

	// initialize User info
	public static String getInitialUser(HttpServletRequest request) {
		String user = (String) request.getSession().getAttribute("currentUser");
		if (user == null)
			return "No Login Info";
		return user;
	}

	// initialize TempUnit
	public static String getInitialTempUnit(HttpServletRequest request) {
		// Acquire current temperature unit
		String tempUnit = (String) request.getSession().getAttribute("tempUnit");
		// If current temperature unit not yet set, default to F°
		if (tempUnit == null) {
			request.getSession().setAttribute("tempUnit", "F");
			return "F";
		}
		return tempUnit;
	}

	// adding a new city
	@SuppressWarnings("unchecked")
	public static void addToFav(CityObject city, String username) {
		f = new Fav();

		try {
			f.addFav(username, city.getName(), city.getCountry(), city.getLat(), city.getLong());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Set User when successfully login or register
	public static void setUser(HttpServletRequest request, String user) {
		request.getSession().setAttribute("user", user);
	}

	// Set User when successfully login or register
	public static int getLikes(String city, String username) {
		f = new Fav();
		try {
			return f.getLikeCount(city, username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	// Set User when successfully login or register
	public static int incrementLikes(String city, String username, int count) {
		f = new Fav();
		try {
			return f.incrementLikes(city, username, count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	// Executed when user clicks on the temperature scale
	public static void toggleTempUnit(HttpServletRequest request) {
		String currTempUnit = (String) request.getSession().getAttribute("tempUnit");

		// Set the current temperature unit to its opposite
		if (currTempUnit == "F") {
			request.getSession().setAttribute("tempUnit", "C");
		}

		else {
			// Also triggered if unit was never toggled so currTempUnit == NULL
			request.getSession().setAttribute("tempUnit", "F");
		}
	}

	// may return either a empty arrayList or null if no fav cities,
	@SuppressWarnings("unchecked")
	public static ArrayList<CityObject> getFavoriteCities(String username) {
		try {
			System.out.println("Calling in sessionData on username: " + username);
			f = new Fav();
			return (ArrayList<CityObject>) f.getFavCities(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void removeFromFav(CityObject c, String username) {
		try {
			f.removeFav(username, c.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean isFavorite(String username, String cityName) {
		boolean foundName = false;
		try {
			f = new Fav();
			foundName = f.isFav(username, cityName);
			System.out.println("Called isFavorite on user: " + username + " and city: " + cityName + " and returned ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundName;
	}

}