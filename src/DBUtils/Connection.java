package DBUtils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface Connection<T> {

    int insert(T t) throws Exception;
    ResultSet getAll() throws Exception;
    int update(T t) throws Exception;
    int deleteById(Integer id) throws Exception;
}
