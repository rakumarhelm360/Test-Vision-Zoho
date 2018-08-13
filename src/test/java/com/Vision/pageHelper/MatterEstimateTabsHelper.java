package com.Vision.pageHelper;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.Vision.locators.LocatorReader;
import com.Vision.util.DriverHelper;
import com.Vision.util.PropertyReader;
import com.cucumber.listener.Reporter;

public class MatterEstimateTabsHelper extends DriverHelper {
	//	private LocatorReader locatorReader;	
	private LocatorReader mELocatorReader;
	protected PropertyReader propertyReader = new PropertyReader();	
	protected WriteDataToFileHelper writeToFile = new WriteDataToFileHelper();
	//---------------------------------------------------------------------------------------------------------
	public MatterEstimateTabsHelper(WebDriver webDriver) 			
	{			
		super(webDriver);		
		//		locatorReader = new LocatorReader("Vision.xml");
		mELocatorReader = new LocatorReader("MatterEstimatesLocators.xml");
	}
	public void expandTask() {
		System.out.println("Locator is"+mELocatorReader.getLocator("FeePlanLocators.ExpandFirstTask"));
		String locator = mELocatorReader.getLocator("FeePlanLocators.ExpandFirstTask");
		clickOn(locator);
	}
	public void click(String button)			
	{	
		String locator = mELocatorReader.getLocator(button);
		waitForElementPresent(locator,5);
		clickOn(locator);		
	}

	public void verifyElement(String field)
	{
		String locator = mELocatorReader.getLocator(field);
		Assert.assertTrue("Element Locator :"
				+ locator + " Not found", isElementPresent(locator));
	}
	public void type(String field, String text)			
	{			
		String locator = mELocatorReader.getLocator(field);
		sendKeys(locator, text);	
	}
	public String getElementText(String element){
		String locator = mELocatorReader.getLocator(element);
		return getText(locator);
	}
	public void verifyEstimateAtDashboard(String estimateName) {
		isElementPresent("//div[contains(@id, 'openestimates')]//td[contains(text(), '"+estimateName+"')]");
		waitForWorkAroundTime(200);
	}
	public void addActivity(String strArg1) {
		if(strArg1.equals("First Fee")||strArg1.equals("Second Fee")){
			String locator = mELocatorReader.getLocator("FeePlanLocators.FeeAddNewRecordButton");
			clickOn(locator);
		}
		else if(strArg1.equals("First Cost")||strArg1.equals("Second Cost")){
			String locator = mELocatorReader.getLocator("CostPlanLocators.CostAddNewRecord");
			clickOn(locator);
		}
	}
	public void enterActivity(String string, String format, String type) {
		switch(type)
		{
		case "First Fee":
			String firstFee = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.Activity");
			sendKeys(firstFee,string+format);
			break;
		case "Second Fee":
			String secondFee = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.Activity");
			sendKeys(secondFee,string+format);
			break;
		case "First Cost":
			String firstCost = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.Activity");
			sendKeys(firstCost,string+format);
			break;
		case "Second Cost":
			String secondCost = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.Activity");
			sendKeys(secondCost,string+format);
			break;
		}
	}
	public void selectResource(String string, String type) {
		switch(type)
		{
		case "First Fee":
			String fffield = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.ResourceTD");
			String ffdropdown = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.ResourceDD");
			clickOn(fffield);waitForWorkAroundTime(1000);clickOn(fffield);
			clickOn(ffdropdown);waitForWorkAroundTime(1000);
			clickOn("//span[contains(text(),'"+string+"')]");waitForWorkAroundTime(700);
			break;
		case "Second Fee":
			String sffield = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.ResourceTD");
			String sfdropdown = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.ResourceDD");
			clickOn(sffield);waitForWorkAroundTime(1000);clickOn(sffield);
			clickOn(sfdropdown);waitForWorkAroundTime(1000);
			clickOn("//span[contains(text(),'"+string+"')]");waitForWorkAroundTime(700);
			break;
		case "First Cost":
			String fcfield = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.ResourceTD");
			String fcdropdown = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.ResourceDD");
			clickOn(fcfield);waitForWorkAroundTime(1000);clickOn(fcfield);
			clickOn(fcdropdown);waitForWorkAroundTime(1000);
			clickOn("//span[contains(text(),'"+string+"')]");waitForWorkAroundTime(700);
			break;
		case "Second Cost":
			String scfield = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.ResourceTD");
			String scdropdown = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.ResourceDD");
			clickOn(scfield);waitForWorkAroundTime(1000);clickOn(scfield);
			clickOn(scdropdown);waitForWorkAroundTime(1000);
			clickOn("//span[contains(text(),'"+string+"')]");waitForWorkAroundTime(700);
			break;
		}
	}
	public void enterHours(String string, String type) {
		switch(type){
		case "First Fee":
			String Ffield = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.HoursTD");
			String Fvalue = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.HoursInput");
			clickOn(Ffield);waitForWorkAroundTime(500);clickOn(Ffield);
			sendKeys(Fvalue,string);waitForWorkAroundTime(200);
			break;
		case "Second Fee":
			String Sfield = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.HoursTD");
			String Svalue = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.HoursInput");
			clickOn(Sfield);waitForWorkAroundTime(200);clickOn(Sfield);
			sendKeys(Svalue,string);waitForWorkAroundTime(200);
			break;
		}
	}
	public void enterStartDate(String string, String type) {
		switch(type){
		case "First Fee":
			String Ffield = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.StartDateTD");
			String Fvalue = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.StartDateInput");
			String FStdRate = mELocatorReader.getLocator("FeePlanLocators.FeeFirstRow.StdRateTD");
			clickOn(Ffield);waitForWorkAroundTime(500);clickOn(Ffield);
			sendKeys(Fvalue,string);waitForWorkAroundTime(200);
			clickOn(FStdRate);waitForWorkAroundTime(200);
			break;
		case "Second Fee":
			String Sfield = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.StartDateTD");
			String Svalue = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.StartDateInput");
			String SStdRate = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.StdRateTD");
			String SCalIcon = mELocatorReader.getLocator("FeePlanLocators.FeeSecondRow.StartDateCalIcon");
			clickOn(Sfield);waitForWorkAroundTime(500);clickOn(Sfield);
			sendKeys(Svalue,string);waitForWorkAroundTime(200);
			clickOn(SCalIcon);waitForWorkAroundTime(500);
			clickOn("//div[@id = 'StartDate_dateview']/div/table/tbody/tr/td/a[@title = '"+convertDateToEEEEMMMMddyyyy(string)+"']");
			waitForWorkAroundTime(200);
			clickOn(SStdRate);waitForWorkAroundTime(200);
			break;
		}

	}

