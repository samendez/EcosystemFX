/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author shanemendez
 * @param <T>
 * @param <Float>
 */
public class Genome<T extends Number> extends ArrayList<T> {


    public Iterator<T> iterator() {
        return new GeneIter(this);
    }
    final class GeneIter implements Iterator<T> {

        Iterator<T> n;

        GeneIter(List<T> g) {
            if (g.isEmpty()) {
                n = new Generator(g);
            } else {
                n = g.listIterator();
            }
        }

        @Override
        public boolean hasNext() {
            return n.hasNext();
        }

        @Override
        public T next() {
            return n.next();

        }

        private class Generator<T> implements Iterator<Float> {

            List l;
            private Random r;
            public Generator(List l) {
                r = new Random();
                this.l = l;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Float next() {
                Float f = new Float(r.nextFloat() * 2 - 1);
                l.add(f);
                return f;
//                return r;
            }
        }

    }
}
