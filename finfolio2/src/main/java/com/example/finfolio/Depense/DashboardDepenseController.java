package com.example.finfolio.Depense;

import Models.Model;
import com.example.finfolio.Entite.Depense;
import com.example.finfolio.Entite.Tax;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.DepenseService;
import com.example.finfolio.Service.TaxService;
import com.example.finfolio.Service.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class DashboardDepenseController {
    private static DashboardDepenseController instance;

    public static DashboardDepenseController getInstance() {
        return instance;
    }

    public DashboardDepenseController() {
        instance = this;
    }

    public ComboBox monthComboBox;
    public Button AddDepense;

    public TableColumn montant;

    public TableColumn type;
    public Label total_depense_card;
    public TextField searchField;
    public Rectangle amount_depense;
    @FXML
    public LineChart chartContainer;
    @FXML
    public Button downloadButton;
    public Label tax_depense;

    @FXML
    private TableView<Depense> depenseTableView;


    //Initilisation du dashboard
    @FXML
    public void initialize() throws SQLException {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchExpenses(new ActionEvent());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        List<Depense> allDepenses = DepenseService.getInstance().readAll();
        Map<YearMonth, Double> monthlyExpenses = calculateMonthlyExpenses(allDepenses);
        displayMonthlyExpensesChart(monthlyExpenses);
        downloadButton.setOnAction(event -> exportToExcel());
        searchExpenses(new ActionEvent());
        tax_Dpense();
        totalDpense();
        showInit();
        loadTableView();
    }
    //filtre par mois
    private void showInit() {

        // Initialize the month ComboBox
        ObservableList<Month> months = FXCollections.observableArrayList(Month.values());
        monthComboBox.setItems(months);

        // Add event listener for ComboBox selection changes
        monthComboBox.setOnAction(event -> {
            Month selectedMonth = (Month) monthComboBox.getValue();
            // Call a method to filter expenses by the selected month
            try {
                filterByMonth(selectedMonth);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //methode pour afficher l'interface d'ajout de dépense
    public void OnAddDepense(ActionEvent actionEvent) {
        try {
            // Load the FXML file of Interface2
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/finfolio/User/Depense/AjouterDepense.fxml")));

            // Create a new scene
            Scene scene = new Scene(root);

            // Create a new stage
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setOnHidden(e-> {
                try {
                    loadDepenseFromDatabase();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            // Show the new stage
            newStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //search dynamique
    @FXML
    private void searchExpenses(ActionEvent actionEvent) throws SQLException {
        String query = searchField.getText();
        List<Depense> deps= DepenseService.getInstance().rechercherEvent(query);
        ObservableList<Depense> depsList =FXCollections.observableArrayList(deps);
        depenseTableView.setItems(depsList);
    }

    //affichage de la table de dépense
    public void loadTableView() throws SQLException {

        TableColumn<Depense, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Depense, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Depense, Double> montantColumn = new TableColumn<>("Montant");
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        TableColumn<Depense, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getActionCellFactory()); // Set custom cell factory

        // Add all columns to the TableView
        depenseTableView.getColumns().addAll(typeColumn, dateColumn, montantColumn, actionColumn);
        depenseTableView.getStyleClass().add("custom-table-view");

        // Apply CSS styling to individual columns
        depenseTableView.getStyleClass().add("custom-table-view");

        // Apply CSS styling to individual columns
        typeColumn.getStyleClass().add("type-column");
        dateColumn.getStyleClass().add("date-column");
        montantColumn.getStyleClass().add("montant-column");
        actionColumn.getStyleClass().add("action-column");
        populateTableView();



    }
    //remplir la table de dépense
    private void populateTableView()  {
        // Fetch depenses from your service
        List<Depense> depenses = DepenseService.getInstance().readAll();

        // Clear existing items in the TableView
        depenseTableView.getItems().clear();

        // Add fetched depenses to the TableView
        depenseTableView.getItems().addAll(depenses);
    }

    //colonne action bouton supprimer et modifier
    private Callback<TableColumn<Depense, Void>, TableCell<Depense, Void>> getActionCellFactory() {
        return new Callback<TableColumn<Depense, Void>, TableCell<Depense, Void>>() {
            @Override
            public TableCell<Depense, Void> call(final TableColumn<Depense, Void> param) {
                final TableCell<Depense, Void> cell = new TableCell<Depense, Void>() {
                    private final Button deleteButton = new Button("Supprimer");
                    private final Button modifyButton = new Button("Modifier");

                    {
                        deleteButton.setOnAction(event -> {
                            // Create a custom dialog
                            Dialog<ButtonType> dialog = new Dialog<>();
                            dialog.setTitle("Confirmation");
                            dialog.setHeaderText("Delete Depense");

                            Label label = new Label("Etes vous sûre de vouloir supprimer cette depense ?");
                            dialog.getDialogPane().setContent(label);

                            ButtonType yesButtonType = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
                            ButtonType noButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(yesButtonType, noButtonType);



                            Optional<ButtonType> result = dialog.showAndWait();

                            if (result.isPresent() && result.get() == yesButtonType) {
                                Depense depense = getTableView().getItems().get(getIndex());
                                DepenseService ds = new DepenseService();
                                ds.delete(depense.getId());
                                getTableView().getItems().remove(depense);
                                TaxService ts =new TaxService();

                                ts.delete(depense.getTax().getId());
                                totalDpense();
                                try {
                                    tax_Dpense();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                try {
                                    loadDepenseFromDatabase();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });

                        modifyButton.setOnAction(event -> {
                            Depense depense = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Depense/DepenseModify.fxml"));
                                Parent root = loader.load();
                                depenseModifyContoller dmc= loader.getController();
                                dmc.setDepense(depense);
                                totalDpense();
                                tax_Dpense();


                                // Get the controller instance

                                // Pass the Depense object to the controller
                                dmc.initData(depense);
                                Scene scene = new Scene(root);

                                Stage newStage = new Stage();
                                newStage.setScene(scene);
                                newStage.setOnHidden(e-> {
                                    try {
                                        loadDepenseFromDatabase();
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                });

                                newStage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Handle modify action
                            // For example: modifyDepense(depense);


                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10);
                            hbox.getChildren().addAll(deleteButton, modifyButton);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };
    }

    //methode qui calcule la somme totale des dépense
    public Double totalDpense()  {
        List<Depense> depenses = DepenseService.getInstance().readAll();
        double totalDepenses = Depense.calculateTotalDepenses(depenses);
        total_depense_card.setText(String.format("%.2f", totalDepenses));
        return totalDepenses;
    }
    public Double tax_Dpense() throws SQLException {
        TaxService ts = new TaxService();
        List<Tax> taxDepTotal = ts.readAll();
        UserService us = new UserService();
        User u =  us.getUserByEmail2(Model.getInstance().getUser().getEmail());
        System.out.println(u);
/*
        Model.getInstance().getUser().setTotal_tax(ts.sommeTaxByDepense());
*/
/*
        us.updatewTax(Model.getInstance().getUser());
*/
        double totalTax = Tax.calculateTotalTax(taxDepTotal);

        double totaltax = u.getTotal_tax();
        System.out.println(totaltax);
        tax_depense.setText(String.format("%.2f", totaltax));

        return totalTax;
    }




    //methode de filtre par mois
    private void filterByMonth(Month selectedMonth) throws SQLException {
        // Clear existing depense cells from the container
        depenseTableView.getItems().clear();

        // Get all depenses from the service
        List<Depense> allDepenses = DepenseService.getInstance().readAll();

        // Filter depenses by the selected month
        List<Depense> depensesForMonth = allDepenses.stream()
                .filter(depense -> depense.getDate().getMonth() == selectedMonth)
                .collect(Collectors.toList());
        depenseTableView.setItems(FXCollections.observableArrayList(depensesForMonth));


    }

//methode de filre par montant et type depense

    private void loadDepenseFromDatabase() throws SQLException {
        // Fetch events from EvennementService
        List<Depense> depenses = DepenseService.getInstance().readAll();

        // Display events in TableView
        ObservableList<Depense> eventList = FXCollections.observableArrayList(depenses);
        depenseTableView.setItems(eventList);
        totalDpense();
        tax_Dpense();
        Map<YearMonth, Double> monthlyExpenses = calculateMonthlyExpenses(depenses);
        displayMonthlyExpensesChart(monthlyExpenses);


    }
//statistique

    private Map<YearMonth, Double> calculateMonthlyExpenses(List<Depense> allDepenses) {
        Map<YearMonth, Double> monthlyExpenses = new HashMap<>();
        for (Depense depense : allDepenses) {
            YearMonth yearMonth = YearMonth.from(depense.getDate());

            double amount = depense.getMontant();
            monthlyExpenses.put(yearMonth, monthlyExpenses.getOrDefault(yearMonth, 0.0) + amount);
        }
        return monthlyExpenses;
    }

    public void displayMonthlyExpensesChart(Map<YearMonth, Double> monthlyExpenses) {
        // Get the existing LineChart from your FXML file
        LineChart<String, Number> lineChart = chartContainer; // Assuming chartContainer is your LineChart

        // Clear existing data in the chart
        lineChart.getData().clear();

        // Set chart title and axis labels
        lineChart.setTitle("Monthly Expenses");
        lineChart.getXAxis().setLabel("Month");
        lineChart.getYAxis().setLabel("Total Expenses");

        // Add data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (YearMonth month : monthlyExpenses.keySet()) {
            series.getData().add(new XYChart.Data<>(month.toString(), monthlyExpenses.get(month)));
            String monthLabel = month.toString();
            series.getData().add(new XYChart.Data<>(monthLabel,0));
            series.setName("dépense par mois");
        }
        lineChart.getData().add(series);
    }


//excel


    public void exportToExcel() {
        List<Depense> depenseList = depenseTableView.getItems();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Depense Data");

        // Create headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Type");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Montant");
        headerRow.createCell(4).setCellValue("Tax");

        // Add data
        int rowNum = 1;
        for (Depense depense : depenseList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(depense.getId());
            row.createCell(1).setCellValue(depense.getType());
            row.createCell(2).setCellValue(depense.getDate().toString());
            row.createCell(3).setCellValue(depense.getMontant());
            row.createCell(4).setCellValue(depense.getUser().getId());
        }

        // Write the workbook content to a file
        try (FileOutputStream fileOut = new FileOutputStream("Depense2.xlsx")) {
            workbook.write(fileOut);

            // Open the Excel file
            File file = new File("Depense2.xlsx");
            Platform.runLater(() -> {
                try {
                    java.awt.Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}