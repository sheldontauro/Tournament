package com.drop.tournament.service;

import com.drop.tournament.db.MatchDAO;
import com.drop.tournament.db.PlayerDAO;
import com.drop.tournament.db.TeamDAO;
import com.drop.tournament.models.Match;
import com.drop.tournament.models.Team;
import com.drop.tournament.util.MatchMapper;
import com.drop.tournament.util.TeamMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bson.Document;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class TeamService {

    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;

    public TeamService(TeamDAO teamDAO, PlayerDAO playerDAO, MatchDAO matchDAO) {
        this.teamDAO = teamDAO;
        this.playerDAO = playerDAO;
        this.matchDAO = matchDAO;
    }

    public Team createTeam(String teamName) {
        if(findByName(teamName) != null) {
            return null;
        }
        teamDAO.createTeam(teamName);
        return findByName(teamName);
    }

    public Team findByName(String name) {
        Document doc = teamDAO.findByName(name);
        return (doc == null)?null:TeamMapper.map(teamDAO.findByName(name));
    }

    public void addPlayer(String teamName, String playerName) {
        ArrayList<String> players= teamDAO.findPlayers(teamName);
        String team = playerDAO.findTeams(playerName);
        if(players == null || !players.contains(playerName)) {
            teamDAO.updatePlayer(teamName, playerName);
        }
        if(team == null || !team.equals(teamName)) {
            playerDAO.updatePlayer(teamName, playerName);
        }
    }

    public JsonObject getStats(String teamName) {
        ArrayList<Integer> matchNums = teamDAO.findMatches(teamName);
//        System.out.println(matchNums);

        JsonObject jsonObject = new JsonObject();
        JsonArray nestedObj = new JsonArray();
        JsonParser parser = new JsonParser();

        int wins = 0, losses = 0, draws = 0;

        for(Integer matchNum : matchNums) {
            Match match = MatchMapper.map(matchDAO.findMatchByNum(matchNum));

            System.out.println(match.toString());
            if(match.getScore() == null) {
                continue;
            }

            String[] score = match.getScore().split("-");
            System.out.println(score[0] + " " + score[1]);
            int homeScore = Integer.valueOf(score[0]);
            int awayScore = Integer.valueOf(score[1]);
            JsonObject matchData = new JsonObject();

            String results = "";

            if(match.getHomeTeam().equals(teamName)) {
                if(homeScore > awayScore) wins += 1;
                else if(homeScore == awayScore) draws += 1;
                else losses = 1;
                matchData.add("versus", parser.parse(match.getAwayTeam()));
                results = homeScore + "-" + awayScore;
            }
            else {
                if(homeScore < awayScore) wins += 1;
                else if(homeScore == awayScore) draws += 1;
                else losses = 1;
                matchData.add("versus", parser.parse(match.getHomeTeam()));
                results = awayScore + "-" + homeScore;
            }


            matchData.add("scores", parser.parse(results) );
            nestedObj.add(matchData);
        }

        ArrayList<String> players = teamDAO.findPlayers(teamName);

        JsonArray playerArray = new JsonArray();

        for(String player : players) {
            playerArray.add(player);
        }


        Gson gson = new Gson();
        jsonObject.add("players", playerArray);
        jsonObject.add("matches", nestedObj);
        jsonObject.add("losses", parser.parse(String.valueOf(losses)));
        jsonObject.add("wins", parser.parse(String.valueOf(wins)));
        jsonObject.add("draws", parser.parse(String.valueOf(draws)));
        return jsonObject;
    }
}
