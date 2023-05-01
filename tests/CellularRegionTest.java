import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CellularRegionTest {
    
    ICellularRegion cr;

    @Before
    public void setUp() throws Exception {
        cr = new CellularRegion(new Coordinate(16, 16), new Coordinate(0, 0), null);
        List<CellTower> cellTowers = new ArrayList<>();
        cellTowers.add(new CellTower(1, 4.0, 0.0, "a", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(2, 4.0, 1.0, "a", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(3, 5.0, 0.0, "a", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(4, 8.0, 0.0, "a", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(5, 9.0, 0.0, "a", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(6, 10.0, 5.0, "a", "b", "c", "d", "e"));
        cr.setTowersInRegion(cellTowers);
        
    }


    @Test
    public void parentTest() {
        
        System.out.println("here");
        
        System.out.println(cr.getTowersInRegion().size());
        
        cr.subDivide();
        
        System.out.println(cr.getTowersInRegion().size());
        
        System.out.println(cr.children().size());
        
        for (ICellularRegion child : cr.children()) {
            System.out.println(child.getTowersInRegion().size());
        }
        
        
       
    }

}
