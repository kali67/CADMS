package seng202.team2.data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Provides methods to connect, extract and insert data from the database
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class AccessFile {

    private static String url = "ProjectData.db";

    /**
     * Tries to connect to the database specified. An error message is printed if the connection fails
     * @return Connection, the connection object with a connection to the database.
     */
    static Connection connect() {
        Connection conn = null;  //The connection object to be returned
        try {
            String path = System.getProperty("user.home");
            String path2 = "jdbc:sqlite:" + path + "/" +  url;
            conn = DriverManager.getConnection(path2);  //Attempts to establish a connection to the given database URL.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return conn;
    }


    /**
     * Retrieves all table names from the connected database
     * @return ArrayList<String>, an ArrayList of table names or null if it cannot connect to the database
     */
    public ArrayList<String> getTableNames(){
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";  //SQL query to be sent to the database
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);  //Uses the statement object to send the SQL query to the database
            while (rs.next()) {  //Loops until there are no more table values
                tableNames.add(rs.getString(1));
            }
            disconnect(conn);
            return tableNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Removes the given table from the database
     * @param tablename the table to be removed
     */
    public void dropTable(String tablename) {
        String sql = "DROP TABLE " + tablename;  //SQL query to drop the given table
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            disconnect(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Compares how many columns are in the give table with how many columns are in each known table to return the
     * correct data type of the given table
     * @param tableName the name of the given table
     * @return String, the data type of the given table
     */
    public String getDataType(String tableName) {
        int columnCount = getColumnCountOfTableName(tableName);
        if (columnCount == 16) {
            return "route";
        } else if (columnCount == 29) {
            return "wifi";
        } else {
            return "retailers";
        }
    }


    /**
     * Creates a new wifi table in the database using the create table SQL query statement specified for wifi
     * @param newTableName the name of the new table to be created
     */
    public void createNewTableWifi(String newTableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + "( OBJECTID text , the_geom text , BORO text , TYPE text , PROVIDER text , NAME text ,LOCATION text ,LAT text ," +
                "LON text , X text , Y text , LOCATION_T text , REMARKS text , CITY text , SSID text , SOURCEID text , " +
                "ACTIVATED text , BOROCODE text , BOROName text , NTACODE text , NTANAME text , COUNDIST text , POSTCODE text , BOROCD text , " +
                "CT2010 text , BOROCT2010 text , BIN text , BBL text , DOITT_ID text );";

        executeSql(sql);
    }


    /**
     * Creates a new bike route table in the database using the create table SQL query statement specified for bike routes
     * @param newTableName the name of the new table to be created
     */
    public void createNewTableRoute(String newTableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + "( 'Trip Duration' text , 'Start Time' text , 'Stop Time' text , 'Start Station ID' text ," +
                " 'Start Station Name' text , 'Start Station Latitude' text ,'Start Station Longitude' text ,'End Station ID' text ," +
                "'End Station Name' text , 'End Station Latitude' text , 'End Station Longitude' text , 'Bike ID' text , 'User Type' text , 'Birth Year' text , 'Gender' text , IdNumber text)";

        executeSql(sql);
    }


    /**
     * Creates a new retailer table in the database using the create table SQL query statement specified for retailers
     * @param newTableName the name of the new table to be created
     */
    public void createNewTableRetailer(String newTableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + "( CnBio_Org_Name text , CnAdrPrf_Addrline1 text , CnAdrPrf_Addrline2 text , CnAdrPrf_City text , CnAdrPrf_State text , CnAdrPrf_ZIP text , 'Block-Lot' text , 'Primary' text ," +
                "Secondary text , Boro text , Lat text , Lon text , 'Community Board' text , 'Council District' text , 'Census Tract' text , BIN text , " +
                "BBL text , NTA text , IdNumber text);";

        executeSql(sql);
    }

    /**
     * Creates a new table "TRAVELED" if it does not already exist
     */
    public void createNewTableTraveled() {
        String sql = "CREATE TABLE IF NOT EXISTS TRAVELED( date text , distance integer)";

        executeSql(sql);
    }

    /**
     * Connects to the database and executes the given SQL query to update the database records
     * @param sql the SQL query to be sent to the database
     */
    void executeSql(String sql) {
        try {
            Connection conn = connect();  //Attempts connect to the database
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            disconnect(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Finds the number of columns for the given table, this number is different for each table data type
     * @param table the name of the given table
     * @return ResultSetMetaData, the count of how many columns there are in the given table or 0 if it cannot connect to database
     */
    public int getColumnCountOfTableName(String table) {
        Connection conn = connect();  //Attempts to connect to the database
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);  //Selects every record from the specified table
            ResultSetMetaData rsmd = rs.getMetaData();
            disconnect(conn);
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    /**
     * Disconnects the given connection. If connection fails, an appropriate message is printed
     * @param conn the connection to be disconnected
     */
    static void disconnect(Connection conn) {
        try {
            conn.close();  //Attempts to close the connection
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Selects the maximum ID from the given table
     *
     * @param table the table to be checked and extracted from
     * @return int, the maximum ID from the table
     */
    public int getMaxId(String table) {
        int id = 0;
        try {
            Connection conn = connect();  //Attempts connect to the database
            Statement stmt = conn.createStatement();
            String sql;
            if (getColumnCountOfTableName(table) == 29) {
                sql = String.format("select COUNT(doitt_id) from %s", table);  //The SQL statement for the wifi table
            } else {
                sql = String.format("select COUNT(IdNumber) from %s", table);  //The SQL statement for the retailer and routes table
            }
            ResultSet rs = stmt.executeQuery(sql);  //Executing the query, and getting a list of results
            id = rs.getInt(1);  //Setting the ID accordingly
            disconnect(conn);  //Disconnecting from the database
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }





    /**
     * Deletes the data record with thegiven Id Number from the given table
     ** @param tableName the table to be observed
     * @param idNumber the uniquevalue to identify which record isto be deleted
     */
    public void delete(String tableName, int idNumber) {
        try {
            Connection conn = connect();  //Attempts to connect to the database
            Statement stmt = conn.createStatement();
            if (getColumnCountOfTableName(tableName) == 29) {
                stmt.executeUpdate(String.format("DELETE FROM %s WHERE DOITT_ID = %d", tableName, idNumber));  //SQL statement for the wifi table
            } else {
                stmt.executeUpdate(String.format("DELETE FROM %s WHERE idNumber = %d", tableName, idNumber));  //SQL statement for the retailer and routes table
            }
            disconnect(conn);  //Disconnects from the database
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Deletes the data record with the given date from the travelled table
     * @param date the value to identify which record is to be deleted
     */
    void deleteFromTraveled(String date){
        try {
            Connection conn = connect();  //Attempts to connect to the database
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("DELETE FROM TRAVELED WHERE Date = '%s'", date));  //Executes the relevant SQL statement
            disconnect(conn);  //Disconnects from the database
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Creates the SQL statements and calls insert() to add a new retailer into the retailers table in the databaseusing the given parameters
     * @param shopName the name of the retailer
     * @param address the address of the retailer
     * @param primary the type of retailer
     * @param city the city that the retailer is in
     * @param tableName the name of the table for the new retailer record to be added to
     */
    public void insertIntoRetailers(String shopName, String address, String primary, String city, String[] latlon, String tableName) {
        int id = getMaxId(tableName);
        id++;
        String sql1 = "INSERT INTO " + tableName + "(CnBio_Org_Name, CnAdrPrf_Addrline1, \"primary\", CnAdrPrf_City, lat, lon, IdNumber)";  //Selects table to insert into
        String sql2 = String.format("VALUES('%s','%s','%s', '%s','%s', '%s', '%s')", shopName.replace("\'","").replace("\"",""), address.replace("\'","").replace("\"",""),
                primary.replace("\'","").replace("\"",""), city.replace("\'","").replace("\"",""), latlon[0], latlon[1], id);  //Sets up values for attributes
        insert(sql1, sql2);
        deleteDuplicateRecordsRetailers(tableName);
    }

    /**
     * Creates the SQL statements and calls insert() to add a new bike route into the routes table in the database using the given parameters
     * @param tripDuration the time taken for the new bike route
     * @param startStationName the name of the start station
     * @param endStationName the name of the end station
     * @param userType the type of user
     * @param tableName the name of the table for the new bike route record to be added to
     */
    public void insertIntoRoutes(String tripDuration, String startStationName, String endStationName, String userType, String[] latLon, String tableName) {
        int id = getMaxId(tableName);
        id++;
        String sql1 = "INSERT INTO " + tableName + "('Trip duration', 'Start Station Name', 'Start Station Latitude', 'Start Station Longitude', 'End Station Name', 'End Station Latitude', 'End Station Longitude', 'User Type', 'IdNumber')"; //Selects table to insert into
        String sql2 = String.format("VALUES('%s','%s','%s','%s', '%s', '%s', '%s', '%s', '%s')", tripDuration, startStationName.replace("\'","").replace("\"",""), latLon[0], latLon[1],
                endStationName.replace("\'","").replace("\"",""), latLon[2], latLon[3], userType.replace("\'","").replace("\"",""), id); //Sets up values for attributes
        insert(sql1, sql2);
        deleteDuplicateRecordsRoutes(tableName);
    }


    /**
     * Creates the SQL statements and calls insert() to add a new wifi location into the wifi table in the database using the given parameters
     * @param provider the type of provider the wifi entry belongs to
     * @param type the subscription type the wifi location has
     * @param boro the borough where the wifi spot is located
     * @param address the street address of the wifi spot
     * @param wifiTableName the name of the table for the new wifi location record to be added to
     */
    public void insertIntoWifi(String provider, String type, String boro, String address, String[] latlong, String wifiTableName) {
        int id = getMaxId(wifiTableName);
        id++;
        String sql1 = "INSERT INTO " + wifiTableName + "(provider, type, boro, location,lat, lon, doitt_id)";  //Selects table to insert into
        String sql2 = String.format("VALUES('%s','%s','%s', '%s', '%s', '%s', '%s')",
                provider.replace("\'","").replace("\"",""), type.replace("\'","").replace("\"",""), boro.replace("\'","").replace("\"",""),
                address.replace("\'","").replace("\"",""), latlong[0], latlong[1], id);  //Sets up values for attributes
        insert(sql1, sql2);
        deleteDuplicateRecordsWifi(wifiTableName);
    }



    /**
     * Deletes duplicates in  a given wifi table
     * @param wifiTableName table name of the wifi data type
     */
    void deleteDuplicateRecordsWifi(String wifiTableName) {
        String sql = "DELETE FROM " + wifiTableName +
                " WHERE ROWID not in  (SELECT min(ROWID) FROM "+ wifiTableName + " GROUP BY OBJECTID, BORO, TYPE, Provider, Location)";
        executeSql(sql);
    }

    /**
     * Deletes duplicates in  a given retailer table
     * @param retailerTableName table name of the retailer data type
     */
    void deleteDuplicateRecordsRetailers(String retailerTableName) {
        String sql = "DELETE FROM " + retailerTableName +
                " WHERE ROWID not in  (SELECT min(ROWID) FROM "+ retailerTableName + " GROUP BY lat,lon,BBL,BIN,CnAdrPrf_Addrline1,CnBio_Org_Name,Primary)";
        executeSql(sql);
    }

    /**
     * Deletes duplicates in  a given route table
     * @param routeTableName table name of the route data type
     */
    void deleteDuplicateRecordsRoutes(String routeTableName) {
        String sql = "DELETE FROM " + routeTableName +
                " WHERE ROWID not in  (SELECT min(ROWID) FROM  "+ routeTableName + " GROUP BY \"trip duration\", \"bike id\", \"start time\", \"end time\", \"End Station Name\", \"Start Station Name\", \"User Type\")";
        executeSql(sql);
    }


    /**
     * Creates the SQL statements and calls insert() to add a new record of the date and displacement of a bike route
     * into the traveled table in the database
     * @param date the date for which a bike route was travelled on
     * @param dist the displacement between the start and end points of a bike route
     */
    void insertIntoTraveled(String date, String dist) {
        String sql1 = "INSERT INTO TRAVELED ";  //Selects table to insert into
        String sql2 = String.format("VALUES('%s',%s)", date, dist);  //Sets up values for attributes
        insert(sql1, sql2);
    }


    /**
     * Inserts a new record into the database
     * @param sqlString1 the SQL insert statement
     * @param sqlString2 the values to be inserted
     */
    private void insert(String sqlString1, String sqlString2) {
        try {
            Connection conn = connect();  //Connect to our database
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlString1 + sqlString2);  //Insert new entry into the database
            disconnect(conn);  //Disconnect from the database
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Updates the given row and column of the given table with the value provided
     * @param tableName the name of the table to be updated
     * @param rowId the row index to find the attribute entry to be updated
     * @param columnName the column index to find the attribute entry to be updated
     * @param value the value to replace the current attribute entry in the table
     */
    public void update(String tableName, String rowId, String columnName, String value) {
        String sql;
        if (getColumnCountOfTableName(tableName) == 29) {
            sql = String.format("UPDATE %s SET %s = '%s' WHERE DOITT_ID = %s", tableName, columnName, value, rowId);  //The SQL statement for the wifi table
        } else {
            sql = String.format("UPDATE %s SET %s = '%s' WHERE IdNumber = %s", tableName, columnName, value, rowId);  //The SQL statement for the retailer and route tables
        }
        executeSql(sql);
    }

    /**
     * Updates the Traveled table in the database so that there is one entry for everyday in the last 365 days
     * and deletes data from before one year ago
     */
    public void updateTraveled(){
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        oneYearAgo = oneYearAgo.plusDays(1);
        String[][] dates= QueryData.selectAllTraveled();
        int counter = 0;
        while (oneYearAgo.isAfter(LocalDate.parse(dates[counter][0]))){
            counter++;
        }
        for (int i = 0; i< counter; i++){
            deleteFromTraveled(dates[i][0]);
        }
        LocalDate lastDate = LocalDate.parse(dates[364][0]);
        for (int i = 365 - counter; i < 365; i++){
            lastDate = lastDate.plusDays(1);
            insertIntoTraveled(lastDate.toString(),"0");
        }
    }

    /**
     * Updates a single entry with the date, "date" to have a distance, "dist" in the TRAVELED table
     * @param date The date of the entry to be updated
     * @param dist The distance to update the entry to
     */
    void updateSingleTraveled(String date, String dist){
        String sql = String.format("UPDATE TRAVELED SET DISTANCE = '%s' WHERE Date = '%s'", dist, date);
        try {
            Connection conn = AccessFile.connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            disconnect(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a distance to the current value of the entry with today's date
     * @param dist The distance to be added to the entry
     */
    public void addDistToTraveled(double dist){
        LocalDate date = LocalDate.now();
        QueryData queryData = new QueryData();
        String currentDist = queryData.getSingleTraveled(date.toString());
        double totalDist = Double.valueOf(currentDist);
        totalDist += dist;
        updateSingleTraveled(date.toString(), String.valueOf(totalDist));

    }

    void setUrl(String url) {
        AccessFile.url = url;
    }
}
