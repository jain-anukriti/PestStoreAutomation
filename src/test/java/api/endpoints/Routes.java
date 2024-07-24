package api.endpoints;

public class Routes {
	
	public static String base_url = "https://petstore.swagger.io/v2";
	public static String reqres_base_url = "https://reqres.in/api";
	
	//User module endpoints
	public static String post_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String put_url = base_url + "/user/{username}";
	public static String delete_url = base_url + "/user/{username}";
	
	//Store module endpoints
	
	//pet module endpoints
	
	//reqres endpoints
	public static String get_users_url = reqres_base_url + "/users";
	public static String post_user_url = reqres_base_url + "/users";

	//XML rest api
	public static String get_authors = "https://thetestrequest.com/authors.xml";
}
