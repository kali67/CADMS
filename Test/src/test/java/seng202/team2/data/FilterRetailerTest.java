package seng202.team2.data;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team2.model.Retailer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

public class FilterRetailerTest {
    AccessFile af;
    ImportData id;

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

    }



    @Test
    public void testSearchRetailerXByY() {
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("testShopName", "60 Pearl Street", "testPrimary", "testCity",latLon, "Retailers");
        ArrayList<Retailer> result = FilterRetailer.searchRetailerXByY("'60 Pearl Street'", "''","'Any'", "Retailers" );
        assertEquals(result.get(0), new Retailer("testShopName", "60 Pearl Street",null, "testCity",
                null, null, null, "testPrimary",null,null,null,null,
                null,null,null, null,null,null,"1"));
        af.delete("Retailers", 1);

    }
    @Test
    public void testSelectAllRetailers(){
        String[] latLon = {"40.703037", "-74.012969"};
        af.insertIntoRetailers("testShopName", "60 Pearl Street", "testPrimary", "testCity", latLon, "Retailers");
        ArrayList<Retailer> result = FilterRetailer.selectAllRetailers("Retailers" );
        assertEquals(result.get(0), new Retailer("testShopName", "60 Pearl Street",null, "testCity",
                null, null, null, "testPrimary",null,null,null,null,
                null,null,null, null,null,null,"1"));
        af.delete("Retailers", 1);
    }


}

