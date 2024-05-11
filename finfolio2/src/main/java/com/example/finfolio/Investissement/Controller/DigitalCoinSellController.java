package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.DigitalCoins;
import com.example.finfolio.Investissement.Service.DigitalCoinsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class DigitalCoinSellController implements Initializable {
    @FXML
    private WebView chart;

    @FXML
    private ChoiceBox<String> code;

    @FXML
    private ChoiceBox<String> intervalle;

    @FXML
    private TextField leverage;

    @FXML
    private TextField montant;

    @FXML
    private Button quitter;

    @FXML
    private Button sell;

    @FXML
    private TextField stoploss;

    @FXML
    private Label warn1;

    @FXML
    private Label warn2;
    DigitalCoinsService digitalCoinsService = new DigitalCoinsService();
    public DigitalCoins digitalCoins;

    @FXML
    void addDigiDateVente(MouseEvent event) {
        digitalCoinsService.getCoinWithMaxId().setDateVente("07-03-2024");
        System.out.println("digitalCoinsService.getCoinWithMaxId()");
        System.out.println(digitalCoinsService.getCoinWithMaxId().getDateVente());
        montant.setText("0");
        leverage.setText("0");
        stoploss.setText("0");
        //userService.updateSolde(digitalCoinsService.getCoinWithMaxId().getMontant()*digitalCoinsService.getCoinWithMaxId().getLeverage());
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



    private String fetchPrice(String symbol) throws IOException {
        URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=" + symbol+"USDT");
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = reader.readLine();
        reader.close();

        // Extract the price from the response
        int start = line.indexOf("\"price\":\"") + 9; // Find the starting index of the price
        int end = line.indexOf("\"", start); // Find the ending index of the price
        return line.substring(start, end); // Extract the price
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

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ChoiceBox with currency codes
        code.getItems().addAll("BTC", "SOL", "ETH", "XRP", "ADA", "DOGE", "BNB");
        // Set default selection
        code.setValue("BTC");
        intervalle.getItems().addAll("1m", "5m", "15m", "30m", "1h");
        intervalle.setValue("15m");

        // Load chart.html with default symbol (BTC)
        WebEngine webEngine = chart.getEngine();
        String url1 = getClass().getResource("/chart.html").toExternalForm();
        webEngine.load(url1 + "?symbol=" + code.getValue() + "&interval=" + intervalle.getValue());
        /*Button addRedLineButton = new Button("Add Red Line");
        addRedLineButton.setOnAction(e -> executeJavaScript(webEngine, "addPriceLineButton"));

        Button addGreenLineButton = new Button("Add Green Line");
        addGreenLineButton.setOnAction(e -> executeJavaScript(webEngine, "addPriceLineButton"));*/

        // Event listener for ChoiceBox selection change
        code.setOnAction(event -> {
            String selectedSymbol = (String) code.getValue();
            String selectedInterval = (String) intervalle.getValue();
            // Load chart.html with selected symbol and interval
            webEngine.load(url1 + "?symbol=" + selectedSymbol + "&interval=" + selectedInterval);
        });

        // Event listener for interval ChoiceBox selection change
        intervalle.setOnAction(event -> {
            String selectedSymbol = (String) code.getValue();
            String selectedInterval = (String) intervalle.getValue();
            // Load chart.html with selected symbol and interval
            webEngine.load(url1 + "?symbol=" + selectedSymbol + "&interval=" + selectedInterval);
        });

        // Event listener for buy button click


        // Event listener for sell button click
        sell.setOnAction(event -> addPriceLine(webEngine, "green"));
        leverage.setOnKeyTyped(this::handleNumericInput);
        montant.setOnKeyTyped(this::handleNumericInput);
        stoploss.setOnKeyTyped(this::handleNumericInput);

    }
    private void addPriceLine(WebEngine engine, String color) {
        engine.executeScript("addPriceLine('" + color + "')");
    }
    private void handleNumericInput(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
            event.consume(); // Consume the event to prevent the character from being entered
            if (textField != null) {
                textField.setStyle("-fx-border-color: red;"); // Change border color to red
                textField.setStyle("-fx-text-fill: red;"); // Change text color to red
                if (textField.equals(montant)) {
                    warn1.setTextFill(Color.RED);
                    warn1.setText("Entrez un montant valide !");
                } else {
                    warn2.setTextFill(Color.RED);
                    warn2.setText("Entrez une valeur valide !");
                }
            }
        } else {
            if (textField != null) {
                textField.setStyle("-fx-border-color: blue;"); // Change border color to blue
                textField.setStyle("-fx-text-fill: black;"); // Change text color to black
                if (textField.equals(montant)) {
                    warn1.setText(""); // Clear warning message
                } else {
                    warn2.setText(""); // Clear warning message
                }
            }
        }
    }

    public void setData(DigitalCoins digitalCoins) {
        this.digitalCoins = digitalCoins;
        montant.setText(String.valueOf(digitalCoins.getMontant()));
        if(digitalCoins.getLeverage() == 0){
            leverage.setText("1");}
        if(digitalCoins.getStopLoss() == 0){
            stoploss.setText("0");}
        leverage.setText(String.valueOf(digitalCoins.getLeverage()));
        stoploss.setText(String.valueOf(digitalCoins.getStopLoss()));

    }
}
