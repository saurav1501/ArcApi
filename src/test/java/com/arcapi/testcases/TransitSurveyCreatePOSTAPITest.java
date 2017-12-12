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

public class TransitSurveyCreatePOSTAPITest extends BaseClass {

	@Test
	public void TransitSurveyCreatePOSTAPI() throws IOException, InterruptedException {

		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		String instance = "u3ejud" + CommonMethod.randomnumbersignup();

		System.out.println(instance);

		JSONObject jsonAsMap = new JSONObject();
		jsonAsMap.put("tenant_name", "Abhishek Gupta");
		jsonAsMap.put("response_method", "web");

		jsonAsMap.put("routes", "[{'walk': '1','bus': '1.2','heavy_rail': '1.5','car23': '4'}]");
		jsonAsMap.put("instance", "u88k3kedh399xj");

		CommonMethod.res = given()
				.parameters("tenant_name", "Abhishek Gupta", "response_method", "web", "routes",
						"[{'walk': '1','bus': '1.2','heavy_rail': '1.5','car23': '4'}]", "instance", instance)
				.header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("Authorization", header).spec(reqSpec).when()
				.post("/assets/LEED:" + CommonMethod.LeedID + "/survey/transit/?key=rkukdMuBOZGYGXp80E0ZWtQr").then()
				.extract().response();
		
		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println(CommonMethod.responsetime);


		CommonMethod.test = CommonMethod.extent
				.startTest("Transit Survey Create API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Transit survey creation")
				.assignCategory("CheckSurvey");

		CommonMethod.testlog("Pass", "Authorization Token generated" + "<br>" + header);

		
		System.out.println(CommonMethod.res.asString());
		CommonMethod.res.then().assertThat().statusCode(201);

		
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