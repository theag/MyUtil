/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.awt.image.BufferedImage;

/**
 *
 * @author nbp184
 */
public class CoordinateImage extends BufferedImage {
    
    private int[][] coordArray;
    
    public CoordinateImage(int width, int height, int imageType, int pointCount, int coordinateCount) {
        super(width, height, imageType);
        coordArray = new int[pointCount][coordinateCount];
    }
    
    public void setCoords(int index, int[] coords) {
        if(coords != null) {
            System.arraycopy(coords, 0, coordArray[index], 0, coords.length);
        } else {
            coordArray[index] = null;
        }
    }
    
    public int[] getCoords(int index) {
        if(coordArray[index] != null) {
            int[] rv = new int[coordArray[index].length];
            System.arraycopy(coordArray[index], 0, rv, 0, rv.length);
            return rv;
        } else {
            return null;
        }
    }
    
    public int[] getCoords(int x, int y) {
        return getCoords(getRGB(x, y) & 0xFFFFFF);
    }
    
}
