package seng202.team2.data;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class QueryDataTest {

    AccessFile accessFile;
    QueryData queryData;

    @Before
    public void initialise(){
        accessFile = new AccessFile();
        queryData = new QueryData();
        accessFile.setUrl("TestData.db");
        accessFile.createNewTableWifi("Wifi");
        accessFile.createNewTableRoute("Routes");
        accessFile.createNewTableRetailer("Retailers");
        accessFile.createNewTableTraveled();
    }

    @Test
    public void testBuildWhereClause() {
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("Provider = NYCHA");
        conditions.add("Type = Free");
        conditions.add("Boro = BK");
        String whereClause = queryData.buildWhereClauseString(conditions);
        assertEquals(whereClause, "WHERE Provider = NYCHA AND Type = Free AND Boro = BK");
    }

    @Test
    public void testSelectAllTraveled() {
        accessFile.insertIntoTraveled("2017-09-01", "3.5");
        String[][] expected = new String[365][2];
        expected[0] = new String[]{"2017-09-01", "3.5"};
        for (int i = 1; i < 365; i++) {
            expected[i] = new String[]{null, null};
        }
        assertArrayEquals(expected, queryData.selectAllTraveled());
        accessFile.executeSql("Delete From Traveled");
    }

    @Test
    public void testSelectAllTraveledException() {
        accessFile.setUrl("jdbc:sqlite:ksdiu.db");
        accessFile.insertIntoTraveled("2017-09-01", "3.5");
        String[][] expected = new String[365][2];
        for (int i = 0; i < 365; i++) {
            expected[i] = new String[]{null, null};
        }
        assertArrayEquals(expected, queryData.selectAllTraveled());
        accessFile.executeSql("Delete From Traveled");
    }

}