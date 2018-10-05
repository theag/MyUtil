/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.util.NoSuchElementException;

/**
 *
 * @author nbp184
 */
public class MyStringTokenizer {
    
    private String remainder;
    private final String delimiter;
    
    public MyStringTokenizer(String string, String delimiter) {
        remainder = string;
        this.delimiter = delimiter;
    }
    
    public int countTokens() {
        int count = 1;
        int index = remainder.indexOf(delimiter);
        while(index >= 0) {
            count++;
            index = remainder.indexOf(delimiter, index+1);
        }
        return count;
    }
    
    public boolean hasMoreTokens() {
        return remainder != null;
    }
    
    public String nextToken() {
        if(remainder == null) {
            throw new NoSuchElementException();
        }
        int index = remainder.indexOf(delimiter);
        String rv;
        if(index >= 0) {
            rv = remainder.substring(0, index);
            remainder = remainder.substring(index + 1);
        } else {
            rv = remainder;
            remainder = null;
        }
        return rv;
    }
    
}
