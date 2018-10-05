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
public class Cartesian3D extends Point3D {

    public Cartesian3D(int x, int y, int z) {
        super(x, y, z);
    }

    public Cartesian3D() {
        super();
    }
    
    public Cartesian3D(Point3D point) {
        super(point);
    }

    @Override
    public double distance(Point3D point) {
        if(point instanceof Point3D) {
            return Math.sqrt((x - point.x)*(x - point.x) + (y - point.y)*(y - point.y) + (z - point.z)*(z - point.z));
        } else {
            Cartesian3D pt = point.convert(this.getClass());
            return Math.sqrt((x - pt.x)*(x - pt.x) + (y - pt.y)*(y - pt.y) + (z - pt.z)*(z - pt.z));
        }
    }

    @Override
    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public <T> T convert(Class<? extends myutil.Point3D> T) {
        if(T.equals(Cylindrical3D.class)) {
            Cylindrical3D pt = new Cylindrical3D(MyMath.round(x*Math.cos(y*Math.PI/180)), MyMath.round(x*Math.sin(y*Math.PI/180)), z);
            return (T)pt;
        } else if(T.equals(Cartesian3D.class)) {
            return (T)this;
        }else {
            return null;
        }
    }
    
}
