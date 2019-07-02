package service;

import DBUtils.MedicalCertificateDB;
import Dao.MedicalCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalCertificateService {

    MedicalCertificateDB medicalCertificateDB;

    {
        try {
            medicalCertificateDB = new MedicalCertificateDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MedicalCertificate> substantialize(ResultSet rs){
        List<MedicalCertificate> medicalCertificateList = new ArrayList<>();
        try {
            while (rs.next()){
                MedicalCertificate medicalCertificate = new MedicalCertificate();
                medicalCertificate.setId(rs.getInt("id"));
                medicalCertificate.setPetId(rs.getInt("pet_id"));
                medicalCertificate.setDate(rs.getDate("date"));
                medicalCertificate.setDetail(rs.getString("detail"));
                medicalCertificate.setMoney(rs.getDouble("money"));
                medicalCertificate.setHospitalId(rs.getInt("hospital_id"));
                medicalCertificate.setPet_owner_id(rs.getInt("pet_owner_id"));
                medicalCertificate.setDiagnosis(rs.getInt("diagnosis"));
                medicalCertificate.setPaid(rs.getInt("paid"));
                medicalCertificateList.add(medicalCertificate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalCertificateList;
    }
}
