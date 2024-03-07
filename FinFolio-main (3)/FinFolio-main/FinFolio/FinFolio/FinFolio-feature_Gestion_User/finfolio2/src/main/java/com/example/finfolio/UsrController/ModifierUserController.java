package com.example.finfolio.UsrController;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {


    public ChoiceBox<String> statut_box;
    public ChoiceBox<String> note_box;
    public DatePicker dateCh;
    @FXML
    private Button confirmerBtn;
    private String[] statuts = {"Statut", "Active", "Desactive", "Ban"};
    private String[]notes={"Note","1","2","3","4","5"};
    private User user;

    public void getStatut(ActionEvent event) {

        String statutBoxValue = statut_box.getValue();

    }
    public void getNotes(ActionEvent event) {

        String notesBox = note_box.getValue();

    }
public void setUser(User u)
{
    user=u;
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        statut_box.getItems().addAll(statuts);
        statut_box.setOnAction(this::getStatut);
        statut_box.setValue("Statut");
        note_box.getItems().addAll(notes);
        note_box.setOnAction(this::getNotes);
        note_box.setValue("Note");
        confirmerBtn.setOnAction(e-> {
            try {
                onConfirmer();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
        public void onConfirmer() throws SQLException {
            if ("Active".equals(statut_box.getValue())) {
                user.setStatut("active");
            } else if ("Desactive".equals(statut_box.getValue())) {
                user.setStatut("desactive");
            } else if ("Ban".equals(statut_box.getValue())) {
                user.setStatut("ban");
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
            String date=dateCh.getValue().toString();
            user.setDatepunition(date);
            UserService us=new UserService();
            us.update(user);

            // Close the window after updating the event
            confirmerBtn.getScene().getWindow().hide();
        }

}




