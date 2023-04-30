import java.util.ArrayList;
import java.util.List;

public class QuadTree implements IQuadTree {

    /**
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
     */

    //  The top left and bottom right points delimiting this block
    private Point topLeft;
    private Point botRight;


    private IQuadTree topLeftSq;
    private IQuadTree topRightSq;
    private IQuadTree botLeftSq;
    private IQuadTree botRightSq;
    private IQuadTree parent;

    private List<CellTower> towersInBlock;
    private CellTower tower;


    /**
     * No-argument constructor. This should:
     * - Initialize the top left and bottom right to two dummy points at (0, 0)
     * - Set the depth to be 0
     * - Set all parent and child pointers to null
     *
     * Even if you don't use this constructor anywhere, it may be useful for testing.
     */
    public QuadTree() {
        // automatically adjust for the first block to be 
        this.setTopLeft(new Point(0,0));
        this.setBotRight(new Point(0,0));
        this.setTowersInBlock(null);
        this.setBotLeftSq(null);
        this.setBotRightSq(null);
        this.setTopLeftSq(null);
        this.setTopRightSq(null);
        this.setParent(null);

    }
    
    
    private void setParent(IQuadTree parent) {
        this.parent = parent;
    }

    @Override
    public void smash(int maxDepth) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<IQuadTree> children() {
         List<IQuadTree> outList = new ArrayList<>();

        if (this.topLeftSq != null) {
            outList.add(topLeftSq);
            outList.add(topRightSq);
            outList.add(botRightSq);
            outList.add(botLeftSq);
        }

        return outList;
    }

    @Override
    public CellTower getCellTower() {
        // TODO Auto-generated method stub
        return this.tower;
    }

    @Override
    public void setCellTower(CellTower ct) {
        // TODO Auto-generated method stub
        this.tower = ct;
    }

    @Override
    public Point getTopLeft() {
        // TODO Auto-generated method stub
        return this.topLeft;
    }

    @Override
    public Point getBotRight() {
        // TODO Auto-generated method stub
        return this.botRight;
    }

    @Override
    public boolean isLeaf() {
        if (this.children().size() == 0) {
            return false;
        }
        return true;
    }


    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setBotRight(Point botRight) {
        this.botRight = botRight;
    }


    public List<CellTower> getTowersInBlock() {
        return towersInBlock;
    }


    public void setTowersInBlock(List<CellTower> towersInBlock) {
        this.towersInBlock = towersInBlock;
    }


    @Override
    public IQuadTree getTopLeftSq() {
        return this.topLeftSq;
    }


    @Override
    public IQuadTree getTopRightSq() {
        return this.topRightSq;
    }


    @Override
    public IQuadTree getBotLeftSq() {
        return this.botLeftSq;
    }


    @Override
    public IQuadTree getBotRighSq() {
        return this.botRightSq;
    }


    @Override
    public List<CellTower> getTowersList() {
        return this.towersInBlock;
    }


    @Override
    public void setTowersList(List<CellTower> towerList) {
        this.towersInBlock = towerList;      
    }


    @Override
    public void setTopLeftSq(IQuadTree topLeftSq) {
        this.topLeftSq = topLeftSq;
    }


    @Override
    public void setTopRightSq(IQuadTree topRightSq) {
        this.topRightSq = topRightSq;
    }


    @Override
    public void setBotLeftSq(IQuadTree botLeftSq) {
        this.botLeftSq = botLeftSq;

    }


    @Override
    public void setBotRightSq(IQuadTree botRightSq) {
        this.botRightSq = botRightSq;

    }

    @Override
    public List<IQuadTree> getSiblings() {
        List<IQuadTree> siblings = this.parent.children();
        siblings.remove(this);
        return siblings;
        
    }

}
