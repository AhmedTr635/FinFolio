package services;
import java.util.List;

public interface iActifsNservices<T> {

    void add(T t);


    void delete(T t);


    void update (T t,String n, String a, float b, float c);

    List<T> readAll();

    T readById(int id);

}
