package com.example.finfolio.Investissement.Controller;

import com.example.finfolio.Investissement.Entite.DigitalCoins;
import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.DigitalCoinsService;
import com.example.finfolio.Investissement.Service.InvestissementService;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class AdminDashboardController {

    public TableColumn ROIDigital;
    public Button AdminRE;
    @FXML
    private TableColumn<?, ?> ROI;

    @FXML
    private BarChart<String, Integer> clickROI;

    @FXML
    private TableColumn<?, ?> code;

    @FXML
    private TableColumn<?, ?> dateAchat;

    @FXML
    private LineChart<String, Double> digiCoins;

    @FXML
    private TableColumn<?, ?> emplacement;

    @FXML
    private TableColumn<?, ?> idRE;

    @FXML
    private TableColumn<?, ?> iduser;

    @FXML
    private TableView<DigitalCoins> listeDigi;

    @FXML
    private TableColumn<?, ?> montant;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private PieChart secteurInvestissement;

    @FXML
    private TableView<RealEstate> topRE;

    @FXML
    private TableColumn<?, ?> totalParticipation;

    @FXML
    private TableColumn<?, ?> valeur;
    RealEstateService realEstateService = new RealEstateService();
    DigitalCoinsService digitalCoinsService=new DigitalCoinsService();
    InvestissementService invS=new InvestissementService();
    private ObservableList<RealEstate> realEstateObservableList = FXCollections.observableArrayList();
    private ObservableList<DigitalCoins> digitalCoinsObservableList = FXCollections.observableArrayList();
    private List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
    public void addSeries(XYChart.Series<Number, Number> series) {
        seriesList.add(series);
    }

    // Now you can add the series of type <String, Integer> to the chart
   /* public void addSeriesToChart() {
        clickROI.getData().addAll(seriesList);
    }*/



    @FXML
    void initialize() {

        // Initialize other table columns...
        /*int id, String name, String emplacement, float ROI, double valeur, double totalPart*/
        emplacement.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        idRE.setCellValueFactory(new PropertyValueFactory<>("id"));
        ROI.setCellValueFactory(new PropertyValueFactory<>("ROI"));
        totalParticipation.setCellValueFactory(new PropertyValueFactory<>("totalPart"));
        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));

        // Configure user participation table columns

        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        dateAchat.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        ROIDigital.setCellValueFactory(new PropertyValueFactory<>("ROI"));
        iduser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        /*iduser.setCellValueFactory(cellData -> {
            DigitalCoins digitalCoin = (DigitalCoins) cellData.getValue();
            User user = digitalCoin.getUser();
            if (user != null) {
                return new SimpleIntegerProperty(user.getId());
            } else {
                return new SimpleIntegerProperty(0); // Or any default value you prefer
            }
        });
*/




        // Load real estates and user participations
        loadRealEstatesFromDatabase();
        loadDigitalCoinsFromDatabase();
        populateBarChart();
        populateLineChart();
        populatePieChart();
    }

    @FXML
    void DigiDash(MouseEvent event) {


    }

    @FXML
    void RealEstateAdmin(MouseEvent event) {

    }
    private void loadRealEstatesFromDatabase() {
        // Load real estates into the table
        List<RealEstate> realEstates = realEstateService.getAllDigitalCoinsWithUserId();
        System.out.println(realEstates);
        ObservableList<RealEstate> realEstateList = FXCollections.observableArrayList(realEstates);
        realEstateObservableList = FXCollections.observableArrayList(realEstates);

        topRE.setItems(realEstateList);

        // Handle selection change in the real estate table

    }
    private void loadDigitalCoinsFromDatabase() {
        // Load real estates into the table
        List<DigitalCoins> digitalCoins = digitalCoinsService.getAllDigitalCoinsWithUserId();
        System.out.println(digitalCoins);
        ObservableList<DigitalCoins> digitalCoinsObservableListList = FXCollections.observableArrayList(digitalCoins);
        digitalCoinsObservableListList = FXCollections.observableArrayList(digitalCoinsObservableListList);

        listeDigi.setItems(digitalCoinsObservableListList);

        // Handle selection change in the real estate table

    }
    private void populateBarChart() {
        Set<RealEstate> realEstates = realEstateService.readAllNbrClick();
        clickROI.setTitle("Interaction des Utilisateurs");


        // Sort real estates based on number of clicks
        List<RealEstate> sortedRealEstates = realEstateService.sortByNbrClick(realEstates);

        // Create data series for the bar chart
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (RealEstate re : sortedRealEstates) {
            series.getData().add(new XYChart.Data<>(re.getName(), re.getNbrclick()));
        }
        series.setName("Nombre de click");

        // Set the data series to the bar chart
        clickROI.getData().add(series);
    }
    private void populateLineChart() {
        // Clear existing data from the line chart
        digiCoins.getData().clear();

        // Retrieve all digital coins
        Set<DigitalCoins> digitalCoins = digitalCoinsService.readAll();

        // Sort digital coins by date
        List<DigitalCoins> sortedDigitalCoins = new ArrayList<>(digitalCoins);
        sortedDigitalCoins.sort(Comparator.comparing(DigitalCoins::getDateAchat));

        // Create a map to store the cumulative montant for each code
        Map<String, Double> codeCumulativeMontantMap = new HashMap<>();

        // Calculate the cumulative montant for each code
        for (DigitalCoins coin : sortedDigitalCoins) {
            String code = coin.getCode();
            double montant = coin.getMontant();
            double cumulativeMontant = codeCumulativeMontantMap.getOrDefault(code, 0.0) + montant;
            codeCumulativeMontantMap.put(code, cumulativeMontant);
        }

        // Create a series for each code
        for (Map.Entry<String, Double> entry : codeCumulativeMontantMap.entrySet()) {
            String code = entry.getKey();
            Double cumulativeMontant = entry.getValue();

            // Create a series with the code as the name
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(code);

            // Add data points representing the cumulative montant for each date
            for (DigitalCoins coin : sortedDigitalCoins) {
                if (coin.getCode().equals(code)) {
                    cumulativeMontant += coin.getMontant();
                    series.getData().add(new XYChart.Data<>(coin.getDateAchat().toString(), cumulativeMontant));
                }
            }

            // Add the series to the line chart
            digiCoins.setTitle("Chiffre d'affaires Crypto ");
            digiCoins.getData().add(series);
        }
    }
    private void populatePieChart() {
        // Retrieve all digital coins
        Set<DigitalCoins> digitalCoinsList = digitalCoinsService.readAll();

        // Calculate the sum of montant for all digital coins
        double sumMontant = digitalCoinsList.stream()
                .mapToDouble(DigitalCoins::getMontant)
                .sum();

        // Calculate the sum of investissement (you need to implement this method)
        Set<Investissement> invList =invS.readAll();
        double sumInvestissement = invList.stream().mapToDouble(Investissement::getMontant)
                .sum();

        // Create the pie chart sectors
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Montant DigitalCoins", sumMontant),
                new PieChart.Data("Investissement", sumInvestissement)
        );

        // Set the pie chart data
        secteurInvestissement.setLegendSide(Side.BOTTOM);
        secteurInvestissement.setMaxSize(250, 250);
        secteurInvestissement.setTitle("Répartition des investissement");
        secteurInvestissement.setData(pieChartData);
    }


    public void adminRE(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/RealEstateAdmin.fxml"));
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
