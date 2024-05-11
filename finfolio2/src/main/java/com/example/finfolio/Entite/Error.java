package com.example.finfolio.Entite;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Error {



    private int id;
    private User user;
    private String error;
    private String timestamp;

    public Error(int id, User user, String error) {
        this.id=id;
        this.user = user;
        this.error = error;
        Instant instant=Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp =localDateTime.format(formatter);
    }


    public Error(User user, String error) {
        this.user = user;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }




    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() { return Instant.now().toString(); }
    public void setTimestamp() { this.timestamp = Instant.now().toString(); }
}
