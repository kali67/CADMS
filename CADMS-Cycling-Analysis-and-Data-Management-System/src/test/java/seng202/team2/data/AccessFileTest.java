package seng202.team2.data;


import org.junit.*;

import seng202.team2.model.Retailer;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.Assert.*;
import seng202.team2.model.Route;
import seng202.team2.model.WifiLocation;

public class AccessFileTest {
    AccessFile af;
    FilterRetailer fr;
    FilterRoute fRoute;
    FilterWifi fWifi;
    ArrayList<Retailer> expectedRetailerArrayList;
    ArrayList<Route> expectedRouteArrayList;
    ArrayList<WifiLocation> expectedWifiArrayList;
    QueryData qd;



    @Before
    public void initialize() {
        af = new AccessFile(); //create new accessFile dataBase
        fr = new FilterRetailer();
        fRoute = new FilterRoute();
        fWifi = new FilterWifi();
        expectedRetailerArrayList = new ArrayList<>();
        expectedRouteArrayList = new ArrayList<>();
        expectedWifiArrayList = new ArrayList<>();
        af.setUrl("TestData.db");
        qd = new QueryData();
        AccessFile accessFile = new AccessFile();
        accessFile.dropTable("WIFI");
        accessFile.dropTable("ROUTES");
        accessFile.dropTable("RETAILERS");
        accessFile.dropTable("TRAVELED");
        accessFile.createNewTableWifi("WIFI");
        accessFile.createNewTableRoute("ROUTES");
        accessFile.createNewTableRetailer("RETAILERS");
        accessFile.createNewTableTraveled();
    }


    @After
    public void tearDown() {
        AccessFile accessFile = new AccessFile();
    }


