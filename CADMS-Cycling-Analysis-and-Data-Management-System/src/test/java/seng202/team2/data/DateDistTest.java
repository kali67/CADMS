package seng202.team2.data;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by jmo146 on 26/09/17.
 */
public class DateDistTest {

    @Test
    public void testCompareToAfter(){
        DateDist dateDist = new DateDist(LocalDate.now(), 1.5);
        assertEquals(1, dateDist.compareTo(new DateDist(LocalDate.now().minusDays(1), 100)));
    }

    @Test
    public void testCompareToBefore(){
        DateDist dateDist = new DateDist(LocalDate.now(), 1.5);
        assertEquals(-1, dateDist.compareTo(new DateDist(LocalDate.now().plusDays(1), 100)));
    }
    @Test
    public void testCompareToSame(){
        DateDist dateDist = new DateDist(LocalDate.now(), 1.5);
        assertEquals(0, dateDist.compareTo(new DateDist(LocalDate.now(), 100)));
    }
}
