package csci310;

import java.util.ArrayList;

public class CityObject {
	private String name;
	private String country;
	private String lat;
	private String lng;
	private ArrayList<String> photoLinks;
		
	public CityObject(String name, String country, String lat, String lng) {
		this.name = name;
		this.country = country;
		this.lat = lat;
		this.lng = lng;
		try {
			this.photoLinks = GooglePlacesConnector.getPhotosCall(Double.parseDouble(lat), Double.parseDouble(lng));
		} catch (Exception e) {
			this.photoLinks = null;
		}
	}

	public String getName() {
		return name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getLat() {
		return lat;
	}
	
	public String getLong() {
		return lng;
	}
	
	public ArrayList<String> getPhotoLinks() {
		return photoLinks;
	}
	
}
