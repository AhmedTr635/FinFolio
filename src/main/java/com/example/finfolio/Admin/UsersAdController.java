package com.example.finfolio.Admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Models.Model;
import Views.UserCellFactory;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersAdController implements Initializable {
    public TableView users_table;
    public PieChart pieChart;
    public BarChart barChart;


    @FXML
    private ChoiceBox<String> filtreBox;

    @FXML
    private TextField cherecher_fld;



    @FXML
    private TableColumn<User, String> email_clm;

    @FXML
    private TableColumn<User, Integer> id_clm;

    @FXML
    private TableColumn<User, Integer> nbrCredits_clm;



    @FXML
    private TableColumn<User, String> nom_clm;



    @FXML
    private TableColumn<User, Float> note_clm;



    @FXML
    private TableColumn<User, String> numtel_clm;



    @FXML
    private TableColumn<User, String> prenom_clm;




    @FXML
    private TableColumn<User, String> role_clm;

    @FXML
    private TableColumn<User, String> solde_clm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {UserService uc=new UserService();

        try {
            initPieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        UserService us =new UserService();

        List<User>liste= null;
        try {
            liste = us.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        filtreBox.getItems().addAll("Utilisateur", "Admin","Filtrer");
        filtreBox.setValue("Filtrer");



        ObservableList<User> users= FXCollections.observableArrayList(liste);
        initializeTableView(users);
        users_table.setItems(FXCollections.observableArrayList(liste));



        //recheche
        cherecher_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            UserService us1= new UserService();

            List<User> resultatsRecherche = null;
            try {
                resultatsRecherche = us1.rechercherUtilisateurs(cherecher_fld.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Mettre à jour la TableView avec les résultats de la recherche
            users_table.setItems(FXCollections.observableArrayList(resultatsRecherche));


    });
        filtreBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            UserService userService = new UserService();
            List<User> filteredUsers = null;
            filteredUsers=filtrerType(newValue);
            users_table.setItems(FXCollections.observableArrayList(filteredUsers));

        });

}
    private void initializeTableView(ObservableList<User> users) {
        id_clm.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom_clm.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_clm.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_clm.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtel_clm.setCellValueFactory(new PropertyValueFactory<>("numtel"));
        note_clm.setCellValueFactory(new PropertyValueFactory<>("rate"));
        nbrCredits_clm.setCellValueFactory(new PropertyValueFactory<>("nbcredit"));
        solde_clm.setCellValueFactory(new PropertyValueFactory<>("solde"));
        role_clm.setCellValueFactory(new PropertyValueFactory<>("role"));

    }
    public List<User> filtrerType(String role)
    {
        UserService userService = new UserService();
        List<User> filteredUsers = null;
        try {
            switch (role) {
                case "Utilisateur" ->filteredUsers = userService.readAll().stream()
                            .filter(user -> "user".equals(user.getRole()))
                            .toList();
                case "Admin"-> filteredUsers = userService.readAll().stream()
                            .filter(user -> "admin".equals(user.getRole()))
                            .toList();
                case "Filtrer" ->filteredUsers=userService.readAll();
                default -> filteredUsers=userService.readAll();

            }
            }



     catch (SQLException e) {
            throw new RuntimeException(e);
          };
       return filteredUsers;
    }
    private void initPieChart() throws SQLException {
       pieChart.setPrefSize(200, 300);
        UserService us =new UserService();

        PieChart.Data slice1 = new PieChart.Data("Utlisateurs", us.readAll().stream().filter(u -> u.getRole().equals("user")).toList().size());
        PieChart.Data slice2 = new PieChart.Data("Administrateurs", us.readAll().stream().filter(u -> u.getRole().equals("admin")).toList().size());

        // Créez le PieChart
        pieChart.getData().addAll(slice1, slice2);

        // Définir des couleurs personnalisées pour chaque tranche
        slice1.getNode().setStyle("-fx-pie-color: #123499;"); // Rouge
        slice2.getNode().setStyle("-fx-pie-color: blue;"); // Vert
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Création du barchart
        barChart.setTitle("Nombre d'utilisateurs selon la note  ");
        xAxis.setLabel("Rating des utilisateurs");
        yAxis.setLabel("Nombre des utilisateurs");

        // Ajout des données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Les notes");
        series.getData().add(new XYChart.Data<>("1", us.readAll().stream().filter(u->u.getRate()==1).toList().size()));
        series.getData().add(new XYChart.Data<>("2",  us.readAll().stream().filter(u->u.getRate()==2).toList().size()));
        series.getData().add(new XYChart.Data<>("3",  us.readAll().stream().filter(u->u.getRate()==3).toList().size()));
        series.getData().add(new XYChart.Data<>("4",  us.readAll().stream().filter(u->u.getRate()==4).toList().size()));
        series.getData().add(new XYChart.Data<>("5",  us.readAll().stream().filter(u->u.getRate()==5).toList().size()));


        // Ajout des données au barchart
        barChart.getData().add(series);
    }

}
