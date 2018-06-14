package seng202.team2.data;


/**
 * Provides methods that calculate distances and powers.
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class HaversineCalc {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM


    /**
     * Calculates the distance between two points from the provided lat longs
     * @param startLat, The latitude of the starting position
     * @param startLong, The longitude f the starting position
     * @param endLat, The latitude of the end position
     * @param endLong, The longitude of the end position
     * @return Double, the distance between the two positions provided.
     */
    public static double distance(double startLat, double startLong, double endLat, double endLong) {
        double dLat  = Math.toRadians((endLat - startLat));  //Creates new variable that converts the differences in latitudes to radians
        double dLong = Math.toRadians((endLong - startLong));  //Creates new variable that converts the differences in longitude to radians
        startLat = Math.toRadians(startLat);  //Converts the start Latitude to radians
        endLat   = Math.toRadians(endLat);  //Converts the end Latitude to radians
        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);  //Uses a formulae from google to calculate distances between the two points.
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));  //Calculates the angle
        return EARTH_RADIUS * c; // <-- d
    }


    /**
     * Calculates the power of the given value converted to a sin value
     * @param val, the value to be converted then squared
     * @return Double, the squared sin value
     */
    static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);  //Halves the given value, converts to a sin value, then squares
    }
}
