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
import com.google.gson.Gson;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

public class SubmitReviewPOSTAPITest extends BaseClass {

	@Test(dependsOnMethods = {"com.arcapi.testcases.ReviewCertificationPricingAPITest.ReviewCertificationPricingAPI" }, groups = {"Certification", "Precertification", "PerformanceScore", "Recertification" })
	@Parameters({ "SheetName", "ProjectTypeColumn", "rownumber", "ProjectType","SoReferenceSR" })
	public void SubmitReviewPOSTAPI(String SheetName, String ProjectTypeColumn, int rownumber, String ProjectType,String SoReferenceSR)
			throws IOException {

		String s;
		System.out.println(ProjectType);
		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();

		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		Gson gson = new Gson();

		String LeedID = data.getCellData(SheetName, ProjectTypeColumn, rownumber);

		if (ProjectType.equalsIgnoreCase("building")) {
			s = "ACS2SS103L-" + LeedID + ",ACS2WE103L-" + LeedID + ",ACS2EA102L-" + LeedID + ",ACS2EA105L-" + LeedID
					+ ",ACS2EA109L-" + LeedID + ",ACS2MR105L-" + LeedID + ",ACS2MR106L-" + LeedID + ",ACS2EQ103L-"
					+ LeedID + ",ACS2EQ106L-" + LeedID + ",ACS2EQ108L-" + LeedID;
		} else if (ProjectType.equalsIgnoreCase("city")) {
			s = "CTP1PR901L-" + LeedID + ",CTP1PR902L-" + LeedID + ",CTP1PR903L-" + LeedID + ",CTP1PR904L-" + LeedID
					+ ",CTP1PR905L-" + LeedID + ",CTP1PR906L-" + LeedID;
		}

		else if (ProjectType.equalsIgnoreCase("community")) {
			s = "CMP1PR901L-" + LeedID + ",CMP1PR902L-" + LeedID + ",CMP1PR903L-" + LeedID + ",CMP1PR904L-" + LeedID
					+ ",CMP1PR905L-" + LeedID + ",CMP1PR906L-" + LeedID;
		}

		else {
			s = "MTS2SS103L-" + LeedID + ",MTS2WE103L-" + LeedID + ",MTS2EA102L-" + LeedID + ",MTS2EA105L-" + LeedID
					+ ",MTS2EA109L-" + LeedID + ",MTS2MR105L-" + LeedID + ",MTS2MR106L-" + LeedID + ",MTS2EQ103L-"
					+ LeedID + ",MTS2EQ106L-" + LeedID + ",MTS2EQ108L-" + LeedID;

		}

		String creditJson = gson.toJson(s);

		System.out.println(creditJson);

		JSONObject jsonAsMap = new JSONObject();
		jsonAsMap.put("first_name", "Micheal");
		jsonAsMap.put("last_name", "Schumakar");
		jsonAsMap.put("email", "testuser@gmail.com");
		jsonAsMap.put("street", "123-A/5");
		jsonAsMap.put("city", "Gurgaon");
		jsonAsMap.put("state", "07");
		jsonAsMap.put("country", "IN");
		jsonAsMap.put("zip_code", "122001");
		jsonAsMap.put("company_code", data.getCellData(SheetName, "CompanyCode", rownumber));
		jsonAsMap.put("base_score", "10");
		jsonAsMap.put("energy_score", "40");
		jsonAsMap.put("water_score", "40");
		jsonAsMap.put("waste_score", "40");
		jsonAsMap.put("human_score", "40");
		jsonAsMap.put("transport_score", "40");
		jsonAsMap.put("certification_payterm", "5");
		jsonAsMap.put("annual_payterm", "5");
		// jsonAsMap.put("material_price_grp", "L1");
		jsonAsMap.put("review_type", "certification");
		jsonAsMap.put("certification_level", "gold");
		jsonAsMap.put("certification_type", "building");
		jsonAsMap.put("review_start_date", "2016-04-15");
		jsonAsMap.put("review_expiry_date", "2017-04-14");
		jsonAsMap.put("score_submitted", "87");
		jsonAsMap.put("target_score", "87");
		jsonAsMap.put("target_level", "platinum");
		jsonAsMap.put("soreference", SoReferenceSR);
		jsonAsMap.put("material_code", data.getCellData(SheetName, "MaterialCode", rownumber));
		jsonAsMap.put("unit_price", "0.0456");
		jsonAsMap.put("actual_price", "6059.328");
		jsonAsMap.put("annual_price", "10");
		jsonAsMap.put("quantity", "5000");
		jsonAsMap.put("material_desc", data.getCellData(SheetName, "MaterialDescription", rownumber));
		jsonAsMap.put("creditId", s);
		//jsonAsMap.put("paymetric_r", "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48UGF5bWV0cmljUmVzcG9uc2U-PFN0YXR1cz5Ub2tlbml6ZWQ8L1N0YXR1cz48WGlQYXlOZXRUcmFuc0lEPjA8L1hpUGF5TmV0VHJhbnNJRD48TWVyY2hhbnRSZWZlcmVuY2VDb2RlIC8-PEF1dGhDb2RlPjA8L0F1dGhDb2RlPjxDYXJkSW5mbz48Q0NUb2tlbj4tRTgwMy0wMDAwLVdZODdUR0cwQlJCQzRZPC9DQ1Rva2VuPjxDQ0V4cGlyYXRpb25EYXRlPjxNb250aD4wMTwvTW9udGg-PFllYXI-MTk8L1llYXI-PC9DQ0V4cGlyYXRpb25EYXRlPjxDVlY-OTk5PC9DVlY-PENDVHlwZT40MDAwPC9DQ1R5cGU-PEJpblJhbmdlVHlwZT40MDAwPC9CaW5SYW5nZVR5cGU-PC9DYXJkSW5mbz48QW1vdW50PjA8L0Ftb3VudD48L1BheW1ldHJpY1Jlc3BvbnNlPg==");
		//jsonAsMap.put("paymetric_s", "9pVBS5jQtnNbRGzfciD03OoBN7fP1aSFigXogT7LOaw=");

		CommonMethod.res = given().log().all().header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("content-type", "application/json").header("Authorization", header).spec(reqSpec)
				.body(jsonAsMap).when()
				.post("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/review/").then()
				.extract().response();

		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println(CommonMethod.responsetime);

		CommonMethod.test = CommonMethod.extent
				.startTest("Submit Review POST API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Submit Review")
				.assignCategory("CheckSubmitReview");

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