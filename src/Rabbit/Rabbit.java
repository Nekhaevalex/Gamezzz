package Rabbit;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import game.IUnit;
import game.IWorld;

import java.awt.*;

public class Rabbit extends CUSInteract implements IUnit {
    //центр пидора
    private int x, y;
    //
    private int R = 10;
    private float t = 0;
    //speed;
    private float mod = 1;
    private float vx = 0;
    private float vy = mod;
    //
    private int children = 0;

    @Override
    public Coordinates getXY() {
        return new Coordinates(x,y);
    }

    @Override
    public void setXY(Coordinates position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Coordinates obj) {
        return getXY().equals(obj);
    }

    public Rabbit(int x, int y, float time, int childrens)
    {
        this.x = x;
        this.y = y;
        t = time;
        children = childrens;
    }
    public int getChildren()
    {
        return children;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public float getT()
    {
        return t;
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
        if(world.getTime()-t > 10000) {
            world.removeUnit(this);
        }
        if((world.getTime()-t) > 2000*children && children < 5) {
            world.addUnit(new Rabbit(x, y,world.getTime(),0));
            children++;
        }
    }

    public Rabbit(Rabbit rabbit) {
        this.x = rabbit.x;
        this.y = rabbit.y;
        this.children = rabbit.children;
        this.mod = rabbit.mod;
        this.R = rabbit.R;
        this.t = rabbit.t;
        this.vx = rabbit.vx;
        this.vy = rabbit.vy;
    }
}