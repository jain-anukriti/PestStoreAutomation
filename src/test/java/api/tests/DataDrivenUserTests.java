package api.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenUserTests {
	
	@Test(priority = 1, dataProvider = "allData", dataProviderClass = DataProviders.class)
	public void testCreateUser(String id, String username, String firstname, String lastname, String email, String password, String phone) {
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(id));
		userPayload.setUsername(username);
		userPayload.setFirstname(firstname);
		userPayload.setLastname(lastname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);			
	}
	
	@Test(priority = 2, dataProvider = "usernames", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String username) {
		Response response = UserEndPoints.getUser(username);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);			
	}
	
	@Test(priority = 3, dataProvider = "allData", dataProviderClass=DataProviders.class)
	public void testUpdateUser(String id, String username, String firstname, String lastname, String email, String password, String phone) {
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(id));
		userPayload.setUsername(username);
		userPayload.setFirstname(firstname);
		userPayload.setLastname(lastname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		//updated email
		userPayload.setEmail(new Faker().internet().safeEmailAddress());
		Response response = UserEndPoints.updateUser(username, userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Validating data change
		response = UserEndPoints.getUser(username);
		response.then().log().all();
		Assert.assertEquals(response.getBody().path("email"), userPayload.getEmail());
	}
	
	@Test(priority = 4, dataProvider = "usernames", dataProviderClass=DataProviders.class)
	public void testDeleteUser(String username) {
		Response response = UserEndPoints.deleteUser(username);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		response = UserEndPoints.getUser(username);
		response.then().log().status();
		Assert.assertEquals(response.getStatusCode(), 404);		
	}
	

}
