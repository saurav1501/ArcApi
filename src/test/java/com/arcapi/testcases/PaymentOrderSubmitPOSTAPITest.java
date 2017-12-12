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
import com.relevantcodes.extentreports.LogStatus;

public class PaymentOrderSubmitPOSTAPITest extends BaseClass {

	@Test//(dependsOnMethods = { "com.arcapi.testcases.CreateAssetPOSTAPITest.CreateAssetPOSTAPI" })
	@Parameters({ "SheetName", "ProjectTypeColumn", "rownumber" })
	public void PaymentOrderSubmitPOSTAPI(String SheetName, String ProjectTypeColumn, int rownumber)
			throws IOException {

		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();

		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		CommonMethod.res = given()
				.parameters("name", "Test name", "email", "test-05@gmail.com", "unit_price", "1500.0", "material_code",
						data.getCellData(SheetName, "MaterialCode", rownumber), "material_description", data.getCellData(SheetName, "MaterialDescription", rownumber),
						"quantity", "1.0", "company_code", data.getCellData(SheetName, "CompanyCode", rownumber), "street", "Test Add", "city", "Test city", "state",
						"NA", "country", "TG", "zip_code", "22673", "SoReference", "REGISTRATION", "actual_price",
						"1500.0", "annual_price", "0.0",
						"paymetric_r","PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48UGF5bWV0cmljUmVzcG9uc2U-PFN0YXR1cz5Ub2tlbml6ZWQ8L1N0YXR1cz48WGlQYXlOZXRUcmFuc0lEPjA8L1hpUGF5TmV0VHJhbnNJRD48TWVyY2hhbnRSZWZlcmVuY2VDb2RlIC8-PEF1dGhDb2RlPjA8L0F1dGhDb2RlPjxDYXJkSW5mbz48Q0NUb2tlbj4tRTgwMy0wMDAwLVdZODdUR0cwQlJCQzRZPC9DQ1Rva2VuPjxDQ0V4cGlyYXRpb25EYXRlPjxNb250aD4wMTwvTW9udGg-PFllYXI-MTk8L1llYXI-PC9DQ0V4cGlyYXRpb25EYXRlPjxDVlY-OTk5PC9DVlY-PENDVHlwZT40MDAwPC9DQ1R5cGU-PEJpblJhbmdlVHlwZT40MDAwPC9CaW5SYW5nZVR5cGU-PC9DYXJkSW5mbz48QW1vdW50PjA8L0Ftb3VudD48L1BheW1ldHJpY1Jlc3BvbnNlPg==",
						"paymetric_s","9pVBS5jQtnNbRGzfciD03OoBN7fP1aSFigXogT7LOaw=")
				.header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("Authorization", header).spec(reqSpec).when()
				.post("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/payments/order/")
				.then().extract().response();

		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println(CommonMethod.responsetime);

		CommonMethod.test = CommonMethod.extent
				.startTest("Payment Order Submit API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Payment done for registration")
				.assignCategory("CheckPayment");

		CommonMethod.testlog("Pass", "Authorization Token generated" + "<br>" + header);

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