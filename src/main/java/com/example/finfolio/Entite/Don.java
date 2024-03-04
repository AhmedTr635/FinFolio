package com.example.finfolio.Entite;

public class Don  {
    private int id;

    private float montant_user;

    private User user;

    private Evennement evennement;

    public Don() {
    }

    public Don(int id, float montant_user, User user, Evennement evennement) {
        this.id = id;
        this.montant_user = montant_user;
        this.user = user;
        this.evennement = evennement;
    }

    public Don(float montant_user, User user, Evennement evennement) {
        this.montant_user = montant_user;
        this.user = user;
        this.evennement = evennement;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant_user() {
        return montant_user;
    }

    public void setMontant_user(float montant_user) {
        this.montant_user = montant_user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evennement getEvennement() {
        return evennement;
    }

    public void setEvenement(Evennement evennement) {
        this.evennement = evennement;
    }

    @Override
    public String toString() {
        return "Don{" +
                "id=" + id +
                ", montant_user='" + montant_user + '\'' +
                ", user_id='" + user + '\'' +
                ", evenement_id='" + evennement + '\'' +
                '}';
    }
}





