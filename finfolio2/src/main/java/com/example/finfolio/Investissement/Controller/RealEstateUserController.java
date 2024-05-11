/*
package com.example.demo1.Controller;

import com.example.demo1.Controller.RealEstateCellController;
import com.example.demo1.Entite.RealEstate;
import com.example.demo1.Service.RealEstateService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TestController {
    @FXML
    private Pane BigPane;

    @FXML
    private VBox BigRE;
    */
/*public void setBigPane(RealEstate re)
    {
        this.emplacement.setText(re.getEmplacement());
        this.nbrChambres.setText(String.valueOf(re.getNbChambres()));
    }*//*


    @FXML
    private Label ROI;

    @FXML
    private Label RealEstateName;

    @FXML
    private VBox affichageRE;

    @FXML
    private Button chercher;

    @FXML
    private Label emplacement;

    @FXML
    private ComboBox<?> filtre;

    @FXML
    private ImageView imageRE;

    @FXML
    private Button investir;

    @FXML
    private Label nbrChambres;

    @FXML
    private Label prixtotal;

    @FXML
    private TextField recherche;

    @FXML
    private Label superficie;

    @FXML
    private Label userParticipation;
    @FXML
    private Button Annuler;



    RealEstateService reS=new RealEstateService();
    RealEstate re=new RealEstate();
    Set<RealEstate> realEstateSet = new HashSet<>();




    @FXML
    void InvestissementAddRE(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/AddRE.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void chercher(MouseEvent event) {

    }
    RealEstateService realEstateService=new RealEstateService();

    public void initialize() {
        // Fetch all real estates from the database
        Set<RealEstate> realEstateSet = realEstateService.readAll();
        System.out.println(realEstateSet);

        // Create and add a cell for each real estate
        for (RealEstate realEstate : realEstateSet) {
            try {
                // Load the RealEstateCell.fxml

                */
/*URL fxmlLocation = getClass().getResource("/com/example/demo1/RealEstateCell.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);*//*

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/RealEstateCell.fxml"));

                AnchorPane cell = fxmlLoader.load();

                // Get the controller
                SisiCellController controller = fxmlLoader.getController();

                // Set real estate data
                //controller.setRealEstate(realEstate);
                controller.setData(realEstate);

                // Add the cell to the affichageRE HBox
                affichageRE.getChildren().addAll(cell);
                System.out.println("hiii");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void retourInvMain(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/InvestissementUser.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    */
/*public void updateRealEstateInfo(RealEstate realEstate) {
        if (realEstate != null) {
            this.RealEstateName.setText(realEstate.getEmplacement());
            this.nbrChambres.setText(String.valueOf(realEstate.getNbChambres()));
        } else {
            System.out.println("RealEstate object is null");
        }
        // Add more label updates here based on the properties of RealEstate
    }*//*


    }



*/
package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RealEstateUserController {

    public Label emplacement;
    public VBox RealEstateDetail;
    public Label superficie;

    public Label roi;

    public Label name;
    public TextField recherche;
    public Button chercher;
    public Button triroi;
    public Pane details;
    public Pane details1;
    public ImageView img;
    public Label ROI;
    public Label valeur;
    public Label nbchambres;
    public ProgressBar userPart;
    public RealEstate realEstateDetail;
    public Button quitter;

    @FXML
    private VBox affichageRE;
    public Button preced_btn;
    public AnchorPane description_pane;
    public Label event_goal1;
    public Label event_id;
    public Button btnpart;
    public Label event_goal;
    public Label event_place;
    public Label event_date;
    public Label event_name;
    public AnchorPane events_pane;
    public ImageView imageRealEstate;
    public Label RealEstateName;
    public AnchorPane cellkbira;
    public Pane cell;
    public RealEstate realEstate;
RealEstateService realEstateService =new RealEstateService();
    public RealEstate getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }

    public void ZoomRE(MouseEvent mouseEvent) {

            /*TestController tc = new TestController();
            tc.setBigPane(realEstate);*/



    }

    public void updateEmplacementLabel(String value) {
        emplacement.setText(value);
    }



    public void setData(RealEstate re) {
        RealEstateName.setText(re.getEmplacement());

    }
    public void setDataDetails(RealEstate re) {
        // RealEstateUserController.java
        System.out.println("setDataDetails : "+re);
        Platform.runLater(() -> {
            name.setText(re.getName());
            emplacement.setText(re.getEmplacement());
            valeur.setText(String.valueOf(re.getValeur()));
            ROI.setText(String.valueOf(re.getROI()));
            superficie.setText(String.valueOf(re.getSuperficie()));
            nbchambres.setText(String.valueOf(re.getNbChambres()));
        });

    }


    public void InvestissementAddRE(MouseEvent mouseEvent) {
    }

    public void retourInvMain(MouseEvent mouseEvent) {
    }

    public void chercher(ActionEvent mouseEvent) throws SQLException {
        String searchTerm = recherche.getText();
        RealEstateService EvennementService = new RealEstateService();

        // Fetch events from EvennementService based on the search term
        List<RealEstate> events = EvennementService.rechercherEvent(searchTerm);
        System.out.println(events);

        // Clear existing content in the event container
        affichageRE.getChildren().clear();

        // Load matching events into the event container
        for (RealEstate evnt : events) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/RealEstateCell.fxml"));

                AnchorPane cell = fxmlLoader.load();
                RealEstateCellController controller = fxmlLoader.getController();
                //System.out.println(evnt.getName());


                controller.setData(evnt);
                controller.setRealEstate(evnt);

                // Add the cell to the container.
                affichageRE.getChildren().add(cell);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void initialize() {
        // Fetch all real estates from the database
        Set<RealEstate> realEstateSet = realEstateService.readAllNbrClick();
        //System.out.println(realEstateSet);


        // Create and add a cell for each real estate
        for (RealEstate realEstate : realEstateSet) {
            try {
                // Load the RealEstateCell.fxml

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/RealEstateCell.fxml"));

                AnchorPane cell = fxmlLoader.load();

                // Get the controller
                RealEstateCellController controller = fxmlLoader.getController();

                // Set real estate data
                //controller.setRealEstate(realEstate);
                System.out.println(realEstate);
                controller.setData(realEstate);
                controller.setRealEstate(realEstate);

                //System.out.println("hne");
                //controller.RealEstateName.setText(realEstate.getEmplacement());
                //controller.zoom(mouseEvent);

                // Add the cell to the affichageRE HBox
                affichageRE.getChildren().addAll(cell);
                //System.out.println("hiii");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void TriROI(ActionEvent actionEvent) {
        //String searchTerm = recherche.getText();
        Set<RealEstate> realEstateSet = realEstateService.readAllNbrClick();
        RealEstateService EvennementService = new RealEstateService();

        // Fetch events from EvennementService based on the search term
        List<RealEstate> events = EvennementService.sortROI(realEstateSet);

        // Clear existing content in the event container
        affichageRE.getChildren().clear();

        // Load matching events into the event container
        for (RealEstate evnt : events) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/RealEstateCell.fxml"));

                AnchorPane cell = fxmlLoader.load();
                RealEstateCellController controller = fxmlLoader.getController();
                //System.out.println(evnt.getName());


                controller.setData(evnt);
                controller.setRealEstate(evnt);

                // Add the cell to the container.
                affichageRE.getChildren().add(cell);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void quitter(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/finfolio/User/Investissement/InvestissementUser.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
