package com.example.finfolio.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public interface IService <T> {
    void add(T t);
    void delete(T t);
    void update(T t, int id);
    T readById(int id);
    Set<T> readAll();

}