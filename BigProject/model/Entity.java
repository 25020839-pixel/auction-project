package model;

public abstract class Entity {
    private String id;

    public Entity(String id){
        this.id = id;
    }

    protected String getID(){ return id; };

    protected abstract String getInfo();
}
