/**
 * This class is used to test the functionality of the CellNetwork class
 */

import org.junit.Assert;
import org.junit.Test;

public class CellNetworkTest {

    @Test
    public void testComputeNauticalMiles() {
        double lat1 = 39.38;
        double long1 = 74.45;
        double lat2 = 39.58;
        double long2 = 74.87;
        int distance = ICellNetwork.computeNauticalMiles(lat1, long1, lat2, long2);
        Assert.assertEquals(23, distance);
    }
}