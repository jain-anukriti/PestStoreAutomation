package api.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Authentications {

	@Test
	void basic_auth() {
		given()
		.auth().basic("postman","password")
		.when()
		.get("https://postman-echo.com/basic-auth")
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true))
		.log().all();
	}
	
	@Test
	void preemptive_auth() {
		given()
		.auth().preemptive().basic("postman","password")
		.when()
		.get("https://postman-echo.com/basic-auth")
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true))
		.log().all();
	}
	
	@Test
	void digest_auth() {
		given()
		.auth().digest("postman","password")
		.when()
		.get("https://postman-echo.com/basic-auth")
		.then()
		.statusCode(200)
		.body("authenticated",equalTo(true))
		.log().all();
	}
	
	@Test
	void bearer_auth() {
		//Generate this token from settings>developer-settings from github profile
		String token = "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"; 
		given()
		.header("Authorization", "Bearer " + token)
		.when()
		.get("https://api.github.com/user/repos")
		.then()
		.statusCode(200)
		.log().all();
	}
	
	@Test
	void oauth1_auth() {
		given()
		.auth().oauth("consumerKey", "consumerSecret", "access_token", "token_secret")
		.when()
		.get("url")
		.then()
		.statusCode(200)
		.log().all();
	}
	
	@Test
	void oauth2_auth() {
		given()
		.auth().oauth2("access_token")
		.when()
		.get("url")
		.then()
		.statusCode(200)
		.log().all();
	}
	
	@Test
	void api_key_auth() {
		//Get api key from an api like https://openweathermap.org
		given() 
		.queryParam("appid", "apiidvalue") //appid is api key
		.when()
		.get("https://pro.openweathermap.org/data/2.5/forecast/climate?lat=35&lon=139")
		.then()
		.statusCode(200)
		.log().all();
	}
}
