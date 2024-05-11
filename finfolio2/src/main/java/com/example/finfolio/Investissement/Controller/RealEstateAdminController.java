package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.InvestissementService;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RealEstateAdminController {

    public TableColumn MontantColumn;
    public TableColumn InvDateColumn;
    public TableColumn userPrenomColumn;
    public TableColumn userNameColumn;
    public TableView InvestissementTable;
    public TableColumn userID;
    public TableColumn tax;
    @FXML
    private TableView<RealEstate> RealEstate_table;

    @FXML
    private TableColumn<RealEstate, Void> event_action;

    @FXML
    private TableColumn<RealEstate, String> emplacement;

    @FXML
    private TableColumn<RealEstate, String> name;

    @FXML
    private TableColumn<RealEstate, Integer> nbchambres;

    @FXML
    private TableColumn<RealEstate, Integer> nbclick;

    @FXML
    private TableColumn<RealEstate, Float> superficie;

    @FXML
    private TableColumn<RealEstate, Double> valeur;


    @FXML
    private TextField search_field;

    @FXML
    private Button ajouter_btn;

    @FXML
    private AnchorPane big_container;

    @FXML
    private Button search_btn;

    private RealEstateService realEstateService;
    private InvestissementService investissementService=new InvestissementService();
    private ObservableList<RealEstate> realEstateObservableList = FXCollections.observableArrayList();
    RealEstateService RealEstateService = new RealEstateService();

    @FXML
    void initialize() {
        realEstateService = new RealEstateService();

        // Initialize other table columns...
        emplacement.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        nbchambres.setCellValueFactory(new PropertyValueFactory<>("nbChambres"));
        nbclick.setCellValueFactory(new PropertyValueFactory<>("nbrclick"));
        superficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));

        // Configure user participation table columns
        userID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tax.setCellValueFactory(new PropertyValueFactory<>("tax"));
        InvDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));

        event_action.setCellFactory(column -> {

            return new TableCell<>() {


                /*final Button deleteButton = new Button("Supprimer");*/
                final Button modifyButton = new Button("Modifier");

                {
                    /*deleteButton.setOnAction(event -> {
                        RealEstate e = getTableView().getItems().get(getIndex());
                        handleDeleteEvent(e);
                    });*/

                    modifyButton.setOnAction(event -> {
                        RealEstate e = getTableView().getItems().get(getIndex());
                        //System.out.println(e);
                        handleModifyEvent(event,e);
                        ModifierREController controller = new ModifierREController();
                        controller.re=e;
                    });
                    /*deleteButton.getStyleClass().add("custom-button");*/
                    modifyButton.getStyleClass().add("custom-button");
                    event_action.getStyleClass().add("action-column");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttonBox = new HBox( modifyButton);
                        setGraphic(buttonBox);
                    }
                }
            };
        });

        // Load real estates and user participations
        loadRealEstatesFromDatabase();
    }
    @FXML
    void handleDeleteEvent(RealEstate event) {


        RealEstateService.delete(event);

        //refreshTableView();
        //loadDonsFromDatabase();
    }
    private void handleModifyEvent(ActionEvent event, RealEstate realEstate) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/ModifierRE.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            ModifierREController controller = loader.getController();
            RealEstateService realEstateService = new RealEstateService();

            //Image image = new Image(new ByteArrayInputStream(String.valueOf(realEstateService.readImage(realEstate.getId())).getBytes()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //baos.write(String.valueOf(realEstateService.readImage(realEstate.getId())).getBytes());
            if(realEstate.getImageData()!=null){
                //realEstate.setImageData((new ByteArrayInputStream(realEstate.getImageData())));
            }

            realEstate.setImageData(baos.toByteArray());
            controller.initData(realEstate); // Pass the RealEstate object to the ModifierREController

            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = new Stage();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadRealEstatesFromDatabase() {
        // Load real estates into the table
        Set<RealEstate> realEstates = realEstateService.readAllNbrClick();
        System.out.println(realEstates);
        ObservableList<RealEstate> realEstateList = FXCollections.observableArrayList(realEstates);
        realEstateObservableList = FXCollections.observableArrayList(realEstates);

        RealEstate_table.setItems(realEstateList);

        // Handle selection change in the real estate table
        RealEstate_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fetchAndLoadInvestissementsByRealEstateId(newValue.getId());
            }
        });
    }
    private void refreshTableView() {
        loadRealEstatesFromDatabase();
    }

   /* private void fetchAndLoadUserParticipations(RealEstate realEstate) {
        // Fetch user participations for the selected real estate
        Map<User, Double> userParticipations = realEstateService.fetchUserParticipation(realEstate.getId());

        // Convert user participations to a list of UserParticipation objects
        List<UserParticipation> userParticipationList = userParticipations.entrySet().stream()
                .map(entry -> new UserParticipation(entry.getKey().getName(), entry.getKey().getPrenom(), entry.getValue()))
                .collect(Collectors.toList());

        // Load user participations into the table
        ObservableList<UserParticipation> userParticipationObservableList = FXCollections.observableArrayList(userParticipationList);
        UserParticipationTable.setItems(userParticipationObservableList);
    }*/
   private void fetchAndLoadInvestissementsByRealEstateId(int realEstateId) {
       List<Investissement> investissements = investissementService.selectInvestissementByRealEstateId(realEstateId);
       System.out.println(investissements);
       ObservableList<Investissement> investissementObservableList = FXCollections.observableArrayList(investissements);
       InvestissementTable.setItems(investissementObservableList);
   }


    @FXML
    void ajouter_event(ActionEvent event) {
        // Handle adding a new real estate
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/finfolio/User/Investissement/AjouterRE.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = new Stage();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void search_event(ActionEvent event) throws SQLException {

        String searchTerm = search_field.getText().toLowerCase();
        ObservableList<RealEstate> filteredList = realEstateObservableList.filtered(
                realEstate -> realEstate.getName().toLowerCase().contains(searchTerm) ||
                        realEstate.getEmplacement().toLowerCase().contains(searchTerm)
        );
        RealEstate_table.setItems(filteredList);

    }

    private void handleDeleteRealEstate(RealEstate realEstate) {
        // Implement logic to delete the selected real estate
        realEstateService.delete(realEstate);
        refreshTableView();
    }

    private void handleModifyRealEstate(RealEstate realEstate) {
        // Implement logic to modify the selected real estate
    }

    public void ajouter_RE(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/finfolio/User/Investissement/AjouterRE.fxml"));
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = new Stage();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dashboard(ActionEvent actionEvent) {
    }

    /*public void dashboard(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/AdminDashboard.fxml"));
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
    }*/
}
