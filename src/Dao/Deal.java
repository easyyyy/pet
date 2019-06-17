package Dao;

import java.util.Date;

public class Deal {
    private Integer id;
    private String dealType;
    private Integer petId;
    private Integer sellerId;
    private Integer goodsId;
    private Integer buyerId;
    private Double price;
    private Date dealTime;

    public Deal() {
    }

    public Deal(Integer id, String dealType, Integer petId, Integer sellerId, Integer goodsId, Integer buyerId, Double price, Date dealTime) {
        this.id = id;
        this.dealType = dealType;
        this.petId = petId;
        this.sellerId = sellerId;
        this.goodsId = goodsId;
        this.buyerId = buyerId;
        this.price = price;
        this.dealTime = dealTime;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", dealType='" + dealType + '\'' +
                ", petId=" + petId +
                ", sellerId=" + sellerId +
                ", goodsId=" + goodsId +
                ", buyerId=" + buyerId +
                ", price=" + price +
                ", dealTime=" + dealTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
}
