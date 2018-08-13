package com.Vision.pageHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.Vision.locators.LocatorReader;
import com.Vision.util.DriverHelper;
import com.Vision.util.PropertyReader;

public class SecurityHelper extends DriverHelper{
	private LocatorReader locatorReader;			
	protected PropertyReader propertyReader = new PropertyReader();

	public SecurityHelper(WebDriver webdriver) {
		super(webdriver);
		locatorReader = new LocatorReader("Vision.xml");
	}
	public void verifyRoleCreated(String RoleName) {
		waitForWorkAroundTime(2000);
		waitForElementPresent("//Span[contains(text(), '"+RoleName+"')]",3);
		Assert.assertTrue(isElementPresent("//Span[contains(text(), '"+RoleName+"')]"), "Element Locator : //Span[contains(text(), '"+RoleName+"')] Not found");
	}
	public void selectNewlyCreatedRole(String roleName) {
		waitForElementPresent("//*[@id='lstRolesToPerms']//span[text() = '"+roleName+"']",2);
		clickOn("//*[@id='lstRolesToPerms']//span[text() = '"+roleName+"']");
	}
	public void checkPermission(List<String> roleToPermission, String PermissionSearchField) {
		String PermissionSearchLocator = locatorReader.getLocator(PermissionSearchField);
		for (int i = 0; i < roleToPermission.size(); i++) {
			if(roleToPermission.get(i).contains("Ability to access reports for all Matters - Menu")){
				sendKeys(PermissionSearchLocator, roleToPermission.get(i));
				clickOn("//input[@value = '"+roleToPermission.get(i)+" "+"']");
			}else
			{
				sendKeys(PermissionSearchLocator, roleToPermission.get(i));
				clickOn("//input[@value = '"+roleToPermission.get(i)+"']");
			}
		}

	}
	public void selectNewlyCreatedRoleFromRoleToProf(String roleName) {
		waitForElementPresent("//*[@id='lstRolesToTK']//span[text() = '"+roleName+"']",5);
		clickOn("//*[@id='lstRolesToTK']//span[text() = '"+roleName+"']");
	}
	public void checkGivenTimekeeper(List<String> roleToPermission, String RoleName, String TimekeeperSearchFieldR2P) {
		String ErrorToast = locatorReader.getLocator("ErrorToast");
		String ErrorMessageToast = locatorReader.getLocator("ErrorMessageToast");
		String SaveButton = locatorReader.getLocator("SaveRoleToProfessionaButton");
		String RoleToProfSearchField = locatorReader.getLocator("RoleToProfSearchField");
		String TimekeeperSearchFieldR2PLocator = locatorReader.getLocator("TimekeeperSearchFieldR2P");

		for (int i = 0; i < roleToPermission.size(); i++) {
			sendKeys(TimekeeperSearchFieldR2PLocator, roleToPermission.get(i));
			clickOn("//input[@value = '"+roleToPermission.get(i)+"']"); 
			waitForWorkAroundTime(1000);

			if(isElementPresent(ErrorToast)==true)
			{
				String ErrorMessage = getText(ErrorMessageToast);
				String Role = StringUtils.substringBetween(ErrorMessage, "is already assigned to Role: ", "!");
				sendKeys(RoleToProfSearchField, Role);
				waitForElementPresent("//*[@id='lstRolesToTK']//span[text() = '"+Role+"']",5);
				clickOn("//*[@id='lstRolesToTK']//span[text() = '"+Role+"']");
				waitForWorkAroundTime(500);
				sendKeys(TimekeeperSearchFieldR2PLocator, roleToPermission.get(i));
				clickOn("//input[@value = '"+roleToPermission.get(i)+"']");
				clickOn(SaveButton);
				waitForWorkAroundTime(3000);//5000
				sendKeys(RoleToProfSearchField, RoleName);
				waitForElementPresent("//*[@id='lstRolesToTK']//span[text() = '"+RoleName+"']",5);
				clickOn("//*[@id='lstRolesToTK']//span[text() = '"+RoleName+"']");
				sendKeys(TimekeeperSearchFieldR2PLocator, roleToPermission.get(i));
				clickOn("//input[@value = '"+roleToPermission.get(i)+"']");
				clickOn(SaveButton);
				waitForWorkAroundTime(3000);//5000
			}				
		}
		waitForWorkAroundTime(1000);
	}
	public void verifyPermissionsAtRoleTab(List<String> roleToPermission,
			String roleName) {
		waitForWorkAroundTime(1000);
		doubleClickOn("//div[@id='treeRoles']//span[contains(text(), '"+roleName+"')]");
		for (int i = 0; i < roleToPermission.size(); i++) {
			Assert.assertTrue(isElementPresent("//li[@id='treeRoles_tv_active']//span[contains(text(), '"+roleToPermission.get(i)+"')]"));
		}

	}
	public void selectCurrentUserAtUserSection(String User) {
		String UserName = propertyReader.readApplicationFile(User);
		waitForElementPresent("//*[@id='lstUsersProf']//span[text() = '"+UserName+"']",2);

		String UserToProfessionalSearchField = locatorReader.getLocator("UserToProfessionalSearchField");
		sendKeys(UserToProfessionalSearchField,UserName);
		waitForWorkAroundTime(500);
		clickOn("//*[@id='lstUsersProf']//span[text() = '"+UserName+"']");
		waitForWorkAroundTime(1000);//3000
	}
	public void removeAllProfessionalsFromUserToProfessionals() {
		String locator = locatorReader.getLocator("ProfessionalTotal");
		int liSize = getXpathCount(locator);
		for(int i=1;i<=liSize;i++) {
			if(isAttributePresent("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']/li["+i+"]/input", "checked")==true) {
				clickOn("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']/li["+i+"]/input");
			}
		}		

	}
	public void checkTimekeeper(List<String> userToProfessional) {
		String searchLocator = locatorReader.getLocator("ProfSearchFieldU2P");
		for (int i = 0; i < userToProfessional.size(); i++) {
			sendKeys(searchLocator, userToProfessional.get(i));
			if(isAttributePresent("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']//input[@value = '"+userToProfessional.get(i)+"']", "checked")!=true) {
				clickOn("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']//input[@value = '"+userToProfessional.get(i)+"']");
			}
		}
	}
	public List<String> getAllCheckedTkprForAvailableUser(String User) {
		String searchLocator = locatorReader.getLocator("ProfSearchFieldU2P");
		List<String> AllTkprList = new ArrayList<String>();
		clearField(searchLocator);
		waitForWorkAroundTime(200);
		String locator = locatorReader.getLocator("ProfessionalTotal");
		int liSize = getXpathCount(locator);

		clickOn("//*[@id='lstUsersProf']//span[text() = '"+propertyReader.readApplicationFile(User)+"']");
		clickOn("//*[@id='lstUsersProf']//span[text() = '"+propertyReader.readApplicationFile(User)+"']");
		for(int i=1;i<=liSize;i++) {

			if(isAttributePresent("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']/li["+i+"]/input", "checked")==true) {
				AllTkprList.add(getAttributeValue("//div[@id='dvProfUsers']/table/tbody/tr/td[3]//ul[@id='lstProf']/li["+i+"]/input", "value"));

			}			
		}

		return AllTkprList;
	}
	public void selectNewlyCreatedRoleFromWidgetToRole(String RoleName) {
		waitForElementPresent("//*[@id='lstRolesToWidgets']//span[text() = '"+RoleName+"']",2);
		clickOn("//*[@id='lstRolesToWidgets']//span[text() = '"+RoleName+"']");
	}
	//The below method is to regarding the issue and when set multiple widget one by one at the same time not setting correctly
	public void removeOtherCheckBoxesForWidget(String RoleName, List<String> widgetToRole){
		String WidgetLocator = locatorReader.getLocator("WidgetSize");
		String SaveButton = locatorReader.getLocator("SaveWidgetToRoleButton");
		String searchLocator = locatorReader.getLocator("WidgetSearchFieldW2R");
		int widgetSize = getXpathCount(WidgetLocator);
		waitForElementPresent("//*[@id='lstRolesToWidgets']//span[text() = '"+RoleName+"']",2);
		clickOn("//*[@id='lstRolesToWidgets']//span[text() = '"+RoleName+"']");
		waitForWorkAroundTime(500);
		clickOn("//*[@id='lstRolesToWidgets']//span[text() = '"+RoleName+"']");
		clearField(searchLocator);
		for(int j=0;j<widgetToRole.size();j++){
			for(int i=1;i<=widgetSize;i++) {		
				if(isAttributePresent("//ul[@id='lstWidgets']//li["+i+"]/input", "checked") == true){
					clickOn("//ul[@id='lstWidgets']//li["+i+"]/input");
				}			
			}
		}
		clickOn(SaveButton);
	}
	public void checkWidget(List<String> widgetToRole, String SearchWidgetField) {
		String SearchWidgetLocator = locatorReader.getLocator(SearchWidgetField);
		for (int i = 0; i < widgetToRole.size(); i++) {
			sendKeys(SearchWidgetLocator, widgetToRole.get(i));
			clickOn("//*[@id='lstWidgets']//input[@value = '"+widgetToRole.get(i)+"']");
		}
	}

