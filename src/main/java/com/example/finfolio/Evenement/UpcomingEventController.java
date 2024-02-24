package com.example.finfolio.Evenement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UpcomingEventController {
    @FXML

    public Pane event;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label event_date;

    @FXML
    private Label event_goal;

    @FXML
    private Label event_name;

    @FXML
    private Label event_place;

    @FXML
    private Pane upcoming_event;

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public Label getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Label event_date) {
        this.event_date = event_date;
    }

    public Label getEvent_goal() {
        return event_goal;
    }

    public void setEvent_goal(Label event_goal) {
        this.event_goal = event_goal;
    }

    public Label getEvent_name() {
        return event_name;
    }

    public void setEvent_name(Label event_name) {
        this.event_name = event_name;
    }

    public Label getEvent_place() {
        return event_place;
    }

    public void setEvent_place(Label event_place) {
        this.event_place = event_place;
    }

    public Pane getUpcoming_event() {
        return upcoming_event;
    }

    public void setUpcoming_event(Pane upcoming_event) {
        this.upcoming_event = upcoming_event;
    }

    @FXML
    void initialize() {


    }

}
