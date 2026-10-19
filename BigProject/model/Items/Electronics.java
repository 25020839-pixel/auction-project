package model.Items;
import enums.ItemStatus;
import model.Users.Seller;

public class Electronics extends Item{
    private String brand;
    private int warrantyMonths;

    public Electronics(String id, String name, String description, ItemStatus status, Seller seller, String brand, int warrantyMonths){
        super(id, name, description, status, seller);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    @Override
    public String getInfo() {
        return "[ITEM]:\nName: " + this.getName() + "\nDescription: " + this.getDescription() + "\nSeller: " + this.getSeller() + 
        "\nStatus: " + this.getStatus() + "\nBrand: " + brand +"\nWarranty Month(s): " + warrantyMonths;
    }
}