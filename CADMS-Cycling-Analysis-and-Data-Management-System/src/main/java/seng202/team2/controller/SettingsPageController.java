package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import seng202.team2.data.AccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class that allows users to change which data table in the database they are filtering/searching data in
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class SettingsPageController implements Initializable {

    @FXML private ComboBox routeTableCB;

    @FXML private ComboBox retailTableCB;

    @FXML private ComboBox wifiTableCB;

    @FXML private ToggleGroup distanceToggle;

    @FXML private ToggleButton milesToggle;

    @FXML private ComboBox deleteCombo;

    @FXML private Label droppedText;

    public static SettingsPageController settingsController;

    /** The name of the table selected for bike routes */
    private String routeTable = "Routes";

    /** The name of the table selected for Wifi Locations */
    private String wifiTable = "WIFI";

    /** The name of the table selected for retailers */
    private String retailTable = "RETAILERS";


    /**
     * Overrides the initialize function so so that all the combo boxes
     * are updated on the construction of the class
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateComboBoxes();
        settingsController = this;
    }


    /**
     * @return {@link SettingsPageController#routeTable}
     */
    public String getRouteTable() {
        return routeTable;
    }


    /**
     * @return {@link SettingsPageController#wifiTable}
     */
    public String getWifiTable() {
        return wifiTable;
    }


    /**
     * @return {@link SettingsPageController#retailTable}
     */
    public String getRetailTable() {
        return retailTable;
    }


    /**
     * Updates the combo boxes for choosing tables to be the tables
     * currently in the database
     */
    public void updateComboBoxes() {
        AccessFile af = new AccessFile();
        ArrayList<String> tableNames =  af.getTableNames();
        ArrayList<String> wifiTables = new ArrayList<>();
        ArrayList<String> routeTables = new ArrayList<>();
        ArrayList<String> retailTables = new ArrayList<>();
        for (String tableName: tableNames) {
            String type = af.getDataType(tableName);
            tableName = tableName.toUpperCase();
            switch(type) {
                case "route":
                    routeTables.add(tableName);
                    break;
                case "wifi":
                    wifiTables.add(tableName);
                    break;
                case "retailers":
                    if (!tableName.equals("TRAVELED")) {
                        retailTables.add(tableName);
                    }
                    break;
            }
        }
        final ObservableList<String> wifiTablesForCB = FXCollections.observableArrayList(wifiTables);
        wifiTableCB.setItems(wifiTablesForCB);
        wifiTableCB.getSelectionModel().select(wifiTable.toUpperCase());
        final ObservableList<String> routeTablesForCB = FXCollections.observableArrayList(routeTables);
        routeTableCB.setItems(routeTablesForCB);
        routeTableCB.getSelectionModel().select(routeTable.toUpperCase());
        final ObservableList<String> retailTablesForCB = FXCollections.observableArrayList(retailTables);
        retailTableCB.setItems(retailTablesForCB);
        retailTableCB.getSelectionModel().select(retailTable.toUpperCase());
    }


    /**
     * Gets all the table names and adds them to the delete table combo box
     */
    public void updateComboDelete(){
        AccessFile af = new AccessFile();
        ArrayList<String>tableNames = af.getTableNames();
        tableNames.remove("TRAVELED");
        tableNames.remove("Wifi");
        tableNames.remove("Retailers");
        tableNames.remove("Routes");
        final ObservableList<String> allTables = FXCollections.observableArrayList(tableNames);
        deleteCombo.setItems(allTables);
    }


    /**
     * Called when the save button is selected, gets the values from the combo boxes for
     * route, wifi, and retailer tables, updates the variables for these
     */
    public void saveSelected() {
        if (!routeTable.toLowerCase().equals(routeTableCB.getSelectionModel().getSelectedItem().toString().toLowerCase())) {
            routeTable = routeTableCB.getSelectionModel().getSelectedItem().toString();
            SearchPageController.searchPageController.filterRoute();
        }
        if (!wifiTable.toLowerCase().equals(wifiTableCB.getSelectionModel().getSelectedItem().toString().toLowerCase())) {
            wifiTable = wifiTableCB.getSelectionModel().getSelectedItem().toString();
            SearchPageController.searchPageController.filterWifi();
        }
        if (!retailTable.toLowerCase().equals(retailTableCB.getSelectionModel().getSelectedItem().toString().toLowerCase())) {
            retailTable = retailTableCB.getSelectionModel().getSelectedItem().toString();
            SearchPageController.searchPageController.filterRetailer();
        }
    }


    /**
     * Returns the string value of the selected distance toggle on the settings page
     * @return Returns either "Miles" or "Km"
     */
    String getSelectedDistToggle() {
        if (distanceToggle.getSelectedToggle() == milesToggle) {
            return "Miles";
        } else {
            return "Km";
        }
    }


    /**
     * Drops the selected table from the database
     */
    public void deleteSelected(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        AccessFile accessFile = new AccessFile();
        if (accessFile.getTableNames().contains(deleteCombo.getSelectionModel().getSelectedItem().toString())) {
                accessFile.dropTable(deleteCombo.getSelectionModel().getSelectedItem().toString());
                alert.setContentText("Table dropped!");
                alert.setTitle("Confirmation");
                alert.showAndWait();
        } else {
            alert.setContentText("Please select a valid table.");
            alert.setTitle("Warning");
            alert.showAndWait();
        }
    }


    /**
     * Runs the filterRoute function  in the SearchPageController class
     * to convert the values for distance into the correct unit
     */
    public void toggleConvert() {
        SearchPageController.searchPageController.filterRoute();
    }

}
