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

public class BraceletTestHomework {
    private static RequestSpecification spec3;

    @BeforeAll
    public static void init() {
        spec3 = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
// .setProxy("gate.swissre.com", 9443)
                .setBaseUri("http://testing-school-api.herokuapp.com/api/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();

    }

    @Test
    public void testBracelets() {
        String braceletRequestBody = "{\"query\":\"query MedicalCards($id: String) {\\n  medicalCard(braceletId: $id) {\\n    guid\\n    active\\n    bracelet\\n    {\\n      braceletId\\n      guid\\n    }\\n    personalInfo {\\n      updatedOn\\n      firstname\\n      lastname\\n      personalcode\\n      phone\\n      dateofbirth\\n      age\\n      street\\n      flat\\n      height\\n      weight\\n    }\\n  }\\n}\\n\\nquery GetBracelets {\\n  bracelets {\\n    braceletId\\n    guid\\n    pin\\n\\n  }\\n}\",\"variables\":{\"id\":\"5b640e2a2109690025bf629a\"},\"operationName\":\"GetBracelets\"}";
        BraceletsResponse response = given()
                .spec(spec3)
                .urlEncodingEnabled(true)
                .body(braceletRequestBody)
                .when()
                .post("graphql")
                .then().statusCode(200)
                .and()
                .extract().as(BraceletsResponse.class);
        String braceletId = response.getData().getBracelets()[0].getGuid();
        String medicalCardRequestBody = "{\"query\":\"query MedicalCards($id: String) {\\n  medicalCard(braceletId: $id) {\\n    guid\\n    active\\n    bracelet\\n    {\\n      braceletId\\n      guid\\n    }\\n    personalInfo {\\n      updatedOn\\n      firstname\\n      lastname\\n      personalcode\\n      phone\\n      dateofbirth\\n      age\\n      street\\n      flat\\n      height\\n      weight\\n    }\\n  }\\n}\\n\\nquery GetBracelets {\\n  bracelets {\\n    braceletId\\n    guid\\n    pin\\n\\n  }\\n}\",\"variables\":{\"id\":\"" + braceletId + "\"},\"operationName\":\"MedicalCards\"}";
        MedicalCardResponse medCardResponse = given()
                .spec(spec3)
                .urlEncodingEnabled(true)
                .body(medicalCardRequestBody)
                .when()
                .post("graphql")
                .then().statusCode(200)
                .and()
                .extract().as(MedicalCardResponse.class);
        assertTrue(medCardResponse.getData().getMedicalCard().isActive());
    }

}
