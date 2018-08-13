#==========================================================================================================================
#Author: akhanduri@helm360.com
#Created At: 22 May 2018


#==========================================================================================================================
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
#Tags to executes
#========================================================================================================================
	  #@SanitySuite @Estimate @EstimateTabs
	   @AFA
  Scenario: Verify and calculated values in AFA Popop
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
     When Expand Task if Budget Type is Matter Details
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
     Then Verify Hourly Budget Section and AFA Budget Section
     Then Verify components of the Hourly Budget and AFA Budget
      | Hourly Budget        | AFA Budget           | 
      | Budget Margin Slider | Budget Margin Slider | 
      | Budget Profit        | Budget Profit        | 
     Then Verify Budget Margin Percentage at Hourly Budget
     Then Verify Budget Profit value at Hourly Budget section
     Then Verify Hrouly Budget Table with below checks
      | Hrouly Budget Margin slider         | 
      | Hourly Budget Margin Percentage     | 
      | Phase is non editable               | 
      | Total Hrouly Budget is Non Editable | 
      | AFA Budget field is Editable        | 
      | Save icon                           | 
      | AFA Budget Margin Slider            | 
     Then Verify Hourly Budget Margin for each available task
     Then Verify Total Hrouly Budget for each available task
     When Change the AFA Budget for a task
     Then AFA Percentage value and AFA Budget Margin percentage values reflected for the same task
      And Total AFA Budget and Percentage reflected for the same task
     When Change the AFA Percentage value for a task
     Then AFA Budget and AFA Budget Margin percentage values is reflected for the same task
      And Total AFA Budget and Percentage reflected for the same task
     When Save Phases one by one
     Then Verify save confirmation
     When Close the popup
      And click on Ok from confirmation
     When Open Slider from bottom
     Then Verify AFA Fee Budget, Total AFA Fee Budget, Budget Profit, Budget Margin
      And  Hide the Slider from bottom
  #========================================================================================================================
  #Tags to executes
  #========================================================================================================================
  #@SanitySuite @Estimate @EstimateTabs 
  @AFA
  Scenario: Delete Phase and Verify the AFA FEE Plan
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
     When Expand Task if Budget Type is Matter Details
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
     When Close the popup
     Then Popup should open with Yes and No buttons
     When click on No button
     Then verify AFA popup not closed
     When Close the popup
     Then Popup should open with Yes and No buttons
     When click on Yes button
     Then Verify popup is closed
      And Click on ellipsis icon
      And Select "Modify phases and tasks" from the menu
     Then Verify "Modify phases and tasks" popup is open
     When click close icon for a phase
     Then Click on Ok button
     When click on Save Template button
     Then verify "Modify phase and tasks" popup closed
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
     Then Verify phase is removed from the table
  
  #========================================================================================================================
  #Tags to executes
  #========================================================================================================================
  #@SanitySuite @Estimate @EstimateTabs 
  @AFA
  Scenario: Add New Phase and Verify the AFA FEE Plan
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
     When Expand Task if Budget Type is Matter Details
      And Click on ellipsis icon     
      And Select "Modify phases and tasks" from the menu
     Then Verify "Modify phases and tasks" popup is open
     When click on "Add Phases and Tasks" button
     Then check any unchecked phase
      And click on Next button
      And click on Next button
     When click on Save Template button
     Then verify "Modify phase and tasks" popup closed
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
      And Verify newly task is added
     When Close the popup
     Then Popup should open with Yes and No buttons
     When click on Yes button
     Then Verify popup is closed
     When expand the newly added task
      And Fill the row with below details
      | Activity   | TimeActivity | 
      | Resource   | B Summers    | 
      | Hours      | 2            | 
      | Start Date | 2/1/2008     | 
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
      And Verify Hourly Budget and Budget Margin for newly added phase
     Then Verify the Hourly Budget Margin Percentage with slider at the top of the Hourly budget section
      And Verify the AFA Budget Margin with  slider  at the top of the AFA budget section       
     When Close the popup
      And click on Ok from confirmation
  #========================================================================================================================
  #Tags to executes
  #========================================================================================================================      
  #@SanitySuite @Estimate @EstimateTabs 
  @AFA
  Scenario: Edit the task details and Verify the AFA FEE Plan
     When Select "My Estimates" from LeftPan
      And Search for previously created Estimate
      And Click on view for the estimate
     When Expand Task if Budget Type is Matter Details
     When Add new activity with below details
      | Activity   | TimeActivity | 
      | Resource   | B Summers    | 
      | Hours      | 2            | 
      | Start Date | 2/1/2008     | 
      And Click on ellipsis icon
      And Select AFA from the menu
     Then Verify AFA popup is open
     Then Verify Hourly Budget and Margin of the same  phase which is updated
     Then Verify AFA budget and Margin of the same  phase which is updated.
     Then Verify the Hourly Budget Margin % with slider at the top of the Hourly budget section
     Then Verify the AFA Budget Margin with  slider  at the top of the AFA budget section