package com.auction.model.Users;
import com.auction.model.BaseEntity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    
    protected User() {}

    public User(String id, String username, String password, String email, String fullName, String phoneNumber){
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
