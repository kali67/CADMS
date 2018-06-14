package seng202.team2.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team2.data.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Runs the whole app and is used as the controller for the base fxml file
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class GUIMain extends Application {

    private Stage primaryStage2;

    @FXML private Pane searchPane;

    @FXML private Pane mapPane;

    @FXML private Pane settingsPane;

    @FXML private Pane graphPane;

    @FXML private Pane addDataPane;

    @FXML private ToolBar mainToolBar;

    private ProgressBar pbar = new ProgressBar();

    private Parent root;


    /**
     * Loads in the base fxml file and all other fxml files as they are included within the base one
     * @param primaryStage the main stage for all GUI stuff to operate on
     * @throws Exception Throws if it cannot connect to google maps
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        createStage();
        new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                AccessFile accessFile = new AccessFile();
                ImportData importData = new ImportData();
                accessFile.createNewTableRetailer("Retailers");
                accessFile.createNewTableRoute("Routes");
                accessFile.createNewTableWifi("Wifi");
                accessFile.createNewTableTraveled();
                if (QueryData.getCountTable("Retailers") == 0) {
                    importData.retailersInsertDB("testData/Lower_Manhattan_Retailers.csv");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pbar.pb.setProgress(0.10);
                    }
                });
                if (QueryData.getCountTable("Wifi") == 0) {
                    importData.wifiInsertDB("testData/NYC_Free_Public_WiFi.csv");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pbar.pb.setProgress(0.20);
                    }
                });
                if (QueryData.getCountTable("Routes") == 0) {
                    importData.routesInsertDB("testData/RoutesTestDB.csv");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pbar.pb.setProgress(0.90); // we're as good as microsoft!!
                    }
                });
                if (QueryData.getCountTable("Traveled") == 0) {
                    importData.insertIntoTraveled();
                }
                updateTraveled();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            root = FXMLLoader.load(getClass().getResource("/base.fxml"));  //Base fxml includes all other fxml files
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pbar.pb.setProgress(1);
                        primaryStage.setScene(new Scene(root));
                        primaryStage.setTitle("Cycle Analysis and Data Management System (CADMS)");  //Our app name!
                        primaryStage.setResizable(false);
                        pbar.hideStage();
                        primaryStage.show();
                    }
                });
                return null;
            }
        }).start();
    }


    /**
     * Tries to create a new Stage pbar
     */
    private void createStage() {
        try {
            pbar.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Calls the Map controller class to resize the map to a smaller size
     * Shifts the mapPane to the left
     */
    private void resizeMapSmall() {
        MapController.mapController.resizeSmall();
        mapPane.setLayoutX(756);
    }


    /**
     * Calls the Map controller class to resize the map to the initial map size
     * Shifts the mapPane to the initial size
     */
    private void resizeMapLarge() {
        MapController.mapController.resizeLarge();
        mapPane.setLayoutX(80);
    }


    /**
     * Sets the visibilities for when the button to swap to the map page is clicked
     * Shifts the toolbar back to its original spot
     * Resizes the map to large
     */
    public void homeButtonClicked() {
        searchPane.setVisible(false);
        settingsPane.setVisible(false);
        graphPane.setVisible(false);
        addDataPane.setVisible(false);
        mainToolBar.setLayoutX(0);
        resizeMapLarge();
        mapPane.setVisible(true);
    }


    /**
     * Sets the visibilities for when the button to swap to the map page is clicked
     * Shifts the toolbar back to the left
     * Resizes the map to small
     */
    public void settingsButtonClicked() {
        searchPane.setVisible(false);
        graphPane.setVisible(false);
        addDataPane.setVisible(false);
        mainToolBar.setLayoutX(676);
        settingsPane.setVisible(true);
        resizeMapSmall();
        mapPane.setVisible(true);
    }


    /**
     * Sets the visibilities for when the button to swap to the map page is clicked
     * Shifts the toolbar back to the left
     * Resizes the map to small
     */
    public void searchButtonClicked() {
        settingsPane.setVisible(false);
        graphPane.setVisible(false);
        addDataPane.setVisible(false);
        mainToolBar.setLayoutX(676);
        searchPane.setVisible(true);
        resizeMapSmall();
        mapPane.setVisible(true);
    }


    /**
     * Sets the visibilities for when the button to swap to the graph page is clicked
     * Shifts the toolbar back to its original spot
     */
    public void graphButtonClicked() {
        settingsPane.setVisible(false);
        searchPane.setVisible(false);
        addDataPane.setVisible(false);
        mapPane.setVisible(false);
        mainToolBar.setLayoutX(0);
        graphPane.setVisible(true);
    }


    /**
     * Sets the visibilities for when the button to swap to the insert page is clicked
     * Shifts the toolbar back to its original spot
     */
    public void insertDataButtonClicked() {
        settingsPane.setVisible(false);
        searchPane.setVisible(false);
        graphPane.setVisible(false);
        mapPane.setVisible(false);
        mainToolBar.setLayoutX(0);
        addDataPane.setVisible(true);
    }


    /**
     * Creates an alert pop up to confirm if the user wants to quit the app
     */
    public void exitButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to quit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    /**
     * Calls the function updateTraveled() in AccessFile
     */
    private void updateTraveled() {
        AccessFile accessFile = new AccessFile();
        accessFile.updateTraveled();
    }


    /**
     * Launches the program
     */
    public static void main(String[] args) {
        launch(args);
    }

}


