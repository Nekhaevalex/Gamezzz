package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Dragon implements IUnit {
    //position
    private int x;
    private int y;
    //size
    private int w = 10;
    private int h = 10;
    //speed;
    private int vx = 50;
    private int vy = 50;
    //
    private float b = 0;
    private int children = 0;
    
    public Dragon(int x, int y, int w, int h,float time) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.b = time;
    }
     
    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(Color.GREEN);
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
        
        Iterable<IUnit> unit_list = world.getUnits();
        for (IUnit i : unit_list) {
            if (i == flower) {
                if ((i.x * i.x + i.y * i.y) <= 20 * 20) {
                    world.removeUnit(i);
                    world.addUnit(dragon);
                }
            }
            if (i == wolf) {
                if ((i.x * i.x + i.y * i.y) <= 20 * 20) {
                    world.removeUnit(i);
                }
            }
        }
    }
}