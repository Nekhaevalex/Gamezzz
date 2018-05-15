package Caravan;

import CrossUnitSupport.CUSInteract;
import CrossUnitSupport.Coordinates;
import game.IUnit;
import game.IWorld;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//TODO: Караван, – время жизни: infinity, – повозка: желтая, 10x20px, – берет и увозит с собой, сбрасывает в случайном месте,– скорость 20

public class Caravan extends CUSInteract implements IUnit {

    //Position:
    private Coordinates position;
    //Speed:
    private int vx;
    private int vy;

    private Action currentAction = Action.idle;
    private boolean actionStatus = false;

    private ArrayList<CUSInteract> inventory;

    private IUnit targetUnit;
    private Coordinates targetPoint;

    @Override
    public void draw(Graphics2D canvas) {
        int x = position.getX();
        int y = position.getY();
        canvas.setColor(new Color(255, 195, 0));
        canvas.drawRect(x - 10, y - 5, 20, 6);
        canvas.fillRect(x - 10, y - 5, 20, 6);
        canvas.setColor(new Color(0, 0, 0));
        canvas.drawOval(x - 10, y + 1, 4, 4);
        canvas.drawOval(x + 5, y + 1, 4, 4);
        canvas.fillOval(x - 10, y + 1, 4, 4);
        canvas.fillOval(x + 5, y + 1, 4, 4);
    }

    private Coordinates selectPoint(int ww, int wh) {
        Random coordinatesSelector = new Random();
        int x = coordinatesSelector.nextInt(ww-position.getX());
        int y = coordinatesSelector.nextInt(wh-position.getY());
        return new Coordinates(x, y);
    }

    private IUnit selectUnit(IWorld world) {
        ArrayList<IUnit> units = (ArrayList<IUnit>) world.getUnits();
        Random unitSelector = new Random();
        boolean acceptableChoice = false;
        int target = 0;
        while (!acceptableChoice) {
            target = unitSelector.nextInt(units.size());
            if (!units.get(target).equals(this)) {
                acceptableChoice = true;
            }
        }
        return units.get(target);
    }

    private boolean calculationCompleated = false;

    private void move(float dt) {
        if (currentAction == Action.targetPoint) {
            int dx = (targetPoint.getX() - position.getX());
            int dy = (targetPoint.getY() - position.getY());
            int d = (int) Math.sqrt(dx*dx + dy*dy);
            vx = Math.round(20*((float)dx / (float) d));
            vy = Math.round(20*((float)dy / (float) d));
            calculationCompleated = true;
            position.setX(position.getX() + (int) (vx * dt));
            position.setY(position.getY() + (int) (vy * dt));
        } else {
            CUSInteract a = (CUSInteract) targetUnit;
            if (!(targetUnit instanceof CUSInteract)) {
                return;
            }
            int dx = (a.getXY().getX() - position.getX());
            int dy = (a.getXY().getY() - position.getY());
            int d = (int) Math.sqrt(dx ^ 2 + dy ^ 2);
            vx = 20 * (dx / d);
            vy = 20 * (dy / d);
            calculationCompleated = true;
            position.setX(position.getX() + (int) (vx * dt));
            position.setY(position.getY() + (int) (vy * dt));
        }
    }

    private void steal(IWorld world) throws CloneNotSupportedException {
        CUSInteract unit = (CUSInteract) targetUnit;
        CUSInteract clone = (CUSInteract) unit.clone();
        inventory.add(clone);
        world.removeUnit(targetUnit);
    }

    private void drop(IWorld world) {
        if (!inventory.isEmpty()) {
            Random dropper = new Random();
            int toDrop = dropper.nextInt(inventory.size());
            CUSInteract object = inventory.get(toDrop);
            object.setXY(position);
            IUnit readyToDropUnit = (IUnit) object;
            world.addUnit(readyToDropUnit);
        }
    }

    private void test(IWorld world) throws CloneNotSupportedException {
        if (currentAction == Action.targetUnit) {
            if (!(targetUnit instanceof CUSInteract)) {
                currentAction = Action.idle;
                actionStatus = false;
                targetUnit = null;
            }
            if (targetUnit == null) {
                currentAction = Action.idle;
                actionStatus = false;
            } else {
                CUSInteract a = (CUSInteract) targetUnit;
                if (position.equals(a.getXY())) {
                    currentAction = Action.pickUnit;
                    steal(world);
                    currentAction = Action.idle;
                    actionStatus = false;
                }
            }
        } else if (currentAction == Action.targetPoint) {
            Random selected = new Random();
            int act = selected.nextInt(2);
            if (act >= 1) {
                currentAction = Action.dropUnit;
                drop(world);
                currentAction = Action.idle;
                actionStatus = false;
                calculationCompleated = false;
            }
        }
    }

    @Override
    public void step(IWorld world, float dt) {
        Random selector = new Random();
        try {
            test(world);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (currentAction == Action.idle) {
            int selected = selector.nextInt(3);
            if (selected == 1) {
                currentAction = Action.targetUnit;
                actionStatus = false;
            } else if (selected == 2) {
                currentAction = Action.targetPoint;
                actionStatus = false;
            } else if (selected == 3) {
                currentAction = Action.pickUnit;
            } else if (selected == 4) {
                currentAction = Action.dropUnit;
            }
        }
        if (currentAction == Action.targetPoint) {
            if (!actionStatus) {
                targetPoint = selectPoint(world.getWidth(), world.getHeight());
                actionStatus = true;
            }
            if (actionStatus) {
                move(dt);
            }
        } else if (currentAction == Action.targetUnit) {
            if (!actionStatus) {
                targetUnit = selectUnit(world);
                actionStatus = true;
            }
            if (actionStatus) {
                try {
                    test(world);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
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
        this.position = new Coordinates(x, y);
        inventory = new ArrayList<>();
    }
}
