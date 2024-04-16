package it.pagopa.swclient.mil.util;

import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;

public final class TerminalTestData {

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

        return new TerminalEntity("c7a1b24b0583477292ebdbaa","","45856",terminal);
    }
}
