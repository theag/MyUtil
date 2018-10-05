/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Hemisphere extends MyShape {
    
    public Hemisphere() {
        super();
    }
    
    public Hemisphere(Point p1, Point p2) {
        super();
        double[] centre = {(p1.x + p2.x)/2.0, (p1.y + p2.y)/2.0};
        double radius = MyMath.distance(p1, centre);
        double theta1 = Math.atan2(p1.y - centre[1], p1.x - centre[0]);
        double theta2 = Math.atan2(p2.y - centre[1], p2.x - centre[0]);
        while(theta2 < theta1) {
            theta2 += 2*Math.PI;
        }
        addPoint(p1);
        Point pp = p1, p;
        for(double t = theta1; t < theta2; t += Math.PI/180) {
            p = new Point(MyMath.round(radius*Math.cos(t) + centre[0]), MyMath.round(radius*Math.sin(t) + centre[1]));
            if(p.x != pp.x || p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        if(p2.x != pp.x || p2.y != pp.y) {
            addPoint(p2);
        }
        cleanUp();
    }
    
    public Hemisphere(int x1, int y1, int x2, int y2) {
        super();
        double[] centre = {(x1 + x2)/2.0, (y1 + y2)/2.0};
        double radius = Math.sqrt((centre[0] - x1)*(centre[0] - x1) + (centre[1] - y1)*(centre[1] - y1));
        double theta1 = Math.atan2(y1 - centre[1], x1 - centre[0]);
        double theta2 = Math.atan2(y2 - centre[1], x2 - centre[0]);
        while(theta2 < theta1) {
            theta2 += 2*Math.PI;
        }
        addPoint(x1, y1);
        Point pp = new Point(x1, y1), p;
        for(double t = theta1; t < theta2; t += Math.PI/180) {
            p = new Point(MyMath.round(radius*Math.cos(t) + centre[0]), MyMath.round(radius*Math.sin(t) + centre[1]));
            if(p.x != pp.x || p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        if(x2 != pp.x || y2 != pp.y) {
            addPoint(x2, y2);
        }
        cleanUp();
    }
    
    public void drawDots(Graphics2D g) {
        Iterator<Point> it = iterator();
        Point p;
        while(it.hasNext()) {
            p = it.next();
            g.drawLine(p.x, p.y, p.x, p.y);
        }
    }
    
}
