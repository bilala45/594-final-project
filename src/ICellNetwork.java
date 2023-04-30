/**
 * This interface defines the behavior of a cell tower network in the United States.
 */

public interface ICellNetwork {

    /**
     * Radius of Earth in nautical miles
     */
    public static final double RADIUS = 3440.069;

    /**
     * Computes the distance between two cell towers in nautical miles using the Haversine formula
     *
     * @param tower1 cell tower 1
     * @param tower2 cell tower 2
     * @return distance between towers 1 and 2 in nautical miles
     */
    public static int computeNauticalMiles(CellTower tower1, CellTower tower2) {
        // get latitude and longitude of towers 1 and 2
        double lat1 = tower1.getLat();
        double long1 = tower1.getLong();
        double lat2 = tower2.getLat();
        double long2 = tower2.getLong();

        // compute difference between latitude and longitudes of each tower
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


    /**
     * Assigns each cell tower in dataset to vertex in graph
     */
    public void assignVertices();


    /**
     * Calculate distance between each cell tower in graph
     */
    public void generateTowerConnections();


    /**
     * Cost function to adjust distance between two cell towers by squaring distance between towers
     * An additional penalty is applied if:
     * - cell towers owned by different providers try to connect
     * - cell towers are > 100 miles apart (no connection possible in this case)
     *
     * @return adjusted cost
     */
    public int computeEdgeWeight(CellTower tower1, CellTower tower2);
}