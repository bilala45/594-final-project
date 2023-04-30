import java.util.*;
import java.io.*;
public class Processor implements IProcessor {
    private Map<Integer, CellTower> idToTower = new HashMap<>();
    
   
    public Processor(){}
   
    public  void readCSVFile(String csvFilePath) {
        try (FileReader fileReader = new FileReader(csvFilePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] fields = line.split(",");
                System.out.println(line);
                int id = Integer.parseInt(fields[0]);
                double lat = Double.valueOf(fields[6]);
                double longt = Double.valueOf(fields[7]);
                
                CellTower tower = new CellTower(id, 
                        lat, longt, fields[1], fields[2], 
                       fields[3], fields[4], fields[5]);
               System.out.print(tower);
               this.idToTower.put(tower.getId(), tower);
                
                
                
                //Extra processing for Zip-Code if needed
                String inputString = fields[2];
                int index = inputString.indexOf('(');

                // Get Zip Code
                if (index != -1) {
                    // Add 1 to the index to start the substring after the search character
                    String substring = inputString.substring(index + 1, inputString.length() - 2);
                    //int zipCode = Integer.valueOf(substring);
                    System.out.println("Zipcode is : " + substring);
                } else {
                    System.out.println("Character not found in the string.");
                }
//                

//                //Process each field here
//                for (String field : fields) {
//                    System.out.print(field + " | ");
//            
//                }
//                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error while reading CSV file: " + e.getMessage());
        }
    }
    
    public Map<Integer, CellTower> getTowerList(){
        return this.idToTower;
    }
    
    
    public static void main(String[] args) {
        //String csvFilePath = "celltowers_small.csv";
        String csvFilePath = "Northeast_dataset.csv";

        Processor p = new Processor();
        p.readCSVFile(csvFilePath);
        System.out.println(p.getTowerList().toString());
        
        
    }
    
}


