package it.pagopa.swclient.mil.util;

public enum ErrorCodes {
    REQUESTID_MUST_NOT_BE_NULL("00TR000001", "RequestId must not be null");


    private final String code;
    private final String description;

    ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
