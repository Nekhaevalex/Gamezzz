/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Батат
 */
import java.awt.Color;
import java.awt.Graphics2D;

public class Stone implements IUnit {
 
private static int rndkam(int min, int max)
        {
                max -= min;
                return (int) (Math.random() * ++max) + min;
        }
    private int k=0;
    private int i;
    private int x;
    private int y;
    private int wmax = 30;
    private int wmin = 10;
    private int hmax = 20;
    private int hmin = 10;
    private int w = rndkam(wmin,wmax);
    private int h = rndkam(hmin,hmax);
    private int nmax = 15;
    private int nmin = 5;
    private int n = rndkam(nmin,nmax);
    private int[] xk = new int[15];
    private int[] yk = new int[15];
    private int[] wk = new int[15];
    private int[] hk = new int[15];
        public Stone(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void draw(Graphics2D canvas) {
        canvas.setColor(Color.LIGHT_GRAY);
        canvas.fillOval(x, y, w, h);
        if (k==0){ for(i = 0;i<n;i++) {
            xk[i]=rndkam(x,x+w-8);
            yk[i]=rndkam(y,y+h-8);
            wk[i]=8;
            hk[i]=8;
           canvas.fillOval(xk[i],yk[i],wk[i],hk[i]);
           k++;
        }}
        for(i = 0;i<n;i++) {   
           canvas.fillOval(xk[i],yk[i],wk[i],hk[i]);
        }
    
    }
    @Override
    public void step(IWorld world, float dt) {
    }
}
