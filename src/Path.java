import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class provides a concrete implementation of the IPath interface.
 */

public class Path implements IPath {

    // output list of cell tower objects
    private List<CellTower> shortestPath;
    // cell network graph
    private Graph graph;
    // id to vertex map
    private Map<Integer, Integer> idToVertexMap;
    // cell tower ids to create path between
    private int cellTowerId1;
    private int cellTowerId2;


    // constructor
    public Path(int cellTowerId1, int cellTowerId2, ICellNetwork network) {
        // assign cell tower ids to instance variables
        this.cellTowerId1 = cellTowerId1;
        this.cellTowerId2 = cellTowerId2;
        // get graph from cell tower network
        graph = ((CellNetwork) network).getCellNetworkGraph();
        // get id to vertex map
        idToVertexMap = ((CellNetwork) network).getIdToVertexMap();
        // stores path from source to destination vertex
        shortestPath = new ArrayList<>();

//        int[][] graphm = ((GraphM) graph).getMatrix();
//        for (int i = 0 ; i < graph.nodeCount() ; i++) {
//            System.out.print(((CellTower) graph.getValue(i)).getId() + "\t");
//        }
//        System.out.println();
//
//        for (int i = 0 ; i < graphm.length ; i++) {
//            for (int j = 0 ; j < graphm[i].length ; j++) {
//                System.out.print(graphm[i][j] + "\t");
//            }
//            System.out.println();
//        }
    }


    /**
     * return the shortest path between two cell towers
     * include the source and destination vertices in your collection
     *
     * @return collection of nodes to follow to go from source to destination
     */
    public List<CellTower> calculatePath() {
        // get vertices associated with cell tower ids
        int source = idToVertexMap.get(cellTowerId1);
        int destination = idToVertexMap.get(cellTowerId2);

        // initialize shortest array to store shortest paths to each vertex
        int[] shortest = new int[graph.nodeCount()];
        // initialize pred array to keep track of previous array in path for vertex
        Integer[] pred = new Integer[graph.nodeCount()];

        // initialize each value in shortest to infinity except seed
        for (int i = 1 ; i < shortest.length ; i++) {
            shortest[i] = INFINITY;
        }
        shortest[source] = 0;

        // initialize a priority queue to store vertices in graph
        PriorityQueue<Integer> pq =
                new PriorityQueue<>((o1, o2) -> Integer.compare(shortest[o1], shortest[o2]));

        // add each index to priority queue
        for (int i = 1 ; i < graph.nodeCount() ; i++) {
            pq.offer(i);
        }

        // iterate until pq is empty
        while (!pq.isEmpty()) {
            int node = pq.poll();
            // System.out.println(graph.getValue(node)); //Added this print
            // iterate through node's neighbors
            for (int neighbor : graph.neighbors(node)) {
                // update shortest of neighbor if path to neighbor is shorter
                // via node and edge between node and neighbor
                if (shortest[node] + graph.weight(node, neighbor) < shortest[neighbor]) {
                    shortest[neighbor] = shortest[node] + graph.weight(node, neighbor);
                    pred[neighbor] = node;
                    // remove and re-insert updated neighbor
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }
            }
        }

        // return null if there's no path to the destination node
        if (pred[destination] == null) {
            return shortestPath;
        }
        // add destination vertex to list
        shortestPath.add((CellTower) graph.getValue(destination));
        // get vertex preceding destination
        int curr = pred[destination];
        // backtrack through pred until source vertex is reached
        while (curr != source) {
            // add curr to front of list
            shortestPath.add(0, (CellTower) graph.getValue(curr));
            curr = pred[curr];
        }
        // add source vertex to start of path
        shortestPath.add(0, (CellTower) graph.getValue(source));

        return shortestPath;
    }


    @Override
    public String toString() {
        String path = "\n################ ROUTING CALL ################\n";
        path += "Start\n";
        for (int i = 0 ; i < shortestPath.size() - 1 ; i++) {
            path = path + shortestPath.get(i) + "\n\t|\n";
        }
        path = path + shortestPath.get(shortestPath.size() - 1) + "\n";
        path += "End";
        return path;
    }
}
