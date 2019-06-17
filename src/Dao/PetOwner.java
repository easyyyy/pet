package Dao;

import java.util.List;

public class PetOwner {
    private Integer id;
    private String name;
    private String password;
    private Double balance = 0.0;
    private List<Integer> petsList;

    @Override
    public String toString() {
        return "PetOwnerDB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", petsList=" + petsList +
                '}';
    }

    public PetOwner() {
    }

    public PetOwner(Integer id, String name, String password, Double balance, List<Integer> petsList) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.petsList = petsList;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Integer> getPetsList() {
        return petsList;
    }

    public void setPetsList(List<Integer> petsList) {
        this.petsList = petsList;
    }
}
