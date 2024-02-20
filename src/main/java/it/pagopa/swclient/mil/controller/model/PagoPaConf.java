package it.pagopa.swclient.mil.controller.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PagoPaConf(@NotNull
                           @Pattern(regexp = "^[\\u0001-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFFF]{1,35}$")
                           // fixme ^[\u0001-\uD7FF\uE000-\uFFFD\u1000-\u10FF]{1,35}$ verificare che la regex sia corretta
                           String pspId,
                         @NotNull
                           @Pattern(regexp = "^[\\u0001-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFFF]{1,35}$")
                           // fixme ^[\u0001-\uD7FF\uE000-\uFFFD\u1000-\u10FF]{1,35}$ verificare che la regex sia corretta
                           String brokerId,
                         @NotNull
                           @Pattern(regexp = "^[\\u0001-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFFF]{1,35}$")
                           // fixme ^[\u0001-\uD7FF\uE000-\uFFFD\u1000-\u10FF]{1,35}$ verificare che la regex sia corretta
                           String channelId) {

}