    @Test
    public void testInsertIntoRetailers() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("ShopName", "shopAddress", "shopPrimary", "testCity", latLon, "RETAILERS");
        expectedRetailerArrayList.add(new Retailer("ShopName", "shopAddress", null, "testCity",
                null, null, null, "shopPrimary", null, null, "40.703037", "-74.012969",
                null, null, null, null, null, null, "0"));
        assertEquals(expectedRetailerArrayList, fr.selectAllRetailers("RETAILERS"));
        af.delete("RETAILERS", 1);
        expectedRetailerArrayList.remove(0);
    }


    @Test
    public void testInsertIntoRoutes() {
        String[] latlong = {"40.75323098", "-73.97032517","40.73221853", "-73.98165557"};
        af.insertIntoRoutes("1", "E 47 St & 2 Ave", "1 Ave & E 15 St", "UserType", latlong, "ROUTES");
        expectedRouteArrayList.add(new Route("1", null, null, null, "E 47 St & 2 Ave",
                "40.75323098", "-73.97032517",null, "1 Ave & E 15 St", "40.73221853", "-73.98165557", null,
                "UserType", null, null, "0"));
        assertEquals(expectedRouteArrayList, fRoute.selectAllRoutes("ROUTES"));
        af.delete("Routes", 1); //delete the inserted route
        expectedRouteArrayList.remove(0);

    }


    @Test
    public void testInsertIntoWifi() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoWifi("Provider", "Type", "Boro", "Address", latLon, "WIFI");
        expectedWifiArrayList.add(new WifiLocation(null, null, "Boro", "Type", "Provider", null, "Address",
                "40.703037", "-74.012969", null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, "0"));
        assertEquals(expectedWifiArrayList, fWifi.selectAllWifi("WIFI"));
        af.delete("WIFI", 1);
        expectedWifiArrayList.remove(0);
    }


    @Test
    public void testDeleteFromRetailers() {
        String[] latlon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("ShopName", "shopAddress", "shopPrimary", "tetsCity", latlon, "RETAILERS");
        expectedRetailerArrayList.add(new Retailer("ShopName", "shopAddress", null, "testCity",
                null, null, null, "shopPrimary", null, null, "40.703037", "-74.012969",
                null, null, null, null, null, null, "0"));
        af.delete("RETAILERS", 1);
        expectedRetailerArrayList.remove(0);
        assertEquals(expectedRetailerArrayList, fr.selectAllRetailers("Retailers"));

    }


    @Test
    public void testDeleteFromRoutes() {
        String[] latlong = {"40.75323098", "-73.97032517","40.73221853", "-73.98165557"};
        af.insertIntoRoutes("1", "E 47 St & 2 Ave", "1 Ave & E 15 St", "UserType", latlong, "ROUTES");
        expectedRouteArrayList.add(new Route("1", null, null, null, "E 47 St & 2 Ave", "40.75323098",
                "-73.97032517", null, "1 Ave & E 15 St", "40.73221853", "-73.98165557", null,
                "UserType", null, null, "0"));
        af.delete("Routes", 1); //delete the inserted route
        expectedRouteArrayList.remove(0);
        assertTrue(expectedRouteArrayList.equals(fRoute.selectAllRoutes("Routes")));

    }


    @Test
    public void testDeleteFromWifi() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoWifi("Provider", "Type", "Boro", "Address", latLon, "WIFI");
        expectedWifiArrayList.add(new WifiLocation(null, null, "Boro", "Type", "Provider", null, "Address",
                "40.703037", "-74.012969", null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, "0"));
        af.delete("WIFI", 1);
        expectedWifiArrayList.remove(0);
        assertEquals(expectedWifiArrayList, fWifi.selectAllWifi("WIFI"));

    }


    @Test
    public void testGetColumnCountOfTableNameRetailers() {
        int i = af.getColumnCountOfTableName("Retailers");
        assertEquals(19, i);
    }

    @Test
    public void testGetColumnCountOfTableNameRoute() {
        int i = af.getColumnCountOfTableName("Routes");
        assertEquals(16, i);
    }

    @Test
    public void testGetColumnCountOfTableNameWifi() {
        int i = af.getColumnCountOfTableName("WIFI");
        assertEquals(29, i);
    }


    @Test
    public void testGetColumnCountOfTableNameFail() {
        assertEquals(0, af.getColumnCountOfTableName("non-existantTable"));
    }


    @Test
    public void testGetMaxIdReatilersEmptyDatabase() {
        int i = af.getMaxId("Retailers");  //no such column for wifi table. Will need to create new method for it!
        assertEquals(0, i);

    }

    @Test
    public void testGetMaxIdRetailersFilledDatabase() {
        String[] latlon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("ShopName", "shopAddress", "shopPrimary", "testCity", latlon, "RETAILERS"); //insert data
        int i = af.getMaxId("Retailers");
        assertEquals(1, i);
        af.delete("Retailers", 1);

    }

    @Test
    public void testGetMaxIdWifiEmptyDatabase() {
        int i = af.getMaxId("Wifi");
        assertEquals(0, i);

    }

    @Test
    public void testGetMaxIdWifiFilledDatabase() {
        String[] latlon = {"40.703037", "-74.012969"};
        af.insertIntoWifi("Provider", "Type", "Boro", "Address", latlon,"WIFI");
        int i = af.getMaxId("Wifi");
        assertEquals(1, i);
        af.delete("Wifi", 1);
    }

    @Test
    public void testGetMaxIdRouteEmptyDatabase() {
        int i = af.getMaxId("Routes");
        assertEquals(0, i);

    }

    @Test
    public void testGetMaxIdRouteFilledDatabase() {
        String[] latlong = {"40.75323098", "-73.97032517","40.73221853", "-73.98165557"};
        af.insertIntoRoutes("1", "E 47 St & 2 Ave", "1 Ave & E 15 St", "UserType",latlong, "ROUTES");
        int i = af.getMaxId("Routes");
        assertEquals(1, i);
        af.delete("Routes", 1);
    }


    @Test
    public void testGetDataTypeWifiTable() {
        String dataType = af.getDataType("Wifi");
        assertEquals(dataType, "wifi");
    }

    @Test
    public void testGetDataTypeRetailersTable() {
        String dataType = af.getDataType("retailers");
        assertEquals("retailers", dataType);
    }

    @Test
    public void testGetDataTypeRoutesTable() {
        String dataType = af.getDataType("routes");
        assertEquals("route", dataType);
    }

    @Test
    public void testGetTableNamesDefaultTables() {
        ArrayList<String> results = af.getTableNames();
        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("WIFI");
        expectedResults.add("ROUTES");
        expectedResults.add("RETAILERS");
        expectedResults.add("TRAVELED");
        assertEquals(expectedResults, results);

    }

    @Test
    public void testGetTableNamesAddingTables() {
        af.createNewTableRetailer("Test");
        ArrayList<String> results = af.getTableNames();
        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("WIFI");
        expectedResults.add("ROUTES");
        expectedResults.add("RETAILERS");
        expectedResults.add("TRAVELED");
        expectedResults.add("Test");
        assertEquals(expectedResults, results);
        af.dropTable("Test");

    }




    @Test
    public void testDropTable() {
        af.createNewTableRetailer("TestRetailer");
        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("WIFI");
        expectedResults.add("ROUTES");
        expectedResults.add("RETAILERS");
        expectedResults.add("TRAVELED");
        af.dropTable("TestRetailer");
        ArrayList<String> results = af.getTableNames();
        assertEquals(expectedResults, results);

    }


