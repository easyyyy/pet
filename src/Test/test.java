package Test;
import service.User;

public class test {

    public static final String URL = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "linhanyao";

    public static void main(String[] args) throws Exception {
        //1.加载驱动程序
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        //2. 获得数据库连接
//        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//        //3.操作数据库，实现增删改查
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM city");
//        //如果有数据，rs.next()返回true
//        while(rs.next()){
//            System.out.println(rs.getString(1));
//        }
//        GoodsDB goodsDB = new GoodsDB();
//        goodsDB.getAll();
//        PetDB petDB = new PetDB();
//        ResultSet rs = petDB.getAll();
//        while(rs.next()){
//            System.out.println(rs.getString("name"));
//        }

        User login = new User();
        login.loginByOwner();
    }

}
