import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CellularRegionTest {
    
    ICellularRegion cr;

    @Before
    public void setUp() throws Exception {
        cr = new CellularRegion(new Coordinate(50, -85) , new Coordinate(34, -69), null);
        List<CellTower> cellTowers = new ArrayList<>();
        //Name, id etc. of towers do not matter.
        cellTowers.add(new CellTower(1, 41.34977778, -72.97266667, "verizon", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(2, 41.57036111, -73.01788889, "verizon", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(3, 41.45066667, -73.516, "verizon", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(4, 41.522, -73.22075, "verizon", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(5, 41.03394444, -73.63083333, "verizon", "b", "c", "d", "e"));
        cellTowers.add(new CellTower(6, 41.27911111, -73.18525, "verizon", "b", "c", "d", "e"));
        cr.setTowersInRegion(cellTowers);
    }


    @Test
    public void parentTest() {
        assertFalse(cr.isLeaf());
        assertNull(cr.getParent());
        cr.subDivide();
       
    }
    
    @Test
    public void childTowersExists () {
        cr.subDivide();
        assertEquals(cr.getTowersInRegion().size(), 6);
 
    }
    
    
    @Test
    public void childTowersSize () {
        cr.subDivide();
        assertEquals(cr.children().size(), 1);
 
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void BottomRightTest() {
        
        assertEquals(cr.botRight.getLatitude(), 34, 0);
        assertEquals(cr.botRight.getLongitude(), -69, 0);
 
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void TopLeftTest() {
        assertEquals(cr.topLeft.getLatitude(), 50, 0.01);
        assertEquals(cr.topLeft.getLongitude(), -85, 0.01);
    }
    
    @Test
    public void TestChildren() {
        assertNull(cr.getTopLeftSq());
        assertNull(cr.getTopRightSq());
        assertNull(cr.getBotRightSq());
        assertNull(cr.getBotLeftSq());
    }


    @Test
    public void TestSiblings() {
        cr.subDivide();
        assertNotNull(cr.getTopLeftSq());
        assertNotNull(cr.getTopRightSq());
        assertNotNull(cr.getBotRightSq());
        assertNotNull(cr.getBotLeftSq());
    }
}
