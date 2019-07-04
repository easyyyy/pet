package DBUtils;

import Dao.Hospital;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class HospitalDB extends Utils implements Connection<Hospital> {

    public HospitalDB() throws Exception {
    }

    @Override
    public int insert(Hospital hospital) throws Exception {
        int i = 0;
        String sql = "insert into hospital (name,password,balance) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, hospital.getName());
            pstmt.setString(2, hospital.getPassword());
            pstmt.setDouble(3, hospital.getBalance());

            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println("该医院名已注册，请更换");
        }
        return i;

    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM hospital");
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getByName(String name) throws Exception {
        ResultSet rs = null;
        String sql = "select * from hospital where name=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);


            rs = pstmt.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getByid(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM hospital where id="+id);
        //如果有数据，rs.next()返回true
        return rs;
    }

    @Override
    public int update(Hospital hospital) throws Exception {
        int i = 0;
        String sql = "update hospital set name=?,password=?,balance=? where id="+hospital.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, hospital.getName());
            pstmt.setString(2, hospital.getPassword());
            pstmt.setDouble(3, hospital.getBalance());

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
        String sql = "delete from hospital where id="+id;
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
