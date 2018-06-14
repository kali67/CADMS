package seng202.team2.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team2.model.Retailer;
import seng202.team2.model.Route;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

public class FilterRouteTest {

    private AccessFile af;
    private ImportData id;

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
    public void tearDown(){
        af.executeSql("DELETE FROM TRAVELED");
        af.executeSql("DELETE FROM ROUTES");
        af.executeSql("DELETE FROM WIFI");
        af.executeSql("DELETE FROM RETAILERS");

    }


    @Test
    public void testSelectAllRoutes() {
        try {
            id.routesInsertDB("src/main/resources/testData/testRouteOneLine.csv");
            assertEquals(new Route("634", "2013-07-01 00:00:00", "2013-07-01 00:10:34",
                    "164", "E 47 St & 2 Ave", "40.75323098",
                    "-73.97032517", "504", "1 Ave & E 15 St",
                    "40.73221853", "-73.98165557", "16950",
                    "Customer", "\\N", "0", "0"), FilterRoute.selectAllRoutes("ROUTES").get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSearchRouteXByY(){
        try {
            id.routesInsertDB("src/main/resources/testData/testRouteOneLine.csv");
            ArrayList<Route> results = FilterRoute.searchRouteXByY("164","504","Routes");
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
    public void testGetRouteDistance() {
        assertEquals(111.19 ,FilterRoute.getRouteDistance("70","40","71","40"), 0.01);
    }


}
