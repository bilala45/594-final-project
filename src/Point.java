
// -------------------------------------------------------------------------
/**
 *  A 2D Points for lat and long
 */
public class Point {
    private double x;
    private double y;
    // ----------------------------------------------------------
    /**
     * Create a new Point object.
     * @param x
     * @param y
     */
    Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
    // ----------------------------------------------------------
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }
    // ----------------------------------------------------------
    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }
    // ----------------------------------------------------------
    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    // ----------------------------------------------------------
    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return "x: " + this.x + ", y: " + this.y;
    }
    
    @Override
    public boolean equals(Object o) {
        
        Point other = (Point) o;
        
        if (x == other.getX()) {
            if (y == other.getY()) {
                return true;
            }
        }
        
        
        return false;
        
    }

}
