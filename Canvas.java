
import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel
{
    private void doDraw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        for(Triangle b : DATA.objects)
        {
            g2d.setColor(DATA.object_lines);
            g2d.fillPolygon(b.xs, b.ys, 3);
        }

        for(Rectangle r : DATA.rectangles)
        {
            g2d.setColor(DATA.object_lines);
            g2d.fillPolygon(r.xs, r.ys, 4);
        }

        for(Bot b : DATA.bots)
        {
            if(b.alive && b.visible)
            {
                g2d.setColor(DATA.bot_color);
                g2d.fillOval((int) b.x - DATA.bot_radius, (int) b.y - DATA.bot_radius, 2 * DATA.bot_radius, 2 * DATA.bot_radius);

                g2d.setColor(Color.RED);
                g2d.fillRect((int) ((int) b.x - 2.5 * DATA.bot_radius), (int) ((int) b.y - 1.5 * DATA.bot_radius), 100, 5);
                g2d.setColor(Color.GREEN);
                g2d.fillRect((int) ((int) b.x - 2.5 * DATA.bot_radius), (int) ((int) b.y - 1.5 * DATA.bot_radius), b.hp, 5);
                g2d.setColor(DATA.bot_color);
                g2d.drawString(b.username, (int) ((int) b.x - 2.5 * DATA.bot_radius), (int) ((int) b.y - 2 * DATA.bot_radius));
            }
        }

        for(Bullet b : DATA.bullets)
        {
            if(b.alive)
            {
                g2d.setColor(DATA.bullet_color);
                g2d.fillOval((int) b.x - DATA.bullet_radius, (int) b.y - DATA.bullet_radius, 2 * DATA.bullet_radius, 2 * DATA.bullet_radius);
            }
        }

        g2d.setColor(DATA.player_color);
        g2d.fillOval((int) DATA.x - DATA.player_radius, (int) DATA.y - DATA.player_radius, 2 * DATA.player_radius, 2 * DATA.player_radius);

        if(DATA.kills == DATA.kill_limit)
        {
            g2d.setColor(Color.MAGENTA);
            g2d.drawString("PLAYER KILLED " + DATA.kills + " BOTS IN " + ((DATA.stop - DATA.start) / 1000) + " SECONDS!", DATA.width/2, DATA.height/2);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDraw(g);
    }
}
