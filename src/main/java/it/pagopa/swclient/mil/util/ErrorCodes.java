package it.pagopa.swclient.mil.util;

import lombok.Getter;

@Getter
public final class ErrorCodes {
    private ErrorCodes() {
    }

    public static final String MODULE_ID = "00TR";


    /*
     * Validation errors code from 000001 - 000200
     */
    public static final String ERROR_REQUESTID_MUST_NOT_BE_NULL                                  = MODULE_ID + "000001";
    public static final String ERROR_AUTHORIZATION_MUST_NOT_BE_NULL                              = MODULE_ID + "000002";
    public static final String ERROR_PSPID_MUST_NOT_BE_NULL                                      = MODULE_ID + "000003";
    public static final String ERROR_BROKERID_MUST_NOT_BE_NULL                                   = MODULE_ID + "000004";
    public static final String ERROR_CHANNELID_MUST_NOT_BE_NULL                                  = MODULE_ID + "000005";
    public static final String ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL                          = MODULE_ID + "000006";
    public static final String ERROR_TERMINALID_MUST_NOT_BE_NULL                                 = MODULE_ID + "000007";
    public static final String ERROR_ENABLED_MUST_NOT_BE_NULL                                    = MODULE_ID + "000008";
    public static final String ERROR_PAYEECODE_MUST_NOT_BE_NULL                                  = MODULE_ID + "000009";
    public static final String ERROR_SLAVE_MUST_NOT_BE_NULL                                      = MODULE_ID + "000010";
    public static final String ERROR_PAGOPA_MUST_NOT_BE_NULL                                     = MODULE_ID + "000011";
    public static final String ERROR_IDPAY_MUST_NOT_BE_NULL                                      = MODULE_ID + "000012";
    public static final String ERROR_TERMINALDTO_MUST_NOT_BE_NULL                                = MODULE_ID + "000013";



    /*
     * Error descriptions
     */
    public static final String ERROR_REQUESTID_MUST_NOT_BE_NULL_DESCR = "RequestId must not be null";
    public static final String ERROR_AUTHORIZATION_MUST_NOT_BE_NULL_DESCR = "Authorization must not be null";
    public static final String ERROR_PSPID_MUST_NOT_BE_NULL_DESCR = "pspId must not be null";
    public static final String ERROR_BROKERID_MUST_NOT_BE_NULL_DESCR = "brokerId must not be null";
    public static final String ERROR_CHANNELID_MUST_NOT_BE_NULL_DESCR = "channelId must not be null";
    public static final String ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL_DESCR  = "terminalHandlerId must not be null";
    public static final String ERROR_TERMINALID_MUST_NOT_BE_NULL_DESCR = "terminalId must not be null";
    public static final String ERROR_ENABLED_MUST_NOT_BE_NULL_DESCR = "enabled must not be null";
    public static final String ERROR_PAYEECODE_MUST_NOT_BE_NULL_DESCR = "payeeCode must not be null";
    public static final String ERROR_SLAVE_MUST_NOT_BE_NULL_DESCR = "slave must not be null";
    public static final String ERROR_PAGOPA_MUST_NOT_BE_NULL_DESCR = "pagoPa must not be null";
    public static final String ERROR_IDPAY_MUST_NOT_BE_NULL_DESCR = "idpay must not be null";
    public static final String ERROR_TERMINALDTO_MUST_NOT_BE_NULL_DESCR = "request body must not be null";



    /*
     * Error complete message
     */
    public static final String ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_REQUESTID_MUST_NOT_BE_NULL + "] " + ERROR_REQUESTID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_AUTHORIZATION_MUST_NOT_BE_NULL_MSG = "[" + ERROR_AUTHORIZATION_MUST_NOT_BE_NULL + "] " + ERROR_AUTHORIZATION_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_PSPID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_PSPID_MUST_NOT_BE_NULL + "] " + ERROR_PSPID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_BROKERID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_BROKERID_MUST_NOT_BE_NULL + "] " + ERROR_BROKERID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_CHANNELID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_CHANNELID_MUST_NOT_BE_NULL + "] " + ERROR_CHANNELID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL + "] " + ERROR_TERMINALHANDLERID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_TERMINALID_MUST_NOT_BE_NULL_MSG = "[" + ERROR_TERMINALID_MUST_NOT_BE_NULL + "] " + ERROR_TERMINALID_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_ENABLED_MUST_NOT_BE_NULL_MSG = "[" + ERROR_ENABLED_MUST_NOT_BE_NULL + "] " + ERROR_ENABLED_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_PAYEECODE_MUST_NOT_BE_NULL_MSG = "[" + ERROR_PAYEECODE_MUST_NOT_BE_NULL + "] " + ERROR_PAYEECODE_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_SLAVE_MUST_NOT_BE_NULL_MSG = "[" + ERROR_SLAVE_MUST_NOT_BE_NULL + "] " + ERROR_SLAVE_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_PAGOPA_MUST_NOT_BE_NULL_MSG = "[" + ERROR_PAGOPA_MUST_NOT_BE_NULL + "] " + ERROR_PAGOPA_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_IDPAY_MUST_NOT_BE_NULL_MSG = "[" + ERROR_IDPAY_MUST_NOT_BE_NULL + "] " + ERROR_IDPAY_MUST_NOT_BE_NULL_DESCR;
    public static final String ERROR_TERMINALDTO_MUST_NOT_BE_NULL_MSG = "[" + ERROR_TERMINALDTO_MUST_NOT_BE_NULL + "] " + ERROR_TERMINALDTO_MUST_NOT_BE_NULL_DESCR;

}
