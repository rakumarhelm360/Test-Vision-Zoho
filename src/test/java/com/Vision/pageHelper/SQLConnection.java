package com.Vision.pageHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;

import com.Vision.util.DriverHelper;
import com.Vision.util.PropertyReader;

public class SQLConnection extends DriverHelper				
{							
	protected PropertyReader propertyReader = new PropertyReader();		
	
	//---------------------------------Initialize DB------------------------------------------------
		String DBSERVER = propertyReader.readApplicationFile("DBSERVER");
		String DBNAME = propertyReader.readApplicationFile("DBNAME");
		String DBUSER = propertyReader.readApplicationFile("DBUSER");
		String DBPASSWORD = propertyReader.readApplicationFile("DBPASSWORD");
		
	//---------------------------------------------------------------------------------------------------------
	public SQLConnection(WebDriver webDriver) 			
	{			
		super(webDriver);					
	}			
	
	public ResultSet getDatabaseConnection(String sqlQuery) throws ClassNotFoundException, SQLException {
		//Loading the required JDBC Driver class
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				//Creating a connection to the database
				Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+DBSERVER+";databaseName="+DBNAME+"",DBUSER,DBPASSWORD);
				//Executing SQL query and fetching the result
				//return conn.createStatement();
				Statement st = conn.createStatement();
				return st.executeQuery(sqlQuery);
	}
}