package DBUtils;

import Dao.Pet;

import java.sql.*;

public class PetDB extends Utils implements Connection<Pet> {

    public PetDB() throws Exception{

    }

    @Override
    public int insert(Pet pet) throws Exception {
        int i = 0;
        String sql = "insert into pet (id,name,type_name,health,love,birthday,owner_id,store_id) values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pet.getId());
            pstmt.setString(2, pet.getName());
            pstmt.setString(3, pet.getTypeName());
            pstmt.setString(4, pet.getHealth());
            pstmt.setInt(5, pet.getLove());
            pstmt.setTimestamp(6, new Timestamp(pet.getBirthday().getTime()));
            pstmt.setInt(7, pet.getOwnerId());
            pstmt.setInt(8, pet.getStoreId());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;

    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet");
        //如果有数据，rs.next()返回true
        return rs;

    }

    public ResultSet getById(Integer id) throws Exception{
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet where id="+id);
        //如果有数据，rs.next()返回true
        return rs;
    }

    @Override
    public int update(Pet pet) throws Exception {
        int i = 0;
        String sql = "update pet set id=?,name=?,type_name=?,health=?,love=?,birthday=?,owner_id=?,store_id=? where id="+pet.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pet.getId());
            pstmt.setString(2, pet.getName());
            pstmt.setString(3, pet.getTypeName());
            pstmt.setString(4, pet.getHealth());
            pstmt.setInt(5, pet.getLove());
            pstmt.setTimestamp(6, new Timestamp(pet.getBirthday().getTime()));
            pstmt.setInt(7, pet.getOwnerId());
            pstmt.setInt(8, pet.getStoreId());

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
        String sql = "delete from pet where id="+id;
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
