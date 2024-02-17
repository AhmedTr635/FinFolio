package interfaces;

import entite.User;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    public void  add(T t) throws SQLException;
    public int  update(T t,T t2)throws SQLException;
    public List<User> readAll()throws SQLException;
    public void delete (T t)throws SQLException;

}
