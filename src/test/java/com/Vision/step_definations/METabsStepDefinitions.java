package com.Vision.step_definations;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;











import org.testng.Assert;
import org.testng.asserts.Assertion;

import com.Vision.pageHelper.CommonHelper;
import com.Vision.pageHelper.EstimateHelper;
import com.Vision.pageHelper.MatterEstimateTabsHelper;
import com.Vision.pageHelper.SQLConnection;
import com.Vision.pageHelper.SQLHelper;
import com.Vision.pageHelper.WriteDataToFileHelper;
import com.Vision.util.DriverTestCase;
import com.Vision.util.PropertyReader;
import com.Vision.util.RandomPropertyReader;
import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;
import com.sun.istack.logging.Logger;

import cucumber.api.DataTable;
import cucumber.api.java.ContinueNextStepsFor;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class METabsStepDefinitions {
	SQLHelper sqlHelper;
	EstimateHelper estimateHelper;
	CommonHelper commonHelper;
	DriverTestCase driverTestCase;
	SQLConnection sqlConnection;
	MatterEstimateTabsHelper mEHelper;
	protected PropertyReader propertyReader = new PropertyReader();
	protected WriteDataToFileHelper writeToFile = new WriteDataToFileHelper();
	protected RandomPropertyReader randomPropertyReader = new RandomPropertyReader();
	public String BudgetType = randomPropertyReader.readApplicationFile("Type_of_Budget");
	public String EstimateName = randomPropertyReader.readApplicationFile("Estimate_Name");
	public String EstimateCurrency = randomPropertyReader.readApplicationFile("Currency");
	public String clientName = randomPropertyReader.readApplicationFile("Client_Name");
	public String practiceGroup = randomPropertyReader.readApplicationFile("Practice_Group");
	private double TotalProfRate;
	private String ResourceType;
	private String ProfName;
	private String Hours;
	private String StartDate;
	private String CostCode;
	private String Unit;
	private String ThirdPartyCatExpertRate;
	private String ThirdPartyHours;
	private String whatIfresource;
	private String whatIfhours;
	private String wahtIfavgRate;
	private double totalhfb;
	private double proposedBudget;
	private double totalCost;
	private double totalThirdParty;
	private double tkprCost1;
	private double tkprCost2;
	private double budgetProfit;
	private double budgetMargin;
	private double lowPercentage;
	private double highPercentage;
	private String feeActivity;
	public METabsStepDefinitions() 
	{
		driverTestCase = new DriverTestCase();
		estimateHelper = new EstimateHelper(driverTestCase.getWebDriver());
		commonHelper = new CommonHelper(driverTestCase.getWebDriver());
		mEHelper = new MatterEstimateTabsHelper(driverTestCase.getWebDriver());
		sqlConnection= new SQLConnection(driverTestCase.getWebDriver());
		sqlHelper = new SQLHelper(driverTestCase.getWebDriver());
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify the created estimate at dashboard$")
	public void verify_the_created_estimate_at_dashboard() throws Throwable {
		mEHelper.verifyEstimateAtDashboard(EstimateName);
	}
	@When("^Search for previously created Estimate$")
	public void search_for_previously_created_Estimate(){
		System.out.println("Estimate Name from Property file is "+EstimateName);
		mEHelper.searchForEstimatedCreated(EstimateName); 
	}
	@And("^Click on view for the estimate$")
	public void click_on_view_for_the_estimate(){
		mEHelper.openEstimate();
	}
	@When("^Expand the Task if Estimate is Type 3$")
	public void expand_the_task_if_estiamte_is_type3()
	{
		if(BudgetType.equals("Matter Detail"))
		{
			mEHelper.expandTask();
		}
	}

	@And("^Click on Add new record button for Fee Plan$")
	public void click_on_add_new_record_button_for_fee_plan() {
		mEHelper.click("FeePlanLocators.FeeAddNewRecordButton");       
	}

	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify the Activity field is required$")
	public void verify_the_activity_field_is_required() throws Throwable {
		mEHelper.click("FeePlanLocators.FeeFirstRow.ResourceTD");mEHelper.waitForWorkAroundTime(500);
		mEHelper.verifyElement("FeePlanLocators.FeeFirstRow.isRequired");

	}
	@When("^Enter activity$")
	public void enter_activity() {
		if(BudgetType.equals("Matter Detail"))
		{
			mEHelper.type("FeePlanLocators.FeeFirstRow.Activity", "DemoActivity");
		}
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Validate start date and end date fields$")
	public void validate_start_date_and_end_date_fields() {
		mEHelper.click("FeePlanLocators.FeeFirstRow.StartDateTD");mEHelper.waitForWorkAroundTime(300);
		mEHelper.click("FeePlanLocators.FeeFirstRow.StartDateTD");mEHelper.waitForWorkAroundTime(300);
		mEHelper.type("FeePlanLocators.FeeFirstRow.StartDateInput", mEHelper.addInCurrentDate(2));
		mEHelper.waitForWorkAroundTime(200);
		mEHelper.click("FeePlanLocators.FeeFirstRow.EndDateTD");
		mEHelper.waitForWorkAroundTime(200);
		mEHelper.click("FeePlanLocators.FeeFirstRow.EndDateTD");
		mEHelper.waitForWorkAroundTime(200);
		mEHelper.type("FeePlanLocators.FeeFirstRow.EndDateInput", mEHelper.getTodayDate());
		mEHelper.waitForWorkAroundTime(200);
		mEHelper.verifyElement("DateError");
		mEHelper.waitForWorkAroundTime(500);
	}
	@And("^Delete the Fee record$")
	public void delete_the_fee_record() {
		mEHelper.click("FeePlanLocators.FeeFirstRow.Delete");mEHelper.waitForWorkAroundTime(500);
		mEHelper.verifyElement("CustomWindowTitle");mEHelper.waitForWorkAroundTime(500);
		mEHelper.click("CustomOk");mEHelper.waitForWorkAroundTime(500);
	}


	@When("^Add \"([^\"]*)\" record with below values$")
	public void add_something_fee_record_with_below_values(String strArg1, DataTable feeRecords) {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		mEHelper.addActivity(strArg1);
		System.out.println("Before if condition the Estimate Type is "+BudgetType);
		List<List<String>> data = feeRecords.raw();
		this.ResourceType = data.get(1).get(0);
		this.ProfName = data.get(1).get(1);
		this.Hours = data.get(2).get(1);
		this.StartDate = data.get(3).get(1);
		this.feeActivity = data.get(0).get(1)+""+timestamp;
		if(BudgetType.equals("Matter Detail"))
		{
			mEHelper.enterActivity(data.get(0).get(1),timestamp,strArg1);
		}
		mEHelper.selectResource(data.get(1).get(1),strArg1);
		mEHelper.enterHours(data.get(2).get(1),strArg1);
		mEHelper.enterStartDate(data.get(3).get(1),strArg1);
	}

	@When("^Delete the \"([^\"]*)\" Fee Record$")
	public void delete_the_something_fee_record(String strArg1) throws Throwable {
		if(strArg1.equals("2nd")){
			mEHelper.click("FeePlanLocators.FeeSecondRow.Delete");mEHelper.waitForWorkAroundTime(500);
			mEHelper.verifyElement("CustomWindowTitle");mEHelper.waitForWorkAroundTime(500);
			mEHelper.click("CustomOk");mEHelper.waitForWorkAroundTime(500);
			System.out.println("Delete the record");
		}
	}
	//Implementation in progress for Type 1
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify \"([^\"]*)\" Fee Record is deleted from database$")
	public void verify_something_fee_record_is_deleted_from_database(String strArg1) throws Throwable {
		if(BudgetType.equals("Matter Detail")){
			if(strArg1.equals("2nd")){
				System.out.println("Verify record deleted from database");
				Assert.assertTrue(sqlHelper.isActivityDeletedInDB(EstimateName, feeActivity));
			}
		}
	}

	//---------------------------------------- Implementing for cost rate -----------------------------------
	//@ContinueNextStepsFor({AssertionError.class})
	@Then("^verify Standard rate and hourly budget for \"([^\"]*)\" Fee Record$")
	public void verify_standard_rate_and_hourly_budget_for_something_fee_record(String strArg1) throws SQLException, ClassNotFoundException  {
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(EstimateName, ProfName, EstimateCurrency, ResourceType, commonHelper.convertDateMMDDYYYY(StartDate));


		switch(strArg1) {
		case "1st":
			mEHelper.verifystdRateOnUIForProf(TotalProfRate, "FeePlanLocators.FeeFirstRow.StdRateTD");
			this.tkprCost1 = sqlHelper.getTkprCostFromDB(EstimateName, ProfName, EstimateCurrency,ResourceType, commonHelper.convertDateMMDDYYYY(StartDate)) * Double.parseDouble(Hours);
			break;
		case "2nd":
			mEHelper.verifystdRateOnUIForProf(TotalProfRate, "FeePlanLocators.FeeFirstRow.StdRateTD");
			this.tkprCost2 = sqlHelper.getTkprCostFromDB(EstimateName, ProfName, EstimateCurrency,ResourceType, commonHelper.convertDateMMDDYYYY(StartDate)) * Double.parseDouble(Hours);
			break;
		}

	}
	//Implementation in progress for Type 1
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Start Date saving correctly in Database for \"([^\"]*)\" record$")
	public void verify_start_date_saving_correctly_in_database_for_something_record(String strArg1) throws Throwable {
		if(BudgetType.equals("Matter Detail")){
			if(strArg1.equals("2nd Fee")){
				String UIDate = mEHelper.getElementText("FeePlanLocators.FeeSecondRow.StartDateTD");
				String DBDate = sqlHelper.getActivityFeeStartDate(EstimateName,feeActivity);
				boolean result = mEHelper.isValuesMatch(commonHelper.convertDateToYYYYMMDD(UIDate),DBDate.replace("-", "/"));
				Reporter.addStepLog("Date at UI before format conversion: "+UIDate);
				Reporter.addStepLog("Date at UI: "+commonHelper.convertDateToYYYYMMDD(UIDate));
				Reporter.addStepLog("Date in Database: "+DBDate.replace("-", "/"));
				if(result == false){
					Reporter.addStepLog("Start Date not matched with Database");
					throw new AssertionError("Start Date not matched with Database");
				}
			}
			if(strArg1.equals("1st Fee")){
				String UIDate = mEHelper.getElementText("FeePlanLocators.FeeFirstRow.StartDateTD");
				String DBDate = sqlHelper.getActivityFeeStartDate(EstimateName,feeActivity);
				boolean result = mEHelper.isValuesMatch(commonHelper.convertDateToYYYYMMDD(UIDate),DBDate.replace("-", "/"));
				Reporter.addStepLog("Date at UI: "+commonHelper.convertDateToYYYYMMDD(UIDate));
				Reporter.addStepLog("Date in Database: "+DBDate.replace("-", "/"));
				if(result == false){
					Reporter.addStepLog("Start Date not matched with Database");
					throw new AssertionError("Start Date not matched with Database");
				}
			}
		}
		if(BudgetType.equals("Matter Budget")){
			if(strArg1.equals("2nd Fee")){
				String UIDate = mEHelper.getElementText("FeePlanLocators.FeeSecondRow.StartDateTD");
				String DBDate = sqlHelper.getType1FeeStartDate(EstimateName);
				boolean result = mEHelper.isValuesMatch(commonHelper.convertDateToYYYYMMDD(UIDate),DBDate.replace("-", "/"));
				Reporter.addStepLog("Date at UI before format conversion: "+UIDate);
				Reporter.addStepLog("Date at UI: "+commonHelper.convertDateToYYYYMMDD(UIDate));
				Reporter.addStepLog("Date in Database: "+DBDate.replace("-", "/"));
				if(result == false){
					Reporter.addStepLog("Start Date not matched with Database");
					throw new AssertionError("Start Date not matched with Database");
				}
			}
			if(strArg1.equals("1st Fee")){
				String UIDate = mEHelper.getElementText("FeePlanLocators.FeeFirstRow.StartDateTD");
				String DBDate = sqlHelper.getType1FeeStartDate(EstimateName);
				boolean result = mEHelper.isValuesMatch(commonHelper.convertDateToYYYYMMDD(UIDate),DBDate.replace("-", "/"));
				Reporter.addStepLog("Date at UI: "+commonHelper.convertDateToYYYYMMDD(UIDate));
				Reporter.addStepLog("Date in Database: "+DBDate.replace("-", "/"));
				if(result == false){
					Reporter.addStepLog("Start Date not matched with Database");
					throw new AssertionError("Start Date not matched with Database");
				}
			}
		}
	}
	@And("^Click on \"([^\"]*)\" button$")
	public void click_on_something_button(String strArg1) {
		mEHelper.saveDetails();
		mEHelper.waitForConfirmation("SaveConfirmation", 5);
	}
	@When("^Switch to \"([^\"]*)\" Tab$")
	public void switch_to_something_tab(String TabName)  {
		mEHelper.switchToTab(TabName);

	}
	@When("^Add \"([^\"]*)\" Cost record with below values$")
	public void add_something_cost_record_with_below_values(String strArg1, DataTable dt)  {
		mEHelper.addActivity(strArg1);
		List<List<String>> data = dt.raw();
		this.ResourceType = data.get(1).get(0);
		this.ProfName = data.get(1).get(1);
		this.CostCode = data.get(2).get(1);
		this.Unit = data.get(3).get(1);
		if(BudgetType.equals("Matter Detail"))
		{
			mEHelper.enterActivity(data.get(0).get(1),new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),strArg1);
		}
		mEHelper.selectResource(ProfName,strArg1);
		mEHelper.enterCostCode(CostCode,strArg1);
		mEHelper.enterUnit(Unit,strArg1);
	}
	@And("^Verify Rate For Cost Code of \"([^\"]*)\" cost record$")
	public void verify_rate_for_cost_code_of_something_cost_record(String strArg1) throws ClassNotFoundException, SQLException  {
		String costRateFromDB = sqlHelper.getCostRateFromDB(CostCode);
		switch(strArg1) {
		case "1st":
			mEHelper.verifyCostRate(costRateFromDB, "CostPlanLocators.CostFirstRow.RateTD");
			break;
		case "2nd":
			mEHelper.verifyCostRate(costRateFromDB, "CostPlanLocators.CostSecondRow.RateTD");
			break;
		}

	}
	@And("^Verify Amount of \"([^\"]*)\" cost plan record$")
	public void verify_amount_of_something_cost_plan_record(String strArg1) {
		switch(strArg1){
		case "1st":
			String costRateFromUIFR = mEHelper.getCostRateFromUI("CostPlanLocators.CostFirstRow.RateTD");
			mEHelper.verifyCostAmount(Unit,costRateFromUIFR, "CostPlanLocators.CostFirstRow.AmountTD");
			break;
		case"2nd":
			String costRateFromUISR = mEHelper.getCostRateFromUI("CostPlanLocators.CostSecondRow.RateTD");
			mEHelper.verifyCostAmount(Unit, costRateFromUISR, "CostPlanLocators.CostSecondRow.AmountTD");
			break;
		}
	}
	@And("^Verify Estimate and Client Info$")
	public void verify_estimate_and_client_info(DataTable dt) throws ClassNotFoundException, SQLException  {
		String estimateCreatedTime = sqlHelper.getEstimateCreatedTime(EstimateName);
		String estimateStatus = sqlHelper.getEstimateStatus(EstimateName);
		String estimateType = sqlHelper.getEstimateType(EstimateName);
		mEHelper.verifyEstimateInfo(clientName,EstimateName, EstimateCurrency,practiceGroup,estimateType,estimateStatus,mEHelper.convertDateMMDDYYYY(estimateCreatedTime));
	}
	@And("^Add \"([^\"]*)\" Firm Record with below values$")
	public void add_something_firm_record_with_below_values(String firmRecord, DataTable dt)  {
		mEHelper.addInfoRecord(firmRecord);
		List<List<String>> data = dt.raw();
		String recordType = data.get(0).get(1);
		String contactPerson = data.get(1).get(1);
		mEHelper.enterFirmRecord(recordType,contactPerson,firmRecord);
	}

	//----------------------------------------------------------------------------- not implemented -----------------------------------------------------	
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Email and phone validation for \"([^\"]*)\" with below details$")
	public void verify_email_and_phone_validation_for_something_with_below_details(String clientDetails, DataTable dt) throws Throwable {
		mEHelper.addInfoRecord(clientDetails);
		List<List<String>> data = dt.raw();
		String contactPerson = data.get(0).get(1)+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String invalidEmail = data.get(1).get(1);
		String inValidPhone = data.get(2).get(1);
		String validEmail = data.get(3).get(1);
		mEHelper.enterClientDetails(contactPerson,invalidEmail,inValidPhone,clientDetails);
		mEHelper.waitForWorkAroundTime(900);
		mEHelper.verifyElement("InfoTabLocators.InvalidEmail");
		mEHelper.waitForWorkAroundTime(900);

		mEHelper.click("InfoTabLocators.ClientSection.DeleteButton");
		mEHelper.waitForWorkAroundTime(500);
		mEHelper.verifyElement("CustomWindowTitle");mEHelper.waitForWorkAroundTime(500);
		mEHelper.click("CustomOk");mEHelper.waitForWorkAroundTime(500);

		mEHelper.addInfoRecord(clientDetails);
		mEHelper.enterClientDetails(contactPerson,validEmail,inValidPhone,clientDetails);
		mEHelper.waitForWorkAroundTime(900);
		mEHelper.click("InfoTabLocators.ClientSection.ContactPersonTD");
		mEHelper.waitForWorkAroundTime(900);
		mEHelper.verifyElement("InfoTabLocators.InvalidPhone");


	}
	@Then("^Delete the \"([^\"]*)\" row$")
	public void delete_the_something_row(String strArg1) throws Throwable {
		mEHelper.click("InfoTabLocators.ClientSection.DeleteButton");
		mEHelper.waitForWorkAroundTime(500);
		mEHelper.verifyElement("CustomWindowTitle");mEHelper.waitForWorkAroundTime(500);
		mEHelper.click("CustomOk");mEHelper.waitForWorkAroundTime(500);
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------
	@And("^\"([^\"]*)\" Client Details as below$")
	public void something_client_details_as_below(String clientDetails, DataTable dt)  {
		mEHelper.addInfoRecord(clientDetails);
		List<List<String>> data = dt.raw();
		String contactPerson = data.get(0).get(1)+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String email = data.get(1).get(1);
		String phone = data.get(2).get(1);
		mEHelper.enterClientDetails(contactPerson,email,phone,clientDetails);
		mEHelper.waitForWorkAroundTime(900);
		mEHelper.click("InfoTabLocators.ClientSection.ContactPersonTD");
	}


	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Validate \"([^\"]*)\" Dates with below entries$")
	public void validate_third_party_dates_with_below_entries(String strArg1, DataTable dt) throws Throwable{
		mEHelper.addThirdPartyRecord(strArg1);
		List<List<String>> data = dt.raw();
		String expCategory = data.get(0).get(1);
		String expWitness = data.get(1).get(1);
		String catExpert = data.get(2).get(1);
		String hours = data.get(3).get(1);
		String startDate = mEHelper.addInCurrentDate(2);
		String endDate = mEHelper.getTodayDate();
		mEHelper.addThirdPartyrows(expCategory, expWitness, catExpert, hours, startDate, endDate, strArg1);
		mEHelper.waitForWorkAroundTime(200);

		mEHelper.verifyElement("DateError");


		mEHelper.waitForWorkAroundTime(500);

	}
	@Then("^Delete the Third Party record$")
	public void delete_the_third_party_record() {
		mEHelper.click("ThirdPartyLocators.TPFirstRow.FRThirdPartyDelete");
		mEHelper.waitForWorkAroundTime(500);
		mEHelper.verifyElement("CustomWindowTitle");mEHelper.waitForWorkAroundTime(500);
		mEHelper.click("CustomOk");mEHelper.waitForWorkAroundTime(500);
	}


	@And("^Add \"([^\"]*)\" Third Party Record with below values$")
	public void add_something_third_party_record_with_below_values(String strArg1, DataTable dt) throws ClassNotFoundException, SQLException  {
		mEHelper.addThirdPartyRecord(strArg1);
		List<List<String>> data = dt.raw();
		String expCategory = data.get(0).get(1);
		String expWitness = data.get(1).get(1);
		String catExpert = data.get(2).get(1);
		String hours = data.get(3).get(1);
		String startDate = mEHelper.getTodayDate();
		String endDate = mEHelper.getTodayDate();
		mEHelper.addThirdPartyrows(expCategory, expWitness, catExpert, hours, startDate, endDate, strArg1);
		this.ThirdPartyCatExpertRate = sqlHelper.getThirdPartyRatefromDB(catExpert);
		this.ThirdPartyHours = hours;
	}
	@ContinueNextStepsFor({AssertionError.class})
	@And("^Verify Rate and Third Party Cost calculation for \"([^\"]*)\" row$")
	public void verify_rate_and_third_party_cost_calculation_for_something_row(String rateline)  {
		mEHelper.verifyTPRateAndCost(rateline, ThirdPartyCatExpertRate,ThirdPartyHours);
	}
	/*@And("^Verify Fee and Cost Details in below columns$")
	public void verify_fee_and_cost_details_in_below_columns(DataTable dt)  {

	}
	@And("^Verify Unassigned \"([^\"]*)\" and \"([^\"]*)\"$")
	public void verify_unassigned_something_and_something(String strArg1, String strArg2)  {

	}
	 */
	@And("^click on copy and edit button on \"Current Budget\"$")
	public void click_on_copy_and_edit_button_on_current_budget() {
		mEHelper.addNewWhatIf();	
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify new What if added$")
	public void verify_new_what_if_added() {
		mEHelper.verifyElementPresent("WhatIfLocators.WhatIfRight");
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Change resource, hours, Avg rate and Budget as below mentionedin new what if$")
	public void change_resource_hours_avg_rate_and_budget_as_below_mentionedin_new_what_if(DataTable dt) {
		List<List<String>> data = dt.raw();
		this.whatIfresource = data.get(0).get(1);
		this.whatIfhours  = data.get(1).get(1);
		this.wahtIfavgRate = data.get(2).get(1);
		mEHelper.changewhatIfdata(whatIfresource, whatIfhours, wahtIfavgRate);
	}
	@And("^Save the What If$")
	public void save_the_what_if() {
		mEHelper.saveWhatIf("WhatIfLocators.SaveWhatIf");
	}

	@And("^Verify that Total Budget and Budget Margin percentage updated correctly$")
	public void verify_that_total_budget_and_budget_margin_percentage_updated_correctly() {
		mEHelper.verifyWhatIfUpdate(whatIfresource, whatIfhours, wahtIfavgRate);
	}

	@And("^Calculate Hourly Fee Budget$")
	public void verify_hourly_fee_budget() throws Throwable {
		this.totalhfb = mEHelper.calculateHourlyFeeOnUI();
		writeToFile.WritePropertiesFile("Hourly_Fee_Budget", String.valueOf(totalhfb));//write to file
	}
	@And("^Calculate Proposed Budget$")
	public void verify_propersed_budget() throws Throwable {
		this.totalCost = mEHelper.calculateCost();
		this.totalThirdParty = mEHelper.calculateThirdParty();
		//this.proposedBudget = mEHelper.calculateProposedBudgetOnUI(totalhfb);
		this.proposedBudget = mEHelper.calculateProposedBudgetOnUI(totalhfb, totalCost, totalThirdParty);
		writeToFile.WritePropertiesFile("Proposed_Budget", String.valueOf(proposedBudget));//write to file
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Match Calculated Hourly Fee Budget and Proposed Budget with Database$")
	public void match_calculated_hourly_fee_budget_and_proposed_budget_with_database() throws Throwable{
		double hourlyFeeBudgetFromDB = 0.00;
		if(BudgetType.equals("Matter Detail")){
			hourlyFeeBudgetFromDB = Double.parseDouble(sqlHelper.getHourlyFeeBudgetType3(EstimateName));
		}
		if(BudgetType.equals("Matter Budget")){
			hourlyFeeBudgetFromDB = Double.parseDouble(sqlHelper.getHourlyFeeBudgetType1(EstimateName));
		}

		mEHelper.verifyHourlyFeeBudget(totalhfb, hourlyFeeBudgetFromDB);
		Assert.assertEquals(totalhfb, hourlyFeeBudgetFromDB, "Hourly Fee Budget is not calculated correctly");
		double proposedBudgetFromDB = Double.parseDouble(sqlHelper.getProposedBudget(EstimateName));
		mEHelper.verifyProposedBudget(proposedBudget, proposedBudgetFromDB);
		Assert.assertEquals(proposedBudget, proposedBudgetFromDB, "Proposed budget is not calculated correctly");
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Calculate Budget Profit and match with Database$")
	public void calculate_budget_profit_and_match_with_database() throws Throwable {
		this.budgetProfit = mEHelper.calculateBudgetProfit(totalhfb,tkprCost1, tkprCost2);
		mEHelper.verifyBudgetProfit(budgetProfit);
		writeToFile.WritePropertiesFile("Budget_Profit", String.valueOf(budgetProfit));//write to file
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Calculate Budget Margin and match with Database$")
	public void calculate_budget_margin_and_match_with_database() throws Throwable {
		this.budgetMargin = mEHelper.getBudgetMargin(budgetProfit,totalhfb);
		mEHelper.verifyBudgetMargin(String.valueOf(budgetMargin)+"%");
		writeToFile.WritePropertiesFile("Budget_Margin", String.valueOf(budgetMargin));//write to file
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify Set Margin Threshold grid is opened$")
	public void verify_set_margin_threshold_grid_is_opened() throws Throwable {
		mEHelper.waitForConfirmation("SetMargin.grid", 5);
		mEHelper.verifyElementPresent("SetMargin.grid");
	}
	@And("^Get the Margin color values of Practice Group and if selected practice group is not mentioned then get it for Firm$")
	public void get_the_margin_color_values_of_practice_group_and_if_selected_practice_group_is_not_mentioned_then_get_it_for_firm() throws Throwable {
		this.lowPercentage = mEHelper.getRedPercentageValue(practiceGroup);
		this.highPercentage = mEHelper.getGreenPercentageValue(practiceGroup);
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Validate the slider bar color$")
	public void validate_the_slider_bar_color() throws Throwable {
		mEHelper.verifyBudgetMarginColor(lowPercentage, highPercentage);
	}
}
