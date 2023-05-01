import java.util.ArrayList;
import java.util.List;

public class CellularRegion implements ICellularRegion {

    // top left and bottom right coordinates of cellular region
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
     * No argument constructor for testing purposes
     */
    public CellularRegion() {
        // set top left and bot right fields for region
        this.topLeft = new Coordinate(0,0);
        this.botRight = new Coordinate(0, 0);
    }


    /**
     * Create a new cellular region object.
     * @param topLeft   top left coordinate of region
     * @param botRight   bottom right coordinate of region
     * @param parent of this region
     */
    public CellularRegion(Coordinate topLeft, Coordinate botRight, CellularRegion parent) {
        // initialize top left and bottom right Points to input top left and bottom right points
        this.topLeft = topLeft;
        this.botRight = botRight;

        // initialize parent to parent arg
        this.parent = parent;

        // empty list to store towers
        towersInRegion = new ArrayList<>();
    }


    // Getters for each child
    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getBotRight() {
        return botRight;
    }

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
     * Divide this cellular region into 4 sub-regions
     */
    @Override
    public void subDivide() {
        // smash if this block's depth is less than maxDepth and block being smashed is a leaf
        if (towersInRegion.size() > 1) {
            // get center coordinate
            Coordinate center = computeCenter(topLeft, botRight);

            // create 4 new Block objects with updated coordinates and random colors
            topLeftSq = new CellularRegion(topLeft, center, this);
            topRightSq = new CellularRegion(new Coordinate(center.getLatitude(), topLeft.getLongitude()),
                    new Coordinate(botRight.getLatitude(), center.getLongitude()), this);
            botRightSq = new CellularRegion(center, botRight, this);
            botLeftSq = new CellularRegion(new Coordinate(topLeft.getLatitude(), center.getLongitude()),
                    new Coordinate(center.getLatitude(), botRight.getLongitude()), this);

            assignTowersToRegion();
        }
    }


    /**
     *
     */
    private void assignTowersToRegion() {
        // get latitude and longitude of current tower
        for (CellTower tower : towersInRegion) {
            Coordinate towerCoord = new Coordinate(tower.getLat(), tower.getLong());

            // check which child tower should be assigned to
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
     * @param coordinate
     * @return boolean
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
     * List of children of this region
     * @return
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
        if (towersInRegion.size() == 0 || towersInRegion.size() == 1) {
            return true;
        }
        return false;
    }



    /**
     * Get list of towers in region
     * @return
     */
    @Override
    public List<CellTower> getTowersInRegion() {
        return towersInRegion;
    }
    
    /**
     * Get list of towers in region
     * @return
     */
    @Override
    public void setTowersInRegion(List<CellTower> towersinRegion) {
        this.towersInRegion = towersinRegion;
    }


    /**
     * Get list of sibling sub-regions
     * @return
     */
    @Override
    public List<ICellularRegion> getSiblings() {
        // get children of parent, including this object
        List<ICellularRegion> siblings = parent.children();
        // remove this object from sibling list
        siblings.remove(this);
        return siblings;
    }

}
