package it.pagopa.swclient.mil.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TerminalService {

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public Uni<TerminalEntity> createTerminal(String serviceProviderId, TerminalDto terminalDto) {

        Log.debugf("TerminalService -> createTerminal - Input parameters: %s", terminalDto);

        String terminalUuid = Utility.generateTerminalUuid();

        TerminalEntity entity = createTerminalEntity(terminalDto, serviceProviderId, terminalUuid);

        return terminalRepository.persist(entity)
                .onFailure().transform(error -> error)
                .onItem().transform(terminalSaved -> terminalSaved);
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
