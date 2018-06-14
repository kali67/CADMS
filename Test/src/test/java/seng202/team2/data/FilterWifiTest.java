package seng202.team2.data;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team2.model.WifiLocation;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FilterWifiTest {

    AccessFile af;
    ImportData id;
    FilterRoute fRoute;

    @Before
    public void init(){
        af = new AccessFile();
        af.setUrl("TestData.db");
        af.createNewTableWifi("WIFI");
        af.createNewTableRoute("ROUTES");
        af.createNewTableRetailer("RETAILERS");
        af.createNewTableTraveled();
        id = new ImportData();
    }

    @After
    public void tearDown() {
        af.executeSql("DELETE FROM TRAVELED");
        af.executeSql("DELETE FROM ROUTES");
        af.executeSql("DELETE FROM WIFI");
        af.executeSql("DELETE FROM RETAILERS");
    }

    @Test
    public void testSearchWifiXByY() {
        try {
            id.wifiInsertDB("testData/testWifiOneLine.csv");
            ArrayList<WifiLocation> results = FilterWifi.searchWifiXByY("'LinkNYC - Citybridge'", "'Free'", "'MN'","Wifi");
            ArrayList<WifiLocation> expectedResults = new ArrayList<>();
            expectedResults.add(new WifiLocation("998", "POINT (-73.99403913047428 40.745968480330795)",
                    "MN", "Free", "LinkNYC - Citybridge", "mn-05-123662", "179 WEST 26 STREET",
                    "40.745968", "-73.994039", "985901.6953", "211053.1306",
                    "Outdoor Kiosk", "Tablet Internet -phone , Free 1 GB Wi-FI Service",
                    "New York", "LinkNYC Free Wi-Fi", "LINK-008695",
                    "01/18/2017 12:00:00 AM +0000", "1", "Manhattan", "MN17",
                    "Midtown-Midtown South", "3", "10001",
                    "105", "95", "1009500", "0", "0", "1425"));
            assertEquals(expectedResults, results);
        } catch (Exception e) {
            // Assert.fail("Exception " + e);
        }
    }

    @Test
    public void testSelectAllWifi() {

    }

    @Test
    public void testFindClosestWifiToRetail() {

    }

}
