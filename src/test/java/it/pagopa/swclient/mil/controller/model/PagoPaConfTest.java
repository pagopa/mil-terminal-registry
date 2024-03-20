package it.pagopa.swclient.mil.controller.model;

import org.junit.jupiter.api.Test;

import static it.pagopa.swclient.mil.util.UtilsValidator.validator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PagoPaConfTest {

  @Test
  void givenValidPagoPaConfThenValidationPasses() {
    var config = new PagoPaConf("AGID_01", "97735020584", "97735020584_03");

    var validationErrors = validator().validate(config);

    assertTrue(validationErrors.isEmpty());
  }

  @Test
  void givenNullPspIdThenValidationKo() {
    var config = new PagoPaConf(null, "97735020584", "97735020584_03");

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("pspId")));
  }

  @Test
  void givenInvalidPspIdThenValidationKo() {
    var config = new PagoPaConf("InvalidPspIdInvalidPspIdInvalidPspId", "97735020584", "97735020584_03");

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("pspId")));
  }

  @Test
  void givenNullBrokerIdThenValidationKo() {
    var config = new PagoPaConf("AGID_01", null, "97735020584_03");

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("brokerId")));
  }

  @Test
  void givenInvalidBrokerIdThenValidationKo() {
    var config = new PagoPaConf("AGID_01", "InvalidBrokerIdInvalidBrokerIdInvalidBrokerId", "97735020584_03");

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("brokerId")));
  }

  @Test
  void givenNullChannelIdThenValidationKo() {
    var config = new PagoPaConf("AGID_01", "97735020584", null);

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getMessage().contains("channelId")));
  }

  @Test
  void givenInvalidChannelIdThenValidationKo() {
    var config = new PagoPaConf("AGID_01", "97735020584", "InvalidChannelIdInvalidChannelIdInvalidChannelId");

    var validationErrors = validator().validate(config);

    assertEquals(1, validationErrors.size());
    assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("channelId")));
  }
}