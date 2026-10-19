package com.auction.model.Items;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.auction.enums.ItemStatus;
import com.auction.model.Users.Seller;
import com.auction.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_category", discriminatorType = DiscriminatorType.STRING, length = 32)
public abstract class Item extends BaseEntity {
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "jsonb")
    private Map<String, String> attributes = new HashMap<>();

    protected Item() {};

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

    public void setAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
