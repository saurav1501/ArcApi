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

import net.minidev.json.JSONObject;

public class CreditUpdatePUTAPITest extends BaseClass {

	
	@Test(dependsOnMethods = { "com.arcapi.testcases.CreateAssetPOSTAPITest.CreateAssetPOSTAPI" },groups = { "Certification", "Precertification","PerformanceScore","Recertification" })
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber","ProjectType" })
	public void CreditUpdatePUTAPI(String SheetName,String ProjectTypeColumn, int rownumber, String ProjectType) throws IOException {

		String credit[];
		
		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		JSONObject jsonAsMap = new JSONObject();
		
		jsonAsMap.put("is_readyForReview", "True");

	
		String LeedID = data.getCellData(SheetName, ProjectTypeColumn, rownumber);
        
		if(ProjectType.equalsIgnoreCase("building")){
			
		
			credit = new String[]{ "ACS2SS103L-" + LeedID, "ACS2WE103L-" + LeedID, "ACS2EA102L-" + LeedID,
				"ACS2EA105L-" + LeedID, "ACS2EA109L-" + LeedID, "ACS2MR105L-" + LeedID, "ACS2MR106L-" + LeedID,
				"ACS2EQ103L-" + LeedID, "ACS2EQ106L-" + LeedID, "ACS2EQ108L-" + LeedID };
		}
		
		else if(ProjectType.equalsIgnoreCase("city")){
			
			
			credit = new String[]{ "CTP1PR901L-" + LeedID,"CTP1PR902L-" + LeedID ,"CTP1PR903L-" + LeedID,"CTP1PR904L-" + LeedID,"CTP1PR905L-" + LeedID,"CTP1PR906L-" + LeedID };
		}
		
		else if(ProjectType.equalsIgnoreCase("community")){
			
			
			credit = new String[]{ "CMP1PR901L-" + LeedID,"CMP1PR902L-" + LeedID,"CMP1PR903L-" + LeedID,"CMP1PR904L-" + LeedID,"CMP1PR905L-" + LeedID,"CMP1PR906L-" + LeedID };
		}
		
		else{
			
			
		credit = new String[]{ "MTS2SS103L-" + LeedID,"MTS2WE103L-" + LeedID,"MTS2EA102L-" + LeedID,"MTS2EA105L-" + LeedID, "MTS2EA109L-" + LeedID,"MTS2MR105L-" + LeedID ,"MTS2MR106L-" + LeedID,"MTS2EQ103L-"
					+ LeedID,"MTS2EQ106L-" + LeedID,"MTS2EQ108L-" + LeedID };
		}
		
		
		for(String i : credit){
		
			System.out.println(i);
			CommonMethod.res = given().header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
					.header("Content-type", "application/json").header("Authorization", header)
					.spec(reqSpec).body(jsonAsMap).when()
					.put("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/actions/ID:" + i + "/")
					.then().extract().response();
			
			CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

			System.out.println(CommonMethod.responsetime);


		}

		CommonMethod.test = CommonMethod.extent
				.startTest("Credit Update PUT API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Credit update")
				.assignCategory("CheckCredit");

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