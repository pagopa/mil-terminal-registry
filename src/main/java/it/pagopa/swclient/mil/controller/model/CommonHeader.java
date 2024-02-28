package it.pagopa.swclient.mil.controller.model;

import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.HeaderParam;

public record CommonHeader(@HeaderParam("RequestId")
                           @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG)
                           @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN)
                           String requestId,
                           @HeaderParam("Authorization")
                           @NotNull(message = ErrorCodes.ERROR_AUTHORIZATION_MUST_NOT_BE_NULL_MSG)
                           String authorization) {
}
