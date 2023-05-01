/**
 * This class is used to test the functionality of the CellNetwork class
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CellNetworkTest {

    private CellNetwork cellNetwork;

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
    public void testComputeNauticalMiles() {
        // create two cell tower objects
        CellTower tower1 = new CellTower(10, 39.38, 74.45,
                "Verizon", "", "", "", "");
        CellTower tower2 = new CellTower(11, 39.58, 74.87,
                    "AT&T", "", "", "", "");

        int distance = ICellNetwork.computeNauticalMiles(tower1, tower2);
        Assert.assertEquals(23, distance);
    }


    @Test
    public void assignVertices() {
        // get graph created by cell network constructor
        Graph graph = cellNetwork.getCellNetworkGraph();
        // ensure number of vertices matches number of cell towers in data set
        Assert.assertEquals(11, graph.nodeCount());
    }


    @Test
    public void generateTowerConnections() {
        // get graph created by cell network constructor
        Graph graph = cellNetwork.getCellNetworkGraph();

        // ensure edge exists between vertices
        Assert.assertTrue(graph.hasEdge(0, 2));
        // ensure edge is undirected
        Assert.assertEquals(graph.weight(0, 2), graph.weight(2, 0));
    }

    @Test
    public void testComputeEdgeWeightInvalid() {
        // create two cell tower objects
        CellTower tower1 = new CellTower(10, 39.38, 74.45,
                "Verizon", "", "", "", "");
        CellTower tower2 = new CellTower(11, 39.58, 74.87,
                "AT&T", "", "", "", "");

        int edgeWt = cellNetwork.computeEdgeWeight(tower1, tower2);
        Assert.assertEquals(-1, edgeWt);
    }

    @Test
    public void testComputeEdgeWeightValid() {
        // create two cell tower objects
        CellTower tower1 = new CellTower(10, 39.38, 74.45,
                "Verizon", "", "", "", "");
        CellTower tower2 = new CellTower(11, 39.52, 74.49,
                "AT&T", "", "", "", "");

        int edgeWt = cellNetwork.computeEdgeWeight(tower1, tower2);
        Assert.assertEquals(85, edgeWt);
    }
}