	public void verifystdRateOnUIForProf(double totalProfRate, String RateValueInUI) {
		String StdSecondRowLocator = mELocatorReader.getLocator(RateValueInUI);
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
	public void saveDetails() {
		String saveDetail = mELocatorReader.getLocator("FeePlanLocators.Save");
		waitForWorkAroundTime(2000);clickOn(saveDetail);waitForWorkAroundTime(2000);

	}
	public void switchToTab(String tabName) {
		switch(tabName){
		case "Cost Plan":
			String costPlan = mELocatorReader.getLocator("CostPlanLocators.CostPlanTab");
			clickOn(costPlan);
			break;
		case "Info":
			String info = mELocatorReader.getLocator("InfoTabLocators.InfoTab");
			clickOn(info);
			break;
		case "Third Party":
			String thirdParty = mELocatorReader.getLocator("ThirdPartyLocators.ThirdPartyTab");
			clickOn(thirdParty);
			break;
		case "Budget to Actual":
			String budgetToActual = mELocatorReader.getLocator("BudgetToActualLocators.BudgetToActualTab");
			clickOn(budgetToActual);
			break;
		case "What If":
			String whatIf = mELocatorReader.getLocator("WhatIfLocators.WhatIfTab");
			clickOn(whatIf);
			break;
		case "Fee Plan":
			String feePlan = mELocatorReader.getLocator("FeePlanLocators.FeePlanTab");
			clickOn(feePlan);
			break;
		}

	}
	public void enterCostCode(String costCode, String strArg1) {
		switch(strArg1){
		case "First Cost":
			String FcostCodeLocator = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.CostCodeDesc");
			String FcostCodeDD = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.CostCodeDD");
			clickOn(FcostCodeLocator);waitForWorkAroundTime(300);
			clickOn(FcostCodeDD);waitForWorkAroundTime(500);
			clickOn("//span[contains(text(),'"+costCode+"')]");
			break;
		case "Second Cost":
			String ScostCodeLocator = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.CostCodeDesc");
			String ScostCodeDD = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.CostCodeDD");
			clickOn(ScostCodeLocator);waitForWorkAroundTime(300);
			clickOn(ScostCodeDD);waitForWorkAroundTime(500);
			clickOn("//span[contains(text(),'"+costCode+"')]");
			break;
		}

	}
	public void enterUnit(String unit, String strArg1) {
		switch(strArg1){
		case "First Cost":
			String FunitField = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.UnitTD");
			String FunitValue = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.UnitInput");
			String FAmount = mELocatorReader.getLocator("CostPlanLocators.CostFirstRow.AmountTD");
			clickOn(FunitField);waitForWorkAroundTime(200);
			sendKeys(FunitValue, unit);waitForWorkAroundTime(100);clickOn(FAmount);waitForWorkAroundTime(200);
			break;
		case "Second Cost":
			String SunitField = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.UnitTD");
			String SunitValue = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.UnitInput");
			String SAmount = mELocatorReader.getLocator("CostPlanLocators.CostSecondRow.AmountTD");
			clickOn(SunitField);waitForWorkAroundTime(200);
			sendKeys(SunitValue, unit);waitForWorkAroundTime(100);clickOn(SAmount);waitForWorkAroundTime(200);
			break;
		}

	}

	public void verifyCostRate(String costRateFromDB, String locator) {
		double stdRateDBInDouble = Double.parseDouble(costRateFromDB);
		String ratelocator = mELocatorReader.getLocator(locator); 
		String StdRateFromLocator = getText(ratelocator).substring(1).replace(",", "");
		double StdRateUI = Double.parseDouble(StdRateFromLocator);
		Reporter.addStepLog("Standard Rate from DATABASE: "+costRateFromDB);
		Reporter.addStepLog("Standard Rate From UI: "+StdRateUI);
		if(StdRateUI==stdRateDBInDouble){
			Assert.assertTrue("Rate value is same on UI as Database and cacluated correctly",true);
		}
		else{
			Assert.assertTrue("Rate Value not matched on UI"+"At UI it is"+StdRateUI+"At Database it is "+stdRateDBInDouble, false);
		}

	}
	public String getCostRateFromUI(String locator) {
		String ratelocator = mELocatorReader.getLocator(locator); 
		String StdRateFromLocator = getText(ratelocator).substring(1).replace(",", "");
		return StdRateFromLocator;
	}
	public void verifyCostAmount(String unit, String costRateFromUI, String locator) {
		Double unitDouble = Double.parseDouble(unit);
		Double rateDouble = Double.parseDouble(costRateFromUI);
		Double Amount = unitDouble*rateDouble;
		Double AmountUI = Double.parseDouble(getText(mELocatorReader.getLocator(locator)).substring(1).replace(",", ""));
		if(Amount.equals(AmountUI))
			Assert.assertTrue("Rate Value is calculated correctly",true);
		else
			Assert.assertTrue("Rate value not calculated correctly", false);
	}
	public void verifyEstimateInfo(String clientName, String estimateName,
			String estimateCurrency, String practiceGroup, String estimateType,
			String estimateStatus, String estimateCreatedTime) {
		String clientLoc = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.ClientName"));
		String estNameLoc = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.EstimateName"));
		String estCur = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.Currency"));
		String estPracGrp = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.PracticeGroup"));
		String estType = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.EstimateType"));
		String estStatus = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.EstimateStatus"));
		//String estCreatedTime = convertDateToDDMMYYYY(getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.EstimateOpenedDate"))); 
		String estCreatedTime = getText(mELocatorReader.getLocator("InfoTabLocators.EstimateAndClientInfo.EstimateOpenedDate")); //need to update later
		Assert.assertEquals("Client Name Not equals from the time of creation",clientName, clientLoc);
		Assert.assertEquals("Estimate Name Not equals from the time of creation",estimateName, estNameLoc);
		Assert.assertEquals("Currency Not equals from the time of creation",estimateCurrency, estCur);
		Assert.assertEquals("Practice Group Not equals from the time of creation",practiceGroup, estPracGrp);
		Assert.assertEquals("Estimate Type Not equals from the time of creation",estimateType, estType);
		Assert.assertEquals("Estimate Status Not equals from the time of creation",estimateStatus, estStatus);
		Assert.assertEquals("Estimate Created Time Not equals from the time of creation",estimateCreatedTime, convertDateMMDDYYYY(estCreatedTime));

	}
	public void addInfoRecord(String firmRecord) {
		if(firmRecord.equals("1st Firm")){
			clickOn(mELocatorReader.getLocator("InfoTabLocators.FirmSection.AddNewRecord"));waitForWorkAroundTime(300);
		}
		else if(firmRecord.equals("1st Client")){
			clickOn(mELocatorReader.getLocator("InfoTabLocators.ClientSection.AddNewRecord"));waitForWorkAroundTime(300);
		}

	}
	public void enterFirmRecord(String recordType, String contactPerson,
			String firmRecord) {
		if(firmRecord.equals("1st Firm")) {
			clickOn(mELocatorReader.getLocator("InfoTabLocators.FirmSection.TypeDD"));waitForWorkAroundTime(300);
			clickOn("//span[contains(text(),'"+recordType+"')]");waitForWorkAroundTime(300);
			while(isElementPresent(mELocatorReader.getLocator("InfoTabLocators.FirmSection.ContactPersonDD")) == false){
				clickOn(mELocatorReader.getLocator("InfoTabLocators.FirmSection.ContactPersonTD"));waitForWorkAroundTime(200);
			}
			//clickOn(mELocatorReader.getLocator("InfoTabLocators.FirmSection.ContactPersonTD"));waitForWorkAroundTime(200);
			clickOn(mELocatorReader.getLocator("InfoTabLocators.FirmSection.ContactPersonDD"));waitForWorkAroundTime(300);
			clickOn("//span[contains(text(),'"+contactPerson+"')]");waitForWorkAroundTime(100);
		}

	}
	public void enterClientDetails(String contactPerson, String email,
			String phone, String clientDetails) {
		if(clientDetails.equals("1st Client")){
			sendKeys(mELocatorReader.getLocator("InfoTabLocators.ClientSection.ContactPersonInput"),contactPerson);waitForWorkAroundTime(1000);
			while(isElementPresent(mELocatorReader.getLocator("InfoTabLocators.ClientSection.EmailInput")) == false){
				clickOn(mELocatorReader.getLocator("InfoTabLocators.ClientSection.EmailTD"));waitForWorkAroundTime(400);
			}
			//clickOn(mELocatorReader.getLocator("InfoTabLocators.ClientSection.EmailTD"));waitForWorkAroundTime(1000);
			sendKeys(mELocatorReader.getLocator("InfoTabLocators.ClientSection.EmailInput"),email);waitForWorkAroundTime(400);
			while(isElementPresent(mELocatorReader.getLocator("InfoTabLocators.ClientSection.PhoneInput")) == false){
				clickOn(mELocatorReader.getLocator("InfoTabLocators.ClientSection.PhoneTD"));waitForWorkAroundTime(400);
			}
			//clickOn(mELocatorReader.getLocator("InfoTabLocators.ClientSection.PhoneTD"));waitForWorkAroundTime(1000);
			sendKeys(mELocatorReader.getLocator("InfoTabLocators.ClientSection.PhoneInput"),phone);waitForWorkAroundTime(300);
		}

	}
	public void addThirdPartyRecord(String strArg1) {
		if(strArg1.equals("First TP")){
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.AddActivity"));waitForWorkAroundTime(300);
		}

	}
	public void addThirdPartyrows(String expCategory, String expWitness,
			String catExpert, String hours, String startDate, String endDate, String strArg1) {
		if(strArg1.equals("First TP")){
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRExpCategoryDD"));waitForWorkAroundTime(300);
			clickOn("//span[contains(text(), '"+expCategory+"')]");
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRExpWitnessTD"));waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRExpWitenessDD"));waitForWorkAroundTime(200);
			clickOn("//span[contains(text(), '"+expWitness+"')]");waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRCatExpertTD"));waitForWorkAroundTime(200);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRCatExpertDD"));waitForWorkAroundTime(200);
			clickOn("//span[contains(text(), '"+catExpert+"')]");waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRHoursTD"));waitForWorkAroundTime(300);
			clearField(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRHoursInput"));
			sendKeys(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRHoursInput"),hours);;waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRStartDataTD"));waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRStartDataTD"));waitForWorkAroundTime(300);//Not clicking once on the element
			sendKeys(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRStartDateInput"), startDate);waitForWorkAroundTime(200);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FREndDateTD"));waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FREndDateTD"));waitForWorkAroundTime(300);//Not clicking once on the element
			sendKeys(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FREndDataInput"),endDate);waitForWorkAroundTime(300);
			clickOn(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRThirdPartyCostTD"));waitForWorkAroundTime(300);
		}

	}
	public void verifyTPRateAndCost(String line, String Rate, String Hours) {
		if(line.equals("First TP")){
			Double rateDB = Double.parseDouble(Rate);
			Double rateUI = Double.parseDouble(getText(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRRateTD")).substring(1).replace(",", ""));
			Assert.assertEquals(rateDB, rateUI);
			Double Total = rateDB*Double.parseDouble(Hours);
			Double TotalFromUI = Double.parseDouble(getText(mELocatorReader.getLocator("ThirdPartyLocators.TPFirstRow.FRThirdPartyCostTD")).substring(1).replace(",", ""));
			Assert.assertEquals(Total, TotalFromUI);
		}
	}
	public void addNewWhatIf() {
		clickOn(mELocatorReader.getLocator("WhatIfLocators.CopyandEditLeft"));waitForWorkAroundTime(200);

	}
	public void verifyElementPresent(String string) {
		isElementPresent(mELocatorReader.getLocator(string));

	}
	public void changewhatIfdata(String resource, String hours, String avgRate) {
		waitForWorkAroundTime(1000);clickOn(mELocatorReader.getLocator("WhatIfLocators.FirstRow.ResourceTD"));waitForWorkAroundTime(400);
		clickOn(mELocatorReader.getLocator("WhatIfLocators.FirstRow.ResourceTD"));waitForWorkAroundTime(1000);
		clickOn(mELocatorReader.getLocator("WhatIfLocators.FirstRow.ResourceDD"));waitForWorkAroundTime(400);
		clickOn("//span[contains(text(), '"+resource+"')]");waitForWorkAroundTime(200);
		clickOn(mELocatorReader.getLocator("WhatIfLocators.CustomOk"));waitForWorkAroundTime(1000);
		int count = getXpathCount(mELocatorReader.getLocator("WhatIfLocators.FirstRow.RowLength"));
		for(int i=1;i<=count;i++) {
			if(getText("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[1]").equals(resource)){
				clickOn("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[2]");waitForWorkAroundTime(500);
				clickOn("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[2]");
				waitForWorkAroundTime(500);
				clearField("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[2]/span/span/input[2]");
				waitForWorkAroundTime(200);
				sendKeys("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[2]/span/span/input[2]",hours);
				waitForWorkAroundTime(1000);
				clickOn("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[3]");waitForWorkAroundTime(200);
				clickOn("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[3]");
				waitForWorkAroundTime(500);
				clearField("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[3]/span/span/input[2]");
				waitForWorkAroundTime(100);
				sendKeys("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[3]/span/span/input[2]",avgRate);
				waitForWorkAroundTime(200);
				clickOn("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]");waitForWorkAroundTime(100);
			}
		}
	}
	public void saveWhatIf(String string) {
		clickOn(mELocatorReader.getLocator(string));waitForWorkAroundTime(100);
	}
	public void verifyWhatIfUpdate(String whatIfresource, String whatIfhours,
			String wahtIfavgRate) {
		double hours=0.0;
		double avgRate = 0.0;
		double Budget = 0.0;
		int count = getXpathCount(mELocatorReader.getLocator("WhatIfLocators.FirstRow.RowLength"));
		for(int i=1;i<=count;i++) {
			if(getText("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[1]").equals(whatIfresource)){
				hours = Double.parseDouble(getText("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[2]"));
				avgRate = Double.parseDouble(getText("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[3]"));
				Budget = Double.parseDouble(getText("//div[@id='whatIfBody']/div[2]//div[contains(@class,'k-grid-content')]/table/tbody/tr[2]/td[2]//table/tbody/tr["+i+"]/td[4]").replace(",", ""));
			}
		}
		if(Budget == (hours*avgRate)){
			Assert.assertTrue("Budget Calculation not correct", true);
		}

	}
	public void waitForConfirmation(String locator, int timeout){
		waitForElementPresent(mELocatorReader.getLocator(locator),timeout);
	}
	public void searchForEstimatedCreated(String estimateName) {
		String MyEstimateTitle = mELocatorReader.getLocator("MyEstimateTitle");
		String SearchInputField = mELocatorReader.getLocator("SearchInputField");
		String SearchButton = mELocatorReader.getLocator("SearchButton");
		waitForElementPresent(MyEstimateTitle, 5);waitForWorkAroundTime(3000);
		clickOn(SearchInputField);waitForWorkAroundTime(500);
		sendKeys(SearchInputField,estimateName);waitForWorkAroundTime(500);
		clickOn(SearchButton);waitForWorkAroundTime(500);
	}
	public void openEstimate() {
		String ViewSearchEstimate = mELocatorReader.getLocator("ViewSearchEstimate");
		waitForWorkAroundTime(5000);clickOn(ViewSearchEstimate);waitForWorkAroundTime(5000);
	}
	public double calculateHourlyFeeOnUI() {
		switchToTab("Fee Plan");waitForWorkAroundTime(500);
		double hbf = 0.00;
		double totalhbf = 0.00;
		int outerCount = getXpathCount(mELocatorReader.getLocator("FeePlanLocators.TaskCount"));
		for(int i=1;i<=outerCount;i++) {
			int innerCount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]/td[@class='k-detail-cell']//div[contains(@id, 'taskDetail')]/table/tbody/tr");
			for(int j=1;j<=innerCount;j++){
				hbf = Double.parseDouble(getText("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]/td[@class='k-detail-cell']//div[contains(@id, 'taskDetail')]/table/tbody/tr["+j+"]/td[9]").substring(1).replace(",", ""));
				totalhbf = totalhbf+hbf;
			}
		}
		System.out.println("Total Hourly Budget Fee is : "+totalhbf);
		Reporter.addStepLog("Total Hourly Budget Fee is : "+totalhbf);
		return Math.round(totalhbf*100)/100;
	}
	public double calculateProposedBudgetOnUI(double totalhfb) {
		switchToTab("Cost Plan");waitForWorkAroundTime(500);
		double ProposedBudgetUI = 0.00;
		double costSum = 0.00;
		double CostPlanTotal = 0.00;
		int costOuterCount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']");
		for(int i=1;i<=costOuterCount;i++) {
			int costInnercount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'costDetail')]//table/tbody/tr");
			for(int j=1;j<=costInnercount;j++){
				costSum = Double.parseDouble(getText("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'costDetail')]//table/tbody/tr["+j+"]/td[6]").substring(1).replace(",", ""));
				CostPlanTotal = CostPlanTotal+costSum;
			}
		}
		System.out.println("Total Cost Plan Details is : "+CostPlanTotal);
		Reporter.addStepLog("Total Cost Plan Details is : "+CostPlanTotal);
		switchToTab("Cost Plan");waitForWorkAroundTime(500);
		double ThirdPartySum = 0.00;
		double ThirdPartyTotal = 0.00;

		int thirdPartyOuterCount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']");
		for(int i=1;i<=thirdPartyOuterCount;i++) {
			int ThirdPartyInnercount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'thirdPartyDetail')]/table/tbody/tr");
			for(int j=1;j<=ThirdPartyInnercount;j++){
				ThirdPartySum = Double.parseDouble(getText("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'thirdPartyDetail')]/table/tbody/tr["+j+"]/td[8]").substring(1).replace(",", ""));
				ThirdPartyTotal = ThirdPartyTotal+ThirdPartySum;
			}
		}
		System.out.println("Total Third Party Details is : "+ThirdPartyTotal);
		Reporter.addStepLog("Total Third Party Details is : "+ThirdPartyTotal);
		ProposedBudgetUI = totalhfb + CostPlanTotal + ThirdPartyTotal;
		System.out.println("Proposed Budget is  : "+ProposedBudgetUI);
		Reporter.addStepLog("Proposed Budget is  : "+ProposedBudgetUI);
		//return Math.round(ProposedBudgetUI*100)/100;
		return ProposedBudgetUI;

	}

	public double calculateCost(){
		switchToTab("Cost Plan");waitForWorkAroundTime(500);
		double costSum = 0.00;
		double CostPlanTotal = 0.00;
		int costOuterCount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']");
		for(int i=1;i<=costOuterCount;i++) {
			int costInnercount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'costDetail')]//table/tbody/tr");
			for(int j=1;j<=costInnercount;j++){
				costSum = Double.parseDouble(getText("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'costDetail')]//table/tbody/tr["+j+"]/td[6]").substring(1).replace(",", ""));
				CostPlanTotal = CostPlanTotal+costSum;
			}
		}
		System.out.println("Total Cost Plan Details is : "+CostPlanTotal);
		Reporter.addStepLog("Total Cost Plan Details is : "+CostPlanTotal);
		//return Math.round(CostPlanTotal*100)/100;
		return CostPlanTotal;
	}
	public double calculateThirdParty(){
		switchToTab("Third Party");waitForWorkAroundTime(500);
		double ThirdPartySum = 0.00;
		double ThirdPartyTotal = 0.00;

		int thirdPartyOuterCount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']");
		for(int i=1;i<=thirdPartyOuterCount;i++) {
			int ThirdPartyInnercount = getXpathCount("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'thirdPartyDetail')]/table/tbody/tr");
			for(int j=1;j<=ThirdPartyInnercount;j++){
				ThirdPartySum = Double.parseDouble(getText("//div[@id='grid']/table/tbody/tr[contains(@class,'k-detail-row')]//tr[@class='k-detail-row']["+i+"]//div[contains(@id, 'thirdPartyDetail')]/table/tbody/tr["+j+"]/td[8]").substring(1).replace(",", ""));
				ThirdPartyTotal = ThirdPartyTotal+ThirdPartySum;
			}
		}
		System.out.println("Total Third Party Details is : "+Math.round(ThirdPartyTotal*100)/100);
		Reporter.addStepLog("Total Third Party Details is : "+Math.round(ThirdPartyTotal*100)/100);
		return ThirdPartyTotal;
		//return Math.round(ThirdPartyTotal*100)/100;
	}

	public double calculateProposedBudgetOnUI(double totalFee, double totalCost, double totalThirdParty){
		double totalProposed = totalFee+totalCost+totalThirdParty;
		System.out.println("Proposed Budget is  : "+Math.round(totalProposed*100)/100);
		Reporter.addStepLog("Proposed Budget is  : "+Math.round(totalProposed*100)/100);
		//return Math.round(totalProposed*100)/100;
		return totalProposed;
	}
	public void verifyHourlyFeeBudget(double totalhfb,
			double hourlyFeeBudgetFromDB) {
		double UIhourlybudget = Double.parseDouble(getText(mELocatorReader.getLocator("Slider.TotalHourlyFeeBudget")).substring(1).replace(",", ""));
		if(totalhfb == hourlyFeeBudgetFromDB && hourlyFeeBudgetFromDB == UIhourlybudget){
			Reporter.addStepLog("Hourly Fee Budget in DB is : "+hourlyFeeBudgetFromDB);
			Reporter.addStepLog("Hourly Fee Budget in UI is : "+UIhourlybudget);
			Reporter.addStepLog("Hourly Fee Budget By calculation is "+totalhfb);
		}
		else{
			throw new AssertionError("Hourly Fee Budget in DB is : "+hourlyFeeBudgetFromDB+"  In UI is : "+UIhourlybudget+"   After calculation is  : "+totalhfb);
		}
	}
	public void verifyProposedBudget(double proposedBudget,
			double proposedBudgetFromDB) {
		double UIProposedBudget = Double.parseDouble(getText(mELocatorReader.getLocator("Slider.ProposedBudget")).substring(1).replace(",", ""));
		if(proposedBudget == proposedBudgetFromDB && proposedBudgetFromDB == UIProposedBudget){
			Reporter.addStepLog("Proposed Budget in DB is : "+proposedBudgetFromDB);
			Reporter.addStepLog("Proposed Budget in UI is : "+UIProposedBudget);
			Reporter.addStepLog("Proposed Budget By calculation is "+proposedBudget);	
		}
		else
		{
			throw new AssertionError("Proposed Budget in DB is : "+proposedBudgetFromDB+"  In UI is : "+UIProposedBudget+"   After calculation is  : "+proposedBudget);
		}
	}
	public double calculateBudgetProfit(double totalhfb, double tkprCost1,
			double tkprCost2) {
		double totaltkprcost = tkprCost1+tkprCost2;
		double budgetProfit = totalhfb-totaltkprcost;
		//return Math.round(budgetProfit*100)/100;
		return budgetProfit;
	}
	public void verifyBudgetProfit(double budgetProfit) {
		double budgetProfitFromUI = Double.parseDouble(getText(mELocatorReader.getLocator("Slider.BudgetProfit")).substring(1).replace(",", ""));
		if(budgetProfit == budgetProfitFromUI){
			Reporter.addStepLog("Budget profit in database and UI are matching");
		}
		else{
			Reporter.addStepLog("Budget Profit at UI : "+budgetProfitFromUI);
			Reporter.addStepLog("Budget Proit in Database : "+budgetProfit);
			throw new AssertionError("Budget Profit at UI and Database are not matching at UI its "+budgetProfitFromUI+ " And in database its "+budgetProfit);
		}

	}
	public double getBudgetMargin(double budgetProfit, double totalhfb) {
		double budgetMargin = (budgetProfit/totalhfb)*100;
		//return Math.round(budgetMargin*100)/100;
		return budgetMargin;
	}
	public void verifyBudgetMargin(String string) {
		String percentageBudgetMargin = getText(mELocatorReader.getLocator("Slider.BudgetMarginValue"));
		if(string.equals(percentageBudgetMargin)) {
			Reporter.addStepLog("Budget Margin at UI is calculated correctly");
		}
		else{
			Reporter.addStepLog("Budget Margin at UI is : "+percentageBudgetMargin);
			Reporter.addStepLog("Budget Margin by calculation is : "+string);
			throw new AssertionError("Budget Margin is not calculated correctly by calculation it is : "+string+" at UI it is : "+percentageBudgetMargin);
		}
	}
	public double getRedPercentageValue(String practiceGroup) {
		int rowCount = getXpathCount(mELocatorReader.getLocator("SetMargin.TableRow"));
		double redpercentage = 0.00;
		for(int i=1;i<=rowCount;i++) {
			if(getText("//div[@id='dvSetMargin']//table/tbody/tr["+i+"]/td[1]").equals(practiceGroup)){
				redpercentage = Double.parseDouble(getText("//div[@id='dvSetMargin']//table/tbody/tr["+i+"]/td[2]").replace("%", ""));
			}

		}
		System.out.println("Red Percentage is : "+redpercentage);
		if(redpercentage == 0.00){
			for(int j=1;j<=rowCount;j++){
				if(getText("//div[@id='dvSetMargin']//table/tbody/tr["+j+"]/td[1]").equals("Firm")){
					redpercentage = Double.parseDouble(getText("//div[@id='dvSetMargin']//table/tbody/tr["+j+"]/td[2]").replace("%", ""));	
				}
			}
		}
		System.out.println("Red percentage is : "+redpercentage);
		return redpercentage;
	}
	public double getGreenPercentageValue(String practiceGroup) {
		int rowCount = getXpathCount(mELocatorReader.getLocator("SetMargin.TableRow"));
		double greenpercentage = 0.00;
		for(int i=1;i<=rowCount;i++) {
			if(getText("//div[@id='dvSetMargin']//table/tbody/tr["+i+"]/td[1]").equals(practiceGroup)){
				greenpercentage = Double.parseDouble(getText("//div[@id='dvSetMargin']//table/tbody/tr["+i+"]/td[4]").replace("%", ""));
			}

		}
		System.out.println("Red Percentage is : "+greenpercentage);
		if(greenpercentage == 0.00){
			for(int j=1;j<=rowCount;j++){
				if(getText("//div[@id='dvSetMargin']//table/tbody/tr["+j+"]/td[1]").equals("Firm")){
					greenpercentage = Double.parseDouble(getText("//div[@id='dvSetMargin']//table/tbody/tr["+j+"]/td[4]").replace("%", ""));	
				}
			}
		}
		System.out.println("Red percentage is : "+greenpercentage);
		return greenpercentage;
	}
	public void verifyBudgetMarginColor(double lowPercentage,
			double highPercentage) {
		double budgetMarginPer = Double.parseDouble(getText(mELocatorReader.getLocator("Slider.BudgetMarginPercentage")).replace("%", ""));
		String color = getAttributeValue(mELocatorReader.getLocator("Slider.AllBudgetMarginPerSlider"), "currmargincol");
		System.out.println("Color at ui is :"+color);
		if(budgetMarginPer<=lowPercentage){
			Assert.assertEquals("Slider Color is not Expected", color, "red");
		}
		else if(budgetMarginPer>=highPercentage){
			Assert.assertEquals("Slider Color is not Expected", color, "green");
		}
		else{
			Assert.assertEquals("Slider Color is not Expected", color, "yellow");
		}
	}
	public boolean isValuesMatch(String convertDateToYYYYMMDD, String dBDate) {
		boolean result = false;
		if(convertDateToYYYYMMDD.equals(dBDate)){
			result = true;
		}
		return result;
	}

}
