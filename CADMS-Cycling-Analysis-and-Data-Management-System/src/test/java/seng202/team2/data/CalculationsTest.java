package seng202.team2.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalculationsTest {

    AccessFile accessFile;
    LocalDate date;
    Calculations calculations;

    @Before
    public void initialise() {

        accessFile = new AccessFile();
        accessFile.setUrl("TestData.db");
        accessFile.createNewTableWifi("WIFI");
        accessFile.createNewTableRoute("ROUTES");
        accessFile.createNewTableRetailer("RETAILERS");
        accessFile.createNewTableTraveled();
        calculations = new Calculations();
    }

    @After
    public void tearDown(){
        accessFile = new AccessFile();
    }

    @After
    public void deleteAllFromTraveledTable() {
        accessFile.executeSql("DELETE FROM TRAVELED");
    }

    @Test
    public void testCalculateCarbon365Days365ValuesInArrayAllValuesAfterOneYearAgo() {
        date = LocalDate.now().minusDays(365);
        int dist = 2;
        for (int i = 0; i < 365; i++) {
            accessFile.insertIntoTraveled(date.toString(), Integer.toString(dist));
            date = date.plusDays(1);
            if (dist == 2) {
                dist = 3;
            } else {
                dist = 2;
            }
        }
        double[] expected = new double[365];
        expected[0] = 2;
        for (int i = 1; i < 365; i++) {
            if (i % 2 == 0) {
                expected[i] = 2;
            } else {
                expected[i] = 3;
            }
        }
        for (int i = 1; i < 365; i++) {
            expected[i] = expected[i] + expected[i - 1];
        }
        for (int i = 0; i < 365; i++) {
            expected[i] = expected[i] * 0.25528;
        }
        assertArrayEquals(expected, calculations.calculateCarbon(365), 0.01);
    }

    @Test
    public void testCalculateCarbon31Days365ValuesInArrayAllValuesAfterOneYearAgo() {
        date = LocalDate.now().minusDays(365);
        int dist = 2;
        for (int i = 0; i < 365; i++) {
            accessFile.insertIntoTraveled(date.toString(), Integer.toString(dist));
            date = date.plusDays(1);
            if (dist == 2) {
                dist = 3;
            } else {
                dist = 2;
            }
        }
        double[] expected = new double[31];
        expected[0] = 2;
        for (int i = 1; i < 31; i++) {
            if (i % 2 == 0) {
                expected[i] = 2;
            } else {
                expected[i] = 3;
            }
        }
        for (int i = 1; i < 31; i++) {
            expected[i] = expected[i] + expected[i - 1];
        }
        for (int i = 0; i < 31; i++) {
            expected[i] = expected[i] * 0.25528;
        }
        assertArrayEquals(expected, calculations.calculateCarbon(31), 0.01);
    }

    @Test
    public void testCalculateCarbon7Days365ValuesInArrayAllValuesAfterOneYearAgo() {
        date = LocalDate.now().minusDays(365);
        int dist = 2;
        for (int i = 0; i < 365; i++) {
            accessFile.insertIntoTraveled(date.toString(), Integer.toString(dist));
            date = date.plusDays(1);
            if (dist == 2) {
                dist = 3;
            } else {
                dist = 2;
            }
        }
        double[] expected = new double[7];
        expected[0] = 2;
        for (int i = 1; i < 7; i++) {
            if (i % 2 == 0) {
                expected[i] = 2;
            } else {
                expected[i] = 3;
            }
        }
        for (int i = 1; i < 7; i++) {
            expected[i] = expected[i] + expected[i - 1];
        }
        for (int i = 0; i < 7; i++) {
            expected[i] = expected[i] * 0.25528;
        }
        assertArrayEquals(expected, calculations.calculateCarbon(7), 0.01);
    }

    @Test
    public void testCalculateCarbon365Days150ValuesInArrayAllValuesAfterOneYearAgo() {
        date = LocalDate.now().minusDays(365);
        int dist = 2;
        for (int i = 0; i < 150; i++) {
            accessFile.insertIntoTraveled(date.toString(), Integer.toString(dist));
            date = date.plusDays(1);
            if (dist == 2) {
                dist = 3;
            } else {
                dist = 2;
            }
        }
        double[] expected = new double[365];
        expected[0] = 2;
        for (int i = 1; i < 150; i++) {
            if (i % 2 == 0) {
                expected[i] = 2;
            } else {
                expected[i] = 3;
            }
        }
        for (int i = 150; i < 365; i++) {
            expected[i] = 0;
        }
        for (int i = 1; i < 365; i++) {
            expected[i] = expected[i] + expected[i - 1];
        }
        for (int i = 0; i < 365; i++) {
            expected[i] = expected[i] * 0.25528;
        }
        assertArrayEquals(expected, calculations.calculateCarbon(365), 0.01);
    }
}