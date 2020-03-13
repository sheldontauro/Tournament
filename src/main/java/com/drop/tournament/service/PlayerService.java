package com.drop.tournament.service;

import com.drop.tournament.db.PlayerDAO;
import com.drop.tournament.db.TeamDAO;
import com.drop.tournament.models.Player;
import com.drop.tournament.util.PlayerMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class PlayerService {

    private PlayerDAO playerDAO;
    private TeamDAO teamDAO;

    public PlayerService(PlayerDAO playerDAO, TeamDAO teamDAO) {
        this.playerDAO = playerDAO;
        this.teamDAO = teamDAO;
    }

    public Player findPlayer(String name) {
        Document player = playerDAO.findByName(name);
        return ((player == null)?null:PlayerMapper.map(playerDAO.findByName(name)));

    }

    public Response createPlayer(String name, Optional<String> teamName, Double age) {
        if(findPlayer(name) != null) {
            return Response.status(400).entity("Trying to create a duplicate object").build();
        }
        if(teamName.isPresent()) {
            if(teamDAO.findByName(teamName.get()) != null) {
                playerDAO.createPlayer(teamName.get(), name, age);
                teamDAO.updatePlayer(teamName.get(), name);
            }
        }
        else {
            playerDAO.createPlayer(name, age);
        }
        return Response.ok(findPlayer(name).toString()).build();
    }
}
