package it.pagopa.swclient.mil.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import it.pagopa.swclient.mil.controller.model.PagoPaConf;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@RegisterForReflection
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Terminal {
    private String terminalId;
    private Boolean enabled;
    private String payeeCode;
    private Boolean slave;
    private List<String> workstations;
    private Boolean pagoPa;
    private PagoPaConf pagoPaConf;
    private Boolean idpay;
}
