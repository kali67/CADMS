package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import seng202.team2.data.Calculations;
import java.time.LocalDate;

/**
 * Controller class that allows the user to control the carbon footprint they saved in a line chart graph
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class GraphPageController {

    @FXML private ComboBox graphTimeFrame;

    @FXML private LineChart lChart;


    /**
     * OnAction button method
     * Allows the user to change which time frame they want to see the graph in
     * and displays it
     */
    public void bClicked() {
        lChart.getData().removeAll(lChart.getData());
        String timeOption = graphTimeFrame.getSelectionModel().getSelectedItem().toString();
        int days = 0;
        if (timeOption.equals("Yearly")) {
            lChart.setTitle("Carbon Emissions Saved Over One Year");
            days = 365;
        } else if (timeOption.equals("Monthly")) {
            lChart.setTitle("Carbon Emissions Saved Over One Month");
            days = 31;
        } else if (timeOption.equals("Weekly")) {
            lChart.setTitle("Carbon Emissions Saved Over One week");
            days = 7;
        }
        XYChart.Series series = new XYChart.Series();
        Calculations cal = new Calculations();
        double[] carbonData = cal.calculateCarbon(days);
        LocalDate date = LocalDate.now();
        date = date.minusDays(days - 1);
        for (int i = 0; i < days; i++) {
            series.getData().add(new XYChart.Data(date.toString(), carbonData[i]));
            date = date.plusDays(1);
        }
        lChart.getData().add(series);
        lChart.setCreateSymbols(false);
        lChart.getStylesheets().add(getClass().getResource("/Stylesheets/GraphStylesheet.css").toExternalForm());
    }
}