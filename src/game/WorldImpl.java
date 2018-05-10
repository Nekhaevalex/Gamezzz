package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldImpl implements IWorld {

    private List<IUnit> units = new ArrayList();
    private List<IUnit> toRemove = new ArrayList();
    private List<IUnit> toAdd = new ArrayList();
    private int w;
    private int h;
    private float time;

    public WorldImpl(int w,int h,List<IUnit> initialUnits) {
        this.w = w;
        this.h = h;
        this.units.addAll(initialUnits);
    }

    @Override
    public void step(float dt) {
        this.time += dt;
        Iterator<IUnit> it = units.iterator();
        while (it.hasNext()) {
            it.next().step(this, dt);
        }
        this.units.removeAll(this.toRemove);
        this.toRemove = new ArrayList();
        this.units.addAll(this.toAdd);
        this.toAdd = new ArrayList();
    }

    @Override
    public Iterable<IUnit> getUnits() {
        return new ArrayList(units);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public float getTime() {
        return time;
    }

    @Override
    public void removeUnit(IUnit unit) {
        this.toRemove.add(unit);
    }

    @Override
    public void addUnit(IUnit unit) {
        this.toAdd.add(unit);
    }
}
