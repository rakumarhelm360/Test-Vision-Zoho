#Feature: CreateEstimate

#========================================================================================================================
#Author: akhanduri@helm360.com
#Created At: 22 May 2018
#========================================================================================================================

Feature: Assign Roles from Security and verify permissions
Description: This Feature file contains the actions related to the different role assignment 
and relation of Professions from Security
#========================================================================================================================	  
Background: User logged in with valid credentials
		Given Verify user is at Login Page
		When Enter username as "Admin_User"
	  And Enter password as "Admin_Passowrd"
 	  And Click on login button
    Then Verify user should Redirected to "MatterMgtDash" page
    

@test1
  Scenario: Print title, url
    When Verify user is at Login Page
    Then Enter username as "Username"
	  When Enter password as "Password"
	#  And Click on login button
	  
	  
	  @test1
  Scenario: Dummy Test skip
    When Verify user is at Login Page
    Then Enter username as "Username"
	  When Enter password as "Password"
	  
	  	  @test12
  Scenario: Dummy Test fail
    #	When I verify test
    When Verify user is at Login Page
    Then Enter username as "Username"
	  When Enter password as "Password"
	  #And Click on login1 button
	  

	  		
	 	 		
	 	
	 	
	 	


@test8
Scenario: After creating estimate
	When Open the created estimate
		And Click on 'Add New Record' button
	 	Then Verify New line should be added in the grid
	 	When Enter some activity name in the 'Activity' column
	 	Then Verify Activity field should be mandatory and cannot be left blank
	 	When Click on Resources dropdown in Resources column
#	 	Then Verify Only active resources should get populated in resources dropdown 
	 	And Select any Title from DATABASE for First row 
	 	#title
	 	When Enter some hours in 'Hours' Column
	 	And Enter start date and End Date
	 	Then Verify If End date is prior to Start date an error message should popup
	 	And Verify Std. Rate field should be non editable 
	 	And Verify Std. Rates should be fetched correctly from DB as per title and start date : 1st row
	 	 # title rate
	 	And Verify propose Rate field is editable
	 	And Verify Proposed Rate field calculated correctly : 1st row

@test9
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
	  		|Currency				|EUR							|	
#Added 10 Second of wait to see the filled values	  		 	 		
	 	When Click on "CreateNewEstimateBtn"
	 	And I verify the Fee Plan page
	 	Then Verify Estimat created in DB
	 #	@next
	 #	Scenario: next
#========================================================================================================================
#[Test_Case_003] Test Fee Plan page through New Estimate (Type3)
#========================================================================================================================
		And Click on 'Add New Record' button
	 	Then Verify New line should be added in the grid
	 	And Enter below values for first row
	 		|Activity		|TimeActivity		|
	 		|Resource		|Administration	|
	 		|Hours			|8							|
	 		|Start Date	|13/06/2018			|
	 		|End Date		|14/06/2018			|
	 		
	 	
	 	
	 	When Enter some activity name in the 'Activity' column
	 	Then Verify Activity field should be mandatory and cannot be left blank
	 	When Click on Resources dropdown in Resources column
#	 	Then Verify Only active resources should get populated in resources dropdown 
	 	And Select below Title for First row
	 			|Title		|Administration	|
	 	When Enter some hours in 'Hours' Column
	 	And Enter start date and End Date
	 	Then Verify If End date is prior to Start date an error message should popup
	 	And Verify Std. Rate field should be non editable 
	 	And Verify Std. Rates should be fetched correctly from DB as per title and start date : 1st row
	 	And Verify propose Rate field is editable
	 	And Verify Proposed Rate field calculated correctly : 1st row
	 	And Verify Hourly Budget field : 1st row
	 	
#========================================================================================================================
#  Add one more Record in Fee Plan	 	
#========================================================================================================================
	 	When Click on 'Add New Record' button again
	 	Then Verify on more New line should be added in the grid
	 	When Enter some activity name in the 'Activity' column of second row
	 	When Click on Resources dropdown in Resources column of second row
	 	And Select any Resource Name from DATABASE in second row
	 	When Enter some hours in 'Hours' Column of second row
	 	And Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for second row
	 	And Verify Hourly Budget column field for second row
	 	
#========================================================================================================================
#  Add one more Record for different phase and task
#========================================================================================================================	 	
		When If available add new record for other phase and task otherwise add on the same
		Then Verify new line added for third record
		When Enter Activity for third row
		And Click on resource dropdown in Activity column of third row
		
#========================================================================================================================
#  Select Resource with Timekeeper rate
#========================================================================================================================	
		And Select any Resource Name from DATABASE in third row
		When Enter "1" hour in 'Hours' Column of third row
		Then Verify Std. Rates should be fetched correctly from DB as per the resource and its start date for third row
		And Verify Hourly Budget column field with "1" Proposed rate and "1" hour for third row
		
#========================================================================================================================
#  Validate propose rate with 0
#========================================================================================================================		
		And Enter the Propose rate "0" for third row
		Then Verify Hourly budget field with "0" hour and "100" proposed rate
		
#========================================================================================================================
#  Validate currency symbol
#========================================================================================================================		
		And Verify Currency symbol "EUR" at UI
		
#========================================================================================================================
#  Verify values on slider bar
#========================================================================================================================
		When Click on Save Estimate button
 	  Then Verify Estimate has been saved 	
	 	When Open Slider from bottom
	 	Then Verify Hourly Fee Budget, Total Hourly Fee Budget and Proposed Budget values
	 	And  Hide the Slider from bottom

