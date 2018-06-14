package seng202.team2.data;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;


/**
 * Provides methods that retrieves a physical address and converts to Latitude and Longitude coordinates using geocoding
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class ConvertAddress {

    private static final String[] API_KEYS = {"AIzaSyAwGkUufDIeqNPrjnt3s5RD34UU0bUgs8Y", "AIzaSyCL-Yo3xQMy39M1KpLJXzxQGOKL9M0ayd4",
            "AIzaSyCngpdUCpdtqcY9TAhwLJduos8eU7_fMwA", " AIzaSyAUHPXQNQzZY5AdFKzakrXbUYatlG4G7Bc", " AIzaSyArPeq4CJzk-B67nLFFwZzVZ09R0nGJghk",
            "AIzaSyD7DEH6Klk3ZyduVyqbaVEyTscj4sp48PQ"};  //Use a random key to increase the amount of queries the user can make in one day


    /**
     * Takes a physical address and converts it to Latitude, Longitude coordinates.
     * @param address the physical address to be converted
     * @return String[], an Array containing the latitude/longitude coordinates of the address
     */
    public static String[] getLatLongPositions(String address)
    {
        int responseCode;  //Time-out
        Random randNumber = new Random();
        int api_index = randNumber.nextInt(3);  //Choose a random api from the key array
        try {
            String api = "https://maps.googleapis.com/maps/api/geocode/xml?address=" +  //Google geocode api, using given address and the random api key.
                URLEncoder.encode(address, "UTF-8") + "&sensor=false&key="+ URLEncoder.encode(API_KEYS[api_index], "UTF-8");
            URL url = new URL(api);
            HttpsURLConnection httpConnection = (HttpsURLConnection)url.openConnection();
            httpConnection.connect();  //Attempts to connect to the internet, and from that the google geocoding api.
            responseCode = httpConnection.getResponseCode();
            String status = "Timeout";

            if(responseCode == 200) { //If the response is positive, the data transfer can begin
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(httpConnection.getInputStream());
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("/GeocodeResponse/status");
                status = (String)expr.evaluate(document, XPathConstants.STRING);  //The relevant data is extracted by the api

                if (status.equals("OK")) {  //If the status is ok, the file is compiled and sent to the app
                    expr = xpath.compile("//geometry/location/lat");  //Lat field in the xml
                    String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
                    expr = xpath.compile("//geometry/location/lng");  //Lng field in the xml
                    String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
                    return new String[] {latitude, longitude};  //Api key.
                }
            } else {
                System.out.println("Error from the API - response status: "+ status);  //Error message if the file status is not as expected
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

