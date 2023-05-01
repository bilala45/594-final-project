import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PathTest {

    private ICellNetwork cellNetwork;

    @Before
    public void setUp() {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        Processor processor = new Processor("atlantic-test-data.csv");

        // Construct graph associated with CellTower objects for routing
        cellNetwork = new CellNetwork(processor);
    }

    @Test
    public void testPathWithDifferentCellTowerIds() {
        // path between different cell towers
        Path path = new Path(15052, 34696, cellNetwork);
        List<CellTower> towerPath = path.calculatePath();
        Assert.assertEquals(7, towerPath.size());
        System.out.println(path);
    }


    @Test
    public void testPathWithSameCellTowerIds() {
        // no path between same cell towers
        Path path = new Path(15052, 15052, cellNetwork);
        List<CellTower> towerPath = path.calculatePath();
        Assert.assertEquals(0, towerPath.size());
    }


    @Test
    public void testToString() {
        // no path between same cell towers
        Path path = new Path(15052, 15052, cellNetwork);
        String string = path.toString();
        Assert.assertEquals("Start:\nEnd:\n", string);
    }
}