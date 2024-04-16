package it.pagopa.swclient.mil.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;
import it.pagopa.swclient.mil.dao.TerminalRepository;
import it.pagopa.swclient.mil.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TerminalService {

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }


    /**
     * Create a new terminal starting from a terminalDto.
     *
     * @param terminalDto       dto of terminal to be generated
     * @param serviceProviderId service provider id
     * @return terminal created
     */
    public Uni<TerminalEntity> createTerminal(String serviceProviderId, TerminalDto terminalDto) {

        Log.debugf("TerminalService -> createTerminal - Input parameters: %s", terminalDto);

        String terminalUuid = Utility.generateTerminalUuid();

        TerminalEntity entity = createTerminalEntity(terminalDto, serviceProviderId, terminalUuid);

        return terminalRepository.persist(entity)
                .onFailure()
                .transform(error -> error)
                .onItem()
                .transform(terminalSaved -> terminalSaved);
    }

    /**
     * Returns a list of terminals paginated. The query filters on serviceProviderId.
     *
     * @param pageIndex 0-based page index
     * @param pageSize  page size
     * @return a list of terminals
     */
    public Uni<List<TerminalEntity>> getTerminalListPaged(String serviceProviderId, int pageIndex, int pageSize) {

        return terminalRepository
                .find("serviceProviderId", serviceProviderId)
                .page(pageIndex, pageSize)
                .list();
    }

    public Uni<Long> getTerminalCount(String serviceProviderId) {

        return terminalRepository
                .count("serviceProviderId", serviceProviderId);
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

        return new TerminalEntity(terminalUuid, terminalDto.terminalHandlerId(),serviceProviderId, terminal);
    }
}
