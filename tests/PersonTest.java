import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

    @Test
    public void ctorBasicTest() {
        ICellularRegion i = new CellularRegion();
        Person p = new Person("john","AT&T", 1.0006, 5.456, i);
        
        assertEquals("AT&T", p.getCarrier());
        assertEquals(1.000, p.getLocation().getLatitude(), 0.001);
        assertEquals(5.456, p.getLocation().getLongitude(), 0.001);
        assertEquals("john", p.getName());
        
    }
    
    @Test
  public void ctorBasicCarrier() {
      ICellularRegion i = new CellularRegion();
      Person p = new Person("Jane","AT&T", 1.0006, 5.456, i);
      assertEquals("AT&T", p.getCarrier());
     
  }
    
    
    @Test
    //This test case fails - why? Why do the others pass but this one fails?
    //Look into this test case for clues to nullPointer error
    public void ctorConvertCarrier() {
        ICellularRegion i = new CellularRegion();
        Person p = new Person("Jane","t-mobile", 1.0006, 5.456, i);
        assertEquals("AT&T", p.getCarrier());
       
    }

}
