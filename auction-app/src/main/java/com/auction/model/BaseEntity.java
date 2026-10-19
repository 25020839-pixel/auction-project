package com.auction.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    private String id;

    protected BaseEntity(){};

    public BaseEntity(String id){
        this.id = id;
    }

    protected String getID(){ return id; };

    protected abstract String getInfo();
}
