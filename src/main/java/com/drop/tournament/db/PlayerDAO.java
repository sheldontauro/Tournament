package com.drop.tournament.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerDAO {

    private MongoCollection<Document> collection;

    public PlayerDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public Document findByName(String name) {
        Bson bsonFilter = Filters.eq("name", name);
        return collection.find(bsonFilter).first();
    }

    public void createPlayer(String name, Double age) {
        Document document = new Document();
        document.append("name", name).append("age", age);
        collection.insertOne(document);
    }

    public void createPlayer(String teamName, String name, Double age) {
        Document document = new Document();
        document.append("name", name).append("age", age).append("teamName", teamName);
        collection.insertOne(document);
    }

    public void updatePlayer(String teamName, String playerName) {
        Document updateQuery = new Document().append("teamName", teamName);
        collection.updateOne(new Document("name", playerName), new Document("$set", updateQuery));
    }

    public String findTeams(String playerName) {
        Bson bsonFilter = Filters.eq("name" ,playerName);
        Document doc = collection.find(bsonFilter).first();
        return doc.getString("teamName");
    }
}
