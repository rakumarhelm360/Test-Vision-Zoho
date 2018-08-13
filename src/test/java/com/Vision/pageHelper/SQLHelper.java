package com.Vision.pageHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;

import com.Vision.util.DriverHelper;
import com.Vision.util.DriverTestCase;
import com.Vision.util.PropertyReader;

public class SQLHelper extends DriverHelper				
{							
	protected PropertyReader propertyReader = new PropertyReader();		
	SQLConnection sqlConnection;
	EstimateHelper estimateHelper;
	CommonHelper commonHelper;
	DriverTestCase driverTestCase;

	//----------------------------------------------------Constructor------------------------------------------------------
	public SQLHelper(WebDriver webDriver) 			
	{			
		super(webDriver);	
		driverTestCase = new DriverTestCase();
		estimateHelper = new EstimateHelper(driverTestCase.getWebDriver());
		commonHelper = new CommonHelper(driverTestCase.getWebDriver());
		sqlConnection= new SQLConnection(driverTestCase.getWebDriver());
	}			
	//---------------------------------------------------------------------------------------------------------------------

	//-----------------------------------------get client name ------------------------------------------------------------
	public String getClientNameFromDB() throws SQLException, ClassNotFoundException {
		String clientName = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 CompName from Clients where IsNew = '0' order by UpdatedBy desc"
				);
		while(rs.next()) {
			clientName = rs.getString("CompName");
		}
		return clientName;
	}
	
	//-----------------------------------------get practice group ----------------------------------------------------------
	public String getPracticeGroupFromDB() throws ClassNotFoundException, SQLException {
		String practiceGroup = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 * from Templates "+
						"inner join PracticeGroups on Templates.PracticeGroupId =PracticeGroups.Id "+
						"order by Template desc"
				);
		while(rs.next()) {
			practiceGroup = rs.getString("PracticeGroup");
		}
		return practiceGroup;
	}
	
	//------------------------------------------get template name ------------------------------------------------------------
	public String getTemplateFromDB(String practiceGroup) throws ClassNotFoundException, SQLException {
		String template = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 * from Templates "+
						"inner join PracticeGroups on Templates.PracticeGroupId =PracticeGroups.Id where PracticeGroup = '"+practiceGroup+"'"+
						"order by Template desc"
				);
		while(rs.next()) {
			template = rs.getString("Template");
		}
		return template;
	}
	//------------------------------------------get matter type ---------------------------------------------------------------
	public String getMatterTypeFromDB() throws ClassNotFoundException, SQLException {
		String matterType = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 MatterType from MatterTypes order by UpdatedDAteTime desc"
				);
		while(rs.next()) {
			matterType = rs.getString("MatterType");
		}
		return matterType;
	}
	//------------------------------------verify estiamte created successfully ------------------------------------------------
	public boolean isEstimateCreatedInDB(String estimateName) throws ClassNotFoundException, SQLException {
		Boolean flag=true;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select * from matterestimates where estimate = '"+estimateName+"'"
				);
		if(!rs.next())
		{
			flag=false;			
		}
		return flag;
	}
	public boolean isDuplicatedEstimateNotCreatedInDB(String estimateName) throws ClassNotFoundException, SQLException {
		Boolean flag=true;
		int count = 0;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select count(Estimate) as Estimate from matterestimates where Estimate = '"+estimateName+"'"
				);
		while(rs.next())
		{
			count  = Integer.parseInt(String.valueOf(rs.getString("Estimate")));			
		}
		if(count >1){
			flag = false;
		}
		return flag;
	}
	//---------------------------------------------get title name --------------------------------------------------------------
	public String getTitleFromDB() throws ClassNotFoundException, SQLException { //titlerates.title
		String profTitle = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 ProfType from TitleRates order by ProfType asc"
				);	
		while(rs.next()){
			profTitle = rs.getString("ProfType");
		}
		return profTitle;
	}
	//---------------------------------------get all resource which are active --------------------------------------------------
	public String[] getActiveResourcesFromDB() throws ClassNotFoundException, SQLException {
		int rowCount=0;
		String rowcountString;
		String[] activeResources;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select count(distinct(ProfName)) as ARC  from Professionals where IsActive = 'Y'"
				);
		while(rs.next())
		{
			rowcountString = rs.getString("ARC");
			rowCount = Integer.parseInt(rowcountString);
		}
		ResultSet rs2 = sqlConnection.getDatabaseConnection(
				"select distinct ProfName from Professionals where IsActive = 'Y' order by ProfName asc"
				);
		int i=0;
		activeResources = new String[rowCount];
		while(rs2.next()){
			activeResources[i]=rs2.getString("ProfName");
			i++;
		}
		return activeResources;	
	}
	//--------------------------------------get all inactive resources -----------------------------------------------------------
	public String[] getInActiveResourcesFromDB() throws ClassNotFoundException, SQLException {
		int rowCount=0;
		String rowcountString;
		String[] inActiveResources;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select count(distinct(ProfName)) as INARC from Professionals where IsActive = 'N'"
				);
		while(rs.next())
		{
			rowcountString = rs.getString("INARC");
			rowCount = Integer.parseInt(rowcountString);
		}
		ResultSet rs2 = sqlConnection.getDatabaseConnection(
				"select distinct ProfName from Professionals where IsActive = 'N' order by ProfName asc"
				);
		int i=0;
		inActiveResources = new String[rowCount];
		while(rs2.next()){
			inActiveResources[i]=rs2.getString("ProfName");
			i++;
		}
		return inActiveResources;
	}
	//--------------------------------------get standard reate for title and resources --------------------------------------------
	public double getStandardRateFromDB(String Estimate, String ResourceName, String Currency, String RoleType, String StartDate) throws ClassNotFoundException, SQLException {
		double Rate = 0; // Initialize the Rate
		double stdTitleRateInUSD = 0; //For title rate in TitleRates table
		double fromCurrencyRate = 0; //Rate of from currency which is USD
		double toCurrencyRate = 0; // Rate of to currency which will be in parameter
		double totalTitleRate = 0; // total rate TotlaRate*(toCurrencyRate/fromCurrencyRate)
		double titleRate = 0;
		String sourceCurrency = "";
		if(RoleType.contains("Title")) {//If need to get Rate for Title like Administration

			ResultSet rs1 = sqlConnection.getDatabaseConnection(
					"select top 1 Rate from TitleRates where ProfType = '"+ResourceName+"' and Currency = '"+Currency+"' and EffectiveDate < '"+StartDate+"'"
					);
			if(rs1.next()){
				titleRate = Double.parseDouble(rs1.getString("Rate"));		
			}
			else {
				ResultSet rs2_1 = sqlConnection.getDatabaseConnection(
						"select top 1 Currency from TitleRates where ProfType = '"+ResourceName+"' and EffectiveDate < '"+StartDate+"' order by EffectiveDate desc"
						);
				if(rs2_1.next()){
					sourceCurrency = rs2_1.getString("Currency");
				}
				ResultSet rs2 = sqlConnection.getDatabaseConnection(
						"select top 1 Rate from TitleRates where ProfType = '"+ResourceName+"' and EffectiveDate < '"+StartDate+"' -- this is title rate now convert"
						);
				while(rs2.next())
					stdTitleRateInUSD = Double.parseDouble(rs2.getString("Rate"));
				ResultSet rs3 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+sourceCurrency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs3.next())
					fromCurrencyRate = Double.parseDouble(rs3.getString("Rate"));
				ResultSet rs4 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+Currency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs4.next())
					toCurrencyRate = Double.parseDouble(rs4.getString("Rate"));
				totalTitleRate = stdTitleRateInUSD*(toCurrencyRate/fromCurrencyRate);
				titleRate = Math.round(totalTitleRate*100.0)/100.0;
			}				
			Rate = titleRate;
		}
		else if(RoleType.contains("Resource")) {
			double resourceRate = 0;
			double totalResourceRate = 0;
			String RatesId = null;
			String TkprId = null;
			ResultSet rs = sqlConnection.getDatabaseConnection(
					"select top 1 RatesId from MatterEstimates where Estimate = '"+Estimate+"'"
					);
			while (rs.next()){
				RatesId = rs.getString("RatesId");
				System.out.println("RatesID"+RatesId);
			}
			ResultSet rs1 = sqlConnection.getDatabaseConnection(
					"select top 1 Id from Professionals where ProfName = '"+ResourceName+"'"
					);
			while(rs1.next()) {
				TkprId = rs1.getString("Id");
				System.out.println("TkprId"+TkprId);
			}
			ResultSet rs2 = sqlConnection.getDatabaseConnection(
					"select * from timekeeperRates CR where TimeKeepersId = '"+TkprId+"' AND RateId = '"+RatesId+"'  AND currency = '"+Currency+"' AND Convert(DATE, '"+StartDate+"') BETWEEN CR.EffectiveStartDate AND ISNULL(CR.EffectiveEndDate,'"+StartDate+"')"
					//	"select top 1 Rate from TimeKeeperRates where TimeKeepersId = '"+TkprId+"' and RateId = '"+RatesId+"' and Currency = '"+Currency+"' and '"+StartDate+"' >= EffectiveStartDate"	
					);
			System.out.println("select * from timekeeperRates CR where TimeKeepersId = '"+TkprId+"' AND RateId = '"+RatesId+"'  AND currency = '"+Currency+"' AND Convert(DATE, '"+StartDate+"') BETWEEN CR.EffectiveStartDate AND ISNULL(CR.EffectiveEndDate,'"+StartDate+"')");
			if(rs2.next()){
				resourceRate = Double.parseDouble(rs2.getString("Rate"));	
				System.out.println("resourceRate"+resourceRate);
			}
			else {
				double TkprRate = 0;
				//double fromCurrencyRate = 0.00;
				//double toCurrencyRate = 0.00;
				ResultSet rs3_1 = sqlConnection.getDatabaseConnection(
						"select Currency from TimeKeeperRates where TimeKeepersId = '"+TkprId+"' and RateId = '"+RatesId+"' and '"+StartDate+"' >= EffectiveStartDate order by EffectiveStartDate desc"
						);
				if(rs3_1.next()){
					sourceCurrency = rs3_1.getString("Currency");
				}
				ResultSet rs3 = sqlConnection.getDatabaseConnection(
						"select Rate from TimeKeeperRates where TimeKeepersId = '"+TkprId+"' and RateId = '"+RatesId+"' and Currency = '"+sourceCurrency+"' and '"+StartDate+"' >= EffectiveStartDate"
						);
				while(rs3.next()) {
					TkprRate = Double.parseDouble(rs3.getString("Rate"));
					System.out.println("TkprRate"+TkprRate);
				}
				ResultSet rs4 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+sourceCurrency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs4.next()) {
					fromCurrencyRate = Double.parseDouble(rs4.getString("Rate"));
					System.out.println("fromCurrencyRate"+fromCurrencyRate);
				}
				ResultSet rs5 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+Currency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs5.next()){
					toCurrencyRate = Double.parseDouble(rs5.getString("Rate"));
					System.out.println("toCurrencyRate"+toCurrencyRate);
				}
				totalResourceRate = TkprRate*(toCurrencyRate/fromCurrencyRate);
				System.out.println("totalResourceRate"+totalResourceRate);
				resourceRate = Math.round(totalResourceRate*100.0)/100.0;
				System.out.println("resourceRate"+resourceRate);
			}
			Rate = resourceRate;
		}
		return Rate;
	}
	//------------------------------------------------get resources which are active -----------------------------------
	public String getResourceFromDB() throws ClassNotFoundException, SQLException {
		String profTitle = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 ProfName from Professionals where IsActive = 'Y' and IsTimekeeper = 'Y' order by ProfName asc"
				);	
		while(rs.next()){
			profTitle = rs.getString("ProfName");
		}
		return profTitle;
	}
	public String getResourceWithEURFromDB() throws ClassNotFoundException, SQLException {
		String profTitle = null;
		String TkprId = null;
		ResultSet rs1 = sqlConnection.getDatabaseConnection(
				"select top 1 TimeKeepersId from timekeeperrates where Currency = 'EUR'"
				);	
		while(rs1.next()){
			TkprId = rs1.getString("TimeKeepersId");
		}
		ResultSet rs2 = sqlConnection.getDatabaseConnection(
				"select top 1 ProfName from professionals where Id = '"+TkprId+"'"
				);
		while(rs2.next()){
			profTitle = rs2.getString("ProfName");
		}
		return profTitle;
	}
	//---------------------------------------get cost rate using cost code -----------------------------------------------
	public String getCostRateFromDB(String costCode) throws ClassNotFoundException, SQLException {
		String CostRate = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 CostRate from Cost where CostDescription = '"+costCode+"'"		
				);
		while(rs.next()){
			CostRate = rs.getString("CostRate");
		}
		return CostRate;
	}
	//---------------------------------------- get estimated created time using estimate name -----------------------------
	public String getEstimateCreatedTime(String estimateName) throws ClassNotFoundException, SQLException {
		String time = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select convert(nvarchar(10),CreatedDateTime,103) as CreatedDateTime from MatterEstimates where Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			time = rs.getString("CreatedDateTime");
		}
		return time;
	}
	//---------------------------------------get third party rate using category expert ------------------------------------
	public String getThirdPartyRatefromDB(String catExpert) throws ClassNotFoundException, SQLException {
		String Rate = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select Rate from CategoryExperts where CategoryExpertsDesc = '"+catExpert+"'"
				);
		while(rs.next()){
			Rate = rs.getString("Rate");
		}
		return Rate;
	}
	//---------------------------------------get status of estimate ---------------------------------------------------------
	public String getEstimateStatus(String estimateName) throws ClassNotFoundException, SQLException {
		String status = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select MatterStatus.MatterStatus from MatterStatus inner join MatterEstimates on MatterEstimates.MatterStatus = MatterStatus.Id and MatterEstimates.Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			status = rs.getString("MatterStatus");
		}
		return status;
	}
	//------------------------------------- get estiamte type using estimate name ---------------------------------------------
	public String getEstimateType(String estimateName) throws ClassNotFoundException, SQLException {
		String estimateType = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select MatterTypes.MatterType from MatterTypes inner join MatterEstimates on MatterEstimates.MatterTypesId = MatterTypes.Id and MatterEstimates.Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			estimateType = rs.getString("MatterType");
		}
		return estimateType;
	}
	//------------------------------------- get hourly fee budget for type 3 (matter detail) -----------------------------------
	public String getHourlyFeeBudgetType3(String estimateName) throws ClassNotFoundException, SQLException {
		String hourlyfeebudget = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select Sum(HourlyBudget) as HourlyBudget from MatterEstimates ms join EstimateTemplatePhases ETP on ms.Id = ETP.EstimateId"+
						" join EstimatePhaseTasks EPT on ETP.Id = EPT.EstimateTemplatePhasesId"+
						" join EstimateTaskDetails ETD on EPT.Id = ETD.EstimatePhaseTaskMappingsId where Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			hourlyfeebudget = rs.getString("HourlyBudget");
		}
		return hourlyfeebudget;
	}
	//------------------------------------- get hourly fee budget for type 1 (matter budget) -----------------------------------
		public String getHourlyFeeBudgetType1(String estimateName) throws ClassNotFoundException, SQLException {
			String hourlyfeebudget = null;
			ResultSet rs = sqlConnection.getDatabaseConnection(
					"select Sum(HourlyBudget) as HourlyBudget from MatterEstimates ms join EstimateTemplatePhases ETP on ms.ID = ETP.EstimateId"+
					" join EstimatePhaseTasks EPT on ETP.ID = EPT.EstimateTemplatePhasesId"+
					" join EstimateType1Fee ET1F on EPT.ID = ET1F.EstimatePhaseTaskMappingsID where Estimate = '"+estimateName+"'"
					);
			while(rs.next()){
				hourlyfeebudget = rs.getString("HourlyBudget");
			}
			return hourlyfeebudget;
		}
	//-------------------------------------get proposed budget by estimate name -------------------------------------------------
	public String getProposedBudget(String estimateName) throws ClassNotFoundException, SQLException {
		String proposedBudget = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select top 1 Budget from MatterEstimates where Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			proposedBudget = rs.getString("Budget");
		}
		return proposedBudget;
	}
	//------------------------------------- get timekeeper and title cost --------------------------------------------------------
	public double getTkprCostFromDB(String Estimate, String ResourceName, String Currency, String RoleType, String StartDate) throws ClassNotFoundException, SQLException {
		
		double CostRate = 0; // Initialize the Cost
		double stdTitleCostRateInUSD = 0; //For title rate in TitleCostRates table
		double fromCurrencyRate = 0; //Rate of from currency which is USD
		double toCurrencyRate = 0; // Rate of to currency which will be in parameter
		double totalTitleCostRate = 0; // total rate TotlaRate*(toCurrencyRate/fromCurrencyRate)
		double titleCostRate = 0;
		String sourceCurrency = "";
		if(RoleType.contains("Title")) {//If need to get Rate for Title like Administration

			ResultSet rs1 = sqlConnection.getDatabaseConnection(
					"select top 1 CostRate from TitleRates where ProfType = '"+ResourceName+"' and Currency = '"+Currency+"' and EffectiveDate < '"+StartDate+"'"
					);
			if(rs1.next()){
				titleCostRate = Double.parseDouble(rs1.getString("CostRate"));		
			}
			else {
				ResultSet rs2_1 = sqlConnection.getDatabaseConnection(
						"select top 1 Currency from TitleRates where ProfType = '"+ResourceName+"' and EffectiveDate < '"+StartDate+"' order by EffectiveDate desc"
						);
				if(rs2_1.next()){
					sourceCurrency = rs2_1.getString("Currency");
				}
				ResultSet rs2 = sqlConnection.getDatabaseConnection(
						"select top 1 CostRate from TitleRates where ProfType = '"+ResourceName+"' and EffectiveDate < '"+StartDate+"' -- this is title cost rate now convert"
						);
				while(rs2.next())
					stdTitleCostRateInUSD = Double.parseDouble(rs2.getString("CostRate"));
				ResultSet rs3 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+sourceCurrency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs3.next())
					fromCurrencyRate = Double.parseDouble(rs3.getString("Rate"));
				ResultSet rs4 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+Currency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs4.next())
					toCurrencyRate = Double.parseDouble(rs4.getString("Rate"));
				totalTitleCostRate = stdTitleCostRateInUSD*(toCurrencyRate/fromCurrencyRate);
				titleCostRate = Math.round(totalTitleCostRate*100.0)/100.0;
			}				
			CostRate = titleCostRate;
		}
		else if(RoleType.contains("Resource")) {
			double resourceRate = 0;
			double totalResourceCost = 0;
			//String RatesId = null;
			String TkprId = null;
		/*	ResultSet rs = sqlConnection.getDatabaseConnection(
					"select top 1 RatesId from MatterEstimates where Estimate = '"+Estimate+"'"
					);
			while (rs.next()){
				RatesId = rs.getString("RatesId");
				System.out.println("RatesID"+RatesId);
			}*/
			ResultSet rs1 = sqlConnection.getDatabaseConnection(
					"select top 1 Id from Professionals where ProfName = '"+ResourceName+"'"
					);
			while(rs1.next()) {
				TkprId = rs1.getString("Id");
				System.out.println("TkprId"+TkprId);
			}
			ResultSet rs2 = sqlConnection.getDatabaseConnection(
					"select * from timekeeperCosts CR where TimeKeepersId = '"+TkprId+"' AND currency = '"+Currency+"' AND Convert(DATE, '"+StartDate+"') BETWEEN CR.EffectiveStartDate AND ISNULL(CR.EffectiveEndDate,'"+StartDate+"')"
					//	"select top 1 Rate from TimeKeeperRates where TimeKeepersId = '"+TkprId+"' and RateId = '"+RatesId+"' and Currency = '"+Currency+"' and '"+StartDate+"' >= EffectiveStartDate"	
					);
			System.out.println("select * from timekeeperCosts CR where TimeKeepersId = '"+TkprId+"' AND currency = '"+Currency+"' AND Convert(DATE, '"+StartDate+"') BETWEEN CR.EffectiveStartDate AND ISNULL(CR.EffectiveEndDate,'"+StartDate+"')");
			if(rs2.next()){
				resourceRate = Double.parseDouble(rs2.getString("Cost"));	
				System.out.println("resourceRate"+resourceRate);
			}
			else {
				double TkprRate = 0;
				//double fromCurrencyRate = 0.00;
				//double toCurrencyRate = 0.00;
				ResultSet rs3_1 = sqlConnection.getDatabaseConnection(
						"select Currency from timekeeperCosts CR where TimekeepersId='"+TkprId+"' and Convert(Date,'"+StartDate+"') between CR.EffectiveStartDate And ISNULL(CR.EffectiveEndDate,'"+StartDate+"') order by EffectiveStartDate desc"
						);
				if(rs3_1.next()){
					sourceCurrency = rs3_1.getString("Currency");
				}
				ResultSet rs3 = sqlConnection.getDatabaseConnection(
						"select Cost from timekeeperCosts CR where TimeKeepersId = '"+TkprId+"' and Currency = '"+sourceCurrency+"' and Convert(Date,'"+StartDate+"') between CR.EffectiveStartDate And ISNULL(CR.EffectiveEndDate,'"+StartDate+"')"
						);
				while(rs3.next()) {
					TkprRate = Double.parseDouble(rs3.getString("Rate"));
					System.out.println("TkprRate"+TkprRate);
				}
				ResultSet rs4 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+sourceCurrency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs4.next()) {
					fromCurrencyRate = Double.parseDouble(rs4.getString("Rate"));
					System.out.println("fromCurrencyRate"+fromCurrencyRate);
				}
				ResultSet rs5 = sqlConnection.getDatabaseConnection(
						"select Rate from currencyrate where code = '"+Currency+"' and trtype = 'D' and '"+StartDate+"' BETWEEN startdate and EndDate"
						);
				while(rs5.next()){
					toCurrencyRate = Double.parseDouble(rs5.getString("Rate"));
					System.out.println("toCurrencyRate"+toCurrencyRate);
				}
				totalResourceCost = TkprRate*(toCurrencyRate/fromCurrencyRate);
				System.out.println("totalResourceRate"+totalResourceCost);
				resourceRate = Math.round(totalResourceCost*100.0)/100.0;
				System.out.println("resourceRate"+resourceRate);
			}
			CostRate = resourceRate;
		}
		return CostRate;
	}

	public String getActivityFeeStartDate(String estimateName,
			String feeActivity) throws ClassNotFoundException, SQLException {
		String DBFeeDate = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
					"select ms.Estimate,ETD.Activity,ETD.Hours,CONVERT(DATE,ETD.StartDate) as StartDate,ETD.BillingRate,ETD.ProposedRate, ETP.EstimateId, EPT.EstimateTemplatePhasesId, ETD.EstimatePhaseTaskMappingsId from MatterEstimates ms join EstimateTemplatePhases ETP on ms.Id = ETP.EstimateId"+
					" join EstimatePhaseTasks EPT on ETP.Id = EPT.EstimateTemplatePhasesId"+
					" join EstimateTaskDetails ETD on EPT.Id = ETD.EstimatePhaseTaskMappingsId where Estimate = '"+estimateName+"' and Activity = '"+feeActivity+"'"
				);
		while(rs.next()){
			DBFeeDate = rs.getString("StartDate");
		}
		return DBFeeDate;
		
	}
	public String getType1FeeStartDate(String estimateName) throws ClassNotFoundException, SQLException {
		String DBFeeDate = null;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select ms.Estimate, CONVERT(DATE,ET1F.StartDate) as StartDate,ET1F.Hours,ET1F.BillingRate, ET1F.ProposedRate,"+
				" ETP.EstimateId, EPT.EstimateTemplatePhasesId, ET1F.EstimatePhaseTaskMappingsId from MatterEstimates ms join EstimateTemplatePhases ETP on ms.ID = ETP.EstimateId"+
				" join EstimatePhaseTasks EPT on ETP.ID = EPT.EstimateTemplatePhasesId"+
				" join EstimateType1Fee ET1F on EPT.ID = ET1F.EstimatePhaseTaskMappingsID where Estimate = '"+estimateName+"'"
				);
		while(rs.next()){
			DBFeeDate = rs.getString("StartDate");
		}
		return DBFeeDate;
		
	}
	public boolean isActivityDeletedInDB(String estimateName,
			String feeActivity) throws ClassNotFoundException, SQLException {
		Boolean flag=true;
		ResultSet rs = sqlConnection.getDatabaseConnection(
				"select ms.Estimate,ETD.Activity,ETD.Hours,CONVERT(DATE,ETD.StartDate) as StartDate,ETD.BillingRate,ETD.ProposedRate, ETP.EstimateId, EPT.EstimateTemplatePhasesId, ETD.EstimatePhaseTaskMappingsId from MatterEstimates ms join EstimateTemplatePhases ETP on ms.Id = ETP.EstimateId"+
						" join EstimatePhaseTasks EPT on ETP.Id = EPT.EstimateTemplatePhasesId"+
						" join EstimateTaskDetails ETD on EPT.Id = ETD.EstimatePhaseTaskMappingsId where Estimate = '"+estimateName+"' and Activity = '"+feeActivity+"'"
				);
		if(rs.next())
		{
			flag=false;			
		}
		return flag;
	}
	
}