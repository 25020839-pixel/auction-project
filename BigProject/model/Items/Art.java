package model.Items;
import enums.ItemStatus;
import model.Users.Seller;

public class Art extends Item{
    private String artist;

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
