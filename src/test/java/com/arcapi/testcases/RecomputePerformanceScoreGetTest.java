package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.relevantcodes.extentreports.LogStatus;

public class RecomputePerformanceScoreGetTest extends BaseClass {

	@Test
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber" })
	public void RecomputePerformanceScoreGet(String SheetName,String ProjectTypeColumn, int rownumber) throws IOException {

	try {
		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		CommonMethod.res = given().header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("Authorization", header).spec(reqSpec).when()
				.get("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/scores/recompute/").then()
				.extract().response();
		
		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println(CommonMethod.responsetime);

		CommonMethod.test = CommonMethod.extent.startTest(
				"RecomputePerformanceScore Get API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
				"Recompute Score").assignCategory("Recompute");

		CommonMethod.fetchingJSONResponse(
				"/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/scores/recompute/");

		JsonParser parser = new JsonParser();
		parser.parse(CommonMethod.res.toString());

		CommonMethod.testlog("Info", "API responded in " + CommonMethod.responsetime + " Milliseconds");
	
	}
	
	catch(JsonSyntaxException jse){
		System.out.println("Not a valid Json String:"+jse.getMessage());
		
	}
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