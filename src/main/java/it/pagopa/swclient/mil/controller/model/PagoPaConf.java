package it.pagopa.swclient.mil.controller.model;


import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PagoPaConf(@NotNull(message = ErrorCodes.ERROR_PSPID_MUST_NOT_BE_NULL_MSG)
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String pspId,
                         @NotNull(message = ErrorCodes.ERROR_BROKERID_MUST_NOT_BE_NULL_MSG)
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String brokerId,
                         @NotNull(message = ErrorCodes.ERROR_CHANNELID_MUST_NOT_BE_NULL_MSG)
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String channelId) {

}
