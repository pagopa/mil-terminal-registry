package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity {

    @BsonId
    @BsonProperty("terminalUuid")
    private String terminalUuid;

    @BsonProperty("terminalHandler")
    private String terminalHandler;

    @BsonProperty("serviceProviderId")
    private String serviceProviderId;

    private Terminal terminal;
}
