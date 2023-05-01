import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * This class provides a concrete implementation of the IPerson interface.
 */

public class Person implements IPerson {

    // Name of person
    private String name;
    // coordinate object for person's position
    private Coordinate location;
    // person's cell phone carrier
    private String carrier;
    // checks if person can connect to network
    private boolean canConnect;
    // CellTower object closest to person's location
    private CellTower personCellTower;


    /**
     * Person constructor
     * @param firstName person's name
     * @param cellCarrier person's cell carrier
     * @param lat person's latitude
     * @param lon person's longitude
     */
    public Person(String firstName, String cellCarrier, double lat, double lon, ICellularRegion root) {
        // set person's first name
        name = firstName;
        // set person's cell phone carrier
        setCarrier(cellCarrier);
        // create coordinate object to represent person's location
        setLocation(lat, lon);

        // if this Person can connect, get the cell tower nearest to the Person
        if (canConnect) {
            personCellTower = findNearestCellTower(root);
        }
    }


    // Getters for Person's fields
    public String getName() { return name; }

    public Coordinate getLocation() {
        return location;
    }

    public String getCarrier() {
        return carrier;
    }

    public boolean getCanConnect() {
        return canConnect;
    }

    public CellTower getPersonCellTower() {
        return personCellTower;
    }


    /**
     * Method to validate person's location
     * @param lat person's latitude
     * @param lon person's longitude
     */
    public void setLocation(double lat, double lon) {
        // create location object for given coordinates
        location = new Coordinate(lat, lon);

        // determine if person can connect by checking if person's location is within bounds of cellular region being served
        if ((lon >= ICellularRegion.botRight.getLongitude() && lon <= ICellularRegion.topLeft.getLongitude())
                && (lat <= ICellularRegion.botRight.getLatitude() && lat >= ICellularRegion.topLeft.getLatitude())) {
            canConnect = true;
        }
    }


    /**
     * Method to set carrier of person (coerces all carriers into AT&T or Verizon)
     * @param carrier a person's cell phone carrier
     */
    public void setCarrier(String carrier) {
        // process carrier string to be lowercase
        carrier = carrier.toLowerCase();

        // check if carrier provided is a valid carrier in the map of valid providers
        if (PROVIDERMAP.containsKey(carrier)) {
            // set person's carrier to carrier in map
            this.carrier = PROVIDERMAP.get(carrier);
            // validate connection
            canConnect = true;
        } else {
            // carrier doesn't match a valid provider, canConnect remains false
            this.carrier = carrier;
        }
    }


