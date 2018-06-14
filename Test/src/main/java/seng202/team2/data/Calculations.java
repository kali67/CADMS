package seng202.team2.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides two methods, to retrieve data and perform calculations on the data
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class Calculations {


    /**
     * Calculates the total carbon emissions saved by the user for their distances traveled
     * @param days: the number of days of data that are wanted
     * @return Double, the carbon emissions saved.
     */
    public double[] calculateCarbon(int days){
        double[] carbonSaved = new double[days];  //Creates new array with size 365
        double[] distTraveled = getDistances(days);  //Calls get Distances to calculate the total distance travelled
        for (int i = 0; i < days; i++) {
            carbonSaved[i] = distTraveled[i] * 0.25528;  //Iterates through the double array provided by getDistances, multiplying by the percentage of carbon
        }
        return carbonSaved;  //Returns an array with the amount of carbon saved for every trip
    }


    /**
     * Retrieves every instance of travel made by the user, then calculates the total distance travelled by the user
     * @param days: the number of days of data that are wanted
     * @return  Double, the total distance travelled by the user from the routes they have taken
     */
    private double[] getDistances(int days){
        double[] distTraveled = new double[days];  //Creating empty double array
        String[][] traveled = QueryData.selectAllTraveled();  //Retrieving all traveled routes from the database into a String Array
        List<DateDist> dateAndDist = new ArrayList();  //Creating a new List of type DateDist
        for (int i = 0; i < 365; i++) {
            if(traveled[i][0] == null) {  //Checking if the route travelled in position i is Null or not
                LocalDate date = LocalDate.now();  //Setting the instance of LocalDate to the time on the machine the app is being used
                dateAndDist.add(i, new DateDist(date,0));  //Adds a new instance of DateDist to the List of DateDist type
            } else {
                LocalDate date = LocalDate.parse(traveled[i][0]);  //Sets an instance of LocalDate to time when route was travelled, and formats accordingly
                dateAndDist.add(i, new DateDist(date, Double.parseDouble(traveled[i][1])));  //Adding at index i a new instance of DateDist into the list of type DateDist
            }
        }
        Collections.sort(dateAndDist);  //Sorts the list
        for (int i = 0; i < days; i++) {  //Iterating through entire distance travelled array
            distTraveled[i] = dateAndDist.get((365 - days) + i).getDist();  //changing the value in distance travelled accordingly
        }
        for (int i = 1; i < days; i++) {
            distTraveled[i] = distTraveled[i] + distTraveled[i-1];  //Iterating through distance travelled adding the previous entry to the current entry
        }
        return distTraveled;
    }
}