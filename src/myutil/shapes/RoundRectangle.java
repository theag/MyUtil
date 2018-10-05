/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Point;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class RoundRectangle extends MyShape {
    
    public RoundRectangle() {
        super();
    }
    
    public RoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        super();
        double a = arcWidth/2.0;
        double b = arcHeight/2.0;
        int xc, yc;
        Point pp, p;
        addPoint(x + arcWidth/2, y);
        pp = new Point(x + width - arcWidth/2, y);
        addPoint(pp);
        xc = x + width - arcWidth/2;
        yc = y + arcHeight/2 + 1;
        for(double t = 3*Math.PI/2 + Math.PI/180; t <= 2*Math.PI; t += Math.PI/180) {
            p = new Point(MyMath.floor(a*Math.cos(t)) + xc, MyMath.floor(b*Math.sin(t)) + yc);
            if(p.x != pp.x && p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        p = new Point(x + width, y + arcHeight/2);
        if(p.x != pp.x && p.y != pp.y) {
            addPoint(p);
        }
        pp = new Point(x + width, y + height - arcHeight/2);
        addPoint(pp);
        xc += 1;
        yc = y + height - arcHeight/2 - 1;
        for(double t = Math.PI/180; t < Math.PI/2; t += Math.PI/180) {
            p = new Point(MyMath.floor(a*Math.cos(t)) + xc, MyMath.floor(b*Math.sin(t)) + yc);
            if(p.x != pp.x && p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        p = new Point(x + width - arcWidth/2, y + height);
        if(p.x != pp.x && p.y != pp.y) {
            addPoint(p);
        }
        pp = new Point(x + arcWidth/2, y + height);
        addPoint(pp);
        xc = x + arcWidth/2 + 1;
        yc += 1;
        for(double t = Math.PI/2; t <= Math.PI; t += Math.PI/180) {
            p = new Point(MyMath.floor(a*Math.cos(t)) + xc, MyMath.floor(b*Math.sin(t)) + yc);
            if(p.x != pp.x && p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        p = new Point(x, y + height - arcHeight/2);
        if(p.x != pp.x && p.y != pp.y) {
            addPoint(p);
        }
        pp = new Point(x, y + arcHeight/2);
        addPoint(pp);
        xc -= 1;
        yc = y + arcHeight/2 + 1;
        for(double t = Math.PI; t <= 3*Math.PI/2; t += Math.PI/180) {
            p = new Point(MyMath.floor(a*Math.cos(t)) + xc, MyMath.floor(b*Math.sin(t)) + yc);
            if(p.x != pp.x && p.y != pp.y) {
                addPoint(p);
                pp = p;
            }
        }
        addPoint(getFirst());
    }
    
}
