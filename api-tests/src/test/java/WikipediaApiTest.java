import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WikipediaApiTest {

    @Test
    public void searchForAppium() {
        RestAssured.baseURI = "https://en.wikipedia.org/w/api.php";

        given()
            .queryParam("action", "query")
            .queryParam("list", "search")
            .queryParam("srsearch", "Appium")
            .queryParam("format", "json")
        .when()
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("query.search[0].title", containsString("Appium"));
    }
}
