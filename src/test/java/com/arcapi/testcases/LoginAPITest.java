package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;
import com.relevantcodes.extentreports.LogStatus;

public class LoginAPITest extends BaseClass {

 
  @Test(groups = { "Certification", "Precertification","PerformanceScore","Recertification" })
  public void LoginAPI()  {
	  
	  CommonMethod.ExtentReportConfig();
	  
	 CommonMethod.res =  given()
		.header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
		.spec(reqSpec)
		.parameters("username", "stg-01@gmail.com", "password",
				"initpass").when()
		.post("/auth/login/").then()
		.extract().response();
	  
	   CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);
		
		CommonMethod.test = CommonMethod.extent.startTest("Login API Test  "+ CommonMethod.getLabel(CommonMethod.responsetime), "Verifies Login").assignCategory("CheckLogin");

	    System.out.println(CommonMethod.res.asString());
	  
	  CommonMethod.res.then().assertThat().statusCode(200);
	    
		CommonMethod.testlog("Pass", "Parameters passed for Login:" + "<br>" + "Username : Testuser@gmail.com" + "<br>" + "Password : ********");
 		CommonMethod.testlog("Pass", "Verifies response from API" + "<br>" + CommonMethod.res.asString());

  
  }
  
  
  
  @AfterMethod
	public void teardown(ITestResult result) {
		
		 if (result.getStatus() == ITestResult.FAILURE) {
			 CommonMethod.test.log(LogStatus.FAIL, result.getThrowable());
	        } else if (result.getStatus() == ITestResult.SKIP) {
	        CommonMethod.test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
	        } else {
	        CommonMethod.test.log(LogStatus.PASS, "Test passed");
	        }


		CommonMethod.extent.endTest(CommonMethod.test);
		CommonMethod.extent.flush();
		
		
	}
}
