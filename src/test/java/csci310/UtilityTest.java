package csci310;

import csci310.Utility;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilityTest {
	@BeforeClass
	static public void UtilityTestSetup() {
		Utility ut = new Utility();
	}
	
	@Test
	//non-corner case for calcDistance Function
	public void calcDistanceBasicTest1() {
		double lat1 = 36.12 ; double lon1 = -86.67;
		double lat2 = 33.94 ; double lon2 = -118.40;
		Assert.assertEquals(1794, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	//non-corner case for calcDistance Function
	public void calcDistanceBasicTest2() {
		double lat1 = 34.05; double lon1 = -118.24;
		double lat2 = 49.28; double lon2 = -123.12;
		Assert.assertEquals(1081, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	//non-corner case for calcDistance Function
	public void calcDistanceBasicTest3() {
		double lat1 = 31.23; double lon1 = 121.47;
		double lat2 = 41.01; double lon2 = 28.98;
		Assert.assertEquals(4962, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	//when the lat and long are the same
	@Test
	public void calcDistanceZeroTest() {
		double lat1 = 36.12; double lon1 = -54.12;
		double lat2 = 36.12; double lon2 = -54.12;
		Assert.assertEquals(0, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	//when lat and long are invalid values (cast failure)
	@Test
	public void calcDistanceInvalidTest() {
		double lat1 = 100.32; double lon1 = -54.12;
		double lat2 = 36.12; double lon2 = -254.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	//when lat and long are invalid values (cast failure)
	@Test
	public void calcDistanceInvalidLat1Test1() {
		double lat1 = 100; double lon1 = -54.12;
		double lat2 = 36.12; double lon2 = -54.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	//when lat and long are invalid values (cast failure)
	@Test
	public void calcDistanceInvalidLat1Test2() {
		double lat1 = -130.32; double lon1 = -54.12;
		double lat2 = 36.12; double lon2 = -24.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	public void calcDistanceInvalidLat2Test1() {
		double lat1 = -13.32; double lon1 = -54.12;
		double lat2 = 360.12; double lon2 = -24.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	public void calcDistanceInvalidLat2Test2() {
		double lat1 = -13.32; double lon1 = -54.12;
		double lat2 = -136.12; double lon2 = -24.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	public void calcDistanceInvalidLon1Test1() {
		double lat1 = -10.32; double lon1 = -354.12;
		double lat2 = 36.12; double lon2 = -24.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	public void calcDistanceInvalidLon1Test2() {
		double lat1 = -10.32; double lon1 = 354.12;
		double lat2 = 36.12; double lon2 = -24.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	// converting C to F
	@Test
	public void convertCelsiusToFahrenheitTest1() {
		double celsius = 54.43;
		Assert.assertEquals(130, Utility.convertToF(celsius));
	}
	
	@Test
	public void calcDistanceInvalidLon2Test1() {
		double lat1 = -10.32; double lon1 = -34.12;
		double lat2 = 36.12; double lon2 = -244.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	@Test
	public void calcDistanceInvalidLon2Test2() {
		double lat1 = -10.32; double lon1 = -34.12;
		double lat2 = 36.12; double lon2 = 244.12;
		Assert.assertEquals(-1, Utility.calcDistance(lat1,lon1,lat2,lon2));
	}
	
	// converting C to F
	@Test
	public void convertCelsiusToFahrenheitTest2() {
		double celsius = 24;
		Assert.assertEquals(75, Utility.convertToF(celsius));
	}
	
	// converting C to F
	@Test
	public void convertCelsiusToFahrenheitTest3() {
		double celsius = -15;
		Assert.assertEquals(5, Utility.convertToF(celsius));
	}
	
	// converting F to C 
	@Test
	public void convertFahrenheitToCelsiusTest1() {
		double f = 78.45;
		Assert.assertEquals(26, Utility.convertToC(f));
	}
	
	// converting F to C 
	@Test
	public void convertFahrenheitToCelsiusTest2() {
		double f = 45;
		Assert.assertEquals(7, Utility.convertToC(f));
	}
	
	// converting F to C 
	@Test
	public void convertFahrenheitToCelsiusTest3() {
		double f = -12;
		Assert.assertEquals(-24, Utility.convertToC(f));
	}
	
	// testing if string is only digits
	@Test
	public void checkStringIsNumericTrueTest1() {
		Assert.assertEquals(true, Utility.isNumeric("38274"));
	}
	
	// testing if string is only digits
	@Test
	public void checkStringIsNumericTrueTest2() {
		Assert.assertEquals(true, Utility.isNumeric("-38274"));
	}
	
	// testing if string is only digits
	@Test
	public void checkStringIsNumericFalseTest1() {
		Assert.assertEquals(false, Utility.isNumeric("38d74"));
	}
	
	// testing if string is only letters
	@Test
	public void checkStringIsAlphaTrueTest1() {
		Assert.assertEquals(true, Utility.isAlpha("HelloWorld"));
	}
	
	// testing if string is only letters
	@Test
	public void checkStringIsAlphaTrueTest2() {
		Assert.assertEquals(true, Utility.isAlpha("Hello World"));
	}
	
	// testing if string is only letters
	@Test
	public void checkStringIsAlphaFalseTest1() {
		Assert.assertEquals(false, Utility.isAlpha("Hell0W0rld"));
	}
	
	// testing if string is only letters
	@Test
	public void checkStringIsAlphaNULLTest1() {
		String s = null;
		Assert.assertEquals(false, Utility.isAlpha(s));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatTrueTest1() {
		Assert.assertEquals(true, Utility.isFloat("382.74"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatTrueTest2() {
		Assert.assertEquals(true, Utility.isFloat("-382.74"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatTrueTest3() {
		Assert.assertEquals(true, Utility.isFloat("44"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatFalseTest1() {
		Assert.assertEquals(false, Utility.isFloat("x382.74"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatFalseTest2() {
		Assert.assertEquals(false, Utility.isFloat("-.-74"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatFalseTest3() {
		Assert.assertEquals(false, Utility.isFloat("3.7.4"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatFalseTest4() {
		Assert.assertEquals(false, Utility.isFloat("3.73t5"));
	}
	
	// testing if string is float/double
	@Test
	public void checkStringIsFloatFalseTest5() {
		Assert.assertEquals(false, Utility.isFloat("345c"));
	}
	
	// testing if string is alpha numeric
	@Test
	public void checkStringIsAlphaNTrueTest1() {
		Assert.assertEquals(true, Utility.isAlphanumeric("04d"));
	}
	
	// testing if string is alpha numeric
	@Test
	public void checkStringIsAlphaNFalseTest1() {
		Assert.assertEquals(false, Utility.isAlphanumeric("04d-"));
	}
	
	// testing if string is alpha numeric
	@Test
	public void checkStringIsAlphaNNULLTest1() {
		String s = null;
		Assert.assertEquals(false, Utility.isAlphanumeric(s));
	}
	
	@AfterClass
	static public void UtilityTestCleanup() {
	
	}
}
