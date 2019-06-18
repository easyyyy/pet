package Dao;

public class PetStore {
    private Integer id;
    private String name;
    private String password;
    private Double balance = 0.0;

    public PetStore() {
    }

    public PetStore(Integer id, String name, String password, Double balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "PetStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
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
}
