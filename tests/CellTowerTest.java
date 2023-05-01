import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CellTowerTest {

    @Test
    public void ctorTest() {
        CellTower ct = new CellTower(12, 1.006324, 45.68374, "AT&T", "123 Main St", "Nowhere","None", "NW");
        
        assertEquals(12, ct.getId());
        assertEquals(1.006, ct.getLat(), 0.001);
        assertEquals(45.683, ct.getLong(), 0.001);
        assertEquals("NW", ct.getState());
        assertEquals("123 Main St", ct.getAddress());
        assertEquals("Nowhere", ct.getCity());
        assertEquals("None", ct.getCounty());
        assertEquals("AT&T", ct.getLicense());
        
    }
}
