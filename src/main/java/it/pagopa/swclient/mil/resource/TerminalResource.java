package it.pagopa.swclient.mil.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.service.TerminalService;
import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/terminals")
public class TerminalResource {
    private final TerminalService terminalService;

    public TerminalResource(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createTerminal(
            @HeaderParam("RequestId") @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG) @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN) String requestId,
            @HeaderParam("Authorization") @NotNull(message = ErrorCodes.ERROR_AUTHORIZATION_MUST_NOT_BE_NULL_MSG) String authorization,
            @Valid @NotNull(message = ErrorCodes.ERROR_TERMINALDTO_MUST_NOT_BE_NULL_MSG) TerminalDto terminal) throws JsonProcessingException {

        CommonHeader headers = new CommonHeader(requestId, authorization);
        Log.debugf("TerminalResource -> createTerminal - Input createTerminal: %s, %s", headers, terminal);

        return terminalService.createTerminal(headers, terminal);
    }
}
