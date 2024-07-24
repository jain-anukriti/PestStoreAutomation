package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReqResEndpoints {
	
	public static Response getUsers() {
		Response response = given()
				.queryParam("page",2)
				.when()
				.get(Routes.get_users_url);
		
		return response;
	}
	
	public static Response createUser() {
		Response r = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.cookie("username","anu")
				.body("{"
						+ "    \"name\": \"morpheus\",\r\n"
						+ "    \"job\": \"leader\"\r\n"
						+ "}")
				.when()
				.post(Routes.post_user_url);
		
		return r;
	}

}
