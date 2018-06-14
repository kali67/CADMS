package seng202.team2.data;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import seng202.team2.model.Retailer;
import seng202.team2.model.WifiLocation;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides methods to filter through the wifi table in the database.
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class FilterWifi extends QueryData {

    /**
     * Retrieves wifi records that comply with the filters specified in the SQL query
     * @param sql the SQL statement to filter through records in the database
     * @return ArrayList<WifiLocation>, an ArrayList of filtered wifi location records
     */
    private static ArrayList<WifiLocation> queryDatabase(String sql) {
        Connection conn = AccessFile.connect();  //Connecting to the database
        Statement stm;
        ArrayList<WifiLocation> result = new ArrayList<>();
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);  //Executing the query
            while (rs.next()) {  //Adding all relevant data from the filtered wifi into the provided arrayList
                result.add(new WifiLocation(rs.getString("OBJECTID"), rs.getString("the_geom"), rs.getString("BORO"), rs.getString("TYPE"),
                        rs.getString("PROVIDER"), rs.getString("NAME"), rs.getString("LOCATION"), rs.getString("LAT"),
                        rs.getString("LON"), rs.getString("X"), rs.getString("Y"), rs.getString("LOCATION_T"), rs.getString("REMARKS"),
                        rs.getString("CITY"), rs.getString("SSID"), rs.getString("SOURCEID"), rs.getString("ACTIVATED"), rs.getString("BOROCODE"),
                        rs.getString("BOROName"), rs.getString("NTACODE"), rs.getString("NTANAME"), rs.getString("COUNDIST"), rs.getString("POSTCODE"), rs.getString("BOROCD"),
                        rs.getString("CT2010"), rs.getString("BOROCT2010"), rs.getString("BIN"), rs.getString("BBL"), rs.getString("DOITT_ID")));
            }
            AccessFile.disconnect(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Connects to the database, using connect(), and searches for certain local Wifi's by the given strings.
     * @param providerCondition, The wifi provider string
     * @param typeCondition,  The type of wifi string
     * @param boroCondition,  the borough where the wifi is situated
     * @return ArrayList containing all of the data about the filtered wifi
     */
    public static ArrayList<WifiLocation> searchWifiXByY(String providerCondition, String typeCondition, String boroCondition, String tableName) {
        ArrayList<String> validConditions = new ArrayList<>();
        if (!providerCondition.equals("'Any'")) {  //Adding the given conditions (from the user) to an arrayList
            validConditions.add("PROVIDER = " +providerCondition);
        }
        if (!typeCondition.equals("'Any'")) {
            validConditions.add("TYPE = " +typeCondition);
        }
        if (!boroCondition.equals("'Any'")){
            validConditions.add("BORO = " + boroCondition);
        }
        String buildWhereClause = buildWhereClauseString(validConditions);  //Creating a where clause for the sql statement from the valid condtions arrayList
        String sql = "SELECT* FROM " + tableName + " " + buildWhereClause;  //Finalising the sql statement
        return queryDatabase(sql);
    }


    /**
     * Finds WifiLocations within a 500m radius of marker on the map
     * @param lat the latitude of the marker
     * @param lon the longitude of the marker
     * @param tableName the name of the table to search for WifiLocations in
     * @return ArrayList<WifiLocation>, contains a subsection of the wifi locations within a certain radius of the marker
     */
    public static ArrayList<WifiLocation> findClosestWifiToPoint(String lat, String lon, String tableName) {
        String sql = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " - \"lat\") <= 0.00500" +
                " AND ABS(" + lon + "-\"lon\") <= 0.00500";
        String sql1 = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " + \"lat\") <= 0.00500" +
                " AND ABS(" + lon + "+\"lon\") <= 0.00500";
        ArrayList<WifiLocation> results = queryDatabase(sql);
        ArrayList<WifiLocation> results2 = queryDatabase(sql1);
        results.removeAll(results2);
        results.addAll(results2);
        Collections.shuffle(results);
        if (results.size() <= 60) {
            return results;
        }
        ArrayList<WifiLocation> minSet = new ArrayList<>();
        for (int i=0; i<=60; i++) {
            minSet.add(results.get(i));
        }
        return minSet;
    }


    /**
     * Retrieves a subset of all wifi location records that have not been filtered
     * @param tableName the name of the table to be searched through
     * @return ArrayList<WifiLocation>, contains a subset of the wifi locations and their information
     */
    public static ArrayList<WifiLocation> selectAllWifi(String tableName) {
        return searchWifiXByY("'Any'","'Any'","'Any'", tableName);
    }


    /**
     * Finds the closet wifi to a given retail address
     * @param retailerAddress the address to find wifi's from
     * @return ArrayList<WifiLocation>, containing all the data about the closest wifi
     */
    public static WifiLocation findClosestWifiToRetail(String retailerAddress, String tableName) {
        try {
            ArrayList<WifiLocation> allWifi = FilterWifi.selectAllWifi(tableName);  //Selects all wifi in the database
            ArrayList<Double> distances = new ArrayList<>();
            String[] result = ConvertAddress.getLatLongPositions(retailerAddress);    //Retrieves the latLong position of the retailer
            for (int i=0; i<allWifi.size(); i++) {   //Iterates through all wifi records and calculates the distance to the retailer
                distances.add(HaversineCalc.distance(Double.parseDouble(result[0]), Double.parseDouble(result[1]),
                        Double.parseDouble(allWifi.get(i).getLat()),
                        Double.parseDouble(allWifi.get(i).getLon())));
            }
            double min = Integer.MAX_VALUE;
            int index = 0;
            for (int j=0; j<distances.size();j++) {  //iterates through the list, comparing every distance to find the smallest
                if (distances.get(j) < min) {  //If shorter distance, set index
                    index = j;
                    min = distances.get(j);
                }
            }
            return allWifi.get(index);

        } catch (Exception e) {  //Catches any errors.
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
        return null;  //if catch is triggered, returns null
    }


    /**
     * Find the closest wifi to a given point
     * @param lat Latitude value of the point to search from
     * @param lon Longitude value of the point to search from
     * @param tableName Table to select retailers from
     * @return Returns the closest Retailer
     */
    private static WifiLocation findClosestWifiToPointRoute(String lat, String lon, String tableName) {

        String sql = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " - \"lat\") <= 0.100" +
                " AND ABS(" + lon + "-\"lon\") <= 0.100";
        ArrayList<WifiLocation> results = queryDatabase(sql);
        for (int i=0; i<results.size();i++) {
            results.get(i).setDistanceToRoute(HaversineCalc.distance(Double.parseDouble(results.get(i).getLat()), Double.parseDouble(results.get(i).getLon()), Double.parseDouble(lat), Double.parseDouble(lon)));
        }
        try {
            WifiLocation closest = results.get(0);
            for (int i=0; i<results.size();i++) {
                if (closest.getDistanceToRoute() > results.get(i).getDistanceToRoute()) {
                    closest = results.get(i);
                }
            }

            return closest;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Checks if a wifi location is in a ArrayList.
     * @param array The ArrayList to check in.
     * @param r The wifi location that is searched for.
     * @return Return true if the wifi location is in the ArrayList, otherwise false.
     */
    private static boolean seen(ArrayList<WifiLocation> array, WifiLocation r) {
        try {
            for (int i=0; i<array.size(); i++) {
                if (r.equals(array.get(i))) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    /**
     * Gets the closest wifi location to a set of points.
     * @param points The set of points to search around.
     * @param tableName The name of the table to search from.
     * @param resultingArray The ArrayList to return.
     * @return Returns the edited resultingArray
     */
    private static  ArrayList<WifiLocation> getClosestWifiLocations(ArrayList<LatLong> points, String tableName, ArrayList<WifiLocation> resultingArray) {
        ArrayList<WifiLocation> temp = new ArrayList<>();
        for (int i = 0; i<points.size(); i+=12) {

            temp.add(findClosestWifiToPointRoute(Double.toString(points.get(i).getLatitude()), Double.toString(points.get(i).getLongitude()),tableName));
            for (int j = 0; j<temp.size();j++) {
                if (!seen(resultingArray, temp.get(j))){
                    resultingArray.add(temp.get(j));
                }
            }
            temp.clear();
        }
        try {
            WifiLocation closest = resultingArray.get(0);
            for (int i=0; i<resultingArray.size();i++) {
                if (closest.getDistanceToRoute() > resultingArray.get(i).getDistanceToRoute()) {
                    closest = resultingArray.get(i);
                }
            }
            resultingArray.add(closest); // the closest one is the last one in the array
            return resultingArray;
        } catch (IndexOutOfBoundsException e) {
            return resultingArray;
        }
    }


    /**
     * Returns the closest wifi location to a route.
     * @param results The route that it searches near.
     * @param tableName The table to search for wifi location from.
     * @return Returns the closest wifi location to a route.
     */
    public static ArrayList<WifiLocation> findNearestWifi(DirectionsResult results, String tableName) {
        ArrayList<LatLong> points = getPointsOnRoute(results);
        ArrayList<WifiLocation> resultingArray = new ArrayList<>();
        ArrayList<WifiLocation> stuff = getClosestWifiLocations(points,tableName, resultingArray);

        return stuff;
    }

}
