package radiusSearch;

import csci310.SessionData;
import csci310.Utility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OpenWeatherConnectorRadius {
	private static String key_API = "YOUR_API_KEY_HERE for openWeatherApi";
	private static ArrayList<String> validCityList = new ArrayList<String>();
	private static ArrayList<ImmutablePair<Integer, String>> cityDistList = new ArrayList<ImmutablePair<Integer, String>>();

	public OpenWeatherConnectorRadius() {

	}

	public static String callAPI(String latitudeStr, String longitudeStr, String count) throws Exception {
		// Calculate latitude-top, latitude-bottom, longitude-left, longitude-right
		// for bounding-box API call using latitude, longitude as origin

		// Constants for bounding-box coordinates calculations
		final double EARTHRADIUS = 3958.8; // Miles
		// double searchRadius = Double.parseDouble(SearchRadiusStr);
		double latitude = Double.parseDouble(latitudeStr);
		double longitude = Double.parseDouble(longitudeStr);

		// REMOVE URL for cities in cycle and replace with bounding box String
		String url = "http://api.openweathermap.org/data/2.5/find?lat=" + latitudeStr + "&lon=" + longitudeStr + "&cnt="
				+ count + "&units=imperial&appid=" + key_API;

		URL object = new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setRequestMethod("GET");
		// int respondCode = con.getResponseCode();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String input;
		StringBuffer response = new StringBuffer();
		while ((input = br.readLine()) != null)
			response.append(input);
		br.close();
		// System.out.println(response.toString());
		return response.toString();
	}

	public static String radiusWeatherCall(String radiusStr, String lat, String lng, String min, String max, String count,
			HttpServletRequest request) throws Exception {

		String weatherJSON = callAPI(lat, lng, count);
		System.out.println(weatherJSON);
		Gson gson = new Gson();
		TypeToken<CityAPIStuff> typeToken = new TypeToken<CityAPIStuff>() {
		};
		Type type = typeToken.getType();
		CityAPIStuff allCities = gson.fromJson(weatherJSON, type);
		int countResults = allCities.getCount();
		java.util.List<radiusSearch.List> cityList = allCities.getList();
		double lat1 = Double.parseDouble(lat);
		double lng1 = Double.parseDouble(lng);
		double minT = Double.parseDouble(min);
		double maxT = Double.parseDouble(max);
		for (int i = 0; i < countResults; i++) {
			// System.out.println("inside list loop for radius call");
			radiusSearch.List currCity = cityList.get(i);
			String cityName = currCity.getName();
			String countryName = currCity.getSys().getCountry();
			double currTemp = currCity.getMain().getTemp();
			double minTemp = currCity.getMain().getTempMin();
			double maxTemp = currCity.getMain().getTempMax();
			double currLat = currCity.getCoord().getLat();
			double currLong = currCity.getCoord().getLon();
			int crt = (int) Math.round(currTemp);
			int mnt = (int) Math.round(minTemp);
			int mxt = (int) Math.round(maxTemp);
			int distance = Utility.calcDistance(lat1, lng1, currLat, currLong);
			//int likes = SessionData.incrementLikes(cityName);
			//System.out.println("Num likes for: " + cityName + " is " + likes);
			String latStr = "" + currLat;
			String longStr = "" + currLong;
			String isFav = "false";
			String comp = cityName + "_" + latStr + "_" + longStr;
			// System.out.println(comp.replace('_', '|'));
//			if (SessionData.isFavorite(request, "donald", cityName)) {
//				// System.out.println("FAVORITES EXIST!!!!!!!!");
//				isFav = "true";
//			}
			String formattedString = cityName + "_" + countryName + "_" + crt + "_" + mnt + "_" + mxt + "_" + currLat + "_"
					+ currLong + "_" + distance + "_" + isFav;

			// Branch statements to ensure city falls within temperature range and RADIUS
			// range
			// System.out.println("ACTIVITY MINIMUM TEMPERATURE: " + minT);
			// System.out.println("CURRENT BROWSER LOCATION TEMPERATURE: " + currTemp);
			// System.out.println("ACTIVITY MAXIMUM TEMPERATURE: " + maxT);
			
			if (currTemp >= minT && currTemp <= maxT) {
				// ADDITIONAL LOGIC TO ENSURE RESULTS FALL WITHIN RADIUS RANGE
				// System.out.println("THIS DISTANCE BY KARTHIK: " + distance);
				// System.out.println("THIS RADIUS BY KARTHIK: " + Double.parseDouble(radiusStr));
				if (distance < Double.parseDouble(radiusStr)) {
					// System.out.println("Within temperature range AND radius range");
					ImmutablePair<Integer, String> cityDistPair = new ImmutablePair<Integer, String>(distance, formattedString);
					cityDistList.add(cityDistPair);
				}
			}
		}
		if (cityDistList.size() > 1) {
			ImmutablePair<Integer, String> first = cityDistList.get(0);
			cityDistList.set(0, cityDistList.get(cityDistList.size() - 1));
			cityDistList.set(cityDistList.size() - 1, first);
		}
		sortListByDist();
		makeCityList();
		// System.out.println("VALID CITY LIST SIZE: " + validCityList.size());
		String finalStr = buildMultipleResultString();

		validCityList.clear();
		cityDistList.clear();

		return finalStr;
	}

	public static final double R = 3958.8; // In kilometers

	public static double haversine(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	private static void makeCityList() {
		for (int i = 0; i < cityDistList.size(); i++) {
			String fstr = cityDistList.get(i).getRight();
			// System.out.println("HELLO THERE:  " + cityDistList.get(i).getRight());
			validCityList.add(fstr);
		}
	}

	private static String buildMultipleResultString() {
		String bigStr = "";
		if (validCityList.size() == 0) {
			bigStr = "None";
			return bigStr;
		}
		for (int i = 0; i < validCityList.size(); i++) {
			if (i == validCityList.size() - 1) {
				bigStr += validCityList.get(i);
			} else {
				bigStr += validCityList.get(i);
				bigStr += "&";
			}
		}
		return bigStr;
	}

	private static void sortListByDist() {
		for (int i = 0; i < cityDistList.size() - 1; i++) {
			for (int j = i + 1; j < cityDistList.size(); j++) {
				ImmutablePair<Integer, String> pair1 = cityDistList.get(i);
				ImmutablePair<Integer, String> pair2 = cityDistList.get(j);
				int dist1 = pair1.getLeft();
				int dist2 = pair2.getLeft();
				if (dist2 < dist1) {
					cityDistList.set(i, pair2);
					cityDistList.set(j, pair1);
				}
			}
		}
	}

	public static String TestAll() {
		CityAPIStuff city = new CityAPIStuff();
		String str = "kms";
		double a = 10.6;
		int b = 4;
		// setting everything
		Coord coord = new Coord();
		coord.setLat(a);
		coord.setLon(a);
		Clouds cloud = new Clouds();
		cloud.setAll(b);
		Wind wind = new Wind();
		wind.setDeg(b);
		wind.setGust(b);
		wind.setSpeed(a);
		Main main = new Main();
		main.setHumidity(b);
		main.setPressure(b);
		main.setTemp(a);
		main.setTempMax(a);
		main.setTempMin(a);
		Weather w = new Weather();
		w.setDescription(str);
		w.setIcon(str);
		w.setId(b);
		w.setMain(str);
		List<Weather> lw = new ArrayList<Weather>();
		lw.add(w);
		Sys sys = new Sys();
		sys.setCountry(str);
		radiusSearch.List list = new radiusSearch.List();
		list.setClouds(cloud);
		list.setCoord(coord);
		list.setDt(b);
		list.setId(b);
		list.setMain(main);
		list.setName(str);
		list.setWind(wind);
		list.setSys(sys);
		list.setWeather(lw);
		List<radiusSearch.List> lol = new ArrayList<radiusSearch.List>();
		lol.add(list);
		city.setList(lol);
		city.setCount(b);
		city.setMessage(str);
		city.setCod(str);
		// getting everything
		String gets = "fml";
		double x = 10.9;
		int y = 5;
		gets = city.getCod();
		gets = city.getMessage();
		gets = city.getList().get(0).getName();
		gets = city.getList().get(0).getSys().getCountry();
		gets = city.getList().get(0).getWeather().get(0).getDescription();
		gets = city.getList().get(0).getWeather().get(0).getIcon();
		gets = city.getList().get(0).getWeather().get(0).getMain();
		y = city.getCount();
		y = city.getList().get(0).getDt();
		y = city.getList().get(0).getId();
		y = city.getList().get(0).getMain().getHumidity();
		y = city.getList().get(0).getMain().getPressure();
		y = city.getList().get(0).getClouds().getAll();
		y = city.getList().get(0).getWeather().get(0).getId();
		x = city.getList().get(0).getCoord().getLat();
		x = city.getList().get(0).getCoord().getLon();
		x = city.getList().get(0).getMain().getTemp();
		x = city.getList().get(0).getMain().getTempMax();
		x = city.getList().get(0).getMain().getTempMin();
		x = city.getList().get(0).getWind().getSpeed();
		y = city.getList().get(0).getWind().getDeg();
		y = city.getList().get(0).getWind().getGust();
		return "Hello";
	}
}
