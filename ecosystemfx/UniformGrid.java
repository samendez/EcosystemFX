/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author shanemendez
 */
public class UniformGrid<T extends Entity> implements Iterable<ArrayList<T>> {

    private ArrayList<ArrayList<T>> grid = new ArrayList();
    
    int resolution = 20;
    private int width;
    UniformGrid(Canvas c) {
        int cells = (int) c.getWidth() / resolution;
        width = cells;
        cells *= (int) c.getHeight() / resolution;
        for (int i = 0; i < cells; i++) {
            grid.add(new ArrayList());
        }
        System.out.println("Grid contains " + width + " columns");
        System.out.println(grid.size()/width + " rows");
    }
    void clear(){
        for (ArrayList<T> cell: grid) {
            cell.clear();
        }
    }
    void populate(ArrayList<T> p) {
        for (T t : p) {
            grid.get(this.resolve(t.pos.x,t.pos.y)).add(t);
        }
    }

    ArrayList<T> get(float x, float y) {
        return grid.get(this.resolve(x,y));
    }
    
    int resolve(double x, double y) {
        int xres = (int)(x/resolution);
        return (int) (y / resolution)*width + xres;
    }

    @Override
    public Iterator<ArrayList<T>> iterator() {
        return grid.iterator();

    }



}