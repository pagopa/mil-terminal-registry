package it.pagopa.swclient.mil.controller.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class TerminalDtoTest {

  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void givenATerminalThenValidationOk() {
    var terminal = new TerminalDto("45856", "34523860", true,
        "RSSMRA85T10A562S", true, false, null, true);

    var validationErrors = validator.validate(terminal);

    assertTrue(validationErrors.isEmpty());
  }
}