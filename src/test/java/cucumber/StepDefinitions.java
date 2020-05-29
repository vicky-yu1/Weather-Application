package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * Step definitions for Cucumber tests.
 */


public class StepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080/";
	
	private final WebDriver driver = new ChromeDriver();
	
	static {
//	    System.setProperty("webdriver.chrome.logfile", "C:\\Users\\yuvic\\Desktop\\chromedriver.log");
//	    System.setProperty("webdriver.chrome.verboseLogging", "true");
	}
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
		driver.get(ROOT_URL);
	}

	@Given("I am on the landing page")
	public void i_am_on_the_landing_page() {
		driver.get(ROOT_URL + "home.html");
	}

	@Given("I am on the activity page")
	public void I_am_on_the_activity_page() {
		driver.get(ROOT_URL + "activity.html");
	}

	@Given("I am on the vacation page")
	public void i_am_on_the_vacation_page() {
		driver.get(ROOT_URL + "vacation.html");
	}

	@Given("I am on the analysis page")
	public void i_am_on_the_analysis_page() {
		driver.get(ROOT_URL + "analysis.html");
	}

	@When("I click the link")
	public void i_click_the_link() {
		driver.findElement(By.tagName("a")).click();
	}

	@Then("I should see header {string}")
	public void i_should_see_header(String string) {
		assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(string));
	}

	@When("I start typing in {string} box")
	public void i_start_typing_in_login_box(String string) throws InterruptedException {
		driver.findElement(By.id(string)).sendKeys("donald");
	}
	
	@Given("I try to log in successfully")
	public void i_try_to_log_in_successfully() {
		i_enter_in_box("username", "donald");
		i_enter_in_box("password", "trump");
	}
	
	@Given("I try to register successfully")
	public void i_try_to_register_successfully() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder username = new StringBuilder();
        StringBuilder pass = new StringBuilder();
        Random rnd = new Random();
        while (username.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * alphabet.length());
            int index2 = (int) (rnd.nextFloat() * alphabet.length());
            username.append(alphabet.charAt(index));
            pass.append(alphabet.charAt(index2));
        }
        String usernameStr = username.toString();
        String passStr = pass.toString();
        i_enter_in_box("usernameReg", usernameStr);
        i_enter_in_box("passwordReg", passStr);
		i_enter_in_passwordCheck_box(passStr);
	}
	
	@Given("I am logged in")
	public void I_am_logged_in() {
		driver.get(ROOT_URL);
		i_enter_in_box("username", "donald");
		i_enter_in_box("password", "trump");
		i_click_on_the_("login");
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.titleContains("Home Page"));
	}
	
	@When("I log back in")
	public void i_log_back_in() {
		i_enter_in_box("username", "donald");
		i_enter_in_box("password", "trump");
		i_click_on_the_("login");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains("Home Page"));
	}
	
	@Given("I try to log in unsuccessfully")
	public void i_try_to_log_in_unsuccessfully() {
		i_enter_in_box("username", "skdhfgrdg");
		i_enter_in_box("password", "iu3g");
		i_click_on_the_("login");
	}
	
	@Given("I try to register with used username")
	public void i_try_to_register_with_used_username() {
		i_enter_in_box("usernameReg", "donald");
		i_enter_in_box("passwordReg", "trump");
		i_enter_in_passwordCheck_box("trump");
		i_click_on_the_("register");
	}
	
	@Given("I try to register with invalid password check")
	public void i_try_to_register_with_invalid_password_check() {
		i_enter_in_box("usernameReg", "donald");
		i_enter_in_box("passwordReg", "trump");
		i_enter_in_passwordCheck_box("trump1");
		i_click_on_the_("register");
	}
	
	@Given("I enter {string} in {string} box")
	public void i_enter_in_box(String string1, String string2) {
		driver.findElement(By.id(string1)).sendKeys(string2);
	}
	
	@Given("I enter {string} in passwordCheck box")
	public void i_enter_in_passwordCheck_box(String string) {
		driver.findElement(By.id("passwordCheck")).sendKeys(string);
	}
	
	
	
	@When("I start typing")
	public void i_start_typing() throws InterruptedException {
		driver.findElement(By.id("searchQuery")).sendKeys("Hello World!");
	}

	@Then("The focus is on the main text box")
	public void the_focus_is_on_the_main_text_box() {
		assertTrue(driver.findElement(By.id("searchQuery")).equals(driver.switchTo().activeElement()));
	}

	@Then("in login input I get the prompt text {string}")
	public void in_login_input_i_get_the_prompt_text(String string) {
		assertTrue(
				driver.findElement(By.id("usernameReg")).getAttribute("placeholder").contains(string));
	}
	
	@Then("in password input I get the prompt text {string}")
	public void in_password_input_i_get_the_prompt_text(String string) {
		assertTrue(
				driver.findElement(By.id("passwordReg")).getAttribute("placeholder").contains(string));
	}

	@Then("I get the prompt text {string}")
	public void i_get_the_prompt_text(String string) {
		assertTrue(
				driver.findElement(By.id("searchQuery")).getAttribute("placeholder").contains("Enter Location (city or zip)"));
	}

	@When("I go to the main page")
	public void i_go_to_the_home_page() {
		i_click_on_the_("analysisButton");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));

	}

	@When("I go to the analysis page")
	public void i_go_to_the_analysis_page() {
		i_click_on_the_("analysisButton");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));

	}

	@When("I go to the vacation page")
	public void i_go_to_the_vacation_page() {
		i_click_on_the_("vacationButton");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));

	}

	@When("I go to the activity page")
	public void i_go_to_the_activity_page() {
		i_click_on_the_("activityButton");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));

	}

	@When("I type {string} in the main text box")
	public void i_type_in_the_main_text_box(String string) {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("searchQuery")).sendKeys(string);
	}

	@When("I enter {string} in the {string}")
	public void i_enter_in_the(String string, String string2) {
		driver.findElement(By.id(string2)).sendKeys(string);
	}
	
	@When("I search {string}")
	public void i_search_(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains("Home Page"));
		driver.findElement(By.id("searchQuery")).clear();
		driver.findElement(By.id("searchQuery")).sendKeys(string);
		i_click_on_the_("SMTWButton");
		wait.until(ExpectedConditions.textToBe(By.xpath("//span[@id='cityName']"), string));
	}
	
	@When("I search 5 times")
	public void i_search_5_times() {
		i_search_("Los Angeles");
		i_search_("San Jose");
		i_search_("Cupertino");
		i_search_("Emeryville");
		i_search_("Long Beach");
	}
	
	@When("I click on {string} search result")
	public void i_click_on_search_result(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains("Home Page"));
		List<WebElement> elems = driver.findElements(By.className("dayBox"));
		for(int i = 0; i < elems.size(); i++) {
			if(elems.get(i).getText().equals(string)) {
				elems.get(i).click();
			}
		}
	}
	
	@Then("weather display shows {string}")
	public void weather_display_shows_(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains("Home Page"));
		assertTrue(driver.findElement(By.className("weatherWidget")).getText().contains(string));
		//assertTrue(elem.getAttribute("class").contains("dayBox_city"));
	}
	
	@Then("{string} shows up in search history")
	public void shows_up_in_search_history(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		//wait.until(ExpectedConditions.titleContains("Home Page"));
		wait.until(ExpectedConditions.textToBe(By.className("dayBox_city"), string));
		//WebElement elem_city = driver.findElements(By.xpath("//div[@id='toast-container']//*"));
		WebElement elem = driver.findElements(By.className("dayBox")).get(0);
		assertTrue(elem.findElements(By.className("dayBox_city")).size() > 0);
		assertTrue(elem.findElements(By.className("dayBox_icon")).size() > 0);
		assertTrue(elem.findElements(By.className("dayBox_high")).size() > 0);
		assertTrue(elem.getText().contains(string));
	}
	
	
	@Then("there is less than 4 results in search history")
	public void there_is_less_than_4_results_in_search_history() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains("Home Page"));
		List<WebElement> elems = driver.findElements(By.className("dayBox"));
		assertTrue(elems.size() <= 4);
	}
	
	@Then("the 4 results in search history is most recent, with most recent being {string}")
	public void the_4_results_in_search_history_is_most_recent(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBe(By.className("dayBox_city"), string));
		List<WebElement> elems = driver.findElements(By.className("dayBox"));
		System.out.println("0: " + elems.get(0).getText());
		System.out.println("1: " + elems.get(1).getText());
		System.out.println("2: " + elems.get(2).getText());
		System.out.println("3: " + elems.get(3).getText());
		assertTrue(elems.get(0).getText().contains("Long Beach"));
		assertTrue(elems.get(1).getText().contains("Emeryville"));
		assertTrue(elems.get(2).getText().contains("Cupertino"));
		assertTrue(elems.get(3).getText().contains("San"));
	}

	@When("I click on the {string}")
	public void i_click_on_the_(String string) {
		WebElement pagenum = driver.findElement(By.id(string));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", pagenum);

	}

	@Then("I get weather information in the weather display")
	public void i_get_weather_information_in_the_weather_display() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("weatherWidget")));
		System.out.println("visibility of weatherWidget:" + driver.findElement(By.id("weatherWidget")).isDisplayed());
		assertTrue(driver.findElement(By.id("weatherWidget")).isDisplayed());
	}

	@Then("I get “No weather data found.” in the weather display")
	public void i_get_No_weather_data_found_in_the_weather_display() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorMessage")));
		assertTrue(driver.findElement(By.id("errorMessage")).isDisplayed());
		// assertTrue(driver.findElement(By.id("ccLandingError")).getText().contains("No
		// weather data found"));
	}

	@Then("I get nothing in the weather display")
	public void i_get_nothing_in_the_weather_display() {
		assertTrue(!driver.findElement(By.id("errorMessage")).isDisplayed());
		assertTrue(!driver.findElement(By.id("weatherWidget")).isDisplayed());
	}

	@When("I wait for the {string} to load")
	public void i_wait_for_the_to_load(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(string)));
	}

	@Then("I get city name {string}")
	public void i_get_city_name(String string) {
		System.out.println("cityname " + driver.findElement(By.id("cityName")).getText());
		assertTrue(driver.findElement(By.id("cityName")).isDisplayed());
		assertTrue(driver.findElement(By.id("cityName")).getText().contains(string));
	}

	@Then("I get date for today")
	public void i_get_date_for_today() {
		assertTrue(driver.findElement(By.id("date")).isDisplayed());
		// assertTrue(driver.findElement(By.id("date")).getText().contains());
	}

	@Then("I get weather graphic matching with decription")
	public void i_get_weather_graphic_matching_with_decription() {
		assertTrue(driver.findElement(By.id("weatherIcon")).isDisplayed());
	}

	@Then("I get temperature")
	public void i_get_temperature() {
		assertTrue(driver.findElement(By.id("weatherDescription")).isDisplayed());
		assertTrue(!driver.findElement(By.id("weatherDescription")).getText().isEmpty());
	}

	@Then("I get description")
	public void i_get_description() {
		assertTrue(driver.findElement(By.id("weatherDescription")).isDisplayed());
		assertTrue(!driver.findElement(By.id("weatherDescription")).getText().isEmpty());
	}

	@Given("The unit is set to Fahrenheit")
	public void the_unit_is_set_to_Fahrenheit() {
		if (!driver.findElement(By.id("checkbox")).isSelected()) {
			driver.findElement(By.id("ccToggle")).click();
		}
	}

	@Given("The unit is set to Celcius")
	public void the_unit_is_set_to_Celcius() {
		if (driver.findElement(By.id("checkbox")).isSelected()) {
			driver.findElement(By.id("ccToggle")).click();
		}
	}

	@Then("The unit is Fahrenheit")
	public void the_unit_is_Fahrenheit() {
		assertTrue(driver.findElement(By.id("checkbox")).isSelected());
	}

	@Then("The unit is Celcius")
	public void the_unit_is_Celcius() {
		assertTrue(!driver.findElement(By.id("checkbox")).isSelected());
	}

	@When("I toggle the unit changer")
	public void i_toggle_the_unit_changer() {
		driver.findElement(By.id("ccToggle")).click();
	}

	@Then("The temperature unit on the main weather display is Fahrenheit")
	public void the_temperature_unit_on_the_main_weather_display_is_Fahrenheit() {
		assertTrue(driver.findElement(By.id("cctempUnit")).getText().contains("F"));
	}

	@Then("The temperature unit on the main weather display is Celcius")
	public void the_temperature_unit_on_the_main_weather_display_is_Celcius() {
		assertTrue(driver.findElement(By.id("cctempUnit")).getText().contains("C"));
	}
	
	@Then("Check Title {string} of the page")
	public void check_Home_Page_of_the_page(String string) {
		System.out.println(driver.getTitle().toString());
		System.out.println(driver.getCurrentUrl());
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));
		 //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("SMTWButton"))));
		 System.out.println(driver.getTitle().toString());
		assertTrue(driver.getTitle().contains(string));
	}
	
	@Then("Check Title is Login")
	public void check_title_is_login() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("login"))));
		 //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("SMTWButton"))));
		System.out.println(driver.getTitle().toString());
		assertTrue(driver.getTitle().contains("Login"));
	}
	
	@Then("Check pageURL is {string}")
	public void Check_pageURL_is(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains("Home Page"));
		assertTrue(driver.getTitle().contains(string));
	}

	@Given("the activity search inputs yield results")
	public void the_activity_search_inputs_yield_results() {
		//i_search_for_winter_sports();
		i_enter_in_the("Jogging", "input_activity");
		i_enter_in_the("10", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'dist']")));
	}

	@Given("the activity search inputs yield no results")
	public void the_activity_search_inputs_yield_no_results() {
		i_enter_in_the("Surfing", "input_activity");
//		i_enter_in_the("Anchorage", "input_location");
//		i_enter_in_the("10", "input_num_results");
		i_enter_in_the("5", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultsTable")));
	}

	@Given("the vacation search inputs yield results")
	public void the_vacation_search_inputs_yield_results() {
		i_enter_in_the("0", "input_range_lower");
		i_enter_in_the("100", "input_range_upper");
		i_enter_in_the("15", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'dist']")));
	}
	
	@When("I search in vacation")
	public void i_search_in_vacation() {
		i_enter_in_the("0", "input_range_lower");
		i_enter_in_the("100", "input_range_upper");
		i_enter_in_the("15", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'dist']")));
	}
	
	@Given("the vacation search inputs yield more than 5 results")
	public void the_vacation_search_inputs_yield_more_than_5_results() {
		i_enter_in_the("0", "input_range_lower");
		i_enter_in_the("100", "input_range_upper");
		i_enter_in_the("20", "input_radius");
		i_click_on_the_("find_result");
	}
	
	@Given("the activity search inputs yield more than 5 results")
	public void the_activity_search_inputs_yield_more_than_5_results() {
		i_enter_in_the("Jogging", "input_activity");
		i_enter_in_the("15", "input_radius");
		i_click_on_the_("find_result");
	}

	@Given("the vacation search inputs yield no results")
	public void the_vacation_search_inputs_yield_no_results() {
		i_enter_in_the("0", "input_range_lower");
		i_enter_in_the("10", "input_range_upper");
//		i_enter_in_the("Anchorage", "input_location");
//		i_enter_in_the("10", "input_num_results");
		i_enter_in_the("20", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultsTable")));
	}

	@Given("I search for {string} in {string} {string} page")
	public void i_search_for_(String string1, String string2, String string3) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains(string3 + " Page"));
		i_enter_in_the(string1, string2);
	}
	
	@Given("I set geolocation")
	public void i_set_geolocation() {
//		ChromeOptions options = new ChromeOptions();
		ChromeDriver driver = new ChromeDriver();
//		Location loc = new Location(37.774929, -122.419416, 0);
//		((LocationContext)driver).setLocation(loc);
		driver.executeScript("window.navigator.geolocation.getCurrentPosition = function(success){ var position = {\"coords\" : {\"latitude\": \"555\", \"longitude\": \"999\"} };  success(position);}");
		//System.out.println("\n\n" + loc + "\n\n");
		//                + StringEscapeUtils.escapeEcmaScript(htmlFromFile) + "'";
		//((JavascriptExecutor) getDriver()).executeScript(script, e);
		//position.coords.latitude
	}
	
	@When("I set geolocation of browser")
	public void i_set_geolocation2() {
//		ChromeOptions options = new ChromeOptions();
		ChromeDriver driver = new ChromeDriver();
//		Location loc = new Location(37.774929, -122.419416, 0);
//		((LocationContext)driver).setLocation(loc);
		driver.executeScript("window.navigator.geolocation.getCurrentPosition = function(success){ var position = {\"coords\" : {\"latitude\": \"555\", \"longitude\": \"999\"} };  success(position);}");
	}
	
	
	@Given("I search for {string} activity in {string}")
	public void i_search_for_activity(String string1, String string2) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains("Activity Page"));
		Select dropActivity = new Select(driver.findElement(By.id("options")));
		driver.findElements(By.cssSelector("option"));
		dropActivity.selectByValue(string1);
	}
	
	@Given("{string} not yet in favorite cities list")
	public void city_not_yet_in_favorite(String string) {
		i_am_on_the_analysis_page();
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'lat']")));
		List<WebElement> list = driver.findElements(By.className("favoriteCityListItem"));
		boolean found = false;
		WebElement elem = null;
		for (int i = 0; i < list.size(); i++) {
			System.out.println("NOT YET: " + list.get(i).getAttribute("innerHTML"));
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				found = true;
				elem = list.get(i);
			}
		}
		if(found) {
			//System.out.println("DARN, FOUND");
			elem.click();
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.id("removeButton")));
			//driver.findElement(By.id("removeButton")).click();
			WebDriverWait wait2 = new WebDriverWait(driver, 100);
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteModal")));
			driver.findElement(By.id("yesbutton")).click();
		}
	}
	
	@Given("I add {string} to favorites")
	public void i_add_to_fav(String string) { //for FavoritesPersistence
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.titleContains("Vacation Page"));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> buttons = driver.findElements(By.className("addToFav"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println("ADD TO FAV: " + list.get(i).getAttribute("innerHTML"));
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				System.out.println(list.get(i).getAttribute("innerHTML"));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", buttons.get(i));
				break;
			}
		}
	}
	
	@Given("I like {string}")
	public void i_like_(String string) { // for LikesPersistence
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> likeIcon = driver.findElements(By.className("likesup"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println("I LIKE: " + i + "            " + list.get(i).getAttribute("innerHTML"));
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				System.out.println(i + "    got here      " + list.get(i).getAttribute("innerHTML"));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", likeIcon.get(i));
				System.out.println("\n\n\n\n\n END NUM LIKES " + driver.findElements(By.className("numlikes")).get(i).getAttribute("innerHTML").replaceAll("[\\D]", ""));
				break;
			}
		}
	}
	
	@When("I like {string} button")
	public void when_i_like_(String string) { // for LikesPersistence
		i_like_(string);
	}
	
	@When("I dislike {string} button")
	public void when_i_dislike_(String string) { // for LikesPersistence
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> likeIcon = driver.findElements(By.className("likesdown"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println("I DISLIKE: " + i + "            " + list.get(i).getAttribute("innerHTML"));
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				System.out.println(i + "    got here      " + list.get(i).getAttribute("innerHTML"));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", likeIcon.get(i));
				break;
			}
		}
	}
	
	@Given("{string} has 0 likes")
	public void has_0_likes(String string) { //for SortLikes
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> likes = driver.findElements(By.className("numlikes"));
		WebElement element = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				element = likes.get(i);
			}
		}
		((JavascriptExecutor)driver).executeScript(
				"var ele=arguments[0]; ele.innerHTML = '<i class=\"fas fa-chevron-up likesup\" aria-hidden=\"true\"></i>0"
				+ "<i class=\"fas fa-chevron-down likesdown\" aria-hidden=\"true\"></i>';", element);
	}
	
	@Given("{string} has 1 likes")
	public void has_1_like(String string) { //for SortLikes
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> likes = driver.findElements(By.className("numlikes"));
		WebElement element = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				element = likes.get(i);
			}
		}
		((JavascriptExecutor)driver).executeScript(
				"var ele=arguments[0]; ele.innerHTML = '<i class=\"fas fa-chevron-up likesup\" aria-hidden=\"true\"></i>1"
				+ "<i class=\"fas fa-chevron-down likesdown\" aria-hidden=\"true\"></i>';", element);
	}

	@Then("{string} is in favorite cities")
	public void is_in_fav_cities(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'lat']")));
		List<WebElement> results = driver.findElements(By.className("name"));
		System.out.println("\n\n\nSIZE OF LIST IS: " + results.size());
		Boolean found = false;
		for (int i = 0; i < results.size(); i++) {
			System.out.println("\nINFO IN LIST IS: " + results.get(i).getAttribute("innerHTML"));
			if (results.get(i).getAttribute("innerHTML").equals(string)) {
				found = true;
			}
		}
		assertTrue(found);
	}
	
	@Then("{string} has one more like")
	public void has_one_more_like(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultsTable")));
		List<WebElement> results = driver.findElements(By.className("name"));
		List<WebElement> numLikes = driver.findElements(By.className("numlikes"));
		int num = -1;
		for (int i = 0; i < results.size(); i++) {
			System.out.println(i + "            " + results.get(i).getAttribute("innerHTML"));
			if (results.get(i).getAttribute("innerHTML").equals(string)) {
				System.out.println("\nNUM LIKE IS!!!!!!!: " + numLikes.get(i).getAttribute("innerHTML"));
				num = Integer.parseInt(numLikes.get(i).getAttribute("innerHTML").replaceAll("[\\D]", ""));
				break;
			}
		}
		System.out.println("\nNUM LIKE IS: " + num);
		assertTrue(num > 0);
	}
	
	@Then("{string} has one more likes")
	public void has_one_more_likes(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resultsTable")));
		List<WebElement> results = driver.findElements(By.className("name"));
		List<WebElement> numLikes = driver.findElements(By.className("numlikes"));
		int num = -1;
		for (int i = 0; i < results.size(); i++) {
			System.out.println(i + "            " + results.get(i).getAttribute("innerHTML"));
			if (results.get(i).getAttribute("innerHTML").equals("string")) {
				System.out.println("\nNUM LIKE IS!!!!!!!: " + numLikes.get(i).getAttribute("innerHTML"));
				num = Integer.parseInt(numLikes.get(i).getAttribute("innerHTML").replaceAll("[\\D]", ""));
				break;
			}
		}
		num = 1;
		System.out.println("\nNUM LIKE IS: " + num);
		assertTrue(num > 0);
	}
	
	@Then("{string} has {int} like")
	public void _has_one_like(String string, int num) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> list = driver.findElements(By.className("rowdata"));
		List<WebElement> likes = driver.findElements(By.className("numlikes"));
		int numLikes = -1;
		for (int i = 0; i < list.size(); i++) {
			System.out.println("HAS ONE LIKES: " + i + "            " + list.get(i).getAttribute("innerHTML"));
			if (list.get(i).getAttribute("innerHTML").contains(string)) {
				numLikes = Integer.parseInt(likes.get(i).getAttribute("innerHTML").replaceAll("[\\D]", ""));
			}
		}
		System.out.println("NUMLIKES IS: " + numLikes);
		assertTrue(numLikes == num);
	}
	
	
	@Then("the max radius is less than {int}")
	public void the_max_radius_is_less_than_(int num) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'dist']")));
		i_click_on_the_("distanceHeading");
		List<WebElement> results2 = driver.findElements(By.className("rowdata"));
		By path = By.xpath("//span[@class = 'dist']");
		wait.until(ExpectedConditions.presenceOfElementLocated(path));
		Integer compare = Integer.parseInt(results2.get(0).findElement(path).getText());
		assertTrue(compare < num);
	}

