package it.pagopa.swclient.mil.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import it.pagopa.swclient.mil.controller.model.PagoPaConf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Terminal {
    private String terminalId;
    private Boolean enabled;
    private String payeeCode;
    private Boolean slave;
    private Boolean pagoPa;
    private PagoPaConf pagoPaConf;
    private Boolean idpay;
}
