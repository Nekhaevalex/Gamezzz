package CrossUnitSupport;


abstract public class CUSInteract implements Cloneable{
    public abstract Coordinates getXY();
    public abstract void setXY(Coordinates position);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
