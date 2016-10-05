/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

/**
 *
 * @author shanemendez
 */
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import neuralnet.NeuralNet;

class Creature extends Entity {

    NeuralNet brain;
    ArrayList<Float> genome = new ArrayList();
    Random r = new Random();
    Color color;
    Vector init;
    private double d = 10;

    private final int SUBSET = 100;
    private LinkedList<Double> fitness = new LinkedList();
    Function<Creature, Double> fitfunc = x -> 1d;

    Creature(double x, double y, Collection<Float> gene) {
        super(x, y);
        color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        brain = new NeuralNet(6, new int[]{8, 12}, 4, gene.iterator());
//    System.out.println(gene);
        this.genome.addAll(gene);
        init = pos.copy();
    }

    Creature(Collection<Float> gene) {
        this(0, 0, gene);
    }

    Creature(Vector v, Collection<Float> gene) {
        this(v.x, v.y, gene);
    }



    void update(ArrayList<? extends Entity> near) {
        //pos.set(mouseX,mouseY);
        pos.add(vel);
//    double[] out = brain.feed(new double[]{pos.x, pos.y, x, y,0});
//    vel.add((double)out[0],(double)out[1],0);
        double r = 1;
        if (vel.mag() > r) {
            vel.setMag(r);
        }
        //println(out);

    }

    void update(double x, double y) {
        //pos.set(mouseX,mouseY);
        pos.add(vel);
        double[] out = brain.feed(new double[]{pos.x, pos.y, x, y, r.nextFloat(), 0});
        vel.add((double) out[0], (double) out[1], 0);
        double r = 16;
        if (vel.mag() > r) {
            vel.setMag(r);
        }
        //println(out);

    }

    void reset() {
        pos = init.copy();
        vel.set(0, 0);
    }

    void write(Writer w) {
        for (Float f : genome) {
            try {
                w.write(f.toString() + " ");

            } catch (IOException e) {
            }
        }
    }

    @Override
    void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(pos.x - d / 2, pos.y - d / 2, d, d);
    }
    void setFitFunc(Function<Creature, Double> func) {
        fitness = new LinkedList();
        fitfunc = func;

    }
    double getFitness() {
        if (fitness.size() > SUBSET) {
            fitness.poll();
        }
        fitness.add(fitfunc.apply(this));

//       System.out.println(fitness.getLast());
        return fitness.getLast();
    }

    double getAverageFitness() {
        double sum = 0;
        for (double f : fitness) {
            sum += f;
        }
        return sum / fitness.size();
    }

    double getFitnessStd() {
//       System.out.println(fitness);
        double sum = 0;
        double avg = getAverageFitness();
        for (double d : fitness) {
            sum += Math.pow(d - avg, 2);
        }
        sum /= fitness.size();

        return Math.sqrt(sum);

    }

    double getWeightedFitness() {
        return getAverageFitness() + 1000 * getFitnessStd();
    }

    Creature evolve() {
        ArrayList<Float> g = new ArrayList();
        for (float gene : genome) {
            g.add(gene + (r.nextFloat() * 2 - 1));
        }
        Creature c = new Creature(init.x, init.y, g);
        c.setFitFunc(fitfunc);
        return c;

    }
}
