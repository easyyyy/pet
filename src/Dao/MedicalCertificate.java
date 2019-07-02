package Dao;

import java.util.Date;

public class MedicalCertificate {
    private Integer id;
    private Integer petId;
    private Date date = new Date();
    private String detail = null;
    private double money = 0;
    private Integer hospitalId;
    private Integer pet_owner_id;
    private Integer diagnosis;
    private Integer paid = 0;

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Integer diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getPet_owner_id() {
        return pet_owner_id;
    }

    @Override
    public String toString() {
        return "MedicalCertificate{" +
                "id=" + id +
                ", petId=" + petId +
                ", date=" + date +
                ", detail='" + detail + '\'' +
                ", money=" + money +
                ", hospitalId=" + hospitalId +
                ", pet_owner_id=" + pet_owner_id +
                '}';
    }

    public void setPet_owner_id(Integer pet_owner_id) {
        this.pet_owner_id = pet_owner_id;
    }

    public MedicalCertificate() {
    }

    public MedicalCertificate(Integer id, Integer petId, Date date, String detail, double money, Integer hospitalId) {
        this.id = id;
        this.petId = petId;
        this.date = date;
        this.detail = detail;
        this.money = money;
        this.hospitalId = hospitalId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
}
