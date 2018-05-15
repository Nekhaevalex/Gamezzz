package Caravan;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import game.IUnit;
import game.IWorld;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//TODO: Караван, – время жизни: infinity, – повозка: желтая, 10x20px, – берет и увозит с собой, сбрасывает в случайном месте,– скорость 20

enum action {
    idle,
    targetUnit,
    targetPoint,
    pickUnit,
    dropUnit
}

public class Caravan extends CUSInteract implements IUnit {

    //Position:
    private Coordinates position;
    //Speed:
    private int vx;
    private int vy;

    private action currentAction = action.idle;
    private boolean actionStatus = false;

    private ArrayList<CUSInteract> inventory;

    private IUnit targetUnit;
    private Coordinates targetPoint;

    @Override
    public void draw(Graphics2D canvas) {
        int x = position.getX();
        int y = position.getY();
        canvas.setColor(new Color(255, 195,0));
        canvas.drawRect(x-10, y-5, 20,6);
        canvas.fillRect(x-10, y-5, 20,6);
        canvas.setColor(new Color(0,0,0));
        canvas.drawOval(x-10,y+1,4,4);
        canvas.drawOval(x+5,y+1,4,4);
        canvas.fillOval(x-10,y+1,4,4);
        canvas.fillOval(x+5,y+1,4,4);
    }

    private boolean hasUnits() {
        return inventory.isEmpty();
    }

    private Coordinates selectPoint(int ww, int wh) {
        Random coordinatesSelector = new Random();
        int x = coordinatesSelector.nextInt(ww);
        int y = coordinatesSelector.nextInt(wh);
        Coordinates target = new Coordinates(x,y);
        return target;
    }

    private IUnit selectUnit(IWorld world) {
        ArrayList<IUnit> units = (ArrayList<IUnit>) world.getUnits();
        Random unitSelector = new Random();
        boolean acceptableChoice = false;
        int target = 0;
        while (acceptableChoice == false) {
            target = unitSelector.nextInt(units.size());
            if (units.get(target).equals(this)) {
                acceptableChoice = true;
            }
        }
        return units.get(target);
    }

    private boolean calculationCompleated = false;

    private void move(float dt) {
        if (currentAction == action.targetPoint) {
            if (calculationCompleated == true) {
                position.setX(position.getX()+(int)(vx*dt));
                position.setY(position.getY()+(int)(vy*dt));
            } else {
                int dx = (targetPoint.getX()-position.getX());
                int dy = (targetPoint.getY()-position.getY());
                int d = (int)Math.sqrt(dx^2+dy^2);
                vx = 20*(dx/d);
                vy = 20*(dy/d);
                calculationCompleated = true;
                position.setX(position.getX()+(int)(vx*dt));
                position.setY(position.getY()+(int)(vy*dt));
            }
        } else {
            CUSInteract a = (CUSInteract) targetUnit;
            int dx = (a.getXY().getX()-position.getX());
            int dy = (a.getXY().getY()-position.getY());
            int d = (int)Math.sqrt(dx^2+dy^2);
            vx = 20*(dx/d);
            vy = 20*(dy/d);
            calculationCompleated = true;
            position.setX(position.getX()+(int)(vx*dt));
            position.setY(position.getY()+(int)(vy*dt));
        }
    }

    private void steal(IWorld world) {
        CUSInteract unit = (CUSInteract) targetUnit;
        CUSInteract clone = unit.clone();
        inventory.add(clone);
        world.removeUnit(targetUnit);
    }

    private void drop(IWorld world) {
        if (!inventory.isEmpty()) {
            Random dropper = new Random();
            int toDrop = dropper.nextInt(inventory.size());
            CUSInteract object = (CUSInteract) inventory.get(toDrop);
            object.setXY(position);
            IUnit readyToDropUnit = (IUnit) object;
            world.addUnit(readyToDropUnit);
        }
    }

    private void test(IWorld world) {
        if (currentAction == action.targetUnit) {
            if (targetUnit == null) {
                currentAction = action.idle;
                actionStatus = false;
            } else {
                CUSInteract a = (CUSInteract) targetUnit;
                if (position.equals(a.getXY())) {
                    currentAction = action.pickUnit;
                    steal(world);
                    currentAction = action.idle;
                    actionStatus = false;
                }
            }
        } else if (currentAction == action.targetPoint) {
            Random selected = new Random();
            int act = selected.nextInt(2);
            if (act >= 1) {
                currentAction = action.dropUnit;
                drop(world);
                currentAction = action.idle;
                actionStatus = false;
            }
        }
    }

    @Override
    public void step(IWorld world, float dt) {
        Random selector = new Random();
        test(world);
        if (currentAction == action.idle) {
            int selected = selector.nextInt(2);
            if (selected == 1) {
                currentAction = action.targetUnit;
                actionStatus = false;
            } else if (selected == 2) {
                currentAction = action.targetPoint;
                actionStatus = false;
            } else if (selected == 3) {
                currentAction = action.pickUnit;
            } else if (selected == 4) {
                currentAction = action.dropUnit;
            }
        } else if (currentAction == action.targetPoint) {
            if (actionStatus == false) {
                targetPoint = selectPoint(world.getWidth(), world.getHeight());
                actionStatus = true;
            } else {
                move(dt);
            }
        } else if (currentAction == action.targetUnit) {
            if (actionStatus == false) {
                targetUnit = selectUnit(world);
                actionStatus = true;
            } else {
                move(dt);
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

    public Caravan(int x, int y) {
        this.position = new Coordinates(x,y);
        inventory = new ArrayList<>();
    }
}
