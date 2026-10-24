package com.auction.model.Users;
import com.auction.model.*;
import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bidder")
public class Bidder extends User {
    private List<BidTransaction> bidHistory;
    protected Bidder(){}

    public Bidder(String id, String username, String password, String email, String fullName, String phoneNumber){
        super(id, username, password, email, fullName, phoneNumber);
    }

    public BidTransaction placeBid(Auction auction, double amount){
        if (auction == null){
            return null;
        }
        bidHistory.add(new BidTransaction());
        return auction.placeBid(this.getID(), amount);
    }

    public List<BidTransaction> getBidHistory(){
        return new ArrayList<>(bidHistory);
    }

    public String getInfo(){
        return "[BIDDER]:\nUsername: " + getUsername() + "\nEmail: " + getEmail()
                + "\nPhone Number: " + getPhoneNumber() + "\nBid History: " + getBidHistory();
    }
}
