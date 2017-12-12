package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

public class UserRegistrationAPITest extends BaseClass {

	@Test (dependsOnMethods={"com.arcapi.testcases.CreateAssetPOSTAPI.fetchingJsonRes"})
	public void UserRegistrationAPI() throws IOException, InterruptedException {

		CommonMethod.ExtentReportConfig();

		CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		String ID = "Testuser" + CommonMethod.randomnumbersignup() + "@gmail.com";
		System.out.println(ID);

		JSONObject jsonAsMap = new JSONObject();
		jsonAsMap.put("first_name", "Test");
		jsonAsMap.put("last_name", "User");
		jsonAsMap.put("email", ID);
		jsonAsMap.put("password", "initpass");

		CommonMethod.res = given().header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("content-type", "application/json").spec(reqSpec).body(jsonAsMap).when().post("/auth/register/")
				.then().extract().response();

		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println(CommonMethod.responsetime);

		CommonMethod.test = CommonMethod.extent
				.startTest("User Registration API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies User Registration")
				.assignCategory("CheckUserRegistration");

		CommonMethod.testlog("Pass", "Authorization Token generated" + "<br>" + CommonMethod.header);

		
		System.out.println(CommonMethod.res.asString());
		CommonMethod.res.then().spec(respSpec);

		
		CommonMethod.testlog("Pass", "Verifies response from API" + "<br>" + CommonMethod.res.asString());

		CommonMethod.testlog("Info", "API responded in " + CommonMethod.responsetime + " Milliseconds");

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