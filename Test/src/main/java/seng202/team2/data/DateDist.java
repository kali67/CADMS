package seng202.team2.data;

import java.time.LocalDate;


/**
 * Provides methods that deal with dates and distances
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class DateDist implements Comparable<DateDist> {

    private LocalDate date;
    private double dist;


    /**
     * Sets the classes private variables to the values given by the parameters
     * @param date The value to set the private variable date to.
     * @param dist The value to set the private variable dist to
     */
    DateDist(LocalDate date, double dist){
        this.date = date;
        this.dist = dist;
    }


    /**
     * Retrieves the private variable date from the instance being used
     * @return The date of the instance of DateDist
     */
    private LocalDate getDate(){
        return date;
    }


    /**
     * Retrieves the private variable distance form the instance being used.
     * @return The distance of the instance of DateDist
     */
    double getDist(){
        return dist;
    }


    /**
     * Compares two separate instances od DateDist to see which comes before the other. Overwrites the compareTo method
     * @param dateDist another instance of DateDist to be compared with the current instance
     * @return int, describing whichever instance of DateDist comes first
     */
    @Override
    public int compareTo(DateDist dateDist) {
        return  getDate().compareTo(dateDist.getDate());
    }

}
