package com.example.finfolio.Entite;

public class ActifsCourants {
    private int id;
    private String name;

    private int montant;
    private String type;
    private int user_id;




    public ActifsCourants() {
    }

    public ActifsCourants(String name,int montant, String type, int user_id ) {
        this.name=name;
        this.montant = montant;
        this.type = type;
        this.user_id = user_id;

    }

    public ActifsCourants(int id,String name, int montant, String type, int user_id) {
        this.id = id;
        this.name=name;
        this.montant = montant;
        this.type = type;
        this.user_id = user_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ActifsCourants{" +
                "id=" + id +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                '}';
    }
}
