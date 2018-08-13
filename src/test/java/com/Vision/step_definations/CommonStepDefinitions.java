package com.Vision.step_definations;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;

import com.Vision.pageHelper.CommonHelper;
import com.Vision.util.DriverTestCase;
import com.Vision.util.PropertyReader;
import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;

import cucumber.api.java.ContinueNextStepsFor;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
//import ch.qos.logback.classic.Logger;
public class CommonStepDefinitions
{
	CommonHelper commonHelper;
	DriverTestCase driverTestCase;
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	String EstimateName = null;
	protected PropertyReader propertyReader = new PropertyReader();

	public CommonStepDefinitions()
	{
		driverTestCase = new DriverTestCase();
		commonHelper = new CommonHelper(driverTestCase.getWebDriver());
	}

	@When("^Verify user is at Login Page$")
	public void verify_that_I_am_login_page() throws Throwable
	{
		commonHelper.verifyTitle("Terminus Vision");
		commonHelper.verifyURL("http://192.168.1.103:81/Account/Login?ReturnUrl=%2F");
		commonHelper.waitForWorkAroundTime(3000);
	}

	@When("^Enter username as \"(.*?)\"$")
	public void i_enter_username_as(String arg1) throws Throwable 
	{
		String Username = propertyReader.readApplicationFile("Admin_User");
		commonHelper.type("Username", Username);
	}

	@When("^Enter password as \"(.*?)\"$")
	public void i_enter_password_as(String arg1) throws Throwable 
	{
		String Password = propertyReader.readApplicationFile("Admin_Passowrd");
		commonHelper.type("Password", Password);
		Reporter.addStepLog(Status.FAIL+" Test Fail");
	}

	@When("^Click on login button$")
	public void i_click_on_login_button() throws Throwable 
	{
		commonHelper.click("Login");
	}

	@Then("^Verify user should Redirected to \"(.+?)\" page$")
	public void i_should_redirect_to(String Page)
	{
		commonHelper.verifyElementPresent(Page, 5);
		Assert.assertTrue(commonHelper.verifyElement(Page));
	}
	@When("^Logout from the application$")
	public void logout_from_the_application()
	{
		commonHelper.waitForWorkAroundTime(200);
		commonHelper.click("LogOff");
	}

	@When("^Click on login1 button$")
	public void i_click_on_login1_button() throws Throwable 
	{
		commonHelper.click("Login1");
	}

	//DUMMMMMYYYYYYYYYY
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Step1 go to$")
	public void step1_go_to() throws Throwable
	{
		System.out.println("Step 1");
		 throw new AssertionError("Addition is incorrect");	
	}

	@Then("^Step2 verify$")
	public void stpe2_verify() throws Throwable
	{
		System.out.println("Step 2");
	}

	@Then("^Step3 verify test$")
	public void step3_verify_test()
	{
		System.out.println("Step 3");
	}
}
