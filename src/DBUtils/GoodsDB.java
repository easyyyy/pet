package DBUtils;

import Dao.Goods;

import java.sql.*;

public class GoodsDB extends Utils implements Connection<Goods> {

    public GoodsDB() throws Exception{

    }
    @Override
    public int insert(Goods goods) throws Exception {
        int i = 0;
        String sql = "insert into goods (id,name,price,number) values(?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goods.getId());
            pstmt.setString(2, goods.getName());
            pstmt.setDouble(3, goods.getPrice());
            pstmt.setInt(4, goods.getNumber());
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
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs);
        }
        return rs;
    }

    @Override
    public int update(Goods goods) throws Exception {
        int i = 0;
        String sql = "update goods set id=?,name=?,price=?,number=? where id="+goods.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, goods.getId());
            pstmt.setString(2, goods.getName());
            pstmt.setDouble(3, goods.getPrice());
            pstmt.setInt(4, goods.getNumber());
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
