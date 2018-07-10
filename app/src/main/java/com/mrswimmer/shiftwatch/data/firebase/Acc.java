package com.mrswimmer.shiftwatch.data.firebase;

public class Acc {
    String email;
    String country;
    String id;
    int amountOfTasks;
    int wins;

    public Acc(String email, String country, String id) {
        this.email = email;
        this.country = country;
        this.id = id;
    }

    public Acc(String email, String country, String id, int amountOfTasks, int wins) {
        this.email = email;
        this.country = country;
        this.id = id;
        this.amountOfTasks = amountOfTasks;
        this.wins = wins;
    }

    public int getAmountOfTasks() {
        return amountOfTasks;
    }

    public void setAmountOfTasks(int amountOfTasks) {
        this.amountOfTasks = amountOfTasks;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Acc() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
