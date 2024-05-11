package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.RealEstate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class CellDetailsController {

    @FXML
    private Label ROI;

    @FXML
    private Label emplacement;

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label nbchambres;

    @FXML
    private Label superficie;

    @FXML
    private ProgressBar userPart;

    @FXML
    private Label valeur;
    public RealEstate realEstate;

    public CellDetailsController() {
    }
    public void setData(RealEstate re) {
        //RealEstateName.setText(re.getEmplacement());
        //System.out.println(re.getEmplacement());
        name.setText("Nom Immobilier : "+re.getName());
        emplacement.setText("Emplacement : "+re.getEmplacement());
        valeur.setText("Valeur : " + String.valueOf(re.getValeur()));
        ROI.setText("ROI : "+ String.valueOf(re.getROI()));
        superficie.setText("Superficie : "+ String.valueOf(re.getSuperficie()));
        nbchambres.setText("Nombres de chambres : "+ String.valueOf(re.getNbChambres()));

        //nbrChambre.setText(String.valueOf(re.getNbChambres())+" Chambres");

    }

}
