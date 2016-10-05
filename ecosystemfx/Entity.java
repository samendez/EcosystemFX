/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;


/**
 *
 * @author shanemendez
 */
public abstract class Entity {
    Vector pos;
    Vector vel;
    Entity(double x, double y){
        pos = new Vector(x,y);
        vel = new Vector();
        
    }
    abstract void draw(GraphicsContext gc);
    abstract void update(double x,double y);
    abstract void update(ArrayList<? extends Entity> neighbors);

}
