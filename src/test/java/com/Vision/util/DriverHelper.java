package com.Vision.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
//import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class DriverHelper
{
	private PropertyReader propertyReader;
	DriverTestCase driverTestCase;
	//=======================================================================Define objects
	protected WebDriver driver;

	//=======================================================================Declare objects
	public DriverHelper(WebDriver webdriver)
	{
		driver = webdriver;
	}

	//===============================================================Return web driver object
	public WebDriver getWebDriver() 
	{
		return driver;  		
	}

	//================================================================Print message on screen
	public void log(String logMsg) 
	{
		System.out.println(logMsg);
	}

	//====================================================================Handle locator type
	public By byLocator(String locator) 
	{
		By result = null;

		if (locator.startsWith("//")) 
		{
			result = By.xpath(locator);
		} 
		else if (locator.startsWith("css=")) 
		{
			result = By.cssSelector(locator.replace("css=", ""));
		} 
		else if (locator.startsWith("#")) 
		{
			result = By.name(locator.replace("#", ""));
		} 
		else if (locator.startsWith("link=")) 
		{
			result = By.linkText(locator.replace("link=", ""));
		} 
		else {
			result = By.id(locator.replace("id=", ""));
		}
		return result;
	}

	//=======================================================================Assert element present
	public Boolean isElementPresent(String locator) 
	{
		Boolean result = false;
		try 
		{
			getWebDriver().findElement(byLocator(locator));
			result = true;
		} 
		catch (Exception ex) 
		{
		}
		return result;
	}

	//=======================================================================Verify is element present	
	public Boolean isElementDisplay(String locator)
	{
		Boolean result = false;
		try 
		{
			getWebDriver().findElement(byLocator(locator)).isDisplayed();
			result = true;
		} 
		catch (Exception ex) 
		{
		}
		return result;
	}

	//======================================================================Wait for element present
	public void waitForElementPresent(String locator, int timeout) 
	{
		for (int i = 0; i < timeout; i++) 
		{
			if (isElementPresent(locator)) 
			{
				break;
			}

			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	//=========================================================wait until element displayed on screen	
	public void waitForElementDisplay(String locator, int timeout) 
	{
		for (int i = 0; i < timeout; i++) 
		{
			if (isElementDisplay(locator)) 
			{
				break;
			}

			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	//=============================================================================Handle click action
	public void clickOn(String locator) 
	{
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"
				+ locator + " Not found");
		this.waitForElementDisplay(locator, 30);
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.click();
	}
	//=============================================================================Handle click action
	public void doubleClickOn(String locator)
	{
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator),"Element Locator :"
				+locator+ "Notfound");
		this.waitForElementDisplay(locator,30);
		WebElement el = getWebDriver().findElement(byLocator(locator));
		try{
			Actions action = new Actions(getWebDriver()).doubleClick(el);
			action.build().perform();
		}catch(Exception e){e.printStackTrace();}
	}
	//=====================================================================Handle click action by java
	public void clickOnByJava(String locator)
	{
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"
				+ locator + " Not found");
		this.waitForElementDisplay(locator, 30);
		WebElement el = getWebDriver().findElement(byLocator(locator));

		JavascriptExecutor executor = (JavascriptExecutor)getWebDriver();
		executor.executeScript("arguments[0].click();", el);
	}
	//=======================================================================Handle send keys action
	public void sendKeys(String locator, String  str) 
	{
		this.waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.clear();
		el.sendKeys(str);
	}
	//=======================================================================Send keys to text field	
	public void clearField(String locator)
	{
		this.waitForElementPresent(locator, 30);
		this.waitForElementDisplay(locator, 30);
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.clear();
	}
	//=====================================================================Store text from a locator
	public String getText(String locator) {
		waitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"
				+ locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getText();
		return text;
	}
	//===========================================================================Get attribute value
	public String getAttribute(String locator, String attribute) 
	{
		waitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"
				+ locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator))
				.getAttribute(attribute);
		return text;
	}
	//====================================================================get xpath count of element
	public Integer getXpathCount(String locator)
	{
		waitForElementPresent(locator, 30);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :"
				+ locator + " Not found");
		int a = driver.findElements(By.xpath(locator)).size();
		return a;
	}
	//=====================================================================================hard wait
	public void waitForWorkAroundTime(int timeout)
	{
		try 
		{
			Thread.sleep(timeout);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	//===========================================================================verify browser title
	public void verifyTitle(String title)
	{
		String actualTitle=getWebDriver().getTitle();
		Assert.assertTrue(actualTitle.contains(title));
	}
	//===========================================================================select from dropdown	
	public void select(String DropdownLocator, String ValueLocator) throws Exception
	{
		getWebDriver().findElement(byLocator(DropdownLocator)).click();
		getWebDriver().wait(5);;
		getWebDriver().findElement(byLocator(DropdownLocator)).findElement(byLocator(ValueLocator)).click();
	}
	//=======================================================================verify attribute present
	public boolean isAttributePresent(String locator, String attribute) 
	{
		Boolean result = false;
		try
		{
			String value = getWebDriver().findElement(byLocator(locator)).getAttribute(attribute);
			if(value!=null)
			{
				result = true;
			}

		}catch (Exception e) {}
		return result;
	}
	//==========================================================================match attribute value
	public boolean isAttributeValueMatch(String locator, String attribute, String attributeValue)
	{
		Boolean result = false;
		try
		{
			String value = getWebDriver().findElement(byLocator(locator)).getAttribute(attribute);
			if(value.equals(attributeValue)==true)
			{
				result=true;
			}
		}catch(Exception e){}
		return result;
	}
	//============================================================================get attribute value
	public String getAttributeValue(String locator, String attribute)
	{
		String attributeValue="";
		try
		{
			attributeValue = getWebDriver().findElement(byLocator(locator)).getAttribute(attribute);
			
		}catch(Exception e){}
		return attributeValue;
	}
	
	//===============================================================================hover with mouse
	public void mouseOver(String locator)
	{
		Actions action = new Actions(getWebDriver());
		action.moveToElement(getWebDriver().findElement(byLocator(locator))).perform();
	}
	//============================================================================return today's date
	public String getTodayDate() 
	{
		propertyReader = new PropertyReader();
		String dateToReturn = "";
		if(propertyReader.readApplicationFile("Browser_Language").equals("en-GB")){
		Calendar c=Calendar.getInstance();
		DateFormat dm = new SimpleDateFormat("dd/MM/yyyy");		
		dateToReturn =  dm.format(c.getTime());
		}
		if(propertyReader.readApplicationFile("Browser_Language").equals("en-US")){
			Calendar c=Calendar.getInstance();
			DateFormat dm = new SimpleDateFormat("MM/dd/yyyy");		
			dateToReturn =  dm.format(c.getTime());
			}
		return dateToReturn;
	}
	//=============================================================================return future date
	public String addInCurrentDate(int daysToAdd)
	{
		propertyReader = new PropertyReader();
		String dateToReturn = "";
		if(propertyReader.readApplicationFile("Browser_Language").equals("en-GB")){
		Calendar c=Calendar.getInstance();
		DateFormat dm = new SimpleDateFormat("dd/MM/yyyy");
		c.add(Calendar.DAY_OF_MONTH, daysToAdd);
		dateToReturn =  dm.format(c.getTime());
		}
		if(propertyReader.readApplicationFile("Browser_Language").equals("en-US")){
			Calendar c=Calendar.getInstance();
			DateFormat dm = new SimpleDateFormat("MM/dd/yyyy");
			c.add(Calendar.DAY_OF_MONTH, daysToAdd);
			dateToReturn =  dm.format(c.getTime());
		}
		return dateToReturn;
	}
	public String convertDateMMDDYYYY(String DateToConvert){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String newDateFormat = DateToConvert;
		try {  
	        Date date = formatter.parse(DateToConvert); 
	        SimpleDateFormat dm = new SimpleDateFormat("MM/dd/yyyy");
	        newDateFormat =  dm.format(date.getTime());
		} catch (ParseException e) {e.printStackTrace();}
		return newDateFormat;
	}
	public String convertDateToDDMMYYYY(String DateToConvert){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String newDateFormat = DateToConvert;
		try {  
	        Date date = formatter.parse(DateToConvert); 
	        SimpleDateFormat dm = new SimpleDateFormat("dd/MM/yyyy");
	        newDateFormat =  dm.format(date.getTime());
		} catch (ParseException e) {e.printStackTrace();}
		return newDateFormat;
	}
	public String convertDateToEEEEMMMMddyyyy(String DateToConvert){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String newDateFormat = DateToConvert;
		try {
			Date date = formatter.parse(DateToConvert);
			SimpleDateFormat dm = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
            newDateFormat = dm.format(date);
		}catch(ParseException e){e.printStackTrace();}
		return newDateFormat;
	}
	public String convertDateToYYYYMMDD(String DateToConvert){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String newDateFormat = DateToConvert;
		try {  
	        Date date = formatter.parse(DateToConvert); 
	        SimpleDateFormat dm = new SimpleDateFormat("yyyy/MM/dd");
	        newDateFormat =  dm.format(date);
		} catch (ParseException e) {e.printStackTrace();}
		return newDateFormat;
	}
}