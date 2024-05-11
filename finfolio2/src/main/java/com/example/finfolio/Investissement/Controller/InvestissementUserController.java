package com.example.finfolio.Investissement.Controller;

import Models.Model;
import com.example.finfolio.Investissement.Entite.DigitalCoins;
import com.example.finfolio.Investissement.Entite.Investissement;
import com.example.finfolio.Investissement.Entite.RealEstate;
import com.example.finfolio.Investissement.Service.DigitalCoinsService;
import com.example.finfolio.Investissement.Service.InvestissementService;
import com.example.finfolio.Investissement.Service.RealEstateService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class InvestissementUserController implements Initializable {

    public VBox invList;
    public VBox topROI;
    public LineChart statInvv;
    public Label price_BTC;
    public Label price_ETH;
    public Label price_SOL;
    public ImageView ETH_icon;
    public HBox ETH;
    public ImageView SOL_icon;
    public HBox SOL;
    public ImageView BTC_icon;
    public HBox BTC;
    @FXML
    private Pane DigiCoinsList;

    @FXML
    private Button InterfaceDigi;

    @FXML
    private Pane InvUserList;

    @FXML
    private Button ListRE;

    @FXML
    private Pane RealEstateList;

    @FXML
    private Pane StatInv;

    @FXML
    private ScrollPane TopRE;

    @FXML
    private VBox stat;

    @FXML
    private ScrollPane topDigi;
    @FXML
    private TableView<?> invreadall;

    @FXML
    void AffichageInterfaceDigi(MouseEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/DigitalCoins.fxml"));
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
    void AffichageRealEstateUser(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo1/RealEstateUser.fxml"));
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
    InvestissementService invS=new InvestissementService();
    DigitalCoinsService digitalCoinsService = new DigitalCoinsService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // int userID=3;
        Set<Investissement> userInvestments = invS.fetchInvestissementByUserId(Model.getInstance().getUser().getId()).stream()
                .filter(re -> re.getUser().getId() == Model.getInstance().getUser().getId())
                .collect(Collectors.toSet());
        /*Set<Investissement> userInvestments2 = userInvestments.stream()
                .filter(re -> re.getUser().getId() == userID)
                .collect(Collectors.toSet());*/
        Map<YearMonth, Double> monthlyROI = calculateMonthlyROI(userInvestments);

        // Display the monthly ROI in the line chart
        displayMonthlyROIChart(monthlyROI, statInvv);

        // Prepare data for the chart


        // Add series to the existing chart
        //statInvv.getData().add(series);
        for(DigitalCoins DC : digitalCoinsService.getDigitalCoinsByUserId(Model.getInstance().getUser().getId())){
            try {
                // Load the RealEstateCell.fxml
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/DigitalCoinCell.fxml"));
                AnchorPane cell = fxmlLoader.load();
                // Get the controller
                DigitalCoinCellController controller = fxmlLoader.getController();
                // Set real estate data
                controller.setData(DC);
                // Add the cell to the affichageRE HBox
                invList.getChildren().addAll(cell);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        for (Investissement investissement : userInvestments) {
            try {
                //if (investissement.getUser().getId()==userID) {
                    // Load the RealEstateCell.fxml



                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/InvCell.fxml"));

                    AnchorPane cell = fxmlLoader.load();

                    // Get the controller
                    InvCellController controller = fxmlLoader.getController();

                    // Set real estate data
                    //controller.setRealEstate(realEstate);
                    //System.out.println(realEstate);
                    controller.setData(investissement);
                    //controller.setRealEstate(realEstate);

                    //System.out.println("hne");
                    //controller.RealEstateName.setText(realEstate.getEmplacement());
                    //controller.zoom(mouseEvent);

                    // Add the cell to the affichageRE HBox
                    invList.getChildren().addAll(cell);
                //}

                // Load the RealEstateCell.fxml

                /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/InvCell.fxml"));

                AnchorPane cell = fxmlLoader.load();*/

                // Get the controller
                /*InvCellController controller = fxmlLoader.getController();*/

                // Set real estate data
                //controller.setRealEstate(realEstate);
                //System.out.println(realEstate);
                /*controller.setData(investissement);*/
                //controller.setRealEstate(realEstate);

                //System.out.println("hne");
                //controller.RealEstateName.setText(realEstate.getEmplacement());
                //controller.zoom(mouseEvent);

                // Add the cell to the affichageRE HBox
                /*invList.getChildren().addAll(cell);*/
                //System.out.println("hiii");


                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            updatePrices();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 60000);

            } catch (IOException e) {
                e.printStackTrace();
            }

        // Convert the Set to a List
       /* ObservableList<Investissement> investmentObservableList = FXCollections.observableArrayList(userInvestments);

        // Set the items for the TableView
        invreadall.setItems(investmentObservableList);*/

        }

        RealEstateService realEstateService=new RealEstateService();
        Set<RealEstate> realEstateSet = realEstateService.readAllNbrClick();
        Set<RealEstate> topThreeRealEstates = realEstateSet.stream()
                .filter(re -> re.getNbrclick() > 0) // Filter out elements with ROI > 0
                .sorted(Comparator.comparingDouble(RealEstate::getNbrclick).reversed()) // Sort in descending order of ROI
                .limit(3) // Limit to the first three elements
                .collect(Collectors.toSet());
         /*Set<RealEstate> reversedSet = topThreeRealEstates.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.reverse(list);
                            return new LinkedHashSet<>(list);
                        })
                );*/


        //System.out.println(realEstateSet);

        // Create and add a cell for each real estate
        for (RealEstate realEstate : topThreeRealEstates) {
            try {
                // Load the RealEstateCell.fxml

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Investissement/RealEstateCell.fxml"));

                AnchorPane cell = fxmlLoader.load();

                // Get the controller
                RealEstateCellController controller = fxmlLoader.getController();

                // Set real estate data
                //controller.setRealEstate(realEstate);
                //System.out.println(realEstate);
                controller.setData(realEstate);
                controller.setRealEstate(realEstate);

                //System.out.println("hne");
                //controller.RealEstateName.setText(realEstate.getEmplacement());
                //controller.zoom(mouseEvent);

                // Add the cell to the affichageRE HBox
                topROI.getChildren().addAll(cell);
                //System.out.println("hiii");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
    public void refresh(){
        int userID=3;
        Set<Investissement> userInvestments = invS.fetchInvestissementByUserId(userID).stream()
                .filter(re -> re.getUser().getId() == userID)
                .collect(Collectors.toSet());
        /*Set<Investissement> userInvestments2 = userInvestments.stream()
                .filter(re -> re.getUser().getId() == userID)
                .collect(Collectors.toSet());*/
        Map<YearMonth, Double> monthlyROI = calculateMonthlyROI(userInvestments);

        // Display the monthly ROI in the line chart
        displayMonthlyROIChart(monthlyROI, statInvv);
    }

    private Map<YearMonth, Double> calculateMonthlyROI(Set<Investissement> investments) {
        Map<YearMonth, Double> monthlyROI = new HashMap<>();
        Map<YearMonth, Double> cumulativeROI = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        for (Investissement investment : investments) {
            LocalDate startDate = investment.getDateAchat();
            double investmentAmount = investment.getMontant();
            double ROI = investment.getROI() / 100.0; // Convert ROI to decimal

            while (!startDate.isAfter(currentDate)) {
                YearMonth startYearMonth = YearMonth.from(startDate);
                cumulativeROI.putIfAbsent(startYearMonth, 0.0);

                // Calculate the ROI for the current investment in this month
                double monthlyInvestmentROI = (ROI * investmentAmount) / 12.0;

                // Add the monthly investment ROI to the cumulative ROI for the month
                double updatedCumulativeROI = cumulativeROI.get(startYearMonth) + monthlyInvestmentROI;
                cumulativeROI.put(startYearMonth, updatedCumulativeROI);

                // Add or update the monthly ROI in the map
                monthlyROI.put(startYearMonth, monthlyROI.getOrDefault(startYearMonth, 0.0) + updatedCumulativeROI);

                // Move to the next month
                startDate = startDate.plusMonths(1);
            }
        }

        // Remove entries with zero ROI
        monthlyROI.entrySet().removeIf(entry -> entry.getValue() == 0.0);

        return monthlyROI;
    }


    private void displayMonthlyROIChart(Map<YearMonth, Double> monthlyROI, LineChart<String, Number> lineChart) {
        lineChart.getData().clear();
        lineChart.setTitle("Monthly ROI");
        lineChart.getXAxis().setLabel("Month");
        lineChart.getYAxis().setLabel("Total ROI");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Sort the monthlyROI map by keys (months)
        List<YearMonth> sortedMonths = new ArrayList<>(monthlyROI.keySet());
        Collections.sort(sortedMonths);
        YearMonth lastMonth = sortedMonths.get(sortedMonths.size() - 1);
        double lastValue = monthlyROI.get(lastMonth);

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedLastValue = df.format(lastValue);

        for (YearMonth month : sortedMonths) {
            series.getData().add(new XYChart.Data<>(month.toString(), monthlyROI.get(month)));
        }
        series.setName(formattedLastValue);

        lineChart.getData().add(series);
    }
    private void updatePrices() throws IOException {
        String[] symbols = {"BTCUSDT", "ETHUSDT", "SOLUSDT"};

        for (String symbol : symbols) {
            String price = fetchPrice(symbol);
            switch (symbol) {
                case "BTCUSDT":
                    Platform.runLater(() -> price_BTC.setText(price));
                    break;
                case "ETHUSDT":
                    Platform.runLater(() -> price_ETH.setText(price));
                    break;
                case "SOLUSDT":
                    Platform.runLater(() -> price_SOL.setText(price));
                    break;
            }
        }
    }

    private String fetchPrice(String symbol) throws IOException {
        URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=" + symbol);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = reader.readLine();
        reader.close();

        // Extract the price from the response
        int start = line.indexOf("\"price\":\"") + 9; // Find the starting index of the price
        int end = line.indexOf("\"", start); // Find the ending index of the price
        return line.substring(start, end); // Extract the price
    }
}
