package it.pagopa.swclient.mil.controller.model;


import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record TerminalDto(@NotNull(message = ErrorCodes.ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL_MSG)
                          @Pattern(regexp = RegexPatterns.MAX_FIVE_NUM_PATTERN)
                          String terminalHandlerId,
                          @NotNull(message = ErrorCodes.ERROR_TERMINALID_MUST_NOT_BE_NULL_MSG)
                          @Pattern(regexp = RegexPatterns.MAX_EIGHT_NUM_PATTERN)
                          String terminalId,
                          @NotNull(message = ErrorCodes.ERROR_ENABLED_MUST_NOT_BE_NULL_MSG)
                          Boolean enabled,
                          @NotNull(message = ErrorCodes.ERROR_PAYEECODE_MUST_NOT_BE_NULL_MSG)
                          @Pattern(regexp = RegexPatterns.ALPHANUMERIC_UPPERCASE_LIMITED_PATTERN)
                          String payeeCode,
                          @NotNull(message = ErrorCodes.ERROR_SLAVE_MUST_NOT_BE_NULL_MSG)
                          Boolean slave,
                          List<String> workstations,
                          @NotNull(message = ErrorCodes.ERROR_PAGOPA_MUST_NOT_BE_NULL_MSG)
                          Boolean pagoPa,
                          PagoPaConf pagoPaConf,
                          @NotNull(message = ErrorCodes.ERROR_IDPAY_MUST_NOT_BE_NULL_MSG)
                          Boolean idpay) {

}
