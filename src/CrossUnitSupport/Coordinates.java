package CrossUnitSupport;

public class Coordinates {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates getXY() {
        return new Coordinates(x, y);
    }

    public Coordinates() {

    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}