/**
 * This class provides a concrete implementation of the IPerson interface.
 */

public class Person implements IPerson {
    
    private String name;
    private double longitude;
    private double latitude;
    private String carrier;
    
    
    public Person(String firstName, String cellCarrier, double lat, double lon) {
        // TODO Auto-generated constructor stub
        setName(firstName);
        setCarrier(cellCarrier);
        setLong(lon);
        setLat(lat);
        
    }

    public double getLong() {
        return longitude;
    }

    public void setLong(double lon) {
        this.longitude = lon;
    }

    public double getLat() {
        return latitude;
    }

    public void setLat(double lat) {
        this.latitude = lat;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        if (PROVIDERMAP.containsKey(carrier)) {
            carrier = carrier.toLowerCase();
            this.carrier = PROVIDERMAP.get(carrier);
        } else {
            this.carrier = carrier;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * private boolean hasTower(QT) {
     * 
     * fir
     * 
     * create some sort of function to check which fourth the lat and long are in
     * 
     * based on that pick the child, repeat until we are at a leaf - if leaf empty no cell tower
     * 
     * - check some distance heuristic (bool for lat and long in quad class?)
     * 
     * - if leaf has cell tower:
     *  - check if tower matches provider
     *      - if yes and matches distances h then return true
     *      
     *  - if no check the other children
        
    }
     */
    
}
