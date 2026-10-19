package model.Users;
import model.Items.Item;

public class Seller extends User {
    private double ratingsScore;

    public Seller(String id, String username, String password, String email, String fullName, String phoneNumber, double ratingsScore){
        super(id, username, password, email, fullName, phoneNumber);
        this.ratingsScore = ratingsScore;
    }

    public Item createItem(){
        Item item = new Item();
        return item;
    }
}
