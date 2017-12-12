package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;
import com.jayway.restassured.http.ContentType;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

public class SignAgreementPOSTAPITest extends BaseClass {

	
	@Test(dependsOnMethods={"com.arcapi.testcases.CreateAssetPOSTAPITest.fetchingJsonRes"})
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber" })
	public void fetchingJsonRes(String SheetName,String ProjectTypeColumn, int rownumber) throws IOException {
		
		CommonMethod.ExtentReportConfig();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());		
		CommonMethod.test = CommonMethod.extent.startTest("Sign Agreement POST API Test", "Verifies sign Agreement").assignCategory("CheckSignAgreement");

		
		//CommonMethod.GeneratingAuthCode();

		JSONObject jsonAsMap = new JSONObject();
		jsonAsMap.put("SoReference", "REVIEW");

		CommonMethod.res = given()
				.multiPart("agreement", CommonMethod.file)
				.parameters("SoReference", "REVIEW")
				.header("Ocp-Apim-Subscription-Key",
						CommonMethod.SubscriptionKey)
				.header("Authorization", header)
				.spec(reqSpec)
				.when()
				.post("/assets/LEED:"
						+ data.getCellData(SheetName, ProjectTypeColumn, rownumber)
						+ "/agreements/").then().contentType(ContentType.JSON)
				.extract().response();
		System.out.println(CommonMethod.res.asString());
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