package com.example.finfolio.Entite;

import java.util.List;

public class Tax {

    private int id;
    private double montantTax;
    private String type;
    private String optimisation;
    private User user ;

    public Tax(double montantTax, String type, String optimisation, User user) {
        this.montantTax = montantTax;
        this.type = type;
        this.optimisation = optimisation;
        this.user = user;
    }

    public double getMontantTax() {
        return montantTax;
    }

    public void setMontantTax(double montantTax) {
        this.montantTax = montantTax;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double  getmontantTax() {
        return montantTax;
    }

    public void setmontantTax(double montantTax) {
        this.montantTax = montantTax;
    }

    public String getType() {
        return type;
    }

    public Tax(int id, double montantTax, String type, String optimisation) {
        this.id = id;
        this.montantTax = montantTax;
        this.type = type;
        this.optimisation = optimisation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptimisation() {
        return optimisation;
    }

    public Tax() {
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id=" + id +
                ", montantTax=" + montantTax +
                ", type='" + type + '\'' +
                ", optimisation='" + optimisation + '\'' +
                '}';
    }

    public Tax(double montantTax, String type, String optimisation) {
        this.montantTax = montantTax;
        this.type = type;
        this.optimisation = optimisation;
    }
    public static double calculateTotalTax(List<Tax> taxs) {
        double total = 0;
        for (Tax tax : taxs) {
            total += tax.getmontantTax();
        }
        return total;
    }
    public void setOptimisation(String optimisation) {
        this.optimisation = optimisation;
    }


   /* public double calculeTaxDepense(Depense d) {
        return d.getMontant() * 0.14;
    }*/
/*
public double calculeTaxRS(RealState rs,float montantTax ){
        return rs.roi_Annuel()*montantTax*0.08;
}
public double calcluleTaxGiving(Credit c){
        return c.getmontantTax()* c.getInteret()*0.05;

}
public double calculeTaxDonation(Donation d)

}*/
}