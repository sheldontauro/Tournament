package com.drop.tournament;

import com.drop.tournament.db.MatchDAO;
import com.drop.tournament.db.PlayerDAO;
import com.drop.tournament.db.TeamDAO;
import com.drop.tournament.resource.MatchResource;
import com.drop.tournament.resource.PlayerResource;
import com.drop.tournament.resource.TeamResource;
import com.drop.tournament.service.MatchService;
import com.drop.tournament.service.PlayerService;
import com.drop.tournament.service.TeamService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.bson.Document;

public class TournamentApplication extends Application<TournamentConfiguration> {

    public static void main(String[] args) throws Exception {
        new TournamentApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TournamentConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(TournamentConfiguration config, Environment environment) throws Exception {

        MongoClient mongoClient = new MongoClient(config.getMongoHost(), config.getMongoPort());
        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        environment.lifecycle().manage(mongoManaged);
        MongoDatabase db = mongoClient.getDatabase(config.getMongoDB());

        MongoCollection<Document> collection = db.getCollection(config.getCollectionName());
        MongoCollection<Document> collection1 = db.getCollection(config.getCollectionName1());
        MongoCollection<Document> collection2 = db.getCollection(config.getCollectionName2());

        TeamDAO teamDAO = new TeamDAO(collection);
        MatchDAO matchDAO = new MatchDAO(collection1);
        PlayerDAO playerDAO = new PlayerDAO(collection2);

        TeamService teamService = new TeamService(teamDAO, playerDAO, matchDAO);
        MatchService matchService = new MatchService(matchDAO, teamDAO);
        PlayerService playerService = new PlayerService(playerDAO, teamDAO);


        environment.jersey().register(new TeamResource(teamService));
        environment.jersey().register(new MatchResource(matchService));
        environment.jersey().register(new PlayerResource(playerService));
    }
}
