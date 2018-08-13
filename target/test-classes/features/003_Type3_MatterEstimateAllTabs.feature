#==========================================================================================================================
#Author: akhanduri@helm360.com
#Created At: 22 May 2018
#==========================================================================================================================
Feature: Type 3 Estimate Related test Scenarios
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
  
#============================================================================================================================
#[Test_Case_002] Test Create Estimate (Type 1/Type3), 
#============================================================================================================================
#Tags to executes
#----------------------------------------------------------------------------------------------------------------------------
  @SanitySuite @Estimate @CreateNewEstimate
  Scenario: Create New Estimate with Type Three (Matter Details)
  
     When Select "Create an Estimate" from LeftPan
      And Click on "FreshEstimateOpt"
      And Click on "Next"
     Then Verify user should Redirected to "CreateEstimatePage" page
     When Enter Estimate Name starting with below text
      | Estimate Name | AutomationEstimate | 
  
      And Select Type of Budget as below
      | Type of Budget | Matter Detail | 
  
      And Select Matter Type and Rate Type  		
      And Type and select Client Name, Practice Group, Template and Currency
      | Client Name    | SONY CORPORATION | 
      | Practice Group | Corporate Tax    | 
      | Currency       | U. S. Dollars    | 
 	  		 	 		
#     When Click on "CreateNewEstimateBtn"
			When Click multiple times on "CreateNewEstimateBtn"
      And I verify the Fee Plan page
     Then Verify Estimat created in DB
     Then Verify duplicate Estimate not created in DB
     Then Close the browser
     
#============================================================================================================================
#[Test_Case_004] Test Fee Plan page through Existing Estimate (Type3) 
#============================================================================================================================
#----------------------------------------------------------------------------------------------------------------------------
#Tags to executes
#----------------------------------------------------------------------------------------------------------------------------
  @SanitySuite @Estimate @EstimateTabs
  Scenario: Fee Plan,Cost Plan, Info, Third Party and What If Tabs
     Then Verify the created estimate at dashboard
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
  #		When Open the created estimate
     When Expand the Task if Estimate is Type 3
      And Click on Add new record button for Fee Plan
     Then Verify the Activity field is required
     When Enter activity
     Then Validate start date and end date fields
      And Delete the Fee record
     When Add "First Fee" record with below values
      | Activity   | TimeActivity | 
      | Resource   | B Summers    | 
      | Hours      | 2            | 
      | Start Date | 2/1/2008     | 
     Then verify Standard rate and hourly budget for "1st" Fee Record
     And Click on "Submit" button
     Then Verify Start Date saving correctly in Database for "1st Fee" record
     When Add "Second Fee" record with below values
      | Activity   | TimeActivity | 
      | Resource   | B Summers    | 
      | Hours      | 2            | 
      | Start Date | 30/12/2006   | 
     When Delete the "2nd" Fee Record
     Then Verify "2nd" Fee Record is deleted from database
     When Add "Second Fee" record with below values
      | Activity   | TimeActivity | 
      | Resource   | B Summers    | 
      | Hours      | 2            | 
      | Start Date | 30/12/2006   |
     Then verify Standard rate and hourly budget for "2nd" Fee Record
      And Click on "Submit" button
     Then Verify Start Date saving correctly in Database for "2nd Fee" record
  
#  @demo
#  Scenario: Fee Plan,Cost Plan, Info, Third Party and What If Tabs
#----------------------------------------------------------------------------------------------------------------------------
# Cost Plan Page
#----------------------------------------------------------------------------------------------------------------------------	
     When Switch to "Cost Plan" Tab
      And Add "First Cost" Cost record with below values
      | Activity  | CostActivity      | 
      | Resource  | B Summers         | 
      | Cost Code | AIRFARE [AIRFARE] | 
      | Unit      | 10                | 
      And Verify Rate For Cost Code of "1st" cost record
      And Verify Amount of "1st" cost plan record
     When Add "Second Cost" Cost record with below values
      | Activity  | CostActivity      | 
      | Resource  | B Summers         | 
      | Cost Code | AIRFARE [AIRFARE] | 
      | Unit      | 10                | 
     Then Verify Rate For Cost Code of "2nd" cost record
      And Verify Amount of "2nd" cost plan record
      And Click on "Submit" button
  
