package com.example.finfolio.Evenement;


import Models.Model;
import com.example.finfolio.Entite.Don;
import com.example.finfolio.Evenement.CalendarActivity;
import com.example.finfolio.Service.DonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;  // The currently focused date
    ZonedDateTime today;      // Today's date

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);  // Move dateFocus back by one month
        calendar.getChildren().clear();       // Clear the calendar UI
        drawCalendar();                        // Redraw the calendar UI
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        // Set the year and month text
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        // Calculate dimensions and spacing for calendar elements
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // Retrieve activities for the current month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus.toLocalDate(), Model.getInstance().getUser().getId());  // Pass the userId parameter

        // Calculate the maximum number of days in the current month
        int monthMaxDate = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());

        // Calculate the offset for the first day of the month
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        // Loop through each cell in the calendar grid
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();  // Create a stack pane for each cell

                Rectangle rectangle = new Rectangle();  // Create a rectangle to represent each day
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);  // Add rectangle to stack pane

                // Calculate the date for the current cell
                int calculatedDate = (j + 1) + (7 * i);

                // Check if the calculated date falls within the current month
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));  // Create text to display the date
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);  // Add date text to stack pane

                        // Retrieve activities for the current date
                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            // Create and display calendar activities
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    // Highlight today's date
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);  // Add stack pane to the calendar
            }
        }
    }

    // Method to create and display calendar activities for a given date
    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();  // Create a vertical box to hold calendar activities
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");  // Display ellipsis for additional activities
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // Handle click event for displaying all activities
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getEventName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);  // Add activity text to the vertical box
            text.setOnMouseClicked(mouseEvent -> {
                // Handle click event for displaying activity details
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);  // Adjust vertical position of activity box
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);  // Set maximum width for activity box
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);  // Set maximum height for activity box
        calendarActivityBox.setStyle("-fx-background-color:rgba(8,120,201,0.68)");  // Set background color for activity box
        stackPane.getChildren().add(calendarActivityBox);  // Add activity box to the stack pane
    }

    // Method to retrieve calendar activities for the current month
    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(LocalDate monthDate, int userId) {
        // Retrieve donations for the current month for the specified user
        List<Don> donations = DonService.getInstance().getDonationsForMonth(monthDate, userId);

        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        // Add donations to the calendarActivityMap
        for (Don donation : donations) {
            LocalDate donationDate = donation.getEvennement().getDate();  // Get donation date
            int dayOfMonth = donationDate.getDayOfMonth();  // Get day of month for the donation date

            ZonedDateTime donationDateTime = donationDate.atStartOfDay(dateFocus.getZone());  // Convert to ZonedDateTime
            CalendarActivity calendarActivity = new CalendarActivity(donationDateTime, donation.getEvennement().getNom());  // Create calendar activity

            if (!calendarActivityMap.containsKey(dayOfMonth)) {
                calendarActivityMap.put(dayOfMonth, new ArrayList<>(List.of(calendarActivity)));  // Add activity to map
            } else {
                List<CalendarActivity> activities = calendarActivityMap.get(dayOfMonth);
                activities.add(calendarActivity);
                calendarActivityMap.put(dayOfMonth, activities);  // Update activity list
            }
        }

        return calendarActivityMap;  // Return map of calendar activities
    }

}





/*
package com.example.finfolio.Evenement;



import com.example.finfolio.Entite.Don;
import com.example.finfolio.Evenement.CalendarActivity;
import com.example.finfolio.Service.DonService;
import com.example.finfolio.Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;  // The currently focused date
    ZonedDateTime today;      // Today's date

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();




    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);  // Move dateFocus back by one month
        calendar.getChildren().clear();       // Clear the calendar UI
        drawCalendar();                        // Redraw the calendar UI
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        // Set the year and month text
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        // Calculate dimensions and spacing for calendar elements
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // Retrieve activities for the current month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        // Calculate the maximum number of days in the current month
        int monthMaxDate = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());

        // Calculate the offset for the first day of the month
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        // Loop through each cell in the calendar grid
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();  // Create a stack pane for each cell

                Rectangle rectangle = new Rectangle();  // Create a rectangle to represent each day
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);  // Add rectangle to stack pane

                // Calculate the date for the current cell
                int calculatedDate = (j + 1) + (7 * i);

                // Check if the calculated date falls within the current month
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));  // Create text to display the date
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);  // Add date text to stack pane

                        // Retrieve activities for the current date
                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            // Create and display calendar activities
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    // Highlight today's date
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);  // Add stack pane to the calendar
            }
        }
    }

    // Method to create and display calendar activities for a given date
    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();  // Create a vertical box to hold calendar activities
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");  // Display ellipsis for additional activities
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // Handle click event for displaying all activities
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getEventName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);  // Add activity text to the vertical box
            text.setOnMouseClicked(mouseEvent -> {
                // Handle click event for displaying activity details
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);  // Adjust vertical position of activity box
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);  // Set maximum width for activity box
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);  // Set maximum height for activity box
        calendarActivityBox.setStyle("-fx-background-color:rgba(8,120,201,0.68)");  // Set background color for activity box
        stackPane.getChildren().add(calendarActivityBox);  // Add activity box to the stack pane
    }

    // Method to retrieve calendar activities for the current month
    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        // Retrieve donations for the current month
        List<Don> donations = DonService.getInstance().getDonationsForMonth(LocalDate.from(dateFocus));

        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        // Add donations to the calendarActivityMap
        for (Don donation : donations) {
            LocalDate donationDate = donation.getEvennement().getDate();  // Get donation date
            int dayOfMonth = donationDate.getDayOfMonth();  // Get day of month for the donation date

            ZonedDateTime donationDateTime = donationDate.atStartOfDay(dateFocus.getZone());  // Convert to ZonedDateTime
            CalendarActivity calendarActivity = new CalendarActivity(donationDateTime, donation.getEvennement().getNom());  // Create calendar activity

            if (!calendarActivityMap.containsKey(dayOfMonth)) {
                calendarActivityMap.put(dayOfMonth, new ArrayList<>(List.of(calendarActivity)));  // Add activity to map
            } else {
                List<CalendarActivity> activities = calendarActivityMap.get(dayOfMonth);
                activities.add(calendarActivity);
                calendarActivityMap.put(dayOfMonth, activities);  // Update activity list
            }
        }

        return calendarActivityMap;  // Return map of calendar activities
    }


}
*/
