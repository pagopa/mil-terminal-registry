package it.pagopa.swclient.mil.dao;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TerminalRepository implements ReactivePanacheMongoRepositoryBase<TerminalEntity, String> {
}