    /**
     * Traverses quad tree to find CellTower closest to this Person
     * @param root of quad tree
     * @return CellTower object closest to this Person
     */
    public CellTower findNearestCellTower(ICellularRegion root) {
        // create copy of person's location for traversal
        Coordinate currLocation = new Coordinate(location.getLatitude(), location.getLongitude());

        // initialize stack and place root in stack
        Stack<ICellularRegion> stack = new Stack<>();
        stack.push(root);

        // iterate until stack is empty
        while (!stack.isEmpty()) {
            // get the current region in the traversal
            ICellularRegion currRegion = stack.pop();

            System.out.println("Top Left and Bottom Right");
            System.out.println(((CellularRegion) currRegion).getTopLeft());
            System.out.println(((CellularRegion) currRegion).getBotRight());
            System.out.println(currRegion.getTowersInRegion().size());
            System.out.println();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // If the current region is a leaf, this region may contain the nearest cell tower
            if (currRegion.isLeaf()) {
                // TODO would have to change this so that we call some method that finds the
                // TODO cell tower closest to us once we get to a sub-region with a small enough number of cell towers
                // return cell tower in region if this region contains a single cell tower
                if (currRegion.getTowersInRegion().size() == 1) {
                    return currRegion.getTowersInRegion().get(0);
                }
                // TODO this logic should still be fine with the change but it would probably work better
                // TODO because it would be more of an edge case, so we would almost always have siblings with leafs
                // current region is a leaf but no cell towers are in the region
                // in this case, we:
                // - reset the person's location to be the center of the person's closest neighboring region
                // - push the new region back onto the stack so that we traverse that region's children instead
                ICellularRegion newRegion = findClosestCellularRegion(currLocation, currRegion);
                stack.push(newRegion);
            }
            // current region is not a leaf
            else {
                // push the region onto the stack that the person's location is located within
                if (((CellularRegion) currRegion).getTopLeftSq() != null && ((CellularRegion) currRegion).getTopLeftSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getTopLeftSq());
                } else if (((CellularRegion) currRegion).getTopRightSq() != null && ((CellularRegion) currRegion).getTopRightSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getTopRightSq());
                } else if (((CellularRegion) currRegion).getBotRightSq() != null && ((CellularRegion) currRegion).getBotRightSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getBotRightSq());
                } else if (((CellularRegion) currRegion).getBotLeftSq() != null && ((CellularRegion) currRegion).getBotLeftSq().isCoordinateWithinRegion(currLocation)) {
                    stack.push(((CellularRegion) currRegion).getBotLeftSq());
                }
            }
        }
        // return null if traversal ended finding the nearest cell tower (should be a rare occurrence)
        // in this case, canConnect is set to false because the person's call could not be routed to a cell tower
        canConnect = false;
        return null;
    }


    /**
     *
     * @param currLocation current location of person (potentially different if person's location was
     *                     reset in findNearestCellTower
     * @param currRegion current region of board that person is in (this region is necessarily a leaf)
     * @return CellularRegion object nearest to person's location
     */
    private ICellularRegion findClosestCellularRegion(Coordinate currLocation, ICellularRegion currRegion) {
        // get siblings of current region
        List<ICellularRegion> siblings = currRegion.getSiblings();

//        if (siblings.size() != 0) {
            // sibling center
            Coordinate[] siblingCenter = new Coordinate[siblings.size()];
            int[] distanceFromSiblingToPerson = new int[siblings.size()];
            for (int i = 0 ; i < siblingCenter.length ; i++) {
                // get center of sibling region
                siblingCenter[i] = computeCenter(((CellularRegion) siblings.get(i)).getTopLeft(),
                        ((CellularRegion) siblings.get(i)).getBotRight());

                // compute distance between current location and each sibling's center
                distanceFromSiblingToPerson[i] = computeNauticalMiles(currLocation, siblingCenter[i]);
            }

            // iterate through array of distances from sibling to person and find min and index of min
            int minIndex = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0 ; i < distanceFromSiblingToPerson.length ; i++) {
                if (distanceFromSiblingToPerson[i] < minDistance) {
                    minIndex = i;
                    minDistance = distanceFromSiblingToPerson[i];
                }
            }

            // set current location to center of sibling with min distance
            currLocation = siblingCenter[minIndex];
            // return region associated with min index
            return siblings.get(minIndex);
//        }
//        // siblings are all empty, go back to parent and try again
//        return ((CellularRegion) currRegion).getParent();
    }


    /**
     * Computes the distance between two coordinates in nautical miles using the Haversine formula
     *
     * @param coord1 coordinate 1
     * @param coord2 coordinate 2
     * @return distance between locations 1 and 2 in nautical miles
     */
    private int computeNauticalMiles(Coordinate coord1, Coordinate coord2) {
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
     * Computes center given top left and bottom right coordinates
     * @param topLeft Coordinate
     * @param botRight Coordinate
     * @return center Coordinate
     */
    private Coordinate computeCenter(Coordinate topLeft, Coordinate botRight) {
        // calculate center coordinate
        double cenLat = (topLeft.getLatitude() + botRight.getLatitude()) / 2;
        double cenLong = (topLeft.getLongitude() + botRight.getLongitude()) / 2;
        return new Coordinate(cenLat, cenLong);
    }
}
