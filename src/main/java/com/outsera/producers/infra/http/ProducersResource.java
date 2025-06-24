package com.outsera.producers.infra.http;

import com.outsera.producers.application.interval.GetWinneingProducersUseCase;
import com.outsera.producers.application.interval.GetWinningProducersOutput;
import com.outsera.producers.infra.models.GetWinningProducersResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/producers")
@RequiredArgsConstructor
public class ProducersResource {

    private final GetWinneingProducersUseCase getWinneingProducersUseCase;

    @GET
    @Path("/winners")
    public Response getWinners() {
        final GetWinningProducersOutput output = getWinneingProducersUseCase.execute();
        final var response = GetWinningProducersResponse.from(output);
        return Response.ok(response).build();
    }
}
