package seng202.team2.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.lynden.gmapsfx.service.directions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import netscape.javascript.JSObject;
import seng202.team2.data.*;
import seng202.team2.model.Retailer;
import seng202.team2.model.WifiLocation;

/**
 * Controller that allows users view and interact with the map
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML private GoogleMapView mapView;

    @FXML private TextField fromInput;

    @FXML private TextField toInput;

    @FXML private ComboBox filterOptions;

    @FXML private Button clearButton;

    @FXML private Button confirmRouteButton;

    @FXML private Button confirmRouteUserButton;

    @FXML private Button confirmSearch;

    @FXML private Button routeCompleted;

    private GoogleMap map;

    private int numMarkers;

    private LatLong[] startAndEndPoints;

    private DirectionsPane directionsPane;

    private DirectionsService directionsService;

    private StringProperty from = new SimpleStringProperty();

    private StringProperty to = new SimpleStringProperty();

    private DirectionsRenderer directionsRenderer;

    private LatLong currentMarker;

    static MapController mapController;

    /** Indication of whether the map is being used to show points of interest or create routes */
    private String mode;

    /** Distance between two points */
    private Double distance;

    private DirectionsResult directionsResults;


    /**
     * Overrides the initialize function for the map to load it in the GUI
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        to.bindBidirectional(toInput.textProperty());
        from.bindBidirectional(fromInput.textProperty());
    }

    /**
     * Overrides the mapInitialized function to set up the map with correct options
     */
    @Override
    public void mapInitialized() {
        mode = "Filter";
        startAndEndPoints = new LatLong[2];
        startAndEndPoints[0] = null;
        startAndEndPoints[1] = null;
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(40.758896, -73.9796)) //Set the initial properties of the map.
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .mapTypeControl(false)
                .styleString(mapStyle())
                .panControl(false)
                .rotateControl(true)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .minZoom(3)
                .zoom(12);
        map = mapView.createMap(mapOptions);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
        mapController = this;
        AccessFile accessFile = new AccessFile();
        ArrayList<String> names = accessFile.getTableNames();
        ObservableList<String> inComboBox = filterOptions.getItems();
        for (int i=0; i< names.size(); i++) {
            if (!seen(inComboBox, names.get(i)) && accessFile.getColumnCountOfTableName(names.get(i)) != 2 && accessFile.getColumnCountOfTableName(names.get(i)) != 16 ) {
                filterOptions.getItems().add(names.get(i));
            }
        }
        //Function creates a marker when the map is clicked
        numMarkers = 0;
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            if (numMarkers == 0) {
                LatLong latLong = event.getLatLong();
                userSetMarker(latLong);
                numMarkers++;
                startAndEndPoints[0] = latLong;
            } else if (numMarkers == 1 && mode.equals("Route")) {
                LatLong latLong = event.getLatLong();
                userSetMarker(latLong);
                numMarkers++;
                startAndEndPoints[1] = latLong;
            }
        });
    }


    /**
     * contains the String which sets the map style
     * @return Returns the map styling
     */
    private String mapStyle() {
        return "[\n" + "{\n" + "\"featureType\": \"administrative\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"off\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"administrative\",\n" + "\"elementType\": \"geometry.stroke\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"administrative\",\n" + "\"elementType\": \"labels\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"on\"\n" + "},\n" + "{\n" + "\"color\": \"#716464\"\n" + "},\n" +
                "{\n" + "\"weight\": \"0.01\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"administrative.country\",\n" + "\"elementType\": \"labels\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"landscape\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"landscape.natural\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"landscape.natural.landcover\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "},\n" + "]\n" + "    },\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"geometry.fill\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"geometry.stroke\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"labels.text\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"labels.text.fill\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi\",\n" + "\"elementType\": \"labels.text.stroke\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"poi.attraction\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{ featureType: \"poi\",  elementType: \"labels.icon\",  stylers: [  { visibility: \"off\" } ] }, " +
                "{\n" + "\"featureType\": \"road\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"road.highway\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"off\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"road.highway\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"road.highway\",\n" + "\"elementType\": \"geometry.fill\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"road.highway\",\n" + "\"elementType\": \"geometry.stroke\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"simplified\"\n" + "},\n" + "{\n" + "\"color\": \"#a05519\"\n" + "},\n" + "{\n" + "\"saturation\": \"-13\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"road.local\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"transit\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"transit\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"simplified\"\n" + "}\n" + "]\n" + "},\n" +
                "{\n" + "\"featureType\": \"transit.station\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" +
                "{ \"featureType\": \"transit.station\", \"elementType\": \"labels.icon\", \"stylers\": [ { \"visibility\": \"off\" } ] }," +
                "{\n" + "\"featureType\": \"water\",\n" + "\"elementType\": \"all\",\n" + "\"stylers\": [\n" + "{\n" + "\"visibility\": \"simplified\"\n" + "},\n" +
                "{\n" + "\"color\": \"#84afa3\"\n" + "},\n" + "{\n" + "\"lightness\": 52\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"water\",\n" + "\"elementType\": \"geometry\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "},\n" + "{\n" + "\"featureType\": \"water\",\n" + "\"elementType\": \"geometry.fill\",\n" + "\"stylers\": [\n" +
                "{\n" + "\"visibility\": \"on\"\n" + "}\n" + "]\n" + "}\n" + "]";
    }


    /**
     * Overrides the directionsReceived function so that it stores the distance of the route
     */
    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        DirectionsLeg directionsLeg = results.getRoutes().get(0).getLegs().get(0); // direction leg from start to finish of a route
        directionsResults = results;
        distance =  directionsLeg.getDistance().getValue();
        routeCompleted.setVisible(true);
    }


    /**
     * Creates and displays a marker for the given LatLong.
     * @param latLong The coordinates to place the marker.
     * @param type String saying where it is being called from.
     * @param infoStr String of information to be shown in the InfoWindow.
     */
    void setMarker(LatLong latLong, String type, String infoStr) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong)
                .visible(Boolean.TRUE);
        Marker marker = new Marker( markerOptions );
        map.addMarker(marker);
        if (type.equals("moreInfo")) {
            map.panTo(latLong);
            storeMarker(latLong);
        }
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.position(latLong);
            infoWindowOptions.pixelOffset(new Size(-1,-35));
            infoWindowOptions.content(infoStr);
            infoWindowOptions.maxWidth(150);
            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            infoWindow.open(map);
        });
    }


    /**
     * Stores given marker as the current marker
     * @param markerLatLong Latitude and Longitude of the marker to store as current
     */
    private void storeMarker(LatLong markerLatLong) {
        currentMarker = markerLatLong;
    }


    /**
     * Creates and displays a marker that the user has placed at a given LatLong.
     * @param latLong The coordinates to place the marker.
     */
    private void userSetMarker(LatLong latLong) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong)
                .icon("http://maps.google.com/mapfiles/ms/icons/green-dot.png")
                .visible(Boolean.TRUE);
        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);
    }


    /**
     * Creates and displays a marker for the given LatLong that is the closest to a point.
     * @param latLong The coordinates to place the marker.
     * @param infoStr String of information to be shown in the InfoWindow.
     */
    void setMarkerClosestEntity(LatLong latLong, String infoStr) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong)
                .icon("http://maps.google.com/mapfiles/ms/icons/green-dot.png")
                .animation(Animation.BOUNCE)
                .visible(Boolean.TRUE);
        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);
        map.panTo(latLong);
        map.setZoom(14);
        storeMarker(latLong);
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.position(latLong);
            infoWindowOptions.pixelOffset(new Size(-9,-30));
            infoWindowOptions.content(infoStr);
            infoWindowOptions.maxWidth(150);
            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            infoWindow.open(map);
        });
    }


    /**
     * Resizes the map to make it small and hides the buttons and inputFields
     */
    void resizeSmall() {
        mapView.setPrefWidth(525);
        clearButton.setLayoutX(325);
        confirmRouteButton.setLayoutX(325);
        routeCompleted.setLayoutX(325);
        routeCompleted.setVisible(false);
        confirmRouteButton.setVisible(false);
        confirmRouteUserButton.setVisible(false);
        confirmSearch.setVisible(false);
        filterOptions.setVisible(false);
        fromInput.setVisible(false);
        toInput.setVisible(false);
        mode = "Filter";
    }


    /**
     * Resizes the map to make it large and shows the buttons and inputFields
     */
    void resizeLarge() {
        mapView.setPrefWidth(1200);
        clearButton.setLayoutX(1000);
        routeCompleted.setLayoutX(1000);
        routeCompleted.setVisible(false);
        confirmRouteButton.setVisible(false);
        confirmRouteUserButton.setVisible(false);
        confirmSearch.setVisible(true);
        filterOptions.setVisible(true);
        fromInput.setVisible(true);
        toInput.setVisible(true);
    }


    /**
     * Gets all tables that are of type retailer or wifi and adds the to the values of the filterOptions comboBox.
     */
    public void getCombo() {
        AccessFile accessFile = new AccessFile();
        ArrayList<String> names = accessFile.getTableNames();
        ObservableList<String> inComboBox = filterOptions.getItems();
        for (int i=0; i< names.size(); i++) {
            if (!seen(inComboBox, names.get(i)) && accessFile.getColumnCountOfTableName(names.get(i)) != 2 && accessFile.getColumnCountOfTableName(names.get(i)) != 16 ){
                filterOptions.getItems().add(names.get(i));
            }
        }
    }


    /**
     * Checks if a string is already in an ObservableList and returns true if it is, otherwise false.
     * @param inComboBox The ObservableList it looks through.
     * @param name The string it looks for.
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
     * Finds the closest wifi locations or retailers to a point and displays markers at their locations.
     */
    public void displayOption() {
        clearMarkers();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (startAndEndPoints[0] != null) {
            userSetMarker(startAndEndPoints[0]);
            String option = filterOptions.getSelectionModel().getSelectedItem().toString();
            AccessFile accessFile = new AccessFile();
            if (accessFile.getColumnCountOfTableName(option) == 29) {
                ArrayList<WifiLocation> searchResult;
                searchResult = FilterWifi.findClosestWifiToPoint("" + startAndEndPoints[0].getLatitude(), "" + startAndEndPoints[0].getLongitude(),option);
                if (searchResult.size() == 0) {
                    alert.setContentText("No locations were found at this point");
                    alert.showAndWait();
                } else {
                    final ObservableList<WifiLocation> data = FXCollections.observableArrayList(searchResult);
                    for (WifiLocation wifiLocation: data) {
                        LatLong ll = new LatLong(Double.parseDouble(wifiLocation.getLat()), Double.parseDouble(wifiLocation.getLon()));
                        String info = "Wifi:<br>" + wifiLocation.getLocation() + "<br>" + wifiLocation.getProvider();
                        setMarker(ll, "filter", info);
                    }
                }
            } else if (accessFile.getColumnCountOfTableName(option) == 19) {
                ArrayList<Retailer> searchResult;
                searchResult = FilterRetailer.findClosestRetailerToPoint("" + startAndEndPoints[0].getLatitude(), "" + startAndEndPoints[0].getLongitude(),option);
                if (searchResult.size() == 0) {
                    alert.setContentText("No locations were found at this point");
                    alert.showAndWait();
                } else {
                    final ObservableList<Retailer> data = FXCollections.observableArrayList(searchResult);
                    for (Retailer ret : data) {
                        LatLong ll = new LatLong(Double.parseDouble(ret.getLatitude()), Double.parseDouble(ret.getLongitude()));
                        String info = "Retailer:<br>" + ret.getCompanyName() + "<br>" + ret.getPrimary() + "<br>" + ret.getAddressLine1();
                        setMarker(ll, "filter", info);
                    }
                }
            }
        } else {
            alert.setContentText("Please place a marker!");
            alert.showAndWait();
        }
    }


    /**
     * Clears all markers from the map
     */
    void clearMarkers(){
        try {
            map.clearMarkers();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Creates and displays a route between two coordinates given by the program.
     * @param start The start coordinate of the route.
     * @param end The end coordinates of the route.
     */
    void showRoute(LatLong start, LatLong end) {
        clearMarkers();
        DirectionsRequest request = new DirectionsRequest(start.getLatitude() + ", " + start.getLongitude(),
                end.getLatitude() + ", " + end.getLongitude() , TravelModes.BICYCLING);
        directionsService.getRoute(request, this, directionsRenderer);
    }


    /**
     * Creates and displays a route between a coordinates given by the user and one given by the program.
     */
    public void confirmRoute() {
        if (startAndEndPoints[0] != null) {
            clearMarkers();
            DirectionsRequest request = new DirectionsRequest(startAndEndPoints[0].getLatitude() + ", " + startAndEndPoints[0].getLongitude(),
                    currentMarker.getLatitude() + ", " + currentMarker.getLongitude(), TravelModes.BICYCLING);
            directionsService.getRoute(request, this, directionsRenderer);
            confirmRouteButton.setVisible(false);
        }
    }


    /**
     * Creates and displays a route between two coordinates given by the user.
     */
    public void confirmRouteUser() {
        if (startAndEndPoints[0] != null && startAndEndPoints[1] != null) {
            clearMarkers();
            DirectionsRequest request = new DirectionsRequest(startAndEndPoints[0].getLatitude() + ", " + startAndEndPoints[0].getLongitude(),
                    startAndEndPoints[1].getLatitude() + ", " + startAndEndPoints[1].getLongitude(), TravelModes.BICYCLING);
            directionsService.getRoute(request, this, directionsRenderer);
        }
    }


    /**
     * Changes the mode of the map so that it can only find the closest wifi and retailers to a point and makes the
     * appropriate buttons visible
     */
    public void swapModeFilter() {
        mode = "Filter";
        confirmRouteUserButton.setVisible(false);
        confirmSearch.setVisible(true);
        filterOptions.setDisable(false);
        clearDirections();
    }


    /**
     * Changes the mode of the map so that it can only routes between two point and makes the
     * appropriate buttons visible
     */
    public void swapModeRoute() {
        mode = "Route";
        confirmSearch.setVisible(false);
        filterOptions.setDisable(true);
        confirmRouteUserButton.setVisible(true);
        clearDirections();
    }


    /**
     * Creates two markers from their addresses (provided by user input) and displays a route between them.
     */
    public void displayRoute() {
        DirectionsRequest request = new DirectionsRequest(from.get(), to.get(), TravelModes.BICYCLING);
        directionsService.getRoute(request, this, directionsRenderer);
    }


    /**
     * Calls two functions, one to clear markers set from clicking on the map or using the comboBox filters,
     * and the other to clear markers and routes created by the displayRoute() function.
     */
    @FXML
    private void clearDirections() {
        clearMarkers();
        directionsRenderer.clearDirections();
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
        numMarkers = 0;
        startAndEndPoints[0] = null;
        startAndEndPoints[1] = null;
        routeCompleted.setVisible(false);
    }


    /**
     * Adds a distance in Kilometres to the traveled table in the database
     */
    public void addDistTraveled(){
        AccessFile accessFile = new AccessFile();
        accessFile.addDistToTraveled(distance/1000);
        routeCompleted.setVisible(false);
    }


    /**
     * Sets the confirm route button to visible
     */
    public void setConfirmRouteVisible() {
        confirmRouteButton.setVisible(true);
    }


    /**
     * @return directions results
     */
    DirectionsResult getDirectionsResults () {
        return directionsResults;
    }
}