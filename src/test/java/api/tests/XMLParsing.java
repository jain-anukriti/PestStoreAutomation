package api.tests;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import api.endpoints.Routes;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class XMLParsing {
	
	@Test
	public void getResponse() {
		given().
			accept(ContentType.XML)
		.when()
			.get(Routes.get_authors)
		.then()
			.header("Content-Type", "application/xml; charset=utf-8")
			.log().all();
	}

	@Test
	public void testXMLResponse() {
		Response res = given()
							.accept(ContentType.XML)
						.when()
							.get(Routes.get_authors);
		XmlPath xobj = new XmlPath(res.asString());
		List<String> names = xobj.getList("objects.object.name");
		List<String> emails = xobj.getList("objects.object.email");
		
		//To print all names
		for (int i = 0; i < names.size(); i++) {
			if(names.get(i).equals("Gracia Keeling")) {
				System.out.println("email is " + emails.get(i));
			} 
		}
	}
	
}
