package com.example.finfolio;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressIndicatorExample extends Application {

    private TextField textField;
    private ProgressIndicator progressIndicator;
    private double sum = 0;
    private Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) {
        // Create a TextField and a Button in the first scene
        textField = new TextField();
        textField.setPromptText("Type a number");
        Button addButton = new Button("Add");

        // Create a layout for the first scene
        VBox layoutScene1 = new VBox(10, textField, addButton);
        layoutScene1.setPadding(new Insets(20));

        // Create the second scene with the ProgressIndicator
        VBox layoutScene2 = new VBox(10);
        layoutScene2.setPadding(new Insets(20));
        progressIndicator = new ProgressIndicator(0);
        layoutScene2.getChildren().addAll(progressIndicator, createReturnButton(primaryStage));

        // Create scenes for each layout
        scene1 = new Scene(layoutScene1, 300, 200);
        scene2 = new Scene(layoutScene2, 300, 200);

        // Set initial scene
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Progress Indicator Example");
        primaryStage.show();

        // Add functionality to switch to the second scene and update the ProgressIndicator
        addButton.setOnAction(event -> {
            try {
                // Parse the text as a double
                double number = Double.parseDouble(textField.getText());

                // Add the number to the sum
                sum += number;

                // Ensure the sum is within [0, 1] range
                sum = Math.max(0, Math.min(1, sum));

                // Set the progress of the ProgressIndicator in the second scene
                progressIndicator.setProgress(sum);

                // Switch to the second scene
                updateScene(primaryStage, scene2);
            } catch (NumberFormatException e) {
                // Ignore non-numeric input
            }
        });
    }

    // Method to create a button to return to the first scene
    private Button createReturnButton(Stage primaryStage) {
        Button returnButton = new Button("Return to Add More");
        returnButton.setOnAction(event -> {
            updateScene(primaryStage, scene1);
        });
        return returnButton;
    }

    // Method to dynamically update the scene based on the progress
    private void updateScene(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        textField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}