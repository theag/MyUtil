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
public class Cylindrical3D extends Point3D {

    public Cylindrical3D() {
        super();
        adjust();
    }
    
    public Cylindrical3D(int rho, int theta, int z) {
        super(rho, theta, z);
        adjust();
    }

    public Cylindrical3D(Cylindrical3D point) {
        super(point);
        adjust();
    }
    
    @Override
    public void move(int x, int y, int z) {
        super.move(x, y, z);
        adjust();
    }
    
    @Override
    public void move(myutil.Point3D point) {
        super.move(point);
        adjust();
    }

    @Override
    public double distance(Point3D point) {
        if(point instanceof Cylindrical3D) {
            return Math.sqrt(x*x + point.x*point.x - 2*x*point.x*Math.cos((y - point.y)*Math.PI/180.0) + (z - point.z)*(z - point.z));
        } else {
            Cylindrical3D pt = point.convert(this.getClass());
            return Math.sqrt(x*x + pt.x*pt.x - 2*x*pt.x*Math.cos((y - pt.y)*Math.PI/180.0) + (z - pt.z)*(z - pt.z));
        }
    }

    @Override
    public double magnitude() {
        return Math.sqrt(x*x + z*z);
    }

    @Override
    public <T> T convert(Class<? extends myutil.Point3D> T) {
        if(T.equals(Cylindrical3D.class)) {
            return (T)this;
        } else if(T.equals(Cartesian3D.class)) {
            Cartesian3D pt = new Cartesian3D(MyMath.round(Math.sqrt(x*x + y*y)), MyMath.round(Math.atan2(y, x)*180/Math.PI), z);
            return (T)pt;            
        }else {
            return null;
        }
    }

    @Override
    public void translate(int dx, int dy, int dz) {
        super.translate(dx, dy, dz);
        adjust();
    }

    private void adjust() {
        if(x < 0) {
            x = -x;
            y += 180;
        }
        while(y < 0) {
            y += 360;
        }
        while(y >= 360) {
            y -= 360;
        }
    }
    
}
