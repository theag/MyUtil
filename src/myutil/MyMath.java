/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.awt.Point;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import java.util.Random;

/**
 *
 * @author nbp184
 */
public class MyMath {
    
    public static final Random rand = new Random();
    
    public static int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static int ceil(float x) {
        return (int)Math.ceil(x);
    }
    
    public static int floor(float x) {
        return (int)Math.floor(x);
    }
    
    public static int ceil(double x) {
        return (int)Math.ceil(x);
    }
    
    public static int floor(double x) {
        return (int)Math.floor(x);
    }

    public static int randSign() {
        if(rand.nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public static int round(double d) {
        return (int)Math.round(d);
    }
    
    public static double round(double d, int places) {
        double push = Math.pow(10, places);
        return Math.round(d * push)/push;
    }

    public static int sign(double normalValue) {
        if(normalValue > 0) {
            return 1;
        } else if(normalValue < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int atan2d(int y, int x) {
        return (int)Math.round(Math.atan2(y, x)*180/Math.PI);
    }

    public static int nonNegativeMin(int a, int b) {
        if(a < 0) {
            return b;
        } else if(b < 0) {
            return a;
        } else {
            return Math.min(a, b);
        }
    }
    
    public static void shuffle(Object[] arr) {
        Object temp;
        int j;
        for(int i = arr.length - 1; i > 0; i--) {
            j = rand.nextInt(i+1);
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    
    public static void shuffle(Object[] arr, int count) {
        Object temp;
        int j;
        for(int c = 0; c < count; c++) {
            for(int i = arr.length - 1; i > 0; i--) {
                j = rand.nextInt(i+1);
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    
    public static void shuffle(Object[] arr, int length, int count) {
        Object temp;
        int j;
        for(int c = 0; c < count; c++) {
            for(int i = length - 1; i > 0; i--) {
                j = rand.nextInt(i+1);
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    
    public static int[] findArc(int x, int y, int h, int c, int section) {
        double r = (4.0*h*h + c*c)/(8.0*h);
        int theta = 2*round(Math.atan2(c,2*(r - h))*180/Math.PI);
        if(section == 0) {
            int[] rv = {round(x + c/2.0 - r), y - h, round(2*r), 90 - theta/2, theta};
            return rv;
        }else if(section == 1) {
            int[] rv = {round(x + h - 2*r), round(y + c/2.0 - r), round(2*r), theta/2, -theta};
            return rv;
        } else if(section == 2) {
            int[] rv = {round(x + c/2.0 - r), round(y - 2*r + h), round(2*r), 270 + theta/2, -theta};
            return rv;
        } else if(section == 3) {
            int[] rv = {x - h, round(y + c/2.0 - r), round(2*r), 90 + theta/2, theta};
            return rv;
        } else {
            return null;
        }
    }
    
    public static int[][] findCurve(int xs, int ys, int xt, int yt, int xe, int ye) {
        QuadCurve2D curve = new QuadCurve2D.Float(xs, ys, xt, yt, xe, ye);
        PathIterator it = curve.getPathIterator(null);
        int[][] rv = new int[2][3];
        int index = 0;
        float[] coords = new float[6];
        int type;
        while(!it.isDone()) {
            type = it.currentSegment(coords);
            if(type == PathIterator.SEG_MOVETO) {
                rv[0][index] = Math.round(coords[0]);
                rv[1][index++] = Math.round(coords[1]);
            } else if(type == PathIterator.SEG_QUADTO) {
                rv[0][index] = Math.round(coords[0]);
                rv[1][index++] = Math.round(coords[1]);
                rv[0][index] = Math.round(coords[2]);
                rv[1][index++] = Math.round(coords[3]);
            }
            it.next();
        }
        return rv;
    }

    public static int distance(int[] p1, int[] p2) {
        if(p1.length != p2.length) {
            return -1;
        }
        int sum = 0;
        for(int i = 0; i < p1.length; i++) {
            sum += (p1[i] - p2[i])*(p1[i] - p2[i]);
        }
        return round(Math.sqrt(sum));
    }

    public static double dot(double[] a, double[] b) {
        if(a.length != b.length) {
            return -1;
        }
        double sum = 0;
        for(int i = 0; i < a.length; i++) {
            sum += a[i]*b[i];
        }
        return sum;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + ((p1.y - p2.y)*(p1.y - p2.y)));
    }
    
    public static double distance(Point p1, double[] p2) {
        return Math.sqrt((p1.x - p2[0])*(p1.x - p2[0]) + ((p1.y - p2[1])*(p1.y - p2[1])));
    }

    public static boolean between(int x1, int x, int x2) {
        return (x1 <= x2 && x >= x1 && x <= x2) || (x1 > x2 && x <= x1 && x >= x2);
    }

    public static String pointToString(Point p) {
        return "(" +p.x +"," +p.y +")";
    }

}
