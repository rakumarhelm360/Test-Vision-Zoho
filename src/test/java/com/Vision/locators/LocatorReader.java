package com.Vision.locators;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.Vision.util.PropertyReader;



public class LocatorReader 
{

	private Document doc;
	private  PropertyReader propObj;  
	
	public LocatorReader(String xmlName)
	{
		SAXReader reader = new SAXReader();
		try 
		{
			propObj = new PropertyReader();
			String localPath = propObj.getFilePath();
			localPath = localPath+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator+"com"+File.separator+"Vision"+File.separator+"locators"+File.separator;	
			doc = reader.read(localPath+xmlName);
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getLocator(String locator)
	{
		return doc.selectSingleNode("//" + locator.replace('.', '/')).getText();
		
	}
}
