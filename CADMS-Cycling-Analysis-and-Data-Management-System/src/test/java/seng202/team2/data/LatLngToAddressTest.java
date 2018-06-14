package seng202.team2.data;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LatLngToAddressTest {

//These tests runs on lab machines but will not run on gitrunner as it requires internet access
    @Test
    public void testGetAddress() {
        LatLngToAddress la = new LatLngToAddress();
        assertEquals("39 Whitehall St, New York, NY 10004, USA", la.getAddress("40.703037", "-74.012969"));
    }

    @Test
    public void testGetAddressElseBranch() {
        LatLngToAddress la = new LatLngToAddress();
        String result = la.getAddress("400", "-740");
        assertEquals(null, result);
    }
}
