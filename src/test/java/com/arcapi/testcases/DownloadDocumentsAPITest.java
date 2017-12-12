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

public class DownloadDocumentsAPITest extends BaseClass {

	@Test
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber" })
	public void DownloadDocumentsAPI(String SheetName,String ProjectTypeColumn, int rownumber) throws IOException {

		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		JSONObject jsonAsMap = new JSONObject();
		jsonAsMap.put("subscription-key", CommonMethod.SubscriptionKey);
		jsonAsMap.put("access-token", CommonMethod.Token);
		jsonAsMap.put("Docfile", "20131104_Certification_Agreement.pdf");
		jsonAsMap.put("IDocType", "Z02");
		jsonAsMap.put("IDocNumber", "0000000010000000000744017");
		jsonAsMap.put("IDocPart", "000");
		jsonAsMap.put("IDocVersion", "00");
		jsonAsMap.put("IFileApplicationId", "E681AA56FDE98BF1ABE70AFDDE1F6773");
		jsonAsMap.put("IFileId", "E681AA56FDE98DF1ABE70AFDDE1F6773");

		CommonMethod.res = given().parameters(jsonAsMap).spec(reqSpec).body(jsonAsMap).when()
				.get("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/documents/download/").then()
				.extract().response();

		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		CommonMethod.test = CommonMethod.extent
				.startTest("Download Documents API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Documents download")
				.assignCategory("CheckDownloadDocuments");

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