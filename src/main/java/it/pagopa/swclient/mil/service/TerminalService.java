package it.pagopa.swclient.mil.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoWriteException;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.Errors;
import it.pagopa.swclient.mil.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TerminalService {

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public Uni<Response> createTerminal(CommonHeader headers, TerminalDto terminalDto) throws JsonProcessingException {

        Log.debugf("TerminalService -> createTerminal - Input parameters: %s, %s", headers, terminalDto);

        String serviceProviderId = Utility.decodeBearerPayload(headers);
        String terminalUuid = Utility.generateTerminalUuid();

        TerminalEntity entity = createTerminalEntity(terminalDto, serviceProviderId, terminalUuid);

        return terminalRepository.persist(entity)
                .onFailure().transform(err -> {
                    if (err instanceof MongoWriteException mongoWriteException && (mongoWriteException.getCode() == 11000)) {
                        Log.errorf(err, " TerminalService -> createTerminal: duplicate key violation for terminalId [%s] and terminalHandlerId [%s]", terminalDto.terminalId(), terminalDto.terminalHandlerId());

                        return new WebApplicationException(Response
                                .status(Response.Status.CONFLICT)
                                .entity(new Errors(ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB, ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB_MSG))
                                .build());

                    } else {
                        Log.errorf(err, " TerminalService -> createTerminal: unexpected error during persist for terminal [%s]", entity);

                        return new InternalServerErrorException(Response
                                .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                                .build());
                    }
                })
                .onItem().transform(terminalSaved -> {
                    Log.debugf(" TerminalService -> createTerminal: terminal saved correctly on DB [%s]", terminalSaved);

                    return Response.status(Response.Status.CREATED).build();
                });
    }

    private TerminalEntity createTerminalEntity(TerminalDto terminalDto, String serviceProviderId, String terminalUuid) {
        Log.debugf("TerminalService -> createTerminalEntity: storing terminal [%s] on DB", terminalDto);

        Terminal terminal = Terminal.builder()
                .terminalId(terminalDto.terminalId())
                .enabled(terminalDto.enabled())
                .payeeCode(terminalDto.payeeCode())
                .slave(terminalDto.slave())
                .pagoPaConf(terminalDto.pagoPaConf())
                .idpay(terminalDto.idpay())
                .build();

        return TerminalEntity.builder()
                .terminalUuid(terminalUuid)
                .terminalHandler(terminalDto.terminalHandlerId())
                .serviceProviderId(serviceProviderId)
                .terminal(terminal)
                .build();
    }
}
