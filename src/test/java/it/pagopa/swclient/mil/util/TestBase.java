package it.pagopa.swclient.mil.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    private static final ValidatorFactory validatorFactory;
    protected Validator validator;

    static {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    void tearDown() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }
}
