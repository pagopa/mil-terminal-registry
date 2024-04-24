package it.pagopa.swclient.mil.resource;

import com.mongodb.MongoWriteException;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import it.pagopa.swclient.mil.controller.model.TerminalDto;
import it.pagopa.swclient.mil.dao.PageMetadata;
import it.pagopa.swclient.mil.dao.TerminalPageResponse;
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
                .onFailure()
                .transform(err -> {
                    if (err instanceof MongoWriteException mongoWriteException && (mongoWriteException.getCode() == 11000)) {
                        Log.errorf(err, "TerminalResource -> createTerminal: duplicate key violation for terminalId [%s] and terminalHandlerId [%s]", terminal.terminalId(), terminal.terminalHandlerId());

                        return new WebApplicationException(Response
                                .status(Response.Status.CONFLICT)
                                .entity(new Errors(ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB, ErrorCodes.ERROR_DUPLICATE_KEY_FROM_DB_MSG))
                                .build());

                    } else {
                        Log.errorf(err, "TerminalResource -> createTerminal: unexpected error during persist for terminal [%s]", terminal);

                        return new InternalServerErrorException(Response
                                .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                                .build());
                    }
                })
                .onItem()
                .transform(terminalSaved -> {
                    Log.debugf("TerminalResource -> createTerminal: terminal saved correctly on DB [%s]", terminalSaved);

                    return Response.status(Response.Status.CREATED).build();
                });
    }

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"pos_service_provider"})
    public Uni<Response> getTerminals(
            @HeaderParam("RequestId") @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG) @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN) String requestId,
            @QueryParam("page") int pageNumber,
            @QueryParam("size") int pageSize) {

        Log.debugf("TerminalResource -> getTerminals - Input requestId, pageNumber, size: %s, %s, %s", requestId, pageNumber, pageSize);

        String serviceProviderId = jwt.getSubject();

        return terminalService.getTerminalCount(serviceProviderId)
                .onFailure()
                .transform(err -> {
                    Log.errorf(err, "TerminalResource -> getTerminals: error while counting terminals for serviceProviderId [%s]", serviceProviderId);

                    return new InternalServerErrorException(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new Errors(ErrorCodes.ERROR_COUNTING_TERMINALS, ErrorCodes.ERROR_COUNTING_TERMINALS_MSG))
                            .build());
                })
                .onItem()
                .transformToUni(numberOfTerminals -> {
                    Log.debugf("TerminalResource -> getTerminals: founded a total count of [%s] terminals", numberOfTerminals);

                    return terminalService.getTerminalListPaged(serviceProviderId, pageNumber, pageSize)
                            .onFailure()
                            .transform(err -> {
                                Log.errorf(err, "TerminalResource -> getTerminals: Error while retrieving list of terminals for serviceProviderId, index and size [%s, %s, %s]", serviceProviderId, pageNumber, pageSize);

                                return new InternalServerErrorException(Response
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .entity(new Errors(ErrorCodes.ERROR_LIST_TERMINALS, ErrorCodes.ERROR_LIST_TERMINALS_MSG))
                                        .build());
                            })
                            .onItem()
                            .transform(terminalsPaged -> {
                                Log.debugf("TerminalResource -> getTerminals: size of list of terminals paginated founded: [%s]", terminalsPaged.size());

                                int totalPages = (int) Math.ceil((double) numberOfTerminals / pageSize);
                                PageMetadata pageMetadata = new PageMetadata(pageSize, numberOfTerminals, totalPages);

                                return Response
                                        .status(Response.Status.OK)
                                        .entity(new TerminalPageResponse(terminalsPaged, pageMetadata))
                                        .build();
                            });
                });
    }

    @PATCH
    @Path("/{terminalUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"pos_service_provider"})
    public Uni<Response> updateTerminal(
            @HeaderParam("RequestId") @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG) @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN) String requestId,
            @Valid @NotNull(message = ErrorCodes.ERROR_TERMINALDTO_MUST_NOT_BE_NULL_MSG) TerminalDto terminal,
            @PathParam(value = "terminalUuid") String terminalUuid) {

        Log.debugf("TerminalResource -> updateTerminal - Input requestId, updateTerminal: %s, %s", requestId, terminal);

        String serviceProviderId = jwt.getSubject();

        return terminalService.findTerminal(serviceProviderId, terminalUuid)
                .onFailure()
                .transform(err -> {
                    Log.errorf(err, "TerminalResource -> updateTerminal: error during search terminal with terminalUuid, serviceProviderId: [%s, %s]", terminalUuid, serviceProviderId);

                    return new InternalServerErrorException(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                            .build());
                })
                .onItem()
                .transformToUni(terminalEntity -> {
                    if (terminalEntity == null) {
                        Log.errorf("TerminalResource -> updateTerminal: error 404 during searching terminal with terminalUuid, serviceProviderId: [%s, %s]", terminalUuid, serviceProviderId);

                        return Uni.createFrom().failure(new NotFoundException(Response
                                .status(Response.Status.NOT_FOUND)
                                .entity(new Errors(ErrorCodes.ERROR_TERMINAL_NOT_FOUND, ErrorCodes.ERROR_TERMINAL_NOT_FOUND_MSG))
                                .build()));
                    }

                    return terminalService.updateTerminal(terminalUuid, serviceProviderId, terminal, terminalEntity)
                            .onFailure()
                            .transform(err -> {
                                Log.errorf(err, "TerminalResource -> updateTerminal: error during update terminal [%s]", terminal);

                                return new InternalServerErrorException(Response
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                                        .build());
                            })
                            .onItem()
                            .transform(terminalUpdated -> {
                                Log.debugf("TerminalResource -> updateTerminal: terminal updated correctly on DB [%s]", terminalUpdated);

                                return Response
                                        .status(Response.Status.NO_CONTENT)
                                        .build();
                            });
                });
    }

    @DELETE
    @Path("/{terminalUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"pos_service_provider"})
    public Uni<Response> deleteTerminal(
            @HeaderParam("RequestId") @NotNull(message = ErrorCodes.ERROR_REQUESTID_MUST_NOT_BE_NULL_MSG) @Pattern(regexp = RegexPatterns.REQUEST_ID_PATTERN) String requestId,
            @PathParam(value = "terminalUuid") String terminalUuid) {

        Log.debugf("TerminalResource -> deleteTerminal - Input requestId, terminalUuid: %s, %s", requestId, terminalUuid);

        String serviceProviderId = jwt.getSubject();

        return terminalService.findTerminal(serviceProviderId, terminalUuid)
                .onFailure()
                .transform(err -> {
                    Log.errorf(err, "TerminalResource -> deleteTerminal: error during search terminal with terminalUuid, serviceProviderId: [%s, %s]", terminalUuid, serviceProviderId);

                    return new InternalServerErrorException(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                            .build());
                })
                .onItem()
                .transformToUni(terminalEntity -> {
                    if (terminalEntity == null) {
                        Log.errorf("TerminalResource -> deleteTerminal: error 404 during searching terminal with terminalUuid, serviceProviderId: [%s, %s]", terminalUuid, serviceProviderId);

                        return Uni.createFrom().failure(new NotFoundException(Response
                                .status(Response.Status.NOT_FOUND)
                                .entity(new Errors(ErrorCodes.ERROR_TERMINAL_NOT_FOUND, ErrorCodes.ERROR_TERMINAL_NOT_FOUND_MSG))
                                .build()));
                    }

                    return terminalService.deleteTerminal(terminalEntity)
                            .onFailure()
                            .transform(err -> {
                                Log.errorf(err, "TerminalResource -> deleteTerminal: error during deleting terminal [%s]", terminalEntity);

                                return new InternalServerErrorException(Response
                                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                                        .entity(new Errors(ErrorCodes.ERROR_GENERIC_FROM_DB, ErrorCodes.ERROR_GENERIC_FROM_DB_MSG))
                                        .build());
                            })
                            .onItem()
                            .transform(terminalUpdated -> {
                                Log.debugf("TerminalResource -> deleteTerminal: terminal deleted correctly on DB [%s]", terminalUpdated);

                                return Response
                                        .status(Response.Status.NO_CONTENT)
                                        .build();
                            });
                });
    }
}
