package seng202.team2.model;


import seng202.team2.controller.SettingsPageController;
import seng202.team2.data.AccessFile;
import seng202.team2.data.ConvertAddress;
import seng202.team2.data.HaversineCalc;

import java.text.DecimalFormat;

/**
 * Route Model Object
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class Route {

    /** The length of time the route takes int seconds */
    private int tripDuration;

    /** Date and time of the start of the bike route in the format YYY-MM-DD HH:MM:SS */
    private String startTime;

    /** Date and time of the end of the bike route in the format YYY-MM-DD HH:MM:SS */
    private String stopTime;

    /** Identification number of the start bike station of the route */
    private String startStationId;

    /** Name of the start bike station - a description of it's location */
    private String startStationName;

    /** The latitude of the start station */
    private String startStationLat;

    /** The longitude of the start station */
    private String startStationLon;

    /** Identification number of the end bike station of the route */
    private String endStationId;

    /** Name of the end bike station - a description of it's location */
    private String endStationName;

    /** The latitude of the end station */
    private String endStationLat;

    /** The longitude of the end station */
    private String endStationLon;

    /** Identification number of the bike used for the route */
    private String bikeId;

    /** Type of the user who took the bike route ('Customer' or 'Subscriber') */
    private String userType;

    /** Birth year of the user who took the bike route */
    private String birthYear;

    /** Int indicating the gender of the cyclist who took the bike route */
    private String gender;

    /** Identification number of the user who took the bike route */
    private String idNumber;

    /** Distance from the route to some point (not always used) */
    private double distance = 0;

    private AccessFile af;

    private static DecimalFormat df2 = new DecimalFormat(".##");


    /**
     * Constructor for Route model class
     */
    public Route(String tripDuration, String startTime, String stopTime, String startStationId, String startStationName, String startStationLat,
                 String startStationLon, String endStationId, String endStationName, String endStationLat, String endStationLon, String bikeId,
                 String userType, String birthYear, String gender, String idNumber) {
        this.tripDuration = Integer.valueOf(tripDuration);
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.startStationId = startStationId;
        this.startStationName = startStationName;
        this.startStationLat = startStationLat;
        this.startStationLon = startStationLon;
        this.endStationId = endStationId;
        this.endStationName = endStationName;
        this.endStationLat = endStationLat;
        this.endStationLon = endStationLon;
        this.bikeId = bikeId;
        this.userType = userType;
        if (birthYear == null || birthYear.isEmpty()){
            birthYear = "N";
        }
        this.birthYear = birthYear;
        if (gender == null || gender.isEmpty()) {
            gender = "0";
        }
        this.gender = gender;
        this.idNumber = idNumber;
        this.distance = getDistance();
    }


    /**
     * @return {@link Route#distance}
     */
    public double getDistance(){
        if (distance == 0 ){
            double distance = HaversineCalc.distance(Double.parseDouble(getStartStationLat()),Double.parseDouble(getStartStationLon()),Double.parseDouble(getEndStationLat()),Double.parseDouble(getEndStationLon()));
            String distance2 = df2.format(distance);
            return Double.parseDouble(distance2);
        } else {
            return distance;
        }

    }


    /**
     * @return {@link Route#tripDuration}
     */
    public int getTripDuration() {
        return tripDuration;
    }


    /**
     * @return {@link Route#startTime}
     */
    public String getStartTime() {
        return startTime;
    }


    /**
     * @return {@link Route#stopTime}
     */
    public String getStopTime() {
        return stopTime;
    }


    /**
     * @return {@link Route#startStationId}
     */
    public String getStartStationId() {
        return startStationId;
    }


    /**
     * @return {@link Route#startStationName}
     */
    public String getStartStationName() {
        return startStationName;
    }


    /**
     * @return {@link Route#startStationLat}
     */
    public String getStartStationLat() {
        return startStationLat;
    }


    /**
     * @return {@link Route#startStationLon}
     */
    public String getStartStationLon() {

        return startStationLon;
    }


    /**
     * @return {@link Route#endStationId}
     */
    public String getEndStationId() {
        return endStationId;
    }


    /**
     * @return {@link Route#endStationName}
     */
    public String getEndStationName() {
        return endStationName;
    }


    /**
     * @return {@link Route#endStationLat}
     */
    public String getEndStationLat() {

        return endStationLat;
    }


    /**
     * @return {@link Route#endStationLon}
     */
    public String getEndStationLon() {

        return endStationLon;
    }


    /**
     * @return {@link Route#bikeId}
     */
    public String getBikeId() {
        return bikeId;
    }


    /**
     * @return {@link Route#userType}
     */
    public String getUserType() {
        return userType;
    }


    /**
     * @return {@link Route#birthYear}
     */
    public String getBirthYear() {
        return birthYear;
    }


    /**
     * @return {@link Route#gender}
     */
    public String getGender() {
        return gender;
    }


    /**
     * @return {@link Route#idNumber}
     */
    public String getIdNumber() {
        return idNumber;
    }


    /**
     * Setter for {@link Route#startTime}
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    /**
     * Setter for {@link Route#stopTime}
     */
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }


    /**
     * Setter for {@link Route#startStationId}
     */
    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }


    /**
     * Setter for {@link Route#startStationName}
     */
    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }


    /**
     * Setter for {@link Route#startStationLat}
     */
    public void setStartStationLat(String startStationLat) {
        this.startStationLat = startStationLat;
    }


    /**
     * Setter for {@link Route#startStationLon}
     */
    public void setStartStationLon(String startStationLon) {
        this.startStationLon = startStationLon;
    }


    /**
     * Setter for {@link Route#endStationId}
     */
    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }


    /**
     * Setter for {@link Route#endStationName}
     */
    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }


    /**
     * Setter for {@link Route#endStationLat}
     */
    public void setEndStationLat(String endStationLat) {
        this.endStationLat = endStationLat;
    }


    /**
     * Setter for {@link Route#endStationLon}
     */
    public void setEndStationLon(String endStationLon) {
        this.endStationLon = endStationLon;
    }


    /**
     * Setter for {@link Route#bikeId}
     */
    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }


    /**
     * Setter for {@link Route#userType}
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }


    /**
     * Setter for {@link Route#birthYear}
     */
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }


    /**
     * Setter for {@link Route#gender}
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * Setter for {@link Route#distance}
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }


    /**
     * Overwrites the equals method for a Route object (used for the junit tests)
     * @param o an Object
     * @return Boolean, depends on whether the Route values are equal to the given objects values
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Route) {
            Route route = (Route) o;
            if (this.startStationName.equals(route.startStationName) && this.getTripDuration() == route.getTripDuration()
                    && this.getEndStationName().equals(route.getEndStationName()) && this.getUserType().equals(route.getUserType())) {
                return true;
            }
        }
        return false;
    }
}
