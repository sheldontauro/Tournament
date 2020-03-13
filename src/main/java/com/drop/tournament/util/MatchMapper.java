package com.drop.tournament.util;

import com.drop.tournament.models.Match;
import org.bson.Document;

public class MatchMapper {

    public static Match map(Document doc) {
        Match match = new Match();

        match.setId(doc.getObjectId("_id"));
        match.setAwayTeam(doc.getString("awayName"));
        match.setHomeTeam(doc.getString("homeName"));
        match.setMatchNumber(doc.getInteger("matchNum"));
        match.setScore(doc.getString("score"));
        return match;
    }
}
