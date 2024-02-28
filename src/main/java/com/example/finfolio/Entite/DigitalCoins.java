package com.example.finfolio.Entite;
import java.time.LocalDate;
import java.util.*;

public class DigitalCoins {
    private int id;
    private String code;
    private double recentValue;
    private LocalDate dateAchat;
    private  LocalDate dateVente;
    private double montant;
    private float leverage;
    private double stopLoss;
    private User user;
    private double ROI;
    private double prixAchat;
    private double tax;

    public DigitalCoins(int id,String code, double recentValue, LocalDate dateAchat, LocalDate dateVente, double montant, float leverage, double stopLoss, User user, double ROI, double prixAchat, double tax) {
        this.id=id;
        this.code = code;
        this.recentValue = recentValue;
        this.dateAchat = dateAchat;
        this.dateVente = dateVente;
        this.montant = montant;
        this.leverage = leverage;
        this.stopLoss = stopLoss;
        this.user = user;
        this.ROI = ROI;
        this.prixAchat = prixAchat;
        this.tax = tax;
    }

    public DigitalCoins() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRecentValue() {
        return recentValue;
    }

    public void setRecentValue(double recentValue) {
        this.recentValue = recentValue;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public float getLeverage() {
        return leverage;
    }

    public void setLeverage(float leverage) {
        this.leverage = leverage;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getROI() {
        return ROI;
    }

    public void setROI(double ROI) {
        this.ROI = ROI;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
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
        DigitalCoins that = (DigitalCoins) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DigitalCoins{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", recentValue=" + recentValue +
                ", dateAchat=" + dateAchat +
                ", dateVente=" + dateVente +
                ", montant=" + montant +
                ", leverage=" + leverage +
                ", stopLoss=" + stopLoss +
                ", user=" + user +
                ", ROI=" + ROI +
                ", prixAchat=" + prixAchat +
                ", tax=" + tax +
                '}';
    }
}
