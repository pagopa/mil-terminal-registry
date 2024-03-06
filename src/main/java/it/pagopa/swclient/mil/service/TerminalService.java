package it.pagopa.swclient.mil.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.InternalServerErrorException;
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
                    Log.errorf(err, "TerminalService -> createTerminal: Error while persist terminal on db [%s]", entity);

                    return new InternalServerErrorException(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .build());
                })
                .onItem().transform(terminalSaved -> {
                    Log.debugf("TerminalService -> createTerminal: terminal saved: [%s]", terminalSaved);

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
