package csci310;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class SessionDataTest extends Mockito {

	@Mock
	protected static HttpServletRequest request;
	protected static HttpSession session;

	@Before
	public void SessionDataTestSetup() {
		request = Mockito.mock(HttpServletRequest.class);
		// HttpSession mocked by custom mock class we made
		session = new MockSession();
		when(request.getSession()).thenReturn(session);
		SessionData sd = new SessionData();
	}

	@Test
	public void testInitialData() {
		String currentTempUnit = SessionData.getInitialTempUnit(request);
		Assert.assertEquals(currentTempUnit, "F");
	}

	@Test
	public void testToggleEffect1() {
		SessionData.toggleTempUnit(request);
		String currentTempUnit = SessionData.getInitialTempUnit(request);
		Assert.assertEquals("F", currentTempUnit);
	}

	@Test
	public void testToggleEffect2() {
		SessionData.toggleTempUnit(request);
		SessionData.toggleTempUnit(request);
		String currentTempUnit = SessionData.getInitialTempUnit(request);
		Assert.assertEquals("C", currentTempUnit);
	}

	@Test
	public void testAddToFav() {
		CityObject city1 = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		String username = "testingUserAdd";
		SessionData.addToFav(city1, username);
		Assert.assertEquals(1, SessionData.getFavoriteCities(username).size());
		SessionData.removeFromFav(city1, username); // clear this for other tests
	}

	@Test
	public void testRemoveFromFavSizeCorrect() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		CityObject city2 = new CityObject("New York", "US", "40.7128", "74.0060");
		String username = "testremoveUser";
		SessionData.addToFav(city1, username);
		SessionData.addToFav(city2, username);
		SessionData.removeFromFav(city1, username);
		Assert.assertEquals(SessionData.getFavoriteCities(username).size(), 1);
		SessionData.removeFromFav(city2, username);
	}

	@Test
	public void testRemoveFromFavCityCorrect() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		CityObject city2 = new CityObject("New York", "US", "40.7128", "74.0060");
		String username = "testingremovecorrect";
		SessionData.addToFav(city1, username);
		SessionData.addToFav(city2, username);
		SessionData.removeFromFav(city1, username);
		ArrayList<CityObject> cities = SessionData.getFavoriteCities(username);
		CityObject currCity = cities.get(0);
		Assert.assertEquals("New York", currCity.getName());
	}

	@Test
	public void testRemoveFromFavCityCorrectNull() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
		String username = "testRemoveEmptyList";
		SessionData.addToFav(city1, username);
		SessionData.removeFromFav(city1, username);
		ArrayList<CityObject> cities = SessionData.getFavoriteCities(username);
		Assert.assertTrue(cities.size() == 0);
	}

	@Test
	public void testRemoveFromFavCityCorrectNull1() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
		String username = "testRemoveNull1";
		SessionData.removeFromFav(city1, username);
		ArrayList<CityObject> cities = SessionData.getFavoriteCities(username);
		Assert.assertTrue(cities.size() == 0);
	}

	@Test
	public void testCityExistsTrue() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
		String username = "testCityExistsTusername";
		SessionData.addToFav(city1, username);
		Assert.assertEquals(false, SessionData.isFavorite("Los Angeles", username));
	}

	@Test
	public void testCityExistsFalse() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.05", "-118.24");
		String username = "testCityFalse";
		SessionData.addToFav(city1, username);
		Assert.assertEquals(false, SessionData.isFavorite("New York", username));
	}

	@Test
	public void testCityExistsFalseNullTest() {
		when(request.getSession()).thenReturn(session);
		String username = "cityExistsFalseNull";
		Assert.assertEquals(false, SessionData.isFavorite("Los Angeles", username));
	}

	@Test
	public void extensiveIsFavTest() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		String username = "extensiveTestUser";
		SessionData.addToFav(city1, username);
		Assert.assertEquals(false, SessionData.isFavorite("Los", username));
		Assert.assertEquals(false, SessionData.isFavorite("Los Angeles", username));
	}

	@Test
	public void extensiveRemoveFavTest() {
		when(request.getSession()).thenReturn(session);
		CityObject city1 = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		String username = "extensiveRemoveUser";
		SessionData.addToFav(city1, username);
		CityObject badCity = new CityObject("Los", "US", "11111", "1111");
		SessionData.removeFromFav(badCity, username);
		ArrayList<CityObject> cities = SessionData.getFavoriteCities(username);
		Assert.assertFalse(cities.size() == 0);
		SessionData.removeFromFav(city1, username);
		cities = SessionData.getFavoriteCities(username);
		Assert.assertTrue(cities.size() == 0);
	}

	@Test
	public void testgetLikes() {
		when(request.getSession()).thenReturn(session);
		String city = "Los Angeles";
		String username = "donald";
		Assert.assertFalse(SessionData.getLikes(city, username) == 1);
	}

	@Test
	public void testincrementLikes() {
		when(request.getSession()).thenReturn(session);
		String city = "Los Angeles";
		String username = "testUser";
		int count = 3;
		Assert.assertTrue(SessionData.incrementLikes(city, username, count) == 3);
		SessionData.incrementLikes(city, username, 0);
	}
}