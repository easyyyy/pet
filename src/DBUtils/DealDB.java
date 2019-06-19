package DBUtils;

import Dao.Deal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DealDB extends Utils implements Connection<Deal> {

    public DealDB() throws Exception {
    }

    @Override
    public int insert(Deal deal) throws Exception {
        String sql = "insert into deal (deal_type,pet_id,goods_id,seller_id,buyer_id,price,deal_time,address,phone,sign) values(?,?,?,?,?,?,?,?,?,?)";
        return dealSQL(sql,deal);
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM deal");
        return rs;
    }

    public ResultSet getDealByBuyerId(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM deal where buyer_id="+id);
        return rs;
    }

    public ResultSet getDealByStoreId(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM deal where seller_id="+id);
        return rs;
    }

    public ResultSet getDealById(Integer id) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM deal where id="+id);
        return rs;
    }

    @Override
    public int update(Deal deal) throws Exception {
        String sql = "update deal set deal_type=?,pet_id=?,goods_id=?,seller_id=?,buyer_id=?,price=?,deal_time=?,address=?,phone=?,sign=? where id="+deal.getId();
        return dealSQL(sql,deal);
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        int i = 0;
        String sql = "delete from deal where id="+id;
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
