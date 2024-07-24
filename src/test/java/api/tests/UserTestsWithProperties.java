package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPointsWithProperties;
import api.payload.User;
import io.restassured.response.Response;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UserTestsWithProperties {
	
	Faker faker;
	User userPayload;
	
	public Logger logger; // APache Logger
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Initiate logs
		logger = LogManager.getLogger(this.getClass());		
	}
	
	@Test(priority = 1)
	public void testCreateUser() {
		logger.info("*********** Creating User ***********");
		Response response = UserEndPointsWithProperties.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);			
	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("*********** Getting User detail for " + this.userPayload.getUsername() + "***********");
		Response response = UserEndPointsWithProperties.getUser(this.userPayload.getUsername());
		response.then().log().all();
		logger.debug("*********** User is displayed ***********");
		Assert.assertEquals(response.getStatusCode(), 200);			
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		//updated email
		logger.info("*********** Updating User ***********");
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response = UserEndPointsWithProperties.updateUser(this.userPayload.getUsername(), userPayload);
		response.then()
		.log().all()
		//Assert.assertEquals(response.getStatusCode(), 200);
		.statusCode(200);
	
		//Validating data change
		response = UserEndPointsWithProperties.getUser(this.userPayload.getUsername());
		response.then()
		.log().all()
		.statusCode(200)
		//Assert.assertEquals(response.getBody().path("email"), userPayload.getEmail());
		.body("email",equalTo(userPayload.getEmail()));	
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		logger.info("*********** Deleting user ***********");
		Response response = UserEndPointsWithProperties.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		response = UserEndPointsWithProperties.getUser(this.userPayload.getUsername());
		response.then().log().status();
		Assert.assertEquals(response.getStatusCode(), 404);		
	}
	

}
