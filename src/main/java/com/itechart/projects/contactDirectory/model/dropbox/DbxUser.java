package com.itechart.projects.contactDirectory.model.dropbox;

public class DbxUser {
    private String accessToken;
    
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
}