//    @Test
//    public void testConnectionFailure() {
//        af.setUrl("someRandomDataBastThatDoesn'tExists");
//        assertEquals(null, af.connect());
//    }

    @Test
    public void testCreateNewTableWifi() {
        af.createNewTableWifi("NewTableWifi");
        assertEquals(af.getTableNames().get(4), "NewTableWifi");
        af.dropTable("NewTableWifi");
    }

    @Test
    public void testCreateNewTableRetailer() {
        af.createNewTableRetailer("NewTableRetail");
        assertEquals(af.getTableNames().get(4), "NewTableRetail");
        af.dropTable("NewTableRetail");

    }

    @Test
    public void testCreateNewTableRoute() {
        af.createNewTableRoute("NewTableRoute");
        assertEquals(af.getTableNames().get(4), "NewTableRoute");
        af.dropTable("NewTableRoute");

    }


    @Test
    public void testUpdateRetailer() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("testname", "testaddress", "primary", "testCity",latLon, "Retailers");
        af.update("retailers", "1", "CnAdrPrf_Addrline1", "test");
        expectedRetailerArrayList.add(new Retailer("testname", "test", null, "testCity",
                null, null, null, "primary", null, null, null, null,
                null, null, null, null, null, null, "1"));
        assertEquals(expectedRetailerArrayList, fr.selectAllRetailers("Retailers"));
        af.delete("retailers", 1);
        expectedRetailerArrayList.remove(0);
    }


    @Test
    public void testUpdateWifi() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoWifi("Provider", "Type", "Boro", "Address", latLon, "Wifi");

        af.update("wifi", "1", "'Provider'", "testProvider");

        expectedWifiArrayList.add(new WifiLocation(null, null, "Boro", "Type", "testProvider", null, "Address",
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, "1"));

        assertEquals(expectedWifiArrayList, fWifi.selectAllWifi("Wifi"));
        af.delete("wifi", 1);
    }

    @Test
    public void testUpdateRoute() {
        String[] latlong = {"40.75323098", "-73.97032517","40.73221853", "-73.98165557"};
        af.insertIntoRoutes("10", "E 47 St & 2 Ave", "1 Ave & E 15 St", "UserType", latlong, "routes");
        af.update("routes", "1", "'Start Station Name'", "test");
        expectedRouteArrayList.add(new Route("10", null, null, null, "test", "40.75323098",
                "-73.97032517", null, "1 Ave & E 15 St", "40.73221853", "-73.98165557", null,
                "UserType", null, null, "0"));
        assertTrue(expectedRouteArrayList.equals(fRoute.selectAllRoutes("routes")));
        af.delete("routes", 1);
    }

    @Test
    public void testInsertIntoTraveled(){
        af.insertIntoTraveled("2017-09-01", "1.5");
        String[][] expected = new String[365][2];
        expected[0] = new String[]{"2017-09-01", "1.5"};
        for (int i = 1; i < 365; i++){
            expected[i] = new String[]{null, null};
        }
        assertArrayEquals(expected, qd.selectAllTraveled());
        af.executeSql("Delete From Traveled");
    }

    @Test
    public void testDeleteFromTraveled(){
        af.insertIntoTraveled("2017-09-01", "1.5");
        af.deleteFromTraveled("2017-09-01");
        String[][] expected = new String[365][2];
        for (int i = 0; i < 365; i++){
            expected[i] = new String[]{null, null};
        }
        assertArrayEquals(expected, qd.selectAllTraveled());
    }

    @Test
    public void testUpdateTraveled(){
        LocalDate localDate = LocalDate.now().minusDays(400);
        String dist;
        for (int i = 0; i < 365; i ++) {
            if (i % 2 == 0) {
                dist = "2";
            } else {
                dist = "4";
            }
            af.insertIntoTraveled(localDate.toString(), String.valueOf(dist));
            localDate = localDate.plusDays(1);
        }
        af.updateTraveled();
        localDate = LocalDate.now().minusDays(364);
        String[][] expected = new String[365][2];
        for (int i = 0; i < 365; i ++) {
            if (i < 329) {
                if (i % 2 == 0) {
                    dist = "2";
                } else {
                    dist = "4";
                }
            } else {
                dist = "0";
            }
            expected[i][0] = localDate.toString();
            expected[i][1] = String.valueOf(dist);
            localDate = localDate.plusDays(1);
        }
        assertArrayEquals(expected, QueryData.selectAllTraveled());
    }

    @Test
    public void testUpdateSingleTraveled(){
        LocalDate localDate = LocalDate.now().minusDays(364);
        String dist;
        for (int i = 0; i < 365; i ++) {
            if (i % 2 == 0) {
                dist = "2";
            } else {
                dist = "4";
            }
            af.insertIntoTraveled(localDate.toString(), String.valueOf(dist));
            localDate = localDate.plusDays(1);
        }
        localDate = LocalDate.now().minusDays(364);
        String[][] expected = new String[365][2];
        for (int i = 0; i < 365; i ++) {
            if (i % 2 == 0) {
                dist = "2";
            } else {
                dist = "4";
            }
            expected[i][0] = localDate.toString();
            expected[i][1] = dist;
            localDate = localDate.plusDays(1);
        }
        expected[364][1] = "20";
        af.updateSingleTraveled(localDate.minusDays(1).toString(), "20");
        assertArrayEquals(expected, QueryData.selectAllTraveled());
    }

    @Test
    public void testAddDistToTraveled(){

        LocalDate localDate = LocalDate.now().minusDays(364);
        String dist;
        for (int i = 0; i < 365; i ++) {
            if (i % 2 == 0) {
                dist = "2";
            } else {
                dist = "4";
            }
            af.insertIntoTraveled(localDate.toString(), String.valueOf(dist));
            localDate = localDate.plusDays(1);
        }
        localDate = LocalDate.now().minusDays(364);
        String[][] expected = new String[365][2];
        for (int i = 0; i < 365; i ++) {
            if (i % 2 == 0) {
                dist = "2";
            } else {
                dist = "4";
            }
            expected[i][0] = localDate.toString();
            expected[i][1] = dist;
            localDate = localDate.plusDays(1);
        }
        expected[364][1] = "20";
        af.addDistToTraveled(18);
        assertArrayEquals(expected, QueryData.selectAllTraveled());
    }
}