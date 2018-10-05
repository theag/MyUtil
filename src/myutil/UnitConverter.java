/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

/**
 *
 * @author nbp184
 */
public class UnitConverter {
    //Imperial units
    public static final int POUND = 0;
    public static final int INCH = 1;
    
    //Metric units
    public static final int KILOGRAM = 10;
    public static final int CENTIMETRE = 11;

    public static double convertMass(int oldUnit, int newUnit, double value) {
        if(!isMassUnit(oldUnit) || !isMassUnit(newUnit)) {
            return Double.NaN;
        }
        if(oldUnit == newUnit) {
            return value;
        } else if(oldUnit == POUND) {
            if(newUnit == KILOGRAM) {
                return value*0.45359237;
            }
        } else if(oldUnit == KILOGRAM) {
            if(newUnit == POUND) {
                return value*2.204622622;
            }
        }
        return Double.NaN;
    }

    public static double convertLength(int oldUnit, int newUnit, double value) {
        if(!isLengthUnit(oldUnit) || !isLengthUnit(newUnit)) {
            return Double.NaN;
        }
        if(oldUnit == newUnit) {
            return value;
        } else if(oldUnit == INCH) {
            if(newUnit == CENTIMETRE) {
                return value*2.54;
            }
        } else if(oldUnit == CENTIMETRE) {
            if(newUnit == INCH) {
                return value*0.393700787;
            }
        }
        return Double.NaN;
    }
    
    private static boolean isMassUnit(int unit) {
        return unit == POUND || unit == KILOGRAM;
    }
    
    private static boolean isLengthUnit(int unit) {
        return unit == INCH || unit == CENTIMETRE;
    }
    
}
