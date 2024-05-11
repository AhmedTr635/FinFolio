package com.example.finfolio.Investissement.Entite;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RealEstate {
    private int id;
    private String name;
    private byte[] imageData;
    private String emplacement;
    private float ROI;
    private double valeur;
    private int nbChambres;
    private float superficie;
    private int nbrclick;
    private transient double totalPart;
    private Map<User, Double> userParticipation = new HashMap<>();


    public RealEstate() {
    }


    public RealEstate(int id, String name, byte[] imageData, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick) {
        this.id = id;
        this.name = name;
        this.imageData = imageData;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.nbrclick = nbrclick;
    }

    public RealEstate(int id, String name, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick, Map<User, Double> userParticipation) {
        this.id = id;
        this.name = name;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.nbrclick = nbrclick;
        this.userParticipation = userParticipation;
    }

    public RealEstate(int id, String name, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick) {
        this.id = id;
        this.name = name;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.nbrclick = nbrclick;
    }

    public RealEstate(String name, String emplacement) {
        this.name = name;
        this.emplacement = emplacement;
    }


    public double getTotalPart() {
        return totalPart;
    }

    public void setTotalPart(double totalPart) {
        this.totalPart = totalPart;
    }

    public RealEstate(int id, String name, String emplacement, float ROI, double valeur, double totalPart) {
        this.id = id;
        this.name = name;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.totalPart = totalPart;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public RealEstate(int id, String emplacement, float ROI, double valeur, int nbChambres, float superficie, int nbrclick) {
        this.id = id;
        this.emplacement = emplacement;
        this.ROI = ROI;
        this.valeur = valeur;
        this.nbChambres = nbChambres;
        this.superficie = superficie;
        this.nbrclick = nbrclick;
    }

    public int getNbrclick() {
        return nbrclick;
    }

    public void setNbrclick(int nbrclick) {
        this.nbrclick = nbrclick;
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

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", ROI=" + ROI +
                ", valeur=" + valeur +
                ", nbChambres=" + nbChambres +
                ", superficie=" + superficie +
                ", nbrclick=" + nbrclick +
                ", userParticipation=" + userParticipation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealEstate that = (RealEstate) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
