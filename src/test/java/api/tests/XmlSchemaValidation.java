package api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import api.endpoints.Routes;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;

public class XmlSchemaValidation {

	@Test
	void xmlSchemaValidation() {
		Response res = given()
				.accept(ContentType.XML)
			.when()
				.get(Routes.get_authors);
		res.then()
		.body(RestAssuredMatchers.matchesXsdInClasspath("Schemas/xmlSchema.xsd"));
	}
}
