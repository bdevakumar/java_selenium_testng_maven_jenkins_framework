package company.project.tests;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver; 
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


public abstract class WebDriverTestBase {

	protected static WebDriver driver; 

	@BeforeMethod
	@Parameters({"browser"}) 
	public void testSetUp(String browser){
		// instantiate the driver, maximize window, and set implicit wait time 
 
		switch(browser) {
		case "Edge":
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "company" + File.separator + "project" + File.separator + "utils" + File.separator + "testData" + File.separator +  "drivers" + File.separator +  "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();	
			break;
		case "Firefox":
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "company" + File.separator + "project" + File.separator + "utils" + File.separator + "testData" + File.separator +  "drivers" + File.separator +  "geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "company" + File.separator + "project" + File.separator + "utils" + File.separator + "testData" + File.separator +  "drivers" + File.separator +  "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("useAutomationExtension", false);
		    driver = new ChromeDriver(options);
			break;
		case "IE11":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "company" + File.separator + "project" + File.separator + "utils" + File.separator + "testData" + File.separator +  "drivers" + File.separator +  "IEDriverServer.exe");
			driver = new InternetExplorerDriver();	
			break; 
		default:
			throw new IllegalArgumentException("Invalid argument: " + browser);
	}
 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void testTeardown() {
		driver.quit();
	}

}
