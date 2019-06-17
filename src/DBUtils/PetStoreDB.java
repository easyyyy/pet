package DBUtils;

import Dao.PetStore;

import java.sql.*;

public class PetStoreDB extends Utils implements Connection<PetStore> {

    public PetStoreDB() throws Exception{

    }

    @Override
    public int insert(PetStore petStore) throws Exception {
        String sql = "insert into pet_store (id,name,password,balance) values(?,?,?,?)";
        return petStoreSQL(sql,petStore);
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet_store");
        return rs;
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
