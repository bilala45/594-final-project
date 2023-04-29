import org.junit.Test;

public class CellTowerTest {

    @Test
    public void toStringTest() {
        CellTower ct = new CellTower("a", 12, "123 Main St", "Nowhere","None", "NW", 1.006324, 45.68374);
        
        System.out.println(ct);
        
    }

}
