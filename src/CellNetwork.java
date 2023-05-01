import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a concrete implementation of the ICellNetwork interface.
 */

public class CellNetwork implements ICellNetwork {

    // Map mapping ID to CellTower object from Processor class
    private Map<Integer, CellTower> IdToCellTowerMap;
    // Cell network graph
    private Graph cellNetworkGraph;
    // Map mapping cell tower id to graph vertex
    private Map<Integer, Integer> IdToVertexMap;


    /**
     * Constructor
     */
    public CellNetwork(Processor processor) {
        // get map from processor object
        IdToCellTowerMap = processor.getTowerMap();

        // initialize field mapping cell tower id to vertex in graph
        IdToVertexMap = new HashMap<>();

        // Initialize graph with number of vertices matching the number of cell towers
        cellNetworkGraph = new GraphM();
        cellNetworkGraph.init(IdToCellTowerMap.size());

        // assign vertices to graph
        assignVertices();

        // create edges in graph
        generateTowerConnections();
    }


    // getter for cellNetworkGraph
    public Graph getCellNetworkGraph() {
        return cellNetworkGraph;
    }

    // getter for IdToVertexMap
    public Map<Integer, Integer> getIdToVertexMap() {
        return IdToVertexMap;
    }


    /**
     * Assigns each cell tower in dataset to vertex in graph
     */
    @Override
    public void assignVertices() {
        int vertexCounter = 0;
        for (Map.Entry<Integer, CellTower> cellTower : IdToCellTowerMap.entrySet()) {
            // map cell tower's id to a vertex in the graph
            IdToVertexMap.put(cellTower.getKey(), vertexCounter);
            // place a cell tower object at each vertex
            cellNetworkGraph.setValue(vertexCounter, cellTower.getValue());
            // update vertexCounter to go to next vertex in graph
            vertexCounter++;
        }
    }


    /**
     * Calculate distance between each cell tower in graph
     */
    @Override
    public void generateTowerConnections() {
        // iterate through every vertex pair in graph and generate edge
        for (int i = 0 ; i < cellNetworkGraph.nodeCount() ; i++) {
            for (int j = 0 ; j < cellNetworkGraph.nodeCount() ; j++) {
                // assign edge to vertex pair if
                // - i and j are not the same vertex
                // - edge does not exist between i and j in graph
                if (i != j && !cellNetworkGraph.hasEdge(i, j)) {
                    // get cell towers associated with vertices i and j
                    CellTower v1 = (CellTower) cellNetworkGraph.getValue(i);
                    CellTower v2 = (CellTower) cellNetworkGraph.getValue(j);

                    // calculate edge weight between both towers
                    int edgeWt = computeEdgeWeight(v1, v2);

                    // add edge in both directions since networks are bidirectional
                    cellNetworkGraph.addEdge(i, j, edgeWt);
                    cellNetworkGraph.addEdge(j, i, edgeWt);
                }
            }
        }
    }


    /**
     * Cost function to adjust distance between two cell towers by squaring distance between towers
     * An additional penalty is applied if:
     * - cell towers owned by different providers try to connect
     * - cell towers are > 100 miles apart (no connection possible in this case)
     *
     * @return adjusted cost
     */
    @Override
    public int computeEdgeWeight(CellTower tower1, CellTower tower2) {
        // calculate distance between towers
        int edgeWt = ICellNetwork.computeNauticalMiles(tower1, tower2);

        // square distance calculated to penalize cell towers that are further apart
        edgeWt = (int) Math.pow(edgeWt, 1.1);

        // add penalty to distance if cell towers are licensed to different providers
        if (!tower1.getLicense().equals(tower2.getLicense())) {
            edgeWt = (int) (edgeWt * 1.05);
        }

        return edgeWt;
    }
}
