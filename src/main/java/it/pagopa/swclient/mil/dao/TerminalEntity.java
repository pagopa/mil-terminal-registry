package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalEntity {

    @BsonProperty("terminalUuid")
    public String terminalUuid;

    @BsonProperty("terminalHandler")
    public String terminalHandler;

    @BsonProperty("serviceProviderId")
    public String serviceProviderId;

    public Terminal terminal;
}
