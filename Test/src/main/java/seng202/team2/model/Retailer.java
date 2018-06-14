package seng202.team2.model;


import seng202.team2.controller.SettingsPageController;
import seng202.team2.data.AccessFile;
import seng202.team2.data.ConvertAddress;
import seng202.team2.data.LatLngToAddress;


/**
 * Retailer Model Object
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */

public class Retailer {

    /** Name of the retailer company */
    private String companyName;

    /** The first line of the address of the retailer */
    private String addressLine1;

    /** The second line of the address of the retailer (may be empty) */
    private String addressLine2;

    /** The city that the retailer is located in */
    private String city;

    /** The state that the retailer is located in */
    private String state;

    /** The zip code for the retailer's location */
    private String zip;

    /** The number of the block lot that the retailer is located in */
    private String blockLot;

    /** The primary category of the retailer */
    private String primary;

    /** The secondary category of the retailer */
    private String secondary;

    /** The borough that the retailer is located in */
    private String borough;

    /** The latitude value of the retailers location */
    private String latitude;

    /** The longitude value of the retailers location */
    private String longitude;

    /** Identification number of the community board for the retailer */
    private String communityBoard;

    /** Identification number of the council district for the retailer */
    private String councilDistrict;

    /** Identification number of the census tract that the retailer belongs to */
    private String censusTract;

    /** Building identification number of the building in which the retailer is located */
    private String bin;

    /** The borough/block/lot number of the retailer */
    private String bbl;

    /** The Neighbourhood Tabulation Area in which the retailer is located */
    private String nta;

    /** Identification number of the retailer */
    private String idNumber;

    /** Distance to some route from the retailer */
    private double distanceToRoute;

    private AccessFile accessFile;


    /**
     * Constructor for Retailer
     */
    public Retailer(String companyName, String addressLine1, String addressLine2, String city, String state, String zip, String blockLot, String primary,
                    String secondary, String borough, String latitude, String longitude, String communityBoard, String councilDistrict,
                    String censusTract, String bin, String bbl, String nta, String idNumber) {
        this.companyName = companyName;
        this.addressLine1 = addressLine1;

        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.blockLot = blockLot;
        this.primary = primary;
        this.secondary = secondary;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
        this.communityBoard = communityBoard;
        this.councilDistrict = councilDistrict;
        this.censusTract = censusTract;
        this.bin = bin;
        this.bbl = bbl;
        this.nta = nta;
        this.idNumber = idNumber;
        this.accessFile = new AccessFile();
    }


    /**
     * @return {@link Retailer#companyName}
     */
    public String getCompanyName() {
        return companyName;
    }


    /**
     * @return {@link Retailer#addressLine1}
     */
    public String getAddressLine1() {
        if (this.addressLine1.isEmpty()) {
            LatLngToAddress l = new LatLngToAddress();
            String address= l.getAddress(getLatitude(),getLongitude());
            accessFile.update(SettingsPageController.settingsController.getRetailTable(), idNumber, "CnAdrPrf_Addrline1",address);
            return address;
        }
        return addressLine1;
    }


    /**
     * @return {@link Retailer#addressLine2}
     */
    public String getAddressLine2() {
        return addressLine2;
    }


    /**
     * @return {@link Retailer#city}
     */
    public String getCity() {
        return city;
    }


    /**
     * @return {@link Retailer#state}
     */
    public String getState() {
        return state;
    }


    /**
     * @return {@link Retailer#zip}
     */
    public String getZip() {
        return zip;
    }


    /**
     * @return {@link Retailer#primary}
     */
    public String getPrimary() {
        return primary;
    }


    /**
     * @return {@link Retailer#secondary}
     */
    public String getSecondary() {
        return secondary;
    }


    /**
     * @return {@link Retailer#borough}
     */
    public String getBorough() {
        return borough;
    }


    /**
     * Uses the address to find the latitude of the retailer
     * @return {@link Retailer#latitude}
     */
    public String getLatitude() {
        if (latitude == null || latitude.isEmpty()) {
            AccessFile accessFile = new AccessFile();
            latitude = ConvertAddress.getLatLongPositions(addressLine1 + city )[0];
            accessFile.update("Retailers", idNumber, "Lat", latitude);
        }
        return latitude;
    }


    /**
     * Uses the address to find the longitude of the retailer
     * @return {@link Retailer#longitude}
     */
    public String getLongitude() {
        if (longitude == null || longitude.isEmpty()) {
            AccessFile accessFile = new AccessFile();
            longitude = ConvertAddress.getLatLongPositions(addressLine1 + city)[1];
            accessFile.update("Retailers", idNumber, "Lon", longitude);
        }
        return longitude;
    }


    /**
     * @return {@link Retailer#communityBoard}
     */
    public String getCommunityBoard() {
        return communityBoard;
    }


    /**
     * @return {@link Retailer#councilDistrict}
     */
    public String getCouncilDistrict() {
        return councilDistrict;
    }


    /**
     * @return {@link Retailer#censusTract}
     */
    public String getCensusTract() {
        return censusTract;
    }


    /**
     * @return {@link Retailer#bin}
     */
    public String getBin() {
        return bin;
    }


    /**
     * @return {@link Retailer#bbl}
     */
    public String getBbl() {
        return bbl;
    }


    /**
     * @return {@link Retailer#nta}
     */
    public String getNta() {
        return nta;
    }


    /**
     * @return {@link Retailer#idNumber}
     */
    public String getIdNumber() {
        return idNumber;
    }


    /**
     * @return {@link Retailer#distanceToRoute}
     */
    public double getDistanceToRoute(){
        return distanceToRoute;
    }


    /**
     * Setter for {@link Retailer#companyName}
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    /**
     * Setter for {@link Retailer#addressLine1}
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }


    /**
     * Setter for {@link Retailer#addressLine2}
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }


    /**
     * Setter for {@link Retailer#city}
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Setter for {@link Retailer#primary}
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }


    /**
     * Setter for {@link Retailer#borough}
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }


    /**
     * Setter for {@link Retailer#latitude}
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    /**
     * Setter for {@link Retailer#longitude}
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    /**
     * Setter for {@link Retailer#bin}
     */
    public void setBin(String bin) {
        this.bin = bin;
    }


    /**
     * Setter for {@link Retailer#bin}
     */
    public void setDistanceToRoute(double x) {
        distanceToRoute = x;

    }


    /**
     * Overwrites the equals method for a Retailer object (used for the junit tests)
     * @param o an Object
     * @return Boolean, depends on whether the Retailer values are equal to the given objects values
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Retailer) {
            Retailer retailer = (Retailer) o;
             if (this.distanceToRoute == retailer.distanceToRoute) {
                return true;
            }

            else if (this.getAddressLine1().equals(retailer.addressLine1) && this.getCompanyName().equals(retailer.getCompanyName()) && this.getPrimary().equals(retailer.getPrimary())) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return String representation of distanceToRoute - used for error checking
     */
    public String toString() {
        return Double.toString(distanceToRoute);
    }
}
