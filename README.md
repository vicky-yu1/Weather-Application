# CSCI 310 Project: Weather-Application

Testing Tools Used:  
-TDD Unit test: JUnit  
-Coverage test: Cobertura  
-Acceptance test: Cucumber Java Selenium WebDriver  
-Continuous Integration: Travis-CI  

APIs used:  
-OpenWeather API  
-Google Places API  

== Set-up ==
1. Install Java Development Kit 8: https://www.oracle.com/java/technologies/javase-jdk8-downloads.html
2. Download/install Eclipse IDE for Java: https://www.eclipse.org/downloads
	* IMPORTANT: When installing the Eclipse IDE, when given the option of "Java 1.8+ VM" be sure to choose from the drop-down the JDK instead of the JRE. By default the installer will pick the JRE, which will not work with Maven.
3. Install Google Chrome: https://www.google.com/chrome/
4. Open Eclipse IDE. 
5. Go to File -> Import, Git -> Projects from Git, Clone URI:
	* For URI: Copy the link from the green "Clone or download" button.
	* For Authentication section fill in your GitHub username/password
	* For the remaining options in the wizard you can just click "Next" and "Finish", the default settings are correct.

== Usage ==  
To run JUnit tests:
Right click project -> Run As -> "Maven test".

To run JUnit tests with coverage:
We are using Eclipse's built-in coverage reporting tool for this.
Expand src/test/java, right click the "csci310" package -> Coverage As -> JUnit Test

To host your web app:  
Please make sure to replace connector files with your personal API Key prior to running.  
1) Go to "Run Configurations".  
2) Click on the environment tab and ensure that the environment variable is the correct path to Weather.json file.  
   ie. Variable: GOOGLE_APPLICATION_CREDENTIALS  
       Value: C:\Users\yuvic\git\Weather-Application\Weather.json
3) Run the "run" configuration.  
4) Open Google Chrome (or your preferred web browser) and navigate to http://localhost:8080 to access the application.  
5) You can stop the web server by clicking the red "stop" button in the console window.  

To run Cucumber tests:
Make sure the webserver is running when you run the Cucumber tests (using the steps for hosting the web app above).
Go to "Run Configurations", run the "cucumber" configuration.
