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
    private int vx = 3;
    private int vy = 3;
    //
    private float b = 0;
    private int children = 0;
    //hunt
    private IUnit nearest_flower;
    private double distance;

    public Dragon(int x, int y, int w, int h,float time) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.b = time;
        this.distance = 100000;
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
        for (IUnit i : unit_list) {
            if (i instanceof CUSInteract) {
                CUSInteract a = (CUSInteract) i;
                if (distance == 100000) {
                    if (i.getClass() == HungryFlower.class) {
                        if (distance > sqrt((a.getXY().getX() - x) * (a.getXY().getX() - x) +
                                (a.getXY().getY() - y) * (a.getXY().getY() - y))) {
                            x_min = a.getXY().getX();
                            y_min = a.getXY().getY();
                            distance = sqrt((x_min - x) * (x_min - x) + (y_min - y)* (y_min - y));
                            nearest_flower = i;
                        }
                    }
                } else {
                    x_min = ((CUSInteract) nearest_flower).getXY().getX();
                    y_min = ((CUSInteract) nearest_flower).getXY().getY();
                    distance = sqrt((x_min - x) * (x_min - x) + (y_min - y)* (y_min - y));
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
//        for (IUnit i : unit_list) {
//            if (i instanceof  CUSInteract) {
//                CUSInteract a = (CUSInteract) i;
                if (distance <= 40) {
                    world.removeUnit(nearest_flower);
                    distance = 100000;
                    world.addUnit(new Dragon(x+20, y+20, w, h, (int)world.getTime()));
                }
//                if (i == wolf) {
//                    if ((i.x * i.x + i.y * i.y) <= 20 * 20) {
//                        world.removeUnit(i);
//                    }
//                }
//            }
//        }
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
