import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.qameta.allure.Description;
import io.qameta.allure.Attachment;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WikipediaApiTest {

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
            {"Appium"},
            {"TestNG"},
            {"Selenium"}
        };
    }

    @Test(dataProvider = "searchTerms", description = "Verify Wikipedia API returns results for various search terms")
    @Description("Searches Wikipedia API for each provided term and verifies that results are returned.")
    public void searchForTerm(String term) {
        RestAssured.baseURI = "https://en.wikipedia.org/w/api.php";

        String response =
            given()
                .queryParam("action", "query")
                .queryParam("list", "search")
                .queryParam("srsearch", term)
                .queryParam("format", "json")
                .log().all()
            .when()
                .get()
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("query.search[0].title", containsString(term))
                .extract().asString();

        attachResponseToAllure(response);
    }

    @Test(description = "Verify Wikipedia API returns no results for gibberish search term")
    @Description("Searches Wikipedia API for a gibberish term and verifies that no results are returned.")
    public void searchForNoResults() {
        RestAssured.baseURI = "https://en.wikipedia.org/w/api.php";

        String response =
            given()
                .queryParam("action", "query")
                .queryParam("list", "search")
                .queryParam("srsearch", "asdkfjhasdkjfhakjsdhf")
                .queryParam("format", "json")
                .log().all()
            .when()
                .get()
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("query.search.size()", equalTo(0))
                .extract().asString();

        attachResponseToAllure(response);
    }

    @Attachment(value = "API Response", type = "application/json")
    public String attachResponseToAllure(String response) {
        return response;
    }
}
