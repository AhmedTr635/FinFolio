package com.example.finfolio.Entite;

public class Tax {

    private int id;
    private double montantTax;
    private String type;
    private String optimisation;

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