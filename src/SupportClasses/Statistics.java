package SupportClasses;

import Fauna.Animal;
import java.util.HashMap;
import java.util.Map;

public class Statistics {

    public static class StatRecord {
        public int left = 0;       // живых животных
        public int rip = 0;        // умерших животных
        public int reproduced = 0; // потомства
        public int eaten = 0;      // съедено животных (если животное может быть съедено)
        public int ateGrass = 0;   // съедено травы (травоядные)
        public int ateAnimals = 0; // сколько животных съел хищник
    }

    private static final Map<String, StatRecord> stats = new HashMap<>();

    public static void reset() {
        stats.clear();
    }

    public static void registerAnimal(Animal animal) {
        stats.putIfAbsent(animal.getName(), new StatRecord());
        stats.get(animal.getName()).left++;
    }

    public static void markDeath(Animal animal, boolean eaten) {
        StatRecord record = stats.computeIfAbsent(animal.getName(), n -> new StatRecord());
        record.left = Math.max(0, record.left - 1);
        record.rip++;
        if (eaten && canBeEaten(animal.getName())) record.eaten++;
    }

    public static void markReproduce(Animal child) {
        StatRecord record = stats.computeIfAbsent(child.getName(), n -> new StatRecord());
        record.left++;
        record.reproduced++;
    }

    public static void markGrassEaten(Animal animal) {
        if (!isHerbivore(animal.getName())) return;
        StatRecord record = stats.computeIfAbsent(animal.getName(), n -> new StatRecord());
        record.ateGrass++;
    }

    public static void markAnimalEatenByPredator(Animal predator) {
        if (!predatorEatsAnimals(predator.getName())) return;
        StatRecord record = stats.computeIfAbsent(predator.getName(), n -> new StatRecord());
        record.ateAnimals++;
    }

    public static void resetGrassCounter() {
        for (StatRecord record : stats.values()) {
            record.ateGrass = 0;
        }
    }

    public static void print() {
        for (Map.Entry<String, StatRecord> entry : stats.entrySet()) {
            String name = entry.getKey();
            StatRecord s = entry.getValue();

            if (isHerbivore(name)) {
                // травоядные: выводим съеденную траву и eaten
                System.out.printf("%s: left - %d, RIP - %d, reproduced - %d, eaten - %d, ate grass - %d%n",
                        name, s.left, s.rip, s.reproduced, s.eaten, s.ateGrass);
            } else if (predatorEatsAnimals(name) && !canBeEaten(name)) {
                // хищники, которых никто не ест: выводим сколько съели
                System.out.printf("%s: left - %d, RIP - %d, reproduced - %d, ate animals - %d%n",
                        name, s.left, s.rip, s.reproduced, s.ateAnimals);
            } else {
                // хищники, которых могут съесть (например, Fox для Boa)
                System.out.printf("%s: left - %d, RIP - %d, reproduced - %d, eaten - %d, ate animals - %d%n",
                        name, s.left, s.rip, s.reproduced, s.eaten, s.ateAnimals);
            }
        }
    }

    private static boolean isHerbivore(String name) {
        return switch (name) {
            case "Rabbit", "Mouse", "Goat", "Sheep" -> true;
            default -> false;
        };
    }

    private static boolean canBeEaten(String name) {
        return switch (name) {
            case "Rabbit", "Mouse", "Goat", "Sheep", "Fox" -> true;
            default -> false;
        };
    }

    private static boolean predatorEatsAnimals(String name) {
        return switch (name) {
            case "Wolf", "Fox", "Boa" -> true;
            default -> false;
        };
    }
}
