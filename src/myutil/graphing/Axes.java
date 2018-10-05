/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Axes {
    
    public static final int BOX = 0;
    public static final int CROSS = 1;
    public static final int NONE = 2;
    
    public static final int UNSCALED = 0;
    public static final int ONE_TO_ONE = 1;
    
    private int type;
    private int scale;
    private double[] x;
    private double[] y;
    public Color backgroundColour;
    public Color colour;
    public final int tickMarkCount[];
    public String tickMarkLabels[][];
    
    public Axes(Plot plot) {
        type = BOX;
        scale = UNSCALED;
        x = new double[2];
        x[0] = plot.getMinX();
        x[1] = plot.getMaxX();
        y = new double[2];
        y[0] = plot.getMinY();
        y[1] = plot.getMaxY();
        backgroundColour = Color.white;
        colour = Color.black;
        tickMarkCount = new int[2];
        if(plot.getType() == Plot.BAR) {
            double[] xValues = plot.getXValues();
            tickMarkLabels = new String[2][];
            tickMarkLabels[0] = new String[xValues.length];
            for(int i = 0; i < tickMarkLabels.length; i++) {
                tickMarkLabels[0][i] = ""+xValues[i];
            }
            tickMarkLabels[1] = new String[0];
            tickMarkCount[0] = plot.getXCount();
            tickMarkCount[1] = 10;
        } else {
            tickMarkCount[0] = 10;
            tickMarkCount[1] = 10;
            tickMarkLabels = new String[2][0];
        }
    }

    public PointConversion draw(Graphics2D g, int width, int height) {
        g.setBackground(backgroundColour);
        g.clearRect(0, 0, width, height);
        FontMetrics fm = g.getFontMetrics();
        PointConversion pc;
        switch(scale) {
            case UNSCALED:
                pc = new PointConversion(width, height, fm.getHeight(), x[0], x[1], y[0], y[1]);
                break;
            case ONE_TO_ONE:
                pc = new PointConversion(width, height, fm.getHeight(), x[0], x[1], (y[0] + y[1])/2);
                break;
            default:
                throw new GraphingException("Invalid Axis Scale");
        }
        g.setColor(colour);
        switch(type) {
            case BOX:
                g.drawRect(pc.getMargin(), pc.getMargin(), pc.getWidth(), pc.getHeight());
                String labels[];
                if(tickMarkCount[0] > 0) {
                    //x-axis
                    if(tickMarkLabels[0].length == 0) {
                        int decimalPlaces = 0;
                        labels = new String[pc.getWidth()+1];
                        for(int i = 0; i <= pc.getWidth(); i += pc.getWidth()/tickMarkCount[0]) {
                            if(decimalPlaces == 0) {
                                labels[i] = "" +MyMath.round(pc.getX(i));
                            } else {
                                labels[i] = "" +MyMath.round(pc.getX(i), decimalPlaces);
                            }
                            if(i > 0 && labels[i].compareTo(labels[i-pc.getWidth()/tickMarkCount[0]]) == 0) {
                                decimalPlaces++;
                                i = 0;
                            }
                        }
                    } else {
                        labels = tickMarkLabels[0];
                    }
                    boolean printString = true;
                    for(int i = 0; i <= pc.getWidth(); i += pc.getWidth()/tickMarkCount[0]) {
                        g.drawLine(i + pc.getMargin(), height - pc.getMargin() - 5, i + pc.getMargin(), height - pc.getMargin() + 5);
                        if(printString) {
                            g.drawString(labels[i], i + pc.getMargin() - fm.stringWidth(labels[i])/2, height);
                        }
                        printString = !printString;
                    }
                }
                //y-axis
                if(tickMarkCount[1] > 0) {
                    if(tickMarkLabels[1].length == 0) {
                        int decimalPlaces = 0;
                        labels = new String[pc.getHeight()+1];
                        for(int j = 0; j <= pc.getHeight(); j += pc.getHeight()/tickMarkCount[1]) {
                            if(decimalPlaces == 0) {
                                labels[j] = "" +MyMath.round(pc.getY(j));
                            } else {
                                labels[j] = "" +MyMath.round(pc.getY(j), decimalPlaces);
                            }
                            if(j > 0 && labels[j].compareTo(labels[j-pc.getHeight()/tickMarkCount[1]]) == 0) {
                                decimalPlaces++;
                                j= 0;
                            }
                        }
                    } else {
                        labels = tickMarkLabels[1];
                    }
                    boolean printString = true;
                    for(int j = 0; j <= pc.getHeight(); j += pc.getHeight()/tickMarkCount[1]) {
                        g.drawLine(pc.getMargin()-5, j + pc.getMargin(), pc.getMargin()+5, j + pc.getMargin());
                        if(printString) {
                            g.transform(AffineTransform.getQuadrantRotateInstance(3, pc.getMargin(), j + pc.getMargin()));
                            g.drawString(labels[j], pc.getMargin() - fm.stringWidth(labels[j])/2, j + pc.getMargin() - 6);
                            g.setTransform(AffineTransform.getTranslateInstance(0, 0));
                        }
                        printString = !printString;
                    }
                }
                break;
            case CROSS:
                
                break;
            case NONE:
                
                break;
            default:
                throw new GraphingException("Invalid Axis Type");
        }
        return pc;
    }
    
}
