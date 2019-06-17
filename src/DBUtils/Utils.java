package DBUtils;

import Dao.Deal;
import Dao.Goods;
import Dao.PetStore;

import java.sql.*;

public class Utils {
    public static final String URL = "jdbc:mysql://localhost:3306/pet?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "linhanyao";
    public Statement stmt;
    public java.sql.Connection conn;
    public Utils() throws Exception{

        Class.forName("com.mysql.cj.jdbc.Driver");
        //2. 获得数据库连接
        this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        this.stmt = conn.createStatement();

    }
    public int petStoreSQL(String sql, PetStore petStore){
        int i = 0;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, petStore.getId());
            pstmt.setString(2, petStore.getName());
            pstmt.setString(3, petStore.getPassword());
            pstmt.setDouble(4, petStore.getBalance());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int dealSQL(String sql, Deal deal){
        int i = 0;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deal.getId());
            pstmt.setInt(2, deal.getDealType());
            pstmt.setInt(3, deal.getPetId());
            pstmt.setInt(4, deal.getGoodsId());
            pstmt.setInt(5, deal.getSellerId());
            pstmt.setInt(6, deal.getBuyerId());
            pstmt.setDouble(7, deal.getPrice());
            pstmt.setTimestamp(8,  new Timestamp(deal.getDealTime().getTime()));
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}