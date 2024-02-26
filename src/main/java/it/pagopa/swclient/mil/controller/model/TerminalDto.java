package it.pagopa.swclient.mil.controller.model;


import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TerminalDto(@NotNull
                          @Pattern(regexp = RegexPatterns.MAX_FIVE_NUM_PATTERN)
                          String terminalHandlerId,
                          @NotNull
                          @Pattern(regexp = RegexPatterns.MAX_EIGHT_NUM_PATTERN)
                          String terminalId,
                          @NotNull
                          Boolean enabled,
                          @NotNull
                          @Pattern(regexp = RegexPatterns.ALPHANUMERIC_UPPERCASE_LIMITED_PATTERN)
                          String payeeCode,
                          @NotNull
                          Boolean slave,
                          @NotNull
                          Boolean pagoPa,
                          PagoPaConf pagoPaConf,
                          @NotNull
                          Boolean idpay) {

}
