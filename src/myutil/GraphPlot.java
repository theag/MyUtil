/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author nbp184
 */
public class GraphPlot {
    
    private static final Color[] colours = {Color.red, Color.blue};
    
    private ArrayList<double[][]> graphs;
    private double xmin;
    private double ymax;
    private int pixelsPerUnit;
    
    public GraphPlot() {
        graphs = new ArrayList<>();
    }
    
    public void addGraph(double[] ydata, double[] xdata) {
        double graph[][] = new double[2][ydata.length];
        System.arraycopy(xdata, 0, graph[0], 0, xdata.length);
        System.arraycopy(ydata, 0, graph[1], 0, ydata.length);
        graphs.add(graph);
    }
    
    public void addGraph(double[] ydata) {
        double graph[][] = new double[2][ydata.length];
        System.arraycopy(ydata, 0, graph[1], 0, ydata.length);
        for(int i = 0; i < ydata.length; i++) {
            graph[0][i] = i;
        }
        graphs.add(graph);
    }
    
    public BufferedImage plot() {
        if(graphs.isEmpty()) {
            return null;
        }
        double[][] graph = graphs.get(0);
        BufferedImage image = draw(graph[1], graph[0]);
        int ci = 0;
        for(int i = 1; i < graphs.size(); i++) {
            graph = graphs.get(i);
            ci++;
            if(ci == colours.length) {
                ci = 0;
            }
            image = add(image, graph[1], graph[0], colours[ci]);
        }
        return image;
    }
    
    private BufferedImage draw(double[] ydata, double[] xdata) {
        double ymin = Double.POSITIVE_INFINITY;
        ymax = Double.NEGATIVE_INFINITY;
        for(double y : ydata) {
            if(y < ymin) {
                ymin = y;
            }
            if(y > ymax) {
                ymax = y;
            }
        }
        xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        for(double x : xdata) {
            if(x < xmin) {
                xmin = x;
            }
            if(x > xmax) {
                xmax = x;
            }
        }
        pixelsPerUnit = MyMath.nonNegativeMin(MyMath.round(500/(xmax - xmin)),  MyMath.round(500/(ymax - ymin)));
        if(pixelsPerUnit > 50 || pixelsPerUnit <= 0) {
            pixelsPerUnit = 50;
        }
        xmax = xmin + 500/pixelsPerUnit;
        ymax = ymin + 500/pixelsPerUnit;
        String str;
        BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.black);
        g.drawLine(50, 550, 550, 550);
        g.drawLine(50, 545, 50, 555);
        str = ""+xmin;
        g.drawString(str, 50 - fm.stringWidth(str)/2, 550 + fm.getAscent());
        g.drawLine(50 + MyMath.round(pixelsPerUnit*(xmax - xmin)), 545, 50 + MyMath.round(pixelsPerUnit*(xmax - xmin)), 555);
        str = ""+xmax;
        g.drawString(str, 50 + MyMath.round(pixelsPerUnit*(xmax - xmin)) - fm.stringWidth(str)/2, 550 + fm.getAscent());
        g.drawLine(50 + pixelsPerUnit, 545, 50 + pixelsPerUnit, 545);
        str = ""+(xmin+1);
        g.drawString(str, 50 + pixelsPerUnit - fm.stringWidth(str)/2, 550 + fm.getAscent());
        g.drawLine(50, 50, 50, 550);
        g.drawLine(45, 550, 55, 550);
        str = ""+ymin;
        g.drawString(str, 45 - fm.stringWidth(str), 550 + fm.getAscent()/2);
        g.drawLine(45, 550 - MyMath.round(pixelsPerUnit*(ymax - ymin)), 55, 550 - MyMath.round(pixelsPerUnit*(ymax - ymin)));
        str = ""+ymax;
        g.drawString(str, 45 - fm.stringWidth(str), 550 - MyMath.round(pixelsPerUnit*(ymax - ymin)) + fm.getAscent()/2);
        g.drawLine(45, 550 - pixelsPerUnit, 55, 550 - pixelsPerUnit);
        str = ""+(ymin + 1);
        g.drawString(str, 45 - fm.stringWidth(str), 550 - pixelsPerUnit + fm.getAscent()/2);
        g.setColor(colours[0]);
        int i = 0, j = 0, ip, jp;
        for(int t = 0; t < ydata.length; t++) {
            ip = i;
            jp = j;
            i = 50 + MyMath.round((xdata[t] - xmin)*pixelsPerUnit);
            j = MyMath.round((ymax - ydata[t] + 1)*pixelsPerUnit);
            g.drawOval(i - 2, j - 2, 4, 4);
            g.fillOval(i - 2, j - 2, 4, 4);
            if(t > 0) {
                g.drawLine(ip, jp, i, j);
            }
        }
        g.dispose();
        return image;
    }

    private BufferedImage add(BufferedImage image, double[] ydata, double[] xdata, Color colour) {
        Graphics g = image.getGraphics();
        g.setColor(colour);
        int i = 0, j = 0, ip, jp;
        for(int t = 0; t < ydata.length; t++) {
            ip = i;
            jp = j;
            i = 50 + MyMath.round((xdata[t] - xmin)*pixelsPerUnit);
            j = MyMath.round((ymax - ydata[t] + 1)*pixelsPerUnit);
            g.drawOval(i - 2, j - 2, 4, 4);
            g.fillOval(i - 2, j - 2, 4, 4);
            if(t > 0) {
                g.drawLine(ip, jp, i, j);
            }
        }
        g.dispose();
        return image;
    }
   
    
}
