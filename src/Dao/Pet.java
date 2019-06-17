package Dao;

import java.util.Date;

public class Pet {
    private int id;
    private String name;
    private String typeName;
    private String health;
    private int love;
    private Date birthday = new Date();
    private int ownerId = 0;
    private int storeId;

    public Pet() {
    }

    public Pet(int id, String name, String typeName, String health, int love, Date birthday, int ownerId, int storeId) {
        this.id = id;
        this.name = name;
        this.typeName = typeName;
        this.health = health;
        this.love = love;
        this.birthday = birthday;
        this.ownerId = ownerId;
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", health='" + health + '\'' +
                ", love=" + love +
                ", birthday=" + birthday +
                ", ownerId=" + ownerId +
                ", storeId=" + storeId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
