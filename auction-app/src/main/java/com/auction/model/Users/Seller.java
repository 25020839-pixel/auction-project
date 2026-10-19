package com.auction.model.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller extends User {
    private double ratingsScore;

    protected Seller() {}

    public Seller(String id, String username, String password, String email, String fullName, String phoneNumber, double ratingsScore){
        super(id, username, password, email, fullName, phoneNumber);
        this.ratingsScore = ratingsScore;
    }

    public double getRatingsScore() { return ratingsScore; }

    @Override
    public String getInfo() {
        return "[SELLER]:\nUsername: " + getUsername() + "\nEmail: " + getEmail()
                + "\nPhone Number: " + getPhoneNumber() + "\nRating Score: " + ratingsScore;
    }
}
