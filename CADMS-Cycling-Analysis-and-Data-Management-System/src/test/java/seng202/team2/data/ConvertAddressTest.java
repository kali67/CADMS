package seng202.team2.data;


import org.junit.Test;
import static org.junit.Assert.*;

public class ConvertAddressTest {

//These tests runs on lab machines but will not run on gitrunner as it requires internet access
    @Test
    public void testGetLatLongPositionsWorking() {
        String address = "104 Wooster St, New York City, New York";
        String[] result = ConvertAddress.getLatLongPositions(address);
        double[] doubleResults = new double[2];
        for (int i = 0; i < 2; i++){
            doubleResults[i] = Double.valueOf(result[i]);
        }
        assertArrayEquals(new double[]{40.724569,-74.000705}, doubleResults, 0.00005);
    }

    @Test
    public void testGetLatLongPositionsCallsElse() {
        String address = "er vgstaeb hntew neay74 hn634 b34h6 n67be34 6h n";
        String[] result = ConvertAddress.getLatLongPositions(address);
        assertArrayEquals(null, result);
    }
}