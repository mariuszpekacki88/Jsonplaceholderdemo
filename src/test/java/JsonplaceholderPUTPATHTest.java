import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPUTPATHTest {

    private static Faker faker;
    private String fakerName;
    private String fakerUserName;
    private String fakerStreet;

    private String fakerEmail;

    @BeforeAll  // uruchomi się przed wszytskimi testami tylko raz
    public static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach  // uruchomi się za każdym razem w każdym teście
    public void beforeEach(){
         fakerName = faker.name().name();
         fakerUserName = faker.name().username();
         fakerEmail = faker.internet().emailAddress();
         fakerStreet = faker.address().streetName();
    }

    @Test
    public  void jsonplaceholderUpdateUserPUTTest(){

        JSONObject geo = new JSONObject();
        geo.put("lat","-1111ddss-111");
        geo.put("lng","81.1496");

        JSONObject address = new JSONObject();
        address.put("street",fakerStreet);
        address.put("suite","Apt. 556");
        address.put("city","Warszawa");
        address.put("zipcode","92998-3874");
        address.put("geo", geo);

        JSONObject company = new JSONObject();
        address.put("name","Romaguera-Crona");
        address.put("catchPhrase","Multi-layered client-server neural-net");
        address.put("bs","arness real-time e-markets");

        JSONObject user = new JSONObject();
        user.put("name",fakerName);
        user.put("username",fakerUserName);
        user.put("email",fakerEmail);
        user.put("phone","511023990");
        user.put("website","hildegard.org");
        user.put("address",address);
        user.put("company",company);


        Response response = given()
                .contentType("application/json")
                .when()
                .body(user.toString())
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakerName, json.get("name"));
        assertEquals(fakerStreet, json.get("address.street"));
        assertEquals("-1111ddss-111", json.get("address.geo.lat"));
    }

    @Test
    public void jsonplaceholderUpdateUserPatchTest(){

        JSONObject address = new JSONObject();
        address.put("street", fakerStreet);

        JSONObject company = new JSONObject();
        company.put("name", fakerName);

        JSONObject userUpdate = new JSONObject();
        userUpdate.put("name", fakerName);
        userUpdate.put("address", address);
        userUpdate.put("company", company);

        Response response = given()
                .contentType("application/json")
                .when()
                .body(userUpdate.toString())
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakerName, json.get("name"));
        assertEquals(fakerStreet, json.get("address.street"));
        assertEquals(fakerName, json.get("company.name"));
    }
}