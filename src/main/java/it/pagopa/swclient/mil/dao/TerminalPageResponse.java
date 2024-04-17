package it.pagopa.swclient.mil.dao;

import java.util.List;
@RegisterForReflection
public record TerminalPageResponse(List<TerminalEntity> terminals, PageMetadata page) {
}
