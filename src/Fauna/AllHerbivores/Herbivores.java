package Fauna.AllHerbivores;

import Fauna.Animal;

public abstract class Herbivores extends Animal {

    public Herbivores(String name, double maxWeight, int maxCountOnCell, int speed, double foodNeed) {
        super(name, maxWeight, maxCountOnCell, speed, foodNeed);
    }

    /**
     * Общая логика травоядных — они едят растения.
     */
    @Override
    public void eat(List<Animal> others) {
        // для простоты — всегда успешно "поели траву"
        System.out.println(name + " поел траву");
        hunger = 1.0;
    }
}