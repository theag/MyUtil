/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class MyShape implements Iterable<Point> {
    
    public static int NOT_OVER = 0;
    public static int TOP_OVER = 1;
    public static int RIGHT_OVER = 2;
    public static int BOTTOM_OVER = 4;
    public static int LEFT_OVER = 8;
    public static int NO_OVERLAP = -1;
    
    private final ArrayDeque<Point> points;
    private int[] bounds;
    
    public MyShape() {
        points = new ArrayDeque<>();
    }
    
    public void addPoint(int x, int y) {
        points.addLast(new Point(x, y));
        if(bounds != null) {
            if(x < bounds[0]) {
                bounds[0] = x;
            } else if(x > bounds[2]) {
                bounds[2] = x;
            }
            if(y < bounds[1]) {
                bounds[1] = y;
            } else if(y > bounds[3]) {
                bounds[3] = y;
            }
        } else {
            bounds = new int[4];
            bounds[0] = x;
            bounds[1] = y;
            bounds[2] = x;
            bounds[3] = y;
        }
    }
    
    public void addPoint(Point p) {
        points.addLast(new Point(p.x, p.y));
        if(bounds != null) {
            if(p.x < bounds[0]) {
                bounds[0] = p.x;
            } else if(p.x > bounds[2]) {
                bounds[2] = p.x;
            }
            if(p.y < bounds[1]) {
                bounds[1] = p.y;
            } else if(p.y > bounds[3]) {
                bounds[3] = p.y;
            }
        } else {
            bounds = new int[4];
            bounds[0] = p.x;
            bounds[1] = p.y;
            bounds[2] = p.x;
            bounds[3] = p.y;
        }
    }
    
    public Point getFirst() {
        return points.getFirst();
    }
    
    public Point getLast() {
        return points.getLast();
    }
    
    public void draw(Graphics g) {
        if(!points.isEmpty()) {
            Iterator<Point> it = points.iterator();
            Point p1 = it.next();
            Point p2;
            while(it.hasNext()) {
                p2 = it.next();
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                p1 = p2;
            }
        }
    }
    
    public void fill(Graphics g) {
        int x[] = new int[points.size()];
        int y[] = new int[points.size()];
        int index = 0;
        for(Point p : points) {
            x[index] = p.x;
            y[index++] = p.y;
        }
        g.fillPolygon(x, y, x.length);
    }
    
    public MyShape join(MyShape shape) {
        MyShape rv = new MyShape();
        boolean skip;
        if(shape == null || shape.points.isEmpty()) {
            for(Point p : points) {
                rv.addPoint(p);
            }
        } else if(points.isEmpty()) {
            for(Point p : shape.points) {
                rv.addPoint(p);
            }
        } else if(points.getLast().equals(shape.points.getFirst())) {
            for(Point p : points) {
                rv.addPoint(p);
            }
            skip = true;
            for(Point p : shape.points) {
                if(skip) {
                    skip = false;
                } else {
                    rv.addPoint(p);
                }
            }
        } else if(shape.points.getLast().equals(points.getFirst())) {
            for(Point p : shape.points) {
                rv.addPoint(p);
            }
            skip = true;
            for(Point p : points) {
                if(skip) {
                    skip = false;
                } else {
                    rv.addPoint(p);
                }
            }
        } else if(points.getFirst().equals(shape.points.getFirst())) {
            Iterator<Point> it = points.descendingIterator();
            while(it.hasNext()) {
                rv.addPoint(it.next());
            }
            skip = true;
            for(Point p : shape.points) {
                if(skip) {
                    skip = false;
                } else {
                    rv.addPoint(p);
                }
            }
        } else if(points.getLast().equals(shape.points.getLast())) {
            for(Point p : points) {
                rv.addPoint(p);
            }
            skip = true;
            Iterator<Point> it = shape.points.descendingIterator();
            while(it.hasNext()) {
                if(skip) {
                    it.next();
                    skip = false;
                } else {
                    rv.addPoint(it.next());
                }
            }
        } else {
            for(Point p : points) {
                rv.addPoint(p);
            }
            for(Point p : shape.points) {
                rv.addPoint(p);
            }
        }
        return rv;
    }

    @Override
    public Iterator<Point> iterator() {
        return points.iterator();
    }
    
    public Point[][] intersect(MyShape shape) {
        ArrayList<Point> checkPoints = new ArrayList<>();
        ArrayList<Point[]> intersections = new ArrayList<>();
        Point pp = null, pps = null;
        double m, ms, t, x, y;
        Point[] toAdd;
        for(Point p : points) {
            if(pp != null) {
                m = (p.y - pp.y)*1.0/(p.x - pp.x);
                for(Point ps : shape.points) {
                    if(pps != null) {
                        ms = (ps.y - pps.y)*1.0/(ps.x - pps.x);
                        if(ms == 0) {
                            if((p.y >= ps.y && pp.y <= ps.y) || (p.y <= ps.y && pp.y >= ps.y)) {
                                t = p.y - m*p.x;
                                x = (ps.y - t)/m;
                                y = ps.y;
                                if(x >= Math.min(pp.x, p.x) && x >= Math.min(pps.x, ps.x) && x <= Math.max(pp.x, p.x) && x <= Math.max(pps.x, ps.x) &&
                                    y >= Math.min(pp.y, p.y) && y >= Math.min(pps.y, ps.y) && y <= Math.max(pp.y, p.y) && y <= Math.max(pps.y, ps.y)) {
                                    toAdd = new Point[5];
                                    toAdd[0] = new Point(MyMath.round(x), MyMath.round(y));
                                    toAdd[1] = pp;
                                    toAdd[2] = p;
                                    toAdd[3] = pps;
                                    toAdd[4] = ps;
                                    if(!checkPoints.contains(toAdd[0])) {
                                        intersections.add(toAdd);
                                        checkPoints.add(toAdd[0]);
                                    }
                                }
                            }
                        } else if(m != ms) {
                            t = (pps.x + (ps.x - pps.x)*(pp.y - pps.y)*1.0/(ps.y - pps.y) - pp.x)/(p.x - pp.x - (ps.x - pps.x)*(p.y - pp.y)/(ps.y - pps.y));
                            x = pp.x + (p.x - pp.x)*t;
                            y = pp.y + (p.y - pp.y)*t;
                            if(x >= Math.min(pp.x, p.x) && x >= Math.min(pps.x, ps.x) && x <= Math.max(pp.x, p.x) && x <= Math.max(pps.x, ps.x) &&
                                    y >= Math.min(pp.y, p.y) && y >= Math.min(pps.y, ps.y) && y <= Math.max(pp.y, p.y) && y <= Math.max(pps.y, ps.y)) {
                                toAdd = new Point[5];
                                toAdd[0] = new Point(MyMath.round(x), MyMath.round(y));
                                toAdd[1] = pp;
                                toAdd[2] = p;
                                toAdd[3] = pps;
                                toAdd[4] = ps;
                                if(!checkPoints.contains(toAdd[0])) {
                                    intersections.add(toAdd);
                                    checkPoints.add(toAdd[0]);
                                }
                            }
                        }
                    }
                    pps = ps;
                }
                pps = null;
            }
            pp = p;
        }
        Point[][] rv = new Point[intersections.size()][];
        intersections.toArray(rv);
        return rv;
    }

    public void drawClosed(Graphics2D g) {
        Iterator<Point> it = points.iterator();
        Point p1 = it.next();
        Point p2;
        while(it.hasNext()) {
            p2 = it.next();
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            p1 = p2;
        }
        p2 = points.getFirst();
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    
    public MyShape deepCopy() {
        MyShape dolly = new MyShape();
        for(Point p : points) {
            dolly.addPoint(p);
        }
        return dolly;
    }
    
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    public boolean contains(int x, int y) {
        Iterator<Point> it = points.iterator();
        Point p1 = it.next();
        Point p2;
        double m, b, xi;
        int leftCount = 0;
        int rightCount = 0;
        while(it.hasNext()) {
            p2 = it.next();
            if((p1.y < y && p2.y >= y) || (p1.y >= y && p2.y < y)) {
                if(p1.x == p2.x) {
                    if(p1.x < x) {
                        leftCount++;
                    } else if(p1.x > x) {
                        rightCount++;
                    } else {
                        return true;
                    }
                } else {
                    m = (p1.y - p2.y)*1.0/(p1.x - p2.x);
                    b = p1.y - m*p1.x;
                    xi = (y - b)/m;
                    if(xi < x) {
                        leftCount++;
                    } else if(xi > x) {
                        rightCount++;
                    } else {
                        return true;
                    }
                }
            }
            p1 = p2;
        }
        p2 = points.getFirst();
        if((p1.y < y && p2.y >= y) || (p1.y >= y && p2.y < y)) {
            if(p1.x == p2.x) {
                if(p1.x < x) {
                    leftCount++;
                } else if(p1.x > x) {
                    rightCount++;
                } else {
                    return true;
                }
            } else {
                m = (p1.y - p2.y)*1.0/(p1.x - p2.x);
                b = p1.y - m*p1.x;
                xi = (y - b)/m;
                if(xi < x) {
                    leftCount++;
                } else if(xi > x) {
                    rightCount++;
                } else {
                    return true;
                }
            }
        }
        return leftCount%2 == 1 && rightCount%2 == 1;
    }

    public int[] getBounds() {
        int[] rv = new int[4];
        System.arraycopy(bounds, 0, rv, 0, 4);
        return rv;
    }

    public MyShape reverse() {
        MyShape rv = new MyShape();
        Iterator<Point> it = points.descendingIterator();
        while(it.hasNext()) {
            rv.addPoint(it.next());
        }
        return rv;
    }

    public void shift(int dx, int dy) {
        for(Point p : points) {
            p.x += dx;
            p.y += dy;
        }
        if(bounds != null) {
            bounds[0] += dx;
            bounds[1] += dy;
            bounds[2] += dx;
            bounds[3] += dy;
        }
    }

    public Iterator<Point> descendingIterator() {
        return points.descendingIterator();
    }
    
    protected double[][] subdivide(CubicCurve2D.Double s, int depth, int limit) {
        double[][] rv = null;
        if(depth >= limit) {
            PathIterator it = s.getPathIterator(null);
            double[] coords = new double[6];
            int segType;
            while(!it.isDone()) {
                segType = it.currentSegment(coords);
                int count;
                switch(segType) {
                    case PathIterator.SEG_MOVETO:
                        count = 2;
                        break;
                    case PathIterator.SEG_LINETO:
                        count = 2;
                        break;
                    case PathIterator.SEG_QUADTO:
                        count = 4;
                        break;
                    case PathIterator.SEG_CUBICTO:
                        count = 6;
                        break;
                    case PathIterator.SEG_CLOSE:
                        count = 0;
                        break;
                    default:
                        count = 0;
                        break;
                }
                rv = new double[count/2][2];
                for(int i = 0; i < count; i += 2) {
                    rv[i/2][0] = coords[i];
                    rv[i/2][1] = coords[i+1];
                }
                it.next();
            }
        } else {
            CubicCurve2D.Double sLeft = new CubicCurve2D.Double();
            CubicCurve2D.Double sRight = new CubicCurve2D.Double();
            s.subdivide(sLeft, sRight);
            double[][] left = subdivide(sLeft, depth+1, limit);
            double[][] right = subdivide(sRight, depth+1, limit);
            rv = new double[left.length + right.length][2];
            System.arraycopy(left, 0, rv, 0, left.length);
            System.arraycopy(right, 0, rv, left.length, right.length);
        }
        return rv;
    }
    
    protected double[][] subdivide(QuadCurve2D.Double s, int depth, int limit) {
        double[][] rv = null;
        if(depth >= limit) {
            PathIterator it = s.getPathIterator(null);
            double[] coords = new double[6];
            int segType;
            while(!it.isDone()) {
                segType = it.currentSegment(coords);
                int count;
                switch(segType) {
                    case PathIterator.SEG_MOVETO:
                        count = 2;
                        break;
                    case PathIterator.SEG_LINETO:
                        count = 2;
                        break;
                    case PathIterator.SEG_QUADTO:
                        count = 4;
                        break;
                    case PathIterator.SEG_CUBICTO:
                        count = 6;
                        break;
                    case PathIterator.SEG_CLOSE:
                        count = 0;
                        break;
                    default:
                        count = 0;
                        break;
                }
                rv = new double[count/2][2];
                for(int i = 0; i < count; i += 2) {
                    rv[i/2][0] = coords[i];
                    rv[i/2][1] = coords[i+1];
                }
                it.next();
            }
        } else {
            QuadCurve2D.Double sLeft = new QuadCurve2D.Double();
            QuadCurve2D.Double sRight = new QuadCurve2D.Double();
            s.subdivide(sLeft, sRight);
            double[][] left = subdivide(sLeft, depth+1, limit);
            double[][] right = subdivide(sRight, depth+1, limit);
            rv = new double[left.length + right.length][2];
            System.arraycopy(left, 0, rv, 0, left.length);
            System.arraycopy(right, 0, rv, left.length, right.length);
        }
        return rv;
    }
    
    public int numPoints() {
        return points.size();
    }
    
    protected void cleanUp() {
        Point p1 = null;
        Point p2 = null;
        Point p3 = null;
        ArrayList<Point> toRemove = new ArrayList<>();
        for(Point p : points) {
            if(p1 == null) {
                p1 = p;
            } else if(p2 == null) {
                p2 = p;
            } else {
                p3 = p;
                if((p2.x == p1.x && p2.y == p3.y) || (p2.x == p3.x && p1.y == p3.y)) {
                    toRemove.add(p2);
                    p2 = p3;                    
                } else {
                    p1 = p2;
                    p2 = p3;
                }
            }
        }
        for(Point p : toRemove) {
            points.remove(p);
        }
    }

    public int containsShape(MyShape s) {
        int[] sides = new int[4];// up, down, left, right;
        int index;
        int rv = NOT_OVER;
        int notInCount = 0;
        for(Point ps : s) {
            if(!contains(ps)) {
                notInCount++;
                if(rv < 15) {
                    sides[0] = 0;
                    sides[1] = 0;
                    sides[2] = 0;
                    sides[3] = 0;
                    for(Point p : points) {
                        if(ps.y < p.y) {
                            sides[0]++;
                        } else if(ps.y > p.y) {
                            sides[1]++;
                        }
                        if(ps.x < p.x) {
                            sides[2]++;
                        } else if(ps.x > p.x) {
                            sides[3]++;
                        }
                    }
                    index = 0;
                    for(int i = 1; i < sides.length; i++) {
                        if(sides[i] > sides[index]) {
                            index = i;
                        }
                    }
                    if(sides[0] == sides[index]) {
                        rv = rv | TOP_OVER;
                    }
                    if(sides[1] == sides[index]) {
                        rv = rv | BOTTOM_OVER;
                    }
                    if(sides[2] == sides[index]) {
                        rv = rv | LEFT_OVER;
                    }
                    if(sides[3] == sides[index]) {
                        rv = rv | RIGHT_OVER;
                    }
                }
            }
        }
        if(notInCount == s.numPoints()) {
            return NO_OVERLAP;
        } else {
            return rv;
        }
    }
    
    public Polygon getPolygon() {
        Polygon p = new Polygon();
        for(Point pt : points) {
            p.addPoint(pt.x, pt.y);
        }
        return p;
    }

    public Point getCentre() {
        int x = 0;
        int y = 0;
        for(Point p : points) {
            x += p.x;
            y += p.y;
        }
        return new Point(x/points.size(), y/points.size());
    }
    
}
