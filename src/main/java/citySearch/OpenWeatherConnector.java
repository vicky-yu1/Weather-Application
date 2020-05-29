package citySearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OpenWeatherConnector {
	private static String key_API = "YOUR_API_KEY_HERE for openWeatherApi"; 

	public OpenWeatherConnector() {
	}

	public static String currWeatherCall(String input, Boolean searchMode) {
		String returnStr = "";
		// Searching by city name
		if (searchMode == true) {
			String weatherJSON = "";
			String urlInput = formatStrURL(input);
			try {
				weatherJSON = cityAPICall(urlInput);
			} catch (Exception e) {
				weatherJSON = "empty";
			}
			if (weatherJSON.equals("empty")) {
				return returnStr;
			}

			Gson gson = new Gson();
			TypeToken<CityAPIStuff> typeToken = new TypeToken<CityAPIStuff>() {
			};
			Type type = typeToken.getType();
			CityAPIStuff currCity = gson.fromJson(weatherJSON, type);
			String cityName = currCity.getName();
			double currTemp = currCity.getMain().getTemp();
			double currLat = currCity.getCoord().getLat();
			double currLong = currCity.getCoord().getLon();
			int cTemp = Math.round((float) currTemp);
			List<Weather> cc = currCity.getWeather();
			Weather ws = cc.get(0);
			String icon = ws.getIcon();
			String descrip = ws.getDescription();
			returnStr = cityName + "_" + descrip + "_" + cTemp + "_" + icon + "_" + currLat + "_" + currLong;
			return returnStr;
		}
		// Searching by ZIP Code
		else {
			String weatherJSON = "";
			String urlInput = formatStrURL(input);
			try {
				weatherJSON = zipAPICall(urlInput);
			} catch (Exception e) {
				weatherJSON = "empty";
			}
			if (weatherJSON.equals("empty")) {
				return returnStr;
			}

			Gson gson = new Gson();
			TypeToken<CityAPIStuff> typeToken = new TypeToken<CityAPIStuff>() {
			};
			Type type = typeToken.getType();
			CityAPIStuff currCity = gson.fromJson(weatherJSON, type);
			String cityName = currCity.getName();
			double currTemp = currCity.getMain().getTemp();
			double currLat = currCity.getCoord().getLat();
			double currLong = currCity.getCoord().getLon();
			int cTemp = Math.round((float) currTemp);
			List<Weather> cc = currCity.getWeather();
			Weather ws = cc.get(0);
			String icon = ws.getIcon();
			String descrip = ws.getDescription();
			ws.getId();
			ws.getMain();
			returnStr = cityName + "_" + descrip + "_" + cTemp + "_" + icon + "_" + currLat + "_" + currLong;
			return returnStr;
		}
	}

	// if searchMode is true, the city is in actual name, else in zip code
	private static String cityAPICall(String city) throws Exception {
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=imperial&appid=" + key_API;
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
		return response.toString();
	}

	// if searchMode is true, the city is in actual name, else in zip code
	private static String zipAPICall(String zipcode) throws Exception {
		String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + zipcode + "&units=imperial&appid=" + key_API;
		URL object = new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String input;
		StringBuffer response = new StringBuffer();
		while ((input = br.readLine()) != null)
			response.append(input);
		br.close();
		return response.toString();
	}

	// formatting the string for URL safety
	private static String formatStrURL(String input) {
		String space = "%20";
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ') {
				result += space;
			} else {
				if (input.charAt(i) == '.') {
					continue;
				} else {
					result += input.charAt(i);
				}
			}
		}
		return result;
	}

	public static String TestMore() {
		CityAPIStuff currCity = new CityAPIStuff();
		double num = 99.99;
		int x = 10;
		String wtf = "WHY";
		// creating and setting new stuff
		Coord nCoord = new Coord();
		nCoord.setLat(num);
		nCoord.setLon(num);
		Clouds nClouds = new Clouds();
		nClouds.setAll(x);
		Main nMain = new Main();
		nMain.setHumidity(x);
		nMain.setPressure(x);
		nMain.setTemp(num);
		nMain.setTempMax(num);
		nMain.setTempMin(num);
		Sys nSys = new Sys();
		nSys.setCountry(wtf);
		nSys.setId(x);
		nSys.setSunrise(x);
		nSys.setSunset(x);
		nSys.setMessage(num);
		nSys.setType(x);
		Weather nWeather = new Weather();
		nWeather.setDescription(wtf);
		nWeather.setIcon(wtf);
		nWeather.setId(x);
		nWeather.setMain(wtf);
		List<Weather> lw = new ArrayList<Weather>();
		lw.add(nWeather);
		Wind nWind = new Wind();
		nWind.setDeg(x);
		nWind.setGust(num);
		nWind.setSpeed(num);
		// setting currCity to stuff
		currCity.setClouds(nClouds);
		currCity.setCoord(nCoord);
		currCity.setBase(wtf);
		currCity.setCod(x);
		currCity.setDt(x);
		currCity.setMain(nMain);
		currCity.setName(wtf);
		currCity.setSys(nSys);
		currCity.setId(x);
		currCity.setVisibility(x);
		currCity.setWind(nWind);
		currCity.setWeather(lw);
		currCity.getBase();
		currCity.getSys().getCountry();
		currCity.getSys().getMessage();
		currCity.getWind().getGust();
		currCity.getWind().getSpeed();
		currCity.getMain().getTempMax();
		currCity.getMain().getTempMin();
		currCity.getCod();
		currCity.getDt();
		currCity.getId();
		currCity.getVisibility();
		currCity.getClouds().getAll();
		currCity.getMain().getHumidity();
		currCity.getMain().getPressure();
		currCity.getSys().getId();
		currCity.getSys().getSunrise();
		currCity.getSys().getSunset();
		currCity.getSys().getType();
		currCity.getWind().getDeg();
		return "Hello";
	}
}
