package seng202.team2.data;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HaversineCalcTest {

    @Test
    public void testDistance() {
        double result = HaversineCalc.distance(40.75323098, -73.97032517, 40.73221853, -73.98165557);
        assertEquals(2.52, result, 0.2);
    }

    @Test
    public void testHaversin(){
        assertEquals(0.97125, HaversineCalc.haversin(40.5), 0.00005);
    }
}
