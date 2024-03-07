package it.pagopa.swclient.mil;

import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteError;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.TerminalTestData;
import jakarta.ws.rs.WebApplicationException;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

@QuarkusTest
class TerminalResourceTest {

    @InjectMock
    TerminalRepository terminalRepository;

    static CommonHeader commonHeader;

    static TerminalDto terminalDto;

    static TerminalEntity terminalEntity;

    @BeforeAll
    static void createTestObjects() {
        commonHeader = TerminalTestData.getCorrectCommonHeader();
        terminalDto = TerminalTestData.getCorrectTerminalDto();
        terminalEntity = TerminalTestData.getCorrectTerminalEntity();
    }

    @Test
    void testCreateTerminalEndpoint() {
        Mockito.when(terminalRepository.persist(Mockito.any(TerminalEntity.class))).thenReturn(Uni.createFrom().item(terminalEntity));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", commonHeader.requestId())
                .header("Authorization", commonHeader.authorization())
                .and()
                .body(terminalDto)
                .when()
                .post("/terminals")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    void testCreateTerminalError_409() {
        WriteError writeError = new WriteError(11000, "Duplicate key violation", new BsonDocument());
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);

        Mockito.when(terminalRepository.persist(Mockito.any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().failure(new MongoWriteException(writeError, serverAddress)));

        // Effettua la richiesta API
        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", commonHeader.requestId())
                .header("Authorization", commonHeader.authorization())
                .and()
                .body(terminalDto)
                .when()
                .post("/terminals")
                .then()
                .extract().response();

        // Verifica che lo stato di risposta sia 409
        Assertions.assertEquals(409, response.statusCode());
    }

    @Test
    void testCreateTerminalError_500() {
        Mockito.when(terminalRepository.persist(Mockito.any(TerminalEntity.class))).thenReturn(Uni.createFrom().failure(new WebApplicationException()));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("RequestId", commonHeader.requestId())
                .header("Authorization", commonHeader.authorization())
                .and()
                .body(terminalDto)
                .when()
                .post("/terminals")
                .then()
                .extract().response();

        Assertions.assertEquals(500, response.statusCode());
    }

}