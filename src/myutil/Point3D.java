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
public abstract class Point3D {
    
    public int x;
    public int y;
    public int z;
    
    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }
    
    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point3D(Point3D point) {
        x = point.x;
        y = point.y;
        z = point.z;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getZ() {
        return z;
    }
    
    public void move(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void move(Point3D point) {
        x = point.x;
        y = point.y;
        z = point.z;
    }
    
    public void translate(int dx, int dy, int dz) {
        x += dx;
        y += dy;
        z += dz;
    }
    
    public void scale(int s) {
        x *= s;
        y *= s;
        z *= s;
    }
    
    public abstract double distance(Point3D point);
    public abstract double magnitude();
    public abstract <T> T convert(Class<? extends Point3D> T);
    
}
