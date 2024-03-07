package com.example.finfolio.Entite;

public class Credit {
    private int id;
    private double montant;
    private double interetMax;
    private double interetMin;
    private String dateD;
    private String dateF;
    private User user;
    private String userName; // New field for the user's name

    // Constructors, getters, and setters

    public Credit(int id, double montant, double interetMax, double interetMin, String dateD, String dateF, User user_id, String userName) {
        this.id = id;
        this.montant = montant;
        this.interetMax = interetMax;
        this.interetMin = interetMin;
        this.dateD = dateD;
        this.dateF = dateF;
        this.user = user_id;
        this.userName = userName;
    }

    public Credit(double montant, double interetMax, double interetMin, String dateD, String dateF, User user_id) {
        this.id = id;
        this.montant = montant;
        this.interetMax = interetMax;
        this.interetMin = interetMin;
        this.dateD = dateD;
        this.dateF = dateF;
        this.user = user_id;
    }

    public Credit(double montant, double interetMax, double interetMin, String dateD, String dateF, User user_id,String userName) {
        this.id = id;
        this.montant = montant;
        this.interetMax = interetMax;
        this.interetMin = interetMin;
        this.dateD = dateD;
        this.dateF = dateF;
        this.user = user_id;
        this.userName=userName;
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

    public double getInteretMax() {
        return interetMax;
    }

    public void setInteretMax(double interetMax) {
        this.interetMax = interetMax;
    }

    public double getInteretMin() {
        return interetMin;
    }

    public void setInteretMin(double interetMin) {
        this.interetMin = interetMin;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", montant=" + montant +
                ", interetMax=" + interetMax +
                ", interetMin=" + interetMin +
                ", dateD='" + dateD + '\'' +
                ", dateF='" + dateF + '\'' +
                ", user_id=" + user +
                ", userName='" + userName + '\'' +
                '}';
    }
}
