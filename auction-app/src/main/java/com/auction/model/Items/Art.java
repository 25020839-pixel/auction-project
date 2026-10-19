package com.auction.model.Items;
import com.auction.enums.ItemStatus;
import com.auction.model.Users.Seller;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ART")
public class Art extends Item{
    private String artist;

    protected Art() {}

    public Art(String id, String name, String description, ItemStatus status, Seller seller, String artist){
        super(id, name, description, status, seller);
        this.artist = artist;
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    @Override
    public String getInfo() {
        return "[ITEM]:\nName: " + this.getName() + "\nDescription: " + this.getDescription() + "\nSeller: " + this.getSeller() + 
        "\nStatus: " + this.getStatus() + "\nArtist: " + artist;
    }
}
