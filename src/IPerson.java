import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface defines the behavior of a person making a call through a cell phone network.
 * Implementing classes must provide functionality to determine the cell tower closest to the
 * location of the person making the call.
 */

public interface IPerson {
    /**
     * This map is used internally to map different cell providers to AT&T or Verizon based on business relationships
     * These can be used to decide the premium that users will pay for celltower usage
     */
    @SuppressWarnings("serial")
    public static final Map<String, String> PROVIDERMAP =
            Collections.unmodifiableMap(new HashMap<String, String>()
            {
            {
            put("at&t", "AT&T");
            put("verizon", "Verizon");
            put("cricket", "AT&T");
            put("h20", "AT&T");
            put("tmobile", "AT&T");
            put("t-mobile", "AT&T");
            put("xfinity", "Verizon");
            
            }
            }); 
    
    
   // boolean hasCoverage(QuadTree qt);
    
   // int closestTower(QuadTree qt);
    
    
}
