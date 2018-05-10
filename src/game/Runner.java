package game;

import Caravan.Caravan;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        List<IUnit> initialUnits = init();
        IWorld world = new WorldImpl(500,500,initialUnits);
        IRenderer renderer = new RendererImpl(world);
        float dt = 10f;
        int iMax = 1000;
        for(float i = 0;i<iMax;i++) {
            world.step(dt);
            renderer.render(world);
            Thread.sleep(40);
        }
    }

    public static List<IUnit> init() {
        List<IUnit> units = new ArrayList();
        units.add(new Human(100,100,10,10,0));
        units.add(new Caravan(150, 150));
        return units;
    }
}
