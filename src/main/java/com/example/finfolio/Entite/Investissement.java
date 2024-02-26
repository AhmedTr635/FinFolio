package com.example.finfolio.Entite;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Investissement {
    private int id;
    private double montant;
    private double prixAchat;
    private LocalDate dateAchat;
    private double ROI;
    private RealEstate Re;
    private User user;
    private double tax;

    public Investissement(int id, double montant, double prixAchat, LocalDate dateAchat, double ROI, RealEstate re, User user, double tax) {
        this.id = id;
        this.montant = montant;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
        this.ROI = ROI;
        Re = re;
        this.user = user;
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getROI() {
        return ROI;
    }

    public void setROI(double ROI) {
        this.ROI = ROI;
    }

    public RealEstate getRe() {
        return Re;
    }

    public void setRe(RealEstate re) {
        Re = re;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investissement that = (Investissement) o;
        return id == that.id && Objects.equals(Re, that.Re) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Re, user);
    }

    public Investissement() {
    }

    @Override
    public String toString() {
        return "Investissement{" +
                "id=" + id +
                ", montant=" + montant +
                ", prixAchat=" + prixAchat +
                ", dateAchat=" + dateAchat +
                ", ROI=" + ROI +
                ", Re=" + Re +
                ", user=" + user +
                ", tax=" + tax +
                '}';
    }
}