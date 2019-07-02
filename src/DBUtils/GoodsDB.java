package DBUtils;

import Dao.Goods;
import Dao.PetStore;

import java.sql.*;

public class GoodsDB extends Utils implements Connection<Goods> {

    public GoodsDB() throws Exception{

    }
    @Override
    public int insert(Goods goods) throws Exception {
        int i = 0;
        String sql = "insert into goods (name,price,number,store_id) values(?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getName());
            pstmt.setDouble(2, goods.getPrice());
            pstmt.setInt(3, goods.getNumber());
            pstmt.setInt(4, goods.getStoreId());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM goods");

        return rs;
    }

    public ResultSet getGoodsByStoreId(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM goods where store_id="+id);
        return rs;
    }

    public ResultSet getById(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM goods where id="+id);

        return rs;
    }

    @Override
    public int update(Goods goods) throws Exception {
        int i = 0;
        String sql = "update goods set id=?,name=?,price=?,number=?,store_id=? where id="+goods.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goods.getId());
            pstmt.setString(2, goods.getName());
            pstmt.setDouble(3, goods.getPrice());
            pstmt.setInt(4, goods.getNumber());
            pstmt.setInt(5, goods.getStoreId());
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
        String sql = "delete from goods where id="+id;
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
