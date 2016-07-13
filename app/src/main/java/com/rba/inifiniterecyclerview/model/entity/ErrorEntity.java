package com.rba.inifiniterecyclerview.model.entity;

/**
 * Created by Ricardo Bravo on 13/07/16.
 */

public class ErrorEntity {

    String description;

    public ErrorEntity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
