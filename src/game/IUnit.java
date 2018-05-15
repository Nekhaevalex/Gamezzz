package game;

import java.awt.*;

public interface IUnit {
    public void draw (Graphics2D canvas);
    public void step (IWorld world, float dt);
}
