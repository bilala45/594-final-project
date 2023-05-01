import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessorTest {

    @Test
    public void test() {
        Processor p = new Processor("atlantic-test-data.csv");
        assertEquals(11, p.getTowerList().size());
        
        Processor p2 = new Processor("Northeast_dataset_2_2.csv");
        assertEquals(1385, p2.getTowerMap().size());
        assertEquals(1385, p2.getTowerList().size());
    }
}
