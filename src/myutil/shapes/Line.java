/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Point;

/**
 *
 * @author nbp184
 */
public class Line extends MyShape {
 
    public Line() {
        super();
    }
    
    public Line(int x1, int y1, int x2, int y2) {
        super();
        addPoint(x1, y1);
        addPoint(x2, y2);
    }
    
    public Line(Point p1, Point p2) {
        super();
        addPoint(p1);
        addPoint(p2);
    }
    
}
