package HungryFlower;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import game.IUnit;
import game.IWorld;
import game.Rabbit;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class HungryFlower extends CUSInteract implements IUnit {
    private int x;
    private int y;

    private float birthtime;

    private boolean virgin = true;

    @Override
    public void draw(Graphics2D canvas) {
        canvas.drawOval(0+x, 0+y, 30, 30);
        canvas.setColor(new Color(0xFF4033));
        canvas.fillOval(0+x,0+y, 30, 30);
    }

    private void pregnant(IWorld world) {
        if (world.getTime()-birthtime > 1000) {
            if (virgin == true) {
                world.addUnit(new HungryFlower(world.getTime()));
                virgin = false;
            }
        }
    }

    private ArrayList<Rabbit> getRabbitList(IWorld world) {
        ArrayList<IUnit> list = (ArrayList<IUnit>) world.getUnits();
        ArrayList<Rabbit> rabbits = new ArrayList<>();
        for (int i = 0; i<list.size(); i++) {
            IUnit testing = list.get(i);
            if (testing.getClass() == Rabbit.class) {
                rabbits.add((Rabbit) list.get(i));
            }
        }
        return rabbits;
    }

    private double length (int xk, int yk, int xc, int yc) {
        return  Math.sqrt(Math.pow(xk-xc, 2)+Math.pow(yk-yc,2));
    }

    private ArrayList<Rabbit> nearbyRabbits(ArrayList<Rabbit> rabbits) {
        ArrayList<Rabbit> toBeDestroyed = new ArrayList<>();
        for (int i = 0; i<rabbits.size(); i++) {
            int xc = x+15;
            int yc = y+15;
            int xk = rabbits.get(i).getX();
            int yk = rabbits.get(i).getY();
            int length = (int) Math.round(length(xk, yk, xc, yc));
            if (length < 75) {
                toBeDestroyed.add(rabbits.get(i));
            }
        }
        return toBeDestroyed;
    }

    private void deleteRabbit(ArrayList<Rabbit> rabbits, IWorld world) {
        for (int i = 1; i<rabbits.size(); i++) {
            world.removeUnit(rabbits.get(i));
        }
    }

    @Override
    public void step(IWorld world, float dt) {
        pregnant(world);
        ArrayList<Rabbit> rabbits = getRabbitList(world);
        rabbits = nearbyRabbits(rabbits);
        deleteRabbit(rabbits, world);
    }

    @Override
    public Coordinates getXY() {
        return new Coordinates(x,y);
    }

    @Override
    public void setXY(Coordinates position) {

    }

    public HungryFlower(float birthtime) {
        Random random = new Random();
        this.x = random.nextInt(500);
        this.y = random.nextInt(500);
        this.birthtime = birthtime;
    }
}
