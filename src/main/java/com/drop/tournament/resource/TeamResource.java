package com.drop.tournament.resource;

import com.codahale.metrics.annotation.Timed;
import com.drop.tournament.models.Team;
import com.drop.tournament.service.TeamService;
import lombok.Getter;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/team")
public class TeamResource {

    private TeamService teamService;

    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    @Timed
    public Response createTeam(@FormParam("name") String teamName) {
        Team team = teamService.createTeam(teamName);
        if(team == null) {
            return Response.status(400).entity("Unable to create team").build();
        }
        return Response.ok(team.toString()).build();
    }

    @GET
    @Timed
    public Response findTeam(@QueryParam("name") String teamName) {
        Team team = teamService.findByName(teamName);
        if(team == null) {
            return Response.status(400).entity("Unable to find team").build();
        }
        return Response.ok(team.toString()).build();
    }

    @POST
    @Timed
    @Path("/players")
    public Response addPlayer(@FormParam("teamName") String teamName, @FormParam("playerName") String playerName) {
        teamService.addPlayer(teamName, playerName);
        return Response.ok().build();
    }

    @GET
    @Timed
    @Path("/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stats(@QueryParam("teamName") String name) {
        if(name == null) {
            return Response.status(400).build();
        }
        return Response.ok(teamService.getStats(name).toString()).build();
    }
}
