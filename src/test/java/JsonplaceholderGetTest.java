import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaceholderGetTest {

    //GIVEN - koncepcja
    //WHEN - wysłanie requestu
    //THEN - asercja

    @Test
    public void jsonplaceholderReadAllUsers(){


        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name");

        Assertions.assertEquals(10, names.size());

        names.stream()
                .forEach(System.out::println);

    }


    @Test
    public void jsonplaceholderReadOneUser() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.statusCode());

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));
        Assertions.assertEquals("-37.3159", json.get("address.geo.lat"));

    }

    // Path Variables wyszukiwanie po id=1 uzytkownika

    @Test
    public void jsonPlaceholderReadOneUserWithPathVariables(){

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");
        System.out.println(response.asString());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.statusCode());

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));
        Assertions.assertEquals("-37.3159", json.get("address.geo.lat"));

    }

    // query params w urlu wpisujemy imie uzytwkonika tak jak kiedyś było https://jsonplaceholder.typicode.com/users?username=Bret

    @Test
    public void jsonplaceholderReadOneUserWithQueryParams()
    {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users/");

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0));
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));
        Assertions.assertEquals("-37.3159", json.getList("address.geo.lat").get(0));
    }

}
