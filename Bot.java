public class Bot
{
    public static int counter = 1;
    public int visibility_counter = 0;
    public int hp;
    public String username;
    public boolean alive;
    public boolean visible;
    public double x;
    public double y;
    public double speed;
    public int direction = 5;
    public Bot(double x, double y)
    {
        this.username = "BOT " + counter;
        counter++;
        this.hp = 100;
        this.x = x;
        this.y = y;
        this.speed = 0.3;
        this.alive = true;
        this.visible = true;
        DATA.alive_bots++;
    }

}
