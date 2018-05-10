package game;

public interface IWorld {
    public Iterable<IUnit> getUnits();
    public int getWidth();
    public int getHeight();
    public float getTime();
    public void removeUnit(IUnit unit);
    public void addUnit(IUnit unit);
}
