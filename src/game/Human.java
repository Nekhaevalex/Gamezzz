package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Human implements IUnit {

    //position
    private int x;
    private int y;
    //size
    private int w = 10;
    private int h = 10;
    //speed;
    private int vx = 1;
    private int vy = 1;
    //
    private float b = 0;
    private int children = 0;

    public Human(int x, int y, int w, int h,float time) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.b = time;
    }

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(Color.red);
        canvas.draw(new Rectangle(x, y, w, h));
    }

    @Override
    public void step(IWorld world, float dt) {
        int ww = world.getWidth();
        int wh = world.getHeight();
        if(this.x >= ww && vx > 0) {
            vx *= -1;
        }
        if(this.y >= wh && vy > 0) {
            vy *= -1;
        }
        if(this.x <= 0 && vx < 0) {
            vx *= -1;
        }
        if(this.y <= 0 && vy < 0) {
            vy *= -1;
        }
        this.x += vx*dt;
        this.y += vy*dt;

        if(world.getTime()-b > 500) {
            world.removeUnit(this);
        }
        if((world.getTime()-b) > 200 && children < 1) {
            world.addUnit(new Human(10*children, 10*children, this.w, this.h,world.getTime()));
            children++;
        }
    }

}
