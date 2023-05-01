import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProcessorTest {

    @Test
    public void test() {
        Processor p = new Processor("atlantic-test-data.csv");
        assertEquals(11, p.getTowerList().size());
        
        Processor p2 = new Processor("Northeast_dataset_2_2.csv");
        assertEquals(1385, p2.getTowerMap().size());
        assertEquals(1385, p2.getTowerList().size());
    }


    @Test
    public void testProcessor() {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        Processor processor = new Processor("northeast-data.csv");

        // Construct graph associated with CellTower objects for routing
        ICellNetwork cellNetwork = new CellNetwork(processor);

        Person person1 = new Person("John", "Verizon", 39.38, -74.45, processor.getRoot());

        // get person1's cell tower id
        Assert.assertTrue(person1.getCanConnect());
        // Assert.assertEquals(15052, person1.getPersonCellTower().getId());
        System.out.println("finished person 1");


        Person person2 = new Person("Alice", "AT&T", 40.92, -80.52, processor.getRoot());

        // get person 2's cell tower id
        Assert.assertTrue(person2.getCanConnect());
        // Assert.assertEquals(34696, person2.getPersonCellTower().getId());
        System.out.println("finished person 2");

        // initialize path object and then calculate path with it
        Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), cellNetwork);
        path.calculatePath();
        System.out.println(path);
    }
}
