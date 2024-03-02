package com.example.finfolio.Entite;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Depense {
    private int id ;
    private Tax tax;
    private LocalDate date ;
    private String type;
    private float montant ;
    private User user;



    public Date getSqlDate() {
        return Date.valueOf(date);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Depense(int id, Tax tax, LocalDate date, String type, float montant, User user) {
        this.id = id;
        this.tax = tax;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.user = user;
    }

    public Depense() {
    }

    public Tax getTax() {
        return tax;
    }

    public Depense(Tax tax, LocalDate date, String type, float montant, User user) {
        this.tax = tax;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.user = user;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", tax=" + tax +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", montant=" + montant +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depense depense = (Depense) o;
        return id == depense.id && Float.compare(montant, depense.montant) == 0 && Objects.equals(tax, depense.tax) && Objects.equals(date, depense.date) && Objects.equals(type, depense.type) && Objects.equals(user, depense.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tax, date, type, montant, user);
    }

    public static double calculateTotalDepenses(List<Depense> depenses) {
            double total = 0;
            for (Depense depense : depenses) {
                total += depense.getMontant();
            }
            return total;
        }
    }

