package com.Vision.pageHelper;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Vision.locators.LocatorReader;
import com.Vision.util.DriverHelper;
import com.Vision.util.PropertyReader;
import com.cucumber.listener.Reporter;

public class EstimateHelper extends DriverHelper				
{				
	private LocatorReader locatorReader;			
	protected PropertyReader propertyReader = new PropertyReader();		
	protected WriteDataToFileHelper writeToFile = new WriteDataToFileHelper();
	//---------------------------------------------------------------------------------------------------------
	public EstimateHelper(WebDriver webDriver) 			
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
	//---------------------------------------Get Field's Text--------------------------------------------------
	public String getFieldText(String field)
	{
		String locator = locatorReader.getLocator(field);
		return getText(locator);
	}
	//-------------------------To select first element available on the page-----------------------------------
	public void clickOnElment(String xpath,int position)
	{
		String locator = locatorReader.getLocator(xpath);
		waitForElementPresent(locator, 20);
		List<WebElement> el = getWebDriver().findElements(byLocator(locator));
		el.get(position).click();
	}
	//-------------------------------------Verify Element Present------------------------------------------------
	public void verifyElementPresent(String element, int timeout)
	{
		String locator = locatorReader.getLocator(element);
		waitForElementPresent(locator,timeout);
	}
	public void selectResourcefromdb(String resource) 
	{
		clickOn("//span[contains(text(),'"+resource+"')]");
	}
	public void selectMatterTypeAndRateType() {
		String matterTypeDropdown = locatorReader.getLocator("MatterTypeDropdown");
		String rateTypeDropdown = locatorReader.getLocator("RateTypeDropdown");
		String matterTypeValue = locatorReader.getLocator("MatterTypeValue");
		String rateTypeValue = locatorReader.getLocator("RateTypeValue");
		clickOn(matterTypeDropdown);clickOn(matterTypeValue);
		clickOn(rateTypeDropdown);clickOn(rateTypeValue);
	}
	public void verifyResourcesInDropdown(String[] activeResources,
			String[] inActiveResources) {
		int count=0;
		String locator = locatorReader.getLocator("ResourcesDropdownSize");
		int liSize = getXpathCount(locator);
		for(int i=0;i<activeResources.length;i++){
			for(int j=i+1;j<=liSize;j++) {
				String ResourcesText = getText("//div[@id='ddl_Resourcesundefined-list']//ul[@id='ddl_Resourcesundefined_listbox']//li["+j+"]");
				if(activeResources[i].equals(ResourcesText)==true) {
					count++;
					break;
				}
			}
			if(count>0){
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		int countInActive=0;
		for(int k=0;k<inActiveResources.length;k++){
			for(int l=k+1;l<=liSize;l++){
				String ResourcesText = getText("//div[@id='ddl_Resourcesundefined-list']//ul[@id='ddl_Resourcesundefined_listbox']//li["+l+"]");
				if(inActiveResources[k].equals(ResourcesText)==true){
					countInActive++;
					break;
				}
			}
			if(countInActive>0){
				Assert.assertTrue(false);
			}
		}

	}
	public void verifyNewLineAdded(String newLineLocator) {
		String locator = locatorReader.getLocator(newLineLocator);
		boolean verify = isElementPresent(locator);
		Assert.assertTrue(verify);	
	}
	public void verifyActivityFieldIsRequired() {
		String locator = locatorReader.getLocator("Activity");
		boolean available = isAttributePresent(locator,"required");
		Assert.assertTrue(available);
	}
	public void validateStartAndEndDate() {
		String startDateField = locatorReader.getLocator("StartDateField");
		String endDateField = locatorReader.getLocator("EndDateField");
		String dateError = locatorReader.getLocator("DateError");
		clickOn(startDateField);clickOn(startDateField);
		waitForWorkAroundTime(900);
		type("FStartDateInput",addInCurrentDate(5));
		clickOn(endDateField);clickOn(endDateField);
		waitForWorkAroundTime(900);
		boolean error = isElementPresent(dateError);
		Assert.assertTrue(error);
	}
	public void enterValidStartAndEndDate() {
		String startDateField = locatorReader.getLocator("StartDateField");
		String endDateField = locatorReader.getLocator("EndDateField");
		clickOn(startDateField);
		waitForWorkAroundTime(1000);
		type("FStartDateInput",getTodayDate());
		waitForWorkAroundTime(500);
		clickOn(endDateField);
		waitForWorkAroundTime(900);
		clickOn(endDateField);
		waitForWorkAroundTime(500);
		type("FEndDateInput",addInCurrentDate(3));
		clickOn(startDateField);
	}
	public void verifyStdRateNonEditable() {
		String locator = locatorReader.getLocator("StdRate1Row");
		Assert.assertTrue(isAttributeValueMatch(locator,"role","gridcell"));
	}
	public void verifyTitleRateOnUI(String rate) {
		String locator = locatorReader.getLocator("StdRate1Row");
		String rateInString = getText(locator).substring(1);
		double rateFromDB = Double.parseDouble(rate);
		//System.out.println("Rate From DB : "+rateFromDB); //this is to print rate from database on console
		double rateFromUI = Double.parseDouble(rateInString);
		//System.out.println("Rate from UI : "+rateFromUI); // this is to print rate from ui on console
		if(rateFromDB==rateFromUI){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
		Reporter.addStepLog("Rate From UI : "+rateFromUI);
	}
	public boolean isPropose_rate_field_editable() {
		String ProposedRate1Row = locatorReader.getLocator("ProposedRate1Row");
		String ProposedRateFieldInput1Row = locatorReader.getLocator("ProposedRateFieldInput1Row");
		clickOn(ProposedRate1Row);waitForWorkAroundTime(500);
		clickOn(ProposedRateFieldInput1Row);
		if(getAttributeValue(ProposedRate1Row, "data-role").equals("editable")) {
			return true;
		}
		else {
			return false;
		}
	}
	public void verifyProposedRateFieldValues(String rate, String HourlyBudgetRow, String ProposedRateRow) {
		String HourlyBudget = locatorReader.getLocator(HourlyBudgetRow);
		clickOn(HourlyBudget);
		String locator = locatorReader.getLocator(ProposedRateRow);
		String rateInString = getText(locator).substring(1);
		double RateFromDB = Double.parseDouble(rate);
		double RateFromUI = Double.parseDouble(rateInString);
		Reporter.addStepLog("Proposed Rate From UI: "+RateFromUI);
		if(RateFromDB==RateFromUI){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
		Assert.assertTrue(isAttributeValueMatch(locator,"class","table-Editable-cell"));
	}
	public void verifyHourlyBudgetField(String rate, int hour, String fieldName) {
		String locator = locatorReader.getLocator(fieldName);
		String hourlyBudgetInString = getText(locator).substring(1).replace(",", "");
		//System.out.println("Hourly budget from UI"+hourlyBudgetInString); to print the hourly budget from ui on console
		double RateFromDB = Double.parseDouble(rate);
		double hourlyBudgetUI = Double.parseDouble(hourlyBudgetInString);
		double calculatedBudget = RateFromDB*hour;
		Reporter.addStepLog("Calculated Budget from Database is : Rate("+RateFromDB+")*"+" Hours("+hour+") = "+calculatedBudget);
		Reporter.addStepLog("Hourly Budgetin UI: "+hourlyBudgetUI);
		if(calculatedBudget==hourlyBudgetUI){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}

	}
	public void verifyValuesAtSider(String locator) {
		String activityRowLocator = locatorReader.getLocator("ActivityRow");
		double hourlyFeeBudget = 0;
		double totalHourlyFeeBudget = 0;
		String feeBudgetInString;
		int rowCounter = getXpathCount(activityRowLocator);
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Row1HourlyBudget = locatorReader.getLocator("Phase2Row1HourlyBudget");
		String Phase1Row6HourlyBudget = locatorReader.getLocator("Phase1Row6HourlyBudget");
		for(int i=1;i<=rowCounter;i++){
			feeBudgetInString = getText("//*[@id='tasks']/table//table/tbody/tr["+i+"]/td[9]").substring(1).replace(",", "");
			hourlyFeeBudget = Double.parseDouble(feeBudgetInString);
			totalHourlyFeeBudget = totalHourlyFeeBudget+hourlyFeeBudget;
		}
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			totalHourlyFeeBudget = totalHourlyFeeBudget+Double.parseDouble(getText(Phase2Row1HourlyBudget).substring(1).replace(",", ""));
		}
		else{
			totalHourlyFeeBudget = totalHourlyFeeBudget+Double.parseDouble(getText(Phase1Row6HourlyBudget).substring(1).replace(",", ""));
		}
		String hourlyFeeBudgetAtSlider = locatorReader.getLocator("HourlyFeeBudgetAtSlider");
		String hourlyFeeBudgetSlider = getText(hourlyFeeBudgetAtSlider).substring(1).replace(",", "");
		double hourlyFeeBudgetSliderInt = Double.parseDouble(hourlyFeeBudgetSlider);
		if(totalHourlyFeeBudget==hourlyFeeBudgetSliderInt){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
	}
	public double calculateStdRate(String timekeeperRate, String toRate,
			String fromRate) {
		double tkprRate = Double.parseDouble(timekeeperRate);
		double toRateDouble = Double.parseDouble(toRate);
		double fromUsdRate = Double.parseDouble(fromRate);
		double Result = tkprRate*(toRateDouble/fromUsdRate);
		double roundoffRate = Math.round(Result*100.0)/100.0;
		return roundoffRate;
	}
	public void verifystdRateOnUIForProf(double totalProfRate, String RateValueInUI) {
		String StdSecondRowLocator = locatorReader.getLocator(RateValueInUI);
		String StdRateSecondRow = getText(StdSecondRowLocator).substring(1).replace(",", "");
		double StdRateUI = Double.parseDouble(StdRateSecondRow);
		Reporter.addStepLog("Standard Rate from DATABASE: "+totalProfRate);
		Reporter.addStepLog("Standard Rate From UI: "+StdRateUI);
		if(StdRateUI==totalProfRate){
			Assert.assertTrue("Rate value is same on UI as Database and cacluated correctly",true);
		}
		else{
			Assert.assertTrue("Rate Value not matched on UI"+"At UI it is"+StdRateUI+"At Database it is "+totalProfRate, false);
		}
	}
	public void verifysavedDialoge(String string) {
		String locator = locatorReader.getLocator(string);
		waitForElementPresent(string,5);
		Assert.assertTrue(isElementPresent(locator));	
	}
	public void addNewReocrdInOtherPhaseIfAvailable(String EstimateType) {
		String AddNewRecord = locatorReader.getLocator("AddNewRecord");
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1Expand = locatorReader.getLocator("Phase2Task1Expand");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1AddRecordBtn = locatorReader.getLocator("Phase2Task1AddRecordBtn");
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			clickOn(Phase2Task1Expand);waitForWorkAroundTime(500);
			clickOn(Phase2Task1AddRecordBtn);waitForWorkAroundTime(500);
		}
		else
			clickOn(AddNewRecord);
	}
	public boolean isThirdLineAdded() {
		boolean status;
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");

		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true)
				status = true;
			else if(isElementPresent(Phase1Row6)==true)
				status = true;
			else
				status = false;
		}
		else if(isElementPresent(Phase1Row6)==true)
			status = true;
		else
			status = false;
		return status;
	}
	public String enterActivityForThirdRow(String string) {
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		String Phase2Row1Activity1 = locatorReader.getLocator("Phase2Row1Activity1");
		String Phase1Row6Activity1 = locatorReader.getLocator("Phase1Row6Activity1");
		//String Result;
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				sendKeys(Phase2Row1Activity1, string);
				return string;
			}
			else if(isElementPresent(Phase1Row6)==true){
				sendKeys(Phase1Row6Activity1, string);
				return string;
			}
			else
				return null;
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			sendKeys(Phase2Row1Activity1, string);
			return string;
		}
		else{
			sendKeys(Phase1Row6Activity1, string);
			return string;
		}
	}
	public void clickResourceForThird() {
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		String Phase2Row1ResourceField = locatorReader.getLocator("Phase2Row1ResourceField");
		String Phase2Row1ResourceDropdown = locatorReader.getLocator("Phase2Row1ResourceDropdown");
		String Phase1Row6ResourceOutL = locatorReader.getLocator("Phase1Row6ResourceOutL");
		String Phase1Row6Dropdown = locatorReader.getLocator("Phase1Row6Dropdown");
		//String Result;
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				clickOn(Phase2Row1ResourceField);waitForWorkAroundTime(500);
				clickOn(Phase2Row1ResourceField);waitForWorkAroundTime(900);
				clickOn(Phase2Row1ResourceDropdown);waitForWorkAroundTime(500);
			}
			else if(isElementPresent(Phase1Row6)==true){
				clickOn(Phase1Row6ResourceOutL);waitForWorkAroundTime(500);
				clickOn(Phase1Row6ResourceOutL);waitForWorkAroundTime(900);
				clickOn(Phase1Row6Dropdown);waitForWorkAroundTime(500);
			}
			else
				System.out.println("No new Row available for third activity");//if row not available just print message on console
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			clickOn(Phase2Row1ResourceField);waitForWorkAroundTime(500);
			clickOn(Phase2Row1ResourceField);waitForWorkAroundTime(900);
			clickOn(Phase2Row1ResourceDropdown);waitForWorkAroundTime(500);
		}
		else{
			clickOn(Phase1Row6ResourceOutL);waitForWorkAroundTime(500);
			clickOn(Phase1Row6ResourceOutL);waitForWorkAroundTime(900);
			clickOn(Phase1Row6Dropdown);waitForWorkAroundTime(500);
		}
	}
	public void enterHoursRowThird(String string) {
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		String Phase2Row1hourly1Field = locatorReader.getLocator("Phase2Row1hourly1Field");
		String Phase2Row1hourlyValue = locatorReader.getLocator("Phase2Row1hourlyValue");
		String Phase1Row6HourlyF = locatorReader.getLocator("Phase1Row6HourlyF");
		String Phase1Row6HourlyV = locatorReader.getLocator("Phase1Row6HourlyV");
		String Phase2Row1HourlyBudget = locatorReader.getLocator("Phase2Row1HourlyBudget");
		String Phase1Row6HourlyBudget = locatorReader.getLocator("Phase1Row6HourlyBudget");
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				clickOn(Phase2Row1hourly1Field);waitForWorkAroundTime(1000);
				sendKeys(Phase2Row1hourlyValue, string);waitForWorkAroundTime(500);
				clickOn(Phase2Row1HourlyBudget);waitForWorkAroundTime(700);

			}
			else if(isElementPresent(Phase1Row6)==true){
				clickOn(Phase1Row6HourlyF);waitForWorkAroundTime(800);
				sendKeys(Phase1Row6HourlyV, string);waitForWorkAroundTime(500);
				clickOn(Phase1Row6HourlyBudget);waitForWorkAroundTime(500);
			}
			else
				System.out.println("No new Row available for third activity");//if row not available just print message on console
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			clickOn(Phase2Row1hourly1Field);waitForWorkAroundTime(800);
			sendKeys(Phase2Row1hourlyValue, string);waitForWorkAroundTime(500);
			clickOn(Phase2Row1HourlyBudget);waitForWorkAroundTime(500);
		}
		else{
			clickOn(Phase1Row6HourlyF);waitForWorkAroundTime(800);
			sendKeys(Phase1Row6HourlyV, string);waitForWorkAroundTime(500);
			clickOn(Phase1Row6HourlyBudget);waitForWorkAroundTime(800);
		}
	}
	public void enterProposeRate(String hour) {
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		String Phase2Row1ProposedRate = locatorReader.getLocator("Phase2Row1ProposedRate");
		String Phase2Row1ProPoseValue = locatorReader.getLocator("Phase2Row1ProPoseValue");
		String Phase1Row6ProposeField = locatorReader.getLocator("Phase1Row6ProposeField");
		String Phase1Row6ProposedValue = locatorReader.getLocator("Phase1Row6ProposedValue");
		String Phase2Row1HourlyBudget = locatorReader.getLocator("Phase2Row1HourlyBudget");
		String Phase1Row6HourlyBudget = locatorReader.getLocator("Phase1Row6HourlyBudget");
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				clickOn(Phase2Row1ProposedRate);waitForWorkAroundTime(500);
				clickOn(Phase2Row1ProposedRate);waitForWorkAroundTime(800);
				sendKeys(Phase2Row1ProPoseValue, hour);waitForWorkAroundTime(500);
				clickOn(Phase2Row1HourlyBudget);waitForWorkAroundTime(600);
			}
			else if(isElementPresent(Phase1Row6)==true){
				clickOn(Phase1Row6ProposeField);waitForWorkAroundTime(500);
				clickOn(Phase1Row6ProposeField);waitForWorkAroundTime(800);
				sendKeys(Phase1Row6ProposedValue, hour);waitForWorkAroundTime(500);
				clickOn(Phase1Row6HourlyBudget);waitForWorkAroundTime(600);
			}
			else
				System.out.println("No new Row available for third activity");//if row not available just print message on console
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			clickOn(Phase2Row1ProposedRate);waitForWorkAroundTime(500);
			clickOn(Phase2Row1ProposedRate);waitForWorkAroundTime(800);
			sendKeys(Phase2Row1ProPoseValue, hour);waitForWorkAroundTime(500);
			clickOn(Phase1Row6HourlyBudget);waitForWorkAroundTime(600);
		}
		else{
			clickOn(Phase1Row6ProposeField);waitForWorkAroundTime(500);
			clickOn(Phase1Row6ProposeField);waitForWorkAroundTime(800);
			sendKeys(Phase1Row6ProposedValue, hour);waitForWorkAroundTime(500);
			clickOn(Phase1Row6HourlyBudget);waitForWorkAroundTime(600);
		}
	}
	public void verifyStdRateForLastRecord(double totalProfRate) {
		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				verifystdRateOnUIForProf(totalProfRate, "Phase3Row3StdRate");			
			}
			else if(isElementPresent(Phase1Row6)==true){
				verifystdRateOnUIForProf(totalProfRate, "Phase1Row6StdRate");
			}
			else
				System.out.println("No new Row available for third activity");//if row not available just print message on console
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			verifystdRateOnUIForProf(totalProfRate, "Phase3Row3StdRate");
		}
		else{
			verifystdRateOnUIForProf(totalProfRate, "Phase1Row6StdRate");
		}
	}
	public void verifyBudgetForThird(double totalProfRate, int hour) {

		String Phase2 = locatorReader.getLocator("Phase2");
		String Phase2Task1 = locatorReader.getLocator("Phase2Task1");
		String Phase2Task1Rows = locatorReader.getLocator("Phase2Task1Rows");
		String Phase1Row6 = locatorReader.getLocator("Phase1Row6");
		if(isElementPresent(Phase2)==true && isElementPresent(Phase2Task1)==true) {
			if(isElementPresent(Phase2Task1Rows)==true){
				verifyHourlyBudgetField(String. valueOf(totalProfRate), hour, "Phase2Row1HourlyBudget");			
			}
			else if(isElementPresent(Phase1Row6)==true){
				verifyHourlyBudgetField(String. valueOf(totalProfRate), hour, "Phase1Row6HourlyBudget");
			}
			else
				System.out.println("No new Row available for third activity");//if row not available just print message on console
		}
		else if(isElementPresent(Phase2Task1Rows)==true){
			verifyHourlyBudgetField(String. valueOf(totalProfRate), hour, "Phase2Row1HourlyBudget");
		}
		else{
			verifyHourlyBudgetField(String. valueOf(totalProfRate), hour, "Phase1Row6HourlyBudget");
		}
	}
	public boolean isCurrencySymbolMatch(String currencySymbol) {
		String HourlyBudget2Row = locatorReader.getLocator("HourlyBudget2Row");
		boolean result = false;
		if(getText(HourlyBudget2Row).contains(currencySymbol))
			result = true;
		return result;
	}
	public void selectCurrency(String string) {
		clickOn("//li[contains(text(), '"+string+"')]");
		
	}
}