package it.pagopa.swclient.mil.dao;



import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity{

    @BsonId
    public String terminalUuid;

    public String terminalHandler;

    public String serviceProviderId;

    public Terminal terminal;
}
