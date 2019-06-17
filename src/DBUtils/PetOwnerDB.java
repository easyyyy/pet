package DBUtils;

import Dao.PetOwner;

import java.sql.*;

public class PetOwnerDB implements Connection<PetOwner> {
    private Statement stmt;
    private java.sql.Connection conn;
    public PetOwnerDB() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2. 获得数据库连接
        this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        this.stmt = conn.createStatement();

    }

    @Override
    public int insert(PetOwner petOwner) throws Exception {
        int i = 0;
        String sql = "insert into pet_owner (id,name,password,balance) values(?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,petOwner.getId());
            pstmt.setString(2,petOwner.getName());
            pstmt.setString(3, petOwner.getPassword());
            pstmt.setDouble(4, petOwner.getBalance());
            i = pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public ResultSet getAll() throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet_owner");
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs);
        }
        return rs;
    }

    @Override
    public int update(PetOwner petOwner) throws Exception {
        int i = 0;
        String sql = "update pet_owner set id=?,name=?,password=?,balance=? where id="+petOwner.getId();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,petOwner.getId());
            pstmt.setString(2,petOwner.getName());
            pstmt.setString(3, petOwner.getPassword());
            pstmt.setDouble(4, petOwner.getBalance());
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
