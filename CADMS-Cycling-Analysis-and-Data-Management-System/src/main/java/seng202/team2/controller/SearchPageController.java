package seng202.team2.controller;

import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import seng202.team2.data.*;
import seng202.team2.model.Retailer;
import seng202.team2.model.Route;
import seng202.team2.model.WifiLocation;

import static seng202.team2.controller.MapController.mapController;

/**
 * Controller class that allows users to filter data records based on different data types
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class SearchPageController implements Initializable {

    @FXML private Pane filterPane;

    @FXML private ToggleGroup searchToggle;

    @FXML private Toggle routeToggle;

    @FXML private Toggle retailerToggle;

    @FXML private Toggle wifiToggle;

    @FXML private Pane wifiOptionsPane;

    @FXML private Pane retailerOptionsPane;

    @FXML private Pane routeOptionsPane;

    @FXML private TextField sStationInput;

    @FXML private TextField eStationInput;

    @FXML private ComboBox providerComboBox;

    @FXML private ComboBox wifiTypeComboBox;

    @FXML private ComboBox boroughComboBox;

    @FXML private TextField streetNameTextField;

    @FXML private TextField zipCodeTextField;

    @FXML private ComboBox primaryCB;

    @FXML private TableView<WifiLocation> tableWifi;

    @FXML private TableColumn boroughColumn;

    @FXML private TableColumn typeColumn;

    @FXML private TableColumn providerColumn;

    @FXML private TableColumn locationColumn;

    @FXML private TableView<Retailer> tableRetailer;

    @FXML private TableView<Route> tableRoute;

    @FXML private TableColumn tripDurationCol;

    @FXML private TableColumn startStationCol;

    @FXML private TableColumn endStationCol;

    @FXML private Pane wifiDetailPane;

    @FXML private  TableColumn companyNameColumn;

    @FXML private TableColumn addressColumn;

    @FXML private TableColumn companyTypeColumn;

    @FXML private Button confirmButton;

    @FXML private Button moreInfoButton;

    @FXML private Button deleteRecordButton;

    @FXML private TextField wifiNameTF;

    @FXML private ComboBox wifiTypeCB;

    @FXML private ComboBox wifiProviderCB;

    @FXML private TextField wifiLocTF;

    @FXML private ComboBox wifiLocTypeCB;

    @FXML private Label wifiBoroughLabel;

    @FXML private TextField wifiCityTF;

    @FXML private Label wifiPostCodeLabel;

    @FXML private Label wifiLatLabel;

    @FXML private Label wifiLonLabel;

    @FXML private Label wifiNTANLabel;

    @FXML private Label wifiSsidLabel;

    @FXML private Label wifiSourceIDLabel;

    @FXML private Label wifiObjectIDLabel;

    @FXML private Label wifiCTLabel;

    @FXML private TableColumn distanceCol;

    @FXML private Label wifiBorOctLabel;

    @FXML private Label wifiBINLabel;

    @FXML private Label wifiBBLLabel;

    @FXML private Label wifiDOITT_IDLabel;

    @FXML private Label wifiXCorLabel;

    @FXML private Label wifiYCorLabel;

    @FXML private Label wifiNTACodeLabel;

    @FXML private TextArea wifiRemarksTA;

    @FXML private Pane routeDetailPane;

    @FXML private TextField routeStartTimeTF;

    @FXML private TextField routeEndTimeTF;

    @FXML private TextField routeStartIdTF;

    @FXML private TextField routeEndIdTF;

    @FXML private Label routeDurLabel;

    @FXML private TextField routeBikeIdTF;

    @FXML private TextField routeStartNameTF;

    @FXML private TextField routeEndNameTF;

    @FXML private Label routeStartLatLabel;

    @FXML private Label routeEndLatLabel;

    @FXML private Label routeStartLonLabel;

    @FXML private Label routeEndLonLabel;

    @FXML private Label routeUserIdLabel;

    @FXML private ComboBox routeUserGenderCB;

    @FXML private ComboBox routeUserTypeCB;

    @FXML private TextField routeBYearTF;

    @FXML private Pane retailerDetailPane;

    @FXML private TextField retailNameTF;

    @FXML private TextField retailCityTF;

    @FXML private TextField retailAdr1TF;

    @FXML private TextField retailAdr2TF;

    @FXML private TextField retailBorTF;

    @FXML private Label retailZipLabel;

    @FXML private Label retailIdLabel;

    @FXML private Label retailCTLabel;

    @FXML private Label retailLatLabel;

    @FXML private Label retailLonLabel;

    @FXML private Label retailComBoardLabel;

    @FXML private Label retailComDisLabel;

    @FXML private Label retailStateLabel;

    @FXML private Label retailBblLabel;

    @FXML private Label retailBinLabel;

    @FXML private Label retailNtaLabel;

    @FXML private ComboBox retailCat1CB;

    @FXML private Label retailCat2Label;

    @FXML private Button moreRecords;

    @FXML private Button findWifiBtn;

    @FXML private Button findRetailersRouteBtn;

    /** The wifi location record currently selected in the wifi location table */
    private WifiLocation wifiSelected;

    /** The route record currently selected in the route table */
    private Route routeSelected;

    /** The retailer record currently selected in the retailer table */
    private Retailer retailerSelected;

    /** Index of the where in the results list the route table displays up to */
    private int endIndexRoute;

    /** Index of the where in the results list the retailer table displays up to */
    private int endIndexRetailer;

    /** Index of the where in the results list the wifi location table displays up to */
    private int endIndexWifi;

    /** Table name of the table where an entry has been updated */
    private String update;

    /** Search page controller */
    static SearchPageController searchPageController;


    /**
     * Constructor for the searchPageController
     */
    public SearchPageController() {
        endIndexRoute = 0;
        endIndexRetailer = 0;
        endIndexWifi = 0;
    }

    /**
     * Initializes the search page controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // These populate the tables with default input (generally select all)
        // so that tables are not empty when first navigating to the search page
        filterRoute();
        routeOptionClicked();
        filterWifi();
        filterRetailer();
        update = "";
        searchPageController = this;
    }


    /**
     * Sets wifi filter options pane to visible to display
     * options for filtering wifi locations
     * Sets retailer and route option panes to invisible
     */
    public void wifiOptionClicked() {
        routeOptionsPane.setVisible(false);
        retailerOptionsPane.setVisible(false);
        wifiOptionsPane.setVisible(true);
    }


    /**
     * Sets retailer filter options pane to visible to display
     * options for filtering retailers
     * Sets wifi and route option panes to invisible
     */
    public void retailerOptionClicked() {
        routeOptionsPane.setVisible(false);
        wifiOptionsPane.setVisible(false);
        retailerOptionsPane.setVisible(true);
    }


    /**
     * Sets route filter options pane to visible to display
     * options for filtering routes
     * Sets retailer and wifi option panes to invisible
     */
    public void routeOptionClicked() {
        wifiOptionsPane.setVisible(false);
        retailerOptionsPane.setVisible(false);
        routeOptionsPane.setVisible(true);
    }


    /**
     * Finds which of the filter options panes is currently
     * displayed, gets the relevant data from input and
     * calls necessary filtering function
     */
    public void confirmButtonClicked() {
        if (wifiOptionsPane.isVisible()) {
            filterWifi();
        } else if (retailerOptionsPane.isVisible()) {
            filterRetailer();
        } else {
            filterRoute();
        }
    }


    /**
     * Finds which search criteria have been input, and searches for
     * relevant records by calling searchRouteXByY in the FilterRoute class
     * These records are then displayed in the tableRoute
     */
    void filterRoute() {
        String startStation = "'" + sStationInput.getText() + "'";
        String endStation = "'" + eStationInput.getText() + "'";
        String tableName = SettingsPageController.settingsController.getRouteTable();
        List<Route> searchResult = FilterRoute.searchRouteXByY(startStation, endStation, tableName);
        if (endIndexRoute + 1000 < searchResult.size()) {
            endIndexRoute += 1000;
        } else {
            endIndexRoute = searchResult.size();
        }
        final ObservableList<Route> data = FXCollections.observableArrayList(searchResult.subList(0, endIndexRoute));
        tripDurationCol.setCellValueFactory(new PropertyValueFactory<Route, String>("tripDuration"));
        startStationCol.setCellValueFactory(new PropertyValueFactory<Route, String>("startStationName"));
        endStationCol.setCellValueFactory(new PropertyValueFactory<Route, String>("endStationName"));
        if (SettingsPageController.settingsController.getSelectedDistToggle().equals("Km")) {
            distanceCol.setText("Distance (Km)");
        } else if (SettingsPageController.settingsController.getSelectedDistToggle().equals("Miles")) {
            distanceCol.setText("Distance (Miles)");
            for (Route route:data) {
                route.setDistance(RoundTo2Decimals(route.getDistance()*0.6213712));
            }
        }
        distanceCol.setCellValueFactory(new PropertyValueFactory<Route, Double>("distance"));
        tableRoute.setItems(data);
        tableRoute.getSelectionModel().selectFirst();
        tableRoute.refresh();
    }


    /**
     * Rounds a given value to two decimal places
     * @param val value to be rounded
     * @return value rounded to two decimals
     */
    private double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }


    /**
     * Finds which search criteria have been input, and searches for
     * relevant records by calling searchRetailerXByY in the FilterRetailer class
     * These records are then displayed in the tableRetailer
     */
    void filterRetailer() {
        String tableName = SettingsPageController.settingsController.getRetailTable();
        String streetName = "'" + streetNameTextField.getText() + "'";
        String zipCode = "'" + zipCodeTextField.getText() + "'";
        String primary = "'" + primaryCB.getSelectionModel().getSelectedItem().toString() + "'";

        ArrayList<Retailer> searchResult = FilterRetailer.searchRetailerXByY(streetName, zipCode, primary, tableName);
        if (endIndexRetailer + 1000 < searchResult.size()) {
            endIndexRetailer += 1000;
        } else {
            endIndexRetailer = searchResult.size();
        }
        final ObservableList<Retailer> data = FXCollections.observableArrayList(searchResult.subList(0, endIndexRetailer));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<Retailer, String>("companyNameFfil"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Retailer, String>("addressLine1"));
        companyTypeColumn.setCellValueFactory(new PropertyValueFactory<Retailer, String>("primary"));
        tableRetailer.setItems(data);
        tableRetailer.getSelectionModel().selectFirst();
        tableRetailer.setVisible(true);
        tableRetailer.refresh();
    }


    /**
     * Finds which search criteria have been input, and searches for
     * relevant records by calling searchWifiXByY in the FilterWifi class
     * These records are then displayed in the tableWifi
     */
    void filterWifi() {
        String provider = convertProviderName(providerComboBox.getSelectionModel().getSelectedItem().toString());
        String wifiType = "\'" + wifiTypeComboBox.getSelectionModel().getSelectedItem().toString() + "\'";
        String borough = convertBoroughName(boroughComboBox.getSelectionModel().getSelectedItem().toString());
        String tableName = SettingsPageController.settingsController.getWifiTable();

        List<WifiLocation> searchResult = FilterWifi.searchWifiXByY(provider, wifiType, borough, tableName);
        if (endIndexWifi + 1000 < searchResult.size()) {
            endIndexWifi += 1000;
        } else {
            endIndexWifi = searchResult.size();
        }
        final ObservableList<WifiLocation> data = FXCollections.observableArrayList(searchResult.subList(0, endIndexWifi));
        boroughColumn.setCellValueFactory(new PropertyValueFactory<WifiLocation, String>("borough"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<WifiLocation, String>("type"));
        providerColumn.setCellValueFactory(new PropertyValueFactory<WifiLocation, String>("provider"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<WifiLocation, String>("location"));

        tableWifi.setItems(data);
        tableWifi.getSelectionModel().selectFirst();
        tableWifi.setVisible(true);
        tableWifi.refresh();
    }


    /**
     * Converts the given borough name string into the abbreviation
     * used in the database
     * @param boroughName String name of the borough selected
     * @return Abbreviation of the borough name
     */
    private String convertBoroughName(String boroughName) {
        String boroughInitial = "'Any'";
        switch (boroughName) {
            case "Any":
                boroughInitial = "'Any'";
                break;
            case "Manhattan":
                boroughInitial = "'MN'";
                break;
            case "The Bronx":
                boroughInitial = "'BX'";
                break;
            case "Brooklyn":
                boroughInitial = "'BK'";
                break;
            case "Staten Island":
                boroughInitial = "'SI'";
                break;
            case "Queens":
                boroughInitial = "'QU'";
                break;
        }
        return boroughInitial;
    }


    /**
     * Adds up to 1000 more records to the table that is currently being viewed
     */
    public void addMoreData() {
        if(searchToggle.getSelectedToggle() == routeToggle) {
            filterRoute();
        } else if (searchToggle.getSelectedToggle() == retailerToggle) {
            filterRetailer();
        } else if (searchToggle.getSelectedToggle() == wifiToggle) {
            filterWifi();
        } else {
            routeToggle.setSelected(true);
            routeOptionClicked();
        }
    }


    /**
     * Converts provider name to how it is in the database
     * @param provider String common name of the wifi provider
     * @return String Provider name used in the database
     */
    private String convertProviderName(String provider) {
        String convProvider = "'" + provider + "'";
        switch(provider) {
            case "Spectrum":
                convProvider = "'SPECTRUM'";
                break;
            case "Altice USA":
                convProvider = "'ALTICEUSA'";
                break;
        }
        return convProvider;
    }


    /**
     * Converts the int indicating the cyclists gender to
     * it's string equivalent
     * @param genderInt int indicating the gender of the cyclist
     * @return the string gender of the cylist
     */
    private String convertGender(int genderInt) {
        String gender;
        switch(genderInt) {
            case 0:
                gender = "Unknown";
                break;
            case 1:
                gender = "Male";
                break;
            case 2:
                gender = "Female";
                break;
            default:
                gender = "Unknown";
        }
        return gender;
    }


    /**
     * Converts string gender into int representation used in the database
     * @param genderStr gender of the cyclist
     * @return String of gender integer representation
     */
    private String convertGenderToNum(String genderStr) {
        String genderInt;
        switch (genderStr) {
            case "Male":
                genderInt = "1";
                break;
            case "Female":
                genderInt = "2";
                break;
            case "Unknown":
                genderInt = "0";
                break;
            default:
                genderInt = "0";
                break;
        }
        return genderInt;
    }


    /**
     * Called when the More Info button is clicked in order to controller more details
     * about a selected record.
     * The currently visible pane is detected and the relevant method is called to
     * display more details about the selected record
     */
    public void moreInfoClicked() {
        MapController.mapController.clearMarkers();
        if(searchToggle.getSelectedToggle() == routeToggle) {
            moreInfoRoute();
        } else if (searchToggle.getSelectedToggle() == retailerToggle) {
            moreInfoRetailer();
        } else if (searchToggle.getSelectedToggle() == wifiToggle) {
            moreInfoWifi();
        } else {
            routeToggle.setSelected(true);
            routeOptionClicked();
        }
    }


    /**
     * Sets the visibility of the toolbar, confirm button, and more
     * info button to bool
     * @param bool whether to set the tool bar to visible or not visible
     */
    private void setToolBarVisibility(boolean bool) {
        filterPane.setVisible(bool);
        confirmButton.setVisible(bool);
        moreInfoButton.setVisible(bool);
        deleteRecordButton.setVisible(bool);
        moreRecords.setVisible(bool);
    }


    /**
     * Inputs the given values to the relevant TextFields and Labels for
     * viewing more detail about a record (WifiLocation, Route, or Retailer)
     * @param labels ArrayList of Labels to be changed to relevant values
     * @param textFields ArrayList of TextFields to be changed to relevant fields
     * @param values ArrayList of String values to changes the given Labels and
     *               TextFields to
     */
    private void fillInDetail(ArrayList<Label> labels, ArrayList<TextField> textFields, ArrayList<String> values) {
        int i = 0;
        for(TextField tfield: textFields) {
            tfield.setText(values.get(i));
            i++;
        }
        for(Label label: labels) {
            label.setText(values.get(i));
            i++;
        }
    }


    /**
     * Called when the More Info button is pressed for a Wifi Location record
     * Creates lists of relevant Labels and TextFields to be updated,
     * and a list of values with which to update them
     * fillInDetail() is called to insert these values
     */
    private void moreInfoWifi() {

        wifiSelected = tableWifi.getSelectionModel().getSelectedItem();
        setToolBarVisibility(false);
        wifiOptionsPane.setVisible(false);
        wifiDetailPane.setVisible(true);

        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(wifiNameTF);
        textFields.add(wifiLocTF);
        textFields.add(wifiCityTF);

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(wifiBoroughLabel);
        labels.add(wifiPostCodeLabel);
        labels.add(wifiLatLabel);
        labels.add(wifiLonLabel);
        labels.add(wifiNTANLabel);
        labels.add(wifiSsidLabel);
        labels.add(wifiSourceIDLabel);
        labels.add(wifiObjectIDLabel);
        labels.add(wifiCTLabel);
        labels.add(wifiBorOctLabel);
        labels.add(wifiBINLabel);
        labels.add(wifiBBLLabel);
        labels.add(wifiDOITT_IDLabel);
        labels.add(wifiXCorLabel);
        labels.add(wifiYCorLabel);
        labels.add(wifiNTACodeLabel);

        // List of values to be added to the fields on the more info page
        ArrayList<String> values = new ArrayList<>();
        values.add(wifiSelected.getName());
        values.add(wifiSelected.getLocation());
        values.add(wifiSelected.getCity());
        values.add(wifiSelected.getBorough());
        values.add(wifiSelected.getPostCode());
        values.add(wifiSelected.getLat());
        values.add(wifiSelected.getLon());
        values.add(wifiSelected.getNtaName());
        values.add(wifiSelected.getSsid());
        values.add(wifiSelected.getSourceId());
        values.add(wifiSelected.getObjectId());
        values.add(wifiSelected.getCt2010());
        values.add(wifiSelected.getBoroCt2010());
        values.add(wifiSelected.getBin());
        values.add(wifiSelected.getBbl());
        values.add(wifiSelected.getDoittId());
        values.add(wifiSelected.getX_cor());
        values.add(wifiSelected.getY_cor());
        values.add(wifiSelected.getNtaCode());

        wifiRemarksTA.setText(wifiSelected.getRemarks());
        wifiTypeCB.getSelectionModel().select(wifiSelected.getType());
        wifiLocTypeCB.getSelectionModel().select(wifiSelected.getLocation_t());

        String wifiTableName = SettingsPageController.settingsController.getWifiTable();
        ArrayList<String> providers = QueryData.getUniqueValues(wifiTableName);
        final ObservableList<String> providersData = FXCollections.observableArrayList(providers);
        wifiProviderCB.getItems().addAll(providersData);
        wifiProviderCB.getSelectionModel().select(wifiSelected.getProvider());
        fillInDetail(labels, textFields, values);

        // Adding a marker to the map at the wifi location
        LatLong latLong = new LatLong(Double.parseDouble(wifiSelected.getLat()), Double.parseDouble(wifiSelected.getLon()));
        String info = "Wifi:<br>" + wifiSelected.getLocation() + "<br>" + wifiSelected.getProvider();
        mapController.setMarker(latLong, "moreInfo", info);
        mapController.setConfirmRouteVisible();
    }


    /**
     * Called when the More Info button is pressed for a Retailer record
     * Creates lists of relevant Labels and TextFields to be updated,
     * and a list of values with which to update them
     * fillInDetail() is called to insert these values
     */
    private void moreInfoRetailer() {

        setToolBarVisibility(false);
        retailerOptionsPane.setVisible(false);
        retailerDetailPane.setVisible(true);
        retailerSelected = tableRetailer.getSelectionModel().getSelectedItem();

        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(retailNameTF);
        textFields.add(retailAdr1TF);
        textFields.add(retailAdr2TF);
        textFields.add(retailBorTF);
        textFields.add(retailCityTF);

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(retailZipLabel);
        labels.add(retailIdLabel);
        labels.add(retailCTLabel);
        labels.add(retailCat2Label);
        labels.add(retailLatLabel);
        labels.add(retailLonLabel);
        labels.add(retailComBoardLabel);
        labels.add(retailComDisLabel);
        labels.add(retailStateLabel);
        labels.add(retailBblLabel);
        labels.add(retailBinLabel);
        labels.add(retailNtaLabel);

        // List of values to be added to the fields on the more info page
        ArrayList<String> values = new ArrayList<>();
        values.add(retailerSelected.getCompanyName());
        values.add(retailerSelected.getAddressLine1());
        values.add(retailerSelected.getAddressLine2());
        values.add(retailerSelected.getBorough());
        values.add(retailerSelected.getCity());
        values.add(retailerSelected.getZip());
        values.add(retailerSelected.getIdNumber());
        values.add(retailerSelected.getCensusTract());
        values.add(retailerSelected.getSecondary());

        // Adds a marker on the map for the selected retailer if it has latitude and longitude values
        if (!retailerSelected.getLatitude().isEmpty() && !retailerSelected.getLongitude().isEmpty()) {
            values.add(retailerSelected.getLatitude());
            values.add(retailerSelected.getLongitude());
            LatLong latLong = new LatLong(Double.parseDouble(retailerSelected.getLatitude()), Double.parseDouble(retailerSelected.getLongitude()));
            String info = "Retailer:<br>" + retailerSelected.getCompanyName() + "<br>" + retailerSelected.getPrimary() + "<br>" + retailerSelected.getAddressLine1();
            MapController.mapController.setMarker(latLong, "moreInfo",info);
            MapController.mapController.setConfirmRouteVisible();
        } else {
            values.add("");
            values.add("");
        }

        values.add(retailerSelected.getCommunityBoard());
        values.add(retailerSelected.getCouncilDistrict());
        values.add(retailerSelected.getState());
        values.add(retailerSelected.getBbl());
        values.add(retailerSelected.getBin());
        values.add(retailerSelected.getNta());
        retailCat1CB.getSelectionModel().select(retailerSelected.getPrimary());
        fillInDetail(labels, textFields, values);
    }


    /**
     * Called when the More Info button is pressed for a Route record
     * Creates lists of relevant Labels and TextFields to be updated,
     * and a list of values with which to update them
     * fillInDetail() is called to insert these values
     */
    private void moreInfoRoute() {

        findRetailersRouteBtn.setVisible(true);
        findWifiBtn.setVisible(true);
        setToolBarVisibility(false);
        routeOptionsPane.setVisible(false);
        routeDetailPane.setVisible(true);
        routeSelected = tableRoute.getSelectionModel().getSelectedItem();

        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(routeStartTimeTF);
        textFields.add(routeEndTimeTF);
        textFields.add(routeBikeIdTF);
        textFields.add(routeStartNameTF);
        textFields.add(routeEndNameTF);
        textFields.add(routeStartIdTF);
        textFields.add(routeEndIdTF);
        //textFields.add(routeUserIdTF);
        textFields.add(routeBYearTF);

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(routeDurLabel);
        labels.add(routeStartLatLabel);
        labels.add(routeEndLatLabel);
        labels.add(routeStartLonLabel);
        labels.add(routeEndLonLabel);
        labels.add(routeUserIdLabel);

        // List of values to be added to the fields on the more info page
        ArrayList<String> values = new ArrayList<>();
        values.add(routeSelected.getStartTime());
        values.add(routeSelected.getStopTime());
        values.add(routeSelected.getBikeId());
        values.add(routeSelected.getStartStationName());
        values.add(routeSelected.getEndStationName());
        values.add(routeSelected.getStartStationId());
        values.add(routeSelected.getEndStationId());

        String birthYear;
        if (routeSelected.getBirthYear().equals("N")) {
            birthYear = "Not specified";
        } else {
            birthYear = routeSelected.getBirthYear();
        }
        values.add(birthYear);
        values.add(String.valueOf(routeSelected.getTripDuration()));
        values.add(routeSelected.getStartStationLat());
        values.add(routeSelected.getEndStationLat());
        values.add(routeSelected.getStartStationLon());
        values.add(routeSelected.getEndStationLon());
        values.add(routeSelected.getIdNumber());

        String userGender = "";
        if (routeSelected.getGender() != null) {
            userGender = convertGender(Integer.parseInt(routeSelected.getGender()));
        }
        routeUserGenderCB.getSelectionModel().select(userGender);
        routeUserTypeCB.getSelectionModel().select(routeSelected.getUserType());



        fillInDetail(labels, textFields, values);

        // Adds a marker to the map if the route has valid start and end points
        if (routeSelected.getStartStationLat() != null && routeSelected.getStartStationLon() != null
                && routeSelected.getEndStationLat() != null && routeSelected.getEndStationLon() != null
                && !routeSelected.getStartStationLat().isEmpty() && !routeSelected.getStartStationLon().isEmpty()
                && !routeSelected.getEndStationLat().isEmpty() && !routeSelected.getEndStationLon().isEmpty()) {
            LatLong startLatLong = new LatLong(Double.parseDouble(routeSelected.getStartStationLat()), Double.parseDouble(routeSelected.getStartStationLon()));
            LatLong endLatLong = new LatLong(Double.parseDouble(routeSelected.getEndStationLat()), Double.parseDouble(routeSelected.getEndStationLon()));
            MapController.mapController.setMarker(startLatLong, "moreInfo","Start Station:<br>" + routeSelected.getStartStationName());
            MapController.mapController.setMarker(endLatLong, "moreInfo","End Station:<br>" + routeSelected.getEndStationName());
            MapController.mapController.showRoute(startLatLong, endLatLong);
        }
    }


    /**
     * Finds the closest to a retailers to route and places markers on the map for each one
     */
    public void findRetailers() {
        try {
            ArrayList<Retailer> results = FilterRetailer.findNearestRetailers(mapController.getDirectionsResults(),
                    SettingsPageController.settingsController.getRetailTable());
            for (int i=0; i<results.size() - 1; i++) {
                String info = "Retailer:<br>" + results.get(i).getCompanyName() + "<br>" + results.get(i).getPrimary()
                        + "<br>" + results.get(i).getAddressLine1();
                if (!results.get(i).equals(results.get(results.size() - 1))) {
                    mapController.setMarker(new LatLong(Double.parseDouble(results.get(i).getLatitude()),
                            Double.parseDouble(results.get(i).getLongitude())), "moreInfo", info);
                } else {
                    mapController.setMarkerClosestEntity(new LatLong(Double.parseDouble(results.get(results.size() - 1).getLatitude()),
                            Double.parseDouble(results.get(results.size() - 1).getLongitude())), info);
                }
            }
        } catch (NullPointerException e) {
            // Alerting the user when no retailers can be found nearby to some point
            Alert noClose = new Alert(Alert.AlertType.INFORMATION);
            noClose.setContentText("Sorry, there are no retailers nearby!");
            noClose.setTitle("No Nearby Retailers!");
            noClose.showAndWait();
        }
    }


    /**
     * Finds the closest to a wifi locations to route and places markers on the map for each one
     */
    public void findWifiLocations() {
        try {
            ArrayList<WifiLocation> results = FilterWifi.findNearestWifi(mapController.getDirectionsResults(),
                    SettingsPageController.settingsController.getWifiTable());
            for (int i=0; i<results.size() - 1; i++) {
                String info ="Wifi:<br>" + results.get(i).getLocation() + "<br>" + results.get(i).getProvider();
                if (!results.get(i).equals(results.get(results.size() - 1))) {
                    mapController.setMarker(new LatLong(Double.parseDouble(results.get(i).getLat()),
                            Double.parseDouble(results.get(i).getLon())), "moreInfo", info);
                } else {
                    mapController.setMarkerClosestEntity(new LatLong(Double.parseDouble(results.get(results.size() - 1).getLat()),
                            Double.parseDouble(results.get(results.size() - 1).getLon())), info);
                }
            }
        } catch (NullPointerException e) {
            Alert noClose = new Alert(Alert.AlertType.INFORMATION);
            noClose.setContentText("Sorry, there are no Wifi Locations nearby!");
            noClose.setTitle("No Nearby Wifi!");
            noClose.showAndWait();
        }
    }


    /**
     * Called when navigating back to the search page from a
     * wifi, retailer, or route detail page, setting the detail pane
     * to not visible and the filter options pane to visible
     */
    public void backButtonPressed() {
        if (update.equals("Wifi")) {
            filterWifi();
        } else if (update.equals("Retailer")) {
            filterRetailer();
        } else if (update.equals("Route")) {
            filterRoute();
        }
        update = "";
        setToolBarVisibility(true);
        moreRecords.setVisible(true);

        if (wifiDetailPane.isVisible()) {
            wifiOptionsPane.setVisible(true);
            wifiDetailPane.setVisible(false);
        } else if (routeDetailPane.isVisible()) {
            routeOptionsPane.setVisible(true);
            routeDetailPane.setVisible(false);
            findWifiBtn.setVisible(false);
            findRetailersRouteBtn.setVisible(false);
        } else if (retailerDetailPane.isVisible()) {
            retailerOptionsPane.setVisible(true);
            retailerDetailPane.setVisible(false);
        }
    }


    /**
     * Finds which record is currently selected and removes it
     * from the database (Using AccessFile.delete())
     */
    public void deleteRecord() {
        int idNumber = 0;
        String table = "";

        if (wifiOptionsPane.isVisible()) {
            WifiLocation wifiSelected = tableWifi.getSelectionModel().getSelectedItem();
            idNumber = Integer.parseInt(wifiSelected.getDoittId());
            table = SettingsPageController.settingsController.getWifiTable();
        } else if (routeOptionsPane.isVisible()) {
            Route routeSelected = tableRoute.getSelectionModel().getSelectedItem();
            idNumber = Integer.parseInt(routeSelected.getIdNumber());
            table = SettingsPageController.settingsController.getRouteTable();
        } else if (retailerOptionsPane.isVisible()) {
            Retailer retailerSelected = tableRetailer.getSelectionModel().getSelectedItem();
            idNumber = Integer.parseInt(retailerSelected.getIdNumber());
            table = SettingsPageController.settingsController.getRetailTable();
        }
        AccessFile af = new AccessFile();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to delete this record?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            af.delete(table, idNumber);
            confirmButtonClicked();
        }
    }


    /**
     * Finds the closest wifi location to the retailer that the user
     * is currently viewing, and display the wifi location on the map
     * as a marker
     */
    public void retailFindClosestWifi() {
        String retailerAddress = retailerSelected.getAddressLine1() + " " + retailerSelected.getCity();
        String wifiTableName = SettingsPageController.settingsController.getWifiTable();
        WifiLocation closestWifi = FilterWifi.findClosestWifiToRetail(retailerAddress, wifiTableName);
        LatLong latLong = new LatLong(Double.parseDouble(closestWifi.getLat()), Double.parseDouble(closestWifi.getLon()));
        mapController.setMarkerClosestEntity(latLong, "Wifi:<br>" + closestWifi.getLocation() + "<br>" + closestWifi.getProvider());
    }


    /**
     * Retrieves changes to the wifi location record
     * from input, and updates the record in the database and the
     * WifiLocation object that represents the wifi location being edited
     * If address has changed it attempts to find the latitude and longitude
     * for these addresses
     */
    public void editWifiRecord() {
        String wifiTableName = SettingsPageController.settingsController.getWifiTable();
        String wifiName = wifiNameTF.getText();
        String wifiLocation = wifiLocTF.getText();
        String wifiType = wifiTypeCB.getSelectionModel().getSelectedItem().toString();
        String wifiProvider = wifiProviderCB.getSelectionModel().getSelectedItem().toString();
        String wifiRemarks = wifiRemarksTA.getText();
        String wifiLocType = wifiLocTypeCB.getSelectionModel().getSelectedItem().toString();
        String wifiCity = wifiCityTF.getText();

        // Updating WifiLocation objects and records within the Wifi table
        AccessFile af = new AccessFile();
        af.update(wifiTableName, wifiSelected.getDoittId(), "'NAME'", wifiName);
        wifiSelected.setName(wifiName);
        af.update(wifiTableName, wifiSelected.getDoittId(), "'TYPE'", wifiType);
        wifiSelected.setType(wifiType);
        af.update(wifiTableName, wifiSelected.getDoittId(), "'PROVIDER'", wifiProvider);
        wifiSelected.setProvider(wifiProvider);
        af.update(wifiTableName, wifiSelected.getDoittId(), "'REMARKS'", wifiRemarks);
        wifiSelected.setRemarks(wifiRemarks);
        af.update(wifiTableName, wifiSelected.getDoittId(), "'LOCATION_T'", wifiLocType);
        wifiSelected.setLocation_t(wifiLocType);
        af.update(wifiTableName, wifiSelected.getDoittId(), "'CITY'", wifiCity);
        wifiSelected.setCity(wifiCity);

        if (!wifiLocation.equals(wifiSelected.getLocation())) {

            wifiSelected.setLocation_t(wifiLocType);
            // Finding latitude and longitude of the new address to update the record
            String[] latLongPositions = ConvertAddress.getLatLongPositions(wifiLocation + " " + wifiCity);

            if (latLongPositions != null) {
                String lat = latLongPositions[0];
                String lon = latLongPositions[1];

                af.update(wifiTableName, wifiSelected.getDoittId(), "'LOCATION'", wifiLocation);

                wifiSelected.setLat(lat);
                af.update(wifiTableName, wifiSelected.getDoittId(), "'LAT'", lat);
                wifiSelected.setLon(lon);
                af.update(wifiTableName, wifiSelected.getDoittId(), "'LON'", lon);

                // Setting x and c coordinates to empty string as these are no longer correct and
                // cannot be calculated
                wifiSelected.setX_cor("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'X'", "");
                wifiSelected.setY_cor("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'Y'", "");

            } else {
                // Alerts the user that the address input cannot be found
                Alert wrongAddress = new Alert(Alert.AlertType.INFORMATION);
                wrongAddress.setContentText("Address and city are invalid");
                wrongAddress.setHeaderText("Error");
                wrongAddress.setTitle("Address not found");
                wrongAddress.showAndWait();

                // Setting lat, lon, x_cor and y_cor values to empty string as these cannot be found
                wifiSelected.setLat("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'LAT'", "");
                wifiSelected.setLon("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'LON'", "");
                wifiSelected.setX_cor("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'X'", "");
                wifiSelected.setY_cor("");
                af.update(wifiTableName, wifiSelected.getDoittId(), "'Y'", "");
            }
        }
        moreInfoWifi(); // Refreshes the page so that new info is displayed (ie the lat and lon if they change)
        update = "Wifi";
    }


    /**
     * Retrieves changes to the retailer record
     * from input, and updates the record in the database and the Retailer object
     * that represents the Retailer being edited
     * If address has changed it attempts to find the latitude and longitude
     * for these addresses
     */
    public void editRetailRecord() {
        AccessFile af = new AccessFile();
        String retailTableName = SettingsPageController.settingsController.getRetailTable();
        String retailName = retailNameTF.getText();
        String retailCategory = retailCat1CB.getSelectionModel().getSelectedItem().toString();
        String retailAdr1 = retailAdr1TF.getText();
        String retailAdr2 = retailAdr2TF.getText();
        String retailBor = retailBorTF.getText();
        String retailCity = retailCityTF.getText();

        af.update(retailTableName, retailerSelected.getIdNumber(), "'CnBio_Org_Name'", retailName);
        retailerSelected.setCompanyName(retailName);
        af.update(retailTableName, retailerSelected.getIdNumber(), "'Primary'", retailCategory);
        retailerSelected.setPrimary(retailCategory);
        af.update(retailTableName, retailerSelected.getIdNumber(), "'CnAdrPrf_Addrline1'", retailAdr1);
        retailerSelected.setAddressLine1(retailAdr1);
        af.update(retailTableName, retailerSelected.getIdNumber(), "'CnAdrPrf_Addrline2'", retailAdr2);
        retailerSelected.setAddressLine2(retailAdr2);
        af.update(retailTableName, retailerSelected.getIdNumber(), "'Boro'", retailBor);
        retailerSelected.setBorough(retailBor);
        af.update(retailTableName, retailerSelected.getIdNumber(), "'CnAdrPrf_City'", retailCity);
        retailerSelected.setCity(retailCity);

        if (!retailAdr1.equals(retailerSelected.getAddressLine1())) {
            String[] latLongPositions = ConvertAddress.getLatLongPositions(retailAdr1);
            String lat;
            String lon;

            if (latLongPositions != null) {
                lat = latLongPositions[0];
                lon = latLongPositions[1];

            } else {
                lat = "";
                lon = "";
            }
            retailerSelected.setAddressLine1(lat);
            af.update(retailTableName, retailerSelected.getIdNumber(), "'Lat'", lat);
            retailerSelected.setAddressLine1(lon);
            af.update(retailTableName, retailerSelected.getIdNumber(), "'Lon'", lon);
        }
        update = "Retailer";
        filterRetailer();
    }


    /**
     * Retrieves changes to the bike route record
     * from input, and updates the record in the database and the Route object
     * that represents the bike route being edited
     * If address for the start or end station has changed it attempts to find
     * the latitude and longitude for these addresses
     */
    public void editRouteRecord() {

        AccessFile af = new AccessFile();
        String routeTableName = SettingsPageController.settingsController.getRouteTable();
        String routeStartTime = routeStartTimeTF.getText();
        String routeEndTime = routeEndTimeTF.getText();
        String routeBikeId = routeBikeIdTF.getText();
        String routeStartName = routeStartNameTF.getText();
        String routeEndName = routeEndNameTF.getText();
        String routeStartId = routeStartIdTF.getText();
        String routeEndId = routeEndIdTF.getText();
        String routeUserGender = convertGenderToNum(routeUserGenderCB.getSelectionModel().getSelectedItem().toString());
        String routeUserType = routeUserTypeCB.getSelectionModel().getSelectedItem().toString();
        String routeUserBYear = routeBYearTF.getText();

        af.update(routeTableName, routeSelected.getIdNumber(), "'Start Time'", routeStartTime);
        routeSelected.setStartTime(routeStartTime);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Stop Time'", routeEndTime);
        routeSelected.setStopTime(routeEndTime);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Bike ID'", routeBikeId);
        routeSelected.setBikeId(routeBikeId);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Start Station Name'", routeStartName);
        routeSelected.setStartStationName(routeStartName);
        af.update(routeTableName, routeSelected.getIdNumber(), "'End Station Name'", routeEndName);
        routeSelected.setEndStationName(routeEndName);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Start Station ID'", routeStartId);
        routeSelected.setStartStationId(routeStartId);
        af.update(routeTableName, routeSelected.getIdNumber(), "'End Station ID'", routeEndId);
        routeSelected.setEndStationId(routeEndId);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Gender'", routeUserGender);
        routeSelected.setGender(routeUserGender);
        af.update(routeTableName, routeSelected.getIdNumber(), "'User Type'", routeUserType);
        routeSelected.setUserType(routeUserType);
        af.update(routeTableName, routeSelected.getIdNumber(), "'Birth Year'", routeUserBYear);
        routeSelected.setBirthYear(routeUserBYear);

        if (!routeStartName.equals(routeSelected.getStartStationName())) {
            String[] latLongPositions = ConvertAddress.getLatLongPositions(routeStartName);
            String lat;
            String lon;

            if (latLongPositions != null) {
                lat = latLongPositions[0];
                lon = latLongPositions[1];
            } else {
                lat = "";
                lon = "";
            }
            routeSelected.setStartStationLat(lat);
            af.update(routeTableName, routeSelected.getIdNumber(), "'Start Station Latitude'", lat);
            routeSelected.setStartStationLon(lon);
            af.update(routeTableName, routeSelected.getIdNumber(), "'Start Station Longitude'", lon);
        }

        if (!routeEndName.equals(routeSelected.getEndStationName())) {
            String[] latLongPositions = ConvertAddress.getLatLongPositions(routeEndName);
            String lat;
            String lon;

            if (latLongPositions != null) {
                lat = latLongPositions[0];
                lon = latLongPositions[1];

            } else {
                lat = "";
                lon = "";
            }
            routeSelected.setEndStationLat(lat);
            af.update(routeTableName, routeSelected.getIdNumber(), "'End Station Latitude'", lat);
            routeSelected.setEndStationLon(lon);
            af.update(routeTableName, routeSelected.getIdNumber(), "'End Station Longitude", lon);
        }
        update = "Route";
    }
}

