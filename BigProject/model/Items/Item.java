package model.Items;
import enums.ItemStatus;
import model.Entity;
import model.Users.Seller;

public abstract class Item extends Entity {
    private String name;
    private String description;
    private ItemStatus status;
    private Seller seller;

    public Item(String id ,String name, String description, ItemStatus status, Seller seller){
        super(id);
        this.name = name;
        this.description = description;
        this.status = status;
        this.seller = seller;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ItemStatus getStatus() { return status; }
    public Seller getSeller() { return seller; }
    public void setDescription(String description) { this.description = description; }
    public void setName(String name) { this.name = name; }
    public void setSeller(Seller seller) { this.seller = seller; }
    public void updateStatus(ItemStatus status){ this.status = status; }

    @Override
    public String getInfo(){
        return "[ITEM]:\nName: " + name + "\nDescription: " + description + "\nSeller: " + seller + "\nStatus: " + status;
    }
}
