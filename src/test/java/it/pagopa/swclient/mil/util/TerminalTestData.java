package it.pagopa.swclient.mil.util;

import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.Terminal;
import it.pagopa.swclient.mil.dao.TerminalEntity;

import java.util.List;

public final class TerminalTestData {

    public static TerminalDto getCorrectTerminalDto() {
        return new TerminalDto("45856", "34523860", true,
                "RSSMRA85T10A562S", true, List.of("cassa-1-ufficio-3", "cassa-2-ufficio-3"), false, null, true);
    }

    public static TerminalEntity getCorrectTerminalEntity() {
        Terminal terminal = new Terminal();
        terminal.setTerminalId("34523860");
        terminal.setEnabled(true);
        terminal.setPayeeCode("RSSMRA85T10A562S");
        terminal.setSlave(true);
        terminal.setWorkstations(null);
        terminal.setPagoPa(false);
        terminal.setPagoPaConf(null);
        terminal.setIdpay(true);

        TerminalEntity terminalEntity = new TerminalEntity();
        terminalEntity.setTerminalUuid("c7a1b24b0583477292ebdbaa");
        terminalEntity.setTerminalHandler("45856");
        terminalEntity.setTerminal(terminal);
      
        return terminalEntity;
    }
}
