package it.pagopa.swclient.mil;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TerminalResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/terminals")
          .then()
             .statusCode(200)
             .body(is("Hello from Italy"));
    }

}