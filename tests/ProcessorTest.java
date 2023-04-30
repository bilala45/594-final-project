import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessorTest {

    @Test
    public void test() {
        //fail("Not yet implemented");
        String csvFilePath = "atlantic-test-data.csv";
        String csvFilePath2 = "Northeast_dataset_2_2.csv";


        Processor p = new Processor();
        p.readCSVFile(csvFilePath);
        assertEquals(11, p.getTowerList().size());
        
        Processor p2 = new Processor();       
        p2.readCSVFile(csvFilePath2);
        assertEquals(1385, p2.getTowerMap().size());
        assertEquals(1385, p2.getTowerList().size());
        //System.out.println(p2.getTowerList());




    }

}
