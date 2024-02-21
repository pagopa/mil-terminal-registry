package it.pagopa.swclient.mil.controller.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PagoPaConf(@NotNull
                         @Pattern(regexp = "^[\\x{0001}-\\x{D7FF}\\x{E000}-\\x{FFFD}\\x{10000}-\\x{10FFFF}]{1,35}$")
                         String pspId,
                         @NotNull
                         @Pattern(regexp = "^[\\x{0001}-\\x{D7FF}\\x{E000}-\\x{FFFD}\\x{10000}-\\x{10FFFF}]{1,35}$")
                         String brokerId,
                         @NotNull
                         @Pattern(regexp = "^[\\x{0001}-\\x{D7FF}\\x{E000}-\\x{FFFD}\\x{10000}-\\x{10FFFF}]{1,35}$")
                         String channelId) {

}
