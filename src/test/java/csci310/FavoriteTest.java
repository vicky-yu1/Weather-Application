package csci310;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class FavoriteTest extends Mockito{
	
	@Mock
	protected static HttpServletRequest request;
	protected static HttpSession session;
		
	@Before
	public void FavoriteTestSetup() {
		request = Mockito.mock(HttpServletRequest.class);
		//HttpSession mocked by custom mock class we made
		session = new MockSession();
		when(request.getSession()).thenReturn(session);
		//SessionData sd = new SessionData();
	}
	

//	@Test
//	public void testaddFav() {
//		when(request.getSession()).thenReturn(session);
//		String cityname = "Los Angeles";
//        String username = "donald";
//        String country = "US";
//        String lat = "28";
//        String lng = "-34";
//		Assert.assertTrue(Fav.addFav(username,cityname,country,lat,lng));
//    }
//    
//    @Test
//	public void testLikeCount() {
//		when(request.getSession()).thenReturn(session);
//		String cityname = "Los Angeles";
//        String username = "donald";
//		Assert.assertTrue(Fav.getLikeCount(cityname,username) == 2);
//	}
//
//	@Test
//	public void testIncrementLikes() {
//		when(request.getSession()).thenReturn(session);
//		String cityname = "Los Angeles";
//        String username = "donald";
//        String count = 3;
//		Assert.assertTrue(Fav.incrementLikes(cityname,username,count) == 3);
//    }
//    
//    @Test
//    public void testremoveFav() {
//        when(request.getSession()).thenReturn(session);
//        String cityname = "Los Angeles";
//        String username = "donald";
//        Assert.assertTrue(Fav.removeFav(cityname,username));
//    }
//
//    @Test
//    public void testisFav() {
//        when(request.getSession()).thenReturn(session);
//        String cityname = "Los Angeles";
//        String username = "donald";
//        Assert.assertTrue(Fav.isFav(cityname,username));
//    }
//
//    @Test
//    public void testisFavEmpty() {
//        when(request.getSession()).thenReturn(session);
//        String username = "donald";
//        Assert.assertTrue(Fav.isFavEmpty(username));
//    }
//
//
//    @Test
//    public void testGetFavCities() {
//        when(request.getSession()).thenReturn(session);
//        String username = "donald";
//        Assert.assertFalse(Fav.isFavCities(username).empty());
//    }
}

