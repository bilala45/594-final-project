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
        Processor processor = new Processor("atlantic-test-data.csv");
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
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456, null);
        assertEquals("AT&T", p.getCarrier());
    }

}
