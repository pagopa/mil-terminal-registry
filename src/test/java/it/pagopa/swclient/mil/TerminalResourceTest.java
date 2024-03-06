package it.pagopa.swclient.mil;

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

}