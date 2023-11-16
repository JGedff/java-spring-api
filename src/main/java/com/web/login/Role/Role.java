package com.web.login.Role;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    private String id;
    private String name;
    private String description;
    private Integer participants;

    public Role(String id, String name, String description, Integer participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}
