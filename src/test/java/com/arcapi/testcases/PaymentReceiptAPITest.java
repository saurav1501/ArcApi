package com.arcapi.testcases;

import static com.jayway.restassured.RestAssured.given;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.arc.driver.BaseClass;
import com.arc.driver.CommonMethod;

public class PaymentReceiptAPITest extends BaseClass {

	@Test
	@Parameters({ "SheetName","ProjectTypeColumn","rownumber" })
	public void PaymentReceiptAPI(String SheetName,String ProjectTypeColumn, int rownumber) throws IOException {

		CommonMethod.ExtentReportConfig();

		//CommonMethod.GeneratingAuthCode();
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

		CommonMethod.res = given().log().all().header("Ocp-Apim-Subscription-Key", CommonMethod.SubscriptionKey)
				.header("Authorization", header).spec(reqSpec).when()
				.get("/assets/LEED:" + data.getCellData(SheetName, ProjectTypeColumn, rownumber)
						+ "/payments/receipt/0010947458/?access-token=" + CommonMethod.Token)
				.then().extract().response();

		CommonMethod.responsetime = CommonMethod.res.getTimeIn(TimeUnit.MILLISECONDS);

		CommonMethod.test = CommonMethod.extent
				.startTest("Payment Receipt API Test  " + CommonMethod.getLabel(CommonMethod.responsetime),
						"Verifies Payment receipt generated")
				.assignCategory("CheckPayment");

		CommonMethod.testlog("Pass", "Authorization Token generated" + "<br>" + header);
		CommonMethod.res.then().spec(respSpec);

		System.out.println(CommonMethod.res.asString());
		CommonMethod.testlog("Pass", "Response received from API" + "<br>" + CommonMethod.res.asString());

	}

}