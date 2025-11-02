package Fauna.AllHerbivores;

import Fauna.Herbs;
import Fauna.Animal;
import SupportClasses.Statistics;

import java.util.List;

public abstract class Herbivores extends Animal {

    public Herbivores(String name, double maxWeight, int maxCountOnCell, int speed, double foodNeed) {
        super(name, maxWeight, maxCountOnCell, speed, foodNeed);
    }

    /**
     * Общая логика травоядных — они едят растения.
     */
    @Override
    public void eat(List<Animal> others) {
        // Если травы нет — животное голодает и теряет часть сытости
        hunger -= 0.1;

        // Но уровень сытости не должен падать ниже нуля
        if (hunger < 0) hunger = 0;
    }

    /**
     * Попытка съесть траву (из отдельного списка растений, если он есть).
     */
    public boolean tryEatPlants(List<Herbs> plants) {
        if (plants != null && !plants.isEmpty()) {
            Herbs herb = plants.remove(0); // съели один пучок
            if (herb != null && herb.isAlive()) {
//                System.out.println(name + " ate some grass 🌿");
                hunger = 1.0;

                // ✅ статистика
                Statistics.markGrassEaten(this);

                return true;
            }
        }
        return false;
    }
}
