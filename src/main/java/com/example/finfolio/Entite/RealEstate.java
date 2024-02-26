package com.example.finfolio.Entite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RealEstate {
    private int id;
    private String emplacement;
    private float ROI;
    private double valeur;
    private int nbChambres;
    private float superficie;
    private Map<User, Double> userParticipation = new HashMap<>();


    public RealEstate() {
    }

    public RealEstate(int id, String emplacement, float ROI, double valeur, int nbChambres, float superficie) {
        this.id = id;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.userParticipation= new HashMap<>();
    }

    public RealEstate(int id, String emplacement, float ROI, double valeur, int nbChambres, float superficie, Map<User, Double> userParticipation) {
        this.id = id;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.userParticipation = userParticipation;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public float getROI() {
        return ROI;
    }

    public void setROI(float ROI) {
        this.ROI = ROI;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public int getNbChambres() {
        return nbChambres;
    }

    public void setNbChambres(int nbChambres) {
        this.nbChambres = nbChambres;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public Map<User, Double> getUserParticipation() {
        return userParticipation;
    }

    public void setUserParticipation(Map<User, Double> userParticipation) {
        this.userParticipation = userParticipation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

