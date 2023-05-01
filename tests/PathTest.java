import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PathTest {

    private Path path;

    @Before
    public void setUp() {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        Processor processor = new Processor("atlantic-test-data.csv");

        // Construct graph associated with CellTower objects for routing
        ICellNetwork cellNetwork = new CellNetwork(processor);

        path = new Path(15052, 34696, cellNetwork);
    }

    @Test
    public void testPath() {
        List<CellTower> towerPath = path.path();
        System.out.println(path);
    }
}