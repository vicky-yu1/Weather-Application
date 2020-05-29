package csci310;

import com.google.maps.*;
import com.google.maps.model.*;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.InvalidRequestException;
import forecastSearch.OpenWeatherConnectorForecast;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import radiusSearch.OpenWeatherConnectorRadius;

import java.util.ArrayList;

public class GooglePlacesConnectorTest extends Mockito {

    // set up the dummy variables and establish class instance
    GooglePlacesConnector gPCDummy;
    ArrayList<String> dummyLinks;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void GooglePlacesConnectorTestSetUp() {
        GooglePlacesConnector gPCDummy = new GooglePlacesConnector();
        dummyLinks = null;
//        gPCDummy.getPhotosCall(-33.8670522, 151.1957362);
    }

    // Tests the main api calling function
    @Test
    public void getPhotosCallTest() throws Exception {
        // TODO
        Double lat = 30.27;
        Double lng = -97.74;
        dummyLinks = gPCDummy.getPhotosCall(lat, lng);
        Assert.assertNotNull(dummyLinks);
        for (int i = 0; i < 5 && i < dummyLinks.size(); i++) {
            Assert.assertNotNull(dummyLinks.get(i));
        }
    }

    // testings when api call is invalid
    @Test
    public void getPhotosCallTestInvalidLatLng() throws Exception{
        //get radius search with invalid parameters
        thrown.expect(Exception.class);
        //we should expect error to be thrown!
        gPCDummy.getPhotosCall(11000, 11000);
    }

    @AfterClass
    public static void GooglePlacesConnectorTestCleanup() {

    }

}
