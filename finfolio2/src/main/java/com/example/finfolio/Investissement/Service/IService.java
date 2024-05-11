package com.example.finfolio.Investissement.Service;

import java.util.Set;

public interface IService <T> {
    void add(T t);
    void delete(T t);
    void update(T t, int id);
    T readById(int id);
    Set<T> readAll();

}
