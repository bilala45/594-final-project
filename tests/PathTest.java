import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PathTest {

    private Path path;

    @Before
    public void setUp() {
        Processor processor = new Processor();
        processor.readCSVFile("./atlantic-test-data.csv");
        CellNetwork cellNetwork = new CellNetwork(processor);
        cellNetwork.networkInit();
        path = new Path(15052, 34696, cellNetwork);
    }

    @Test
    public void testPath() {
        List<CellTower> towerPath = path.path();
        System.out.println(path);
    }
}