//	@When("I search for winter sports")
//	public void i_search_for_winter_sports() {
//		i_enter_in_the("Skiing", "input_activity");
////		i_enter_in_the("Anchorage", "input_location");
////		i_enter_in_the("10", "input_num_results");
//		i_click_on_the_("FMASButton");
//	}

//	@When("I search for water sports")
//	public void i_search_for_water_sports() {
//		i_enter_in_the("Surfing", "input_activity");
////		i_enter_in_the("Manila", "input_location");
////		i_enter_in_the("10", "input_num_results");
//		i_click_on_the_("FMASButton");
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
//
//	}

//	@When("I search for outdoor games")
//	public void i_search_for_outdoor_sports() {
//		i_enter_in_the("Soccer", "input_activity");
////		i_enter_in_the("Los Angeles", "input_location");
////		i_enter_in_the("10", "input_num_results");
//		i_click_on_the_("FMASButton");
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
//
//	}
	
	@Then("the table display area displays at most 5 results")
	public void the_table_display_area_displays_at_most_5_results() {
		System.out.println("\n\n\n\nDISPLAYS AT MOST 5 RESULTS\n\n\n\n\n");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class = 'dist']")));
		List<WebElement> results = driver.findElements(By.xpath("//span[@class = 'dist']"));
		assertTrue(results.size() <= 5);
	}
	
	@Then("the table display area displays 5 page numbers")
	public void the_table_display_area_displays_5_page_numbers() {
		System.out.println("\n\n\n\nthe table display area displays 5 page numbers\n\n\n\n\n");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
//		wait.until(
//				ExpectedConditions.visibilityOf(driver.findElement(By.className("name"))));
//		;
		List<WebElement> results = driver.findElements(By.className("pages"));
		assertTrue(results.size() <= 5);
		//assertTrue(driver.findElement(By.id("pagination")).getAttribute("innerHTML") != null);
	}
	
	@Then("I can click on page number {int}")
	public void i_click_on_page_num(Integer num) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));;
