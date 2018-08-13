package com.Vision.step_definations;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.testng.Assert;

import com.Vision.pageHelper.CommonHelper;
import com.Vision.pageHelper.SQLConnection;
import com.Vision.pageHelper.EstimateHelper;
import com.Vision.pageHelper.SQLHelper;
import com.Vision.pageHelper.WriteDataToFileHelper;
import com.Vision.util.DriverTestCase;
import com.Vision.util.PropertyReader;
import com.cucumber.listener.Reporter;

import cucumber.api.DataTable;
import cucumber.api.java.ContinueNextStepsFor;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateEstimate
{
	SQLHelper sqlHelper;
	EstimateHelper estimateHelper;
	CommonHelper commonHelper;
	DriverTestCase driverTestCase;
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	String EstimateName = null;
	String TimeActivity = null;
	String ProfTitle=null;
	String ProfName = null;
	String TimeKeepersId = null;
	String Rate = null;
	double TotalProfRate;
	String[] ActiveResources;
	String[] InActiveResources;
	SQLConnection sqlConnection;
	String EstimateType = "";
	String EstimateCurrency = "";
	String ResourceType = "";
	String Hours = "";//using after row 2
	String StartDate = "";//using after row 2
	protected PropertyReader propertyReader = new PropertyReader();
	protected WriteDataToFileHelper writeToFile = new WriteDataToFileHelper();
	public CreateEstimate() 
	{
		driverTestCase = new DriverTestCase();
		estimateHelper = new EstimateHelper(driverTestCase.getWebDriver());
		commonHelper = new CommonHelper(driverTestCase.getWebDriver());
		sqlConnection= new SQLConnection(driverTestCase.getWebDriver());
		sqlHelper = new SQLHelper(driverTestCase.getWebDriver());
	}
	//----------------------------------------------------------------------------------------------	
	@When("^Select \"(.+?)\" from LeftPan$")
	public void select_option_from_left_pan(String Option)
	{
		estimateHelper.verifyElementPresent("BreadCrumb", 5);
		commonHelper.click("BreadCrumb");
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.selectFromLeftPan(Option);
	}
	//---------------------------------------------------------------------------------------------	
	@And("^Click on \"(.+?)\"$")
	public void click_on_element(String option)
	{
		estimateHelper.verifyElementPresent(option, 5);
		commonHelper.click(option);
		estimateHelper.waitForWorkAroundTime(5);
	}
	//---------------------------------------------------------------------------------------------	
	@And("^Click multiple times on \"(.+?)\"$")
	public void click_multiple_times_on_element(String option)
	{
		estimateHelper.verifyElementPresent(option, 5);
		commonHelper.doubleClick(option);
		commonHelper.click(option);
		commonHelper.click(option);
		
		estimateHelper.waitForWorkAroundTime(5);
	}
	//----------------------------------------------------------------------------------------------	
	@When("^Enter Estimate Name starting with below text$")
	public void enter_estimate_name(DataTable EstimateName) throws FileNotFoundException, UnsupportedEncodingException
	{
		String localtime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		//UUID uuid = UUID.randomUUID();
		//String randomUUIDString = uuid.toString();
		List<List<String>> data = EstimateName.raw();
		String EstName = data.get(0).get(1).toString();
		estimateHelper.type("EstimateNameTxt", EstName+""+localtime);
		this.EstimateName =EstName+""+localtime;
		System.out.println(this.EstimateName);
		//METabsStepDefinitions.EstimateName = EstName+""+localtime;
		//------------------------------------------------------------------------------------------
		//PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"/EstimateName.txt", "UTF-8");
		//writer.print(EstName+""+localtime);
		//writer.close();

	}
	//----------------------------------------------------------------------------------------------	
	@And("^Select Type of Budget as below$")
	public void select_type_of_budget(DataTable TypeOfBudget)
	{
		commonHelper.click("TypeOfBudgetDropdown");
		List<List<String>> data = TypeOfBudget.raw();
		String BudgetType = data.get(0).get(1).toString();
		switch(BudgetType)
		{
		case "Matter Detail":
			estimateHelper.verifyElementPresent("TypeOfBudgetMatterDetail", 3);
			commonHelper.click("TypeOfBudgetMatterDetail");
			this.EstimateType = "Matter Detail";
			//METabsStepDefinitions.BudgetType = "Matter Detail";//----------------------------------------------
			break;
		case "Matter Budget":
			estimateHelper.verifyElementPresent("TypeOfBudgetMatterBudget", 3);
			commonHelper.click("TypeOfBudgetMatterBudget");
			this.EstimateType = "Matter Budget";
			//METabsStepDefinitions.BudgetType = "Matter Detail";
			break;
		default:
			System.out.println("There is some issue with entering Budget Type");		
		}
	}
	@And("^Type and select Client Name, Practice Group, Template and Currency$")
	public void slect_client_practice_template_currency(DataTable values) throws ClassNotFoundException, SQLException, IOException
	{
		List<List<String>> data = values.raw();
		String ClientName = data.get(0).get(1);
		String PracticeGroup = data.get(1).get(1);
		if(data.get(2).get(1).equals("U. S. Dollars")){
			this.EstimateCurrency = "USD";
			//METabsStepDefinitions.EstimateCurrency = "USD";
		}
		else if(data.get(2).get(1).equals("EURO")){
			this.EstimateCurrency = "EUR";
			//METabsStepDefinitions.EstimateCurrency = "EUR";
		}
		else if(data.get(2).get(1).equals("Japanese Yen")){
			this.EstimateCurrency = "JPY";
			//METabsStepDefinitions.EstimateCurrency = "JPY";
		}
		else if(data.get(2).get(1).equals("Hungarian Forint")){
			this.EstimateCurrency = "HUF";
			//METabsStepDefinitions.EstimateCurrency = "HUF";
		}
		else if(data.get(2).get(1).equals("Swedish Krona")){
			this.EstimateCurrency = "SEK";
			//METabsStepDefinitions.EstimateCurrency = "SEK";
		}
		else if(data.get(2).get(1).equals("Canadian Dollar")){
			this.EstimateCurrency = "CAD";
			//METabsStepDefinitions.EstimateCurrency = "CAD";
		}
		else if(data.get(2).get(1).equals("Pound Sterling")){
			this.EstimateCurrency = "GBP";
			//METabsStepDefinitions.EstimateCurrency = "GBP";
		}
		else if(data.get(2).get(1).equals("Hong Kong Dollar")){
			this.EstimateCurrency = "HKD";
			//METabsStepDefinitions.EstimateCurrency = "HKD";
		}
		else if(data.get(2).get(1).equals("French Franc - NO LONGER USED")){
			this.EstimateCurrency = "FF";
			//METabsStepDefinitions.EstimateCurrency = "FF";
		}

		System.out.println("Client Name is : "+ ClientName+" Practice Group is : "+PracticeGroup+" Template is : "+sqlHelper.getTemplateFromDB(PracticeGroup)+" Currency is : "+EstimateCurrency);
		commonHelper.type("ClientNameTxt", ClientName);
		commonHelper.click("ClientName");
		//METabsStepDefinitions.clientName = ClientName;

		commonHelper.type("PracticeGroupTxt", PracticeGroup);
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("PracticeGroupValue");
		//METabsStepDefinitions.practiceGroup = PracticeGroup;

		if(EstimateType.equals("Matter Detail")){
			commonHelper.type("TemplateName", sqlHelper.getTemplateFromDB(PracticeGroup));
			commonHelper.waitForWorkAroundTime(2000);
			commonHelper.click("TemplateValue");
		}
		commonHelper.type("CurrencyName", data.get(2).get(1));
		commonHelper.waitForWorkAroundTime(2000);
		estimateHelper.selectCurrency(data.get(2).get(1));
		//commonHelper.click("CurrencyValue");
		commonHelper.waitForWorkAroundTime(10000);

		//----------------------------------write to properties file ----------------------------------
		writeToFile.WritePropertiesFile("Estimate_Name", String.valueOf(EstimateName));//write to file
		writeToFile.WritePropertiesFile("Client_Name", String.valueOf(ClientName));//write to file
		writeToFile.WritePropertiesFile("Currency", String.valueOf(EstimateCurrency));//write to file
		writeToFile.WritePropertiesFile("Practice_Group", String.valueOf(PracticeGroup));//write to file
		writeToFile.WritePropertiesFile("Type_of_Budget", String.valueOf(EstimateType));//write to file
		//----------------------------------------------------------------------------------------------
		/*	OutputStream os = null;
		Properties prop = new Properties();
		prop.setProperty("Estimate_Name", EstimateName);
		prop.setProperty("Client_Name", ClientName);
		prop.setProperty("Currency", EstimateCurrency);
		prop.setProperty("Practice_Group", PracticeGroup);
		prop.setProperty("Type_of_Budget", EstimateType);

		try {
			os = new FileOutputStream("MyEstimateInfo.properties");
			prop.store(os, "Dynamic Property File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		//----------------------------------------------------------------------------------------

	}
	//----------------------------------------------------------------------------------------------	
	@And("^Select Matter Type and Rate Type$")
	public void select_matter_type_and_rate_type()
	{
		estimateHelper.selectMatterTypeAndRateType();//As of now Matter Type set to Hourly and Rate Type set to Standard bydefault
	}
	//----------------------------------------------------------------------------------------------	
	@Then("^I verify the Fee Plan page$")
	public void verify_fee_plan_page()
	{
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.verifyElementPresent("FeePlantab",5);
	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify Estimat created in DB$")
	public void verify_estimate_in_database()throws SQLException, ClassNotFoundException, IOException
	{
		Assert.assertTrue(sqlHelper.isEstimateCreatedInDB(EstimateName));

	}
	@ContinueNextStepsFor({AssertionError.class})
	@Then("^Verify duplicate Estimate not created in DB$")
	public void verify_duplicate_estimate_not_created_in_database()throws SQLException, ClassNotFoundException, IOException
	{
		if(sqlHelper.isDuplicatedEstimateNotCreatedInDB(EstimateName)==false){
			Reporter.addStepLog("Estimate Created Multiple Times in Database");
			throw new AssertionError("Estimate Created Multiple Times in Database");
		}
	}

	@When("^Expand the Tasks$")
	public void expand_taks()
	{
		if(EstimateType.equals("Matter Detail")){
			commonHelper.waitForWorkAroundTime(1000);
			commonHelper.click("TaskExpand");
		}
	}
	@When("^Open the created estimate$")
	public void open_created_estimate()
	{
		commonHelper.click("viewEstiamte");
		commonHelper.waitForWorkAroundTime(5000);
	}
	@And("^Click on 'Add New Record' button$")
	public void click_on_add_new_record(){
		System.out.println("Estimate Type is "+EstimateType);
		if(EstimateType.equals("Matter Detail")){
			commonHelper.click("TaskExpand");
		}
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("AddNewRecord");
	}
	@Then("^Verify New line should be added in the grid$")
	public void verify_new_line_created_in_grid(){
		estimateHelper.verifyNewLineAdded("FirstActivityLine");
	}
	@When("^Enter some activity name in the 'Activity' column$")
	public void enter_activity_name() {
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			estimateHelper.type("Activity", "TimeActivity"+""+randomUUIDString);
			this.TimeActivity ="TimeActivity"+""+randomUUIDString;
			Reporter.addStepLog("The First Activity is : "+TimeActivity);
		}
	}
	@Then("^Verify Activity field should be mandatory and cannot be left blank$")
	public void verify_activity_field_should_required(){
		if(EstimateType.equals("Matter Detail")){
			estimateHelper.verifyActivityFieldIsRequired();
		}
	}
	@When("^Click on Resources dropdown in Resources column$")
	public void click_on_resource_dropdown_row_first() {
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesOutline");
		commonHelper.click("ResourcesOutline");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesDropDown");
		commonHelper.waitForWorkAroundTime(1000);
	}
	@Then("^Verify Only active resources should get populated in resources dropdown$")
	public void verify_only_active_resources_should_get_populated()throws SQLException, ClassNotFoundException {		
		this.ActiveResources = sqlHelper.getActiveResourcesFromDB();
		this.InActiveResources = sqlHelper.getInActiveResourcesFromDB();
		estimateHelper.verifyResourcesInDropdown(ActiveResources,InActiveResources);
	}

	@And("^Select below Title for First row$")
	public void select_any_title_from_database(DataTable titleValues)throws SQLException, ClassNotFoundException {
		//this.ProfTitle = sqlHelper.getTitleFromDB(); //if we want to get random title value from database
		List<List<String>> data = titleValues.raw();
		this.ResourceType = data.get(0).get(0);
		this.ProfTitle = data.get(0).get(1);
		estimateHelper.selectResourcefromdb(ProfTitle);//this method may be confusing as select resource from db but its depends on ProfTitle, here it picked from test
		Reporter.addStepLog("Title Value is : "+ProfTitle);
	}
	@When("^Enter Hours as \"(.+?)\" for first row$")
	public void enter_hours_as_for_first_row(String hours) {
		commonHelper.click("Hours1");
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.clearFieldText("Hours3");
		commonHelper.type("Hours3", hours);
		commonHelper.waitForWorkAroundTime(500);
		commonHelper.click("StartDateField");
	}
	@When("^Enter start date and End Date$")
	public void enter_start_date_and_End_Date() {	
		estimateHelper.enterValidStartAndEndDate(); 
	}
	@Then("^Verify If End date is prior to Start date an error message should popup$")
	public void verify_If_End_date_is_prior_to_Start_date_an_error_message_should_popup() {
		estimateHelper.validateStartAndEndDate();
		estimateHelper.enterValidStartAndEndDate();
		commonHelper.waitForWorkAroundTime(1000);
	}
	@Then("^Verify Std\\. Rate field should be non editable$")
	public void verify_Std_Rate_field_should_be_non_editable() {
		estimateHelper.verifyStdRateNonEditable();
	}
	@Then("^Verify Std\\. Rates should be fetched correctly from DB as per title and start date : 1st row$")
	public void verify_Std_Rates__fetched_correctly_from_DB_as_per_title_and_start_date()throws SQLException, ClassNotFoundException  {
		this.Rate = String.valueOf(sqlHelper.getStandardRateFromDB(this.EstimateName, this.ProfTitle, EstimateCurrency, "Title", commonHelper.getTodayDate()));
		Reporter.addStepLog("Standard Rate From Database: "+Rate);
		System.out.println("this.Rate : "+this.Rate);
		estimateHelper.verifyTitleRateOnUI(this.Rate);
	}
	@And("^Verify propose Rate field is editable")
	public void verify_propose_rate_column_field_is_editable() {
		Assert.assertTrue(estimateHelper.isPropose_rate_field_editable());
	}
	@Then("^Verify Proposed Rate field calculated correctly : 1st row$")
	public void verify_Proposed_Rate_field_calculated_correctly() {
		estimateHelper.verifyProposedRateFieldValues(this.Rate, "HourlyBudget1Row", "ProposedRate1Row");//#######updated
		Reporter.addStepLog("Proposed Rate From Database: "+Rate);
	}
	@And("^Verify Hourly Budget field : 1st row$")
	public void verify_hourly_budget_field(){
		estimateHelper.verifyHourlyBudgetField(this.Rate,8, "HourlyBudget1Row");
	}
	@Then("^Verify on more New line should be added in the grid$")
	public void verify_one_more_new_line_created_in_grid(){
		estimateHelper.verifyNewLineAdded("SecondActivityLine");
	}
	@And("^Click on 'Add New Record' button again$")
	public void click_on_add_new_record_again(){
		commonHelper.click("AddNewRecord");
	}
	@When("^Enter some activity name in the 'Activity' column of second row$")//##########################
	public void enter_some_activity_name_in_the_activity_column_of_second_row(){
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			estimateHelper.type("ActivitySecondRow", "TimeActivity"+""+randomUUIDString);
			this.TimeActivity ="TimeActivity"+""+randomUUIDString;
			Reporter.addStepLog("The Second Activity is : "+TimeActivity);
		}
	}
	@When("^Click on Resources dropdown in Resources column of second row$")
	public void click_on_resource_dropdown_in_resources_column_of_second_row(){
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesOutlineSecondRow");
		commonHelper.click("ResourcesOutlineSecondRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesDropDownSecondRow");
		commonHelper.waitForWorkAroundTime(1000);
	}
	@And("^Select any Resource Name from DATABASE in second row$")//##################################
	public void select_any_resource_name_from_db_in_secind_row()throws SQLException, ClassNotFoundException {
		//Enter title in dropdown-------------------------------------------------------
		this.ProfName = sqlHelper.getResourceFromDB();
		estimateHelper.selectResourcefromdb(ProfName);
	}
	@When("^Enter some hours in 'Hours' Column of second row$")
	public void enter_some_hours_in_hours_column_of_second_row(){
		commonHelper.click("Hours1SecondRow");
		commonHelper.waitForWorkAroundTime(1000);

		commonHelper.clearFieldText("Hours3SecondRow");
		commonHelper.type("Hours3SecondRow", "8");
		commonHelper.click("StartDateFieldSecondRow");
	}
	@And("^Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for second row$")
	public void verify_std_rates_should_be_fetched_correctly_from_db_as_per_resource_and_its_start_date_for_second_row()throws SQLException, ClassNotFoundException{
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(this.EstimateName/*"AutomationEstimateType_MD2018.06.08.09.45.57"*/, this.ProfName, EstimateCurrency, "Resource", commonHelper.getTodayDate());
		System.out.println("Total prof rate for resource is : "+this.TotalProfRate);
		estimateHelper.verifystdRateOnUIForProf(TotalProfRate, "StdRate2Row");
	}
	@And("^Verify Hourly Budget column field for second row$")
	public void verify_hourly_budget_column_field_for_second_row(){
		estimateHelper.verifyHourlyBudgetField(String. valueOf(TotalProfRate), 8, "HourlyBudget2Row");
	}


	//Change the locators values
	@When("^Add third activity with below values$")
	public void add_third_activity_with_below_values(DataTable row3Values){
		List<List<String>> data = row3Values.raw();		
		//if type 3
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("AddNewRecord");
		commonHelper.waitForWorkAroundTime(2000);
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			estimateHelper.type("Activity3rdRow", "TimeActivity"+""+randomUUIDString);
			this.TimeActivity =data.get(0).get(1)+""+randomUUIDString;
			Reporter.addStepLog("The Third Activity is : "+TimeActivity);
		}
		//Enter Resource
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesOutline3rdRow");
		commonHelper.click("ResourcesOutline3rdRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesDropDown3rdRow");
		commonHelper.waitForWorkAroundTime(1000);
		this.ResourceType = data.get(1).get(0);
		this.ProfName = data.get(1).get(1);
		estimateHelper.selectResourcefromdb(ProfName); //here do not confuse with db in fun name not related to db
		//Enter hours
		this.Hours = data.get(2).get(1);
		commonHelper.click("Hours13rdRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.clearFieldText("Hours33rdRow");
		commonHelper.type("Hours33rdRow", Hours);
		commonHelper.click("StartDateField3rdRow");
		commonHelper.waitForWorkAroundTime(1000);
		//Enter Start Date
		this.StartDate = data.get(3).get(1);
		commonHelper.click("StartDateField3rd");
		commonHelper.type("StartDateInput3rd", data.get(3).get(1));
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("HourlyBudget3rdRow");
		commonHelper.waitForWorkAroundTime(1500);
	}

	@Then("^verify Standard rate and hourly budget for third record$")
	public void verify_standard_rate_and_hourly_budget_for_third_record() throws ClassNotFoundException, SQLException
	{
		System.out.println("Estimate name"+this.EstimateName+"Prof name"+ProfName+"Currency"+EstimateCurrency+"Resource Type"+ResourceType+"Date"+commonHelper.convertDateMMDDYYYY(StartDate));
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(this.EstimateName, ProfName, EstimateCurrency, ResourceType, commonHelper.convertDateMMDDYYYY(StartDate));
		System.out.println("Total prof rate for resource is from database: "+this.TotalProfRate);
		estimateHelper.verifystdRateOnUIForProf(TotalProfRate, "StdRate3rdRow");

		//estimateHelper.verifyProposedRateFieldValues(String.valueOf(TotalProfRate),"HourlyBudget3rdRow","ProposedRate3rdRow");
		//Reporter.addStepLog("Proposed Rate From Database: "+TotalProfRate);

		estimateHelper.verifyHourlyBudgetField(String.valueOf(this.TotalProfRate),Integer.parseInt(this.Hours), "HourlyBudget3rdRow");
	}

	@When("^Add fourth activity with below values$")
	public void add_fouth_activity_with_below_values(DataTable row4Values){
		List<List<String>> data = row4Values.raw();	
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("AddNewRecord");
		commonHelper.waitForWorkAroundTime(2000);
		//if type 3
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			estimateHelper.type("Activity4thRow", "TimeActivity"+""+randomUUIDString);
			this.TimeActivity =data.get(0).get(1)+""+randomUUIDString;
			Reporter.addStepLog("The Third Activity is : "+TimeActivity);
		}
		//Enter Resource
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesOutline4thRow");
		commonHelper.click("ResourcesOutline4thRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesDropDown4thRow");
		commonHelper.waitForWorkAroundTime(1000);
		this.ResourceType = data.get(1).get(0);
		this.ProfName = data.get(1).get(1);
		estimateHelper.selectResourcefromdb(ProfName); //here do not confuse with db in fun name not related to db
		//Enter hours
		this.Hours = data.get(2).get(1);
		commonHelper.click("Hours14thRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.clearFieldText("Hours34thRow");
		commonHelper.type("Hours34thRow", Hours);
		commonHelper.click("StartDateField4thRow");
		commonHelper.waitForWorkAroundTime(1000);
		//Enter Start Date
		this.StartDate = data.get(3).get(1);
		commonHelper.click("StartDateField4th");
		commonHelper.type("StartDateInput4th", data.get(3).get(1));
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("HourlyBudget4thRow");
		commonHelper.waitForWorkAroundTime(1500);
	}

	@Then("^verify Standard rate and hourly budget for fourth record$")
	public void verify_standard_rate_and_hourly_budget_for_fourth_record() throws ClassNotFoundException, SQLException
	{
		System.out.println("Estimate name"+this.EstimateName+"Prof name"+ProfName+"Currency"+EstimateCurrency+"Resource Type"+ResourceType+"Date"+commonHelper.convertDateMMDDYYYY(StartDate));
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(this.EstimateName, ProfName, EstimateCurrency, ResourceType, commonHelper.convertDateMMDDYYYY(StartDate));
		System.out.println("Total prof rate for resource is : "+this.TotalProfRate);
		estimateHelper.verifystdRateOnUIForProf(TotalProfRate, "StdRate4thRow");

		//estimateHelper.verifyProposedRateFieldValues(String.valueOf(TotalProfRate),"HourlyBudget3rdRow","ProposedRate3rdRow");
		//Reporter.addStepLog("Proposed Rate From Database: "+TotalProfRate);

		estimateHelper.verifyHourlyBudgetField(String.valueOf(this.TotalProfRate),Integer.parseInt(this.Hours), "HourlyBudget4thRow");
	}

	@When("^Add fifth activity with below values$")
	public void add_fifth_activity_with_below_values(DataTable row4Values){
		List<List<String>> data = row4Values.raw();
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("AddNewRecord");
		commonHelper.waitForWorkAroundTime(2000);
		//if type 3
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			estimateHelper.type("Activity5thRow", "TimeActivity"+""+randomUUIDString);
			this.TimeActivity =data.get(0).get(1)+""+randomUUIDString;
			Reporter.addStepLog("The Third Activity is : "+TimeActivity);
		}
		//Enter Resource
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesOutline5thRow");
		commonHelper.click("ResourcesOutline5thRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("ResourcesDropDown5thRow");
		commonHelper.waitForWorkAroundTime(1000);
		this.ResourceType = data.get(1).get(0);
		this.ProfName = data.get(1).get(1);
		estimateHelper.selectResourcefromdb(ProfName); //here do not confuse with db in fun name not related to db
		//Enter hours
		this.Hours = data.get(2).get(1);
		commonHelper.click("Hours15thRow");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.clearFieldText("Hours35thRow");
		commonHelper.type("Hours35thRow", Hours);
		commonHelper.click("StartDateField5thRow");
		commonHelper.waitForWorkAroundTime(1000);
		//Enter Start Date
		this.StartDate = data.get(3).get(1);
		commonHelper.click("StartDateField5th");
		commonHelper.type("StartDateInput5th", data.get(3).get(1));
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("HourlyBudget5thRow");
		commonHelper.waitForWorkAroundTime(1500);
	}

	@Then("^verify Standard rate and hourly budget for fifth record$")
	public void verify_standard_rate_and_hourly_budget_for_fifth_record() throws ClassNotFoundException, SQLException
	{
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(this.EstimateName, ProfName, EstimateCurrency, ResourceType, commonHelper.convertDateMMDDYYYY(StartDate));
		System.out.println("Total prof rate for resource is : "+this.TotalProfRate);
		estimateHelper.verifystdRateOnUIForProf(TotalProfRate, "StdRate5thRow");

		//estimateHelper.verifyProposedRateFieldValues(String.valueOf(TotalProfRate),"HourlyBudget3rdRow","ProposedRate3rdRow");
		//Reporter.addStepLog("Proposed Rate From Database: "+TotalProfRate);

		estimateHelper.verifyHourlyBudgetField(String.valueOf(this.TotalProfRate),Integer.parseInt(this.Hours), "HourlyBudget5thRow");
	}


	@When("^If available add new record for other phase and task otherwise add on the same$")
	public void if_available_add_new_record_for_other_phase_and_task_otherwise_add_on_same() {
		estimateHelper.addNewReocrdInOtherPhaseIfAvailable(EstimateType);
	}
	@Then("^Verify new line added for third record$")
	public void verify_new_line_added_for_third_record(){
		Assert.assertTrue(estimateHelper.isThirdLineAdded());
	}
	@When("^Enter Activity for third row$")
	public void enter_activity_for_third_row(){
		if(EstimateType.equals("Matter Detail")){
			UUID uuid = UUID.randomUUID();
			String randomUUIDString = uuid.toString();
			this.TimeActivity = estimateHelper.enterActivityForThirdRow("TimeActivityThird"+""+randomUUIDString);
			Reporter.addStepLog("Activity is : "+TimeActivity);
		}
	}
	@And("^Click on resource dropdown in Activity column of third row$")
	public void click_on_resource_dropdown_for_third_row() {
		estimateHelper.clickResourceForThird();
	}
	@And("^Select any Resource Name from DATABASE in third row$")
	public void select_any_resource_name_from_database_in_third_row() throws ClassNotFoundException, SQLException {
		this.ProfName = sqlHelper.getResourceWithEURFromDB();
		estimateHelper.selectResourcefromdb(ProfName);
	}
	@When("^Enter \"(.*?)\" hour in 'Hours' Column of third row$")
	public void enter_some_hours_in_hours_column_of_third_row(String hour){
		estimateHelper.enterHoursRowThird(hour);
	}
	@And("^Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for third row$")
	public void verify_std_rates_should_be_fetched_correctly_from_db_as_per_resource_and_its_start_date_for_third_row()throws SQLException, ClassNotFoundException{
		this.TotalProfRate = sqlHelper.getStandardRateFromDB(this.EstimateName/*"AutomationEstimateType_MD2018.06.08.09.45.57"*/, this.ProfName, EstimateCurrency, "Resource", commonHelper.getTodayDate());
		System.out.println("Total prof rate for resource is : "+this.TotalProfRate);
		estimateHelper.verifyStdRateForLastRecord(TotalProfRate);
		//estimateHelper.verifystdRateOnUIForProf(TotalProfRate, "StdRate2Row");
	}
	@And("^Verify Hourly Budget column field with \"(.*?)\" Proposed rate and \"(.*?)\" hour for third row$")
	public void verify_hourly_budget_column_field_for_third_row(String rate, String hour){
		estimateHelper.verifyBudgetForThird(TotalProfRate,Integer.parseInt(hour));
		//estimateHelper.verifyHourlyBudgetField(String. valueOf(TotalProfRate), 8, "Phase2Row1HourlyBudget");
	}
	@And("^Enter the Propose rate \"(.*?)\" for third row$")
	public void enter_the_propose_rate_for_third_row(String rate) {
		estimateHelper.enterProposeRate(rate);
	}
	@Then("^Verify Hourly budget field with \"(.*?)\" hour and \"(.*?)\" proposed rate$")
	public void verify_hourly_budget_field_calculated_correctly(String hour, String ProposedRate) {
		estimateHelper.verifyBudgetForThird(Double.parseDouble(ProposedRate),Integer.parseInt(hour));
	}
	@And("^Verify Currency symbol at UI$")
	public void verify_currency_sybmol_at_ui() {
		if(EstimateCurrency.equals("EUR")){
			Assert.assertTrue(estimateHelper.isCurrencySymbolMatch("€"));
		}
		else if(EstimateCurrency.equals("USD")){
			Assert.assertTrue(estimateHelper.isCurrencySymbolMatch("$"));
		}
	}

	@When("^Open Slider from bottom$")
	public void open_slider_from_bottom(){
		commonHelper.click("SliderDropdownIcon");
	}
	@Then("^Verify Hourly Fee Budget, Total Hourly Fee Budget and Proposed Budget values$")
	public void verify_hourly_fee_budget_total_hourly_fee_budget_and_proposed_budget(){
		estimateHelper.verifyValuesAtSider("HourlyFeeBudgetAtSlider");
		estimateHelper.verifyValuesAtSider("TotalHourlyFeeBudgetAtSlider");
		estimateHelper.verifyValuesAtSider("ProposedBudgetAtSilder");
		commonHelper.waitForWorkAroundTime(10000);//Added to see the result for POC
	}
	@When("^Hide the Slider from bottom$")
	public void hide_the_slider_from_bottom(){
		commonHelper.click("CloseSliderDropdown");
		commonHelper.waitForWorkAroundTime(500);
	}
	@When("^Click on Save Estimate button$")
	public void click_on_save_estimate_button(){
		commonHelper.click("btnSaveTaskDetails");
	}
	@Then("^Verify Estimate has been saved$")
	public void verify_estimate_has_been_saved(){
		commonHelper.waitForWorkAroundTime(500);
		estimateHelper.verifysavedDialoge("EstimateSavedConfirmation");
	}
}