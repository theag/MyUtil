/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author nbp184
 */
public class Colour {
    
    public static final Color Beige = new Color(0xF5, 0xF5, 0xDC);
    public static final Color BurlyWood = new Color(0xDE, 0xB8, 0x87);
    public static final Color Chartreuse = new Color(0x7F, 0xFF, 0x00);
    public static final Color Chocolate = new Color(0xD2, 0x69, 0x1E);
    public static final Color CornflowerBlue = new Color(0x64, 0x95, 0xED);
    public static final Color DarkBlue = new Color(0x00, 0x00, 0x8B);
    public static final Color DarkGoldenRod = new Color(0xB8, 0x86, 0x0B);
    public static final Color DarkGray = new Color(0xA9, 0xA9, 0xA9);
    public static final Color DarkGreen = new Color(0x00, 0x64, 0x00);
    public static final Color DarkRed = new Color(0x8B, 0x00, 0x00);
    public static final Color DarkSalmon = new Color(0xE9, 0x96, 0x7A);
    public static final Color DarkSlateBlue = new Color(0x48, 0x3D, 0x8B);
    public static final Color DarkTurquoise = new Color(0x00, 0xCE, 0xD1);
    public static final Color DimGray = new Color(0x69, 0x69, 0x69);
    public static final Color DodgerBlue = new Color(0x1E, 0x90, 0xFF);
    public static final Color FireBrick = new Color(0xB2, 0x22, 0x22);
    public static final Color ForestGreen = new Color(0x22, 0x8B, 0x22);
    public static final Color Gainsboro = new Color(0xDC, 0xDC, 0xDC);
    public static final Color Gold = new Color(0xFF, 0xD7, 0x00);
    public static final Color Gray = new Color(0x80, 0x80, 0x80);
    public static final Color Indigo = new Color(0x4B, 0x00, 0x82);
    public static final Color Khaki = new Color(0xF0, 0xE6, 0x8C);
    public static final Color LightBlue = new Color(0xAD, 0xD8, 0xE6);
    public static final Color LightCyan = new Color(0xE0, 0xFF, 0xFF);
    public static final Color LightGray = new Color(0xD3, 0xD3, 0xD3);
    public static final Color LightGreen = new Color(0x90, 0xEE, 0x90);
    public static final Color LightSeaGreen = new Color(0x20, 0xB2, 0xAA);
    public static final Color LimeGreen = new Color(0x32, 0xCD, 0x32);
    public static final Color MediumTurquoise = new Color(0x48, 0xD1, 0xCC);
    public static final Color MediumSeaGreen = new Color(0x3C, 0xB3, 0x71);
    public static final Color MidnightBlue = new Color(0x19, 0x19, 0x70);
    public static final Color Moccasin = new Color(0xFF, 0xE4, 0xB5);
    public static final Color Orange = new Color(0xFF, 0xA5, 0x00);
    public static final Color Orchid = new Color(0xDA, 0x70, 0xD6);
    public static final Color PaleGreen = new Color(0x98, 0xFB, 0x98);
    public static final Color Purple = new Color(0x80, 0x00, 0x80);
    public static final Color RebeccaPurple = new Color(0x66, 0x33, 0x99);
    public static final Color RoyalBlue = new Color(0x41, 0x69, 0xE1);
    public static final Color SaddleBrown = new Color(0x8B, 0x45, 0x13);
    public static final Color SandyBrown = new Color(0xF4, 0xA4, 0x60);
    public static final Color SeaGreen = new Color(0x2E, 0x8B, 0x57);
    public static final Color SlateGray = new Color(0x70, 0x80, 0x90);
    public static final Color SteelBlue = new Color(0x46, 0x82, 0xB4);
    public static final Color Tomato = new Color(0xFF, 0x63, 0x47);
    public static final Color Violet = new Color(0xEE, 0x82, 0xEE);
    public static final Color Wheat = new Color(0xF5, 0xDE, 0xB3);
    public static final Color WhiteSmoke = new Color(0xF5, 0xF5, 0xF5);
    public static final Color YellowGreen = new Color(0x9A, 0xCD, 0x32);
    
    //MINE
    public static final Color Charcoal = new Color(0x0E, 0x0E, 0x0E);
    public static final Color DarkKhaki = new Color(0xD8, 0xCF, 0x7E);
    public static final Color LightKhaki = new Color(0xF2, 0xE8, 0x98);
    public static final Color DarkWheat = new Color(0xC4, 0xB2, 0x8F);
    public static final Color MidnightCornflowerBlue = new Color(62, 87, 175);
    public static final Color LightCornflowerBlue = new Color(137, 216, 255);
    public static final Color MidnightGray = new Color(0x41, 0x41, 0xFF);
    public static final Color MidnightDimGray = new Color(0x1E, 0x1E, 0xFF);
    public static final Color Iron = new Color(0x34, 0x34, 0x34);
    public static final Color YellowOrange = new Color(0xFF, 0xD2, 0x00);
    public static final Color GrayPurple = new Color(0x90, 0x73, 0xB2);
    
