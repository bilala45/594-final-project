import java.util.*;
import java.awt.Color;
import java.io.*;


public class Processor implements IProcessor {
    private Map<Integer, CellTower> idToTower = new HashMap<>();
    private List<CellTower> cellTowers = new ArrayList<>();
    //root of quad tree
    private ICellularRegion root;


    //Swap Comment 1
    public Processor(){}

    public  void readCSVFile(String csvFilePath) {
        try (FileReader fileReader = new FileReader(csvFilePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {


            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] fields = line.split(",");
                //System.out.println(line);
                int id = Integer.parseInt(fields[0]);
                double lat = Double.valueOf(fields[6]);
                double longt = Double.valueOf(fields[7]);

                CellTower tower = new CellTower(id, 
                        lat, longt, fields[1], fields[2], 
                        fields[3], fields[4], fields[5]);
                //System.out.print(tower);
                this.cellTowers.add(tower);
                this.idToTower.put(tower.getId(), tower);


            }
        } catch (IOException e) {
            System.err.println("Error while reading CSV file: " + e.getMessage());
        }
    }


    public void buildQuadTree() {
        //initialize cellular region and subdivide until area has cell towers allocated 
        root = new CellularRegion(ICellularRegion.topLeft, ICellularRegion.botRight, null);
        root.setTowersInRegion(cellTowers);
        
        Queue<CellularRegion> q = new LinkedList<CellularRegion>();
        
        q.offer((CellularRegion) root);
        
        while (!q.isEmpty()) {
            
            CellularRegion curr = q.poll();
            curr.subDivide();
            
            for(ICellularRegion c: curr.children()) {
                q.offer((CellularRegion) c);
            }
            
        }
        

    }


    public Map<Integer, CellTower> getTowerMap(){
        return this.idToTower;
    }

    public List<CellTower> getTowerList(){
        return this.cellTowers;
    }


    public static void main(String[] args) {
  
        // read csv file and build map
        String csvFilePath = "atlantic-test-data.csv";
        Processor p = new Processor();
        p.readCSVFile(csvFilePath);
        
        System.out.println("86");

        // build quad tree
        p.buildQuadTree();
        
        System.out.println("91");

        // build graph
        ICellNetwork cn = new CellNetwork(p);

        // initialize network
        ((CellNetwork)cn).networkInit();

        // initialize scanner
        Scanner scanner = new Scanner(System.in);

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
        
        Person person1 = new Person(name, carrier, lat1, long1, p.root);
        
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
        
        scanner.close();
        
        Person person2 = new Person(name2, carrier2, lat2, long2, p.root);
        
        Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), (CellNetwork)cn);
        
        System.out.println(path);
        

    }






}




