import java.util.*;
import java.awt.Color;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Implements logic for program
 */
public class Processor implements IProcessor {

    // Maps cell tower ID to CellTower object
    private Map<Integer, CellTower> idToTower;
    // List of CellTower objects
    private List<CellTower> cellTowers;
    // root of quad tree
    private ICellularRegion root;

    /**
     * Processor constructor
     */
    public Processor(String csvFilePath) {
        idToTower = new HashMap<>();
        cellTowers = new ArrayList<>();
        // populate tower list and map
        readCSVFile(csvFilePath);

        // build quad tree
        buildQuadTree();
    }


    // Processor getters
    public Map<Integer, CellTower> getTowerMap() {
        return idToTower;
    }

    public List<CellTower> getTowerList() {
        return cellTowers;
    }

    public ICellularRegion getRoot() {
        return root;
    }


    /**
     * Read from CSV file, initializing list and map fields of CellTower objects
     * @param csvFilePath Path of CSV file to read from
     */
    public void readCSVFile(String csvFilePath) {
        try (FileReader fileReader = new FileReader(csvFilePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            // read lines from file until EOF
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] fields = line.split(",");
                // Extract ID, latitude, and longitude fields for each line
                int id = Integer.parseInt(fields[0]);
                double lat = Double.valueOf(fields[6]);
                double longt = Double.valueOf(fields[7]);

                // initialize CellTower object for each line
                CellTower tower = new CellTower(id, 
                        lat, longt, fields[1], fields[2], 
                        fields[3], fields[4], fields[5]);

                // add CellTower objects to map and list
                this.cellTowers.add(tower);
                this.idToTower.put(tower.getId(), tower);
            }
        }
        // handle error with reading from file
        catch (IOException e) {
            System.err.println("Error while reading CSV file: " + e.getMessage());
        }
    }


    /**
     * Constructs QuadTree from list of CellTower objects
     * using pre-defined boundaries for latitude and longitude
     */
    public void buildQuadTree() {
        // Initialize root of quad tree using statically defined boundaries for lat and long
        root = new CellularRegion(ICellularRegion.topLeft, ICellularRegion.botRight, null);
        root.setTowersInRegion(cellTowers);

        // Create queue of ICellularRegion blocks to sub-divide and pass root to queue
        Queue<ICellularRegion> q = new LinkedList<>();
        q.offer(root);

        // Iterate until queue is empty
        while (!q.isEmpty()) {
            // Poll from queue
            ICellularRegion curr = q.poll();

            // subdivide the region as long as the region itself isn't a leaf
            if (!curr.isLeaf()) {
                curr.subDivide();

                // if subdivision was necessary, iterate through the region's children and add to queue
                // this handles potential future subdivision of region
                for (ICellularRegion subRegion : curr.children()) {
                    q.offer(subRegion);
                }
            }
        }
    }


    public static void main(String[] args) {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        Processor processor = new Processor("northeast-data.csv");

        // Construct graph associated with CellTower objects for routing
        ICellNetwork cellNetwork = new CellNetwork(processor);

        // initialize scanner
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("---  ✆ MAKING A CALL BETWEEN TWO PEOPLE IN THE NORTHEAST US ✆ ---\n");
        System.out.println("(enter latitudes and longitudes within NY, NJ, PA, MA, RI)");
        System.out.println("------------------------------------------- \n");
       
        
        // get person 1's name, coordinate and carrier and initialize corresponding Person object
        System.out.println("Enter Person 1's name: ");
        String name = scanner.nextLine();
        name = name.strip();

        System.out.println("Enter Person 1's latitude ");
        String latSt = scanner.nextLine();
        double lat1 = Double.parseDouble(latSt);

        System.out.println("Enter Person 1's longitude ");
        String longSt = scanner.nextLine();
        double long1 = Double.parseDouble(longSt);

        System.out.println("Enter Person 1's carrier ");
        String carrier = scanner.nextLine();
        carrier = carrier.trim();
        
        Person person1 = new Person(name, carrier, lat1, long1, processor.getRoot());

        // get person 2's name, coordinate and carrier and initialize corresponding Person object
        System.out.println("Enter Person 2's name: ");
        String name2 = scanner.nextLine();
        name2 = name2.strip();

        System.out.println("Enter Person 2's latitude ");
        String latSt2 = scanner.nextLine();
        double lat2 = Double.parseDouble(latSt2);

        System.out.println("Enter Person 2's longitude ");
        String longSt2 = scanner.nextLine();
        double long2 = Double.parseDouble(longSt2);

        System.out.println("Enter Person 2's carrier ");
        String carrier2 = scanner.nextLine();
        carrier2 = carrier2.trim();

        Person person2 = new Person(name2, carrier2, lat2, long2, processor.getRoot());

        // close scanner
        scanner.close();

        // check if both Person objects can connect
        if (person1.getCanConnect() && person2.getCanConnect()) {
            // compute cell tower path between Person 1 and Person 2 and print path
            Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), cellNetwork);
            path.calculatePath();
            System.out.println(path);
        } else {
            System.out.println("This call could not be directed. Please try again.");
        }
    }
}




