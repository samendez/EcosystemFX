package ecosystemfx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shanemendez
 */
public class Vector {
    double x,y,z;
    Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector(double x,double y){
        this(x,y,0);
    }
    Vector(){
        this(0,0,0);
    }
    public void add(Vector v){
        this.x += v.x;
        this.y += v.y;
    }
    public void add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public void add(double x, double y){
        this.add(x,y,0);
    }
    public double mag(){
        return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2));
    }
    public void normalize(){
        double m = this.mag();
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }
    public void setMag(double m){
        this.normalize();
        this.x *= m;
        this.y *= m;
        this.z *= m;
    }
    public double dist(Vector fin){
        return Math.pow(fin.x-this.x, 2) + Math.pow(fin.y-this.y, 2);
    }
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(double x, double y){
        set(x,y,0);
    }
    public Vector copy(){
        return new Vector(x,y,z);
    }
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
