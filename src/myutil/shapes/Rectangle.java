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
public class Rectangle extends MyShape {
    
    private int[] values;
    
    public Rectangle() {
        super();
        values = new int[4];
    }
    
    public Rectangle(int x, int y, int width, int height) {
        super();
        addPoint(x, y);
        addPoint(x + width, y);
        addPoint(x + width, y + height);
        addPoint(x, y + height);
        addPoint(x, y);
        values = new int[4];
        values[0] = x;
        values[1] = y;
        values[2] = width;
        values[3] = height;
    }
    
    public Point getTopLeft() {
        return new Point(values[0], values[1]);
    }
    
    @Override
    public void shift(int dx, int dy) {
        super.shift(dx, dy);
        values[0] += dx;
        values[1] += dy;
    }
    
}
