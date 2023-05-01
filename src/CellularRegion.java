import java.util.ArrayList;
import java.util.List;

public class CellularRegion implements ICellularRegion {

    // top left and bottom right coordinates of a cellular region
    private Coordinate topLeft;
    private Coordinate botRight;

    // children of current cellular region
    private ICellularRegion topLeftSq;
    private ICellularRegion topRightSq;
    private ICellularRegion botLeftSq;
    private ICellularRegion botRightSq;

    // parent of current cellular region
    private ICellularRegion parent;

    // list of towers in cellular region
    private List<CellTower> towersInRegion;


    /**
     * Create a new cellular region object
     * @param topLeft top left coordinate of region
     * @param botRight bottom right coordinate of region
     * @param parent of this region
     */
    public CellularRegion(Coordinate topLeft, Coordinate botRight, CellularRegion parent) {
        // initialize top left and bottom right coordinates to topLeft and botRight arguments
        this.topLeft = topLeft;
        this.botRight = botRight;

        // initialize parent to parent arg
        this.parent = parent;

        // initialize empty list to store towers
        towersInRegion = new ArrayList<>();
    }


    // getters for this region's coordinates
    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getBotRight() {
        return botRight;
    }


    // Getters for each sub-region of this region
    public ICellularRegion getTopLeftSq() {
        return topLeftSq;
    }

    public ICellularRegion getTopRightSq() {
        return topRightSq;
    }

    public ICellularRegion getBotRightSq() {
        return botRightSq;
    }

    public ICellularRegion getBotLeftSq() {
        return botLeftSq;
    }


    /**
     * Get list of towers in region
     * @return list of towers
     */
    @Override
    public List<CellTower> getTowersInRegion() {
        return towersInRegion;
    }


    /**
     * Set list of towers in region
     */
    @Override
    public void setTowersInRegion(List<CellTower> towersInRegion) {
        this.towersInRegion = towersInRegion;
    }


    /**
     * Divides this cellular region into 4 sub-regions
     */
    @Override
    public void subDivide() {
        // subDivide is only called if the current region is a leaf
        // get center coordinate
        Coordinate center = computeCenter(topLeft, botRight);

        // create 4 new subregions with new coordinates and this region as its parent
        topLeftSq = new CellularRegion(topLeft, center, this);
        topRightSq = new CellularRegion(new Coordinate(center.getLatitude(), topLeft.getLongitude()),
                new Coordinate(botRight.getLatitude(), center.getLongitude()), this);
        botRightSq = new CellularRegion(center, botRight, this);
        botLeftSq = new CellularRegion(new Coordinate(topLeft.getLatitude(), center.getLongitude()),
                new Coordinate(center.getLatitude(), botRight.getLongitude()), this);

        // assign towers from this region into each subregion
        assignTowersToRegion();
    }


    /**
     * Divides towers in this cellular region across 4 sub-regions based on coordinates of each tower
     */
    private void assignTowersToRegion() {
        // iterate each tower in this region and determine which subregion the tower should be assigned to
        // get latitude and longitude of current tower
        for (CellTower tower : towersInRegion) {
            // initialize coordinates for tower
            Coordinate towerCoord = new Coordinate(tower.getLat(), tower.getLong());

            // compare each tower's coordinates to the bounds of each region and add to
            // corresponding region's list of towers
            if (topLeftSq.isCoordinateWithinRegion(towerCoord)) {
                topLeftSq.getTowersInRegion().add(tower);
            } else if (topRightSq.isCoordinateWithinRegion(towerCoord)) {
                topRightSq.getTowersInRegion().add(tower);
            } else if (botRightSq.isCoordinateWithinRegion(towerCoord)) {
                botRightSq.getTowersInRegion().add(tower);
            } else {
                botLeftSq.getTowersInRegion().add(tower);
            }
        }
    }


    /**
     * Checks if a coordinate is within this region's area
     * @param coordinate location to check within region's area
     * @return true if coordinate is within area, false otherwise
     */
    public boolean isCoordinateWithinRegion(Coordinate coordinate) {
        // get lat and long for each coordinate
        double latitude = coordinate.getLatitude();
        double longitude = coordinate.getLongitude();

        // return true if coordinate is within bounds of cellular region
        if ((longitude >= botRight.getLongitude() && longitude <= topLeft.getLongitude())
            && (latitude <= botRight.getLatitude() && latitude >= topLeft.getLatitude())) {
            return true;
        }
        return false;
    }


    /**
     * Computes center of region given top left and bottom right coordinates
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


    /**
     * List of children of this region (sub-regions of this region)
     * @return List of children of this region
     */
    @Override
    public List<ICellularRegion> children() {
        // list of siblings of this block
        List<ICellularRegion> list = new ArrayList<>();

        // cellular region has subdivisions if it is not a leaf region
        if (!isLeaf()) {
            list.add(topLeftSq);
            list.add(topRightSq);
            list.add(botRightSq);
            list.add(botLeftSq);
        }

        return list;
    }


    /**
     * Checks if CellularRegion is a leaf (has found nearest tower because
     * there is only 0 or 1 tower in region)
     * @return boolean
     */
    @Override
    public boolean isLeaf() {
        // CellularRegion is a leaf if 0 or 1 towers are in the region
        if (towersInRegion.size() == 0 || towersInRegion.size() == 1) {
            return true;
        }
        return false;
    }


    /**
     * Get list of sibling sub-regions
     * @return List of neighboring sub-regions
     */
    @Override
    public List<ICellularRegion> getSiblings() {
        // Get all children of parent region
        List<ICellularRegion> siblings = parent.children();
        // remove this sub-region from sibling list
        siblings.remove(this);
        return siblings;
    }

}
