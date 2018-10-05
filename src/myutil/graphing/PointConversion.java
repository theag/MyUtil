/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

/**
 *
 * @author nbp184
 */
public class PointConversion {
    
    private double iTox[];
    private double jToy[];
    private double dx;
    private double dy;
    private int margin;
    
    public PointConversion(int width, int height, int margin, double xMin, double xMax, double yMin, double yMax) {
        this.margin = margin;
        iTox = new double[width-2*margin+1];
        jToy = new double[height-2*margin+1];
        dx = (xMax - xMin)/(iTox.length-1);
        for(int i = 0; i < iTox.length; i++) {
            iTox[i] = xMin + i*dx;
        }
        dy = (yMax - yMin)/(jToy.length-1);
        for(int j = 0; j < jToy.length; j++) {
            jToy[j] = yMax - j*dy;
        }
    }

    public PointConversion(int width, int height, int margin, double xMin, double xMax, double yCentre) {
        this.margin = margin;
        iTox = new double[width-2*margin+1];
        jToy = new double[height-2*margin+1];
        dx = (xMax - xMin)/(iTox.length-1);
        for(int i = 0; i < iTox.length; i++) {
            iTox[i] = xMin + i*dx;
        }
        dy = dx;
        double yMax = (dx*(jToy.length-1) + 2*yCentre)/2;
        for(int j = 0; j < jToy.length; j++) {
            jToy[j] = yMax - j*dx;
        }
    }
    
    public int getMargin() {
        return margin;
    }
    
    public int getWidth() {
        return iTox.length-1;
    }
    
    public int getHeight() {
        return jToy.length-1;
    }

    public double getX(int i) {
        if(i < 0 || i >= iTox.length) {
            return Double.NaN;
        } else {
            return iTox[i];
        }
    }

    public double getY(int j) {
        if(j < 0 || j >= jToy.length) {
            return Double.NaN;
        } else {
            return jToy[j];
        }
    }

    public double getDomainWidth() {
        return iTox[iTox.length-1] - iTox[0];
    }

    public int getI(double x) {
        if(x < iTox[0]) {
            int rv = 0;
            double rvX = iTox[0];
            while(rvX > x) {
                rv--;
                rvX -= dx;
            }
            if(Math.abs(rvX - x) < Math.abs(rvX + dx - x)) {
                return rv;
            } else {
                return rv + 1;
            }
        } else if(x > iTox[iTox.length-1]) {
            int rv = iTox.length-1;
            double rvX = iTox[iTox.length-1];
            while(rvX < x) {
                rv++;
                rvX += dx;
            }
            if(Math.abs(rvX - x) < Math.abs(rvX - dx - x)) {
                return rv;
            } else {
                return rv - 1;
            }
        } else {
            int rv = 0;
            for(int i = 1; i < iTox.length; i++) {
                if(Math.abs(iTox[i] - x) < Math.abs(iTox[rv] - x)) {
                    rv = i;
                }
            }
            return rv;
        }
    }

    public int getJ(double y) {
        if(y < jToy[0]) {
            int rv = 0;
            double rvY = jToy[0];
            while(rvY > y) {
                rv--;
                rvY -= dy;
            }
            if(Math.abs(rvY - y) < Math.abs(rvY + dy - y)) {
                return rv;
            } else {
                return rv + 1;
            }
        } else if(y > jToy[jToy.length-1]) {
            int rv = jToy.length-1;
            double rvY = jToy[jToy.length-1];
            while(rvY < y) {
                rv++;
                rvY += dy;
            }
            if(Math.abs(rvY - y) < Math.abs(rvY - dy - y)) {
                return rv;
            } else {
                return rv - 1;
            }
        } else {
            int rv = 0;
            for(int i = 1; i < jToy.length; i++) {
                if(Math.abs(jToy[i] - y) < Math.abs(jToy[rv] - y)) {
                    rv = i;
                }
            }
            return rv;
        }
    }
    
}
