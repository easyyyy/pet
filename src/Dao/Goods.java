package Dao;

public class Goods {
    private Integer id;
    private String name;
    private Double price;
    private Integer number;
    private Integer storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer store_id) {
        this.storeId = store_id;
    }

    public Goods() {
    }

    public Goods(Integer id, String name, Double price, Integer number) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", number=" + number +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
