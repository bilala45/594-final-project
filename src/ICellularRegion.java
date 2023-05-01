import java.util.List;

public interface ICellularRegion {

        //  Top left and bottom right points delimiting this QuadTree
//        public static final Coordinate topLeft = new Coordinate(-85, 50);
//        public static final Coordinate botRight = new Coordinate(-69, 34);
        public static final Coordinate topLeft = new Coordinate(50, -85);
        public static final Coordinate botRight = new Coordinate(34, -69);


        /**
         * Get list of towers in region
         * @return list of towers
         */
        public List<CellTower> getTowersInRegion();


        /**
         * Set list of towers in region
         */
        public void setTowersInRegion(List<CellTower> towersInRegion);


        /**
         * Divides this cellular region into 4 sub-regions
         */
        public void subDivide();


        /**
         * Checks if a coordinate is within this region's area
         * @param coordinate location to check within region's area
         * @return true if coordinate is within area, false otherwise
         */
        public boolean isCoordinateWithinRegion(Coordinate coordinate);


        /**
         * List of children of this region (sub-regions of this region)
         * @return List of children of this region
         */
        public List<ICellularRegion> children();


        /**
         * Checks if this region is a leaf
         * (A cellular region that contains 0 or 1 tower is a leaf)
         * @return true if leaf, false otherwise
         */
        public boolean isLeaf();


        /**
         * Get list of sibling sub-regions
         * @return List of neighboring sub-regions
         */
        public List<ICellularRegion> getSiblings();
        
     
       /**
        * Getter for parent's region
        * @return parent of the given region
        */
        public ICellularRegion getParent();
        
}


