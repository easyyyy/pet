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
            pstmt.setString(1, petStore.getName());
            pstmt.setString(2, petStore.getPassword());
            pstmt.setDouble(3, petStore.getBalance());
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

            pstmt.setInt(1, deal.getDealType());
            pstmt.setInt(2, deal.getPetId());

            pstmt.setInt(3, deal.getGoodsId());
            pstmt.setInt(4, deal.getSellerId());
            pstmt.setInt(5, deal.getBuyerId());
            pstmt.setDouble(6, deal.getPrice());
            pstmt.setTimestamp(7,  new Timestamp(deal.getDealTime().getTime()));
            pstmt.setString(8, deal.getAddress());
            pstmt.setString(9, deal.getPhone());
            pstmt.setInt(10, deal.getSign());
            pstmt.setInt(11,deal.getNumber());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
