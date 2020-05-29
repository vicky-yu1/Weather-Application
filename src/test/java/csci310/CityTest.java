package csci310;

import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;

public class CityTest extends Mockito {

	ArrayList<String> photoLinks;

	@Before
	public void cityTestSetup() {

	}
	
	@Test
	public void cityBasicTest() {
		CityObject c = new CityObject("Los Angeles", "US", "34.0522", "118.2437");
		Assert.assertEquals(c.getName(), "Los Angeles" );
		Assert.assertEquals(c.getCountry(), "US" );
		Assert.assertEquals(c.getLat(), "34.0522" );
		Assert.assertEquals(c.getLong(), "118.2437");
		Assert.assertNotNull(c.getPhotoLinks());
	}

	@Test
	public void invalidPhotoLinkTest() {
		CityObject c = new CityObject("Los Angeles", "US", "34323d.0522", "11832.2437");
		Assert.assertNull(c.getPhotoLinks());
	}
	
	@AfterClass
	static public void cityTestCleanup() {
	
	}
}
