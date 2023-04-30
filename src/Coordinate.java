public class Coordinate {

    private double latitude;
    private double longitude;

    /**
     * Create a new coordinate object
     * @param latitude
     * @param longitude
     */
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    
    public String toString() {
        return "lat: " + this.latitude + ", long: " + this.longitude;
    }

}
