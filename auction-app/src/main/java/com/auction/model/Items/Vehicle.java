package com.auction.model.Items;
import com.auction.enums.ItemStatus;
import com.auction.model.Users.Seller;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VEHICLE")
public class Vehicle extends Item{
    private String brand;
    private String model;
    private int manufacturerYear;

    protected Vehicle() {}

    public Vehicle(String id, String name, String description, ItemStatus status, Seller seller, String brand, String model, int manufacturerYear){
        super(id, name, description, status, seller);
        this.brand = brand;
        this.model = model;
        this.manufacturerYear = manufacturerYear;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getInfo(){
        return "[ITEM]:\nName: " + this.getName() + "\nDescription: " + this.getDescription() + "\nSeller: " + this.getSeller() + 
        "\nStatus: " + this.getStatus() + "\nBrand: " + brand + "\nModel: " + model + "\nManufacturer Year: " + manufacturerYear;
    }
}
