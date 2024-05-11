package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RealEstateCellController /*implements Initializable*/ {
    /*public RealEstateCellController(RealEstate realEstate) {
        this.realEstate = realEstate;
    }*/

    @FXML
    public Label RealEstateName;
    public Label name;
    public Label emplacement;
    public Label valeur;
    public Label Roi;
    public Label superficie;
    public Label nbchambres;

    @FXML
    private Pane cell;

    @FXML
    private AnchorPane cellkbira;

    @FXML
    private ImageView imageRealEstate;
    public RealEstate realEstate;
    @FXML
    public Label nbrChambre;
    RealEstateService reS = new RealEstateService();
    private int idRE;
    private RealEstateService realEstateService=new RealEstateService();

    public Label getRealEstateName() {
        return RealEstateName;
    }

    public void setRealEstateName(Label realEstateName) {
        RealEstateName = realEstateName;
    }

    public Pane getCell() {
        return cell;
    }

    public void setCell(Pane cell) {
        this.cell = cell;
    }

    public ImageView getImageRealEstate() {
        return imageRealEstate;
    }

    public void setImageRealEstate(ImageView imageRealEstate) {
        this.imageRealEstate = imageRealEstate;
    }

    public RealEstateService getReS() {
        return reS;
    }

    public void setReS(RealEstateService reS) {
        this.reS = reS;
    }

    public int getIdRE() {
        return idRE;
    }

    public void setIdRE(int idRE) {
        this.idRE = idRE;
    }

    public RealEstateService getRealEstateService() {
        return realEstateService;
    }

    public void setRealEstateService(RealEstateService realEstateService) {
        this.realEstateService = realEstateService;
    }

    @FXML
    void ZoomRE(MouseEvent event) throws NullPointerException {
        // RealEstateCellController.java
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/RealEstateUser.fxml"));
        try {
            Parent root = loader.load();
            RealEstateUserController realEstateUserController = loader.getController();

            // Pass the real estate details to the RealEstateUserController
            realEstateUserController.setDataDetails(realEstate);

            // Add the pane of RealEstateUserController to the details AnchorPane
            realEstateUserController.details.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        /*
        realEstateUserController.roi.setText(String.valueOf(realEstate.getROI()));*/
       /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/RealEstateUser.fxml"));
        RealEstateUserController realEstateUserController = fxmlLoader.getController();

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/example/demo1/CellDetails.fxml"));

        CellDetailsController cellDetailsController = fxmlLoader1.getController();
        cellDetailsController.setData(realEstate);
        realEstateUserController.details.getChildren().add(fxmlLoader1.load());*/

        /*try {
            // Load the RealEstateCell.fxml

            *//*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/CellDetails.fxml"));

            AnchorPane cell = fxmlLoader.load();
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/example/demo1/RealEstateUser.fxml"));
            RealEstateUserController realEstateUserController = fxmlLoader1.getController();

            // Get the controller
            CellDetailsController controller = fxmlLoader.getController();

            // Set real estate data
            //controller.setRealEstate(realEstate);
            //System.out.println(realEstate);
            controller.setData(realEstate);
            //controller.setRealEstate(realEstate);

            //System.out.println("hne");
            //controller.RealEstateName.setText(realEstate.getEmplacement());
            //controller.zoom(mouseEvent);

            // Add the cell to the affichageRE HBox
            realEstateUserController.details.getChildren().add(cell);
            //System.out.println("hiii");*//*

        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
    public void setData(RealEstate re) {
        //RealEstateName.setText(re.getEmplacement());
        //System.out.println(re.getEmplacement());
        name.setText("Nom: "+re.getName());
        emplacement.setText("Emplacement: "+re.getEmplacement());
        valeur.setText(String.valueOf("Valeur:"+re.getValeur()));
        Roi.setText(String.valueOf("ROI: "+re.getROI()));
        /*superficie.setText(String.valueOf(re.getSuperficie()));
        nbchambres.setText(String.valueOf(re.getNbChambres()));*/

        //nbrChambre.setText(String.valueOf(re.getNbChambres())+" Chambres");

    }

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fetch real estate name using realEstateId and set it in the label
        RealEstateName.setText(realEstate.getEmplacement());

    }*/

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate=realEstate;    }

    public RealEstate getRealEstate() {
        return realEstate;
    }


    public void zoom(MouseEvent mouseEvent) {

    }

    public void Investissement(MouseEvent event) {
        try {
            System.out.println(realEstate);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/AddRE.fxml"));
                Parent root = fxmlLoader.load();/*FXMLLoader.load(getClass().getResource("/com/example/demo1/AddRE.fxml"));*/
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = new Stage();
            AddREController addREController = fxmlLoader.getController();
            addREController.setData(realEstate);
            addREController.setRe(realEstate);
            int i = realEstateService.readNbrClickById(realEstate.getId());
            realEstateService.updateNbrClick(realEstate.getId(), i + 1);


            /*AddREController addREController = new AddREController();
            addREController.nom.setText(realEstate.getEmplacement());
            addREController.superficie.setText(String.valueOf(realEstate.getSuperficie()));
            addREController.nbChabres.setText(String.valueOf(realEstate.getNbChambres()));
            addREController.valeur.setText(String.valueOf(realEstate.getValeur()));
            addREController.Roi.setText(String.valueOf(realEstate.getROI()));*/


            // Set the scene to the stage
            //stage.setScene(scene);

            // Show the stage
            //stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void details(MouseEvent mouseEvent) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/RealEstateUser.fxml"));
        RealEstateUserController realEstateUserController = fxmlLoader.getController();

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/example/demo1/CellDetails.fxml"));
        CellDetailsController cellDetailsController;

         cellDetailsController = fxmlLoader1.getController();
        cellDetailsController.setData(realEstate);
        realEstateUserController.details.getChildren().add(fxmlLoader1.load());*/
    }

    public void invest(ActionEvent actionEvent) {
        try {
            System.out.println(realEstate);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/AddRE.fxml"));
            Parent root = fxmlLoader.load();/*FXMLLoader.load(getClass().getResource("/com/example/demo1/AddRE.fxml"));*/
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = new Stage();
            AddREController addREController = fxmlLoader.getController();
            addREController.setData(realEstate);
            addREController.setRe(realEstate);
            int i = realEstateService.readNbrClickById(realEstate.getId());
            realEstateService.updateNbrClick(realEstate.getId(), i + 1);


            /*AddREController addREController = new AddREController();
            addREController.nom.setText(realEstate.getEmplacement());
            addREController.superficie.setText(String.valueOf(realEstate.getSuperficie()));
            addREController.nbChabres.setText(String.valueOf(realEstate.getNbChambres()));
            addREController.valeur.setText(String.valueOf(realEstate.getValeur()));
            addREController.Roi.setText(String.valueOf(realEstate.getROI()));*/


            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
