import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

    @Test
    public void ctorBasicTest() {
        Person p = new Person("john","AT&T", 1.0006, 5.456);
        
        assertEquals("AT&T", p.getCarrier());
        assertEquals(1.000, p.getLat(), 0.001);
        assertEquals(5.456, p.getLong(), 0.001);
        assertEquals("john", p.getName());
        
    }
    
    @Test
    public void ctorConvertCarrier() {
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456);
        
        assertEquals("AT&T", p.getCarrier());
       
    }

}
