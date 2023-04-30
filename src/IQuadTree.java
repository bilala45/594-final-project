import java.util.List;

public interface IQuadTree {

    /**
     * === Class Attributes === 
     * bounds: 
     *  - topLeft 
     *  - botRight 
     *
     *  color: 
     *  - color 
     *  If this block is not subdivided, <color> stores its color.
     *   Otherwise, <color> is <null> and this  block's sublocks 
     *   store their individual colors. 
     *   
     * level/depth: 

     * 
     * children: 
     *  - topLeftTree 
     *  - topRightTree 
     *  - botLeftTree 
     *  - botRightTree 
     *  
     *  The blocks into which this block is subdivided. 
     *  
     *  The children are returned in this order: 
     * upper-left child (NE),
     * upper-right child (NW), 
     * lower-right child (SW), 
     * lower-left child (SE).
     * 
     * parent: 
     *  - parent 
     *  The block that this block is directly within. 
     *
     *  Create getter and setters for all private fields
     *  use eclipse naming suggestion.
     *  
     *  
     * ===Representation Invariants === 
     * 
     * - children.size() == 0 or children.size() == 4 
     * 
     * - If this Block has children, 
     *      - their max_depth is the same as that of this Block, 
     *      - their size is half that of this Block, 
     *      - their level is one greater than that of this Block, 
     *      - their position is determined by the position and
     *        size of this Block, as defined in the Assignment handout, and 
     *      - this Block's color is null 
     *      
     * - If this Block has no children, 
     *      - its color is not null 
     *      - level <= max_depth 
     *      
     *===Constructor === 
     *
     *The Block class constructor have the following signature 
     *Block(Point topLeft, Point botRight, int depth, Block parent)
     **/
    

        /**
         * smash this block into 4 sub block of random color. the depth of the new
         * blocks should be less than maximum depth
         * 
         * @param maxDepth the max depth of this board/quadtree
         */
        public void smash(int maxDepth);


        /**
         * used by {@link IGame#randomInit()} random_init
         * to keep track of sub blocks.
         * 
         * The children are returned in this order: 
         * upper-left child (NE),
         * upper-right child (NW), 
         * lower-right child (SW), 
         * lower-left child (SE).
         * 
         * @return the list of all the children of this block (clockwise order,
         *         starting with top left block)
         */
        public List<IQuadTree> children();



        /**
         * @return the color of this block
         */
        public CellTower getCellTower();


        /**
         * Sets the cell tower contained in the block
         * 
         * @param ct. the new cell tower
         */
        public void setCellTower(CellTower ct);


        /**
         * @return top left point
         */
        public Point getTopLeft();


        /**
         * @return Bottom right point
         */
        public Point getBotRight();


        /**
         * @return true if this area has no children/ no subdivisions
         */
        public boolean isLeaf();
        
        /**
         *  Sets the top left sq
         */
        public void setTopLeftSq(IQuadTree topLeftSq);

        /**
         *  Sets the top right square
         */
        public void setTopRightSq(IQuadTree topRightSq);
        
        /**
         *  Sets the bottom left square
         */
        public void setBotLeftSq(IQuadTree botLeftSq);
        
        /**
         *  Sets the bottom right square
         */
        public void setBotRightSq(IQuadTree botRightSq);
        
        /**
         * @return the top left sub block (NE)
         */
        public IQuadTree getTopLeftSq();

        /**
         * @return the top right sub block (NW)
         */
        public IQuadTree getTopRightSq();

        /**
         * @return the bottom left sub block (SE)
         */
        public IQuadTree getBotLeftSq();

        /**
         * @return the bottom right sub block (SW)
         */
        public IQuadTree getBotRighSq();
        

        /**
         * @return the list of cell towers within the given area
         */
        public List<CellTower> getTowersList();
        
        /**
         * sets the list of cell towers within the given area
         */
        public void setTowersList(List<CellTower> towerList);
        
        /**
         * Sets the parent of the block
         */
        public List<IQuadTree> getSiblings();
        
        
    }


