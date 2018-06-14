package seng202.team2.data;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.service.directions.DirectionsLeg;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import java.sql.*;
import java.util.ArrayList;



/**
 * Class of methods that get data from the database
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class QueryData {


    /**
     * Builds a where clause statement to be added to an sql statement.
     * @param validConditions, an arrayList containing all of the conditions required by the user
     * @return String, the clause to be added on the end of an sql statement
     */
    static String buildWhereClauseString(ArrayList<String> validConditions) {
        String buildWhereClause = "";  //Initialising to an empty string
        if (validConditions.size() > 0) {
            buildWhereClause = "WHERE ";  //If the arrayList isn't empty, there is at least one condition to be included in a where clause
            for (int i = 0; i < validConditions.size(); i++) {  //Iterating through the valid conditions list
                if (i != validConditions.size() - 1) {  //If the condition isn't the last one, adding clause plus and
                    buildWhereClause += (validConditions.get(i) + " AND ");
                } else {
                    buildWhereClause += validConditions.get(i);  //Since the condition is the last in the list, adding it by itself
                }
            }
        }
        return buildWhereClause;  //Returning the clause to be used in an sql statement
    }


    /**
     * Gets the number of items in a table.
     * @param tableName The table to get the number of items in.
     * @return Returns the number of items in the table.
     */
    public static int getCountTable(String tableName) {
        String sql = "SELECT COUNT(*) as size FROM " + tableName;
        Connection conn = AccessFile.connect();  //Connecting to the database
        Statement stm;
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);  //Executing the query
            rs.next();
            int count = rs.getInt("size");
            AccessFile.disconnect(conn);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Connects to the database, using connect(), then retrieves all the routes the user has "travelled" up to the point of this query.
     * If the database can't be accessed, a suitable message is relayed to the user.
     * @return ArrayList, a list of all the distances of the routes the user has taken.
     */
    public static String[][] selectAllTraveled() {
        String sql = "SELECT * FROM Traveled";  //The sql statement to retrieve all travelled routes
        String[][] results = new String[365][2];  //Allocating space to hold all of the travelled routes
        try {
            Connection conn = AccessFile.connect();  //Attempting to connect to our database
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  //Executing the sql statement
            int counter = 0;
            while (rs.next()) {  //While there are still routes, iterate
                for (int j = 1; j < 3; j++) {
                    results[counter][j - 1] = rs.getString(j);  //Adding a route to a space in n array
                }
                counter++;  //Incrementing to the next position in the array
            }
            AccessFile.disconnect(conn);
            for (; counter < 365; counter++) {  //Iterating through the rest of the results array and setting the first two positions in each section to null
                results[counter][0] = null;
                results[counter][1] = null;
            }
            return results;
        } catch (SQLException e) {  //Catching the connection error
            System.out.println("Error accessing data...");
            System.out.println(e.getMessage());
            for (int i = 0; i < 365; i++) {  //Setting the elements in the results array to null
                results[i][0] = null;
                results[i][1] = null;
            }
            return results;
        }
    }

    /**
     * Finds all the unique values in the given column of a given table
     * @param tableName name of the table to search
     * @return ArrayList of the unique values in the given column of the given table
     */
    public static ArrayList<String> getUniqueValues(String tableName) {
        ArrayList<String> uniqueValues = new ArrayList<>();
        String sql = "SELECT distinct provider FROM " + tableName;
        try {
            Connection conn = AccessFile.connect();  //Attempting to connect to our database
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  //Executing the sql statement
            while (rs.next()) {
                uniqueValues.add(rs.getString("provider"));
            }
            AccessFile.disconnect(conn);
            return uniqueValues;
        } catch (SQLException e) {  //Catching the connection error
            System.out.println("Error accessing data...");
            System.out.println(e.getMessage());
            return uniqueValues;
        }
    }


    /**
     * Gets the distance from the TRAVELED table with that corresponds to the given date.
     * @param date The date  corresponding to the distance wanted.
     * @return returns a string of a distance.
     */
    String getSingleTraveled(String date) {
        String sql = String.format("SELECT DISTANCE FROM TRAVELED WHERE Date = '%s'", date);
        String result;
        try {
            Connection conn = AccessFile.connect();  //Attempting to connect to our database
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  //Executing the sql statement
            result = rs.getString(1);
            AccessFile.disconnect(conn);
            return result;
        } catch (SQLException e) {
            System.out.println("Error accessing data...");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets a set of points on a route
     * @param results The route to get points from.
     * @return Returns an ArrayList of LatLong points.
     */
    static ArrayList<LatLong> getPointsOnRoute(DirectionsResult results) {
        DirectionsLeg directionsLeg = results.getRoutes().get(0).getLegs().get(0); // direction leg from start to finish of a route
        ArrayList<LatLong> pointsOnRoute = new ArrayList<>();
        for (int i = 0; i < directionsLeg.getSteps().size(); i++) {
            for (int j = 0; j < directionsLeg.getSteps().get(i).getPath().size(); j++) {
                LatLong ll = directionsLeg.getSteps().get(i).getPath().get(j);
                pointsOnRoute.add(ll);
            }
        }
        return pointsOnRoute;
    }
}