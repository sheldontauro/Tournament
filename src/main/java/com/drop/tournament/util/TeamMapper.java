package com.drop.tournament.util;

import com.drop.tournament.models.Team;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class TeamMapper {

    public static Team map(Document doc) {
        Team team = new Team();

        team.setId(doc.getObjectId("_id"));
        team.setName(doc.getString("name"));
        team.setMatches((ArrayList<Integer>) doc.get("matches"));
        team.setPlayers((ArrayList<String>) doc.get("players"));

        return team;
    }
}
