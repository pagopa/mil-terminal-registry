package it.pagopa.swclient.mil.dao;



import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

@Builder
@Getter
@ToString
@Data
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity{

    public String terminalUuid;

    public String terminalHandler;

    public String serviceProviderId;

    public Terminal terminal;
}
