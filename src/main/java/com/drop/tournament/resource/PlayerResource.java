package com.drop.tournament.resource;

import com.codahale.metrics.annotation.Timed;
import com.drop.tournament.models.Player;
import com.drop.tournament.service.PlayerService;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/player")
public class PlayerResource {

    private PlayerService playerService;

    public PlayerResource(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GET
    @Timed
    public Response findPlayer(@QueryParam("name") String name) {
        return Response.ok(playerService.findPlayer(name).toString()).build();
    }

    @POST
    @Timed
    public Response createPlayer(@FormParam("name") String name, @FormParam("teamName") Optional<String> teamName, @FormParam("age") Double age) {
       return playerService.createPlayer(name, teamName, age);
    }
}
