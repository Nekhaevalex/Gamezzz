package game;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RendererImpl implements IRenderer {

    private Graphics2D canvas;
    private JFrame window;
    
    private void generateGraphics(IWorld world) {
        JFrame frame = new JFrame("Simple World");
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        frame.add(panel);
        frame.setSize(world.getWidth(), world.getHeight());
        frame.setResizable(false);
        frame.setVisible(true);
        this.window = frame;
        this.canvas = (Graphics2D)panel.getGraphics();
    }
    
    public RendererImpl(IWorld world) {
        generateGraphics(world);
    }
    
    @Override
    public void render(IWorld world) {
        this.window.setTitle("Simple World. Time ="+world.getTime());
        clear(this.canvas,world);
        for(IUnit u : world.getUnits()) {
            u.draw(this.canvas);
        }
    }

    private void clear(Graphics2D canvas,IWorld world) {
        canvas.clearRect(0, 0, world.getWidth(),
                world.getHeight());
    }
}
