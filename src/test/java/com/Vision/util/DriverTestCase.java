package com.Vision.util;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DriverTestCase
{
	enum DriverType 
	{ Firefox, IE, Chrome }

	//Define objects
	static WebDriver driver;
	public DriverTestCase drivertestCase;
	private PropertyReader propertyReader;
	Scenario scenario;
	//---------------------------------------------------------------------------------------------------------
	static boolean demo=true;

	@Before
	public void seUp(Scenario scenario)
	//public void seUp()
	{
		this.scenario=scenario;
	/*	propertyReader = new PropertyReader();

		String driverType = propertyReader.readApplicationFile("BROWSER");  

		if (DriverType.Firefox.toString().equals(driverType)) 
		{ 
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();   
		}
		else if (DriverType.IE.toString().equals(driverType)) 
		{ 
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
		}
		else
		{
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
		}

		//Delete cookies
		driver.manage().deleteAllCookies();

		//Maximize window
		driver.manage().window().maximize();
		driver.navigate().to(propertyReader.readApplicationFile("URL"));*/
		Reporter.assignAuthor("Ashish Khanduri");
		

	}
	//---------------------------------------------------------------------------------------------------------
	   @Given("^Open Browser and navigate to application URL$")
	    public void open_browser_and_navigate_to_application_url()	{
		   propertyReader = new PropertyReader();

			String driverType = propertyReader.readApplicationFile("BROWSER");  

			if (DriverType.Firefox.toString().equals(driverType)) 
			{ 
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();   
			}
			else if (DriverType.IE.toString().equals(driverType)) 
			{ 
				WebDriverManager.iedriver().arch32().setup();
				driver = new InternetExplorerDriver();
			}
			else
			{
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--lang="+propertyReader.readApplicationFile("Browser_Language"));
				driver = new ChromeDriver(options);
			}

			//Delete cookies
			driver.manage().deleteAllCookies();

			//Maximize window
			driver.manage().window().maximize();
			driver.navigate().to(propertyReader.readApplicationFile("URL"));

	   }
	   
	   @Then("^Close the browser$")
	    public void close_the_browser() throws Throwable {
		   driver.quit(); 
	    }
	public WebDriver getWebDriver()
	{
		return driver;
	}

	@After
	public void quitDriver() throws IOException
	{
		if(scenario.isFailed())
		{
			System.out.println("Scenario Name Is: "+scenario.getName());
			Reporter.addScreenCaptureFromPath(captureScreenshot());
			//driver.quit();
		}
		System.out.println("Scenario Name Is: "+scenario.getName());
		Reporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	    Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	    Reporter.setSystemInfo("Machine", 	"Windows 10" +" " + "64 Bit");
	    Reporter.setSystemInfo("Selenium", "3.7.0");
	    Reporter.setSystemInfo("Maven", "3.5.2");
	    Reporter.setSystemInfo("Java Version", "1.8.0_151");
	    Reporter.setSystemInfo("Application URL", propertyReader.readApplicationFile("URL"));
	    Reporter.setSystemInfo("Database Server", propertyReader.readApplicationFile("DBSERVER"));
	    Reporter.setSystemInfo("Database Name", propertyReader.readApplicationFile("DBNAME"));
		//driver.quit();
	}

	//---------------------------------------------------------------------------------------------------------
	//Get absolute path
	public String getPath()
	{
		String path ="";		
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");		
		return path;
	}

	public String captureScreenshot() throws IOException
	{
		String scnarioName="";
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		scnarioName = scenario.getName().replace(" ","_");
		File DestFile=new File(System.getProperty("user.dir")+"/screenshots/"+scnarioName+".png");
		FileUtils.copyFile(SrcFile, DestFile);
		return System.getProperty("user.dir")+"/screenshots/"+scnarioName+".png";

	}
}
