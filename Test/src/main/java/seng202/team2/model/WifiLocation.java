package seng202.team2.model;

import seng202.team2.data.ConvertAddress;

/**
 * WifiLocation Model Object
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class WifiLocation {

    /** Identification number of the wifi location */
    private String objectId;

    /**
     * String containing the latitude and longitude of the wifi location
     * in the format 'POINT([lat], [lon])'
     */
    private String the_geom;

    /** Two character abbreviation of the borough in which the retailer is located */
    private String borough;

    /** type of wifi available at the wifi location (eg 'Free', 'Limited Free') */
    private String type;

    /** Name of the company that provides the wifi at the wifi location */
    private String provider;

    /** Name of the wifi location */
    private String name;

    /** Description of where the wifi location is located */
    private String location;

    /** Latitude of the wifi location */
    private String lat;

    /** Longitude of the wifi location */
    private String lon;

    /** X-coordinate of the wifi location */
    private String x_cor;

    /** Y-coordinate of the wifi location */
    private String y_cor;

    /** Location type of the wifi location (eg 'Outdoor', 'Indoor') */
    private String location_t;

    /** Comments about the wifi location */
    private String remarks;

    /** Name of the city in which the wifi location is located */
    private String city;

    /** Name of the network providing the wifi location */
    private String ssid;

    /** Source ID of the wifi location */
    private String sourceId;

    /** Date and time that the wifi location was activated
     * in the format 'MM/DD/YYYY HH:MM:SS +0000' */
    private String activated;

    /** Identification number of the borough the wifi location is in */
    private String boroCode;

    /** Name of the borough the wifi location is in */
    private String boroName;

    /** Identification code of the Neighbourhood Tabulation Area which the wifi location is in */
    private String ntaCode;

    /** Name of the Neighbourhood Tabulation Area which the wifi location is in */
    private String ntaName;

    /** Identification number of the council district the wifi location is in */
    private String counDist;

    /** Postal code of the wifi location */
    private String postCode;

    /** String of the borough code and community district number combined */
    private String boroCd;

    /** Census tract number */
    private String ct2010;

    /** String of the borough code and census tract number combined */
    private String boroCt2010;

    /** Building identification number of the building the wifi location is in
     *  (0 if not applicable, ie it is not in a building) */
    private String bin;

    /** The borough/block/lot number of the wifi location (0 if not applicable) */
    private String bbl;

    /** Unique identification number of the wifi location */
    private String doittId;

    /** distance to a given route, used as a way to sort retailers*/
    private double distanceToRoute;


    /**
     * Constructor for WifiLocation
     */
    public WifiLocation(String objectId, String the_geom, String borough, String type, String provider, String name, String location,
                        String lat, String lon, String x, String y_cor, String location_t, String remarks, String city, String ssid, String sourceId,
                        String activated, String boroCode, String boroName, String ntaCode, String ntaName, String counDist, String postCode, String boroCd,
                        String ct2010, String boroCt2010, String bin, String bbl, String doittId) {
        this.objectId = objectId;
        this.the_geom = the_geom;
        this.borough = borough;
        this.type = type;
        this.provider = provider;
        this.name = name;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.x_cor = x;
        this.y_cor = y_cor;
        this.location_t = location_t;
        this.remarks = remarks;
        this.city = city;
        this.ssid = ssid;
        this.sourceId = sourceId;
        this.activated = activated;
        this.boroCode = boroCode;
        this.boroName = boroName;
        this.ntaCode = ntaCode;
        this.ntaName = ntaName;
        this.counDist = counDist;
        this.postCode = postCode;
        this.boroCd = boroCd;
        this.ct2010 = ct2010;
        this.boroCt2010 = boroCt2010;
        this.bin = bin;
        this.bbl = bbl;
        this.doittId = doittId;
        this.distanceToRoute =0;
    }


    /**
     * @return {@link WifiLocation#objectId}
     */
    public String getObjectId() {
        return objectId;
    }


    /**
     * @return {@link WifiLocation#borough}
     */
    public String getBorough() {
        return borough;
    }

    /**
     * @return {@link WifiLocation#type}
     */
    public String getType() {
        return type;
    }

    /**
     * @return {@link WifiLocation#provider}
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @return {@link WifiLocation#name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link WifiLocation#location}
     */
    public String getLocation() {
        return location;
    }

    /**
     * Calculates the latitude of the wifi location using it's
     * address (location)
     * @return {@link WifiLocation#lat}
     */
    public String getLat() {
        if (lat != null && !lat.isEmpty()) {
            //System.out.println(lat.length());
            return lat;
        }
        return ConvertAddress.getLatLongPositions(location + city)[0];
    }

    /**
     * Calculates the longitude of the wifi location using it's
     * address (location)
     * @return {@link WifiLocation#lon}
     */
    public String getLon() {
        if (lon != null && !lon.isEmpty()) {
            //System.out.println(lon.length());
            return lon;
        }
        return ConvertAddress.getLatLongPositions(location + city)[1];
    }

    /**
     * @return {@link WifiLocation#x_cor}
     */
    public String getX_cor() {
        return x_cor;
    }

    /**
     * @return {@link WifiLocation#y_cor}
     */
    public String getY_cor() {
        return y_cor;
    }

    /**
     * @return {@link WifiLocation#location_t}
     */
    public String getLocation_t() {
        return location_t;
    }

    /**
     * @return {@link WifiLocation#remarks}
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return {@link WifiLocation#city}
     */
    public String getCity() {
        return city;
    }

    /**
     * @return {@link WifiLocation#ssid}
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * @return {@link WifiLocation#sourceId}
     */
    public String getSourceId() {
        return sourceId;
    }


    /**
     * @return {@link WifiLocation#ntaCode}
     */
    public String getNtaCode() {
        return ntaCode;
    }

    /**
     * @return {@link WifiLocation#ntaName}
     */
    public String getNtaName() {
        return ntaName;
    }


    /**
     * @return {@link WifiLocation#postCode}
     */
    public String getPostCode() {
        return postCode;
    }


    /**
     * @return {@link WifiLocation#ct2010}
     */
    public String getCt2010() {
        return ct2010;
    }

    /**
     * @return {@link WifiLocation#boroCt2010}
     */
    public String getBoroCt2010() {
        return boroCt2010;
    }

    /**
     * @return {@link WifiLocation#bin}
     */
    public String getBin() {
        return bin;
    }

    /**
     * @return {@link WifiLocation#bbl}
     */
    public String getBbl() {
        return bbl;
    }

    /**
     * @return {@link WifiLocation#doittId}
     */
    public String getDoittId() {
        return doittId;
    }

    /*
    Todo: finish java doc
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /*
    Todo: finish java doc
     */
    public void setType(String type) {
        this.type = type;
    }

    /*
    Todo: finish java doc
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /*
    Todo: finish java doc
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
    Todo: finish java doc
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /*
    Todo: finish java doc
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /*
    Todo: finish java doc
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /*
    Todo: finish java doc
     */
    public void setX_cor(String x_cor) {
        this.x_cor = x_cor;
    }

    /*
    Todo: finish java doc
     */
    public void setY_cor(String y_cor) {
        this.y_cor = y_cor;
    }

    /*
    Todo: finish java doc
     */
    public void setLocation_t(String location_t) {
        this.location_t = location_t;
    }

    /*
    Todo: finish java doc
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /*
    Todo: finish java doc
     */
    public void setCity(String city) {
        this.city = city;
    }


    /*
    Todo: finish java doc
     */
    public void setBin(String bin) {
        this.bin = bin;
    }


    /**
     * Overwrites the equals method for a WifiLocation object (used for the junit tests)
     * @param o an Object
     * @return Boolean, depends on whether the WifiLocation values are equal to the given objects values
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof WifiLocation) {
            WifiLocation wifi = (WifiLocation) o;
            if (this.location.equals(wifi.location) && this.getBorough().equals(wifi.getBorough())
                    && this.getType().equals(wifi.getType()) && this.getProvider().equals(wifi.getProvider())) {
                return true;
            }
        }
        return false;
    }


    public double getDistanceToRoute(){
        return distanceToRoute;
    }


    public void setDistanceToRoute(double x){
        distanceToRoute = x;
    }

}
