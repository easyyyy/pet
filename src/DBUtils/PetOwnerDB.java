package DBUtils;

import Dao.PetOwner;

import java.sql.*;

public class PetOwnerDB extends Utils implements Connection<PetOwner> {

    public PetOwnerDB() throws Exception{


    }

    @Override
    public int insert(PetOwner petOwner){
        if (petOwner.getId()==null){
            try {
                ResultSet rs = this.getAll();
                rs.last();
                petOwner.setId(rs.getInt("id")+1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        String sql = "insert into pet_owner (id,name,password,balance,address,phone) values(?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,petOwner.getId());
            pstmt.setString(2,petOwner.getName());
            pstmt.setString(3, petOwner.getPassword());
            pstmt.setDouble(4, petOwner.getBalance());
            pstmt.setString(5, petOwner.getAddress());
            pstmt.setString(6, petOwner.getPhone());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            System.out.println("该用户名已注册，请更换");
        }
        return i;
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet_owner");
        //如果有数据，rs.next()返回true
        return rs;
    }

    public PetOwner getByName(String name) throws Exception{
        String sql = "SELECT * FROM pet_owner where name=?";
        ResultSet rs;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);

            rs = pstmt.executeQuery();
            PetOwner petOwner = new PetOwner();

            if (rs.next()){

                petOwner.setId(rs.getInt("id"));
                petOwner.setName(rs.getString("name"));
                petOwner.setPassword(rs.getString("password"));
                petOwner.setBalance(rs.getDouble("balance"));
                petOwner.setAddress(rs.getString("address"));
                petOwner.setPhone(rs.getString("phone"));
                pstmt.close();
                return petOwner;

            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ResultSet getPetByOwnerId(Integer id){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM pet where owner_id="+id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //如果有数据，rs.next()返回true
        return rs;
    }

    public ResultSet getById(Integer id){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM pet_owner where id="+id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //如果有数据，rs.next()返回true
        return rs;
    }



    @Override
    public int update(PetOwner petOwner) throws Exception {
        int i = 0;
        String sql = "update pet_owner set id=?,name=?,password=?,balance=?,address=?,phone=? where id="+petOwner.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,petOwner.getId());
            pstmt.setString(2,petOwner.getName());
            pstmt.setString(3, petOwner.getPassword());
            pstmt.setDouble(4, petOwner.getBalance());
            pstmt.setString(5, petOwner.getAddress());
            pstmt.setString(6, petOwner.getPhone());
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
        String sql = "delete from pet_owner where id="+id;
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
