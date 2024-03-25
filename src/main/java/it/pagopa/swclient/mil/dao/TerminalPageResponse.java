package it.pagopa.swclient.mil.dao;

import java.util.List;

public record TerminalPageResponse(List<TerminalEntity> terminals, PageMetadata page) {
}
