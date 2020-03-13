package com.drop.tournament.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Optional;

public class TeamDAO {

    private MongoCollection<Document> collection;

    public TeamDAO(MongoCollection<Document> collection) {
            this.collection = collection;
    }


    public Document find(ObjectId teamId) {
        Bson bsonFilter = Filters.eq("_id", teamId);
        return collection.find(bsonFilter).first();
    }

    public Document findByName(String name) {
        Bson bsonFilter = Filters.eq("name", name);
        Document doc = collection.find(bsonFilter).first();
        return doc;
    }

    public void createTeam(String teamName) {
        Document doc = new Document();
        doc.append("name", teamName);
        collection.insertOne(doc);
    }

    public void updatePlayer(String teamName, String playerName) {
        Document updateQuery = new Document().append("players", playerName);
        collection.updateOne(new Document("name", teamName), new Document("$push", updateQuery));
    }

    public ArrayList<String> findPlayers(String teamName) {
        Bson bsonFilter = Filters.eq("name" ,teamName);
        Document doc = collection.find(bsonFilter).first();
        if(doc == null || doc.get("players") == null) {
            return new ArrayList<>();
        }
        return (ArrayList<String>) doc.get("players");
    }

    public ArrayList<Integer> findMatches(String teamName) {
        Bson bsonFilter = Filters.eq("name" ,teamName);
        Document doc = collection.find(bsonFilter).first();
        if(doc == null || doc.get("matches") == null) {
            return new ArrayList<>();
        }
        return (ArrayList<Integer>) doc.get("matches");
    }

    public void addMatch(String name, int matchNum) {
        Document updateQuery = new Document().append("matches", matchNum);
        collection.updateOne(new Document("name", name), new Document("$push", updateQuery));
    }
}
