package DBUtils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface Connection<T> {

    public static final String URL = "jdbc:mysql://localhost:3306/pet?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "linhanyao";


    int insert(T t) throws Exception;
    ResultSet getAll() throws Exception;
    int update(T t) throws Exception;
    int deleteById(Integer id) throws Exception;
}
