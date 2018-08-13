package com.Vision.pageHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Vision.locators.LocatorReader;
import com.Vision.util.DriverHelper;
import com.Vision.util.PropertyReader;

public class CommonHelper extends DriverHelper
{
	private LocatorReader locatorReader;			
	protected PropertyReader propertyReader = new PropertyReader();		
	//------------------------------------Constructor-------------------------------------------------
	public CommonHelper(WebDriver webDriver) 			
	{			
		super(webDriver);		
		locatorReader = new LocatorReader("Vision.xml");			
	}
	//-------------------------------Enter text into the input field-------------------------------------------
	public void type(String field, String text)			
	{			
		String locator = locatorReader.getLocator(field);
		sendKeys(locator, text);	
	}	
	//---------------------------------Click on Button---------------------------------------------------------
	public void click(String button)			
	{	
		String locator = locatorReader.getLocator(button);
		waitForElementPresent(locator,5);
		clickOn(locator);		
	}	
	//---------------------------------Click on Button---------------------------------------------------------
		public void doubleClick(String button)			
		{	
			String locator = locatorReader.getLocator(button);
			waitForElementPresent(locator,5);
			doubleClickOn(locator);		
		}	
	//-------------------------------------Verify Element Present------------------------------------------------
	public void verifyElementPresent(String element, int timeout)
	{
		String locator = locatorReader.getLocator(element);
		waitForElementPresent(locator,timeout);
	}

	//---------------------------------------------Verify element----------------------------------------------
	public boolean verifyElement(String field)
	{
		String locator = locatorReader.getLocator(field);
		return isElementPresent(locator);
	}

	//---------------------------------------------Verify URL----------------------------------------------
	public boolean verifyURL(String URL)
	{
		boolean result=false;
		if(driver.getCurrentUrl().contains(URL))
		{
			result=true;
		}
		return result;
	}

	//--------------------------------Click by Javascript--------------------------------------------
	public void clickByJava(String button)
	{
		String locator = locatorReader.getLocator(button);
		clickOnByJava(locator);
	}
	//----------------------------------Clear the field text-------------------------------------------
	public void clearFieldText(String field)
	{
		String locator = locatorReader.getLocator(field);
		clearField(locator);
	}
	//--------------------To select first element available on the page---------------------------------
	public void clickOnElment(String xpath,int position)
	{
		String locator = locatorReader.getLocator(xpath);
		waitForElementPresent(locator, 20);
		List<WebElement> el = getWebDriver().findElements(byLocator(locator));
		el.get(position).click();
	}
	//--------------------------To get the count of element of same locator------------------------------
	public int getCount(String xpath)
	{
		String locator = locatorReader.getLocator(xpath);
		return getXpathCount(locator);
	}
	//----------------------------------To get the value of a attribute-----------------------------------
	public String getAttributeValue(String field, String attribute)
	{
		String locator = locatorReader.getLocator(field);
		return getAttribute(locator, attribute);
	}
	//-----------------------------------------Select menu from left panel--------------------------------------
		public void selectFromLeftPan(String Option)
		{
			switch(Option)
			{
			case "Dashboard":
				String dashboard = locatorReader.getLocator("Dashboard");
				verifyElementPresent("Dashboard", 5);
				clickOn(dashboard);
				break;
			case "Create an Estimate":
				String createAnEstimate = locatorReader.getLocator("CreateAnEstimate");
				verifyElementPresent("CreateAnEstimate", 5);
				clickOn(createAnEstimate);
				break;
			case "My Estimates":
				String myEstimates = locatorReader.getLocator("MyEstimates");
				verifyElementPresent("MyEstimates", 5);
				clickOn(myEstimates);
				break;
			case "My Matters":
				String myMatters = locatorReader.getLocator("MyMatters");
				verifyElementPresent("MyMatters", 5);
				clickOn(myMatters);
				break;
			case "My Tasks":
				String myTasks = locatorReader.getLocator("MyTasks");
				verifyElementPresent("MyTasks", 5);
				clickOn(myTasks);
				break;
			case "View all Matter/Estimate":
				String viewAllMatterEstimate = locatorReader.getLocator("ViewAllMatterEstimates");
				verifyElementPresent("ViewAllMatterEstimates", 5);
				clickOn(viewAllMatterEstimate);
				break;
			case "Reports":
				String reports = locatorReader.getLocator("Report");
				verifyElementPresent("Report", 5);
				clickOn(reports);
				break;
			case "Matter Templates":
				String matterTemplates = locatorReader.getLocator("MatterTemplates");
				verifyElementPresent("MatterTemplates", 5);
				clickOn(matterTemplates);
				break;
			case "Historical Matters":
				String historicalMatters = locatorReader.getLocator("HistoricalMatter");
				verifyElementPresent("HistoricalMatter", 5);
				clickOn(historicalMatters);
				break;
			case "Set Margins":
				String setMargins = locatorReader.getLocator("SetMargin");
				verifyElementPresent("SetMargin", 5);
				clickOn(setMargins);
				break;
			case "Leverages":
				String laverages = locatorReader.getLocator("Laverages");
				verifyElementPresent("Laverages", 5);
				clickOn(laverages);
				break;
			case "Security":
				String security = locatorReader.getLocator("Security");
				verifyElementPresent("Security", 5);
				clickOn(security);
				break;
			case "About":
				String about = locatorReader.getLocator("About");
				verifyElementPresent("About", 5);
				clickOn(about);
				break;
			default:System.out.println("Option not found which you have selected from left Pan");

			}

		}

	//--------------------------------------switch to child window---------------------------------------------
	public void selectWindow()
	{
		//String homeWindow = getWebDriver().getWindowHandle();
		Set<String> allWindows = getWebDriver().getWindowHandles();

		//Use Iterator to iterate over windows
		Iterator<String> windowIterator =  allWindows.iterator();

		//Verify next window is available
		while(windowIterator.hasNext())
		{
			//Store the Recruiter window id
			String childWindow = windowIterator.next();
			getWebDriver().switchTo().window(childWindow);
		}
	}
	
}
