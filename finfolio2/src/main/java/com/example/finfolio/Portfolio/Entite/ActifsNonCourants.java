package com.example.finfolio.Portfolio.Entite;

import com.example.finfolio.Entite.User;

import java.time.LocalDate;

public class ActifsNonCourants {
    private int id;
    String name;
    private String type;
    private float valeur;
    private float prix_achat;
    private User user;


    public ActifsNonCourants() {
    }

    public ActifsNonCourants(String type, String name,float valeur, float prix_achat, User user) {
        this.type = type;
        this.valeur = valeur;
        this.prix_achat = prix_achat;
        this.user = user;
        this.name=name;
    }

    public ActifsNonCourants(int id, String name,String type, float valeur, float prix_achat, User user) {
        this.id = id;
        this.type = type;
        this.valeur = valeur;
        this.prix_achat = prix_achat;
        this.user = user;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValeur() {
        return valeur;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    public float getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(float prix_achat) {
        this.prix_achat = prix_achat;
    }

    public User getUser() {
        return user;
    }

//    public int getUser_id() {
//        return user_id;
//    }

    public void setUser(User user) {
        this.user = user;
    }




    public int calculateDepreciation() {
        double annualDepreciationRate;
        int lifespan;

        switch (type) {
            case "logiciel":
                annualDepreciationRate = 0.15; // 15% per year
                lifespan = 5; // 5 years lifespan
                break;
            case "droit d'utilisation":
                annualDepreciationRate = 0.20; // 20% per year
                lifespan = 3; // 3 years lifespan
                break;
            case "machine":
                annualDepreciationRate = 0.33; // 33% per year
                lifespan = 2; // 2 years lifespan
                break;
            case "études":
                annualDepreciationRate = 0.40; // 40% per year
                lifespan = 1; // 1 year lifespan
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }

        int currentYear = LocalDate.now().getYear(); // get the current year or the year you want to calculate the depreciation for
        int depreciation = (int) (valeur * annualDepreciationRate * (currentYear % lifespan));

        return depreciation;
    }

    @Override
    public String toString() {
        return "AcifsNonCourants{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", valeur=" + valeur +
                ", prix_achat=" + prix_achat +
                ", user=" + user +
                '}';
    }
}
