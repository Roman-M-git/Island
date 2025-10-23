package Fauna;

import Fauna.AllPredators.Fox;
import Fauna.AllPredators.Wolf;
import Fauna.AllHerbivores.Rabbit;
import MapEngine.Island;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // Создаём остров 10x5 клеток
        Island island = new Island(10, 5);
        Random random = new Random();

        // Добавляем траву
        int grassCount = 50; // количество пучков травы
        for (int i = 0; i < grassCount; i++) {
            Herbs herbs = new Herbs();
            int x = random.nextInt(10);
            int y = random.nextInt(5);
            island.addPlant(herbs, x, y);
        }
        System.out.println("\uD83C\uDF3F Added " + grassCount + " grass patches to the map.");

        // Добавляем 2 волков
        int wolfCount = 2;
        for (int i = 0; i < wolfCount; i++) {
            Wolf wolf = new Wolf();
            int x = random.nextInt(10);
            int y = random.nextInt(5);
            island.addAnimal(wolf, x, y);
        }
        System.out.println("\uD83D\uDC3A Added " + wolfCount + " wolfs to the map.");

        // Добавляем 2 кроликов
        int rabbitCount = 2;
        for (int i = 0; i < rabbitCount; i++) {
            Rabbit rabbit = new Rabbit();
            int x = random.nextInt(10);
            int y = random.nextInt(5);
            island.addAnimal(rabbit, x, y);
        }
        System.out.println("\uD83D\uDC07 Added " + rabbitCount + " rabbits to the map.");

        // Добавляем 2 лис
        int foxCount = 2;
        for (int i = 0; i < foxCount; i++) {
            Fox fox = new Fox();
            int x = random.nextInt(10);
            int y = random.nextInt(5);
            island.addAnimal(fox, x, y);
        }
        System.out.println("\uD83E\uDD8A Added " + foxCount + " fox to the map.");


        // Симуляция 10 шагов
        for (int i = 1; i <= 10; i++) {
            System.out.println("\n===== Step " + i + " =====");
            island.simulateStep();

        }
    }
}
