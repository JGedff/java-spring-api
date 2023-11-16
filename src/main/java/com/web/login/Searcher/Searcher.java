package com.web.login.Searcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.web.login.Role.Role;
import com.web.login.User.User;
import com.web.login.User.Google.GoogleUser;
import com.web.login.User.Microsoft.MicrosoftUser;

@Component
public class Searcher implements SearchRepository {

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    public List<User> findUserByName(String name) {
        final List<User> users = new ArrayList<User>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("users");
        
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", name).append("path", "name")))));

        result.forEach(doc -> users.add(converter.read(User.class, doc)));

        return users;
    }
    
    public List<MicrosoftUser> findMicrosoftUserByName(String name) {
        final List<MicrosoftUser> users = new ArrayList<MicrosoftUser>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("users");
        
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", name).append("path", "name")))));

        result.forEach(doc -> users.add(converter.read(MicrosoftUser.class, doc)));

        return users;
    }
    
    public List<GoogleUser> findGoogleUserByName(String name) {
        final List<GoogleUser> users = new ArrayList<GoogleUser>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("users");
        
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", name).append("path", "name")))));

        result.forEach(doc -> users.add(converter.read(GoogleUser.class, doc)));

        return users;
    }

    public List<User> findUserByUser(String user) {
        final List<User> users = new ArrayList<User>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("users");
        
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", user).append("path", "user")))));

        result.forEach(doc -> users.add(converter.read(User.class, doc)));

        return users;
    }

    public List<User> findUsersByRole(String name) {
        final List<User> users = new ArrayList<User>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("users");
        
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", name).append("path", "role")))));

        result.forEach(doc -> users.add(converter.read(User.class, doc)));

        return users;
    }

    public List<Role> findRoleByName(String name) {
        final List<Role> roles = new ArrayList<Role>();

        MongoDatabase database = client.getDatabase("reactForm");
        MongoCollection<Document> collection = database.getCollection("roles");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
            new Document("text",
                new Document("query", name).append("path", "name")))));

        result.forEach(doc -> roles.add(converter.read(Role.class, doc)));

        return roles;
    }

    public List<Role> findRoleByUserName(String userName) {
        List<Role> roles = new ArrayList<Role>();
        final User user = findUserByName(userName).get(0);

        roles = findRoleByName(user.getRole());

        return roles;
    }
}
