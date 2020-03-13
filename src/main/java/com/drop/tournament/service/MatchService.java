package com.drop.tournament.service;

import com.drop.tournament.db.MatchDAO;
import com.drop.tournament.db.TeamDAO;
import com.drop.tournament.models.Match;
import com.drop.tournament.util.MatchMapper;
import org.bson.Document;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class MatchService {

    private MatchDAO matchDAO;
    private TeamDAO teamDAO;

    public MatchService(MatchDAO matchDAO, TeamDAO teamDAO) {
        this.matchDAO = matchDAO;
        this.teamDAO = teamDAO;
    }

    public Match findMatchByNum(int matchNum) {
        Document doc = matchDAO.findMatchByNum(matchNum);
        return ((doc == null)?null:MatchMapper.map(doc));
    }

    public Response createMatch(int matchNum, String homeName, String awayName) {
        if(findMatchByNum(matchNum) != null) {
            return Response.status(400).entity("Duplicate Match ID").build();
        }

        ArrayList<Integer> home =  teamDAO.findMatches(homeName);
        ArrayList<Integer> away =  teamDAO.findMatches(awayName);

        if(home == null || !home.contains(matchNum)) {
            teamDAO.addMatch(homeName, matchNum);
        }

        if(away == null || !away.contains(matchNum)) {
            teamDAO.addMatch(awayName, matchNum);
        }

        matchDAO.createMatch(matchNum, homeName, awayName);
        return Response.ok(findMatchByNum(matchNum).toString()).build();
    }

    public String updateScore(String score, int matchNum) {
        String[] checker = score.split("-");
//        System.out.println(checker.length + " " + checker[0] + " " + checker[1] + " " + findMatchByNum(matchNum));
        if(checker.length == 2 && checker[0].matches("(0|[1-9]\\d*)") &&
                checker[1].matches("(0|[1-9]\\d*)") && findMatchByNum(matchNum) != null) {
            matchDAO.updateScore(score, matchNum);
            return "Success";
        }
        else {
            return "Data not valid";
        }
    }
}
