package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.team2.data.AccessFile;
import seng202.team2.data.ConvertAddress;
import seng202.team2.data.ImportData;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Controller that allows users to import csv files to the database or input a new record manually to a data list of their choice
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class AddDataPageController {

    private Stage primaryStage;

    @FXML private Label reqFieldsFlag;

    @FXML private ComboBox importDataCombo;

    @FXML private Button addDataBtn;

    @FXML private Button importBtn;

    @FXML private TextField tableName;

    @FXML private ComboBox dataTypeCombo;

    @FXML private Pane manualInsertRoutePane;

    @FXML private Pane manualInsertRetailerPane;

    @FXML private Pane manualInsertWifiPane;

    @FXML private TextField tripDuration;

    @FXML private TextField startName;

    @FXML private TextField endName;

    @FXML private ComboBox userType;

    @FXML private TextField shopName;

    @FXML private TextField shopType;

    @FXML private TextField addressRetailer;

    @FXML private TextField cityRetailer;

    @FXML private TextField provider;

    @FXML private TextField type;

    @FXML private TextField borough;

    @FXML private TextField addressWifi;

    private AccessFile accessFile = new AccessFile();

    private Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * Sets the text for labels on the addWifi pane
     */
    private void wifiInput() {
        manualInsertRetailerPane.setVisible(false);
        manualInsertRoutePane.setVisible(false);
        manualInsertWifiPane.setVisible(true);
    }


    /**
     * Sets the text for labels on the addRetailer pane
     */
    private void retailerInput() {
        manualInsertWifiPane.setVisible(false);
        manualInsertRoutePane.setVisible(false);
        manualInsertRetailerPane.setVisible(true);
    }


    /**
     * Sets the text for labels on the addRoute pane
     */
    private void routeInput() {
        manualInsertRetailerPane.setVisible(false);
        manualInsertWifiPane.setVisible(false);
        manualInsertRoutePane.setVisible(true);
    }


    /**
     * Creates and displays a dialog box stating invalid data
     */
    private void invalidInputsDialog() {
        Image image = new Image("https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
        ImageView im = new ImageView(image);
        im.setFitHeight(55);
        im.setFitWidth(55);
        alert2.setContentText("Please enter valid data in the provided fields.");
        alert2.setTitle("Invalid data");
        alert2.setHeaderText("Warning!");
        alert2.show();
        alert2.setGraphic(im);
    }

    /**
     * Checks that all manual input entries are appropriate for their field
     * Checks that all required fields are filled out
     * @return Boolean,
     */
    private String[] allValidInputsRoute() {

        if (tripDuration.getText() == null || startName.getText() == null || endName.getText() == null
                || tripDuration.getText().isEmpty() || startName.getText().isEmpty() || endName.getText().isEmpty()) {
            invalidInputsDialog();
        }
        else {
            String[] latlon = new String[4];
            try {
                latlon[0] = ConvertAddress.getLatLongPositions(startName.getText())[0];
                latlon[1] = ConvertAddress.getLatLongPositions(startName.getText())[1];
                latlon[2] = ConvertAddress.getLatLongPositions(endName.getText())[0];
                latlon[3] = ConvertAddress.getLatLongPositions(endName.getText())[1];

                Integer.parseInt(tripDuration.getText());
                return latlon;
            }catch (Exception e) {
                invalidInputsDialog();
                return null;
            }

        }
        return null;
    }


    /**
     * Checks that all manual input entries are appropriate for their field
     * Checks that all required fields are filled out
     * @return Boolean,
     */
    private String[] allValidInputsRetailers() {
        if (shopName.getText() == null || shopType.getText() == null || addressRetailer.getText() == null || cityRetailer.getText() == null || shopName.getText().isEmpty() || shopType.getText().isEmpty() || addressRetailer.getText().isEmpty() || cityRetailer.getText().isEmpty()) {
            invalidInputsDialog();
        } else {
            try {
                String[] latlon = ConvertAddress.getLatLongPositions(addressRetailer.getText() + ", " + cityRetailer.getText());
                return latlon;
            }catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Checks that all manual input entries are appropriate for their field
     * Checks that all required fields are filled out
     * @return Boolean,
     */
    private String[] allValidInputsWifi() {

        if (provider.getText() == null || type.getText() == null || borough.getText() == null || addressWifi.getText() == null || provider.getText().isEmpty() || type.getText().isEmpty() || borough.getText().isEmpty() || addressWifi.getText().isEmpty()) {
            invalidInputsDialog();
        } else {
            try {
                String[] latLong = ConvertAddress.getLatLongPositions(addressWifi.getText());
                return latLong;
            }catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Checks if the inputs are valid and then adds the new record if they are
     * @param tableName the name of the table the user is inserting data to
     */
    private void addWifi(String tableName) {
        String[] latLong = allValidInputsWifi();
        if (latLong != null) {
            accessFile.insertIntoWifi(provider.getText(), type.getText(), borough.getText(), addressWifi.getText(),  latLong, tableName);
            showSuccessAlert();
        } else {
            invalidInputsDialog();
        }
    }


    /**
     * Checks if the inputs are valid and then adds the new record if they are
     * @param tableName the name of the table the user is inserting data to
     */
    private void addRetailer(String tableName) {
        String[] latlon = allValidInputsRetailers();
        if (latlon != null) {
            accessFile.insertIntoRetailers(shopName.getText(), addressRetailer.getText(), shopType.getText(), cityRetailer.getText(), latlon, tableName);
            showSuccessAlert();
        } else {
            invalidInputsDialog();
        }
    }


    /**
     * Checks if the inputs are valid and then adds the new record if they are
     * @param tableName the name of the table the user is inserting data to
     */
    private void addRoute(String tableName) throws Exception{
        String[] latLon = allValidInputsRoute();
        if (latLon != null) {
            accessFile.insertIntoRoutes(tripDuration.getText(), startName.getText(), endName.getText(), userType.getValue().toString(), latLon, tableName);
            showSuccessAlert();
        } else {
            throw new Exception();
        }
    }

    /**
     * Checks if a string is an ObservableList
     * @param inComboBox The ObservableList that will be looked in
     * @param name The string that is being looked for
     * @return Returns true if the string is in the ObservableList, otherwise false
     */
    private boolean seen(ObservableList<String> inComboBox, String name) {
        for (int i=0; i<inComboBox.size(); i++) {
            if (name.equals(inComboBox.get(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds table names from data base to the importDataCombo ComboBox values if the they
     * are not already in importDataCombo and not equal to "TRAVELED"
     */
    public void getComboValues() {
        AccessFile af = new AccessFile();
        ArrayList<String>tableNames = af.getTableNames();
        tableNames.remove("TRAVELED");
        final ObservableList<String> allTables = FXCollections.observableArrayList(tableNames);
        importDataCombo.setItems(allTables);
    }


    /**
     * OnAction button method
     * Allows the user to add a data record manually
     */
    public void manualAdd() {
        String tableName = importDataCombo.getSelectionModel().getSelectedItem().toString();  //Gets the data type the user wants to create a record of
        int columnCount = accessFile.getColumnCountOfTableName(tableName);
        if (columnCount == 16) {
            try{
                addRoute(tableName);
                SearchPageController.searchPageController.filterRoute();
            } catch (Exception e) {
                invalidInputsDialog();
            }


        } else if (columnCount == 29) {
            addWifi(tableName);
            SearchPageController.searchPageController.filterWifi();
        } else if (columnCount == 19) {
            addRetailer(tableName);
            SearchPageController.searchPageController.filterRetailer();
        }
    }


    /**
     * Creates and displays a dialog box stating successfully added data
     */
    private void showSuccessAlert() {
        alert2.setTitle("Success");
        alert2.setHeaderText("Success!");
        alert2.setContentText("We have successfully added your data.");
        Image image = new Image("https://openclipart.org/image/2400px/svg_to_png/167549/Kliponious-green-tick.png");
        ImageView im = new ImageView(image);
        im.setFitHeight(55);
        im.setFitWidth(55);
        alert2.setGraphic(im);
        alert2.showAndWait();
    }


    /**
     * Gets the current value of the addDataCombo ComboBox. Then checks the number of columns the
     * corresponding table has. If it has 16 columns it calls routeInput() or if it has 29 columns
     * it calls wifiInput() or if it has 19 columns it calls retailerInput();
     */
    public void setBtns() {
        reqFieldsFlag.setVisible(false);
        addDataBtn.setDisable(false);
        importBtn.setDisable(false);
        AccessFile af = new AccessFile();
        String tableName =  importDataCombo.getSelectionModel().getSelectedItem().toString();
        if (!tableName.equals("null")) {
            addDataBtn.setDisable(false);
            importBtn.setDisable(false);
            int columnCount = af.getColumnCountOfTableName(tableName);
            if (columnCount == 16) {
                routeInput();
            } else if (columnCount == 29) {
                wifiInput();
            } else if (columnCount == 19) {
                retailerInput();
            }
        }
    }


    /**
     * Checks if the table name the user has tried to create already exists
     * @param name the name the user want to create a table with
     * @return Boolean, if the name exists or not
     */
    private boolean isUniqueTableName(String name) {
        ArrayList<String> names = accessFile.getTableNames();
        for (int i =0; i<names.size(); i++) {
            if (name.toLowerCase().equals(names.get(i).toLowerCase())) {
                return false;
            }
        }
        return true;
    }


    /**
     * Creates and displays a dialog box stating that the table name is already taken
     */
    private void showTableDialogDupName() {
        alert2.setHeaderText("Warning!");
        alert2.setContentText("Please choose another table name as this one already exists.");
        alert2.setTitle("Duplicate names");
        alert2.showAndWait();
    }


    /**
     * OnAction button method
     * Allows the user to create a new data list in the database
     */
    public void createNewTable() {
        String dataType = dataTypeCombo.getSelectionModel().getSelectedItem().toString();  //Gets the type of data the user wants to enter
        String name = tableName.getText();  //Gets the name of the new data list
        if (dataType.equals("Select") || name.equals("")) {
            invalidInputsDialog();
        } else if (!isUniqueTableName(name)) {  //Checks if the name already exists
            showTableDialogDupName();
        } else {
            if (dataType.toLowerCase().equals("wifi")) {
                accessFile.createNewTableWifi(name);
                importDataCombo.getItems().add(name);
            } else if (dataType.toLowerCase().equals("routes")) {
                accessFile.createNewTableRoute(name);
                importDataCombo.getItems().add(name);
            } else {
                accessFile.createNewTableRetailer(name);
                importDataCombo.getItems().add(name);
            }
            showSuccessAlert();
        }
    }


    /**
     * OnAction button method
     * Allows the user to select a file and import the data from that file
     * Displays alert warnings if the wrong file type was chosen
     */
    public void importFile() {
        reqFieldsFlag.setVisible(false);
        ImportData importData = new ImportData();
        FileChooser fileChooser = new FileChooser();  //User selects the file
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert alertAccepted = new Alert(Alert.AlertType.CONFIRMATION);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String filepath = file.getAbsolutePath();  //Gets the file path
            alertAccepted.setTitle("Confirmation");
            alertAccepted.setContentText("Do you wish to import the data file:\n " + file.getName());
            Optional<ButtonType> result = alertAccepted.showAndWait();
            if (accessFile.getDataType(importDataCombo.getSelectionModel().getSelectedItem().toString()).equals("wifi")) {
                if (result.get() == ButtonType.OK) {
                    try {
                        importData.wifiInsertUser(filepath, importDataCombo.getSelectionModel().getSelectedItem().toString()); //Data is imported into the wifi table
                        SearchPageController.searchPageController.filterWifi();
                        showSuccessAlert();
                    } catch (Exception e) {
                        alert.setTitle("Invalid file selection");
                        alert.setContentText("Please select a valid file to import into: " + importDataCombo.getSelectionModel().getSelectedItem().toString());
                        alert.showAndWait();
                    }
                }
            } else if (accessFile.getDataType(importDataCombo.getSelectionModel().getSelectedItem().toString()).equals("retailers")) {
                if (result.get() == ButtonType.OK) {
                    try {
                        importData.retailersInsertUser(filepath, importDataCombo.getSelectionModel().getSelectedItem().toString());  //Data is imported into the retailer table
                        SearchPageController.searchPageController.filterRetailer();
                        showSuccessAlert();
                    } catch (Exception e) {
                        alert.setTitle("Invalid file selection");
                        alert.setContentText("Please select a valid file to import into: " + importDataCombo.getSelectionModel().getSelectedItem().toString());
                        alert.showAndWait();
                    }
                }
            } else {
                if (result.get() == ButtonType.OK) {
                    try {
                        importData.routesInsertUser(filepath, importDataCombo.getSelectionModel().getSelectedItem().toString());  //Data is imported into the route table
                        SearchPageController.searchPageController.filterRoute();
                        showSuccessAlert();
                    } catch (Exception e) {
                        alert.setTitle("Invalid file selection");
                        alert.setContentText("Please select a valid file to import into: " + importDataCombo.getSelectionModel().getSelectedItem().toString());
                        alert.showAndWait();
                    }
                }
            }
        }
    }
}

