package Test;
import DBUtils.PetDB;
import DBUtils.PetStoreDB;
import Dao.Pet;
import Dao.PetStore;
import service.PetOwnerService;
import service.PetStoreService;

import java.sql.Date;
import java.sql.ResultSet;

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

//        PetOwnerService petOwnerService = new PetOwnerService();
////        petOwnerService.changeBalance(petOwnerService.loginByOwner());
//        Pet pet = new Pet(3,"lin","dog","good",1,1,1,100.0);
//////        PetDB petDB = new PetDB();
//////        ResultSet rs = petDB.getById(3);
//////        while (rs.next()){
//////            System.out.println(rs);
//////        }
//        petOwnerService.buyingPet(petOwnerService.loginByOwner(),pet);

//        petOwnerService.getInfoById(1);

        PetStoreService petStoreService = new PetStoreService();
        petStoreService.printNotSellPet(1);

    }

}
