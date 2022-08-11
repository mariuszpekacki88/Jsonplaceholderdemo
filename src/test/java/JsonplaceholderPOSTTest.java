import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPOSTTest {
    @Test
    public void jsonplaceholderCreateNewUser(){

        String jsonBody = " {\n" +
                "        \"name\": \"Mariusz Mariusz\",\n" +
                "        \"username\": \"Bret\",\n" +
                "        \"email\": \"Mariusz@april.biz\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Celulozy\",\n" +
                "            \"suite\": \"Apt. 556\",\n" +
                "            \"city\": \"Warszawa\",\n" +
                "            \"zipcode\": \"92998-3874\",\n" +
                "            \"geo\": {\n" +
                "                \"lat\": \"-1111-111\",\n" +
                "                \"lng\": \"81.1496\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"phone\": \"511023990\",\n" +
                "        \"website\": \"hildegard.org\",\n" +
                "        \"company\": {\n" +
                "            \"name\": \"Romaguera-Crona\",\n" +
                "            \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "            \"bs\": \"harness real-time e-markets\"\n" +
                "        }\n" +
                "    }";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Mariusz Mariusz", json.get("name"));
        assertEquals("Celulozy", json.get("address.street"));
        assertEquals("-1111-111", json.get("address.geo.lat"));
    }
}
