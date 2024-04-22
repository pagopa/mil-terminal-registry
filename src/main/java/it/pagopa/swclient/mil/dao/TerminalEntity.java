package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity extends PanacheMongoEntity {

    private String terminalUuid;

    private String terminalHandler;

    private String serviceProviderId;

    private Terminal terminal;
}
