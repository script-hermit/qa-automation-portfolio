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
        String requestParams = "action=query&list=search&srsearch=" + term + "&format=json";
        attachRequestToAllure("GET", RestAssured.baseURI, requestParams);

        try {
            String response =
                given()
                    .queryParam("action", "query")
                    .queryParam("list", "search")
                    .queryParam("srsearch", term)
                    .queryParam("format", "json")
                    .log().ifValidationFails()
                .when()
                    .get()
                .then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("query.search[0].title", containsString(term))
                    .extract().asString();

            attachResponseToAllure(response);
        } catch (AssertionError | Exception e) {
            attachErrorToAllure(e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify Wikipedia API returns no results for gibberish search term")
    @Description("Searches Wikipedia API for a gibberish term and verifies that no results are returned.")
    public void searchForNoResults() {
        RestAssured.baseURI = "https://en.wikipedia.org/w/api.php";
        String gibberish = "asdkfjhasdkjfhakjsdhf";
        String requestParams = "action=query&list=search&srsearch=" + gibberish + "&format=json";
        attachRequestToAllure("GET", RestAssured.baseURI, requestParams);

        try {
            String response =
                given()
                    .queryParam("action", "query")
                    .queryParam("list", "search")
                    .queryParam("srsearch", gibberish)
                    .queryParam("format", "json")
                    .log().ifValidationFails()
                .when()
                    .get()
                .then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("query.search.size()", equalTo(0))
                    .extract().asString();

            attachResponseToAllure(response);
        } catch (AssertionError | Exception e) {
            attachErrorToAllure(e.getMessage());
            throw e;
        }
    }

    @Attachment(value = "API Request", type = "text/plain")
    public String attachRequestToAllure(String method, String url, String params) {
        return "Method: " + method + "\nURL: " + url + "\nParams: " + params;
    }

    @Attachment(value = "API Response", type = "application/json")
    public String attachResponseToAllure(String response) {
        return response;
    }

    @Attachment(value = "API Error", type = "text/plain")
    public String attachErrorToAllure(String error) {
        return "Error: " + error;
    }
}
