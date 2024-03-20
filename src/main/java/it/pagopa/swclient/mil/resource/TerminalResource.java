package it.pagopa.swclient.mil.resource;

import com.mongodb.MongoWriteException;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.service.TerminalService;
import it.pagopa.swclient.mil.util.ErrorCodes;
import it.pagopa.swclient.mil.util.Errors;
import it.pagopa.swclient.mil.util.RegexPatterns;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/terminals")
public class TerminalResource {

    private final JsonWebToken jwt;

    private final TerminalService terminalService;

    public TerminalResource(TerminalService terminalService, JsonWebToken jwt) {
        this.terminalService = terminalService;
        this.jwt = jwt;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"pos_service_provider"})
    public Uni<Response> createTerminal(
            @HeaderParam("RequestId") @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG) @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN) String requestId,
            @Valid @NotNull(message = ErrorCodes.ERROR_TERMINALDTO_MUST_NOT_BE_NULL_MSG) TerminalDto terminal) {

        Log.debugf("TerminalResource -> createTerminal - Input requestId, createTerminal: %s, %s", requestId, terminal);

        String serviceProviderId = jwt.getSubject();

        return terminalService.createTerminal(serviceProviderId, terminal)
                .onFailure().transform(err -> {
                    if (err instanceof MongoWriteException mongoWriteException && (mongoWriteException.getCode() == 11000)) {
                        Log.errorf(err, " TerminalService -> createTerminal: duplicate key violation for terminalId [%s] and terminalHandlerId [%s]", terminal.terminalId(), terminal.terminalHandlerId());

                        return new WebApplicationException(Response
                                .status(Response.Status.CONFLICT)
                                .entity(new Errors(ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB, ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB_MSG))
                                .build());

                    } else {
                        Log.errorf(err, " TerminalService -> createTerminal: unexpected error during persist for terminal [%s]", terminal);

                        return new InternalServerErrorException(Response
                                .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                                .build());
                    }
                })
                .onItem().transform(terminalSaved -> {
                    Log.debugf(" TerminalService -> createTerminal: terminal saved correctly on DB [%s]", terminalSaved);

                    return Response.status(Response.Status.CREATED).build();
                });
    }
}
