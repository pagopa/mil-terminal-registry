package it.pagopa.swclient.mil.controller.model;


import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PagoPaConf(@NotNull
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String pspId,
                         @NotNull
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String brokerId,
                         @NotNull
                         @Pattern(regexp = RegexPatterns.GENERIC_ID_PATTERN)
                         String channelId) {

}
