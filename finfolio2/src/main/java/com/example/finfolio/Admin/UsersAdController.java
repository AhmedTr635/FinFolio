package com.example.finfolio.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import Views.AlerteFinFolio;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.UsrController.ModifierUserController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;

public class UsersAdController implements Initializable {
    public TableView users_table;
    public PieChart pieChart;
    public BarChart barChart;
    public TableColumn <User,Void>actions_clmn;
    public TableColumn <User,String>statut_clmn;


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
        try {
            initializeTableView(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    private void initializeTableView(ObservableList<User> users) throws SQLException {
        id_clm.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom_clm.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_clm.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_clm.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtel_clm.setCellValueFactory(new PropertyValueFactory<>("numtel"));
        note_clm.setCellValueFactory(new PropertyValueFactory<>("rate"));
        nbrCredits_clm.setCellValueFactory(new PropertyValueFactory<>("nbcredit"));
        solde_clm.setCellValueFactory(new PropertyValueFactory<>("solde"));
        role_clm.setCellValueFactory(new PropertyValueFactory<>("role"));
        statut_clmn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        actions_clmn.setCellFactory(column -> {
            return new TableCell<>() {
                Button deleteButton = new Button("S");



                // Ajout d'une action au bouton
                final Button modifyButton = new Button("M");

                {
                    deleteButton.setOnAction(event -> {
                        User u = getTableView().getItems().get(getIndex());
                        UserService us=new UserService();
                        try {
                            us.delete(u);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            refreshTableView();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    modifyButton.setOnAction(event -> {
                        User u = getTableView().getItems().get(getIndex());
                        System.out.println(u);
                        ModifierUser(u);
                        try {
                            refreshTableView();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttonBox = new HBox(deleteButton, modifyButton);
                        setGraphic(buttonBox);
                    }
                }
            };
        });

       refreshTableView();


    };




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
    private void ModifierUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/modifierUser.fxml"));
            Parent root = loader.load();

            ModifierUserController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier User");
            stage.getIcons().add(new Image(String.valueOf(AlerteFinFolio.class.getResource("/com/example/finfolio/Pics/icon.png"))));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void refreshTableView() throws SQLException {
        UserService us=new UserService();
        List<User> users = us.readAll();

        // Display events in TableView
        ObservableList<User> userList = FXCollections.observableArrayList(users);
        users_table.setItems(userList);

    }




}
