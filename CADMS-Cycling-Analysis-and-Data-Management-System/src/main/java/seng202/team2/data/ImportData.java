package seng202.team2.data;

import com.opencsv.CSVReader;
import seng202.team2.controller.SearchPageController;
import seng202.team2.controller.SettingsPageController;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Imports data from a csv file into a table chosen by the user
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class ImportData {

    private static AccessFile accessFile = new AccessFile();

    /**
     * Adds the retailer data file to the database
     * @param reader Data in a csv format to insert into the database
     * @param tableName the name of the table to add the data to
     * @throws Exception throws exception if it fails to insert into database
     */
    private static void insertIntoRetailers(CSVReader reader, String tableName) throws Exception {
        AccessFile af = new AccessFile();
        Connection conn = AccessFile.connect();
        try {
            conn.setAutoCommit(false);
            String sqlString = "INSERT INTO " + tableName + "  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlString);
            int id = af.getMaxId(tableName);
            int count = 0;
            int batchSize = 1000;
            ArrayList<String[]> records = new ArrayList<>();
            List<String[]> rows = reader.readAll();
            if (!(rows.get(0).length == 18)) { //missing the id number
                throw new Exception();
            }
            for (int j=0; j<rows.size();j++) {
                records.add(new String[21]); // change this to get the number of columns in the retailers table
                for (int k=0; k<rows.get(j).length;k++) {
                    records.get(j)[k] = rows.get(j)[k];
                }
            }
            for (int i=1; i<records.size();i++) {
                pstmt.setString(1, records.get(i)[0]);
                pstmt.setString(2, records.get(i)[1]);
                pstmt.setString(3, records.get(i)[2]);
                pstmt.setString(4, records.get(i)[3]);
                pstmt.setString(5, records.get(i)[4]);
                pstmt.setString(6, records.get(i)[5]);
                pstmt.setString(7, records.get(i)[6]);
                pstmt.setString(8, records.get(i)[7]);
                pstmt.setString(9, records.get(i)[8]);
                pstmt.setString(10, records.get(i)[9]);
                pstmt.setString(11, records.get(i)[10]);
                pstmt.setString(12, records.get(i)[11]);
                pstmt.setString(13, records.get(i)[12]);
                pstmt.setString(14, records.get(i)[13]);
                pstmt.setString(15, records.get(i)[14]);
                pstmt.setString(16, records.get(i)[15]);
                pstmt.setString(17, records.get(i)[16]);
                pstmt.setString(18, records.get(i)[17]);
                pstmt.setString(19, Integer.toString(id));
                id++;
                pstmt.addBatch();
                if (count % batchSize == 0) {
                    pstmt.executeBatch();
                }
                count++;
            }
            pstmt.executeBatch();
            conn.commit();
            accessFile.deleteDuplicateRecordsRetailers(tableName);
            AccessFile.disconnect(conn);
        } catch (IOException e) {
            System.out.println("File could not be found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Adds the bike route data file to the database
     * @param reader Data in a csv format to insert into the database
     * @param tableName the name of the table to add the data to
     * @throws Exception throws exception if it fails to insert into database
     */
    private static void insertIntoRoutes(CSVReader reader, String tableName) throws Exception {
        AccessFile af = new AccessFile();
        Connection conn2 = AccessFile.connect();
        try {
            conn2.setAutoCommit(false);
            String sqlString = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn2.prepareStatement(sqlString);
            int id = af.getMaxId(tableName);
            int count2 = 0;
            int batchSize = 100000;
            ArrayList<String[]> records = new ArrayList<>();
            List<String[]> rows = reader.readAll();
            if (!(rows.get(0).length == 15)) { //before adding in the id
                throw new Exception();
            }
            for (int j=0; j<rows.size();j++) {
                records.add(new String[21]); // change this to get the number of columns in the retailers table
                for (int k=0; k<rows.get(j).length;k++) {
                    records.get(j)[k] = rows.get(j)[k];
                }
            }
            for (int i=1; i<records.size();i++) {
                pstmt.setString(1, records.get(i)[0].replace("\"",""));
                pstmt.setString(2, records.get(i)[1]);
                pstmt.setString(3, records.get(i)[2]);
                pstmt.setString(4, records.get(i)[3]);
                pstmt.setString(5, records.get(i)[4]);
                pstmt.setString(6, records.get(i)[5]);
                pstmt.setString(7, records.get(i)[6]);
                pstmt.setString(8, records.get(i)[7]);
                pstmt.setString(9, records.get(i)[8]);
                pstmt.setString(10, records.get(i)[9]);
                pstmt.setString(11, records.get(i)[10]);
                pstmt.setString(12, records.get(i)[11]);
                pstmt.setString(13, records.get(i)[12]);
                pstmt.setString(14, records.get(i)[13]);
                pstmt.setString(15, records.get(i)[14]);
                pstmt.setString(16,Integer.toString(id));
                id++;
                pstmt.addBatch();
                if (count2 % batchSize == 0) {
                    pstmt.executeBatch();
                }
                count2++;
            }
            pstmt.executeBatch();
            conn2.commit();
            accessFile.deleteDuplicateRecordsRoutes(tableName);
            AccessFile.disconnect(conn2);

        } catch (IOException e) {
            System.out.println("File could not be found!");
            AccessFile.disconnect(conn2);
        } catch (SQLException e) {
            e.printStackTrace();
            AccessFile.disconnect(conn2);
        } finally {
            AccessFile.disconnect(conn2);
        }


    }


    /**
     * Adds the wifi location data file to the database
     * @param reader Data in a csv format to insert into the database
     * @param tableName the name of the table to add the data to
     * @throws Exception throws exception if it fails to insert into database
     */
    private static void insertIntoWifi(CSVReader reader, String tableName) throws Exception {
        Connection conn2 = AccessFile.connect();
        try {
            conn2.setAutoCommit(false);
            String sqlString = "INSERT INTO " + tableName + "  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //29
            PreparedStatement pstmt = conn2.prepareStatement(sqlString);
            //int id = af.getMaxId("WIFI"); <- maybe add back in later
            //System.out.println(id);
            int count2 = 0;
            int batchSize = 1000;
            ArrayList<String[]> records = new ArrayList<>();
            List<String[]> rows = reader.readAll();
            if (!(rows.get(0).length == 29)) {
                throw new Exception();
            }
            for (int j=0; j<rows.size();j++) {
                records.add(new String[29]); // change this to get the number of columns in the retailers table
                for (int k=0; k<rows.get(j).length;k++) {
                    records.get(j)[k] = rows.get(j)[k];
                }
            }
            for (int i=1; i<records.size();i++) {
                pstmt.setString(1, records.get(i)[0]);
                pstmt.setString(2, records.get(i)[1]);
                pstmt.setString(3, records.get(i)[2]);
                pstmt.setString(4, records.get(i)[3]);
                pstmt.setString(5, records.get(i)[4]);
                pstmt.setString(6, records.get(i)[5]);
                pstmt.setString(7, records.get(i)[6]);
                pstmt.setString(8, records.get(i)[7]);
                pstmt.setString(9, records.get(i)[8]);
                pstmt.setString(10, records.get(i)[9]);
                pstmt.setString(11, records.get(i)[10]);
                pstmt.setString(12, records.get(i)[11]);
                pstmt.setString(13, records.get(i)[12]);
                pstmt.setString(14, records.get(i)[13]);
                pstmt.setString(15, records.get(i)[14]);
                pstmt.setString(16, records.get(i)[15]);
                pstmt.setString(17, records.get(i)[16]);
                pstmt.setString(18, records.get(i)[17]);
                pstmt.setString(19, records.get(i)[18]);
                pstmt.setString(20, records.get(i)[19]);
                pstmt.setString(21, records.get(i)[20]);
                pstmt.setString(22, records.get(i)[21]);
                pstmt.setString(23, records.get(i)[22]);
                pstmt.setString(24, records.get(i)[23]);
                pstmt.setString(25, records.get(i)[24]);
                pstmt.setString(26, records.get(i)[25]);
                pstmt.setString(27, records.get(i)[26]);
                pstmt.setString(28, records.get(i)[27]);
                pstmt.setString(29, records.get(i)[28]);

                pstmt.addBatch();
                if (count2 % batchSize == 0) {
                    pstmt.executeBatch();
                }

                count2++;
            }
            pstmt.executeBatch();
            conn2.commit();
            accessFile.deleteDuplicateRecordsWifi(tableName);
            AccessFile.disconnect(conn2);

        } catch (IOException e){
            System.out.println("File could not be found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts wifi from user into the database.
     * @param path The path to the file to insert.
     * @param table The name of the table to insert into.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void wifiInsertUser(String path, String table) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(path));
        insertIntoWifi(reader, table);

    }

    /**
     * Inserts wifi at start up into the database.
     * @param path The path to the file to insert.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void wifiInsertDB(String path) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader reader = new CSVReader(input);
        insertIntoWifi(reader, "Wifi");
    }

    /**
     * Inserts retailers from user into the database.
     * @param path The path to the file to insert.
     * @param table The name of the table to insert into.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void retailersInsertUser(String path, String table) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(path));
        insertIntoRetailers(reader, table);
    }

    /**
     * Inserts retailers at start up into the database.
     * @param path The path to the file to insert.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void retailersInsertDB(String path) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader reader = new CSVReader(input);
        insertIntoRetailers(reader, "Retailers");
    }

    /**
     * Inserts routes from user into the database.
     * @param path The path to the file to insert.
     * @param table The name of the table to insert into.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void routesInsertUser(String path, String table) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(path));
        insertIntoRoutes(reader, table);
    }

    /**
     * Inserts retailers at start up into the database.
     * @param path The path to the file to insert.
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void routesInsertDB(String path) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader reader = new CSVReader(input);
        insertIntoRoutes(reader, "Routes");
    }


    /**
     * Inserts into the TRAVELED table on start up
     * @throws Exception Throws an exception when it cannot insert into the database.
     */
    public void insertIntoTraveled() throws Exception {
        Connection conn2 = AccessFile.connect();
        try {
            conn2.setAutoCommit(false);
            String sqlString = "INSERT INTO TRAVELED VALUES(?,?)";
            PreparedStatement pstmt = conn2.prepareStatement(sqlString);
            int count2 = 0;
            int batchSize = 1000;
            ArrayList<String[]> records = new ArrayList<>();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testData/TestTraveledData.csv");
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader reader =  new CSVReader(input);
            List<String[]> rows = reader.readAll();
            if (!(rows.get(0).length == 2)) { //before adding in the id
                throw new Exception();
            }
            for (int j=0; j<rows.size();j++) {
                records.add(new String[21]); // change this to get the number of columns in the retailers table
                for (int k=0; k<rows.get(j).length;k++) {
                    records.get(j)[k] = rows.get(j)[k];
                }
            }
            for (int i=1; i<records.size();i++) {
                pstmt.setString(1, records.get(i)[0].replace("\"",""));
                pstmt.setString(2, records.get(i)[1]);

                pstmt.addBatch();
                if (count2 % batchSize == 0) {
                    pstmt.executeBatch();
                }
                count2++;
            }
            pstmt.executeBatch();
            conn2.commit();
            AccessFile.disconnect(conn2);
        } catch (IOException e) {
            System.out.println("File could not be found!");
            AccessFile.disconnect(conn2);
        } catch (SQLException e) {
            e.printStackTrace();
            AccessFile.disconnect(conn2);
        } finally {
            AccessFile.disconnect(conn2);
        }
    }
}
