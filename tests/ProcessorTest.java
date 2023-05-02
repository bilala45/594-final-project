import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProcessorTest {

    private Processor processorAtlantic;
    private Processor processorNortheast;
    private ICellNetwork cellNetworkAtlantic;
    private ICellNetwork cellNetworkNortheast;

    @Before
    public void setUp() {
        // Create processor object
        // On initialization, the processor object
        // - reads a CSV file
        // - constructs the quad tree object corresponding to the region
        processorAtlantic = new Processor("atlantic-test-data.csv");
        processorNortheast = new Processor("northeast-data.csv");

        // Construct graph associated with CellTower objects for routing
        cellNetworkAtlantic = new CellNetwork(processorAtlantic);
        cellNetworkNortheast = new CellNetwork(processorNortheast);
    }

    @Test
    public void testProcessorTowerListSize() {
        assertEquals(11, processorAtlantic.getTowerList().size());
        assertEquals(11, processorAtlantic.getTowerMap().size());

        assertEquals(1385, processorNortheast.getTowerMap().size());
        assertEquals(1385, processorNortheast.getTowerList().size());
    }


    @Test
    public void testProcessorOutputNYandPhilly() {
        Person person1 = new Person("John", "Verizon", 40.71, -74.01, processorNortheast.getRoot());
        Assert.assertTrue(person1.getCanConnect());
        Assert.assertEquals(314, person1.getPersonCellTower().getId());

        Person person2 = new Person("Alice", "AT&T", 39.95, -75.17, processorNortheast.getRoot());
        Assert.assertTrue(person2.getCanConnect());
        Assert.assertEquals(912, person2.getPersonCellTower().getId());

        // initialize path object and then calculate path with it
        Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), cellNetworkNortheast);
        List<CellTower> towerList = path.calculatePath();
        Assert.assertEquals(13, towerList.size());
        System.out.println(path);
    }


    @Test
    public void testProcessorOutputTopLeftAndBotRight() {
        Person person1 = new Person("John", "Verizon", 49, -84, processorNortheast.getRoot());
        Assert.assertTrue(person1.getCanConnect());
        Assert.assertEquals(865, person1.getPersonCellTower().getId());

        Person person2 = new Person("Alice", "AT&T", 35, -70, processorNortheast.getRoot());
        Assert.assertTrue(person2.getCanConnect());
        Assert.assertEquals(451, person2.getPersonCellTower().getId());

        // initialize path object and then calculate path with it
        Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), cellNetworkNortheast);
        List<CellTower> towerList = path.calculatePath();
        Assert.assertEquals(74, towerList.size());
        System.out.println(path);
    }


    @Test
    public void testProcessorOutputBotLeftAndTopRight() {
        Person person1 = new Person("John", "Verizon", 35, -84, processorNortheast.getRoot());
        Assert.assertTrue(person1.getCanConnect());
        Assert.assertEquals(972, person1.getPersonCellTower().getId());

        Person person2 = new Person("Alice", "AT&T", 49, -70, processorNortheast.getRoot());
        Assert.assertTrue(person2.getCanConnect());
        Assert.assertEquals(160, person2.getPersonCellTower().getId());

        // initialize path object and then calculate path with it
        Path path = new Path(person1.getPersonCellTower().getId(), person2.getPersonCellTower().getId(), cellNetworkNortheast);
        List<CellTower> towerList = path.calculatePath();
        Assert.assertEquals(83, towerList.size());
        System.out.println(path);
    }
}