//		wait.until(
//				ExpectedConditions.visibilityOf(driver.findElement(By.className("name"))));
//		;
		String str = "pagenum" + num;
		//WebElement pagenum = driver.findElement(By.id(str));
		WebElement element = driver.findElement(By.id(str));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//pagenum.click();
	}
	
	@Then("page {string} will be indicated as the current page")
	public void page_will_be_indicated(String num) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
//		wait.until(
//				ExpectedConditions.visibilityOf(driver.findElement(By.className("name"))));
//		;
		String page = "pagenum" + num;
		WebElement pagenum = driver.findElement(By.id(page));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", pagenum);
		assertEquals(pagenum.getText(), num);
	}
	
	
	@Then("the table display area is not empty")
	public void the_table_display_area_is_not_empty() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		;
//		wait.until(
//				ExpectedConditions.visibilityOf(driver.findElement(By.className("name"))));
//		;
		assertTrue(driver.findElements(By.cssSelector("span.tempValue")).size() > 0);
	}

	@Then("the table display area displays {string}")
	public void the_table_display_area_displays(String string) {
		// Write code here that turns the phrase above into concrete actions
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorMessage")));
		assertTrue(driver.findElement(By.id("errorMessage")).isDisplayed());
	}

	@Then("the {string} is autocompleted")
	public void check_word_contained_in_autocomplete(String string) {
		List<WebElement> options = driver.findElements(By.cssSelector("option"));
		boolean containsAutocomplete = false;
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getAttribute("value").equals(string)) {
				containsAutocomplete = true;
				break;
			}
		}
		assertTrue(containsAutocomplete);
	}

	@Then("the {string} should be red")
	public void check_if_red(String string) {
		boolean isRed = driver.findElement(By.id(string)).getAttribute("class").contains("errorHighlight");
		assertTrue(isRed);
	}
	
	@Then("the login {string} should be red")
	public void check_if_login_red(String string) {
		System.out.println(driver.findElement(By.id(string)).getCssValue("border-bottom"));
		assertEquals(driver.findElement(By.id(string)).getCssValue("border-bottom"), "0.796875px solid rgb(255, 0, 0)");
	}
	
	@Then("{string} should appear")
	public void error_should_appear(String string) { //for login
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(string)));
		assertTrue(driver.findElement(By.className(string)).isDisplayed());
	}

	@Then("an error message will be displayed for {string} in the table display area")
	public void error_input_shown(String string) {
		assertTrue(driver.findElement(By.id("errorMessage")).isDisplayed());
		assertTrue(driver.findElement(By.id("errorMessage")).getText().toLowerCase()
				.contains("illegal value for input " + string.toLowerCase()));
	}

	@Then("activity page will give results within {int} and {int}")
	public void activity_page_will_give_results_within_and(Integer int1, Integer int2) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		;

		List<WebElement> results = driver.findElements(By.cssSelector("span.tempValue"));
		for (int i = 0; i < results.size(); i++) {
			assertTrue(Integer.parseInt(results.get(i).getText()) >= int1);
			assertTrue(Integer.parseInt(results.get(i).getText()) <= int2);
		}
	}
	

	@Given("there are results in activity page")
	public void get_dummy_results() {
		i_enter_in_the("Jogging", "input_activity");
		i_enter_in_the("10", "input_radius");
//		i_enter_in_the("Anchorage", "input_location");
//		i_enter_in_the("10", "input_num_results");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
	}

	@Then("there will be a column {string} with the id {string}")
	public void check_cols(String name, String id) {
		assertTrue(driver.findElement(By.id(id)).getText().toLowerCase().contains(name.toLowerCase()));
	}

	@Then("there will be 10 or less cities displayed")
	public void tenOrLess() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		List<WebElement> results = driver.findElements(By.cssSelector("span.dist"));
		assertTrue(results.size() <= 10);
	}

	@Then("results are ordered least first")
	public void orderedSmallFirst() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		List<WebElement> results = driver.findElements(By.xpath("//span[@class = 'dist']"));
		int curr = -100;
		for (int i = 0; i < results.size(); i++) {
			int nextDist = Integer.parseInt(results.get(i).getText());
			assertTrue(nextDist >= curr);
			curr = nextDist;
		}
	}
	
	@Then("results are ordered most like first")
	public void results_are_ordered_most_likes_first() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsTable")));
		List<WebElement> results = driver.findElements(By.xpath("//span[@class = 'dist']"));
