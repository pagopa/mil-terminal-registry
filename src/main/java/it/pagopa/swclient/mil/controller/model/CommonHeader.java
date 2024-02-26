package it.pagopa.swclient.mil.controller.model;

import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.HeaderParam;

public record CommonHeader(@HeaderParam("RequestId")
                           @NotNull(message = "[000000001] RequestId must not be null")
                           @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN,
                                   message = "[000000002] RequestId must match \"{regexp}\"")
                           String requestId,
                           @HeaderParam("Authorization")
                           @NotNull(message = "[000000001] Authorization must not be null")
                           String authorization) {
}
