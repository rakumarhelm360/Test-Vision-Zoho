package com.Vision.runnerClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
//import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.TestNGCucumberRunner;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"classpath:features"},
		glue = {"com.Vision.step_definations","com.Vision.util","com.Vision.pageHelper"},
				plugin = {"com.cucumber.listener.ExtentCucumberFormatter:VisionAutomation.html"},
		//format = {"html:target/cucumber-html-report/cucumber-pretty"},
		tags = {"@Estimate"}
		)

public class RunnerClass
{	
	@BeforeClass
	public void test()
	{
		System.out.println("Execution Started");
	}
	
	@Test
	public void runCukes()
	{
		new TestNGCucumberRunner(getClass()).runCukes();
	}

	@AfterClass
	public static void writeExtentReport()
	{
		//Reporter.loadXMLConfig(System.getenv("user.dir")+"/extent-config.xml");
		Reporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	    Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	    Reporter.setSystemInfo("Machine", 	"Windows 10" + "64 Bit");
	    Reporter.setSystemInfo("Selenium", "3.7.0");
	    Reporter.setSystemInfo("Maven", "3.5.2");
	    Reporter.setSystemInfo("Java Version", "1.8.0_151");
	}

}
