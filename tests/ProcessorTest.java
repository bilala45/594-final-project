import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessorTest {

    @Test
    public void test() {
        //fail("Not yet implemented");
        String csvFilePath = "atlantic-test-data.csv";
        String csvFilePath2 = "Northeast_dataset.csv";


        Processor p = new Processor();
        p.readCSVFile(csvFilePath);
        assertEquals(11, p.getTowerList().size());
        
        p.readCSVFile(csvFilePath2);
        assertEquals(1385, p.getTowerList().size());


    }

}
