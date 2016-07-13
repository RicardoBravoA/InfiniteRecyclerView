package com.rba.inifiniterecyclerview.model.entity;

/**
 * Created by Ricardo Bravo on 13/07/16.
 */

public class ItemEntity {

    int id;
    String description;

    public ItemEntity(int id, String description) {
        this.id = id;
        this.description = description;
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
}
