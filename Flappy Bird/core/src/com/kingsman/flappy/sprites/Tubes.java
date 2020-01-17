package com.kingsman.flappy.sprites;

import com.badlogic.gdx.utils.Queue;

public class Tubes {
    private Queue<Tube> tubes;

    public Tubes() {
        tubes = new Queue<Tube>();
        for (int i = 1; i <= 4; i++) {
            tubes.addLast(new Tube(i * (140 + Tube.TUBE_WIDTH)));
        }
    }

    public Queue<Tube> getTubes() {
        return tubes;
    }
    public Tube first() {
        return tubes.first();
    }
    public Tube last() {
        return tubes.last();
    }
    public Tube removeFirst() {
        return tubes.removeFirst();
    }
    public void addLast(Tube t) {
        tubes.addLast(t);
    }

    public void dispose() {
        for (Tube t : tubes) {
            t.dispose();
        }
        System.out.println("Tubes Disposed");
    }

}