#----------------------------------------------------------------------------------------------------------------------------
# Info Page
#----------------------------------------------------------------------------------------------------------------------------		
     When Switch to "Info" Tab
      And Verify Estimate and Client Info
      | Client Name          | 
      | Estimate Name        | 
      | Currency             | 
      | Practice Group       | 
      | Estimate Type        | 
      | Estimage Status      | 
      | Estimate Opened Date | 
  
      And Add "1st Firm" Firm Record with below values
      | Type           | Supervising      | 
      | Contact Person | Alexander Harris | 
  
     Then Verify Email and phone validation for "1st Client" with below details
      | Contact Person | AutomationClient      | 
      | Invalid Email  | akhanduri             | 
      | Invalid Phone  | 123456789             | 
      | Valid Email    | akhanduri@helm360.com | 
  
      And Delete the "1st Client" row
     When "1st Client" Client Details as below
      | Contact Person | AutomationClient      | 
      | Email          | akhanduri@helm360.com | 
      | Phone          | 1234567890            | 
      And Click on "Submit" button		
#----------------------------------------------------------------------------------------------------------------------------
# Third Party Page
#----------------------------------------------------------------------------------------------------------------------------				
     When Switch to "Third Party" Tab
     Then Validate "First TP" Dates with below entries
      | Exp Category | Accident & Injury Experts | 
      | Exp Witness  | Animals                   | 
      | Cat. Expert  | Animal Stress             | 
      | Hours        | 5                         | 
      | Start Date   | current Date              | 
      | End Date     | Previous Date             | 
     Then Delete the Third Party record
  
      And Add "First TP" Third Party Record with below values
      | Exp Category | Accident & Injury Experts | 
      | Exp Witness  | Animals                   | 
      | Cat. Expert  | Animal Stress             | 
      | Hours        | 5                         | 
      | Start Date   | current Date              | 
      | End Date     | Current Date              | 
      And Verify Rate and Third Party Cost calculation for "First TP" row
      And Click on "Submit" button
#----------------------------------------------------------------------------------------------------------------------------
# Budget to Actual Page
#----------------------------------------------------------------------------------------------------------------------------
#		When Switch to "Budget to Actual" Tab
#		And Verify Fee and Cost Details in below columns
#		|Budget|Actual|Remaining|%Remaining|
#		And Verify Unassigned "Fees Actual" and "Cost Actual"
#----------------------------------------------------------------------------------------------------------------------------
# What If Page
#----------------------------------------------------------------------------------------------------------------------------		
  		When Switch to "What If" Tab
  		And click on copy and edit button on "Current Budget"
  		Then Verify new What if added
  		When Change resource, hours, Avg rate and Budget as below mentionedin new what if
  				|Resource	|Amanda King|
  				|Hours		|10					|
  				|Avg Rate	|200				|
  		And Save the What If		
  		Then Verify that Total Budget and Budget Margin percentage updated correctly
  		And Click on "Submit" button
  
#----------------------------------------------------------------------------------------------------------------------------
# Calculate Hourly Fee Budget, Proposed Budget, Budget Profit and Budget Margin
#----------------------------------------------------------------------------------------------------------------------------		  
     Then Calculate Hourly Fee Budget
      And Calculate Proposed Budget
     When Open Slider from bottom
     Then Match Calculated Hourly Fee Budget and Proposed Budget with Database
     Then Calculate Budget Profit and match with Database
     Then Calculate Budget Margin and match with Database
      And  Hide the Slider from bottom
  
     Then Close the browser
  
#========================================================================================================================
#Tags to executes
#========================================================================================================================
  @SanitySuite @Estimate @MarginColor
  Scenario: Check Budget Margin slider Color from Set Margin Threshold and validate in Estimate 
     When Select "Set Margins" from LeftPan  
     Then Verify Set Margin Threshold grid is opened
      And Get the Margin color values of Practice Group and if selected practice group is not mentioned then get it for Firm
  
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
     When Open Slider from bottom
     Then Validate the slider bar color
      And  Hide the Slider from bottom
     Then Close the browser
  
