package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
//@CumberOptions(strict = true, features = {"src/test/resources/cucumber/LikesPersistence.feature"})
//@CcucumberOptions(strict = true, features = {"src/test/resources/cucumber/FavoritesPersistence.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/SearchHistoryPersistence.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/Logout.feature", "src/test/resources/cucumber/login1.feature", "src/test/resources/cucumber/login2.feature",
//		"src/test/resources/cucumber/register1.feature", "src/test/resources/cucumber/register2.feature"})
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/SearchHistory1.feature"})

//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/Pagination1.feature", "src/test/resources/cucumber/Pagination2.feature"})	
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/Radius.feature"})	
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/CurrentPage.feature"})	
@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/SortLikes.feature", "src/test/resources/cucumber/SortLikes2.feature",
		"src/test/resources/cucumber/SortLikes3.feature", "src/test/resources/cucumber/SortLikes4.feature",
		"src/test/resources/cucumber/SortLikes5.feature"}) 

public class RunCucumberTests {
	

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}

}