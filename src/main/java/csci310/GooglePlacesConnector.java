package csci310;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
//import com.sun.deploy.security.ValidationState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GooglePlacesConnector {
	private static String key_API = "YOUR_API_KEY_HERE for GooglePlacesAPI";

	// FUNCTION
	public static ArrayList<String> getPhotosCall(double lat, double lng) throws InterruptedException, ApiException, IOException, Exception {
		ArrayList<String> apiURLs = new ArrayList<>();
		int maxWidth = 800;
		//	int maxHeight = 800;

//		Random rand = new Random();
//		int picIndex = 0;

		// set "Geo Context" for api call
		GeoApiContext context = new GeoApiContext.Builder().apiKey(key_API).build();

		LatLng latLng = new LatLng(lat, lng);

		// establish a nearby search request of a lat and lng of a city, for tourist attractions ordered by prominence, within 50000 meters
		NearbySearchRequest nSRequest = new NearbySearchRequest(context);
		nSRequest.location(latLng);
		nSRequest.radius(50000);
		nSRequest.type(PlaceType.TOURIST_ATTRACTION);
		nSRequest.rankby(RankBy.PROMINENCE);

		// wait for API call to return and store location search candidates into an array
		PlacesSearchResponse pSResponse = nSRequest.await();
		PlacesSearchResult[] candidates = pSResponse.results;

		// loop through first five or less candidates to get photos, build the API string, and store it into an arraylist to return
		for (int i = 0; i < 5 && i < candidates.length; i++) {
			if (candidates[i].photos != null) {
//				picIndex = (rand.nextInt() * 1000) % candidates[i].photos.length;
//				String pRef = candidates[i].photos[picIndex].photoReference;
				String pRef = candidates[i].photos[0].photoReference;
//				System.out.println("Adding ref... " + pRef);
				String placePhotoRequest = "https://maps.googleapis.com/maps/api/place/photo?" +
//							"&maxheight=" + Integer.toString(maxHeight) +
						"maxwidth=" + Integer.toString(maxWidth) +
						"&photoreference=" + pRef +
						"&key=" + key_API;
				apiURLs.add(placePhotoRequest);
//				System.out.println("Added url ref: " + apiURLs.get(i));
			}
		}

		return apiURLs;
	}
}
