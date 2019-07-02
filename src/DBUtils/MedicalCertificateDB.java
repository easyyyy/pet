package DBUtils;

import Dao.Hospital;
import Dao.MedicalCertificate;
import Dao.PetOwner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MedicalCertificateDB extends Utils implements Connection<MedicalCertificate> {
    public MedicalCertificateDB() throws Exception {
    }

    @Override
    public int insert(MedicalCertificate medicalCertificate) throws Exception {
        int i = 0;
        String sql = "insert into medical_certificate (pet_id,date,detail,money,hospital_id,pet_owner_id,diagnosis,paid) values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, medicalCertificate.getPetId());
            pstmt.setTimestamp(2, new Timestamp(medicalCertificate.getDate().getTime()));
            pstmt.setString(3, medicalCertificate.getDetail());
            pstmt.setDouble(4,medicalCertificate.getMoney());
            pstmt.setInt(5, medicalCertificate.getHospitalId());
            pstmt.setInt(6,medicalCertificate.getPet_owner_id());
            pstmt.setInt(7,medicalCertificate.getDiagnosis());
            pstmt.setInt(8,medicalCertificate.getPaid());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_certificate");
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getById(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_certificate where id="+id);
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getAllDiagnosisByHospitalId(Integer id) throws Exception{
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_certificate where hospital_id="+id);
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getWaitDiagnosisByHospitalId(Integer id) throws Exception{
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_certificate where hospital_id="+id+" and diagnosis=0");
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getAllByOwnerId(Integer id) throws Exception{
        ResultSet rs = stmt.executeQuery("SELECT * FROM medical_certificate where pet_owner_id="+id);
        //如果有数据，rs.next()返回true
        return rs;
    }

    @Override
    public int update(MedicalCertificate medicalCertificate) throws Exception {
        int i = 0;
        String sql = "update medical_certificate set pet_id=?,date=?,detail=?,money=?,hospital_id=?,pet_owner_id=?,diagnosis=?,paid=? where id="+medicalCertificate.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, medicalCertificate.getPetId());
            pstmt.setTimestamp(2, new Timestamp(medicalCertificate.getDate().getTime()));
            pstmt.setString(3, medicalCertificate.getDetail());
            pstmt.setDouble(4,medicalCertificate.getMoney());
            pstmt.setInt(5, medicalCertificate.getHospitalId());
            pstmt.setInt(6,medicalCertificate.getPet_owner_id());
            pstmt.setInt(7,medicalCertificate.getDiagnosis());
            pstmt.setInt(8,medicalCertificate.getPaid());

            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        int i = 0;
        String sql = "delete from medical_certificate where id="+id;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
