package api.tests;

import org.testng.annotations.Test;

import api.endpoints.ReqResEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class JSONSchemaValidation {
	
	@Test
	public void schemaValidation() {
		Response res = ReqResEndpoints.getUsers();
		res.then()
		.log().body()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/reqResJsonSchema.json"));
	}

}
