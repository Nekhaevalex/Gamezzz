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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinates getXY() {
        return new Coordinates(x, y);
    }

    public boolean equals(Coordinates obj) {
        return this.x == obj.x && this.y == obj.y;
    }

    public Coordinates() {

    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
