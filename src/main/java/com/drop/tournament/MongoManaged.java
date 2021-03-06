package com.drop.tournament;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

public class MongoManaged implements Managed {
    private MongoClient mongoClient;

    public MongoManaged(Mongo mongo) {
        this.mongoClient = mongoClient;
    }


    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        this.mongoClient.close();
    }
}
