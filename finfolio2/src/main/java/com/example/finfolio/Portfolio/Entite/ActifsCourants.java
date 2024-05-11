package com.example.finfolio.Portfolio.Entite;

import com.example.finfolio.Entite.User;

import java.time.LocalDate;

public class ActifsCourants {
    private int id;
    private String name;

    private float montant;
    private String type;
    private User user;


    public ActifsCourants() {
    }

    public ActifsCourants(String name, float montant, String type, User user) {
        this.name = name;
        this.montant = montant;
        this.type = type;
        this.user = user;

    }

    public ActifsCourants(int id, String name, float montant, String type, User user) {
        this.id = id;
        this.name = name;
        this.montant = montant;
        this.type = type;
        this.user = user;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public  User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int calculateDepreciation() {
        double annualDepreciationRate;
        int lifespan;

        switch (type) {
            case "vehicule":
                annualDepreciationRate = 0.15; // 15% per year
                lifespan = 5; // 5 years lifespan
                break;
            case "fourniture":
                annualDepreciationRate = 0.20; // 20% per year
                lifespan = 3; // 3 years lifespan
                break;
            case "Avance":
                annualDepreciationRate = 0.33; // 33% per year
                lifespan = 2; // 2 years lifespan
                break;
            case "charges":
                annualDepreciationRate = 0.40; // 40% per year
                lifespan = 1; // 1 year lifespan
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + type);
        }

        int currentYear = LocalDate.now().getYear(); // get the current year or the year you want to calculate the depreciation for
        int depreciation = (int) (montant * annualDepreciationRate * (currentYear % lifespan));

        return depreciation;
    }


    @Override
    public String toString() {
        return "ActifsCourants{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}
