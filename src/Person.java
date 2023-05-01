import java.util.List;
import java.util.Stack;

/**
 * This class provides a concrete implementation of the IPerson interface.
 */

public class Person implements IPerson {

    // Name of person
    private String name;
    // coordinate object for person's position (must be negative)
    private Coordinate location;
    // person's cell phone carrier
    private String carrier;
    // checks if person can connect to network
    private boolean canConnect;
    // cell tower associated with person
    private CellTower personCellTower;


    /**
     * Person constructor
     * @param firstName
     * @param cellCarrier
     * @param lat
     * @param lon
     */
    public Person(String firstName, String cellCarrier, double lat, double lon, ICellularRegion root) {
        setName(firstName);
        setCarrier(cellCarrier);
        // create coordinate object to represent person's location
        location = new Coordinate(lat, lon);
        // check if the person can connect
        if (canConnect) {
            personCellTower = findNearestCellTower(root);
        }
    }


    /**
     * Method to set carrier of person by coercing all carriers into AT&T or Verizon
     * @param carrier
     */
    public void setCarrier(String carrier) {
        if (PROVIDERMAP.containsKey(carrier)) {
            carrier = carrier.toLowerCase();
            this.carrier = PROVIDERMAP.get(carrier);
            canConnect = true;
        } else {
            this.carrier = carrier;
        }
    }


    /**
     *
     */
    private CellularRegion findClosestCellularRegion(Coordinate currLocation, CellularRegion currRegion) {
        // get siblings of current region
        List<ICellularRegion> siblings = currRegion.getSiblings();

        // for each sibling, compute its center
        Coordinate center1 = computeCenter(((CellularRegion) siblings.get(0)).getTopLeft(),
                ((CellularRegion) siblings.get(0)).getBotRight());
        Coordinate center2 = computeCenter(((CellularRegion) siblings.get(1)).getTopLeft(),
                ((CellularRegion) siblings.get(1)).getBotRight());
        Coordinate center3 = computeCenter(((CellularRegion) siblings.get(2)).getTopLeft(),
                ((CellularRegion) siblings.get(2)).getBotRight());

        // compute distance between current location and each center coordinate
        int distance1 = computeNauticalMiles(currLocation, center1);
        int distance2 = computeNauticalMiles(currLocation, center2);
        int distance3 = computeNauticalMiles(currLocation, center3);

        // take min of 3 distances
        int tempMin = Math.min(distance1, distance2);
        tempMin = Math.min(tempMin, distance3);

        // return region corresponding to minimum of distances
        if (tempMin == distance1) {
            currLocation = center1;
            return (CellularRegion) siblings.get(0);
        } else if (tempMin == distance2) {
            currLocation = center2;
            return (CellularRegion) siblings.get(1);
        }
        currLocation = center3;
        return (CellularRegion) siblings.get(2);
    }


    /**
     * Computes the distance between two coordinates in nautical miles using the Haversine formula
     *
     * @param coord1 coordinate 1
     * @param coord2 coordinate 2
     * @return distance between towers 1 and 2 in nautical miles
     */
    public int computeNauticalMiles(Coordinate coord1, Coordinate coord2) {
        // get latitude and longitude of towers 1 and 2
        double lat1 = coord1.getLatitude();
        double long1 = coord1.getLongitude();
        double lat2 = coord2.getLatitude();
        double long2 = coord2.getLongitude();

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
        double distance = ICellNetwork.RADIUS * 2 * Math.asin(Math.sqrt(haversine));

        return (int) Math.round(distance);
    }



    /**
     * Computes center given top left and bottom right points
     * @param topLeft Point
     * @param botRight Point
     * @return center Point
     */
    private Coordinate computeCenter(Coordinate topLeft, Coordinate botRight) {
        // calculate center coordinate
        double cenLat = (topLeft.getLatitude() + botRight.getLatitude()) / 2;
        double cenLong = (topLeft.getLongitude() + botRight.getLongitude()) / 2;
        return new Coordinate(cenLat, cenLong);
    }



    /**
     * Given a Person's coordinates, find the nearest cell tower
     * @return
     */
    public CellTower findNearestCellTower(ICellularRegion root) {
        // create copy of person's location for traversal
        Coordinate currLocation = new Coordinate(location.getLatitude(), location.getLongitude());

        // initialize queue and place root in queue
        Stack<ICellularRegion> stack = new Stack<>();
        stack.push(root);

        // iterate until queue is empty
        while (!stack.isEmpty()) {
            // get the current region in the traversal
            ICellularRegion currRegion = stack.pop();

            // if current region is a leaf:
            // if empty, go back up the tree
            // otherwise, return the cell
            if (currRegion.isLeaf()) {
                // return cell tower if only one cell tower in region
                if (currRegion.getTowersInRegion().size() == 1) {
                    return currRegion.getTowersInRegion().get(0);
                }
                // no cell towers in region
                ICellularRegion newRegion = findClosestCellularRegion(currLocation, (CellularRegion) currRegion);
                stack.push(newRegion);
            }

            if (!currRegion.isLeaf()) {
                if (((CellularRegion) currRegion).getTopLeftSq() != null && ((CellularRegion) currRegion).getTopLeftSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getTopLeftSq());
                } else if (((CellularRegion) currRegion).getTopRightSq() != null && ((CellularRegion) currRegion).getTopRightSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getTopRightSq());
                } else if (((CellularRegion) currRegion).getBotRightSq() != null && ((CellularRegion) currRegion).getBotRightSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getBotRightSq());
                } else {
                    stack.push(((CellularRegion) currRegion).getBotLeftSq());
                }
            }
        }
        // return null if traversal ended without finding a sub block with pos arg
        return null;
    }


    // Getters and setters for fields

    public boolean getCanConnect() {
        return canConnect;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrier() {
        return carrier;
    }


    public CellTower getPersonCellTower() {
        return personCellTower;
    }
    
    public Coordinate getLocation() {
        return this.location;
    }

}
