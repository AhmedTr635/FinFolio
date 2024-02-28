package com.example.finfolio.Entite;

import java.util.Objects;

public class User {
    private int id;
    String password ;
    private int nbrCredit;
    private String nom;
    private String prenom;
    private String email;
    private String adresse ;
    private String numTel;
    private String role;
    private float rate;

    private String solde;
    private String statut;
    private  String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public User(){};

    public User(int id,String nom,String prenom,String email,String numTel,String password,String adresse,int nbrCredit,float rate,  String role,String solde,String staut) {
        this.id = id;
        this.password = password;
        this.nbrCredit = nbrCredit;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numTel=numTel;
        this.adresse = adresse;
        this.role = role;
        this.rate = rate;
        this.solde=solde;
        this.statut=staut;
    }
    public User(int id,String nom,String prenom,String email,String numTel,String password,String adresse,int nbrCredit,float rate,  String role,String solde,String statut,String img) {
        this.id = id;
        this.password = password;
        this.nbrCredit = nbrCredit;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numTel=numTel;
        this.adresse = adresse;
        this.role = role;
        this.rate = rate;
        this.solde=solde;
        this.statut=statut;
        image=img;
    }

    public User(String nom,String prenom,String email,String numTel,String password,String adresse,int nbrCredit,float rate,  String role,String solde,String statut,String img) {
        this.password = password;
        this.nbrCredit = nbrCredit;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numTel=numTel;
        this.adresse = adresse;
        this.role = role;
        this.rate = rate;
        this.solde=solde;
        this.statut=statut;
        image=img;
    }
    public User(String nom,String prenom,String email,String numTel,int nbrCredit,float rate,  String role,String solde) {
        this.nbrCredit = nbrCredit;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numTel=numTel;
        this.role = role;
        this.rate = rate;
        this.solde=solde;
    }
    public User(int id,String nom,String prenom,String email,String password,String adresse,int nbrCredit,int rate){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.password=password;
        this.adresse=adresse;
        this.nbrCredit=nbrCredit;
        this.rate=rate;
    };
    public User(int id,String nom){
        this.id=id;
        this.nom=nom;
    }


    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumtel() {
        return numTel;
    }

    public void setNumtel(String numtel) {
        this.numTel = numtel;
    }

    public int getNbcredit() {
        return nbrCredit;
    }

    public void setNbcredit(int nbcredit) {
        this.nbrCredit = nbcredit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", nbrCredit=" + nbrCredit +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numero de telephone='" + numTel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", role='" + role + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
