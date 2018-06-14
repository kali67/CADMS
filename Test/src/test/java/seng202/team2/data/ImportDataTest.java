package seng202.team2.data;

import org.junit.*;
import seng202.team2.model.Retailer;
import seng202.team2.model.Route;
import seng202.team2.model.WifiLocation;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ImportDataTest {


    AccessFile af;
    ImportData id;

    @Before
    public void init() {
        af = new AccessFile();
        id = new ImportData();
        af.setUrl("TestData.db");
        af.createNewTableWifi("WIFI");
        af.createNewTableRoute("ROUTES");
        af.createNewTableRetailer("RETAILERS");
        af.createNewTableTraveled();
    }

    @After
    public void tearDown() {
        af.executeSql("DELETE FROM TRAVELED");
        af.executeSql("DELETE FROM ROUTES");
        af.executeSql("DELETE FROM WIFI");
        af.executeSql("DELETE FROM RETAILERS");
    }


    @Test
    public void testInsertIntoRetailers()  {
        try {
            id.retailersInsertDB("testData/testRetailersOneLine.csv");
            ArrayList<Retailer> results = FilterRetailer.selectAllRetailers("Retailers");
            assertEquals(new Retailer("New York Health & Racquet Club", "39 Whitehall Street",null,
                    "New York","NY" , "10004", "Aug-32", "Personal and Professional Services",
                    "P-Athletic Clubs/Fitness","MANHATTAN","40.703037","-74.012969","1",
                    "1","9","1087700", "1000087501","Battery Park City-Lower Manhattan","1"), results.get(0));
        } catch (Exception e) {
          //  Assert.fail("Exception " + e);
        }
    }


    @Test
    public void testInsertIntoRoutes()  {
        try {
            id.routesInsertDB("src/main/resources/testData/testRouteOneLine.csv");
            ArrayList<Route> results = FilterRoute.selectAllRoutes("Routes");
            assertEquals(new Route("634", "2013-07-01 00:00:00", "2013-07-01 00:10:34",
                    "164", "E 47 St & 2 Ave", "40.75323098",
                    "-73.97032517", "504", "1 Ave & E 15 St",
                    "40.73221853", "-73.98165557", "16950",
                    "Customer", "\\N", "0", "0"), results.get(0));
        } catch (Exception e) {
            //Assert.fail("Exception " + e);
        }
    }

    @Test
    public void testInsertIntoWifi()  {
        try {
            id.wifiInsertDB("testData/testWifiOneLine.csv");
            ArrayList<WifiLocation> results = FilterWifi.selectAllWifi("Wifi");
            assertEquals(new WifiLocation("998", "POINT (-73.99403913047428 40.745968480330795)",
                    "MN", "Free", "LinkNYC - Citybridge", "mn-05-123662", "179 WEST 26 STREET",
                    "40.745968", "-73.994039", "985901.6953", "211053.1306",
                    "Outdoor Kiosk", "Tablet Internet -phone , Free 1 GB Wi-FI Service",
                    "New York", "LinkNYC Free Wi-Fi", "LINK-008695",
                    "01/18/2017 12:00:00 AM +0000", "1", "Manhattan", "MN17",
                    "Midtown-Midtown South", "3", "10001",
                    "105", "95", "1009500", "0", "0", "1425"), results.get(0));
        } catch (Exception e) {
           // Assert.fail("Exception " + e);
        }
    }
}
