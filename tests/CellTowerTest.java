import org.junit.Test;

public class CellTowerTest {

    @Test
    public void toStringTest() {
        CellTower ct = new CellTower(12, 1.006324, 45.68374, "AT&T", "123 Main St", "Nowhere","None", "NW");
        System.out.println(ct);
    }
}
