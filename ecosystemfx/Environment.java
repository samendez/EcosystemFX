/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author shanemendez
 */
public class Environment {

    ArrayList<Creature> creatures = new ArrayList();
    UniformGrid<Creature> grid;
    GraphicsContext gc;
    Vector target = new Vector(400, 250);
    Random rand;
    int champs = 2;
    int population = 1000;
    double p = .5;  //prob that first best creature will reproduce

    Environment(Canvas c, int pop) {
        population = pop;
        rand = new Random();
        gc = c.getGraphicsContext2D();
        grid = new UniformGrid(c);

    }


    void spawn(int i, Vector location) {
        for (; i > 0 && creatures.size() < population; i--) {
            Genome g = new Genome();

            Creature creature = new Creature(location, g);
            creature.setFitFunc((Creature cre) -> Math.abs(target.dist(cre.pos)));

            creatures.add(creature);
//            System.out.println(creature.gene);
        }
        grid.populate(creatures);
    }

    //repopulates based on a list of creatures, evolving each from start to finish until population size is maxed

    void repopulate(ArrayList<Creature> parents) {
        creatures = new ArrayList(creatures.subList(0, champs));
        int numparents = parents.size();
        double pn = p;
        for (int i = 0; i < numparents; i++) {
            int children = (int) (population * pn);
            pn *= 1 - p;
            System.out.println("Children:" + children);
            for (int j = 0; j < children; j++) {
                creatures.add(parents.get(i).evolve());
            }

        }
//        grid.clear();
//        grid.populate(creatures);
    }

    void update(Consumer<Creature> block) {
        Iterator<Creature> iter = creatures.iterator();
        while(iter.hasNext()) {
            block.accept(iter.next());
        }
    }

    void update() {
        Consumer<Creature> block = c -> {c.draw(gc);c.update(target.x,target.y);};
        update(block);
        
    }

    //assumes sort best has been run, picks as if best -> worst 
    ArrayList<Creature> pickBest() {
        creatures.sort((x, y) -> Double.compare(x.getWeightedFitness(), y.getWeightedFitness()));
        ArrayList best = new ArrayList(creatures.subList(0, champs));
        double pn = p * Math.pow(1 - p, champs + 1);
        for (int i = champs; i < creatures.size() && best.size() < 10; i++) {
            if (rand.nextFloat() >= pn) {
                best.add(creatures.get(i));
            }
        }
        return best;
    }

    void randomTarget() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
