/**
 * This class provides a concrete implementation of the ICellTower interface.
 */

public class CellTower implements ICellTower {
    
    private String license;
    private int id;
    private String address;
    private String city;
    private String county;
    private String state;
    private double latitude;
    private double longitude;
    
    
    /**
     * ctor
     */
    
    public CellTower(int towerID, double latDeg, double longDeg, String licensee, String ad, String cty, String county, 
            String st) {
       
        setId(towerID);
        setLicense(licensee);
        setAddress(ad);
        setCity(cty);
        setCounty(county);
        setLat(latDeg);
        setLong(longDeg);
        setState(st);
    }


    public String getLicense() {
        return license;
    }


    public void setLicense(String license) {
        this.license = license;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getCounty() {
        return county;
    }


    public void setCounty(String county) {
        this.county = county;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public double getLat() {
        return latitude;
    }


    public void setLat(double latitude) {
        this.latitude = latitude;
    }


    public double getLong() {
        return longitude;
    }


    public void setLong(double longitude) {
        this.longitude = longitude;
    }
    
    @Override
    public String toString() {
        String out = null;
        
        //out  += "\t⧋  \t\n" + "id: " + id + " at " + city + ", " + state;
        
        out =  id + ": " + county +"," + city + ", " + state + " located at: (" + latitude + ","  + longitude + ")" + "\n";
        
        return out;
        
    }
}