package Fauna.AllPredators;

import Fauna.AllHerbivores.Herbivores;
import Fauna.Animal;

public abstract class Predators extends Animal {

    public Predators(String name, double maxWeight, int maxCountOnCell, int speed, double foodNeed) {
        super(name, maxWeight, maxCountOnCell, speed, foodNeed);
    }

    /**
     * Общая логика для хищников: ищет травоядных и ест.
     * Конкретные виды могут переопределять этот метод.
     */
    @Override
    public void eat(List<Animal> others) {
        for (Animal target : others) {
            if (target instanceof Herbivores && target.alive) {
                double chance = getEatChance(target);
                if (random.nextDouble() < chance) {
                    System.out.println(name + " съел " + target.name);
                    hunger = 1.0;
                    target.alive = false;
                    return;
                }
            }
        }
        hunger -= 0.2; // если никого не съел — становится голоднее
    }

    /**
     * Вероятность съесть конкретного травоядного.
     * Каждый потомок-хищник должен переопределить под свою таблицу.
     */
    protected abstract double getEatChance(Animal prey);
}
