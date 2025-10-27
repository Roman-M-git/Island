package Fauna;

import Fauna.AllHerbivores.*;
import Fauna.AllPredators.*;
import MapEngine.Island;
import SupportClasses.Statistics;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Island island = new Island(100, 20);
        Statistics.reset();

        //  Используем универсальный метод для добавления животных:
        addAnimals(island, Wolf.class, 30, "\uD83D\uDC3A");
        addAnimals(island, Fox.class, 30, "\uD83E\uDD8A");
        addAnimals(island, Boa.class, 30, "\uD83D\uDC0D");
        addAnimals(island, Bear.class, 5, "\uD83D\uDC3B");
        addAnimals(island, Eagle.class, 20, "\uD83E\uDD85");
        addAnimals(island, Rabbit.class, 150, "\uD83D\uDC07");
        addAnimals(island, Horse.class, 20, "\uD83D\uDC0E");
        addAnimals(island, Deer.class, 20, "\uD83E\uDD8C");
        addAnimals(island, Mouse.class, 100, "\uD83D\uDC01");
        addAnimals(island, Goat.class, 140, "\uD83D\uDC10");
        addAnimals(island, Sheep.class, 140, "\uD83D\uDC11");
        addAnimals(island, Boar.class, 50, "\uD83D\uDC17");
        addAnimals(island, Buffalo.class, 50, "\uD83D\uDC03");
        addAnimals(island, Duck.class, 50, "\uD83E\uDD86");
        addAnimals(island, Caterpillar.class, 50, "\uD83D\uDC1B");

        // Симуляция 10 шагов
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n===== Step " + i + " =====");
            Statistics.resetGrassCounter();
            island.simulateStep();
            Statistics.print();
            island.printMap();
            System.out.println("💀 " + Statistics.getDeathsThisStep() + " animals died this step.");
        }

        System.out.println("\n===== FINAL SUMMARY =====");
        Statistics.print();
        System.out.println("=========================");
    }

    // универсальный метод чтобы не писать для каждого животного подобный код:
//    Добавляем Caterpillar
//    int caterpillarCount = 50;
//        for (int i = 0; i < caterpillarCount; i++) {
//        Caterpillar caterpillar = new Caterpillar();
//        int x = random.nextInt(island.getWidth());
//        int y = random.nextInt(island.getHeight());
//        island.addAnimal(caterpillar, x, y);
//        Statistics.registerAnimal(caterpillar); //  учитываем в статистике
//}
//        System.out.println("\uD83D\uDC1B Added " + caterpillarCount + " caterpillar to the map.");

private static <T extends Animal> void addAnimals(
        Island island, Class<T> clazz, int count, String emoji) {
    Random random = new Random();
    for (int i = 0; i < count; i++) {
        try {
            T animal = clazz.getDeclaredConstructor().newInstance();
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());
            island.addAnimal(animal, x, y);
            Statistics.registerAnimal(animal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    System.out.printf("%s Added %d %s to the map.%n",
            emoji, count, clazz.getSimpleName().toLowerCase() + "s");
}
}
