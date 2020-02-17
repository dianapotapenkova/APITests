import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestReqres {

    private static RequestSpecification spec;

    @BeforeAll
    public static void init() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                // .setProxy("gate.swissre.com", 9443)
                .setBaseUri("https://reqres.in/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void twoTotalPagesTest() {
        ReqresResponse response = given()
                .spec(spec)
                .when()
                .get("api/unknown")
                .then()
                .statusCode(200)
                .extract().as(ReqresResponse.class);
        assertTrue(response.getTotal_pages() >= 2);
    }

    @Test
    public void trueRedNameTest() {
        ReqresResponse response = given()
                .spec(spec)
                .when()
                .get("api/unknown")
                .then()
                .statusCode(200)
                .extract().as(ReqresResponse.class);
        Color[] data = response.getData();
        int count = 0;
        for (Color color :
                data) {
            if (color.getName().equals("true red")) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void loginUnsuccessfulTest() {
        ReqresRequest request = new ReqresRequest();
        request.setEmail("peter@klaven");
        UnsuccessfulLoginResponse response = given()
                .spec(spec)
                .body(request)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .extract().as(UnsuccessfulLoginResponse.class);
        assertEquals("Missing password", response.getError());
    }
}
