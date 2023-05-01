import java.util.ArrayList;
import java.util.List;

public interface ICellularRegion {

        //  The top left and bottom right points delimiting this QuadTree
        public static final Coordinate topLeft = new Coordinate(-85, 50);
        public static final Coordinate botRight = new Coordinate(-69, 34);



        /**
         * Divide this cellular region into 4 sub-regions
         */
        public void subDivide();


        /**
         * Checks if a coordinate is within this region's area
         * @param coordinate
         * @return boolean
         */
        public boolean isCoordinateWithinRegion(Coordinate coordinate);


        /**
         * List of children of this region
         * @return
         */
        public List<ICellularRegion> children();


        /**
         * Checks if CellularRegion is a leaf (has found nearest tower because
         * there is only 0 or 1 tower in region)
         * @return boolean
         */
        public boolean isLeaf();


        /**
         * Get list of towers in region
         * @return
         */
        public List<CellTower> getTowersInRegion();


        /**
         * Get list of sibling sub-regions
         * @return
         */
        public List<ICellularRegion> getSiblings();


        /**
         * Get list of towers in region
         * @return
         */
        void setTowersInRegion(List<CellTower> towersinRegion);


}


