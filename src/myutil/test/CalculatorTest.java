/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import myutil.graphing.Calculator;
import myutil.graphing.GraphingException;

/**
 *
 * @author nbp184
 */
public class CalculatorTest {
    
    private static String input = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if(input.isEmpty()) {
            BufferedReader keyin = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            while(true) {
                System.out.print(">");
                line = keyin.readLine();
                if(line.toLowerCase().compareTo("quit") == 0) {
                    break;
                } else {
                    try {
                        System.out.println("\t" +Calculator.evaluate(line) +"\n");
                    } catch(GraphingException ex) {
                        ex.printStackTrace(System.out);
                        System.out.println();
                    }
                }
            }
        } else {
            System.out.print(">" +input +"\n\t" +Calculator.evaluate(input) +"\n");
        }
    }
    
}
