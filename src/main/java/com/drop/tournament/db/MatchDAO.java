package com.drop.tournament.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MatchDAO {

    private MongoCollection<Document> collection;

    public MatchDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public Document findMatchByNum(int matchNum) {
        Bson bsonFilter = Filters.eq("matchNum", matchNum);
        return collection.find(bsonFilter).first();
    }

    public void createMatch(int matchNum, String homeName, String awayName) {
        Document doc = new Document();
        doc.append("matchNum", matchNum).append("awayName", awayName).append("homeName", homeName);
        collection.insertOne(doc);
    }

    public void updateScore(String score, int matchNum) {
        Document updateQuery = new Document().append("score", score);
        collection.updateOne(new Document("matchNum", matchNum), new Document("$set", updateQuery));
    }
}
