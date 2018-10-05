/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.CubicCurve2D;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class CubicCurve extends MyShape {
    
    private final int[][] points;
    
    public CubicCurve() {
        super();
        points = new int[4][2];
        addPoint(0, 0);
        addPoint(0, 0);
    }
    
    public CubicCurve(int x1, int y1, int ctrlx1, int ctrly1, int ctrlx2, int ctrly2, int x2, int y2) {
        CubicCurve2D.Double s = new CubicCurve2D.Double(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
        double[][] cPoints = subdivide(s, 0, 4);
        addPoint(x1, y1);
        boolean earlyStop = false;
        for(double[] p : cPoints) {
            addPoint(MyMath.round(p[0]), MyMath.round(p[1]));
            if(getLast().x == x2 && getLast().y == y2) {
                earlyStop = true;
                break;
            }
        }
        if(!earlyStop) {
            addPoint(x2, y2);
        }
        points = new int[4][2];
        points[0][0] = x1;
        points[0][1] = y1;
        points[1][0] = ctrlx1;
        points[1][1] = ctrly1;
        points[2][0] = ctrlx2;
        points[2][1] = ctrly2;
        points[3][0] = x2;
        points[3][1] = y2;
    }
    
    public void draw(Graphics g, boolean ctrlLine) {
        super.draw(g);
        Color c = g.getColor();
        g.setColor(Color.red);
        g.drawLine(points[0][0], points[0][1], points[1][0], points[1][1]);
        g.drawLine(points[1][0], points[1][1], points[2][0], points[2][1]);
        g.drawLine(points[2][0], points[2][1], points[3][0], points[3][1]);
        g.setColor(c);
    }
    
}
