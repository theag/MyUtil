/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *
 * @author nbp184
 */
public class Preferences {
    
    private static HashMap<String,Object> preferences;
    
    public static void load(String name) {
        preferences = new HashMap<>();
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(name +".ini"));
            String line = inFile.readLine();
            int index1, index2, index3;
            while(line != null) {
                index1 = line.indexOf(":");
                index2 = line.indexOf("=");
                if(index2 >= 0) {
                    if(index1 < 0 || index1 > index2) {
                        preferences.put(line.substring(0, index2), line.substring(index2+1));
                    } else if(index1 >= 0 && index1 > index2) {
                        System.out.println("Incorrect formatting for line: " +line);
                    } else {
                        try {
                            switch(line.substring(index1+1, index2)) {
                                case "Integer":
                                    preferences.put(line.substring(0, index1), Integer.parseInt(line.substring(index2+1)));
                                    break;
                                case "Double":
                                    preferences.put(line.substring(0, index1), Double.parseDouble(line.substring(index2+1)));
                                    break;
                                case "Float":
                                    preferences.put(line.substring(0, index1), Float.parseFloat(line.substring(index2+1)));
                                    break;
                                case "Short":
                                    preferences.put(line.substring(0, index1), Short.parseShort(line.substring(index2+1)));
                                    break;
                                case "Boolean":
                                    preferences.put(line.substring(0, index1), Boolean.parseBoolean(line.substring(index2+1)));
                                    break;
                                case "String":
                                    preferences.put(line.substring(0, index1), line.substring(index2+1));
                                    break;
                                case "Long":
                                    preferences.put(line.substring(0, index1), Long.parseLong(line.substring(index2+1)));
                                    break;
                                case "Byte":
                                    preferences.put(line.substring(0, index1), Byte.parseByte(line.substring(index2+1)));
                                    break;
                                case "Point":
                                    index3 = line.indexOf(" ", index2);
                                    preferences.put(line.substring(0, index1), new Point(Integer.parseInt(line.substring(index2+1, index3)), Integer.parseInt(line.substring(index3+1))));
                                    break;

                            }
                        } catch(NumberFormatException ex) {
                            ex.printStackTrace(System.out);
                        }
                    }
                } else {
                    System.out.println("Incorrect formatting for line: " +line);
                }
                line = inFile.readLine();
            }
            inFile.close();
        } catch(IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void save(String name) {
        try {
            PrintWriter outFile = new PrintWriter(new File(name +".ini"));
            Object value;
            for(String key : preferences.keySet()) {
                value = preferences.get(key);
                if(value instanceof String) {
                    outFile.println(key +"=" +value);
                } else if(value instanceof Point) {
                    Point p = (Point)value;
                    outFile.println(key +":Point=" +p.x +" " +p.y);
                } else {
                    outFile.println(key +":" +value.getClass().getSimpleName() +"=" +value);
                }
            }
            outFile.close();
        } catch(IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static Object getPreference(String key) {
        return preferences.get(key);
    }
    
    public static void setPreference(String key, Object value) {
        if(preferences.containsKey(key)) {
            preferences.replace(key, value);
        } else {
            preferences.put(key, value);
        }
    }

    public static Integer getIntegerPreference(String key) {
        Object value = preferences.get(key);
        if(value instanceof Integer) {
            return (Integer)value;
        } else {
            return null;
        }
    }

    public static Integer getIntegerPreference(String key, Integer defaultValue) {
        Object value = preferences.get(key);
        if(value instanceof Integer) {
            return (Integer)value;
        } else {
            return defaultValue;
        }
    }

    public static boolean hasPreference(String key) {
        return preferences.containsKey(key);
    }

    public static String getStringPreference(String key) {
        Object value = preferences.get(key);
        if(value instanceof String) {
            return (String)value;
        } else {
            return null;
        }
    }
    
    public static String getStringPreference(String key, String defaultValue) {
        Object value = preferences.get(key);
        if(value instanceof String) {
            return (String)value;
        } else {
            return defaultValue;
        }
    }

    public static Boolean getBooleanPreference(String key, boolean defaultValue) {
        Object value = preferences.get(key);
        if(value instanceof Boolean) {
            return (Boolean)value;
        } else {
            return defaultValue;
        }
    }

    public static Point getPointPreference(String key) {
        Object value = preferences.get(key);
        if(value instanceof Point) {
            return (Point)value;
        } else {
            return null;
        }
    }
    
}
