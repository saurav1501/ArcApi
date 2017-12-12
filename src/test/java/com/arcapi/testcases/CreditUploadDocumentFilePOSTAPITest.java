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

public class CreditUploadDocumentFilePOSTAPITest extends BaseClass {

	@Test
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber" })
	public void CreditUploadDocumentFilePOSTAPI(String SheetName,String ProjectTypeColumn, int rownumber) throws IOException {
		
		CommonMethod.ExtentReportConfig();
		
		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		CommonMethod.res = given().log().all()
				.multiPart("file", CommonMethod.formuploadfile)
				.parameters("data_element", data.getCellData(SheetName, "CreditID", rownumber))
				.header("Ocp-Apim-Subscription-Key",
						CommonMethod.SubscriptionKey)
				.header("Authorization", header)
				.spec(reqSpec)
				.when()
				.post("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber) + "/actions/ID:"
						+ data.getCellData(SheetName, "CreditID", rownumber) + "/uploads/").then().extract()
				.response();
		System.out.println(CommonMethod.responsetime);
		
		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);
		
		CommonMethod.test = CommonMethod.extent.startTest("Credit Upload Document File API Test  "+ CommonMethod.getLabel(CommonMethod.responsetime), "Verifies credit upload document file").assignCategory("CheckCredit");


		CommonMethod.testlog("Pass", "Authorization Token generated" + "<br>" + header);
		
		System.out.println(CommonMethod.res.asString());
		CommonMethod.res.then().spec(respSpec);

		
		
		CommonMethod.testlog("Pass", "Verifies response from API" + "<br>" + CommonMethod.res.asString());
		
		CommonMethod.fetchedID = CommonMethod.res.path("uploaded.Documentnumber").toString();

		System.out.println(CommonMethod.fetchedID);

		data.setCellData(SheetName, "DocumentNumber", rownumber, CommonMethod.fetchedID);
		
		CommonMethod.fetchedID = CommonMethod.res.path("uploaded.ApplicationId").toString();

		System.out.println(CommonMethod.fetchedID);

		data.setCellData(SheetName, "ApplicationID", rownumber, CommonMethod.fetchedID);
		
		CommonMethod.fetchedID = CommonMethod.res.path("uploaded.FileId").toString();

		System.out.println(CommonMethod.fetchedID);

		data.setCellData(SheetName, "FileID", rownumber, CommonMethod.fetchedID);
		
		CommonMethod.testlog("Info", "API responded in "
				+ CommonMethod.responsetime + " Milliseconds");

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