package it.pagopa.swclient.mil.dao;

import org.bson.codecs.pojo.annotations.BsonProperty;

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

    public TerminalEntity(@BsonProperty("terminalUuid") String terminalUuid, 
        @BsonProperty("terminalHandler") String terminalHandler,
        @BsonProperty("serviceProviderId") String serviceProviderId,
        @BsonProperty("terminal") Terminal terminal ){
            this.terminalUuid = terminalUuid;
            this.terminalHandler = terminalHandler;
            this.serviceProviderId = serviceProviderId;
            this.terminal = terminal;
        }
}
