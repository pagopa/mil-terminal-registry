package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

@ToString
@Data
@Getter
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity {

    private String terminalUuid;

    private String terminalHandler;

    private String serviceProviderId;

    private Terminal terminal;

    public TerminalEntity() {
    }

    public TerminalEntity(String terminalUuid, String terminalHandler, String serviceProviderId, Terminal terminal) {
        this.terminalUuid = terminalUuid;
        this.terminalHandler = terminalHandler;
        this.serviceProviderId = serviceProviderId;
        this.terminal = terminal;
    }
}
