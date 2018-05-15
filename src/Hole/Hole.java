package Hole;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import game.IUnit;
import game.IWorld;
import game.Rabbit;

import java.awt.*;
import java.util.ArrayList;

public class Hole extends CUSInteract implements IUnit {
    private Coordinates position;

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(new Color(59, 53, 226));
        canvas.drawRect(position.getX(), position.getY(), 20, 20);
    }

    @Override
    public void step(IWorld world, float dt) {
        ArrayList<IUnit> units = (ArrayList<IUnit>) world.getUnits();
        for (IUnit unit : units) {
            if (unit instanceof CUSInteract) {
                CUSInteract a = (CUSInteract) unit;
                if (position.equals(a.getXY())) {
                    if (unit.getClass() == Rabbit.class) {
                        if (storage.size() < 4) {
                            Rabbit toSave = new Rabbit((Rabbit) unit);
                            storage.add(new RabbitPack(toSave, world.getTime()));
                            world.removeUnit(unit);
                        }
                    }
                }
            }
        }
        for (int i = 0; i<storage.size(); i++) {
            float saveTime = storage.get(i).getDeathTime();
            if ((world.getTime()-saveTime) >= 30) {
                Rabbit toReturn = storage.get(i).returnRabbit();
                toReturn.setX(position.getX()+5);
                toReturn.setY(position.getY()+5);
                world.addUnit(toReturn);
                storage.remove(i);
            }
        }
    }

    @Override
    public Coordinates getXY() {
        return position;
    }

    @Override
    public void setXY(Coordinates position) {

    }

    private ArrayList<RabbitPack> storage;

    public Hole(int x, int y) {
        this.position = new Coordinates(x,y);
        storage = new ArrayList<>();
    }
}
