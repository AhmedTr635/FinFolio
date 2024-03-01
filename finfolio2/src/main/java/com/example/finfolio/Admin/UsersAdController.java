package com.example.finfolio.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class UsersAdController implements Initializable {
    public TableView users_table;
    public PieChart pieChart;
    public BarChart barChart;
    public TableColumn <User,Void>actions_clmn;
    public TableColumn <User,String>statut_clmn;
    public ChoiceBox<String> statut_box;
    public ChoiceBox<String> note_box;
    public DatePicker dateCh;
    public Button updateBtn;
    public Label dateError;
    private String[] statuts = {"Statut", "Active", "Desactive", "Ban"};
    private String[]notes={"Note","1","2","3","4","5"};

    @FXML
    private ChoiceBox<String> filtreBox;

    @FXML
    private TextField cherecher_fld;
    public TableColumn <User,String>datePunition;



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


    public void getStatut(ActionEvent event) {

        String statutBoxValue = statut_box.getValue();

    }
    public void getNotes(ActionEvent event) {

        String notesBox = note_box.getValue();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {UserService uc=new UserService();
//choice box
        statut_box.getItems().addAll(statuts);
        statut_box.setOnAction(this::getStatut);
        statut_box.setValue("Statut");
        note_box.getItems().addAll(notes);
        note_box.setOnAction(this::getNotes);
        note_box.setValue("Note");
        dateCh.setVisible(false);
        addDatePickerListener(dateCh,dateError,"date");
        statut_box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Your code here to handle the change
            if ("Active".equals(statut_box.getValue())) {
                dateCh.setVisible(false);

            } else if ("Desactive".equals(statut_box.getValue())) {
                dateCh.setVisible(true);
            } else if ("Ban".equals(statut_box.getValue())) {
                dateCh.setVisible(false);
            }
            else                 dateCh.setVisible(false);

        });
        try {
            refreshTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        users_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate input fields with selected credit's details
                User user = (User) users_table.getSelectionModel().getSelectedItem();
                switch (user.getStatut()){
                    case "active" -> statut_box.setValue("Active");
                    case "ban" -> statut_box.setValue("Ban");
                    case "desactive" -> statut_box.setValue("Desactive");
                }
                switch ((int) user.getRate())
                {
                    case 1 -> note_box.setValue("1");
                    case 2 -> note_box.setValue("2");
                    case 3 -> note_box.setValue("3");
                    case 4 -> note_box.setValue("4");
                    case 5-> note_box.setValue("5");

                }
                if (!user.getDatepunition().equals("vide"))
                {dateCh.setValue(LocalDate.parse(user.getDatepunition()));}
                updateBtn.setOnAction(e-> {
                    try {
                        onConfirmer(user);
                        refreshTableView();


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                });



            }
        });



        //Statistique

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

        filtreBox.getItems().addAll("Utilisateur", "Admin","Filtrer","Active","Ban","Desactive");
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
            ObservableList<User> filteredUsers = FXCollections.observableArrayList(filtrerType(newValue));
            users_table.setItems(filteredUsers);


        });
        UserService us2 =new UserService();
        try {
            users_table.setItems(FXCollections.observableArrayList(us2.readAll()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Initialization de la table view
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
        datePunition.setCellValueFactory(new PropertyValueFactory<>("datepunition"));

        actions_clmn.setCellFactory(column -> {
            return new TableCell<>() {
                HBox buttonBox = new HBox(); // Container to hold the button and icon
                Button deleteButton = new Button(); // Button without text

                {
                    // Create the Font Awesome icon view
                    FontAwesomeIconView iconView = new FontAwesomeIconView();
                    // Set the icon name
                    iconView.setGlyphName("TRASH"); // Replace "TRASH" with the desired icon name
                    // Set the size of the icon
                    iconView.setSize("16"); // You can adjust the size as needed
                    iconView.setFill(javafx.scene.paint.Color.WHITE); // Set icon color to white

                    // Set button properties
                    deleteButton.setGraphic(iconView); // Set the icon as the graphic
                    deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;"); // Set button style

                    // Add action to the button
                    deleteButton.setOnAction(event -> {
                        User u = getTableView().getItems().get(getIndex());
                        UserService us = new UserService();
                        if(u.getRole()!="user")
                            AlerteFinFolio.alertechoix("Vous ne pouvez pas supprimer un admin","Suppression admin");
                        else {
                        try {
                            us.delete(u);
                            refreshTableView();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }}
                    });

                    // Add button to the HBox
                    buttonBox.getChildren().add(deleteButton);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty ) {
                        setGraphic(null);
                    } else {
                        setGraphic(buttonBox);
                    }
                }
            };
        });
        //refreshTableView();


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
                case "Ban"-> filteredUsers = userService.readAll().stream()
                        .filter(user -> "ban".equals(user.getStatut()))
                        .toList();
                case "Active"-> filteredUsers = userService.readAll().stream()
                        .filter(user -> "active".equals(user.getStatut()))
                        .toList();
                case "Desactive"-> filteredUsers = userService.readAll().stream()
                        .filter(user -> "desactive".equals(user.getStatut()))
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
    public void onConfirmer(User user) throws SQLException {
        if (user.getRole().equals("admin"))
        AlerteFinFolio.alertechoix("Tu ne peux pas punir un admin !","Modifier un admin");
        else {
        if ("Active".equals(statut_box.getValue())) {
            user.setStatut("active");
            user.setDatepunition("vide");

        } else if ("Desactive".equals(statut_box.getValue())) {
            user.setStatut("desactive");
        } else if ("Ban".equals(statut_box.getValue())) {
            user.setStatut("ban");
            user.setDatepunition("vide");
        }

        if ("1".equals(note_box.getValue())) {
            user.setRate(1);
        } else if ("2".equals(note_box.getValue())) {
            user.setRate(2);
        } else if ("3".equals(note_box.getValue())) {
            user.setRate(3);
        } else if ("4".equals(note_box.getValue())) {
            user.setRate(4);
        } else if ("5".equals(note_box.getValue())) {
            user.setRate(5);
        }
        if (dateCh.isVisible()&& dateCh.getValue()!=null )
        {String date = dateCh.getValue().toString();
            user.setDatepunition(date);}
        if( validateDatePicker(dateCh,dateError))
        {UserService us = new UserService();
        us.update(user);
        AlerteFinFolio.alerteSucces("Utilisateur sauvegardé avec succes","Modification utilisateur");
        refreshTableView();}

    }}
//controle de saisie date
private boolean validateDatePicker(DatePicker datePicker, Label errorLabel) {
    LocalDate selectedDate = datePicker.getValue();
    LocalDate currentDate = LocalDate.now();
if (!datePicker.isVisible())
    return true;
    if (selectedDate == null) {
        errorLabel.setText("Veuillez sélectionner une date.");
        datePicker.getStyleClass().add("error");
        errorLabel.getStyleClass().add("error-label");
        return false;
    } else if (selectedDate.isBefore(currentDate)) {
        errorLabel.setText("La date doit être ultérieure à aujourd'hui.");
        datePicker.getStyleClass().add("error");
        errorLabel.getStyleClass().add("error-label");
        return false;
    } else {
        errorLabel.setText("");
        datePicker.getStyleClass().removeAll("error");
        errorLabel.getStyleClass().removeAll("error-label");
        return true;
    }
}
    private void addDatePickerListener(DatePicker datePicker, Label errorLabel, String fieldName) {
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateDatePicker(datePicker, errorLabel);
        });
    }


    private void initPieChart() throws SQLException {
      /* pieChart.setPrefSize(200, 300);
        UserService us =new UserService();

        PieChart.Data slice1 = new PieChart.Data("Utlisateurs", us.readAll().stream().filter(u -> u.getRole().equals("user")).toList().size());
        PieChart.Data slice2 = new PieChart.Data("Administrateurs", us.readAll().stream().filter(u -> u.getRole().equals("admin")).toList().size());

        // Créez le PieChart
        pieChart.getData().addAll(slice1, slice2);

        // Définir des couleurs personnalisées pour chaque tranche
        slice1.getNode().setStyle("-fx-pie-color: #123499;"); // Rouge
        slice2.getNode().setStyle("-fx-pie-color: blue;"); // Vert
        */

        // Création du barchart
        UserService us =new UserService();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
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



    private void refreshTableView() throws SQLException {
        UserService us=new UserService();
        List<User> users = us.readAll();

        // Display events in TableView
        ObservableList<User> userList = FXCollections.observableArrayList(users);
        users_table.setItems(userList);

    }




}