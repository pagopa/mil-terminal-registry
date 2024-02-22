package it.pagopa.swclient.mil.controller.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class PagoPaConfTest {

  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void givenPagoPaConfThenValidationOk() {
    var config = new PagoPaConf("AGID_01", "97735020584", "97735020584_03");

    var validationErrors = validator.validate(config);

    assertTrue(validationErrors.isEmpty());
  }
}