	public void matchProfessionalList(List<String> userToProfessional) {
		boolean match;
		String locator = locatorReader.getLocator("GetProfItemSize");
		int itemsize = getXpathCount(locator);
		for(int i=1; i<=itemsize; i++) {
			match = false;
			String path = "//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li["+i+"]";
			String user = getText(path);
			for(int j=0;j<userToProfessional.size();j++) {
				if(user.contains(userToProfessional.get(j))) {				
					match = true;
				}
			}
			Assert.assertTrue(match);
		}
	}
	public void selectNewlyCreatedTimekeeper(String timekeeper, String UserType) {
		String OKFromWarning = locatorReader.getLocator("OKFromWorning");
		mouseOver("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+timekeeper+"']");
		waitForWorkAroundTime(2000);		
		if(isAttributePresent("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+timekeeper+"']/i","ng-click")==true && UserType=="Admin") {

			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+timekeeper+"']/i");
			waitForWorkAroundTime(1000);
			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+timekeeper+"']/i");
		}

		else{
			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+timekeeper+"']");	
		}
		waitForWorkAroundTime(1000);
		clickOn(OKFromWarning);
	}
	public void verifyMenuItemsForTimekeeper(String[] menuItems, String[] menuItemNotPresent) {
		for(int i=0; i<menuItems.length;i++) {
			Assert.assertTrue(isElementPresent("//span[contains(text(), '"+menuItems[i]+"')]"));
		}
		for(int j=0;j<menuItemNotPresent.length;j++) {
			Assert.assertFalse(isElementPresent("//span[contains(text(), '"+menuItemNotPresent[j]+"')]"));
		}

	}
	public void verifyWidgetsForNewTimekeeper(List<String> widgetToRole) {
		//String locator = locatorReader.getLocator("DashboardCount");
		for(int i=0;i<widgetToRole.size();i++) {
			if(widgetToRole.get(i).contains("My open estimates")){
				Assert.assertTrue(isElementPresent("//div[@id='dashboard']//span[text() = 'My Estimates']"), "Element"+widgetToRole.get(i)+"is Not Found");
			}
			else if(widgetToRole.get(i).contains("Matters I am managing")){
				Assert.assertTrue(isElementPresent("//div[@id='dashboard']//span[text() = 'Matters I am Managing']"), "Element"+widgetToRole.get(i)+"is Not Found");	
			}
			else if(widgetToRole.get(i).contains("My time")) {
				Assert.assertTrue(isElementPresent("//div[@id='dashboard']//span[text() = 'My time']"), "Element"+widgetToRole.get(i)+"is Not Found");
			}
			else {
				Assert.assertTrue(isElementPresent("//div[@id='dashboard']//span[text() = '"+widgetToRole.get(i)+"']"), "Element"+widgetToRole.get(i)+"is Not Found");
			}

		}

	}

	public void verifyAllMenuOptions(String[] allMenuItems) {
		for(int i=0;i<allMenuItems.length;i++) {
			Assert.assertTrue(isElementPresent("//span[contains(text(), '"+allMenuItems[i]+"')]"));
		}
	}

	public void verifyAllWidget(String[] allWidget) {

		for(int i=0;i<allWidget.length;i++) {
			Assert.assertTrue(isElementPresent("//span[text() = '"+allWidget[i]+"']"));
		}

	} 
	public void verifyDefaultAndSelectOtherUser(String AdminUser, String SimpleUser){
		String DashboardLocator = locatorReader.getLocator("MatterMgtDash");
		String OKFromWarning = locatorReader.getLocator("OKFromWorning");
		String LogOff = locatorReader.getLocator("LogOff");
		mouseOver("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+AdminUser+"']");
		waitForWorkAroundTime(2000);
		String AttributeValue = getAttributeValue("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+AdminUser+"']/i","ng-if");
		if(AttributeValue.equals("isDefault(professional)")){
			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+SimpleUser+"']");
		}
		else{
			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+AdminUser+"']/i");
			waitForWorkAroundTime(1000);
			clickOn("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+SimpleUser+"']");
		}
		waitForWorkAroundTime(1000);
		clickOn(OKFromWarning);
		waitForElementPresent(DashboardLocator,5);
		mouseOver(LogOff);waitForWorkAroundTime(1000);
	}
	public void verifyDashboardLoadedForDefaultAdminUser(String AdminUser, String[] AllMenuItems){
		String ProfDropdown = locatorReader.getLocator("ProfDropdown");
		String LogOff = locatorReader.getLocator("LogOff");
		String BreadCrumb = locatorReader.getLocator("BreadCrumb");
		String RemoveMenu = locatorReader.getLocator("RemoveMenu");
		String DashboardLocator = locatorReader.getLocator("MatterMgtDash");
		String AttributeValue = getAttributeValue("//*[@id='DashboardController']//ul[@class='dropdown-menu profDdListMenu noselect']//li[text() = '"+AdminUser+"']/i","ng-if");
		if(AttributeValue.equals("isDefault(professional)")){
			Assert.assertTrue(true,"Admin not Set to default");
		}
		clickOn(ProfDropdown);
		waitForWorkAroundTime(700);
		clickOn(BreadCrumb);
		verifyAllMenuOptions(AllMenuItems);
		waitForWorkAroundTime(700);
		clickOn(RemoveMenu);
		waitForElementPresent(DashboardLocator,5);
		mouseOver(LogOff);waitForWorkAroundTime(1000);
	}
}
