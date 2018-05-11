package game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Rabbit implements IUnit {
    //центр пидора
    private int x, y;
    //
    private int R = 10;
    private float t = 0;
    //speed;
    private float mod = 1;
    private float vx =0;
    private float vy = mod;
    //
    private int chaild = 0;

    public Rabbit(int x, int y, float time)
    {
        t = time;
    }
    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(Color.GRAY);
        canvas.drawOval(x,y,10,10);

    }

    @Override
    public void step(IWorld world, float dt) {
        if(((int)(world.getTime()-t))%(int)(100*Math.random()+1) == 0) {

            vx = (-mod) + (float) ( 2 * mod * Math.random());
            //double doubl =mod*mod - vx * vx;
            if((int)(1*Math.random()) ==0)
                vy = (float) Math.sqrt(mod*mod - vx * vx);
            else
                vy = -(float) Math.sqrt(mod*mod - vx * vx);
        }
        int ww = world.getWidth();
        int wh = world.getHeight();
        if(this.x>= ww && vx > 0) {
            vx *= -1;
        }
        if(this.y >= wh && vy > 0) {
            vy *= -1;
        }
        if(this.x  <= 0 && vx < 0) {
            vx *= -1;
        }
        if(this.y  <= 0 && vy < 0) {
            vy *= -1;
        }
        this.x += vx*dt;
        this.y += vy*dt;
    }
}
