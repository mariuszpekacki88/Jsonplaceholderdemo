import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGetTwoTest {

    //GIVEN - koncepcja
    //WHEN - wysłanie requestu
    //THEN - asercja

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    @Test
    public void jsonplaceholderReadAllUsers(){


        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then().statusCode(200)
                .extract()       //dodatkowa sekcja dla wyciągnięcia responsa
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
        names.stream()                      // stream jest potrzebny do wyciągnięcia danych z listy na konsole, tego nie powinno być tu w sumie.
                .forEach(System.out::println);

    }


    @Test
    public void jsonplaceholderReadOneUser() {
         given()
                 .when()
                 .get(BASE_URL + "/" + USERS + "/1")
                 .then()
                 .statusCode(200)
                         .body("name", equalTo("Leanne Graham"))
                                 .body("username", equalTo("Bret"))
                                         .body("address.street", equalTo("Kulas Light"))
                                                 .body("address.geo.lat", equalTo("-37.3159"));
    }

    // Path Variables wyszukiwanie po id=1 uzytkownika

    @Test
    public void jsonPlaceholderReadOneUserWithPathVariables(){

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Kulas Light", json.get("address.street"));
        assertEquals("-37.3159", json.get("address.geo.lat"));


    }

    // query params w urlu wpisujemy imie uzytwkonika tak jak kiedyś było https://jsonplaceholder.typicode.com/users?username=Bret

    @Test
    public void jsonplaceholderReadOneUserWithQueryParams()
    {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));
        assertEquals("-37.3159", json.getList("address.geo.lat").get(0));
    }
}