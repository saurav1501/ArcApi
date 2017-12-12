package com.arc.driver;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class CommonMethod extends BaseClass {
	
	public static long responsetime;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String signupID;
	public static String LeedID = "1000115915";
	public static String WasteID = "2562";
	public static String CreditID = "EBS8PI101L-";
	public static String MeterID = "6812";
	public static String PK = "282844";
	public static String ZipCode = "20037";
	public static String Key = "rkukdMuBOZGYGXp80E0ZWtQr";
	public static String SubscriptionKey = "06c50a1570bd4d40a7859ec28514b185";
	public static Response res;
	public static String fetchedID;
    public static File formuploadfile = new File("/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/src/main/resources/file.pdf");
	public static File excelfile = new File("/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/src/main/resources/LEED_Dynamic_Plaque_Data_Template_v02.xlsm");
	public static File file = new File("/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/src/main/resources/20131104_Certification_Agreement.htm");
	public static String JsonfileUrl = "/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/src/main/resources/JsonfileAPI.txt";
	
	public static void GeneratingAuthCode() {

		Token = given()
				.header("Ocp-Apim-Subscription-Key",
						SubscriptionKey)
				.spec(reqSpec)
				.parameters("username", "saurav@groupten.com", "password",
						"LEEDg10").expect().statusCode(200).when()
				.post("/auth/login/").then().contentType(ContentType.JSON)
				.extract().response().path("authorization_token").toString();
		
		header = "Bearer " + Token;
		System.out.println(header);
		
		
	}
	
	public static String getLabel(long responsetime) {
		
		if (responsetime <= 4000){
			
	    return "<span class='label outline info'>" + CommonMethod.responsetime + " Milliseconds" + "</span>";
		}
		
		else
		{
		return "<span class='label outline fatal'>" + CommonMethod.responsetime + " Milliseconds" + "</span>";
		}
	    
	    
	}
	
	public static void fetchingJSONResponse(String Url) {
        testlog("Pass", "Authorization Token generated"+ "<br>" +header);
		Response res = given()
				.header("Ocp-Apim-Subscription-Key",
						SubscriptionKey)
				.header("Authorization", header).spec(reqSpec).expect()
				.statusCode(200).when().get(Url).then()
				.extract().response();

		System.out.println(res.asString());
		testlog("Pass", "Response received from API"+ "<br>" +res.asString());

	}
	
	public static void responsetimevalidation(String Url) {
		given().header("Ocp-Apim-Subscription-Key",
				SubscriptionKey)
				.header("Authorization", CommonMethod.header).spec(reqSpec).when()
				.get(Url).then().spec(respSpec)
				.and().time(lessThan(50000L));
	}
	
	public static void filewriteID(String url) throws IOException{
		
		File file = new File(url);

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(fetchedID);
		bw.close();
	}
	
	public static void trimLeedID(){
		
        System.out.println(res.asString());
		String responseBody = res.getBody().asString();
		System.out.println(responseBody);
		fetchedID = responseBody.trim().substring(274, 284);
		System.out.println(fetchedID);
	}
	
public static void trimMeterID(){
		
        System.out.println(res.asString());
		String responseBody = res.getBody().asString();
		System.out.println(responseBody);
		fetchedID = responseBody.trim().substring(6, 10);
		System.out.println(fetchedID);
	}


public static void ExtentReportConfig() {
	 Format formatter = new SimpleDateFormat("YYYY-MM-dd");
	 Date date = new Date();
	extent = new ExtentReports("/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/Advance/ARC_API-AutomationReport" + "_" + formatter.format(date) + ".html", false);
	
	extent.loadConfig(new File("/Users/Group10/workspace/ARC-API/AutomationTesting/TestArcAPI/src/main/resources/extent-config.xml"));
   Map<String, String> sysInfo = new HashMap<String, String>();
	sysInfo.put("Environment", "Staging");
   extent.addSystemInfo(sysInfo);
	
	
}

public static void testlog(String log, String message){
	
	switch(log){
	
	case "Pass":
		test.log(LogStatus.PASS, message);
		break;
		
	case "Info":
		test.log(LogStatus.INFO, message);
		break;
		
	 default:
     	
     	System.out.println("Not Valid Input");
	}
}

	
	public static String filereadID(String url) throws IOException{
		
		 FileReader inputFile = new FileReader(url);
		
	        //Instantiate the BufferedReader Class
	        BufferedReader bufferReader = new BufferedReader(inputFile);

	        //Variable to hold the one line data
	     
	        String text;
	        // Read file line by line and print on the console
	        while ((text = bufferReader.readLine()) != null)   {
	        
	        	fetchedID=text;
	         //System.out.println(CommonMethod.ProgramID);
	         
	          
	        }
	        
	      //Close the buffer reader
	        bufferReader.close();
	        return fetchedID;
	}
	
	public static String randomnumbersignup() throws IOException, InterruptedException{
    	
	   	 int random_num = 10000;
			    Random t = new Random();
			    // random integers in [1000, 800000]
			    random_num=	(t.nextInt(800000));
			    signupID = String.valueOf(random_num);
			    System.out.println(signupID);
				return signupID;
				
	   }
	
	
	
	
	
	
	
	
	
	
	
	
		
}