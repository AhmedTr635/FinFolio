package com.example.finfolio.Entite;

import java.time.LocalDate;

public class Evennement {
    private int id;

    private String nom;

    private float montant;

    private LocalDate date;

    private String adresse;

    public Evennement() {
    }


    public Evennement(int id, String nom, float montant, LocalDate date, String adresse) {
        this.id = id;
        this.nom = nom;
        this.montant = montant;
        this.date = date;
        this.adresse= adresse;

    }

    public Evennement(String nom, float montant, LocalDate date, String adresse) {
        this.nom = nom;
        this.montant = montant;
        this.date = date;
        this.adresse=adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Evennement{" +
                "id=" + id +
                ", montant='" + montant + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


}
