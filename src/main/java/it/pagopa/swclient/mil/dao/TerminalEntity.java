package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@EqualsAndHashCode
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity {

    @BsonProperty("_id")
    @BsonId
    public ObjectId id;

    @BsonProperty("terminalUuid")
    public String terminalUuid;

    @BsonProperty("terminalHandler")
    public String terminalHandler;

    @BsonProperty("serviceProviderId")
    public String serviceProviderId;

    public Terminal terminal;
}
