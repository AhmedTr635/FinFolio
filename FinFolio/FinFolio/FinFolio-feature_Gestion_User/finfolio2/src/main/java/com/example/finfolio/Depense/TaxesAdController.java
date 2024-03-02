package com.example.finfolio.Depense;

import com.example.finfolio.Entite.Depense;
import com.example.finfolio.Entite.Tax;
import com.example.finfolio.Service.TaxService;

import java.util.List;


public class TaxesAdController {



    public double sommeTaxByDepense(){
        TaxService ts= new TaxService();
        List<Tax> taxes = ts.readAll();
        double somme = taxes.stream()
                .filter(tax -> tax.getType().equals("depense"))
                .mapToDouble(Tax::getmontantTax)
                .sum();
        return somme;


    }


}
