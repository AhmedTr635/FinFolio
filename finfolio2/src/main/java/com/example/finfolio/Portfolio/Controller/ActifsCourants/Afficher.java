package com.example.finfolio.Portfolio.Controller.ActifsCourants;

import com.example.finfolio.Entite.User;
import com.example.finfolio.Portfolio.Entite.ActifsCourants;
import com.example.finfolio.Portfolio.Entite.ActifsNonCourants;
import com.example.finfolio.Service.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Afficher<tableview> {
    public PieChart piechart;
    @FXML
    private TableColumn<ActifsNonCourants, Integer> achatid;
    @FXML
    private TableColumn<ActifsNonCourants, Integer> ida1;
    @FXML
    private TableColumn<ActifsNonCourants, Integer> valeurid;

    @FXML
    private TableColumn<ActifsNonCourants, String> nameid1;
    @FXML
    private TableColumn<ActifsNonCourants, String> typeid1;
    @FXML
    private TableColumn<ActifsNonCourants, Integer> userid1;
    @FXML
    private TextField research;

    @FXML
    private Button searchid;
    @FXML
    private Button updateid;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ComboBox<String> actifchoice;

    @FXML
    private TableColumn<ActifsCourants, Integer> ida;

    @FXML
    private TableColumn<ActifsCourants, Float> montantid;

    @FXML
    private TableColumn<ActifsCourants, String> nameid;

    @FXML
    private TableView<ActifsCourants> tab1;

    @FXML
    private TableColumn<ActifsCourants, String> typeid;

    @FXML
    private TableColumn<ActifsCourants, Integer> userid;
    @FXML
    private Button addid;
    @FXML
    private Button deleteid;
    @FXML
    private Button bilan_id;
    @FXML
    private TableView<ActifsNonCourants> tab11;
    @FXML
    private Button addid1;
    @FXML
    private Button deleteid1;
    @FXML
    private Button updateid1;


    //private BoxBlur blur;
    ObservableList<ActifsCourants> tableview= FXCollections.observableArrayList();
    private FilteredList<ActifsCourants> filteredData;
    @FXML


//    void loadtable(){
//        TableColumn<ActifsCourants, Integer> typeColumn = new TableColumn<>("id");
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        TableColumn<ActifsCourants, String> dateColumn = new TableColumn<>("name");
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<ActifsCourants, Float> montantColumn = new TableColumn<>("Montant");
//        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
//        TableColumn<ActifsCourants, Float> actionColumn = new TableColumn<>("Atype");
//        actionColumn.setCellFactory(getActionCellFactory()); // Set custom cell factory
//
//        // Add all columns to the TableView
//        tab1.getColumns().addAll(ida, nameid, montantid, typeid,userid);
//        tab1.getStyleClass().add("custom-table-view");
//
//        // Apply CSS styling to individual columns
//        depenseTableView.getStyleClass().add("custom-table-view");
//
//        // Apply CSS styling to individual columns
//        typeColumn.getStyleClass().add("type-column");
//        dateColumn.getStyleClass().add("date-column");
//        montantColumn.getStyleClass().add("montant-column");
//        actionColumn.getStyleClass().add("action-column");
//
//        // Add columns to the TableView
//
//        // Populate the TableView with data (you can implement this according to your application logic)
//        populateTableView();
//    }
    void initialize() throws SQLException {
        ActifsCservices pc=new ActifsCservices();
        List<ActifsCourants> liste=pc.readAll();
        ObservableList<ActifsCourants> actifsC= FXCollections.observableArrayList(liste);
        ida.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameid.setCellValueFactory(new PropertyValueFactory<>("name"));
        montantid.setCellValueFactory(new PropertyValueFactory<>("montant"));
        typeid.setCellValueFactory(new PropertyValueFactory<>("type"));
        userid.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        refreshTableView();
        tab1.setItems(actifsC);
        tab1.setRowFactory(tv -> {
            TableRow<ActifsCourants> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    ActifsCourants rowData = row.getItem();
                    showButtons(rowData);
                }
            });
            return row;
        });
        userid.setVisible(false);
        userid1.setVisible(false);
        deleteid1.setVisible(false);
        addid1.setVisible(false);
        updateid1.setVisible(false);
        updateid.setVisible(false);
        deleteid.setVisible(false);
        tab11.setVisible(false);

        ObservableList<String> items = FXCollections.observableArrayList("Actifs Courants", "Actifs Non Courants");
        actifchoice.setItems(items);
        //FilteredList<ActifsCourants> filteredData = new FilteredList<>(tableview, p -> true);

        initPieChart();
        setUpSearchFilter();
    }

    private void setUpSearchFilter() {
        // Wrap the ObservableList in a FilteredList
        filteredData = new FilteredList<>(tableview, p -> true);

        // Set up the search functionality
        research.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Search Text: " + newValue); // Debug statement

            filteredData.setPredicate(actifCourant -> filterActifCourant(actifCourant, newValue));

            // Update the TableView with filtered data
            tab1.setItems(filteredData);
        });
    }

    private boolean filterActifCourant(ActifsCourants actifCourant, String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return true; // Show all items when search text is empty
        }

        // Check if any of the fields contain the search text (case-insensitive)
        String lowerCaseSearchText = searchText.toLowerCase();
        return actifCourant.getName().toLowerCase().contains(lowerCaseSearchText) ||
                String.valueOf(actifCourant.getId()).contains(lowerCaseSearchText) ||
                String.valueOf(actifCourant.getMontant()).contains(lowerCaseSearchText) ||
                actifCourant.getType().toLowerCase().contains(lowerCaseSearchText) ||
                String.valueOf(actifCourant.getUser().getId()).contains(lowerCaseSearchText);
    }




    private void showButtons(ActifsCourants rowData) {

        updateid.setVisible(true);
        deleteid.setVisible(true);

    }
    private void showButtons2(ActifsNonCourants rowData) {

        updateid1.setVisible(true);
        deleteid1.setVisible(true);

    }

    @FXML
    void handlechoice(ActionEvent event) throws SQLException {
        String selectedOption = actifchoice.getValue();

        // Toggle visibility based on the selected item
        if ("Actifs Courants".equals(selectedOption)) {
            tab1.setVisible(true);
            tab11.setVisible(false);
            addid.setVisible(true);

            addid1.setVisible(false);
            deleteid1.setVisible(false);
            updateid1.setVisible(false);
            tab1.setRowFactory(tv -> {
                TableRow<ActifsCourants> row = new TableRow<>();
                row.setOnMouseClicked(event1 -> {
                    if (!row.isEmpty()) {
                        ActifsCourants rowData = row.getItem();
                        showButtons(rowData);
                    }
                });
                return row;
            });
            updateid.setVisible(false);
            deleteid.setVisible(false);
        } else  {
            tab1.setVisible(false);
            tab11.setVisible(true);
            ActifsNCservice pnc=new ActifsNCservice();
            List<ActifsNonCourants> liste=pnc.readAll();
            ObservableList<ActifsNonCourants> actifsNC= FXCollections.observableArrayList(liste);
            ida1.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameid1.setCellValueFactory(new PropertyValueFactory<>("name"));
            typeid1.setCellValueFactory(new PropertyValueFactory<>("type"));
            valeurid.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            achatid.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
            userid1.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            refreshTableViewNC();
            tab11.setItems(actifsNC);
            addid.setVisible(false);
            deleteid.setVisible(false);
            updateid.setVisible(false);
            addid1.setVisible(true);
            deleteid1.setVisible(true);
            updateid1.setVisible(true);
            tab11.setRowFactory(tv -> {
                TableRow<ActifsNonCourants> row = new TableRow<>();
                row.setOnMouseClicked(event1 -> {
                    if (!row.isEmpty()) {
                        ActifsNonCourants rowData = row.getItem();
                        showButtons2(rowData);
                    }
                });
                return row;
            });
            updateid1.setVisible(false);
            deleteid1.setVisible(false);
        }
    }

    // You can add more methods and logic as needed for your application






   /* public void setBlur(BoxBlur blur) {
        this.blur = blur;

        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);

    }
*/
    @FXML
    void deleteactif(ActionEvent event) {
        int selectedIndex = tab1.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            ActifsCourants a=tab1.getItems().get(selectedIndex);
            //int id = (int) tab1.getItems().get(selectedIndex).getUser_id();
            ActifsCservices ac=new ActifsCservices();
            ac.delete(a);
            tab1.getItems().remove(selectedIndex);


    }
}



    @FXML
    void adder(ActionEvent event)  {



            try {

                Parent root= FXMLLoader.load(getClass().getResource("/com/example/finfolio/Actifs/ajouter.fxml"));
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.setOnHidden(e-> {
                    try {
                        refreshTableView();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                stage.show(); }
            catch (IOException e){throw new RuntimeException(e);}

    }

    @FXML
    void updateactif(ActionEvent event) {
        ActifsCourants selectedItem = tab1.getSelectionModel().getSelectedItem();

        try {


             FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/finfolio/Actifs/update.fxml"));
            Parent root=loader.load();
            Update Controller=loader.getController();
            Controller.initData(selectedItem);
            Controller.setA(selectedItem);
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnHidden(e-> {
                try {
                    refreshTableView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            stage.show(); }
        catch (IOException e){throw new RuntimeException(e);}


    }

    private void refreshTableView() throws SQLException {
        ActifsCservices us=new ActifsCservices();
        List<ActifsCourants> users = us.readAll();

        // Display events in TableView
        ObservableList<ActifsCourants> userList = FXCollections.observableArrayList(users);
        tab1.setItems(userList);

    }
    private void refreshTableViewNC() throws SQLException {
        ActifsNCservice us=new ActifsNCservice();
        List<ActifsNonCourants> users = us.readAll();

        // Display events in TableView
        ObservableList<ActifsNonCourants> userList = FXCollections.observableArrayList(users);
        tab11.setItems(userList);
        updateid1.setVisible(false);
        deleteid1.setVisible(false);

    }
    @FXML
    void updateactifNC(ActionEvent event) {
        ActifsNonCourants selectedItem = tab11.getSelectionModel().getSelectedItem();

        try {

            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/finfolio/Actifs/update2.fxml"));
            Parent root=loader.load();
            com.example.finfolio.Portfolio.Controller.ActifsNonCourants.Update Controller=loader.getController();
            Controller.initData(selectedItem);
            Controller.setA(selectedItem);
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnHidden(e-> {
                try {
                    refreshTableViewNC();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            stage.show(); }
        catch (IOException e){throw new RuntimeException(e);}


    }


    @FXML
    void deleteactifNC(ActionEvent event) {
        int selectedIndex = tab11.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            ActifsNonCourants a=tab11.getItems().get(selectedIndex);

            ActifsNCservice anc=new ActifsNCservice();
            anc.delete(a);
            tab11.getItems().remove(selectedIndex);


        }

    }



    @FXML
    void adderNC(ActionEvent event) {
        try {

            Parent root= FXMLLoader.load(getClass().getResource("/com/example/finfolio/Actifs/ajouter2.fxml"));
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnHidden(e-> {
                try {
                    refreshTableViewNC();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            stage.show(); }
        catch (IOException e){throw new RuntimeException(e);}
    }


















    @FXML
    void generer_bilan(String filePath) throws IOException {


        String filename = filePath;;

            // Initialize document and PDF writer
            PdfWriter pdfWriter = new PdfWriter(filename);
            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDoc,PageSize.A4.rotate());

            // Define A4 page dimensions (inches)
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();

            // Calculate individual table area (assuming equal size)
            float tableWidth = (pageWidth - 4 * 20) / 2; // Subtract margins and half for two columns
            float tableHeight = (pageHeight - 4 * 20) / 2; // Subtract margins and half for two rows

            // Create a main table with 2 rows and 2 columns
            Table mainTable = new Table(2);
            mainTable.setWidth(tableWidth*3);
            mainTable.setHeight(pageHeight-380);

            // Sample data (replace with your actual data retrieval logic)
            String[][] tablesData = {
                    {"Table 1 Data"}, {"Table 2 Data"},
                    {"Table 3 Data"}, {"Table 4 Data"}
            };


            ActifsCservices localActifsCourantsService = new ActifsCservices(); // Or obtain them via dependency injection
            ActifsNCservice localActifsNonCourantsService = new ActifsNCservice() ;
            DepenseService  localDepenseService=new DepenseService();
            CreditService localCreditService=new CreditService();


            Double amortissementAC=localActifsCourantsService.totalamortissement();
            Double amortissementANC=localActifsNonCourantsService.totalamortissement();

            // Retrieve data using service methods
            List<String[]> actifsCourantsTableData = convertObjectsToStringArrays(localActifsCourantsService.readAll());
            List<String[]> actifsNonCourantsTableData = convertObjectsToStringArrays(localActifsNonCourantsService.readAll());
            List<String[]> DepensesTableData = convertObjectsToStringArrays(localDepenseService.readAll());
            List<String[]> CreditsTableData =convertObjectsToStringArrays(localCreditService.readAllCredits());

            // Create separate populated tables
            Div actifsCourantsTitle = new Div();
            actifsCourantsTitle.add(new Paragraph("Actifs Courants").setTextAlignment(TextAlignment.LEFT));
            //document.add(actifsCourantsTitle);

// Add the table for Actifs Courants
            Table actifsCourantsTable = createAndPopulateTable(actifsCourantsTableData, "Actifs Courants");
            Div actifsCourantsContainer = new Div();
            actifsCourantsContainer.setPadding(20);
            actifsCourantsContainer.add(actifsCourantsTable);
// Add the title for the second table
            Div actifsNonCourantsTitle = new Div();
            actifsNonCourantsTitle.add(new Paragraph("Actifs Non Courants").setTextAlignment(TextAlignment.LEFT));
            //document.add(actifsNonCourantsTitle);

// Add the table for Actifs Non Courants
            Table actifsNonCourantsTable = createAndPopulateTableANC(actifsNonCourantsTableData, "Actifs Non Courants");
            Div actifsNonCourantsContainer = new Div();
            actifsNonCourantsContainer.setPadding(20);
            actifsNonCourantsContainer.add(actifsNonCourantsTable);



            //Paragraph actifsCourantsSumParagraph = new Paragraph("Total Actifs Courants: " + actifsCourantsSum);
            float columnWidth = tableWidth / 3;
            Div totalAc=new Div();
            totalAc.add(new Paragraph("Total Actifs Courants = ").add(String.valueOf(calculateAndDisplaySumAC()))
                    .setTextAlignment(TextAlignment.LEFT));

            Cell actifsCourantsCell = new Cell();
            actifsCourantsCell.add(actifsCourantsTitle);
            actifsCourantsCell.add(actifsCourantsTable);
            actifsCourantsCell.add(totalAc);
            actifsCourantsCell.setPadding(20);

            actifsCourantsCell.add("Amortissement= "+String.valueOf(amortissementAC));

            actifsCourantsCell.setPadding(20);
            actifsCourantsTable.setWidth(tableWidth );// Adjust padding if needed









            Cell actifsNonCourantsCell = new Cell();
            actifsNonCourantsCell.add(actifsNonCourantsTitle).add(actifsNonCourantsTable).
                    add("Total actifs non courants ="+String.valueOf(calculateAndDisplaySumANC()))
                    .add("Amortissement= "+String.valueOf(amortissementANC));
            //Div totalAnc=new Div();
            // totalAc.add(new Paragraph("Total Actifs Non Courants = ").add(String.valueOf(calculateAndDisplaySumANC()))
            //.setTextAlignment(TextAlignment.LEFT));//actifsNonCourantsCell;
            actifsNonCourantsCell.setPadding(20);
            actifsNonCourantsTable.setWidth(tableWidth );// Adjust padding if needed

        Table DepenseTable = createAndPopulateTableD(DepensesTableData, "Dépense");
        Cell DepenseCell = new Cell();
        Div DepenseTitle = new Div();
        DepenseTitle.add(new Paragraph("Dépenses").setTextAlignment(TextAlignment.LEFT));
        DepenseCell.add(DepenseTitle).add(DepenseTable);


        actifsCourantsCell.setPadding(20);

        Table CreditTable=createAndPopulateTable(CreditsTableData,"Crédits");
        Cell CreditCell = new Cell();
        Div CreditTitle = new Div();
        CreditTitle.add(new Paragraph("Crédits").setTextAlignment(TextAlignment.LEFT)); FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/finfolio/User/credit.fxml"));
        Parent root=loader.load();

        com.example.finfolio.Credits.CreditController Controller=loader.getController();
        //Double totalCr=Controller. calculateAndDisplaySumTotalCrWT();
        Div CreditTotal = new Div();
        CreditTotal.add(new Paragraph("Total Crédits = ").add(String.valueOf(Controller.calculateAndDisplaySumTotalCrWT())));
        CreditCell.add(CreditTitle).add(CreditTable).add(CreditTotal);


        mainTable.addCell(actifsCourantsCell);
        mainTable.addCell(CreditCell);
        mainTable.addCell(actifsNonCourantsCell);

        mainTable.addCell(DepenseCell);


            mainTable.setBackgroundColor(Color.CYAN);
            mainTable.setBorder(new SolidBorder(Color.BLACK, 3));

//// Set the border for the cells in the main table
//        mainTable.getCells().get(0).setBorder(new SolidBorder(1, ColorConstants.BLACK));
//        mainTable.getCells().get(1).setBorder(new SolidBorder(1, ColorConstants.BLACK));


// Add the mainTable to the Cell
            //mainTableCell.add(mainTable);

            Paragraph title = new Paragraph("Mon Bilan Financier").setBold().setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(mainTable);

            Double recetteto=calculateAndDisplaySumAC()+calculateAndDisplaySumANC()- Controller.calculateAndDisplaySumTotalCrWT();
            Paragraph recette = new Paragraph("recette finale= "+recetteto);
            document.add(recette);
            document.setBorder(new SolidBorder(Color.DARK_GRAY, 80));
            document.setBackgroundColor(Color.GRAY);

            //createWatermark(pdfDoc,"\"D:\\pidev2\\FinFolio2\\FinFolio\\FinFolio\\FinFolio-feature_Gestion_User\\finfolio2\\src\\main\\resources\\com\\example\\finfolio\\Pics\\logo.png\"");
//       /* String barcodeValue = "1234567890";
//        Image barcodeImage = create1DBarcode(pdfDoc, barcodeValue, Barcode128.class);
//        document.add(barcodeImage);*/
addWatermark(pdfDoc,"C:\\Users\\PC\\Desktop\\finfolio2\\src\\main\\resources\\com\\example\\finfolio\\Pics\\icon.png");
            // Close the generated PDF document
            document.close();

//        PdfWriter pdfWriter2 = new PdfWriter("letter.pdf");
//        PdfDocument pdfDoc2 = new PdfDocument(pdfWriter);
//        Document document2 = new Document(pdfDoc,PageSize.A4.rotate());
//
//        PdfPageFormCopier formCopier = new PdfPageFormCopier();
//        for (int i = 1; i <= originalPdfDoc.getNumberOfPages(); i++) {
//            PdfPage page = originalPdfDoc.getPage(i);
//            document2.addNewPage(page.getPageSize());
//            formCopier.copyPageTo(page, targetPdfDoc.getLastPage());
//        }
        // Prompt user to choose a download location





            System.out.println("PDF created"+ filename);
        }
    private void addWatermark(PdfDocument pdfDocument, String imagePath) {
        try {
            ImageData imageData = ImageDataFactory.create(imagePath);

            float pageWidth = pdfDocument.getDefaultPageSize().getWidth();
            float pageHeight = pdfDocument.getDefaultPageSize().getHeight();

            float imageWidth = imageData.getWidth();
            float imageHeight = imageData.getHeight();

            // Position the image in the upper left corner
            float x = 0;
            float y = pdfDocument.getDefaultPageSize().getHeight() - imageHeight;

            PdfCanvas canvas = new PdfCanvas(pdfDocument.getFirstPage());
            canvas.addImage(imageData, x, y, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void generatePDF(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName("Bilan.pdf");
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            String filePath = file.getAbsolutePath();
            generer_bilan(filePath);

            // Handle opening file using platform-specific method
            openFile(file);
        }

    }
    private void openFile(File file) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // For Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
            } else if (os.contains("mac")) {
                // For   macOS
                Runtime.getRuntime().exec("open " + file.getAbsolutePath());
            } else {
                // For Linux/Unix
                Runtime.getRuntime().exec("xdg-open " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    public void mergePdfs() throws IOException, DocumentException {
//        try {
//            String[] files = { "letter.pdf" ,"output1.pdf" };
//            PdfWriter writer = new PdfWriter("CombinedFile.pdf");
//            PdfDocument originalPdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader(files[0]), writer);
//            PdfDocument newDocument = new PdfDocument(writer);
//            PdfPageEventHelper helper = new PdfPageEventHelper() {
//                @Override
//                public void onEndPage(com.itextpdf.text.pdf.PdfWriter writer, com.itextpdf.text.Document document) {
//                    super.onEndPage(writer, document);
//                    PdfCopy copy = null;
//                    try {
//                        copy = new PdfCopy(new ByteArrayOutputStream());
//                    } catch (DocumentException e) {
//                        throw new RuntimeException(e);
//                    }
//                    copy.setAdd DickInformation(false);
//                    for (int i = 1; i < files.length; i++) {
//                        PdfDocument template = new PdfDocument(new PdfReader(files[i]));
//                        PdfImportedPage importedPage;
//                        for (int j = 1; j <= template.getNumberOfPages(); j++) {
//                            importedPage = copy.getImportedPage(template, j);
//                            newDocument.addNewPage(importedPage.getPageSize());
//                            newDocument.addPage(importedPage);
//                        }
//                        template.close();
//                    }
//                    ByteArrayOutputStream outputStream = (ByteArrayOutputStream) copy.getOutputStream();
//                    PdfDocument importedPdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(outputStream.toByteArray())));
//                    for (int i = 1; i <= importedPdfDoc.getNumberOfPages(); i++) {
//                        importedPage = newDocument.getImportedPage(importedPdfDoc, i);
//                        newDocument.addNewPage(importedPage.getPageSize());
//                        newDocument.addPage(importedPage);
//                    }
//                    importedPdfDoc.close();
//                    outputStream.close();
//                }
//            };
//            originalPdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, (IEventHandler) helper);
//            originalPdfDoc.copyPagesTo(1, originalPdfDoc.getNumberOfPages(), newDocument);
//            originalPdfDoc.close();
//            newDocument.close();
//        } catch (Exception i) {
//            System.out.println(i);
//        }
//    }

  /* private <C extends Barcode1D> Image create1DBarcode(PdfDocument pdfDocument, String code, Class<C> barcodeClass) throws IOException {
        try {
            var codeObject = barcodeClass.getConstructor(PdfDocument.class).newInstance(pdfDocument);
            codeObject.setCode(code);
            return new Image(codeObject.createFormXObject(pdfDocument));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new IOException("Failed to create barcode", e);
        }
    }*/

        public static <T> List<String[]> convertObjectsToStringArrays(List<T> objects){
            List<String[]> stringArrays = new ArrayList<>();
            for (Object obj : objects) {
                Class<?> objClass = obj.getClass();
                // Get field names (modify as needed)
                Field[] fields = objClass.getDeclaredFields();
                String[] rowData = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true); // Allow access to private fields
                    Object fieldValue = null;
                    try {
                        fieldValue = fields[i].get(obj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    rowData[i] = fieldValue != null ? fieldValue.toString() : ""; // Handle null values
                }
                stringArrays.add(rowData);
            }
            return stringArrays;
        }

        private Table createAndPopulateTable(List<String[]> tableData, String title) {


            Table table = new Table(3);
            Cell titleCell = new Cell().add(new Paragraph(title));
            titleCell.setPaddingTop(10);
            //titleCell.setBackgroundColor(Color.red);
            //Paragraph tableTitle = new Paragraph(title).setTextAlignment(TextAlignment.LEFT);
            //document.add(tableTitle);

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Price");

            for (String[] row : tableData) {
                table.addCell(row[0]);
                table.addCell(row[1]);
                table.addCell(row[2]);
            }







            return table;
        }


        private Table createAndPopulateTableANC(List<String[]> tableData, String title) {


            Table table = new Table(3);
            Cell titleCell = new Cell().add(new Paragraph(title));
            titleCell.setPaddingTop(10);
            //titleCell.setBackgroundColor(Color.red);
            //Paragraph tableTitle = new Paragraph(title).setTextAlignment(TextAlignment.LEFT);
            //document.add(tableTitle);

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Price");

            for (String[] row : tableData) {
                table.addCell(row[0]);
                table.addCell(row[1]);
                table.addCell(row[4]);
            }







            return table;
        }

    private Table createAndPopulateTableD(List<String[]> tableData, String title) {


        Table table = new Table(3);
        Cell titleCell = new Cell().add(new Paragraph(title));
        titleCell.setPaddingTop(10);
        //titleCell.setBackgroundColor(Color.red);
        //Paragraph tableTitle = new Paragraph(title).setTextAlignment(TextAlignment.LEFT);
        //document.add(tableTitle);

        table.addHeaderCell("ID");
        table.addHeaderCell("Type");
        table.addHeaderCell("Montant");

        for (String[] row : tableData) {
            table.addCell(row[0]);
            table.addCell(row[3]);
            table.addCell(row[4]);
        }
        return table;
    }
    private Table createAndPopulateTableCr(List<String[]> tableData, String title) {


        Table table = new Table(3);
        Cell titleCell = new Cell().add(new Paragraph(title));
        titleCell.setPaddingTop(10);
        //titleCell.setBackgroundColor(Color.red);
        //Paragraph tableTitle = new Paragraph(title).setTextAlignment(TextAlignment.LEFT);
        //document.add(tableTitle);


        table.addHeaderCell("Montant");
        table.addHeaderCell("InteretMax");
        table.addHeaderCell("InteretMin");


        for (String[] row : tableData) {
            table.addCell(row[1]);
            table.addCell(row[2]);
            table.addCell(row[3]);

        }
        return table;
        }
        private PdfFormXObject createWatermark(PdfDocument pdfDoc, String imagePath) throws IOException {
            // Create a new canvas
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(1));

            // Read the image from the file system
            ImageData imageData = ImageDataFactory.create(imagePath);

            // Create a new canvas with a watermark
            PdfCanvas watermarkCanvas = new PdfCanvas(pdfDoc.getPage(1).newContentStreamBefore(), pdfDoc.getPage(1).getResources(), pdfDoc);
            float imageWidth = imageData.getWidth();
            float imageHeight = imageData.getHeight();
            float pageWidth = pdfDoc.getPage(1).getPageSize().getWidth();
            float pageHeight = pdfDoc.getPage(1).getPageSize().getHeight();
            float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);
            float x = (pageWidth - imageWidth * scale) / 2;
            float y = (pageHeight - imageHeight * scale) / 2;
            watermarkCanvas.addImage(imageData, scale, 0, 0, imageWidth, imageHeight, x);

            // Create a form XObject from the watermark canvas
            PdfFormXObject watermark = new PdfFormXObject(new Rectangle(0, 0, pageWidth, pageHeight));
            watermark.getPdfObject().put(PdfName.Resources, pdfDoc.getPage(1).getResources().getPdfObject());
            watermark.getPdfObject().put(PdfName.BBox, new PdfArray(new float[]{0, 0, pageWidth, pageHeight}));
            watermark.getPdfObject().put(PdfName.Group, new PdfDictionary().makeIndirect(pdfDoc));
            watermark.getPdfObject().setModified();
            watermark.getPdfObject().getIndirectReference();

            // Return the watermark
            return watermark;
        }
    private Double calculateAndDisplaySumANC() {
        double sum = 0.0;


        for (ActifsNonCourants item : tab11.getItems()) {
            sum += item.getPrix_achat();
        }
        return sum;

    }

    private Double calculateAndDisplaySumAC() {
        double sum = 0.0;


        for (ActifsCourants item : tab1.getItems()) {
            sum += item.getMontant();
        }
        return sum;

    }
    private void initPieChart() {
        piechart.setPrefSize(300, 300);
        ActifsCservices cs = new ActifsCservices();
        ActifsNCservice ncs = new ActifsNCservice();

        piechart.setTitle("Pourcentage d'admins et utilisateurs");

        List<ActifsCourants> allAC = cs.readAll();
        List<ActifsNonCourants> allACn = ncs.readAll();

        long totalActifs = allACn.size()+allAC.size();
        long ACCount = allAC.stream().count();
        long ANCCount = allACn.stream().count();

        double userPercentage = ((double) ANCCount / totalActifs) * 100;
        double adminPercentage = ((double) ACCount / totalActifs) * 100;

        PieChart.Data slice1 = new PieChart.Data("Actifs Courants " + ACCount + " - " + String.format("%.2f", userPercentage) + "%", ACCount);
        PieChart.Data slice2 = new PieChart.Data("Actifs Non courants " + ANCCount + " - " + String.format("%.2f", adminPercentage) + "%", ANCCount);

        piechart.getData().addAll(slice1, slice2);

        // Set custom colors for each slice
        slice1.getNode().setStyle("-fx-pie-color: #123499;"); // Blue
        slice2.getNode().setStyle("-fx-pie-color: #00FF00;"); // Green

        // Create a ParallelTransition to animate separation and expansion
        ParallelTransition parallelTransition = new ParallelTransition();

        // Animate separation of slices in opposite directions
        TranslateTransition slice1Translate = new TranslateTransition(Duration.seconds(1), slice1.getNode());
        slice1Translate.setToX(-20); // Adjust separation distance for slice 1 (opposite direction)
        parallelTransition.getChildren().add(slice1Translate);

        TranslateTransition slice2Translate = new TranslateTransition(Duration.seconds(1), slice2.getNode());
        slice2Translate.setToX(20); // Adjust separation distance for slice 2 (opposite direction)
        parallelTransition.getChildren().add(slice2Translate);

        // Animate expansion of slices
        ScaleTransition slice1Scale = new ScaleTransition(Duration.seconds(1), slice1.getNode());
        slice1Scale.setToX(1.1); // Adjust expansion factor
        slice1Scale.setToY(1.1); // Adjust expansion factor
        parallelTransition.getChildren().add(slice1Scale);

        ScaleTransition slice2Scale = new ScaleTransition(Duration.seconds(1), slice2.getNode());
        slice2Scale.setToX(1.1); // Adjust expansion factor
        slice2Scale.setToY(1.1); // Adjust expansion factor
        parallelTransition.getChildren().add(slice2Scale);

        parallelTransition.play();
    }

}


























