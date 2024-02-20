package it.pagopa.swclient.mil.controller.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TerminalDto(@NotNull
                          @Pattern(regexp = "^\\d{5}$")
                          String terminalHandlerId,
                          @NotNull
                          @Pattern(regexp = "^\\d{8}$")
                          String terminalId,
                          @NotNull
                          Boolean enabled,
                          @NotNull
                          @Pattern(regexp = "^[A-Z0-9]{1,16}$")
                          String payeeCode,
                          @NotNull
                          Boolean slave,
                          @NotNull
                          Boolean pagoPa,
                          PagoPaConf pagoPaConf,
                          @NotNull
                          Boolean idpay) {

}
