/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Ellipse extends MyShape {
    
    int[] values;
    
    public Ellipse() {
        super();
    }
    
    public Ellipse(int x, int y, int width, int height) {
        super();
        values = new int[4];
        values[0] = x;
        values[1] = y;
        values[2] = width;
        values[3] = height;
        Ellipse2D.Double s = new Ellipse2D.Double(x, y, width, height);
        PathIterator it = s.getPathIterator(null);
        double[] coords = new double[6];
        double[] prevPoint = new double[2];
        int segType;
        while(!it.isDone()) {
            double[][] points = null;
            segType = it.currentSegment(coords);
            switch(segType) {
                case PathIterator.SEG_MOVETO:
                    //System.out.println("move");
                    prevPoint[0] = coords[0];
                    prevPoint[1] = coords[1];
                    break;
                case PathIterator.SEG_LINETO:
                    //System.out.println("line");
                    break;
                case PathIterator.SEG_QUADTO:
                    //System.out.println("quad");
                    break;
                case PathIterator.SEG_CUBICTO:
                    //System.out.println("cube");
                    points = subdivide(new CubicCurve2D.Double(prevPoint[0], prevPoint[1], coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]), 0, 4);
                    prevPoint[0] = coords[4];
                    prevPoint[1] = coords[5];
                    break;
                case PathIterator.SEG_CLOSE:
                    //System.out.println("close");
                    break;
            }
            if(points != null) {
                for(double[] point : points) {
                    addPoint(MyMath.round(point[0]), MyMath.round(point[1]));
                }
                points = null;
            }
            it.next();
        }
        cleanUp();
    }

}
