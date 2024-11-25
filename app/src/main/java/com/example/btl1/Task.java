package com.example.btl1;

import java.util.Date;

public class Task {
    private String description;
    private String datenote;
    private int id;
    public Task(String description, String datenote,int id) {
        this.description = description;
        this.datenote = datenote;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatenote() {
        return datenote;
    }

    public void setDatenote(String datenote) {
        this.datenote = datenote;
    }
}
