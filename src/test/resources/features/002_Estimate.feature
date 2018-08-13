#========================================================================================================================
#Author: akhanduri@helm360.com
#Created At: 22 May 2018
#========================================================================================================================

Feature: Estimate Related test cases
Description: In this Feature file all the estimate related transactions for example create estimate, Fee Plan, Cost Plan, 
Third Party, Budget to Actual etc transactions
#------------------------------------------------------------------------------------------------------------------------
Background: User logged in with valid credentials
		Given Open Browser and navigate to application URL
		Given Verify user is at Login Page
		When Enter username as "Admin_User"
	  And Enter password as "Admin_Passowrd"
 	  And Click on login button
    Then Verify user should Redirected to "MatterMgtDash" page
    
#========================================================================================================================
#[Test_Case_002] Test Create Estimate (Type 1/Type3), 
#========================================================================================================================
#Tags to executes
#========================================================================================================================
@SanitySuite @EstimateTest1
Scenario: Create New Estimate with Type One (Matter Details)	
		When Select "Create an Estimate" from LeftPan
	  And Click on "FreshEstimateOpt"
	  And Click on "Next"
	  Then Verify user should Redirected to "CreateEstimatePage" page
	  When Enter Estimate Name starting with below text
	  		|Estimate Name	|AutomationEstimate|
	  		  		
	  And Select Type of Budget as below
	  		|Type of Budget	|Matter Detail		|
	  		
	  And Select Matter Type and Rate Type  		
	  And Type and select Client Name, Practice Group, Template and Currency
	  		|Client Name		|SONY CORPORATION	|
	  		|Practice Group	|Corporate Tax		|
	  		|Currency				|U. S. Dollars		|	
#Added 10 Second of wait to see the filled values	  		 	 		
	 	When Click on "CreateNewEstimateBtn"
	 	And I verify the Fee Plan page
	 	Then Verify Estimat created in DB
#========================================================================================================================
#[Test_Case_003] Test Fee Plan page through New Estimate (Type3)
#========================================================================================================================
		And Click on 'Add New Record' button
	 	Then Verify New line should be added in the grid
	 	When Enter some activity name in the 'Activity' column
	 	Then Verify Activity field should be mandatory and cannot be left blank
	 	When Click on Resources dropdown in Resources column
#	 	Then Verify Only active resources should get populated in resources dropdown 
	 	And Select below Title for First row
	 			|Title		|Administration	|
	 	When Enter Hours as "8" for first row
	 	And Enter start date and End Date
	 	Then Verify If End date is prior to Start date an error message should popup
	 	And Verify Std. Rate field should be non editable 
	 	And Verify Std. Rates should be fetched correctly from DB as per title and start date : 1st row
	 	And Verify propose Rate field is editable
	 	And Verify Proposed Rate field calculated correctly : 1st row
	 	And Verify Hourly Budget field : 1st row
	 	
#------------------------------------------------------------------------------------------------------------------------
#  Second Record ==>> In below steps we are validating standard rate and hourly budget for Resource with currency conversion 	
# 									  These values will be calculated from database and match on UI
#------------------------------------------------------------------------------------------------------------------------
	 	When Click on 'Add New Record' button again
	 	Then Verify on more New line should be added in the grid
	 	When Enter some activity name in the 'Activity' column of second row
	 	When Click on Resources dropdown in Resources column of second row
	 	And Select any Resource Name from DATABASE in second row
	 	When Enter some hours in 'Hours' Column of second row
	 	And Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for second row
	 	And Verify Hourly Budget column field for second row
	 	
#------------------------------------------------------------------------------------------------------------------------
#  Third Record ==>> In below step validate the currency conversion for standard rate with different date ranges 	
# 									 These values will be calculated from database and match on UI
#------------------------------------------------------------------------------------------------------------------------
		When Add third activity with below values
				|Activity		|TimeActivity	|
				|Resource		|B Summers		|
				|Hours			|2						|
				|Start Date	|1/1/2005			|
		Then verify Standard rate and hourly budget for third record
		
#------------------------------------------------------------------------------------------------------------------------
#  Fourth Record ==>> In below step validate the currency conversion for standard rate with different date ranges
# 									  These values will be calculated from database and match on UI 	
#------------------------------------------------------------------------------------------------------------------------
		When Add fourth activity with below values
				|Activity		|TimeActivity	|
				|Resource		|B Summers		|
				|Hours			|2						|
				|Start Date	|2/1/2005			|
		Then verify Standard rate and hourly budget for fourth record

#------------------------------------------------------------------------------------------------------------------------
#  Fifth Record ==>> In below step validate the currency conversion for standard rate with different date ranges 	
# 									 These values will be calculated from database and match on UI
#------------------------------------------------------------------------------------------------------------------------
When Add fifth activity with below values
				|Activity		|TimeActivity	|
				|Resource		|B Summers		|
				|Hours			|2						|
				|Start Date	|2/1/2008			|
		Then verify Standard rate and hourly budget for fifth record
	 	
#------------------------------------------------------------------------------------------------------------------------
#  In below steps we are verifying if there are multiple phase and task then we can add reords in other phase's task
#------------------------------------------------------------------------------------------------------------------------	 	
		When If available add new record for other phase and task otherwise add on the same
		Then Verify new line added for third record
		When Enter Activity for third row
		And Click on resource dropdown in Activity column of third row
		
#------------------------------------------------------------------------------------------------------------------------
#  Select Resource with Timekeeper rate
#------------------------------------------------------------------------------------------------------------------------	
		And Select any Resource Name from DATABASE in third row
		When Enter "1" hour in 'Hours' Column of third row
		Then Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for third row
		And Verify Hourly Budget column field with "1" Proposed rate and "1" hour for third row
		
#------------------------------------------------------------------------------------------------------------------------
#  In below step we are validating hourly budget for 0 hours###
#------------------------------------------------------------------------------------------------------------------------		
		When Enter "0" hour in 'Hours' Column of third row
		And Enter the Propose rate "1" for third row
		Then Verify Hourly budget field with "0" hour and "1" proposed rate
		
#------------------------------------------------------------------------------------------------------------------------
#  In below step we are validating the currency symbol
#------------------------------------------------------------------------------------------------------------------------		
		And Verify Currency symbol at UI
		
#------------------------------------------------------------------------------------------------------------------------
#  In below steps we are validating Budget, Total hourly budget and proposed budget in silde bar
#------------------------------------------------------------------------------------------------------------------------
		When Click on Save Estimate button
 	  Then Verify Estimate has been saved 	
	 	When Open Slider from bottom
	 	Then Verify Hourly Fee Budget, Total Hourly Fee Budget and Proposed Budget values
	 	And  Hide the Slider from bottom
	 	
	 	Then Close the browser
