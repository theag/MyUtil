/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Arc extends MyShape {
    
    public Arc() {
        super();
    }
    
    public Arc(int x, int y, int width, int height, int arcStart, int arcLength) {
        super();
        Arc2D.Double a = new Arc2D.Double(x, y, width, height, arcStart, arcLength, Arc2D.OPEN);
        PathIterator it = a.getPathIterator(null);
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
                    points = subdivide(new QuadCurve2D.Double(prevPoint[0], prevPoint[1], coords[0], coords[1], coords[2], coords[3]), 0, 4);
                    prevPoint[0] = coords[2];
                    prevPoint[1] = coords[3];
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
