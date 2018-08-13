package com.Vision.step_definations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Vision.pageHelper.CommonHelper;
import com.Vision.pageHelper.EstimateHelper;
import com.Vision.pageHelper.SecurityHelper;
import com.Vision.util.DriverTestCase;
import com.Vision.util.PropertyReader;

import cucumber.api.DataTable;
import cucumber.api.java.ContinueNextStepsFor;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SecurityStepDefinition
{
	EstimateHelper estimateHelper;
	CommonHelper commonHelper;
	SecurityHelper securityHelper;
	DriverTestCase driverTestCase;
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	String RoleName_1;
	String RoleName_2;
	List<String> roleToPermission_1;// Save permissions for the first roles available from feature file
	List<String> roleToPermission_2;// Save permissions for the second roles available from feature file
	List<String> roleToProfessional_1;
	List<String> roleToProfessional_2;
	List<String> userToProfessional_AdminUser;
	List<String> userToProfessional_SecondUser; //save professionals or timekeeper for the other user
	List<String> AllTkprList; //All timekeper list for the admin user
	List<String> AllTkprList_SecondUser;// all selected timekeepr list for other user from feature file
	List<String> widgetToRole_1;
	List<String> widgetToRole_2;
	protected PropertyReader propertyReader = new PropertyReader();
	public SecurityStepDefinition()
	{
		driverTestCase = new DriverTestCase();
		estimateHelper = new EstimateHelper(driverTestCase.getWebDriver());
		commonHelper = new CommonHelper(driverTestCase.getWebDriver());
		securityHelper = new SecurityHelper(driverTestCase.getWebDriver());
	}
	@When("^Click on Plus icon from Role tab$")
	public void click_on_icon_from_Role_tab(){
		commonHelper.waitForElementPresent("FirmAdminElement", 5);
		commonHelper.click("AddIcon");	
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Add Role popup window should open up$")
	public void verify_Add_Role_popup_window_should_open_up() {
		commonHelper.waitForElementPresent("AddRolePopup", 5);
		commonHelper.verifyElement("AddRolePopup");
	}
	@When("^Add Role Name and Description in  the text fields in popup window and click on Submit$")
	public void add_Role_Name_and_Description_in_the_text_fields_in_popup_window_and_click_on_Submit() {
		this.RoleName_1 = "AutoMationRole_1_"+timeStamp;
		commonHelper.type("RoleNameAtPopup", RoleName_1);
		commonHelper.type("DescriptionAtPopup", "Description"+timeStamp);
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("SaveButtonAtPopup");
		commonHelper.waitForElementPresent("RandomRowToVerify", 5);
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify New role created should reflect in the roles list$")
	public void verify_New_role_created_should_reflect_in_the_roles_list() {
		securityHelper.verifyRoleCreated(RoleName_1);
	}
	@When("^Add Another Role Name and Description in the text fields in popup window and click on Submit$")
	public void add_another_role_name_and_description_in_the_text_field_in_popup_window_and_click_on_submit() {
		this.RoleName_2 = "AutoMationRole_2_"+timeStamp;
		commonHelper.type("RoleNameAtPopup", RoleName_2);
		commonHelper.type("DescriptionAtPopup", "Description"+timeStamp);
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("SaveButtonAtPopup");
		commonHelper.waitForElementPresent("RandomRowToVerify", 5);
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Second role also should reflect in the role tab$")
	public void verify_second_role_also_should_relect_in_the_role_tab() {
		securityHelper.verifyRoleCreated(RoleName_2);
	}
	@When("^Go to Role to Permission tab, search and select first role created above in roles section$")
	public void go_to_Role_to_Permission_tab_search_and_select_first_role_created_above_in_roles_section() {
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.click("RoleToPermissionTab");
		commonHelper.type("RoleToPerSearchField", RoleName_1);
		securityHelper.selectNewlyCreatedRole(RoleName_1);
	}
	@When("^In Permissions section select below permissions checkbox and click on 'Save' button at the bottom$")
	public void in_Permissions_section_select_below_permissions_checkbox_and_click_on_Save_button_at_the_bottom(DataTable permission) {
		List<List<String>> data = permission.raw();		
		roleToPermission_1 = new ArrayList<String>();
		for(int i = 0; i<data.get(0).size();i++){
			this.roleToPermission_1.add(data.get(0).get(i));
			System.out.println("The emelent is : "+roleToPermission_1.get(i));	
		}
		securityHelper.checkPermission(roleToPermission_1, "PermissionSearchField");
		commonHelper.click("SaveRoleToPermissionButton");
		commonHelper.waitForWorkAroundTime(2000);
	}
	@When("^In Role to Permission tab, search and select second role created from role section$")
	public void in_role_to_permission_tab_search_and_select_second_role_created_from_role_section() {
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.type("RoleToPerSearchField", RoleName_2);
		securityHelper.selectNewlyCreatedRole(RoleName_2);
	}
	@When("^In Permissions section select below permissions checkbox for second role and click on 'Save' button at the bottom$")
	public void in_Permissions_section_select_below_permissions_checkbox_for_second_tole_and_click_on_Save_button_at_the_bottom(DataTable permission) {
		List<List<String>> data = permission.raw();			
		roleToPermission_2 = new ArrayList<String>();	
		for(int i = 0; i<data.get(0).size();i++){
			this.roleToPermission_2.add(data.get(0).get(i));
			System.out.println("The emelent is : "+roleToPermission_2.get(i));
		}
		securityHelper.checkPermission(roleToPermission_2, "PermissionSearchField");
		commonHelper.click("SaveRoleToPermissionButton");
		commonHelper.waitForWorkAroundTime(2000);
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Permissions should get saved for the role selected$")
	public void verify_Permissions_should_get_saved_for_the_role_selected() {
		commonHelper.waitForElementPresent("SaveRoleToPermissionConfirmation", 5);
		commonHelper.verifyElement("SaveRoleToPermissionConfirmation");
	}
	@And("^Verify Permissions are saved under Roles section and can be visible by expanding$")
	public void verify_permissions_are_saved_under_roles_section_and_can_be_visible_by_expanding() {
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.click("RoleTab");
		securityHelper.verifyPermissionsAtRoleTab(roleToPermission_1, RoleName_1);
		securityHelper.verifyPermissionsAtRoleTab(roleToPermission_2, RoleName_2);		
	}			
	@When("^Go to Role to Professionals tab, search and select first role created above in Roles section$")
	public void go_to_Role_to_Professionals_tab_search_and_select_first_role_created_above_in_Roles_section() {
		commonHelper.click("RoleToProfessionalTab");
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.type("RoleToProfSearchField", RoleName_1);
		securityHelper.selectNewlyCreatedRoleFromRoleToProf(RoleName_1);
	}
	@When("^In the Timekeepers section select below active timekeepers checkbox for First Role and click on 'Save'$")
	public void in_the_Timekeepers_section_select_below_active_timekeepers_checkbox_for_first_role_and_click_on_Save(DataTable tkpr) {
		List<List<String>> data = tkpr.raw();	
		roleToProfessional_1 = new ArrayList<String>();
		for(int i = 0; i<data.get(0).size();i++){
			this.roleToProfessional_1.add(data.get(0).get(i));
			System.out.println("The emelent is : "+roleToProfessional_1.get(i));
		}
		securityHelper.checkGivenTimekeeper(roleToProfessional_1,RoleName_1, "TimekeeperSearchFieldR2P");
		commonHelper.click("SaveRoleToProfessionaButton");
		commonHelper.waitForWorkAroundTime(2000);
	}

	@When("^In Role to Professionals tab, search and select second role created above in Roles section$")
	public void in_role_to_professional_tab_search_and_select_second_role_created_above_in_role_section() {
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.type("RoleToProfSearchField", RoleName_2);
		securityHelper.selectNewlyCreatedRoleFromRoleToProf(RoleName_2);	
	}	
	@When("^In the Timekeepers section select below active timekeepers checkbox for Second Role and click on 'Save'$")
	public void in_the_Timekeepers_section_select_below_active_timekeepers_checkbox_for_second_role_and_click_on_Save(DataTable tkpr) {
		List<List<String>> data = tkpr.raw();		
		roleToProfessional_2 = new ArrayList<String>();	
		for(int i = 0; i<data.get(0).size();i++){
			this.roleToProfessional_2.add(data.get(0).get(i));
			System.out.println("The emelent is : "+roleToProfessional_2.get(i));		
		}
		securityHelper.checkGivenTimekeeper(roleToProfessional_2,RoleName_2, "TimekeeperSearchFieldR2P");
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("SaveRoleToProfessionaButton");
	}	
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Professionals should get mapped to the role selected$")
	public void verify_Professionals_should_get_mapped_to_the_role_selected() {
		commonHelper.waitForElementPresent("SaveRoleToProfessionalConfirmation", 5);
		commonHelper.verifyElement("SaveRoleToProfessionalConfirmation");
	}
	@When("^Go to User to Profesional tab, search and select user who is used to login the application from users section$")
	public void go_to_User_to_Profesional_tab_and_select_user_who_is_used_to_login_the_application_from_users_section() {
		commonHelper.click("UserToProfessionalTab");
		commonHelper.waitForWorkAroundTime(2000);
		securityHelper.selectCurrentUserAtUserSection("Admin_User");
		commonHelper.waitForWorkAroundTime(2000);
	}
	@When("^In Professionals section select below timekeeper and professional and click on 'Save'$")
	public void in_Professionals_section_select_below_timekeeper_and_professional_and_click_on_Save(DataTable tkpr) {
		List<List<String>> data = tkpr.raw();	
		userToProfessional_AdminUser = new ArrayList<String>();		
		for(int i = 0; i<data.get(0).size();i++){
			this.userToProfessional_AdminUser.add(data.get(0).get(i));
			System.out.println("The emelent is : "+userToProfessional_AdminUser.get(i));		
		}
		commonHelper.waitForWorkAroundTime(500);
		securityHelper.checkTimekeeper(userToProfessional_AdminUser);
		commonHelper.waitForWorkAroundTime(200);
		commonHelper.click("SaveUserToProfessionalButton");
		commonHelper.waitForWorkAroundTime(700);
		this.AllTkprList = securityHelper.getAllCheckedTkprForAvailableUser("Admin_User");
		System.out.println("All timekeeper list"+this.AllTkprList);
		commonHelper.click("SaveUserToProfessionalButton");
	}
	@When("^In User to Professional tab search and select other user then admin$")
	public void in_user_to_professional_tab_search_and_select_other_user_then_admin() {
		commonHelper.waitForWorkAroundTime(1000);
		securityHelper.selectCurrentUserAtUserSection("Other_User");
		commonHelper.waitForWorkAroundTime(1200);
	}
	@And("^In Professionals section select below timekeeper, professional for other user and click on 'Save'$")
	public void in_professional_section_select_below_timekeeper_professional_for_other_user_and_click_on_save(DataTable tkpr){
		List<List<String>> data = tkpr.raw();
		userToProfessional_SecondUser = new ArrayList<String>();
		for(int i = 0; i<data.get(0).size();i++){
			this.userToProfessional_SecondUser.add(data.get(0).get(i));		
		}
		commonHelper.waitForWorkAroundTime(1000);
		securityHelper.checkTimekeeper(userToProfessional_SecondUser);
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("SaveUserToProfessionalButton");
		commonHelper.waitForWorkAroundTime(1000);
		this.AllTkprList_SecondUser = securityHelper.getAllCheckedTkprForAvailableUser("Other_User");
		commonHelper.click("SaveUserToProfessionalButton");
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Prosessionals should get mapped with the User$")
	public void verify_Prosessionals_should_get_mapped_with_the_User() {
		commonHelper.waitForElementPresent("SaveUserToProfessionalConfirmation", 5);
		commonHelper.verifyElement("SaveUserToProfessionalConfirmation");
	}
	@When("^Go to Widget  to Role tab and select first role created in above steps in roles section$")
	public void go_to_Widget_to_Role_tab_and_select_new_role_created_in_above_steps_in_roles_section() {
		commonHelper.click("WidgetToRoleTab");
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.type("RoleToWidgetSearchField", RoleName_1);
		securityHelper.selectNewlyCreatedRoleFromWidgetToRole(RoleName_1);
	}
	@When("^In Widget section select below widgets checkbox for first role and click on 'Save'$")
	public void in_Widget_section_select_below_widgets_checkbox_for_first_role_and_click_on_Save(DataTable widget) {
		List<List<String>> data = widget.raw();
		widgetToRole_1 = new ArrayList<String>();
		for(int i = 0; i<data.get(0).size();i++){
			this.widgetToRole_1.add(data.get(0).get(i));		
		}
		securityHelper.checkWidget(widgetToRole_1, "WidgetSearchFieldW2R");
		commonHelper.waitForWorkAroundTime(3000);
		commonHelper.click("SaveWidgetToRoleButton");
		commonHelper.waitForWorkAroundTime(1000);
		commonHelper.click("SaveWidgetToRoleButton");
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Widgets should get mapped to the role selected$")
	public void verify_Widgets_should_get_mapped_to_the_role_selected() {
		commonHelper.waitForElementPresent("SaveWidgetToRoleConfirmation", 5);
		commonHelper.verifyElement("SaveWidgetToRoleConfirmation");
	}
	@When("^In Widget to Role tab search and select second role created above in Role section$")
	public void in_widget_to_role_tab_search_and_select_second_role_created_above_in_role_section() {
		commonHelper.waitForWorkAroundTime(700);
		commonHelper.type("RoleToWidgetSearchField", RoleName_2);
		securityHelper.selectNewlyCreatedRoleFromWidgetToRole(RoleName_2);			
	}
	@When("^In Widget section select below widgets checkbox for second role and click on 'Save'$")
	public void in_Widget_section_select_below_widgets_checkbox_for_second_role_and_click_on_Save(DataTable widget) {
		List<List<String>> data = widget.raw();	
		widgetToRole_2 = new ArrayList<String>();	
		for(int i = 0; i<data.get(0).size();i++){
			this.widgetToRole_2.add(data.get(0).get(i));
			System.out.println("The emelent is : "+widgetToRole_2.get(i));		
		}
		System.out.println("widget in second dashboard : "+widgetToRole_2);
		securityHelper.checkWidget(widgetToRole_2, "WidgetSearchFieldW2R");
		commonHelper.waitForWorkAroundTime(2000);
		commonHelper.click("SaveWidgetToRoleButton");
	}	
	@When("^Login with \"(.*?)\" and \"(.*?)\"$")
	public void login_using_given_user(String UserName, String UserPassword){
		//Relogin------------------------------------------------------------------
		String Username = propertyReader.readApplicationFile(UserName);
		String Password = propertyReader.readApplicationFile(UserPassword);
		commonHelper.type("Username", Username);
		commonHelper.waitForWorkAroundTime(100);
		commonHelper.type("Password", Password);
		commonHelper.click("Login");
		commonHelper.waitForElementPresent("ProfDropdown", 2);
	}	
	@When("^Click on red icon on top right corner of the dashboard for professionals list$")
	public void click_on_red_icon_on_top_right_corner_of_the_dashboard_for_professionals_list() {

		commonHelper.click("ProfDropdown");
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}
	@Then("^Verify Professionals list should popup with all the timekeepers which are mapped to the user in security$")
	public void verify_Professionals_list_should_popup_with_all_the_timekeepers_which_are_mapped_to_the_user_in_security() {
		securityHelper.matchProfessionalList(AllTkprList); 
	}
	@When("^Select the timekeeper which is assigned to first role$")
	public void select_the_timekeeper_which_is_assigned_to_first_role() {
		securityHelper.selectNewlyCreatedTimekeeper(userToProfessional_AdminUser.get(1), "User");
	}

	@Then("^Verify the access to the processes checked for that timekeeper and also the widgets on the dashboard$")
	public void verify_the_access_to_the_processes_checked_for_that_timekeeper_and_also_the_widgets_on_the_dashboard() {
		// Write code here that turns the phrase above into concrete actions
	}
	@Then("^Only those processes related to first role, which are mapped to selected professional should reflect in the menu options$")
	public void only_those_processes__related_first_role_which_are_mapped_to_selected_professional_should_reflect_in_the_menu_options() {
		String[] menuItems = {"My Estimates", "My Matters", "My Tasks", "View all Matter/Estimate"};
		String[] menuItemNotPresent = {"Create an Estimate", "Reports", "Matter Templates", "Historical Matters", "Set Margins",
				"Laverages", "Security", "About"};
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		estimateHelper.verifyElementPresent("BreadCrumb", 2);
		commonHelper.click("BreadCrumb");
		securityHelper.verifyMenuItemsForTimekeeper(menuItems,menuItemNotPresent);
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Only those widget which are mapped for first professional should reflect for widgets$")
	public void only_those_widget_which_are_mapped_for__first_professional_should_reflect_for_widgets() {
		commonHelper.click("RemoveMenu");
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		System.out.println("WidgetToRole _1 value is "+widgetToRole_1);
		securityHelper.verifyWidgetsForNewTimekeeper(widgetToRole_1); //changed
		commonHelper.waitForWorkAroundTime(8000);
	}
	@When("^Select the timekeeper which is assigned to second role$")
	public void select_the_timekeeper_which_is_assigned_to_second_role() {
		commonHelper.click("ProfDropdown");
		commonHelper.waitForWorkAroundTime(600);
		securityHelper.selectNewlyCreatedTimekeeper(userToProfessional_AdminUser.get(2), "User");
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify Only those processes related to second role, which are mapped to selected professional should reflect in the menu options$")
	public void verify_only_those_processes__related_second_role_which_are_mapped_to_selected_professional_should_reflect_in_the_menu_options() {
		String[] menuItems = {"My Estimates", "My Matters", "My Tasks", "Reports", "About"};
		String[] menuItemNotPresent = {"View all Matter/Estimate", "Create an Estimate", "Matter Templates", "Historical Matters", "Set Margins",
				"Laverages", "Security"};
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		estimateHelper.verifyElementPresent("BreadCrumb", 2);
		commonHelper.click("BreadCrumb");
		securityHelper.verifyMenuItemsForTimekeeper(menuItems,menuItemNotPresent);
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Only those widget which are mapped for second professional should relect for widgets$")
	public void only_those_widget_which_are_mapped_for__second_professional_should_relect_for_widgets() {
		commonHelper.click("RemoveMenu");
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		System.out.println("WidgetToRole _1 value is "+widgetToRole_2);
		securityHelper.verifyWidgetsForNewTimekeeper(widgetToRole_2); //changed
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}	
	@When("^Select professional as Vision Admin$")
	public void select_professional_as_Vision_Admin() {
		commonHelper.click("ProfDropdown");
		securityHelper.selectNewlyCreatedTimekeeper(userToProfessional_AdminUser.get(0), "Admin");//Check if user type is admin then make timekeeper default
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify All the options should be visible in menu options to Vision Admin since the timekeeper belongs to admin role$")
	public void verify_All_the_options_should_be_visible_in_menu_options_to_Vision_Admin_since_the_timekeeper_belongs_to_admin_role() {
		String[] allMenuItems = {"Dashboard", "Create an Estimate", "My Estimates", "My Matters",
				"My Tasks", "View all Matter/Estimate", "Reports", "Matter Templates", "Historical Matters",
				"Set Margins", "Leverages", "Security","About"};
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		estimateHelper.verifyElementPresent("BreadCrumb", 5);
		commonHelper.click("BreadCrumb");
		commonHelper.waitForWorkAroundTime(2000);
		securityHelper.verifyAllMenuOptions(allMenuItems);
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify dashboard should also display all the widgets$")
	public void verify_dashboard_should_also_display_all_the_widgets() {
		String[] allWidget = {"My Time", "My Estimates", "Unassigned Timecards For My Matters",
				"Top 10 Matters Actual more than Budget", "My Tasks", "Active Matters"};
		commonHelper.click("RemoveMenu");
		commonHelper.waitForElementPresent("MatterMgtDash", 2);
		securityHelper.verifyAllWidget(allWidget);
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify professional list for the other user$")
	public void verify_professional_list_for_the_other_user() {
		securityHelper.matchProfessionalList(AllTkprList_SecondUser);
		commonHelper.waitForWorkAroundTime(8000);//hard wait
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify current professional set to default, and switch to other user$")
	public void verify_current_professional_set_to_default_and_switch_to_other_user() {
		securityHelper.verifyDefaultAndSelectOtherUser(userToProfessional_AdminUser.get(0), userToProfessional_AdminUser.get(1));
		commonHelper.verifyElementPresent("MatterMgtDash", 5);
	}
	@ContinueNextStepsFor(AssertionError.class)
	@Then("^Verify dashboard loaded for default professional$")
	public void verify_dashboard_loaded_for_default_professional() {
		String[] allMenuItems = {"Dashboard", "Create an Estimate", "My Estimates", "My Matters",
				"My Tasks", "View all Matter/Estimate", "Reports", "Matter Templates", "Historical Matters",
				"Set Margins", "Leverages", "Security","About"};
		securityHelper.verifyDashboardLoadedForDefaultAdminUser(userToProfessional_AdminUser.get(0), allMenuItems);
	}
}
