package forecastSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.lang.reflect.Type; 

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OpenWeatherConnectorForecast {
	private static String key_API = "YOUR_API_KEY_HERE for openWeatherApi";
	private static ArrayList<String> datesH = new ArrayList<String>();
	private static ArrayList<String> datesL = new ArrayList<String>();
	private static ArrayList<Double> lowTemp = new ArrayList<Double>();
	private static ArrayList<Double> highTemp = new ArrayList<Double>();
	private static ArrayList<String> iconList = new ArrayList<String>();
	
	public OpenWeatherConnectorForecast() {
		
	}
	
//	public static void main(String[] args) {
//		try {
//			String lat = "34.05";
//			String lng = "-118.24"; 
//			String finStr = fiveDayForecastCall(lat, lng);
//			System.out.println(finStr);
//		} catch (Exception e) {
//			System.out.println("Forecast Call Error");
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//		}
//	}
	
	public static String fiveDayForecastCall(String lat, String lng) throws Exception {
		String weatherJSON;
		weatherJSON = apiCall(lat, lng);
		// System.out.println(weatherJSON);
		Gson gson = new Gson();
		TypeToken<CityAPIStuff> typeToken = new TypeToken<CityAPIStuff>() {};
		Type type = typeToken.getType();
		CityAPIStuff currCity = gson.fromJson(weatherJSON, type);
		int countStuff = currCity.getCnt();
		List<forecastSearch.List> allInfo = currCity.getList();
		for (int i = 0; i < countStuff; i++) {
			forecastSearch.List hourReport = allInfo.get(i);
			List<Weather> wlist = hourReport.getWeather();
			Weather currW = wlist.get(0);
			String icon = currW.getIcon();
			double minTemp = hourReport.getMain().getTempMin();
			double maxTemp = hourReport.getMain().getTempMax();
			double currTemp = hourReport.getMain().getTemp();
			long dateTime = hourReport.getDt();
			long millis = dateTime * 1000;
			Date date = new Date(millis);
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy", Locale.ENGLISH);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			String formattedDate = sdf.format(date);
//			System.out.println("i = " + i + " = " + formattedDate); // Tuesday,November 1,2011 12:00,AM
//			System.out.println("curTemp = " + currTemp);
//			System.out.println("minTemp = " + minTemp);
//			System.out.println("maxTemp = " + maxTemp);
//			System.out.println();
			if (minTemp < maxTemp) {
				addLowTemp(formattedDate, minTemp);
				addHighTemp(formattedDate, maxTemp);
			}
			else {
				addLowTemp(formattedDate, currTemp);
				addHighTemp(formattedDate, currTemp);
			}
			iconList.add(icon);
		}
		String currDate = new SimpleDateFormat("EEEE,MMMM d,yyyy", Locale.ENGLISH).format(new Date());
//		removeExtraDate(currDate);
		// System.out.println(currDate);
		// System.out.print("Low Temp Forecast: ");
		printLowTemps();
		// System.out.print("High Temp Forecast: ");
		printHighTemps();
		String finStr = make1String();
		return finStr;
	}
	
	// call to API and gets a JSON string in response
	public static String apiCall(String lat , String lng) throws Exception { 
		String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lng + "&units=imperial&appid=" + key_API;
		URL object = new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setRequestMethod("GET");
		int respondCode = con.getResponseCode();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String input;
		StringBuffer response = new StringBuffer();
		while ((input = br.readLine()) != null)
			response.append(input);
		br.close();
		return response.toString();
	}
	
	private static String make1String() {
		String retStr = "";
		for (int i = 0; i < datesH.size(); i++) {
			int high = (int) Math.round(highTemp.get(i));
			int low = (int) Math.round(lowTemp.get(i));
			if (i == datesH.size()-1) {
				retStr += low + "_" + high + "_" + iconList.get(i);
			}
			else {
				retStr += low + "_" + high + "_" + iconList.get(i) + "&";
			}
		}
		return retStr;
	}
	
	// internal function for checking high and lows of each day
	private static void addHighTemp(String date, double temp) {
		// date is found
		if (datesH.size() > 0) {
			boolean foundDate = false;
			int dateIndex = -1;
			// trying to find date
			for (int i = 0; i < datesH.size(); i++) {
				String currDate = datesH.get(i);
				if (currDate.contentEquals(date)) {
					foundDate = true;
					dateIndex = i;
					break;
				}
			}
			// found date
			if (foundDate) {
				double currTemp = highTemp.get(dateIndex);
				double max = Math.max(currTemp, temp);
				highTemp.set(dateIndex, max);
			}
			// did not find date
			else {
				datesH.add(date);
				highTemp.add(temp);
			}
		}
		// make a new date key
		else {
			datesH.add(date);
			highTemp.add(temp);
		}
	}
	
	private static void addLowTemp(String date, double temp) {
		// date is found
		if (datesL.size() > 0) {
			boolean foundDate = false;
			int dateIndex = -1;
			// trying to find date
			for (int i = 0; i < datesL.size(); i++) {
				String currDate = datesL.get(i);
				if (currDate.contentEquals(date)) {
					foundDate = true;
					dateIndex = i;
					break;
				}
			}
			// found date
			if (foundDate) {
				double currTemp = lowTemp.get(dateIndex);
				double min = Math.min(currTemp, temp);
				lowTemp.set(dateIndex, min);
			}
			// did not find date
			else {
				datesL.add(date);
				lowTemp.add(temp);
			}
		}
		// make a new date key
		else {
			datesL.add(date);
			lowTemp.add(temp);
		}
	}
	
	private static void printLowTemps() {
		for (int i = 0; i < datesL.size(); i++) {
			// System.out.print(datesL.get(i) + ": " + lowTemp.get(i) + ", ");
		}
		// System.out.println();
	}
	
	private static void printHighTemps() {
		for (int i = 0; i < datesH.size(); i++) {
			// System.out.print(datesH.get(i) + ": " + highTemp.get(i) + ", ");
		}
		// System.out.println();
	}
	
//	private static void removeExtraDate(String currDate) {
//		if (datesH.size() == 5) {
//			return;
//		}
//		if (currDate.contentEquals(datesH.get(0))) {
//			datesH.remove(0);
//			datesL.remove(0);
//			lowTemp.remove(0);
//			highTemp.remove(0);
//			iconList.remove(0);
//		}
//		else {
//			int last = datesH.size()-1;
//			datesH.remove(last);
//			datesL.remove(last);
//			lowTemp.remove(last);
//			highTemp.remove(last);
//			iconList.remove(last);
//		}
//	}
	
	public static String TestAll() {
		CityAPIStuff city = new CityAPIStuff();
		String wtf = "WHY"; double a = 9.9; int b = 8;
		// setting everything
		Coord coord = new Coord();
		coord.setLat(a); coord.setLon(a);
		City cc = new City(); 
		cc.setCountry(wtf); cc.setId(b); cc.setName(wtf); cc.setCoord(coord);
		Clouds clouds = new Clouds(); clouds.setAll(b);		
		Main main = new Main(); main.setTempMin(a);
		main.setGrndLevel(a); main.setHumidity(b); main.setPressure(a);
		main.setSeaLevel(a); main.setTemp(a); main.setTempKf(a); main.setTempMax(a);
		Rain rain = new Rain();
		Sys sys = new Sys(); sys.setPod(wtf);
		Weather weath = new Weather();
		weath.setDescription(wtf); weath.setIcon(wtf); weath.setId(b); weath.setMain(wtf);
		List<Weather> lw = new ArrayList<Weather>(); lw.add(weath);
		Wind wind = new Wind(); wind.setDeg(a); wind.setSpeed(a);
		List<forecastSearch.List> list = new ArrayList<forecastSearch.List>();
		forecastSearch.List nl = new forecastSearch.List();
		nl.setClouds(clouds); nl.setDt(b); nl.setDtTxt(wtf); nl.setMain(main);
		nl.setRain(rain); nl.setSys(sys); nl.setWind(wind); nl.setWeather(lw);
		list.add(nl); city.setCity(cc); city.setCnt(b); city.setCod(wtf); 
		city.setMessage(a); city.setList(list);
		// getting everything that we didn't get
		String gets = "fml"; int x = 10; double y = 8.8;
		gets = city.getCod(); gets = city.getCity().getCountry();
		gets = city.getCity().getName(); gets = city.getList().get(0).getDtTxt();
		gets = city.getList().get(0).getSys().getPod();
		gets = city.getList().get(0).getWeather().get(0).getDescription();
		gets = city.getList().get(0).getWeather().get(0).getMain();
		x = city.getList().get(0).getMain().getHumidity();
		x = city.getCnt(); x = city.getCity().getId();
		x = city.getList().get(0).getDt(); x = city.getList().get(0).getClouds().getAll();
		x = city.getList().get(0).getWeather().get(0).getId();
		y = city.getList().get(0).getMain().getGrndLevel();
		y = city.getList().get(0).getMain().getPressure();
		y = city.getList().get(0).getMain().getSeaLevel();
		y = city.getList().get(0).getMain().getTemp();
		y = city.getList().get(0).getMain().getTempKf();
		y = city.getMessage(); y = city.getCity().getCoord().getLat();
		y = city.getCity().getCoord().getLon();
		y = city.getList().get(0).getWind().getDeg();
		y = city.getList().get(0).getWind().getSpeed();
		Rain r = city.getList().get(0).getRain();
		return "Hello";
	}

}