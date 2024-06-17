import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class RegressApiTests extends TestBase {
    @Test
    @DisplayName("Successful registration")
    void registrationSuccessfulTest() {
        var requestBody = "{\"email\": \"rachel.howell@reqres.in\", \"password\": \"qwerty\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Incorrect user registration")
    void registrationIncorrectUserTest() {
        var requestBody = "{\"email\": \"fake@fake.in\", \"password\": \"qwerty\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("Registration without password")
    void registrationUserWithOutPasswordTest() {
        var requestBody = "{\"email\": \"rachel.howell@reqres.in\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("List of users contains six users")
    void listOfUsersContainsSixUsersTest() {
        given()
                .log().uri()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.size()", is(6));
    }

    @Test
    @DisplayName("Get single user successful")
    void getSingleResourceSuccessfulTest() {
        given()
                .log().uri()
                .when()
                .get("/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data", notNullValue());
    }
}
