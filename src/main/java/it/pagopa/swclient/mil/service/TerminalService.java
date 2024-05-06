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

    /**
     * Find first terminal equals to serviceProviderId and terminalUuid given in input.
     *
     * @param serviceProviderId service provider id
     * @param terminalUuid      uuid of terminal
     * @return terminal founded
     */
    public Uni<TerminalEntity> findTerminal(String serviceProviderId, String terminalUuid) {

        return terminalRepository
                .find("serviceProviderId = ?1 and terminalUuid = ?2", serviceProviderId, terminalUuid)
                .firstResult();
    }

    /**
     * Update terminal starting from a terminalDto.
     *
     * @param terminalDto       dto of modified terminal
     * @param terminalUuid      terminalUuid of old terminal to be modified
     * @param serviceProviderId service provider id
     * @return terminal updated
     */
    public Uni<TerminalEntity> updateTerminal(String terminalUuid, String serviceProviderId, TerminalDto terminalDto, TerminalEntity oldTerminal) {

        TerminalEntity entity = createTerminalEntity(terminalDto, serviceProviderId, terminalUuid);
        entity.id = oldTerminal.id;

        return terminalRepository.update(entity)
                .onFailure()
                .transform(error -> error)
                .onItem()
                .transform(terminalUpdated -> terminalUpdated);
    }

    private TerminalEntity createTerminalEntity(TerminalDto terminalDto, String serviceProviderId, String terminalUuid) {
        Log.debugf("TerminalService -> createTerminalEntity: storing terminal [%s] on DB", terminalDto);

        Terminal terminal = new Terminal();
        terminal.setTerminalId(terminalDto.terminalId());
        terminal.setEnabled(terminalDto.enabled());
        terminal.setPayeeCode(terminalDto.payeeCode());
        terminal.setSlave(terminalDto.slave());
        terminal.setPagoPaConf(terminalDto.pagoPaConf());
        terminal.setIdpay(terminalDto.idpay());
        terminal.setWorkstations(terminalDto.workstations());

        TerminalEntity terminalEntity = new TerminalEntity();
        terminalEntity.setTerminalUuid(terminalUuid);
        terminalEntity.setTerminalHandler(terminalDto.terminalHandlerId());
        terminalEntity.setServiceProviderId(serviceProviderId);
        terminalEntity.setTerminal(terminal);

        return terminalEntity;
    }

    /**
     * Update terminal starting from a terminalDto.
     *
     * @param terminal      terminal to be deleted
     * @return void
     */
    public Uni<Void> deleteTerminal(TerminalEntity terminal) {

        return terminalRepository.delete(terminal)
                .onFailure()
                .transform(error -> error)
                .onItem()
                .transform(terminalDeleted -> terminalDeleted);
    }
}
