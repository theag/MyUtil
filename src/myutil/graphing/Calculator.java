/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class Calculator {
    
    public static double evaluate(String expression) {
        return (new Calculator(whiteSpaceStrip(expression))).getResult();                
    }
    
    public static String whiteSpaceStrip(String str) {
        String rv = "";
        int index1 = 0, index2 = str.indexOf(" ", index1);
        while(index2 >= 0) {
            rv += str.substring(index1, index2);
            index1 = index2 + 1;
            index2 = str.indexOf(" ", index1);
        }
        rv += str.substring(index1);
        return rv;
    }
    
    private static final int zero = (int)'0';
    protected static final String[] functions = {"sin", "cos", "tan", "ln", "log", "arcsin", "arccos", "arctan", "sqrt", "root"};
    protected static final String[] constants = {"e", "pi"};
    private static final double[] constant_values = {Math.E, Math.PI};
    protected static final String sn = "" +((char)15);
    
    private double[] values;
    private int vInd;
    private double result;
    private String[] function;

    private Calculator(String expression) {
        function = null;
        expression = expression.replaceAll("E", sn).toLowerCase();
        values = new double[expression.length()];
        vInd = -1;
        result = calc(expression);
    }

    public Calculator(String expression, String x) {
        function = new String[2];
        function[0] = whiteSpaceStrip(expression.replaceAll("E", sn).toLowerCase());
        function[1] = x;
        values = new double[expression.length() + 1];
        vInd = -1;
    }
    
    private double getResult() {
        return result;
    }
    
    public double fCalc(double xValue) {
        vInd = -1;
        return calc(functions[0].replaceAll(function[1], ""+xValue));
    }

    private double calc(String expression) {
        int ind, ind1, ind2;
        
        try {
            return myParseDouble(expression);
        } catch(NumberFormatException ex) {
            
        }
        
        //constants
        for(int i = 0; i < constants.length; i++) {
            if(expression.compareTo(constants[i]) == 0) {
                return constant_values[i];
            }
            ind = expression.indexOf(constants[i]);
            if(ind >= 0) {
                values[++vInd] = constant_values[i];
                expression = expression.replaceAll(constants[i], getVariable(vInd));
            }
        }
        
        //functions
        for(int i = 0; i < functions.length; i++) {
            ind1 = expression.indexOf(functions[i]);
            while(ind1 >= 0) {
                ind2 = doFunction(i, expression, ind1);
                expression = expression.substring(0, ind1) +getVariable(vInd) +expression.substring(ind2 + 1);
                ind1 = expression.indexOf(functions[i]);
            }
        }
        
        //brackets
        ind1 = expression.indexOf("(");
        while(ind1 >= 0) {
            ind2 = doBracket(expression, ind1);
            expression = expression.substring(0, ind1) +getVariable(vInd) +expression.substring(ind2 + 1);
            ind1 = expression.indexOf("(");
        }
        
        //powers
        ind = expression.indexOf("^");
        while(ind >= 0) {
            ind1 = ind;
            while(ind1 > 0 && expression.charAt(ind1-1) != '^' && expression.charAt(ind1-1) != '*' && expression.charAt(ind1-1) != '/' && expression.charAt(ind1-1) != '+' && expression.charAt(ind1-1) != '-') {
                ind1--;
            }
            ind2 = ind;
            while(ind2 < expression.length()-1 && expression.charAt(ind2+1) != '^' && expression.charAt(ind2+1) != '*' && expression.charAt(ind2+1) != '/' && expression.charAt(ind2+1) != '+' && expression.charAt(ind2+1) != '-') {
                ind2++;
            }
            doPower(expression, ind, ind1, ind2);
            expression = expression.substring(0, ind1) +getVariable(vInd) +expression.substring(ind2 + 1);
            ind = expression.indexOf("^");
        }
        
        //multiplication & division
        ind = MyMath.nonNegativeMin(expression.indexOf("*"), expression.indexOf("/"));
        while(ind >= 0) {
            ind1 = ind;
            while(ind1 > 0 && expression.charAt(ind1-1) != '*' && expression.charAt(ind1-1) != '/' && expression.charAt(ind1-1) != '+' && expression.charAt(ind1-1) != '-') {
                ind1--;
            }
            ind2 = ind;
            while(ind2 < expression.length()-1 && expression.charAt(ind2+1) != '*' && expression.charAt(ind2+1) != '/' && expression.charAt(ind2+1) != '+' && expression.charAt(ind2+1) != '-') {
                ind2++;
            }
            if(expression.charAt(ind) == '*') {
                doMultiply(expression, ind, ind1, ind2);
            } else {
                doDivide(expression, ind, ind1, ind2);
            }
            expression = expression.substring(0, ind1) +getVariable(vInd) +expression.substring(ind2 + 1);
            ind = MyMath.nonNegativeMin(expression.indexOf("*"), expression.indexOf("/"));
        }
        
        //addition & subtraction
        ind = MyMath.nonNegativeMin(expression.indexOf("+"), expression.indexOf("-"));
        while(ind >= 0) {
            ind1 = ind;
            while(ind1 > 0 && expression.charAt(ind1-1) != '+' && expression.charAt(ind1-1) != '-') {
                ind1--;
            }
            ind2 = ind;
            while(ind2 < expression.length()-1 && expression.charAt(ind2+1) != '+' && expression.charAt(ind2+1) != '-') {
                ind2++;
            }
            if(expression.charAt(ind) == '+') {
                doAddition(expression, ind, ind1, ind2);
            } else {
                doSubtraction(expression, ind, ind1, ind2);
            }
            expression = expression.substring(0, ind1) +getVariable(vInd) +expression.substring(ind2 + 1);
            ind = MyMath.nonNegativeMin(expression.indexOf("+"), expression.indexOf("-"));
        }
        return values[vInd];
    }
    
    private String getVariable(int ind) {
        String sind = "" +ind;
        String rv = "";
        for(int i = 0; i < sind.length(); i++) {
            rv += ((char)(sind.charAt(i) - zero));
        }
        return rv;
    }
    
    private int getIndex(String variable) {
        String ind = "";
        for(int i = 0; i < variable.length(); i++) {
            ind += ((char)(variable.charAt(i) + zero));
        }
        return Integer.parseInt(ind);
    }
    
    protected static double myParseDouble(String str) {
       return Double.parseDouble(str.replaceAll(sn, "E"));
    }

    private int doFunction(int i, String expression, int ind) {
        int ind1 = ind + functions[i].length();
        if(expression.charAt(ind1) != '(') {
            throw new GraphingException("Invalid Command: " +expression +"\n\t\"(\" must immediatly follow \"" +functions[i] +"\".");
        }
        int open = 1;
        int ind2 = ind1;
        while(open > 0) {
            ind2++;
            if(ind2 == expression.length()) {
                throw new GraphingException("Invalid Command: " +expression +"\n\tBracket opened at " +ind1 +" is not closed.");
            }
            if(expression.charAt(ind2) == '(') {
                open++;
            } else if(expression.charAt(ind2) == ')') {
                open--;
            }
        }
        double result;
        int n = 0;
        if(i < 9) {
            result = calc(expression.substring(ind1+1, ind2));
        } else {
            int c = expression.indexOf(",", ind1);
            result = calc(expression.substring(ind1+1, c));
            try {
                n = Integer.parseInt(expression.substring(c+1, ind2));
            } catch(NumberFormatException ex) {
                throw new GraphingException("Invalid Command: " +expression +"\n\tRoot must be of the form \"root(x, n)\".");
            }
        }
        //{"sin", "cos", "tan", "ln", "log", "arcsin", "arccos", "arctan", "sqrt", "root"}
        switch(i) {
            case 0:
                result = Math.sin(result);
                break;
            case 1:
                result = Math.cos(result);
                break;
            case 2:
                result = Math.tan(result);
                break;
            case 3:
                result = Math.log(result);
                break;
            case 4:
                result = Math.log10(result);
                break;
            case 5:
                result = Math.asin(result);
                break;
            case 6:
                result = Math.acos(result);
                break;
            case 7:
                result = Math.atan(result);
                break;
            case 8:
                result = Math.sqrt(result);
                break;
            case 9:
                result = Math.pow(result, 1.0/n);
                break;
        }
        values[++vInd] = result;
        return ind2;
    }

    private int doBracket(String expression, int ind1) {
        int open = 1;
        int ind2 = ind1;
        while(open > 0) {
            ind2++;
            if(ind2 == expression.length()) {
                throw new GraphingException("Invalid Command: " +expression +"\n\tBracket opened at " +ind1 +" is not closed.");
            }
            if(expression.charAt(ind2) == '(') {
                open++;
            } else if(expression.charAt(ind2) == ')') {
                open--;
            }
        }
        double result = calc(expression.substring(ind1+1, ind2));
        values[++vInd] = result;
        return ind2;
    }

    private void doPower(String expression, int ind, int ind1, int ind2) {
        double a;
        try {
            a = myParseDouble(expression.substring(ind1, ind));
        } catch(NumberFormatException ex) {
            a = values[getIndex(expression.substring(ind1, ind))];
        }
        double b;
        try {
            b = myParseDouble(expression.substring(ind+1, ind2+1));
        } catch(NumberFormatException ex) {
            b = values[getIndex(expression.substring(ind+1, ind2+1))];
        }
        values[++vInd] = Math.pow(a, b);
    }

    private void doMultiply(String expression, int ind, int ind1, int ind2) {
        double a;
        try {
            a = myParseDouble(expression.substring(ind1, ind));
        } catch(NumberFormatException ex) {
            a = values[getIndex(expression.substring(ind1, ind))];
        }
        double b;
        try {
            b = myParseDouble(expression.substring(ind+1, ind2+1));
        } catch(NumberFormatException ex) {
            b = values[getIndex(expression.substring(ind+1, ind2+1))];
        }
        values[++vInd] = a*b;
    }

    private void doDivide(String expression, int ind, int ind1, int ind2) {
        double a;
        try {
            a = myParseDouble(expression.substring(ind1, ind));
        } catch(NumberFormatException ex) {
            a = values[getIndex(expression.substring(ind1, ind))];
        }
        double b;
        try {
            b = myParseDouble(expression.substring(ind+1, ind2+1));
        } catch(NumberFormatException ex) {
            b = values[getIndex(expression.substring(ind+1, ind2+1))];
        }
        values[++vInd] = a/b;
    }

    private void doAddition(String expression, int ind, int ind1, int ind2) {
        double a;
        try {
            a = myParseDouble(expression.substring(ind1, ind));
        } catch(NumberFormatException ex) {
            a = values[getIndex(expression.substring(ind1, ind))];
        }
        double b;
        try {
            b = myParseDouble(expression.substring(ind+1, ind2+1));
        } catch(NumberFormatException ex) {
            b = values[getIndex(expression.substring(ind+1, ind2+1))];
        }
        values[++vInd] = a+b;
    }

    private void doSubtraction(String expression, int ind, int ind1, int ind2) {
        double a;
        try {
            a = myParseDouble(expression.substring(ind1, ind));
        } catch(NumberFormatException ex) {
            a = values[getIndex(expression.substring(ind1, ind))];
        }
        double b;
        try {
            b = myParseDouble(expression.substring(ind+1, ind2+1));
        } catch(NumberFormatException ex) {
            b = values[getIndex(expression.substring(ind+1, ind2+1))];
        }
        values[++vInd] = a-b;
    }
    
}
