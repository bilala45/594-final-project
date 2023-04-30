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
        // processor should be initialized with atlantic test dataset
        Processor processor = new Processor();
        processor.readCSVFile("./atlantic-test-data.csv");
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
        // assign vertices to graph
        cellNetwork.assignVertices();
        // get graph created by assigning vertices
        Graph graph = cellNetwork.getCellNetworkGraph();
        // ensure number of vertices matches number of cell towers in data set
        Assert.assertEquals(11, graph.nodeCount());
    }

    @Test
    public void generateTowerConnections() {
        // assign vertices to graph
        cellNetwork.assignVertices();
        // assign edges to graph
        cellNetwork.generateTowerConnections();
        // get graph created by assigning vertices
        Graph graph = cellNetwork.getCellNetworkGraph();

        // ensure edge exists between vertices
        Assert.assertTrue(graph.hasEdge(0, 2));
        // ensure edge is undirected
        Assert.assertEquals(graph.weight(0, 2), graph.weight(2, 0));
    }

    @Test
    public void testComputeEdgeWeight() {
        // create two cell tower objects
        CellTower tower1 = new CellTower(10, 39.38, 74.45,
                "Verizon", "", "", "", "");
        CellTower tower2 = new CellTower(11, 39.58, 74.87,
                "AT&T", "", "", "", "");

        int edgeWt = cellNetwork.computeEdgeWeight(tower1, tower2);
        Assert.assertEquals(555, edgeWt);
    }
}