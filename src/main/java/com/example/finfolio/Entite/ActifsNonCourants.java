package com.example.finfolio.Entite;

public class ActifsNonCourants {
    private int id;
    String name;
    private String type;
    private float valeur;
    private float prix_achat;
    private int user_id;

    public ActifsNonCourants() {
    }

    public ActifsNonCourants(String type, String name,float valeur, float prix_achat, int user_id) {
        this.type = type;
        this.valeur = valeur;
        this.prix_achat = prix_achat;
        this.user_id = user_id;
        this.name=name;
    }

    public ActifsNonCourants(int id, String name,String type, float valeur, float prix_achat, int user_id) {
        this.id = id;
        this.type = type;
        this.valeur = valeur;
        this.prix_achat = prix_achat;
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "AcifsNonCourants{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", valeur=" + valeur +
                ", prix_achat=" + prix_achat +
                ", user_id=" + user_id +
                '}';
    }
}
