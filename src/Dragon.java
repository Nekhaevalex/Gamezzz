package Dragon;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import HungryFlower.HungryFlower;
import game.IUnit;
import game.IWorld;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Dragon extends CUSInteract implements IUnit {
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
        Iterable<IUnit> unit_list = world.getUnits();
        int x_min = 0, y_min = 0; //расстояние до ближайшего цветка
        double distance = 100000;
        IUnit nearest_flower;
        for (IUnit i : unit_list) {
            if (i instanceof CUSInteract) {
                CUSInteract a = (CUSInteract) i;
                if (i.getClass() == HungryFlower.class) {
                    if (distance > sqrt((((CUSInteract) i).getXY().getX() - x) * (((CUSInteract) i).getXY().getX() - x) +
                            (((CUSInteract) i).getXY().getY() - y) * (((CUSInteract) i).getXY().getY() - y))) {
                        x_min = ((CUSInteract) i).getXY().getX();
                        y_min = ((CUSInteract) i).getXY().getY();
                        distance = sqrt((x_min - x) * (x_min - x) + (y_min - y)* (y_min - y));
                    }
                }
            }
        }
        //Дракон делает шаг
        if (abs(x_min - x) < vx) {
            this.x = x_min;
        } else {
            if (x_min > x) {
                this.x += vx * dt;
            } else {
                this.x -= vx * dt;
            }
        }
        if (abs(y_min - y) < vy) {
            this.y = y_min;
        } else {
            if (y_min > y) {
                this.y += vy * dt;
            } else {
                this.y -= vy * dt;
            }
        }
        
        for (IUnit i : unit_list) {
            if (i instanceof  CUSInteract) {
                CUSInteract a = (CUSInteract) i;
                if (i.getClass() == HungryFlower.class) {
                    if ((a.getXY().getX() * a.getXY().getX() + a.getXY().getY() * a.getXY().getY()) <= 20 * 20) {
                        world.removeUnit(i);
                        world.addUnit(new Dragon(x+20, y+20, w, h, (int)world.getTime()));
                    }
                }
//                if (i == wolf) {
//                    if ((i.x * i.x + i.y * i.y) <= 20 * 20) {
//                        world.removeUnit(i);
//                    }
//                }
            }
        }
    }

    @Override
    public Coordinates getXY() {
        return new Coordinates(x,y);
    }

    @Override
    public void setXY(Coordinates position) {
        this.x = position.getX();
        this.y = position.getY();
    }
}
