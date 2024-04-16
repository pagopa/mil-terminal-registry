package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

@ToString
@Data
@Builder
@Getter
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity extends PanacheMongoEntity{

    private String terminalUuid;

    private String terminalHandler;

    private String serviceProviderId;

    private Terminal terminal;
}
