package services;

import java.util.List;

public interface iActifsservice<T> {

        void add(T t);


        void delete(T t);

        void update(T t,String n, int a, String b);


        List<T> readAll();

        T readById(int id);

    }


