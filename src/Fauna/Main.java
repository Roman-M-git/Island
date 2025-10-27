package Fauna;

import Fauna.AllHerbivores.*;
import Fauna.AllPredators.*;
import MapEngine.Island;
import SupportClasses.Statistics;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Создаём остров 100x20 клеток
        Island island = new Island(100, 20);
        Random random = new Random();

        // Сбрасываем старую статистику перед стартом симуляции
        Statistics.reset();

        // Добавляем траву
        addHerbs(island, 200, random);

        // Добавляем животных
        addAnimals(island, 30, Wolf.class, random, "🐺");
        addAnimals(island, 150, Rabbit.class, random, "🐇");
        addAnimals(island, 30, Fox.class, random, "🦊");
        addAnimals(island, 30, Boa.class, random, "🐍");
        addAnimals(island, 5, Bear.class, random, "🐻");
        addAnimals(island, 20, Eagle.class, random, "🦅");
        addAnimals(island, 20, Horse.class, random, "🐎");
        addAnimals(island, 20, Deer.class, random, "🦌");
        addAnimals(island, 100, Mouse.class, random, "🐁");
        addAnimals(island, 140, Goat.class, random, "🐐");
        addAnimals(island, 140, Sheep.class, random, "🐑");
        addAnimals(island, 50, Boar.class, random, "🐗");
        addAnimals(island, 50, Buffalo.class, random, "🐃");
        addAnimals(island, 50, Duck.class, random, "🦆");
        addAnimals(island, 50, Caterpillar.class, random, "🐛");

        // Симуляция 10 шагов
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n===== Step " + i + " =====");

            // 🟢 Сбрасываем только счётчик съеденной травы перед новым шагом
            Statistics.resetGrassCounter();

            island.simulateStep();

            // Вывод статистики после каждого шага
            Statistics.print();
            island.printMap();
            System.out.println("💀 " + Statistics.getDeathsThisStep() + " animals died this step.");
        }

        // После всех шагов
        System.out.println("\n===== FINAL SUMMARY =====");
        Statistics.print();
        System.out.println("=========================");
    }

    // Метод для добавления травы
    private static void addHerbs(Island island, int count, Random random) {
        for (int i = 0; i < count; i++) {
            Herbs herbs = new Herbs();
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());
            island.addPlant(herbs, x, y);
        }
        System.out.println("🌿 Added " + count + " grass patches to the map.");
    }

    // Метод для добавления животных
    private static void addAnimals(Island island, int count, Class<? extends Animal> animalClass, Random random, String emoji) {
        for (int i = 0; i < count; i++) {
            try {
                Animal animal = animalClass.getDeclaredConstructor().newInstance();
                int x = random.nextInt(island.getWidth());
                int y = random.nextInt(island.getHeight());
                island.addAnimal(animal, x, y);
                Statistics.registerAnimal(animal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(emoji + " Added " + count + " " + animalClass.getSimpleName() + "(s) to the map.");
    }

}
