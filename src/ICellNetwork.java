/**
 * This interface defines the behavior of a cell tower network in the United States.
 */

public interface ICellNetwork {

    /**
     * Radius of Earth in nautical miles
     */
    public static final double RADIUS = 3440.069;

    /**
     * Computes the distance between two locations in nautical miles using the Haversine formula
     *
     * @param lat1 latitude of location 1
     * @param long1 longitude of location 1
     * @param lat2 latitude of location 2
     * @param long2 longitude of location 2
     * @return distance between locations 1 and 2 in nautical miles
     */
    public static int computeNauticalMiles(double lat1, double long1, double lat2, double long2) {
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLong = Math.toRadians(long2 - long1);

        // convert latitudes to radians
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat1);

        // Haversine formula
        double haversine = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat2Rad) * Math.cos(lat1Rad) * Math.pow(Math.sin(deltaLong / 2), 2);

        // compute distance in nautical miles using Earth's radius
        double distance = RADIUS * 2 * Math.asin(Math.sqrt(haversine));

        return (int) Math.round(distance);
    }
}
