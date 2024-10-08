package it.pagopa.swclient.mil.service;

import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteError;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.TerminalTestData;
import jakarta.ws.rs.WebApplicationException;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TerminalServiceTest {

    @InjectMock
    static TerminalRepository terminalRepository;

    static TerminalDto terminalDto;

    static TerminalEntity terminalEntity;

    static TerminalService terminalService;

    @BeforeAll
    static void createTestObjects() {
        terminalDto = TerminalTestData.getCorrectTerminalDto();
        terminalEntity = TerminalTestData.getCorrectTerminalEntity();
        terminalService = new TerminalService(terminalRepository);
    }

    @Test
    void testCreateTerminal_Success() {
        Mockito.when(terminalRepository.persist(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().item(terminalEntity));

        Uni<TerminalEntity> result = terminalService.createTerminal("1234567890", terminalDto);

        result.subscribe()
                .with(entity -> Assertions.assertEquals(terminalEntity, entity));
    }

    @Test
    void testCreateTerminal_Failure() {
        WriteError writeError = new WriteError(11000, "Duplicate key violation", new BsonDocument());
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);

        Mockito.when(terminalRepository.persist(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().failure(new MongoWriteException(writeError, serverAddress)));

        Uni<TerminalEntity> result = terminalService.createTerminal("1234567890", terminalDto);

        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(MongoWriteException.class);
    }

    @Test
    void whenGetTerminalListThenSuccess() {
        ReactivePanacheQuery<TerminalEntity> query = Mockito.mock(ReactivePanacheQuery.class);
        Mockito.when(query.page(anyInt(), anyInt())).thenReturn(query);
        Mockito.when(query.list()).thenReturn(Uni.createFrom().item(mockedList()));
        Mockito.when(terminalRepository.find("serviceProviderId", "serviceProviderId")).thenReturn(query);

        Uni<List<TerminalEntity>> result = terminalService.getTerminalListPaged("serviceProviderId", 0, 10);

        result.subscribe()
                .with(list -> Assertions.assertEquals(mockedList(), list));
    }

    @Test
    void whenGetTerminalCountThenSuccess() {
        Mockito.when(terminalRepository.count("serviceProviderId", "serviceProviderId"))
                .thenReturn(Uni.createFrom().item(10L));

        var terminalCount = terminalService.getTerminalCount("serviceProviderId");

        terminalCount
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertItem(10L);
    }
  
    @Test
    void testFindTerminal_Success() {
        ReactivePanacheQuery<TerminalEntity> query = Mockito.mock(ReactivePanacheQuery.class);
        Mockito.when(query.firstResult()).thenReturn(Uni.createFrom().item(terminalEntity));
        Mockito.when(terminalRepository.find("serviceProviderId = ?1 and terminalUuid = ?2", "serviceProviderId", "terminalUuid")).thenReturn(query);

        Uni<TerminalEntity> terminalEntityUni = terminalService.findTerminal("serviceProviderId", "terminalUuid");

        terminalEntityUni
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertItem(terminalEntity);
    }

    @Test
    void testUpdateTerminal_Success() {
        Mockito.when(terminalRepository.update(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().item(terminalEntity));

        Uni<TerminalEntity> result = terminalService.updateTerminal("terminalUuid", "serviceProviderId", terminalDto, terminalEntity);

        result.subscribe()
                .with(entity -> Assertions.assertEquals(terminalEntity, entity));
    }

    @Test
    void testUpdateTerminal_Failure() {
        Mockito.when(terminalRepository.update(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().failure(new WebApplicationException()));

        Uni<TerminalEntity> result = terminalService.updateTerminal("terminalUuid", "serviceProviderId", terminalDto, terminalEntity);

        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(WebApplicationException.class);
    }

    @Test
    void testDeleteTerminal_Success() {
        Mockito.when(terminalRepository.delete(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = terminalService.deleteTerminal(terminalEntity);

        result.subscribe()
                .with(Assertions::assertNull);
    }

    @Test
    void testDeleteTerminal_Failure() {
        Mockito.when(terminalRepository.delete(any(TerminalEntity.class)))
                .thenReturn(Uni.createFrom().failure(new WebApplicationException()));

        Uni<Void> result = terminalService.deleteTerminal(terminalEntity);

        result.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(WebApplicationException.class);
    }

    private List<TerminalEntity> mockedList() {
        TerminalEntity te1 = new TerminalEntity();
        te1.setTerminalUuid("uuid1");
        TerminalEntity te2 = new TerminalEntity();
        te2.setTerminalUuid("uuid2");

        return List.of(te1, te2);
    }
}
