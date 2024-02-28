package it.pagopa.swclient.mil.controller.model;

import it.pagopa.swclient.mil.util.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonHeaderTest extends TestBase {
  @ParameterizedTest(name = "givenRequestDataThenValidationResult - Test {index}: requestId={0}, authorization={1}, expectedErrors={2}")
  @CsvSource({
          "1a2b3c4d-5e6f-789a-bcde-f0123456789a, Bearer token, 0",
          "null, Bearer token, 1",
          "zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz, Bearer token, 1",
          "1a2b3c4d-5e6f-789a-bcde-f0123456789a, null, 1"
  })
  void givenRequestDataThenValidationResult(String requestId, String authorization, int expectedErrors) {
    var config = new CommonHeader(Objects.equals(requestId, "null") ? null : requestId, Objects.equals(authorization, "null") ? null : authorization);
    var validationErrors = validator.validate(config);

    assertEquals(expectedErrors, validationErrors.size());

    if (expectedErrors > 0) {
      if (config.authorization() != null) {
        assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("requestId")));
      } else {
        assertTrue(validationErrors.stream().anyMatch(error -> error.getPropertyPath().toString().equals("authorization")));
      }
    }
  }
}