    public static Color[] allColours() {
        Color[] rv = new Color[57];
        rv[0] = Chartreuse;
        rv[1] = Chocolate;
        rv[2] = CornflowerBlue;
        rv[3] = DarkGreen;
        rv[4] = DarkRed;
        rv[5] = DarkSalmon;
        rv[6] = DarkSlateBlue;
        rv[7] = FireBrick;
        rv[8] = ForestGreen;
        rv[9] = Gold;
        rv[10] = Indigo;
        rv[11] = Khaki;
        rv[12] = LightBlue;
        rv[13] = LightSeaGreen;
        rv[14] = LimeGreen;
        rv[15] = MediumTurquoise;
        rv[16] = MediumSeaGreen;
        rv[17] = MidnightBlue;
        rv[18] = Orange;
        rv[19] = Orchid;
        rv[20] = PaleGreen;
        rv[21] = Purple;
        rv[22] = RoyalBlue;
        rv[23] = SaddleBrown;
        rv[24] = SandyBrown;
        rv[25] = SlateGray;
        rv[26] = SteelBlue;
        rv[27] = Tomato;
        rv[28] = Violet;
        rv[29] = Wheat;
        rv[30] = YellowGreen;
        rv[31] = Charcoal;
        rv[32] = DarkKhaki;
        rv[33] = LightKhaki;
        rv[34] = DarkWheat;
        rv[35] = DodgerBlue;
        rv[36] = MidnightCornflowerBlue;
        rv[37] = LightCornflowerBlue;
        rv[38] = DarkGray;
        rv[39] = DimGray;
        rv[40] = Gray;
        rv[41] = LightGray;
        rv[42] = MidnightGray;
        rv[43] = MidnightDimGray;
        rv[44] = Iron;
        rv[45] = DarkGoldenRod;
        rv[46] = LightCyan;
        rv[47] = YellowOrange;
        rv[48] = DarkBlue;
        rv[49] = LightGreen;
        rv[50] = BurlyWood;
        rv[51] = DarkTurquoise;
        rv[52] = GrayPurple;
        rv[53] = Moccasin;
        rv[54] = Beige;
        rv[55] = Gainsboro;
        rv[56] = WhiteSmoke;
        return rv;
    }
    
    public static BufferedImage drawAll() {
        Color[] list = allColours();
        BufferedImage rv = new BufferedImage(100, 50*list.length, BufferedImage.TYPE_INT_RGB);
        Graphics g = rv.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        for(int i = 0; i < list.length; i++) {
            g.setColor(list[i]);
            g.drawRect(0, i*50, 100, 50);
            g.fillRect(0, i*50, 100, 50);
            Rectangle2D bounds = fm.getStringBounds(""+i, g);
            g.setColor(Color.white);
            g.drawRect(10, i*50 + MyMath.round((50 - bounds.getHeight())/2), MyMath.round(bounds.getWidth()), MyMath.round(bounds.getHeight()));
            g.fillRect(10, i*50 + MyMath.round((50 - bounds.getHeight())/2), MyMath.round(bounds.getWidth()), MyMath.round(bounds.getHeight()));
            g.setColor(Color.black);
            g.drawString(""+i, 10, MyMath.round(i*50 + 25 + bounds.getCenterX()));
        }
        g.dispose();
        return rv;
    }
    
    public static BufferedImage drawSelection(int[] indices) {
        Color[] list = allColours();
        BufferedImage rv = new BufferedImage(100, 50*indices.length, BufferedImage.TYPE_INT_RGB);
        Graphics g = rv.getGraphics();
        FontMetrics fm = g.getFontMetrics();
        for(int i = 0; i < indices.length; i++) {
            g.setColor(list[indices[i]]);
            g.drawRect(0, i*50, 100, 50);
            g.fillRect(0, i*50, 100, 50);
            Rectangle2D bounds = fm.getStringBounds(""+indices[i], g);
            g.setColor(Color.white);
            g.drawRect(10, i*50 + MyMath.round((50 - bounds.getHeight())/2), MyMath.round(bounds.getWidth()), MyMath.round(bounds.getHeight()));
            g.fillRect(10, i*50 + MyMath.round((50 - bounds.getHeight())/2), MyMath.round(bounds.getWidth()), MyMath.round(bounds.getHeight()));
            g.setColor(Color.black);
            g.drawString(""+indices[i], 10, MyMath.round(i*50 + 25 + bounds.getCenterX()));
        }
        g.dispose();
        return rv;
    }

    public static Color create(int[] coords) {
        return new Color(coords[0], coords[1], coords[2]);
    }
    
}
