package com.itechart.projects.contactDirectory.model.dropbox;

public class DbxUser {
    private String accessToken;
    private String username;
    
    public DbxUser() {
    }

    public DbxUser(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
