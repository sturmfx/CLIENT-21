public class Bullet
{
    boolean alive;
    double x;
    double y;
    double dx;
    double dy;

    public Bullet(double x, double y)
    {
        this.x = DATA.x;
        this.y = DATA.y;
        this.dx = x - this.x;
        this.dy = y - this.y;
        this.alive = true;
    }
}
