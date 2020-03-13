package com.drop.tournament.util;

import com.drop.tournament.models.Player;
import org.bson.Document;

public class PlayerMapper {

    public static Player map(Document doc) {
        Player player = new Player();

        player.setAge(doc.getDouble("age"));
        player.setId(doc.getObjectId("_id"));
        player.setName(doc.getString("name"));
        if(doc.getString("teamName") != null) {
            player.setTeamName(doc.getString("teamName"));
        }

        return player;
    }
}
