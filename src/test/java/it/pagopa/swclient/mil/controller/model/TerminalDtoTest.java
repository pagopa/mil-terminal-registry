package it.pagopa.swclient.mil.controller.model;

import static it.pagopa.swclient.mil.util.UtilsValidator.validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerminalDtoTest {

  @Test
  void givenATerminalThenValidationOk() {
    var terminal = new TerminalDto("45856", "34523860", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertTrue(validationErrors.isEmpty());
  }

  @Test
  void givenNullTerminalHandlerThenValidationKo() {
    var terminal = new TerminalDto(null, "34523860", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("terminalHandlerId")));
  }

  @Test
  void givenInvalidTerminalHandlerThenValidationKo() {
    var terminal = new TerminalDto("458356", "34523860", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("terminalHandlerId")));
  }

  @Test
  void givenNullTerminalThenValidationKo() {
    var terminal = new TerminalDto("45835", null, true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("terminalId")));
  }

  @Test
  void givenInvalidTerminalThenValidationKo() {
    var terminal = new TerminalDto("45836", "34523860ABC", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("terminalId")));
  }

  @Test
  void givenNullEnabledThenValidationKo() {
    var terminal = new TerminalDto("45835", "34523860", null,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("enabled")));
  }

  @Test
  void givenNullPayeeCodeThenValidationKo() {
    var terminal = new TerminalDto("45856", "34523860", true,
            "InvalidPayeeCodeInvalidPayeeCodeInvalidPayeeCode", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("payeeCode")));
  }

  @Test
  void givenInvalidPayeeCodeThenValidationKo() {
    var terminal = new TerminalDto("45836", "34523860", true,
            "invalidPayee", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("payeeCode")));
  }

  @Test
  void givenNullSlaveThenValidationKo() {
    var terminal = new TerminalDto("45835", "34523860", true,
            "RSSMRA85T10A562S", null, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("slave")));
  }

  @Test
  void givenNullPagoPaThenValidationKo() {
    var terminal = new TerminalDto("45835", "34523860", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), null, null, true);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("pagoPa")));
  }

  @Test
  void givenNullIdPayThenValidationKo() {
    var terminal = new TerminalDto("45856", "34523860", true,
            "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, null);

    var validationErrors = validator().validate(terminal);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("idpay")));
  }
}