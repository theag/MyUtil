/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author nbp184
 */
public class PlotImage {
    
    public static final int POINT_EVERY_PIXELS = 5;
    public static final int MIN_WIDTH = 200;
    public static final int MIN_HEIGHT = 200;
    private static final Color[] DEFAULT_COLOURS = {new Color(0x78,0x00,0x0E),new Color(0x00,0x0E,0x78),new Color(0x4A,0x78,0x00),new Color(0x3E,0x57,0x8A),new Color(0x78,0x00,0x72),new Color(0x00,0x78,0x6A),new Color(0x60,0x41,0x91),new Color(0x00,0x4A,0x78),new Color(0x78,0x4C,0x00),new Color(0x91,0x41,0x4A),new Color(0x3E,0x73,0x8A),new Color(0x78,0x00,0x3B),new Color(0x00,0x78,0x3F),new Color(0x91,0x41,0x86),new Color(0x51,0x00,0x78),new Color(0x77,0x78,0x00)};
    
    public Axes axes;
    private int width;
    private int height;
    private ArrayList<Plot> plots;
    private Color[] colours;
    
    public PlotImage(Plot plot) {
        width = MIN_WIDTH;
        height = MIN_HEIGHT;
        plots = new ArrayList<>();
        plots.add(plot);
        axes = new Axes(plot);
        colours = null;
    }

    public PlotImage(int width, int height, Plot plot) {
        if(width >= MIN_WIDTH) {
            this.width = width;
        } else {
            this.width = MIN_WIDTH;
        }
        if(height >= MIN_HEIGHT) {
            this.height = height;
        } else {
            this.height = MIN_HEIGHT;
        }
        plots = new ArrayList<>();
        plots.add(plot);
        axes = new Axes(plot);
        colours = null;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        if(width >= MIN_WIDTH) {
            this.width = width;
        } else {
            this.width = MIN_WIDTH;
        }
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        if(height >= MIN_HEIGHT) {
            this.height = height;
        } else {
            this.height = MIN_HEIGHT;
        }
    }
    
    public Color[] getColours() {
        Color[] rv;
        if(colours == null) {
            rv = new Color[DEFAULT_COLOURS.length];
            System.arraycopy(DEFAULT_COLOURS, 0, rv, 0, DEFAULT_COLOURS.length);
        } else {
            rv = new Color[colours.length];
            System.arraycopy(colours, 0, rv, 0, colours.length);
        }
        return rv;
    }
    
    public void setColours(Color[] colours) {
        if(colours == null) {
            this.colours = null;
        } else {
            this.colours = new Color[colours.length];
            System.arraycopy(colours, 0, this.colours, 0, colours.length);
        }
    }
    
    public BufferedImage draw() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        PointConversion pc = axes.draw(g, width, height);
        g.setClip(pc.getMargin(), pc.getMargin(), pc.getWidth(), pc.getHeight());
        Stroke s;
        int colourInd = 0;
        for(Plot p : plots) {
            if(p.getColour() == null) {
                if(colours == null) {
                    g.setColor(DEFAULT_COLOURS[colourInd++]);
                    if(colourInd == DEFAULT_COLOURS.length) {
                        colourInd = 0;
                    }
                } else {
                    g.setColor(colours[colourInd++]);
                    if(colourInd == colours.length) {
                        colourInd = 0;
                    }
                }
            } else {
                g.setColor(p.getColour());
            }
            s = p.getStroke();
            if(s != null) {
                g.setStroke(s);
            }
            if(p.getType() == Plot.LINE && s != null) {
                drawLinePlot(g, pc, p);
            } else if(p.getType() == Plot.BAR) {
                drawBarPlot(g, pc, p);
            }
        }
        g.dispose();
        return img;
    }

    private void drawLinePlot(Graphics2D g, PointConversion pc, Plot p) {
        double dx = width*pc.getDomainWidth()/POINT_EVERY_PIXELS;
        double points[][] = p.getPoints(dx);
        int i1, j1, i2, j2;
        for(int t = 1; t < points.length; t++) {
            i1 = pc.getI(points[0][0]);
            j1 = pc.getJ(points[t][0]);
            for(int s = 1; s < points[0].length; s++) {
                i2 = pc.getI(points[0][s]);
                j2 = pc.getJ(points[t][s]);
                g.drawLine(i1, j1, i2, j2);
                i1 = i2;
                j1 = j2;
            }
        }
    }

    private void drawBarPlot(Graphics2D g, PointConversion pc, Plot p) {
        double points[][] = p.getPoints(0);
        
    }
    
}
            