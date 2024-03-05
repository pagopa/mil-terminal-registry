package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(database = "mil", collection = "terminal")
public class TerminalEntity {

    @BsonId
    public String id;


    public String terminalUuid;

    public String terminalHandler;

    public String serviceProviderId;

    public Terminal terminal;
}
