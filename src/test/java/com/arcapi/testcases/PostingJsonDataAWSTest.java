package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.testng.annotations.AfterSuite;
import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class PostingJsonDataAWSTest extends BaseClass{
  @AfterSuite
  public void PostingJsonDataAWS() throws FileNotFoundException, ParseException {
	  try {
		  
	  
	  JSONParser parser = new JSONParser();
      
      Object object = parser.parse(new FileReader(CommonMethod.fetchedID));
      
      JSONObject jsonObject = (JSONObject)object;
      
      System.out.println(jsonObject);
      
  	CommonMethod.res = given().relaxedHTTPSValidation()
			.header("content-type", "application/json")
			.spec(reqSpecjson)
			.body(jsonObject).when().post("/test?environment=stg").then()
			.extract().response();
  	
  	CommonMethod.res.then().spec(respSpec);
  	
  	System.out.println(CommonMethod.res.asString());
  
}

catch(IOException ex) {
    
    ex.printStackTrace();
} catch(Exception e) {  
    e.printStackTrace();  
}
}
  }