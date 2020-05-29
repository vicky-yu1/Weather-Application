package csci310;

public class Utility {

	public Utility() {

	}

	// calculates the distance in miles by taking in latitude and longitude from 2
	// locations
	// if valid coordinates, return 0 or greater
	// if invalid coordinates, return -1
	public static int calcDistance(double lat1, double lon1, double lat2, double lon2) {
		// check for valid latitude and longitude
		// latitude -90 ~ +90
		// longitude -180 ~ +180
		if (lat1 > 90 || lat1 < -90 || lat2 > 90 || lat2 < -90 || lon1 > 180 || lon1 < -180 || lon2 > 180 || lon2 < -180) {
			return -1;
		}

		// use haversine formula

		// difference in latitude and longitude in radians
		double dLat = (lat2 - lat1) * (Math.PI / 180.0);
		double dLon = (lon2 - lon1) * (Math.PI / 180.0);

		// latitude in radians
		lat1 = lat1 * (Math.PI / 180.0);
		lat2 = lat2 * (Math.PI / 180.0);

		double a = (Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2));
		double c = 2 * Math.asin(Math.sqrt(a));

		// earth’s radius in km
		int r = 6371;

		double distanceKM = (r * c);

		// convert kilometer to miles
		float distanceMiles = (float) (0.621371 * distanceKM);
		int miles = Math.round(distanceMiles);
		return miles;

	}

	public static int convertToC(double temp) {
		float celsius = (float) ((temp - 32.00) * (5.00 / 9.00));
		int ans = Math.round(celsius);
		return ans;
	}

	public static int convertToF(double temp) {
		float fahren = (float) ((temp * (9.00 / 5.00)) + 32.0);
		int ans = Math.round(fahren);
		return ans;
	}

	// checks if the string consists of only digits
	public static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// checks if the string doesn't have any numbers in it.
	public static boolean isAlpha(String s) {
		if (s == null) {
			return false;
		}
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char letter = s.charAt(i);
			if (Character.isDigit(letter)) {
				return false;
			}
		}
		return true;
	}

	// checks if string is alphanumeric
	public static boolean isAlphanumeric(String s) {
		if (s == null) {
			return false;
		}
		int len = s.length();
		for (int i = 0; i < len; i++) {
			boolean isAlpha = Character.isLetter(s.charAt(i));
			boolean isDig = Character.isDigit(s.charAt(i));
			boolean isEither = isAlpha | isDig;
			if (!isEither) {
				return false;
			}
		}
		return true;
	}

	// checks if the string is a float
	public static boolean isFloat(String s) {
		try {
			Float.parseFloat(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
