package it.pagopa.swclient.mil;

import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteError;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.resource.TerminalResource;
import it.pagopa.swclient.mil.service.TerminalService;
import it.pagopa.swclient.mil.util.TerminalTestData;
import jakarta.ws.rs.WebApplicationException;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(TerminalResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TerminalResourceTest {

    @InjectMock
    static TerminalService terminalService;

    static TerminalDto terminalDto;

    static TerminalEntity terminalEntity;

    @BeforeAll
    static void createTestObjects() {
        terminalDto = TerminalTestData.getCorrectTerminalDto();
        terminalEntity = TerminalTestData.getCorrectTerminalEntity();
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"pos_service_provider"})
    @JwtSecurity(claims = {
            @Claim(key = "sub", value = "1234567890")
    })
    void testCreateTerminalEndpoint() {
        Mockito.when(terminalService.createTerminal(Mockito.any(String.class), Mockito.any(TerminalDto.class)))
                .thenReturn(Uni.createFrom().item(terminalEntity));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", "1a2b3c4d-5e6f-789a-bcde-f0123456789a")
                .and()
                .body(terminalDto)
                .when()
                .post("/")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"pos_service_provider"})
    @JwtSecurity(claims = {
            @Claim(key = "sub", value = "1234567890")
    })
    void testCreateTerminalError_409() {
        WriteError writeError = new WriteError(11000, "Duplicate key violation", new BsonDocument());
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);

        Mockito.when(terminalService.createTerminal(Mockito.any(String.class), Mockito.any(TerminalDto.class)))
                .thenReturn(Uni.createFrom().failure(new MongoWriteException(writeError, serverAddress)));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", "1a2b3c4d-5e6f-789a-bcde-f0123456789a")
                .and()
                .body(terminalDto)
                .when()
                .post("/")
                .then()
                .extract().response();

        Assertions.assertEquals(409, response.statusCode());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"pos_service_provider"})
    @JwtSecurity(claims = {
            @Claim(key = "sub", value = "1234567890")
    })
    void testCreateTerminalError_500() {
        Mockito.when(terminalService.createTerminal(Mockito.any(String.class), Mockito.any(TerminalDto.class)))
                .thenReturn(Uni.createFrom().failure(new WebApplicationException()));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", "1a2b3c4d-5e6f-789a-bcde-f0123456789a")
                .and()
                .body(terminalDto)
                .when()
                .post("/")
                .then()
                .extract().response();

        Assertions.assertEquals(500, response.statusCode());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"pos_service_provider"})
    @JwtSecurity(claims = {
            @Claim(key = "sub", value = "1234567890")
    })
    void testCreateTerminalError_OtherCode() {
        WriteError writeError = new WriteError(12345, "Some other error", new BsonDocument());
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);

        Mockito.when(terminalService.createTerminal(Mockito.any(String.class), Mockito.any(TerminalDto.class)))
                .thenReturn(Uni.createFrom().failure(new MongoWriteException(writeError, serverAddress)));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", "1a2b3c4d-5e6f-789a-bcde-f0123456789a")
                .and()
                .body(terminalDto)
                .when()
                .post("/")
                .then()
                .extract().response();

        Assertions.assertEquals(500, response.statusCode());
    }

}