package com.example.finfolio.Entite;

import java.util.List;

public interface ICredit<T> {
    void addCredit(T t);
    void deleteCredit(T t);
     boolean deleteCredit(int idCredit);

    List<T> readAllCredits();
    void addi(T t);

}
