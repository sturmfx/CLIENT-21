
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends JFrame implements MouseListener
{
    public int fire_wait = DATA.rate_of_fire;
    public long spawn_counter = 1000;
    public boolean go = true;
    public Random random;
    public long counter = 0;
    Timer timer;
    boolean isW = false;
    boolean isA = false;
    boolean isS = false;
    boolean isD = false;

    public Client()
    {
        random = new Random();
        initUI();
        initDrawTimer();
        DATA.start = System.currentTimeMillis();
    }

    private void initUI()
    {
        JPanel canvas = new Canvas();
        canvas.setDoubleBuffered(true);
        add(canvas);
        setSize(DATA.width, DATA.height + 100);
        setResizable(false);
        setTitle("GAME");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        canvas.addMouseListener(this);
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyChar() == 'w') isW = true;
                if(e.getKeyChar() == 'a') isA = true;
                if(e.getKeyChar() == 's') isS = true;
                if(e.getKeyChar() == 'd') isD = true;
                try
                {
                    updateDirection();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyChar() == 'w') isW = false;
                if(e.getKeyChar() == 'a') isA = false;
                if(e.getKeyChar() == 's') isS = false;
                if(e.getKeyChar() == 'd') isD = false;
                try
                {
                    updateDirection();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void initDrawTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {

            @Override
            public void run()
            {
                if(go)
                {
                    clear();
                    spawn();
                    updateBotDirection();
                    update();
                    update_bullets();
                    update_visibility();
                    counter++;
                    spawn_counter--;
                    if (counter % 10 == 0)
                    {
                        Client.super.setTitle("BOTS KILLED: " + DATA.kills);
                        repaint();
                    }
                }
            }
        }, 0, 1);
    }

    private void updateDirection() throws IOException
    {
        int direction = 5;

        if(isW && isA && !isS && !isD) direction = 1;
        if(isW && !isA && !isS && !isD) direction = 2;
        if(isW && !isA && !isS && isD) direction = 3;
        if(!isW && isA && !isS && !isD) direction = 4;
        if(!isW && !isA && !isS && !isD) direction = 5;
        if(!isW && !isA && !isS && isD) direction = 6;
        if(!isW && isA && isS && !isD) direction = 7;
        if(!isW && !isA && isS && !isD) direction = 8;
        if(!isW && !isA && isS && isD) direction = 9;

        DATA.player_direction = direction;

    }

    private void updateBotDirection()
    {

        for(Bot b : DATA.bots)
        {
            if(random.nextInt(1000000) < 500)
            {
                b.direction = random.nextInt(9) + 1;
            }
        }
    }

    private void update()
    {
        for(Bot b : DATA.bots)
        {
            if(b.alive)
            {
                switch (b.direction)
                {
                    case 1:
                        if (b.x - DATA.delta * b.speed >= 0 && !check(b.x - DATA.delta * b.speed, b.y))
                            b.x -= DATA.delta * b.speed;
                        if (b.y - DATA.delta * b.speed >= 0 && !check(b.x, b.y - DATA.delta * b.speed))
                            b.y -= DATA.delta * b.speed;
                        break;
                    case 2:
                        if (b.y - DATA.delta * b.speed >= 0 && !check(b.x, b.y - DATA.delta * b.speed))
                            b.y -= DATA.delta * b.speed;
                        break;
                    case 3:
                        if (b.x + DATA.delta * b.speed <= DATA.width && !check(b.x + DATA.delta * b.speed, b.y))
                            b.x += DATA.delta * b.speed;
                        if (b.y - DATA.delta * b.speed >= 0 && !check(b.x, b.y - DATA.delta * b.speed))
                            b.y -= DATA.delta * b.speed;
                        break;
                    case 4:
                        if (b.x - DATA.delta * b.speed >= 0 && !check(b.x - DATA.delta * b.speed, b.y))
                            b.x -= DATA.delta * b.speed;
                        break;
                    case 5:
                        break;
                    case 6:
                        if (b.x + DATA.delta * b.speed <= DATA.width && !check(b.x + DATA.delta * b.speed, b.y))
                            b.x += DATA.delta * b.speed;
                        break;
                    case 7:
                        if (b.x - DATA.delta * b.speed >= 0 && !check(b.x - DATA.delta * b.speed, b.y))
                            b.x -= DATA.delta * b.speed;
                        if (b.y + DATA.delta * b.speed <= DATA.height && !check(b.x, b.y + DATA.delta * b.speed))
                            b.y += DATA.delta * b.speed;
                        break;
                    case 8:
                        if (b.y + DATA.delta * b.speed <= DATA.height && !check(b.x, b.y + DATA.delta * b.speed))
                            b.y += DATA.delta * b.speed;
                        break;
                    case 9:
                        if (b.x + DATA.delta * b.speed <= DATA.width && !check(b.x + DATA.delta * b.speed, b.y))
                            b.x += DATA.delta * b.speed;
                        if (b.y + DATA.delta * b.speed <= DATA.height && !check(b.x, b.y + DATA.delta * b.speed))
                            b.y += DATA.delta * b.speed;
                        break;
                }

                if(Math.sqrt((b.x - DATA.x) * (b.x - DATA.x) + (b.y - DATA.y) *(b.y - DATA.y)) < DATA.bot_radius + DATA.player_radius)
                {
                    b.alive = false;
                    DATA.alive_bots--;
                    DATA.kills++;
                    if(DATA.kills == DATA.kill_limit)
                    {
                        go = false;
                        DATA.stop = System.currentTimeMillis();
                        repaint();
                    }
                }
            }


        }

        switch (DATA.player_direction)
        {
            case 1:
                if (DATA.x - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x - DATA.delta * DATA.player_speed, DATA.y)) DATA.x -= DATA.delta * DATA.player_speed;
                if (DATA.y - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x, DATA.y - DATA.delta * DATA.player_speed)) DATA.y -= DATA.delta * DATA.player_speed;
                break;
            case 2:
                if (DATA.y - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x, DATA.y - DATA.delta * DATA.player_speed)) DATA.y -= DATA.delta * DATA.player_speed;
                break;
            case 3:
                if (DATA.x + DATA.delta * DATA.player_speed <= DATA.width && !check(DATA.x + DATA.delta * DATA.player_speed, DATA.y)) DATA.x += DATA.delta * DATA.player_speed;
                if (DATA.y - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x, DATA.y - DATA.delta * DATA.player_speed)) DATA.y -= DATA.delta * DATA.player_speed;
                break;
            case 4:
                if (DATA.x - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x - DATA.delta * DATA.player_speed, DATA.y)) DATA.x -= DATA.delta * DATA.player_speed;
                break;
            case 5:
                break;
            case 6:
                if (DATA.x + DATA.delta * DATA.player_speed <= DATA.width && !check(DATA.x + DATA.delta * DATA.player_speed, DATA.y)) DATA.x += DATA.delta * DATA.player_speed;
                break;
            case 7:
                if (DATA.x - DATA.delta * DATA.player_speed >= 0 && !check(DATA.x - DATA.delta * DATA.player_speed, DATA.y)) DATA.x -= DATA.delta * DATA.player_speed;
                if (DATA.y + DATA.delta * DATA.player_speed <= DATA.height && !check(DATA.x, DATA.y + DATA.delta * DATA.player_speed)) DATA.y += DATA.delta * DATA.player_speed;
                break;
            case 8:
                if (DATA.y + DATA.delta * DATA.player_speed <= DATA.height && !check(DATA.x, DATA.y + DATA.delta * DATA.player_speed)) DATA.y += DATA.delta * DATA.player_speed;
                break;
            case 9:
                if (DATA.x + DATA.delta * DATA.player_speed <= DATA.width && !check(DATA.x + DATA.delta * DATA.player_speed, DATA.y)) DATA.x += DATA.delta * DATA.player_speed;
                if (DATA.y + DATA.delta * DATA.player_speed <= DATA.height && !check(DATA.x, DATA.y + DATA.delta * DATA.player_speed)) DATA.y += DATA.delta * DATA.player_speed;
                break;
        }
    }

    private void update_visibility()
    {
        for(Bot b : DATA.bots)
        {
            boolean result = false;

            for(Triangle t : DATA.objects)
            {
                if(t.intercects(new Point((int) b.x, (int) b.y), new Point((int) DATA.x, (int) DATA.y)))
                {
                    result = true;
                }
            }

            for(Rectangle r : DATA.rectangles)
            {
                if(r.intercects(new Point((int) b.x, (int) b.y), new Point((int) DATA.x, (int) DATA.y)))
                {
                    result = true;
                }
            }

            if(result)
            {
                if(b.visibility_counter == 0)
                {
                    b.visible = false;
                }
                else
                {
                    b.visibility_counter--;
                }
            }
            else
            {
                b.visibility_counter = DATA.visibility_counter;
                b.visible = true;
            }
        }
    }

    private void update_bullets()
    {
        for(Bullet b : DATA.bullets)
        {
            if(b.alive)
            {
                double tx = b.x + b.dx;
                double ty = b.y + b.dy;

                if(check(tx, ty) && tx > 0 && tx < DATA.width && ty > 0 && ty < DATA.height)
                {
                    b.alive = false;
                }
                else
                {
                    b.x = tx;
                    b.y = ty;
                    for(Bot t : DATA.bots)
                    {
                        if(Math.sqrt((b.x - t.x) * (b.x - t.x) + (b.y - t.y) * (b.y - t.y)) < DATA.bot_radius + DATA.bullet_radius)
                        {
                            b.alive = false;
                            t.hp = t.hp - DATA.bullet_damage;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void clear()
    {
        for(int i = 0; i < DATA.bots.size() - 1; i++)
        {
            if(!DATA.bots.get(i).alive)
            {
                DATA.bots.remove(i);
            }
        }

        for(int i = 0; i < DATA.bullets.size() - 1; i++)
        {
            if(!DATA.bullets.get(i).alive)
            {
                DATA.bullets.remove(i);
            }
        }
    }

    private static boolean check(double x, double y)
    {
        for(Triangle b : DATA.objects)
        {
            if(b.contains(x, y))
            {
                return true;
            }
        }

        for(Rectangle r : DATA.rectangles)
        {
            if(r.contains(x, y))
            {
                return true;
            }
        }

        return false;
    }

    private void spawn()
    {

        if(spawn_counter < 0 && DATA.alive_bots < DATA.max_bots)
        {
            add_bots(1);
            spawn_counter = DATA.min_spawn_wait + random.nextInt(DATA.max_spawn_wait - DATA.min_spawn_wait);
        }
    }

    public static void add_bots(int n)
    {
        Random random = new Random();

        for(int i = 0; i < n; i++)
        {
            boolean temp = true;
            int counter = 1000;

            while(temp)
            {
                double x = random.nextDouble() * DATA.width;
                double y = random.nextDouble() * DATA.height;
                if(Math.sqrt((x - DATA.x) *(x - DATA.x) + (y - DATA.y) * (y - DATA.y)) > DATA.spawn_distance_to_player && !check(x, y))
                {
                    DATA.bots.add(new Bot(x, y));
                    temp = false;
                }

                if(counter < 0)
                {
                    temp = false;
                }

                counter--;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        //System.out.println("BOT ADDED!");
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    public static void main(String[] args)
    {

        EventQueue.invokeLater(() ->
        {
            //DATA.objects.add(new Triangle(0, 0, 200, 0, 200, 200));
            DATA.rectangles.add(new Rectangle(0, 0, DATA.width, 0, DATA.width, 10, 0, 10));
            DATA.rectangles.add(new Rectangle(0, DATA.height - 10, DATA.width, DATA.height - 10, DATA.width, DATA.height, 0, DATA.height));
            DATA.rectangles.add(new Rectangle(0, 0, 10, 0, 10, DATA.height, 0, DATA.height));
            DATA.rectangles.add(new Rectangle(DATA.width - 10, 0, DATA.width, 0, DATA.width, DATA.height, DATA.width - 10, DATA.height));

            add_bots(1);
            Client client = new Client();
            client.setVisible(true);
        });
    }


}
