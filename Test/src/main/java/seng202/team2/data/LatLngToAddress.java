package seng202.team2.data;

import org.w3c.dom.Document;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Provides methods that retrieve addresses from the database and converts LatLong details to an actual address.
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class LatLngToAddress {

    private static final String[] API_KEYS = {"AIzaSyAwGkUufDIeqNPrjnt3s5RD34UU0bUgs8Y", "AIzaSyCL-Yo3xQMy39M1KpLJXzxQGOKL9M0ayd4",
            "AIzaSyCngpdUCpdtqcY9TAhwLJduos8eU7_fMwA", " AIzaSyAUHPXQNQzZY5AdFKzakrXbUYatlG4G7Bc", " AIzaSyArPeq4CJzk-B67nLFFwZzVZ09R0nGJghk",
            "AIzaSyD7DEH6Klk3ZyduVyqbaVEyTscj4sp48PQ"};  //The array holding all the geocaching api keys
    private static Random randNumber = new Random();


    /**
     * Takes Latitude and Longitude coordinates, connects to the google geocode api and converts them into a physical address.
     * @param lat The latitude to be converted
     * @param lng The longitude to be converted
     * @return String, the address given by the coordinates
     */
    public String getAddress(String lat, String lng) {
        int responseCode;
        int api_index = getRandNumber().nextInt(3); // changes api key each method call.
        try{
            String api = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=" + URLEncoder.encode(lat, "UTF-8") + "," + URLEncoder.encode(lng, "UTF-8") +
                    "&sensor=false&key=" + URLEncoder.encode(getApiKeys()[api_index], "UTF-8");
            URL url = new URL(api);
            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.connect();  //Attempts to connect to the internet, and from that the google geocoding api.
            responseCode = httpConnection.getResponseCode();  //If the response is postive, the data transfer can begin
            if (responseCode == 200) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(httpConnection.getInputStream());
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("/GeocodeResponse/status");
                String status = (String) expr.evaluate(document, XPathConstants.STRING);  //The relevant data is extracted by the api
                if (status.equals("OK")) {  //If the status is ok, the file is compiled and sent to the app
                    expr = xpath.compile("//formatted_address");
                    return (String) expr.evaluate(document, XPathConstants.STRING);
                } else {
                    throw new Exception("Error from the API - response status: " + status);  //Error message if the file status is not as expected
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();  //Connection fails to internet
        }
        return null;
    }

    private Random getRandNumber() {
        return randNumber;
    }

    private String[] getApiKeys(){
        return API_KEYS;
    }

}



