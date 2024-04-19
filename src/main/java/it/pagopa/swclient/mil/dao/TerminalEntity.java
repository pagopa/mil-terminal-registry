package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity {

    @BsonProperty("_id")
    @BsonId
    private ObjectId id;

    @BsonProperty("terminalUuid")
    private String terminalUuid;

    @BsonProperty("terminalHandler")
    private String terminalHandler;

    @BsonProperty("serviceProviderId")
    private String serviceProviderId;

    private Terminal terminal;
}
