
public class Rectangle
{
    public final double x1, y1, x2, y2, x3, y3, x4, y4;
    Triangle t1;
    Triangle t2;
    int[] xs;
    int[] ys;
    public Rectangle(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4)
    {
        t1 = new Triangle(x1, y1, x2, y2, x3, y3);
        t2 = new Triangle(x3, y3, x4, y4, x1, y1);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        xs = new int[] {(int) x1, (int) x2, (int) x3, (int) x4};
        ys = new int[] {(int) y1, (int) y2, (int) y3, (int) y4};
    }

    boolean contains(double x, double y)
    {
        if(t1.contains(x, y) || t2.contains(x, y))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean intercects(Point p1, Point q1)
    {
        boolean result = false;
        if(Geometry.doIntersect(p1, q1, new Point((int) x1, (int) y1), new Point((int) x2, (int) y2))) result = true;
        if(Geometry.doIntersect(p1, q1, new Point((int) x2, (int) y2), new Point((int) x3, (int) y3))) result = true;
        if(Geometry.doIntersect(p1, q1, new Point((int) x3, (int) y3), new Point((int) x4, (int) y4))) result = true;
        if(Geometry.doIntersect(p1, q1, new Point((int) x4, (int) y4), new Point((int) x1, (int) y1))) result = true;
        return result;
    }

}
