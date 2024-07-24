package api.tests;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import api.endpoints.ReqResEndpoints;

public class ReqResTests {
	
	@Test
	public void getUsers() {
		Response response = ReqResEndpoints.getUsers();
		response.then()
			.statusCode(200)   //assertThat is added by default
			.log().all();

	}
	
	@Test
	public void createUser() {
		Response response = ReqResEndpoints.createUser();
		response.then()
		.log().all()
		.statusCode(201)
		.body("id",notNullValue());
		
		ResponseBody body = response.getBody();
		String id = body.jsonPath().getString("id");
		System.out.println("id is " + id);
	}
	
	@Test
	public void parseJSONResponseDataThroughDirectThen() {
		Response res = ReqResEndpoints.getUsers();
		res.then()
		.contentType(ContentType.JSON)
		.statusCode(200)
		.body("data[5].last_name",equalTo("Howell"))
		.body("data.id", hasItems(7,8));
	}
	
	@Test
	public void parseJSONResponseDataUsingTestngAssertions() {
		Response res = ReqResEndpoints.getUsers();

		//testng assertions
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.getHeader("Content-Type"), "application/json; charset=utf-8");
		
		String lastname = res.jsonPath().get("data[5].last_name").toString();
		Assert.assertEquals(lastname, "Howell");               	
	}
	
	@Test
	public void parseJSONResponseDataToCaptureData() {
		Response res = ReqResEndpoints.getUsers();
		
		//Approach 1
		//Converting response into JSONObject type
		JSONObject jobj = new JSONObject(res.asString());
		for(int j = 0; j < jobj.getJSONArray("data").length(); j++) {
			String lastName = jobj.getJSONArray("data").getJSONObject(j).get("last_name").toString();
			System.out.println(lastName);
		}
		
		//Approach 2
		ArrayList<String> lastnames = res.jsonPath().get("data.last_name");
		for (String i : lastnames) {
		System.out.println(i);
	}
	}
	
	@Test
	public void getSumOfIds() {
	
		Response res = ReqResEndpoints.getUsers();
		JSONObject o = new JSONObject(res.asString());
		int sum = 0;
		
		System.out.println("JSON object value is \n" + o.toString());
		for (int i = 0; i < o.getJSONArray("data").length(); i++) {
			sum += o.getJSONArray("data").getJSONObject(i).getInt("id");
		}
		
		System.out.println("Sum of IDS is " + sum);
		Assert.assertEquals(sum, 57);
	}

}
