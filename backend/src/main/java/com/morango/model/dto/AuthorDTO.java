package com.morango.model.dto;

import com.morango.model.entities.User;

public class AuthorDTO {
    
    private String id;
    private String username;
    
    public AuthorDTO() {}
    
    public AuthorDTO(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