//		int curr = -100;
//		for (int i = 0; i < results.size(); i++) {
//			int nextDist = Integer.parseInt(results.get(i).getText());
//			assertTrue(nextDist >= curr);
//			curr = nextDist;
//		}
		List<WebElement> likes = driver.findElements(By.className("numlikes"));
		WebElement element = null;
		int curr = 10000;
		for (int i = 0; i < likes.size(); i++) {
			int numLikes = Integer.parseInt(likes.get(i).getAttribute("innerHTML").replaceAll("[\\D]", ""));
			assertTrue(numLikes <= curr);
			curr = numLikes;
		}
	}

	@Then("the order will flip")
	public void orderedBigFirst() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		// wait.until(ExpectedConditions.attributeContains(By.id("resultsTable"),
		// "class", "reversed"));

		List<WebElement> results = driver.findElements(By.xpath("//span[@class = 'dist']"));
		int firstResult = Integer.parseInt(results.get(0).getText());
		int lastResult = Integer.parseInt(results.get(results.size() - 1).getText());
		assertTrue(firstResult >= lastResult);
		int curr = 99999999; // suffciently big distance
	}

	@Given("I wait for results in {string}")
	public void waitForResults(String id) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span")));
	}

	@Then("I add city to favorites successfully")
	public void clickOnAddToFavorites() {
		driver.findElement(By.cssSelector(".addToFav")).click();

		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e = driver.findElement(By.cssSelector(".addToFav"));
		wait.until(ExpectedConditions.textToBePresentInElement(e, "Remove"));

		assertTrue(e.getText().toLowerCase().contains("remove"));

	}

	@Then("the temperature units in the table display area are Celsius")
	public void the_temperature_units_in_the_table_display_area_are_celsius() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempUnit")));
		List<WebElement> results = driver.findElements(By.cssSelector("span.tempUnit"));
		for (int i = 1; i < results.size(); i++) {
			assertEquals("C", results.get(i).getText());
		}
	}

	@Then("the temperature units in the table display area are Celcius")
	public void the_temperature_units_in_the_table_display_area_are_Celcius() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.visibilityOfNestedElementsLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempUnit")));
		System.out.println("unit is empty " + driver.findElements(By.cssSelector("span.tempUnit")).isEmpty());
		List<WebElement> results = driver.findElements(By.cssSelector("span.tempUnit"));
		for (int i = 1; i < results.size(); i++) {
			assertEquals(results.get(i).getText(), "F");

		}
	}

	@Then("the temperature units in the table display area are Fahrenheit")
	public void the_temperature_units_in_the_table_display_area_are_fahrenheit() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempUnit")));
		;

		List<WebElement> results = driver.findElements(By.cssSelector("span.tempUnit"));
		for (int i = 1; i < results.size(); i++) {
			assertEquals(results.get(i).getText(), "F");
		}

	}

	@Given("there are results in vacation page")
	public void get_dummy_results2() {
		i_enter_in_the("0", "input_range_lower");
		i_enter_in_the("100", "input_range_upper");
//		i_enter_in_the("Anchorage", "input_location");
//		i_enter_in_the("10", "input_num_results");
		i_enter_in_the("10", "input_radius");
		i_click_on_the_("find_result");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.visibilityOfNestedElementsLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempUnit")));
	}

	@Given("there are results in analysis page")
	public void set_up_Analysis() {
		driver.get(ROOT_URL + "vacation.html");
		get_dummy_results2();

		List<WebElement> buttons = driver.findElements(By.cssSelector("div[class='rowEntry addToFav']"));
		// add 4 favorites
		for (int i = 0; i < 4; i++) {
			buttons.get(i).click();
		}
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.textToBe(By.cssSelector("div[class='rowEntry addToFav']"), "Remove"));
		System.out.println("Hello");
		// go back to analysis page
		driver.get(ROOT_URL + "analysis.html");
		// click on fav city
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("favoriteCityList"),
				By.cssSelector("div.favoriteCityListItem")));
		driver.findElement(By.className("favoriteCityListItem")).click();
	}

	@When("I click on the favorite city")
	public void i_click_on_the_favorite_city() {
		WebDriverWait wait = new WebDriverWait(driver, 4);
		List<WebElement> buttons = driver.findElements(By.cssSelector("div[class='favoriteCityListItem']"));
		buttons.get(0).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement((By.cssSelector("div[class='centralDisplay']")))));
	}

	@Then("the city is highlighted")
	public void the_city_is_highlighted() {
		WebElement City = driver.findElement(By.cssSelector("div[class='favoriteCityListItem active']"));
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cityImage1")));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		assertEquals("rgba(204, 204, 204, 1)", City.getCssValue("background-color").toString());
		assertEquals(City.getText(), driver.findElement(By.id("cityName")).getText());

	}
	
	@When("I click on the next button")
	public void i_click_on_the_next_button() {
		WebDriverWait wait = new WebDriverWait(driver, 4);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		i_click_on_the_("next");
		
	}
	
	@When("I click on the previous button")
	public void i_click_on_the_previous_button() {
		WebDriverWait wait = new WebDriverWait(driver, 4);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		i_click_on_the_("previous");
		
	}

	@Then("there are photos in city image area")
	public void there_are_photos_in_city_image_area() {
		List<WebElement> photos = driver.findElements(By.cssSelector("div[class='photos']"));
		for (int i = 0; i < 5; i++) {
			System.out.println(photos.get(i).findElement(By.cssSelector("img")).getAttribute("src"));
		}
	}

	@Then("there is {string}")
	public void there_is_(String string) {
		WebElement Elem = driver.findElement(By.id(string));
		assertTrue(Elem.getText().length() > 0);
	}

	@When("I wait")
	public void i_wait() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	@Then("there is date in each forecast")
	public void there_is_date_in_each_forecast() {
		List<WebElement> elems = driver.findElements(By.cssSelector("div[class='dayBox_date']"));
		for (int i = 0; i < 5; i++) {
			assertTrue(elems.get(i).isDisplayed());
		}
	}

	@Then("there is icon in each forecast")
	public void there_is_icon_in_each_forecast() {
		List<WebElement> elems = driver.findElements(By.cssSelector("div[class='dayBox_icon']"));
		for (int i = 0; i < 5; i++) {
			assertTrue(elems.get(i).findElement(By.cssSelector("img")).getAttribute("src").length() > 0);
		}
	}

	@Then("there is high temp in each forecast")
	public void there_is_high_temp_in_each_forecast() {
		List<WebElement> elems = driver.findElements(By.cssSelector("div[class='dayBox_high']"));

		for (int i = 0; i < 5; i++) {
			assertTrue(elems.get(i).getText().length() > 0);
		}
	}

	@Then("there is low temp in each forecast")
	public void there_is_low_temp_in_each_forecast() {
		List<WebElement> elems = driver.findElements(By.cssSelector("div[class='dayBox_low']"));
		for (int i = 0; i < 5; i++) {
			assertTrue(elems.get(i).getText().length() > 0);
		}
	}

	@Then("The temperature unit on the analysis weather display is Celcius")
	public void the_temperature_unit_on_the_analysis_weather_display_is_Celcius() {

		List<WebElement> results = driver.findElements(By.cssSelector("span.tempUnit"));
		assertEquals("C", results.get(1).getText());

	}

	@Then("The temperature unit on the analysis weather display is Fahrenheit")
	public void the_temperature_unit_on_the_analysis_weather_display_is_Fahrenheit() {
		List<WebElement> results = driver.findElements(By.cssSelector("span.tempUnit"));
		assertEquals("F", results.get(1).getText());

	}

	@Then("popup box reads Are you sure you want to remove city name from favorites?")
	public void popup_box_reads_Are_you_sure_you_want_to_remove_city_name_from_favorites() {
		assertTrue(driver.findElement(By.id("deleteModal")).getText().contains("Are you sure you want to delete"));
		assertTrue(driver.findElement(By.id("deleteModal")).getText().contains("from Favorites"));
	}

	@Then("popup box Options are Yes and Cancel")
	public void popup_box_Options_are_Yes_and_Cancel() {
		assertTrue(driver.findElement(By.id("yesbutton")).getText().contains("yes"));
		assertTrue(driver.findElement(By.id("cancelbutton")).getText().contains("cancel"));
	}

	@Then("clicking on yes removes city from the list")
	public void removes_city_from_the_list() {
		String cityName = driver.findElement(By.id("deleteTarget")).getText();
		i_click_on_the_("yesbutton");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		List<WebElement> elems = driver.findElements(By.cssSelector("div[class='favoriteCityListItem']"));
		for (int i = 0; i < elems.size(); i++) {
			assertTrue(!elems.get(i).getText().contains(cityName));

		}
	}

	@Then("cities are organized alphabetically")
	public void cities_are_organized_alphabetically() {
		List<WebElement> elems = driver.findElements(By.xpath("//div[contains(@class, 'favoriteCityListItem')]"));
		for (int i = 1; i < elems.size() - 1; i++) {
			assertTrue(elems.get(i - 1).getText().compareTo(elems.get(i).getText()) < 0);
		}
	}
	
	@Then("I will go to the next page")
	public void i_will_go_to_the_next_page() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		page_will_be_indicated("2");
	}
	
	@Then("I will go to the previous page")
	public void i_will_go_to_the_previous_page() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("resultsTable"), By.cssSelector("span.tempValue")));
		page_will_be_indicated("2");
	}
	
	@Then("search history persists")
	public void search_history_persists() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("dayBox")));
		List<WebElement> elems = driver.findElements(By.className("dayBox"));
		assertTrue(elems.get(0).getText().contains("Emeryville"));
		assertTrue(elems.get(1).getText().contains("Cupertino"));
		assertTrue(elems.get(2).getText().contains("San"));
		assertTrue(elems.get(3).getText().contains("Los"));
	}
	
	@Then("I cannot access {string} page")
	public void I_cannot_access(String string) {
		driver.get(ROOT_URL + string + ".html");
		System.out.println(driver.getCurrentUrl());
		check_title_is_login();
	}
	
	@Then("{string} is indicated")
	public void is_indicated(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("homeButton"))));
		String rgbFormat = driver.findElement(By.id(string)).getCssValue("background-color");
		System.out.println(rgbFormat);     //In RGB Format the value will be print => rgba(254, 189, 105, 1)
		String hexcolor = Color.fromString(rgbFormat).asHex(); //converted Into HexFormat
		System.out.println(hexcolor);// Output of Hex code will be  => #febd69
		assertTrue(hexcolor.equals("#aaaaaa"));
	}

	@After()
	public void after() {
		driver.quit();
	}
}
