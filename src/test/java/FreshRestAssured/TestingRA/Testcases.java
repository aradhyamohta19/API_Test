package FreshRestAssured.TestingRA;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.Matchers.equalTo;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

public class Testcases {
	
	ResponseSpecification spec = null;
	@BeforeClass
	public void setSpecification() {
		spec = RestAssured.expect();
		spec.contentType(ContentType.JSON);
		spec.statusCode(200);
		spec.statusLine("HTTP/1.1 200 OK");		
		ExtentReportManager.createReport();
	}
	
	@Test
	public void getListOfUsers() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("getListOfUsers", "Gets the list of Users");
		try {



			
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, " BASE URL was Configured", RestAssured.baseURI);

			ExtentReportManager.test.log(LogStatus.INFO, "GET API call was made", "api/v1/employees");

			ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "Successfully! All records has been fetched.");

			given().contentType(ContentType.JSON).when().get("api/v1/employees").then().body("message", equalTo("Successfully! All records has been fetched.")) ;
			
			ExtentReportManager.test.log(LogStatus.PASS, "Result", "PASS");
			
		}
		
		catch(Exception ex){
			
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception Occured", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL , "TC Failed", ex.getMessage());
			
		}
		
		



	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void CreateUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("CreateUser", "Create An User");
		JSONObject params = new JSONObject();
		params.put("name", "Arzoo");
		params.put("age", "28");
		params.put("salary", "600000");




		ExtentReportManager.test.log(LogStatus.INFO, "JSON request body Configured ", params.toJSONString());
		RestAssured.baseURI = "https://dummy.restapiexample.com/";

		ExtentReportManager.test.log(LogStatus.INFO, "BASE URL Configured", RestAssured.baseURI);
		ExtentReportManager.test.log(LogStatus.INFO, "POST API call Made", "api/v1/create");
		Response response = given().when().header("Content-Type", "application/json").body(params.toJSONString()).post("api/v1/create");
		
		ExtentReportManager.test.log(LogStatus.INFO, "Expected Status Code ", "200");
		ExtentReportManager.test.log(LogStatus.INFO, "Actual Status Code ", Integer.toString(response.statusCode()));
		


	}
	
	
	@Test
	public void getSpecificUser() {
		//ResponseSpecification spec2 = RestAssured.expect();
		//spec2.contentType(ContentType.JSON);
		//spec2.statusCode(200);
		ExtentReportManager.test = ExtentReportManager.reports.startTest("getSpecificUser", "Fetch record of One user");
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "BASE URL Configured ", RestAssured.baseURI);
		ExtentReportManager.test.log(LogStatus.INFO, " GET API call made", "api/v1/employee/<id>");
		ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "22");
		ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "Record had been succefully Fetched!");
		try {
		given().when().get("api/v1/employee/22").then().assertThat().body("message", IsEqual.equalTo("Record had been succefully Fetched!"));

		}
		
		catch(Exception ex){
			
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception Occured", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL , "TC Failed", ex.getMessage());
			
		}
		
		
		
	}
	
	
	@Test
	public void DeleteUser() {
		
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("DeleteSpecificUser", "Delete record");
		
		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		ExtentReportManager.test.log(LogStatus.INFO, "Configured the BASE URL", RestAssured.baseURI);
		ExtentReportManager.test.log(LogStatus.INFO, "Make a DELETE API call", "/api/v1/delete/<id>");
		ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "2");
		ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "Record had been succefully Deleted!");
		try {
		given().delete("/api/v1/delete/2").then().assertThat().body("message", IsEqual.equalTo("Record had been succefully Deleted!"));
		}
		
		catch(Exception ex){
			
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception Occured", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL , "TC Failed", ex.getMessage());
			
		}
		
		

	}
	
	
	@Test
	public void UpdateUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("UpdateUser", "Update record");

		RestAssured.baseURI = "https://dummy.restapiexample.com/";
		
		ExtentReportManager.test.log(LogStatus.INFO, "Configured the BASE URL", RestAssured.baseURI);
		ExtentReportManager.test.log(LogStatus.INFO, "Make a PUT API call", "/api/v1/update/<id>");
		ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "21");
		ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "Record had been succefully updated!");
		try {
		given().put("/api/v1/update/21").then().assertThat().body("message", IsEqual.equalTo("Record had been succefully updated!"));
		}
		
		catch(Exception ex){
			
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception Occured", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL , "TC Failed", ex.getMessage());
			
		}
		
		
	}
	
	
	@AfterClass
	public void endReport() {
		
		ExtentReportManager.reports.flush();
	}
	
	

	

}
