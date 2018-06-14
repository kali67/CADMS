

package seng202.team2.data;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import seng202.team2.model.Retailer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides methods to filter through the retailer table
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class FilterRetailer extends QueryData{


    /**
     * Retrieves retailer records that comply with the filters specified in the SQL query
     * @param sql the SQL statement to filter through records in the database
     * @return ArrayList<Retailer>, an ArrayList of filtered retailer records
     */
    private static ArrayList<Retailer> queryDatabase(String sql) {
        ArrayList<Retailer> result = new ArrayList<>();
        try {
            Connection conn = AccessFile.connect();  //Attempting to connect to the database to retrieve filtered data
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {  //While there is a next in the result set, getting the strings from the table and adding to the final array list
                result.add(new Retailer(rs.getString("CnBio_Org_Name"), rs.getString("CnAdrPrf_Addrline1"), rs.getString("CnAdrPrf_Addrline2"),
                        rs.getString("CnAdrPrf_City"), rs.getString("CnAdrPrf_State"), rs.getString("CnAdrPrf_ZIP"),
                        rs.getString("Block-Lot"), rs.getString("Primary"), rs.getString("Secondary"), rs.getString("Boro"),rs.getString("Lat"),
                        rs.getString("Lon"), rs.getString("Community Board"),rs.getString("Council District"),rs.getString("Census Tract"),
                        rs.getString("BIN"), rs.getString("BBL"), rs.getString("NTA"), rs.getString("IdNumber")));
            }
            AccessFile.disconnect(conn);
            return result;
        } catch (SQLException e){  //Catching the error if connecting to the database failed
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Creates the SQL queries that are required to search for Retailers that match the given inputs
     * @param streetNameCondition the name of the street to be filtered by
     * @param nameCondition the name of the retailer to be filtered by
     * @param primaryCondition the primary use of the shop
     * @return ArrayList<Retailer>, that contains string arrayLists with the filtered data from the database
     */
    public static ArrayList<Retailer> searchRetailerXByY(String streetNameCondition, String nameCondition, String primaryCondition, String tableName) {
        ArrayList<String> validConditions = new ArrayList<>();
        if (!streetNameCondition.equals("''")) {  //Checking for conditions provided by user and adding to array
            validConditions.add("LOWER(CnAdrPrf_Addrline1) LIKE LOWER('%" +streetNameCondition.replace("'", "") + "%')");
        }
        if (!nameCondition.equals("''")) {
            validConditions.add("LOWER(CnBio_Org_Name) LIKE LOWER('%" + nameCondition.replace("'", "") + "%')");
        }
        if (!primaryCondition.equals("'Any'")) {
            validConditions.add("\"Primary\" = " + primaryCondition);
        }
        String buildWhereClause = buildWhereClauseString(validConditions);  //Building a where clause for an sql statement with the conditions given by the user
        String sql = "SELECT* FROM " + tableName + " " + buildWhereClause;  //Finishing the sql statement
        return queryDatabase(sql);
    }


    /**
     * Retrieves a subset of all retailer records that have not been filtered
     * @param tableName the name of the table to be searched through
     * @return ArrayList<Retailer>, containing all of the retailers and their information
     */
    public static ArrayList<Retailer> selectAllRetailers(String tableName) {
        return searchRetailerXByY("''", "''", "'Any'", tableName);
    }


    /**
     * Finds Retailers within a 500m radius of marker on the map
     * @param lat the latitude of the marker
     * @param lon the longitude of the marker
     * @param tableName the name of the table to search for Retailers in
     * @return ArrayList<Retailer>, contains a subsection of the retailers within a certain radius of the marker
     */
    public static ArrayList<Retailer> findClosestRetailerToPoint(String lat, String lon, String tableName) {
        String sql = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " - \"lat\") <= 0.0500" +
                " AND ABS(" + lon + "-\"lon\") <= 0.0500";
        String sql1 = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " + \"lat\") <= 0.0500" +
                " AND ABS(" + lon + "+\"lon\") <= 0.0500";
        ArrayList<Retailer> results = queryDatabase(sql);
        ArrayList<Retailer> results2 = queryDatabase(sql1);
        results.removeAll(results2);
        results.addAll(results2);
        Collections.shuffle(results);
        if (results.size() <= 60) {
            return results;
        }
        ArrayList<Retailer> minSet = new ArrayList<>();
        for (int i=0; i<=60; i++) {  //If there are too many retailers in a close proximity, only return a subset of them
            minSet.add(results.get(i));
        }
        return minSet;
    }


    /**
     * Find the closest retailer to a given point
     * @param lat Latitude value of the point to search from
     * @param lon Longitude value of the point to search from
     * @param tableName Table to select retailers from
     * @return Returns the closest Retailer
     */
    private static Retailer findClosestRetailerToPointRoute(String lat, String lon, String tableName) {

        String sql = "SELECT * FROM " + tableName + " WHERE ABS(" + lat + " - \"lat\") <= 0.100" +
                " AND ABS(" + lon + "-\"lon\") <= 0.100";
        ArrayList<Retailer> results = queryDatabase(sql);

        for (int i=0; i<results.size();i++) {
            results.get(i).setDistanceToRoute(HaversineCalc.distance(Double.parseDouble(results.get(i).getLatitude()), Double.parseDouble(results.get(i).getLongitude()), Double.parseDouble(lat), Double.parseDouble(lon)));
        };
        try {
            Retailer closest = results.get(0);
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
     * Checks if a retailer is in a ArrayList.
     * @param array The ArrayList to check in.
     * @param r The Retailer that is searched for.
     * @return Return true if the Retailer is in the ArrayList, otherwise false.
     */
    private static boolean seen(ArrayList<Retailer> array, Retailer r) {
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
     * Gets the closest Retailers to a set of points.
     * @param points The set of points to search around.
     * @param tableName The name of the table to search from.
     * @param resultingArray The ArrayList to return.
     * @return Returns the edited resultingArray
     */
    private static  ArrayList<Retailer> getClosestRetailerLocation(ArrayList<LatLong> points, String tableName, ArrayList<Retailer> resultingArray) {
        ArrayList<Retailer> temp = new ArrayList<>();
        for (int i = 0; i<points.size(); i+=25) {
            temp.add(findClosestRetailerToPointRoute(Double.toString(points.get(i).getLatitude()), Double.toString(points.get(i).getLongitude()),tableName));
            for (int j = 0; j<temp.size();j++) {
                resultingArray.add(temp.get(j));
            }
            temp.clear();
        }
        try {
            Retailer closest = resultingArray.get(0);
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
     * Returns the closest retailers to a route.
     * @param results The route that it searches near.
     * @param tableName The table to search for Retailers from.
     * @return Returns the closest retailers to a route.
     */
    public static ArrayList<Retailer> findNearestRetailers(DirectionsResult results, String tableName) {

        ArrayList<LatLong> points = getPointsOnRoute(results);
        ArrayList<Retailer> resultingArray = new ArrayList<>();

        ArrayList<Retailer> retail = getClosestRetailerLocation(points,tableName, resultingArray);

        return retail;
    }
}

