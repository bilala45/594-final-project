import java.util.*;
import java.io.*;
interface IProcessor {

    /**
     * used to process CSV file line by line and create a Map of cell tower id 
     * to cell tower instance
     * @return void
     */
    
    public void readCSVFile(String csvFilePath); 
    
    /**
     * used to return Map of CellTower id 
     * to cell tower object 
     * @return Map<Integer, CellTower>
     */
    
    public Map<Integer, CellTower> getTowerMap();
    public List<CellTower> getTowerList();

}
