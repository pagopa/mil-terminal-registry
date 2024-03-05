package it.pagopa.swclient.mil.resource;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.CommonHeader;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.service.TerminalService;
import it.pagopa.swclient.mil.util.ErrorCodes;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/terminals")
public class TerminalResource {
    private final TerminalService terminalService;

    public TerminalResource(TerminalService terminalService) {
        this.terminalService = terminalService;
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Italy";
    }

/*    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createTerminal(
            @Valid @BeanParam CommonHeader headers,
            @Valid
            @NotNull(message = ErrorCodes.ERROR_TERMINALDTO_MUST_NOT_BE_NULL_MSG)
            TerminalDto terminal) {

        Log.debugf("TerminalResource -> createTerminal - Input createTerminal: %s, %s", headers, terminal);

        return terminalService.createTerminal(headers, terminal).chain(res -> {
            Log.debugf("TerminalResource -> TerminalService -> createTerminal - Response %s", res);

            return Uni.createFrom().item(res);
        });
    }*/
}
