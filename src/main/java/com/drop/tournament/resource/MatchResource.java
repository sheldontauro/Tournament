package com.drop.tournament.resource;

import com.codahale.metrics.annotation.Timed;
import com.drop.tournament.models.Match;
import com.drop.tournament.service.MatchService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/match")
public class MatchResource {

    private MatchService matchService;

    public MatchResource(MatchService matchService) {
        this.matchService = matchService;
    }

    @GET
    @Timed
    public Response findMatch(@QueryParam("matchNum") int matchNum) {
        Match match = matchService.findMatchByNum(matchNum);
        if(match == null) {
            return Response.status(400).entity("Couldnt find match").build();
        }
        return Response.ok(match.toString()).build();
    }

    @POST
    @Timed
    @Path("/create")
    public Response createMatch(@FormParam("matchNum") int matchNum, @FormParam("homeName") String homeName, @FormParam("awayName") String awayName) {
        return matchService.createMatch(matchNum, homeName, awayName);
    }

    @PUT
    @Timed
    @Path("/{matchNum}")
    public Response updateScore(@QueryParam("score") String score, @PathParam("matchNum") int matchNum) {
        String msg = matchService.updateScore(score, matchNum);
        return Response.ok(msg).build();
    }
}
