
public class Triangle
{
    public final double x1, y1, x2, y2, x3, y3;
    private final double y23, x32, y31, x13;
    private final double det, minD, maxD;
    int[] xs;
    int[] ys;
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        y23 = y2 - y3;
        x32 = x3 - x2;
        y31 = y3 - y1;
        x13 = x1 - x3;
        det = y23 * x13 - x32 * y31;
        minD = Math.min(det, 0);
        maxD = Math.max(det, 0);
        xs = new int[] {(int) x1, (int) x2, (int) x3};
        ys = new int[] {(int) y1, (int) y2, (int) y3};
    }

    boolean contains(double x, double y)
    {
        double dx = x - x3;
        double dy = y - y3;
        double a = y23 * dx + x32 * dy;
        if (a < minD || a > maxD)
            return false;
        double b = y31 * dx + x13 * dy;
        if (b < minD || b > maxD)
            return false;
        double c = det - a - b;
        if (c < minD || c > maxD)
            return false;
        return true;
    }

    boolean intercects(Point p1, Point q1)
    {
        boolean result = false;
        if(Geometry.doIntersect(p1, q1, new Point((int) x1, (int) y1), new Point((int) x2, (int) y2))) result = true;
        if(Geometry.doIntersect(p1, q1, new Point((int) x2, (int) y2), new Point((int) x3, (int) y3))) result = true;
        if(Geometry.doIntersect(p1, q1, new Point((int) x3, (int) y3), new Point((int) x1, (int) y1))) result = true;
        return result;
    }
}
