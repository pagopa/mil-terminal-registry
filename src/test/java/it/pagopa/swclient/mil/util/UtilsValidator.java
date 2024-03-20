package it.pagopa.swclient.mil.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UtilsValidator {
    public static Validator validator() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            return validatorFactory.getValidator();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Validator", e);
        }
    }
}
