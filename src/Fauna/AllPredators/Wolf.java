package Fauna.AllPredators;

import Fauna.AllHerbivores.Rabbit;
import Fauna.Animal;


public class Wolf extends Predators {

    public Wolf(){
        super("Wolf", 50, 30, 3, 8);
        this.maxAge=30;
    }
    @Override
    protected double getEatChance(Animal prey) {
        if (prey instanceof Rabbit) return 0.6;

        return 0.0;
    }

    @Override
    protected Animal createChild() {
        return new Wolf();
    }
}
