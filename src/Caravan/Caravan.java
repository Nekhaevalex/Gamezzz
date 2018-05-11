package Caravan;

import game.IUnit;
import game.IWorld;

import java.awt.*;
import java.awt.geom.Ellipse2D;

//TODO: Караван, – время жизни: infinity, – повозка: желтая, 10x20px, – берет и увозит с собой, сбрасывает в случайном месте,– скорость 20

public class Caravan implements IUnit {

    //Position:
    private int x;
    private int y;
    //Speed:
    private int vx;
    private int vy;

    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(new Color(255, 195,0));
        canvas.drawRect(x-10, y-5, 20,6);
        canvas.fillRect(x-10, y-5, 20,6);
        canvas.setColor(new Color(0,0,0));
        canvas.drawOval(x-10,y+1,4,4);
        canvas.drawOval(x+5,y+1,4,4);
        canvas.fillOval(x-10,y+1,4,4);
        canvas.fillOval(x+5,y+1,4,4);

    }

    @Override
    public void step(IWorld world, float dt) {

    }

    public Caravan(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
