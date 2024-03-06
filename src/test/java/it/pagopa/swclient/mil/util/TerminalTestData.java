package it.pagopa.swclient.mil.util;

import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;

public final class TerminalTestData {
    public static CommonHeader getCorrectCommonHeader() {
        return new CommonHeader("1a2b3c4d-5e6f-789a-bcde-f0123456789a", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
    }

    public static TerminalDto getCorrectTerminalDto() {
        return new TerminalDto("45856", "34523860", true,
                "RSSMRA85T10A562S", true, false, null, true);
    }

    public static TerminalEntity getCorrectTerminalEntity() {
        Terminal terminal = Terminal.builder()
                .terminalId("34523860")
                .enabled(true)
                .payeeCode("RSSMRA85T10A562S")
                .slave(true)
                .pagoPa(false)
                .pagoPaConf(null)
                .idpay(true)
                .build();

        return TerminalEntity.builder()
                .terminalUuid("c7a1b24b0583477292ebdbaa")
                .terminalHandler("45856")
                .terminal(terminal)
                .build();
    }
}
