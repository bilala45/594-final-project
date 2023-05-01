import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private Processor processor;

    @Before
    public void setUp() {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        processor = new Processor("atlantic-test-data.csv");
    }

    @Test
    public void constructorTest() {
        Person p = new Person("john","AT&T", 1.0006, 5.456, processor.getRoot());
        
        assertEquals("AT&T", p.getCarrier());
        assertEquals(1.000, p.getLocation().getLatitude(), 0.001);
        assertEquals(5.456, p.getLocation().getLongitude(), 0.001);
        assertEquals("john", p.getName());
    }
    
    @Test
    public void constructorConvertCarrier() {
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456, processor.getRoot());
        assertEquals("AT&T", p.getCarrier());
    }
    
    @Test
    public void setCarrierTest() {
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456, processor.getRoot());
        p.setCarrier("sprint");
        assertEquals("Verizon", p.getCarrier());
    }
    
    @Test
    public void invalidLocationFarSouthTest() {
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456, processor.getRoot());
        assertFalse(p.getCanConnect());
    }
    
    @Test
    public void invalidLocationFarNorthTest() {
        Person p = new Person("Jane","t-mobile", 100, 100, processor.getRoot());
        assertFalse(p.getCanConnect());
    }
    
    @Test
    public void getLocationTest() {
        Person p = new Person("Jane","t-mobile", 37.01, -78.001, processor.getRoot());
        assertEquals(37.010, p.getLocation().getLatitude(), 0.001);
        assertEquals(-78.001, p.getLocation().getLongitude(), 0.001);
        
    }
    
    @Test
    public void getNearestCellTowerTest() {
        //create a person and find the nearest cell tower. Check if the id matches
        Person p = new Person("Jane","t-mobile", 39.41, -74.80, processor.getRoot());
        CellTower ct = p.findNearestCellTower(processor.getRoot());
        assertEquals(34708, ct.getId());
        assertEquals(34708, p.getPersonCellTower().getId());
        
    }
    
    
    

}
