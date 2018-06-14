package seng202.team2.data;

import seng202.team2.model.Route;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Provides methods to filter through the Routes table in the database
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class FilterRoute extends QueryData {

    private static DecimalFormat df2 = new DecimalFormat(".##");


    /**
     * Uses the HaversinceCalc.distance() method to calculate the distance between two stations for a bike route
     * @param startLat the latitude of the start station
     * @param startLon the longitude of the start station
     * @param endLat the latitude of the end station
     * @param endLon the longitude of the end station
     * @return double, the distance of the bike route
     */
    static double getRouteDistance(String startLat, String startLon, String endLat, String endLon) {
        try {
            double distance = HaversineCalc.distance(Double.parseDouble(startLat),
                    Double.parseDouble(startLon), Double.parseDouble(endLat), Double.parseDouble(endLon));
            String distance2 = df2.format(distance);
            return Double.parseDouble(distance2);
        } catch (NullPointerException e){
            return 0.0;
        }
    }


    /**
     * Retrieves bike route records that comply with the filters specified in the SQL query
     * @param sql the SQL statement to filter through records in the database
     * @param results and empty ArrayList to store the filtered Routes in
     * @return ArrayList<Route>, an ArrayList of filtered route records
     */
    private static ArrayList<Route> connectDb(String sql, ArrayList<Route> results ) { // <------WHY!!!!!
        try {
            Connection conn = AccessFile.connect();  //Connect to our db
            Statement stm = conn.createStatement();  //Defines which sql statements we can use
            ResultSet rs = stm.executeQuery(sql);  //The executeQuery allows us to use the SELECT sql statement rs gives us results as a line or row of the data table
            while (rs.next()) {  //Loop through all the result rows, until there are no more rows
                results.add(new Route(rs.getString("Trip Duration"), rs.getString("Start Time"), rs.getString("Stop Time"),
                        rs.getString("Start Station ID"), rs.getString("Start Station Name"), rs.getString("Start Station Latitude"),
                        rs.getString("Start Station Longitude"), rs.getString("End Station ID"), rs.getString("End Station Name"),
                        rs.getString("End Station Latitude"), rs.getString("End Station Longitude"), rs.getString("Bike ID"),
                        rs.getString("User Type"), rs.getString("Birth Year"), rs.getString("Gender"), rs.getString("IdNumber")));
            }
            AccessFile.disconnect(conn);
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves a subset of all route records that have not been filtered
     * @param tableName the name of the table to be searched through
     * @return ArrayList<Route>, containing all of the routes and their information
     */
    public static ArrayList<Route> selectAllRoutes(String tableName) {
        String sql = "SELECT *  FROM " + tableName;
        ArrayList<Route> results = new ArrayList<>();
        return connectDb(sql,results);
    }


    /**
     * Creates the SQL queries that are required to search for Routes that match the given inputs
     * @param startStation the name of the start station to filter by
     * @param endStation the name of the end station to filter by
     * @param tableName the name of the table to search through
     * @return ArrayList<Route>, containing all the information about the filtered routes
     */
    public static ArrayList<Route> searchRouteXByY(String startStation, String endStation, String tableName) {
        ArrayList<String> validConditions = new ArrayList<>();
        if (!startStation.equals("''")) {  //Checking for valid conditions (from user inputs to add to the sql statement)
            validConditions.add("\"START STATION Name\" LIKE LOWER('%" + startStation.replaceAll("'","") + "%')");
        }
        if (!endStation.equals("''")) {
            validConditions.add("\"END STATION Name\" LIKE LOWER('%" + endStation.replaceAll("'","") + "%')");
        }
        int maxQuery = Integer.MAX_VALUE;
        String buildWhereClause = buildWhereClauseString(validConditions);  //Building the where clause for the sql statement with relevant conditions
        tableName = tableName + " ";
        String sql = "SELECT* FROM " + tableName + buildWhereClause;  //Finishing off the sql statement
        ArrayList<Route> result = new ArrayList<>();
        try {
            Connection conn = AccessFile.connect();  //Attempting to connect to the database
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);  //Executing the sql statement
            int i = 0;
            while (rs.next() && i < maxQuery) {  //While there is still a result, iterating through and retrieving all information
                result.add(new Route(rs.getString("Trip Duration"), rs.getString("Start Time"), rs.getString("Stop Time"),
                        rs.getString("Start Station ID"), rs.getString("Start Station Name"), rs.getString("Start Station Latitude"),
                        rs.getString("Start Station Longitude"), rs.getString("End Station ID"), rs.getString("End Station Name"),
                        rs.getString("End Station Latitude"), rs.getString("End Station Longitude"), rs.getString("Bike ID"),
                        rs.getString("User Type"), rs.getString("Birth Year"), rs.getString("Gender"), rs.getString("IdNumber")));
            }
            AccessFile.disconnect(conn);
            return result;
        } catch (SQLException e) {  //catching database connection error
            e.printStackTrace();
        }
        return null;
    }







}
