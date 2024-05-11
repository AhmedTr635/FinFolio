package com.example.finfolio.Entite;

public class Offre {
    private int id;
    private double montant;
    private double interet;
    private int user_id;
    private int credit_id;
    private String userName;
    private User user; // Add User attribute

    // Constructors
    public Offre(int id, double montant, double interet, int user_id, int credit_id, String userName) {
        this.id = id;
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
        this.userName = userName;
    }


    public Offre(int id, double montant, double interet, int user_id, int credit_id, String userName, User user) {
        this.id = id;
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
        this.userName = userName; // Set user name
        this.user = user; // Set User object
    }

    public Offre(int id, double montant, double interet, int user_id, int credit_id) {
        this.id = id;
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
    }

    public Offre(int credit_id, double montant, double interet, int user_id, User user) {
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
        this.user = user;
    }

    // Getters and setters
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

    public double getInteret() {
        return interet;
    }

    public void setInteret(double interet) {
        this.interet = interet;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(int credit_id) {
        this.credit_id = credit_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", montant=" + montant +
                ", interet=" + interet +
                ", user_id=" + user_id +
                ", credit_id=" + credit_id +
                '}';
    }
}
