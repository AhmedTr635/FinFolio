package com.example.finfolio.Entite;

import java.time.LocalDate;

public class Evennement {
    private int id;

    private String nom_event;

    private float montant;

    private LocalDate date;

    private String adresse;

    public Evennement() {
    }


    public Evennement(int id, String nom_event, float montant, LocalDate date, String adresse) {
        this.id = id;
        this.nom_event = nom_event;
        this.montant = montant;
        this.date = date;
        this.adresse= adresse;

    }

    public Evennement(String nom_event, float montant, LocalDate date, String adresse) {
        this.nom_event = nom_event;
        this.montant = montant;
        this.date = date;
        this.adresse=adresse;
    }

    public Evennement(String nomEvenemment, LocalDate dateEvenemment) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom_event;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNom(String nom_event) {
        this.nom_event = nom_event;
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
