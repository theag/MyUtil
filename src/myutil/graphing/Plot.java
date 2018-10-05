/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.StringTokenizer;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Plot {
    
    protected static final int LINE = 0;
    protected static final int BAR = 1;
    
    protected static final int NONE = 0;
    protected static final int SOLID = 1;
    
    protected static final int ARRAY = 0;
    protected static final int FUNCTION = 1;
    protected static final int PARAMETRIC = 2;
    
    private final int type;
    private Color colour;
    private int lineStyle;
    private int dotStyle;
    private int plotStyle;
    private final double[][] yValues;
    private final double[] xValues;
    private final String fx;
    private final String gx;
    private final String x;
    
    public Plot(String cmd, String type) {
        cmd = Calculator.whiteSpaceStrip(cmd);
        int index = cmd.indexOf("[");
        if(index == 0) {
            plotStyle = ARRAY;
            int index2 = index;
            int open = 1;
            while(open > 0) {
                index2++;
                if(index2 == cmd.length()) {
                    throw new GraphingException("Invalid Command: " +cmd);
                }
                if(cmd.charAt(index2) == '[') {
                    open++;
                } else if(cmd.charAt(index2) == ']') {
                    open--;
                }
            }
            
            if(index2 == cmd.length()-1) {
                yValues = getDblArray(cmd.substring(index, index2+1));
                xValues = new double[yValues[0].length];
                for(int i = 0; i < xValues.length; i++) {
                    xValues[i] = i;
                }
            } else {
                index2++;
                if(cmd.charAt(index2) != ',') {
                    throw new GraphingException("Invalid Command: " +cmd);
                }
                yValues = getDblArray(cmd.substring(0, index2));
                xValues = getArray(cmd.substring(index2+1));
                if(xValues.length != yValues[0].length) {
                    throw new GraphingException("Invalid Command: " +cmd);
                }
            }
            fx = null;
            gx = null;
            x = null;
        } else if(index > 0) {
            throw new GraphingException("Invalid Command: " +cmd);
        } else {
            StringTokenizer tokens = new StringTokenizer(cmd, ",");
            if(tokens.countTokens() == 2) {
                plotStyle = FUNCTION;
                fx = tokens.nextToken();
                gx = null;
                String str = tokens.nextToken();
                int index1 = str.indexOf("=");
                if(index1 < 0) {
                    throw new GraphingException("Invalid Range: " +str);
                }
                x = str.substring(0, index1);
                xValues = getRange(str);
            } else if(tokens.countTokens() == 3) {
                plotStyle = PARAMETRIC;
                fx = tokens.nextToken();
                gx = tokens.nextToken();
                String str = tokens.nextToken();
                int index1 = str.indexOf("=");
                if(index1 < 0) {
                    throw new GraphingException("Invalid Range: " +str);
                }
                x = str.substring(0, index1);
                xValues = getRange(str);
            } else {
                throw new GraphingException("Invalid Command: " +cmd);
            }
            yValues = null;
        }
        type = type.toLowerCase();
        if(type.compareTo("line") == 0) {
            this.type = LINE;
        } else if(type.compareTo("bar") == 0) {
            this.type = BAR;
            if(plotStyle == PARAMETRIC || plotStyle == FUNCTION) {
                throw new GraphingException("Bar graph must be an array plot");
            }
        } else {
            throw new GraphingException("Invalid Type: " +type);
        }
        colour = null;
        lineStyle = SOLID;
        dotStyle = NONE;
    }

    private double[][] getDblArray(String str) {
        if(str.charAt(0) != '[' || str.charAt(str.length()-1) != ']') {
            throw new GraphingException("Invalid Double Array: " +str);
        }
        int count = 0;
        int index1 = str.indexOf("],[");
        while(index1 >= 0) {
            count++;
            index1 = str.indexOf("],[", index1 + 3);
        }
        double[][] rv = new double[count+1][];
        count = 0;
        index1 = 1;
        int rvSize = -1;
        try {
            int index2 = str.indexOf("],[",index1);
            while(index2 >= 0) {
                rv[count++] = getArray(str.substring(index1, index2+1));
                if(rv[count-1].length != rv[0].length) {
                    throw new GraphingException("Invalid Double Array: " +str);
                }
                index1 = index2+2;
                index2 = str.indexOf("],[", index1);
            }
            rv[count++] = getArray(str.substring(index1,str.length()-1));
        } catch(ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace(System.out);
            System.out.println();
            throw new GraphingException("Invalid Double Array: " +str);
        }
        if(count < rv.length) {
            throw new GraphingException("Invalid Double Array: " +str);
        }
        return rv;
    }

    private double[] getArray(String str) {
        if(str.charAt(0) != '[' || str.charAt(str.length()-1) != ']') {
            throw new GraphingException("Invalid Array: " +str);
        }
        StringTokenizer tokens = new StringTokenizer(str.substring(1, str.length()-1), ",");
        double[] rv = new double[tokens.countTokens()];
        
        for(int i = 0; i < rv.length; i++) {
            String line = tokens.nextToken();
            try {
                rv[i] = Calculator.evaluate(line);
            } catch(GraphingException ex) {
                throw new GraphingException("Invalid Array: " +str);
            }
        }
        
        return rv;
    }

    private double[] getRange(String str) {
        int index2 = str.indexOf("..");
        double[] rv = new double[2];
        try {
            rv[0] = Calculator.evaluate(str.substring(0, index2));
            rv[1] = Calculator.evaluate(str.substring(index2+2));
        } catch(GraphingException ex) {
            throw new GraphingException("Invalid Range: " +str);
        }
        if(rv[1] < rv[0]) {
            throw new GraphingException("Invalid Range: " +str);
        }
        return rv;
    }

    public double getMinX() {
        double min;
        switch(plotStyle) {
            case ARRAY:
                min = Double.POSITIVE_INFINITY;
                for(int i = 0; i < xValues.length; i++) {
                    if(xValues[i] < min) {
                        min = xValues[i];
                    }
                }
                return min;
            case FUNCTION:
                return xValues[0];
            case PARAMETRIC:
                min = Double.POSITIVE_INFINITY;
                Calculator c = new Calculator(fx, x);
                double value;
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value < min) {
                        min = value;
                    }
                }
                return min;
            default:
                return Double.NaN;
        }
    }

    public double getMaxX() {
        double max;
        switch(plotStyle) {
            case ARRAY:
                max = Double.NEGATIVE_INFINITY;
                for(int i = 0; i < xValues.length; i++) {
                    if(xValues[i] > max) {
                        max = xValues[i];
                    }
                }
                return max;
            case FUNCTION:
                return xValues[1];
            case PARAMETRIC:
                max = Double.NEGATIVE_INFINITY;
                Calculator c = new Calculator(fx, x);
                double value;
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value > max) {
                        max = value;
                    }
                }
                return max;
            default:
                return Double.NaN;
        }
    }

    public double getMinY() {
        double min, value;
        Calculator c;
        switch(plotStyle) {
            case ARRAY:
                min = Double.POSITIVE_INFINITY;
                for(int i = 0; i < yValues.length; i++) {
                    for(int j = 0; j < yValues[i].length; j++) {
                        if(yValues[i][j] < min) {
                            min = yValues[i][j];
                        }
                    }
                }
                return min;
            case FUNCTION:
                min = Double.POSITIVE_INFINITY;
                c = new Calculator(fx, x);
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value < min) {
                        min = value;
                    }
                }
                return min;
            case PARAMETRIC:
                min = Double.POSITIVE_INFINITY;
                c = new Calculator(gx, x);
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value < min) {
                        min = value;
                    }
                }
                return min;
            default:
                return Double.NaN;
        }
    }

    public double getMaxY() {
        double max, value;
        Calculator c;
        switch(plotStyle) {
            case ARRAY:
                max = Double.NEGATIVE_INFINITY;
                for(int i = 0; i < yValues.length; i++) {
                    for(int j = 0; j < yValues[i].length; j++) {
                        if(yValues[i][j] > max) {
                            max = yValues[i][j];
                        }
                    }
                }
                return max;
            case FUNCTION:
                max = Double.NEGATIVE_INFINITY;
                c = new Calculator(fx, x);
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value > max) {
                        max = value;
                    }
                }
                return max;
            case PARAMETRIC:
                max = Double.NEGATIVE_INFINITY;
                c = new Calculator(gx, x);
                for(double t = xValues[0]; t <= xValues[1]; t += (xValues[1] - xValues[0])/(PlotImage.MIN_WIDTH/PlotImage.POINT_EVERY_PIXELS)) {
                    value = c.fCalc(t);
                    if(value > max) {
                        max = value;
                    }
                }
                return max;
            default:
                return Double.NaN;
        }
    }

    public Color getColour() {
        return colour;
    }

    public Stroke getStroke() {
        if(type == BAR) {
            return new BasicStroke();
        } else if(type == LINE) {
            switch(lineStyle) {
                case NONE:
                    return null;
                case SOLID:
                    return new BasicStroke();
                default:
                    throw new GraphingException("Invalid line style");
            }
        } else {
            throw new GraphingException("Invalid plot type");
        }
    }

    protected int getType() {
        return type;
    }

    public double[][] getPoints(double dx) {
        double rv[][] = null;
        switch(plotStyle) {
            case ARRAY:
                rv = new double[yValues.length+1][xValues.length];
                System.arraycopy(xValues, 0, rv[0], 0, xValues.length);
                for(int i = 0; i < yValues.length; i++) {
                    System.arraycopy(yValues[i], 0, rv[i+1], 0, yValues[i].length);
                }
                break;
            case FUNCTION:
                Calculator c = new Calculator(fx, x);
                rv = new double[2][MyMath.ceil((xValues[1] - xValues[0])/dx)];
                for(int i = 0; i < rv[0].length; i++) {
                    rv[0][i] = xValues[0] + i*dx;
                    rv[1][i] = c.fCalc(rv[0][i]);
                }
                break;
            case PARAMETRIC:
                Calculator cx = new Calculator(fx, x);
                Calculator cy = new Calculator(gx, x);
                ArrayList<Double> params = new ArrayList<>();
                double t = xValues[0];
                double shift;
                while(t < xValues[1]) {
                    params.add(t);
                    shift = 1;
                    while(Math.abs(cx.fCalc(t) - cx.fCalc(t + dx/shift)) > dx) {
                        shift++;
                    }
                    t += dx/shift;
                }
                rv = new double[2][params.size()];
                for(int i = 0; i < rv[0].length; i++) {
                    rv[0][i] = cx.fCalc(params.get(i));
                    rv[1][i] = cy.fCalc(params.get(i));
                }
                break;
        }
        return rv;
    }

    int getXCount() {
        return xValues.length;
    }

    double[] getXValues() {
        double[] rv = new double[xValues.length];
        System.arraycopy(xValues, 0, rv, 0, xValues.length);
        return rv;
    }
    
}
