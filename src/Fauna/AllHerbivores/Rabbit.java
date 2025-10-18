package Fauna.AllHerbivores;

import Fauna.Animal;


public class Rabbit extends Herbivores {

    public Rabbit(){
        super("Rabbit", 2, 150, 2, 0.45);
        this.maxAge=10;
    }

    @Override
    protected Animal createChild() {
        return new Rabbit();
    }
}
