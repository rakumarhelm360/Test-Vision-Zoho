#========================================================================================================================
#Author: akhanduri@helm360.com
#Created At: 22 May 2018
#========================================================================================================================
Feature: Assign Roles from Security and verify permissions
Description: This Feature file contains the actions related to the different role assignment 
and relation of Professions from Security
#========================================================================================================================	  
Background: User logged in with valid credentials
		Given Open Browser and navigate to application URL
		Given Verify user is at Login Page
		When Enter username as "Admin_User"
	  And Enter password as "Admin_Passowrd"
 	  And Click on login button
    Then Verify user should Redirected to "MatterMgtDash" page
    
#========================================================================================================================
#[Test_Case_001] Test User/Professional relation through Security
#========================================================================================================================
#========================================================================================================================
#Tags to executes
#========================================================================================================================
@SanitySuite @SecurityTest1
		Scenario: Test User/Professional relation through Security
		When Select "Security" from LeftPan
		Then Verify user should Redirected to "SecurityDetail" page
		When Click on Plus icon from Role tab
		Then Verify Add Role popup window should open up
		When Add Role Name and Description in  the text fields in popup window and click on Submit
		Then Verify New role created should reflect in the roles list
		
#------------------------------------------------------------------------------------------------------------------------
#Add one more Role to verify with tow roles
#------------------------------------------------------------------------------------------------------------------------
		When Click on Plus icon from Role tab
		Then Verify Add Role popup window should open up
		When Add Another Role Name and Description in the text fields in popup window and click on Submit
		Then Verify Second role also should reflect in the role tab
		
#------------------------------------------------------------------------------------------------------------------------	
#Add permission for both roles created in previous steps
#------------------------------------------------------------------------------------------------------------------------
		When Go to Role to Permission tab, search and select first role created above in roles section
		And In Permissions section select below permissions checkbox and click on 'Save' button at the bottom
				|My Matters - (Managing Matters)- Menu|Ability to View My Matter Tasks|Ability to View all Matters and Estimates - Menu|	
		Then Verify Permissions should get saved for the role selected
		When In Role to Permission tab, search and select second role created from role section	
		And In Permissions section select below permissions checkbox for second role and click on 'Save' button at the bottom
				|Ability to View the software version - Menu|Ability to access reports for all Matters - Menu |
		Then Verify Permissions should get saved for the role selected	
		
#------------------------------------------------------------------------------------------------------------------------
#Verify Permissions at the Role tab by expanding them
#------------------------------------------------------------------------------------------------------------------------
		And Verify Permissions are saved under Roles section and can be visible by expanding
		
#------------------------------------------------------------------------------------------------------------------------
#Add Roles to Professionals, add multiple roles to multiple professionals
#------------------------------------------------------------------------------------------------------------------------		
	 	When Go to Role to Professionals tab, search and select first role created above in Roles section	 	 	
		And In the Timekeepers section select below active timekeepers checkbox for First Role and click on 'Save'
				|APPLE NEWTON|
		Then Verify Professionals should get mapped to the role selected
		When In Role to Professionals tab, search and select second role created above in Roles section
		And In the Timekeepers section select below active timekeepers checkbox for Second Role and click on 'Save'
				|ARCHIBALD MANNERS|			
		Then Verify Professionals should get mapped to the role selected
		
#------------------------------------------------------------------------------------------------------------------------
#Add professionals for multiple users, (here we have two user admin@lpm.com and rakumar@lpm.com) later verify both
#------------------------------------------------------------------------------------------------------------------------	
		When Go to User to Profesional tab, search and select user who is used to login the application from users section
		And In Professionals section select below timekeeper and professional and click on 'Save'
				|Vision Admin|APPLE NEWTON|ARCHIBALD MANNERS|
		Then Verify Prosessionals should get mapped with the User
		When In User to Professional tab search and select other user then admin
		And In Professionals section select below timekeeper, professional for other user and click on 'Save'
				|Vision Admin|APPLE NEWTON|ARCHIBALD MANNERS|
		Then Verify Prosessionals should get mapped with the User
		
#------------------------------------------------------------------------------------------------------------------------
#Add Different Widget for different roles (note: multiple selection for multiple roles have issues, have bypass the script)
#------------------------------------------------------------------------------------------------------------------------	
		When Go to Widget  to Role tab and select first role created in above steps in roles section
		And In Widget section select below widgets checkbox for first role and click on 'Save'
						|My open estimates|Matters I am managing|
		Then Verify Widgets should get mapped to the role selected
		When In Widget to Role tab search and select second role created above in Role section
		And In Widget section select below widgets checkbox for second role and click on 'Save'
						|My open estimates|
		Then Verify Widgets should get mapped to the role selected
		
#------------------------------------------------------------------------------------------------------------------------
#Relogin with same user [Now all roles and permissions are set, now start verify all of them]
#------------------------------------------------------------------------------------------------------------------------		
		And Select "Dashboard" from LeftPan
		Then Verify user should Redirected to "MatterMgtDash" page		
		And Logout from the application
		When Login with "Admin_User" and "Admin_Passowrd"
		Then Verify user should Redirected to "MatterMgtDash" page		
		When Click on red icon on top right corner of the dashboard for professionals list
		
#------------------------------------------------------------------------------------------------------------------------
#Verify Professionals, Widgets, and Menu Items for currently Logged In user (i.e. admin@lpm.com)
#=========================================================================================================================		
		Then Verify Professionals list should popup with all the timekeepers which are mapped to the user in security
		When Select the timekeeper which is assigned to first role
		Then Verify the access to the processes checked for that timekeeper and also the widgets on the dashboard
		And Only those processes related to first role, which are mapped to selected professional should reflect in the menu options
		And Only those widget which are mapped for first professional should reflect for widgets
		When Select the timekeeper which is assigned to second role
		Then Verify Only those processes related to second role, which are mapped to selected professional should reflect in the menu options
		And Only those widget which are mapped for second professional should relect for widgets	
		When Select professional as Vision Admin
		Then Verify All the options should be visible in menu options to Vision Admin since the timekeeper belongs to admin role
		And Verify dashboard should also display all the widgets		
		And Logout from the application
		
#------------------------------------------------------------------------------------------------------------------------
#Verify Dashboard and professionals for the second user which is updated at User to Professionals tab (i.e. rakumar@lpm.com)
#------------------------------------------------------------------------------------------------------------------------
		When Login with "Other_User" and "Other_Password"
		Then Verify user should Redirected to "MatterMgtDash" page
		When Click on red icon on top right corner of the dashboard for professionals list
 		Then Verify professional list for the other user
		And Logout from the application
		
#------------------------------------------------------------------------------------------------------------------------
#Verify which timekeeper is set to default, After Login Dashboard, Widget and Menu items loaded for that timekeeper
#------------------------------------------------------------------------------------------------------------------------
		When Login with "Admin_User" and "Admin_Passowrd"
		Then Verify user should Redirected to "MatterMgtDash" page		
		When Click on red icon on top right corner of the dashboard for professionals list		
		Then Verify current professional set to default, and switch to other user
		And Logout from the application
		When Login with "Admin_User" and "Admin_Passowrd"
		Then Verify user should Redirected to "MatterMgtDash" page	
		When Click on red icon on top right corner of the dashboard for professionals list
		Then Verify dashboard loaded for default professional
		And Logout from the application
		Then Close the browser