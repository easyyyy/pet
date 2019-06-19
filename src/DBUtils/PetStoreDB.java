package DBUtils;

import Dao.PetOwner;
import Dao.PetStore;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.*;

public class PetStoreDB extends Utils implements Connection<PetStore> {

    public PetStoreDB() throws Exception{

    }

    @Override
    public int insert(PetStore petStore) throws Exception {
        String sql = "insert into pet_store (name,password,balance) values(?,?,?)";
        return petStoreSQL(sql,petStore);
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet_store");
        return rs;
    }

    public ResultSet getNotSellPet(Integer storeId){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM pet where owner_id=0 and store_id="+ storeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public String getStoreNameById(Integer id){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT name FROM pet_store where id="+id);
            while (rs.next()){
                String name = rs.getString("name");
                return name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PetStore getByName(String name) throws Exception{
        String sql = "SELECT * FROM pet_store where name=?";
        ResultSet rs;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);

            rs = pstmt.executeQuery();
            PetStore petStore = new PetStore();

            if (rs.next()){

                petStore.setId(rs.getInt("id"));
                petStore.setName(rs.getString("name"));
                petStore.setPassword(rs.getString("password"));
                petStore.setBalance(rs.getDouble("balance"));
                pstmt.close();
                return petStore;

            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public int update(PetStore petStore) throws Exception {
        String sql = "update pet_store set id=?,name=?,password=?,balance=? where id="+petStore.getId();
        return petStoreSQL(sql,petStore);
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        int i = 0;
        String sql = "delete from pet_store where id="+id;
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
