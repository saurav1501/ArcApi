package com.arc.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;




public class BaseClass {
	
	public static XlsReader data;
	public static RequestSpecification reqSpec;
	public static RequestSpecification reqSpecjson;
	public static ResponseSpecification respSpec;
	public static String Token;
    public static String header;
	
	
	@BeforeClass
	public void setBaseUri() {
		
		data = new XlsReader(System.getProperty("user.dir") + "/src/main/resources/ARC-API-STG.xlsx");

			// RestAssured.baseURI = "https://api.usgbc.org/dev/leed";

			reqSpec = new RequestSpecBuilder().setBaseUri(
					"https://api.usgbc.org/stg/leed").build();
			reqSpecjson = new RequestSpecBuilder().setBaseUri("https://5b927ev4hj.execute-api.us-west-1.amazonaws.com").build();
			respSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
		}

		
	
	
	
	
	@AfterClass
	public void end(){
		
		
			
	}
	
	
	
	
	
	
	
	
	
	

}
