package it.pagopa.swclient.mil.repository;

@ToString
@MongoEntity(database = "mil", collection = "terminalRegistry")
public class TerminalRegistryEntity {

  private String terminalUuid;
  private String serviceProviderId;
  private String terminalHandlerId;
  private String terminalId;
  private Boolean enabled;
  private String payeeCode;
  private String slave;
  private String pagoPa;
  private PagoPAConf pagoPaConf;
  private String idpay;

}
