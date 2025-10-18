package Fauna;

import Fauna.AllPredators.Wolf;
import Fauna.AllHerbivores.Rabbit;
import MapEngine.Island;

public class Main {
    public static void main(String[] args) {

        // Создаём остров 10x5 клеток
        Island island = new Island(10, 5);

        // Создаём пару животных
        Wolf wolf = new Wolf();
        Rabbit rabbit = new Rabbit();

        // Добавляем их на остров (в клетку 3,2)
        island.addAnimal(wolf, 3, 2);
        island.addAnimal(rabbit, 3, 2);

        // Симуляция 5 шагов
        for (int i = 1; i <= 5; i++) {
            System.out.println("===== Step " + i + " =====");
            island.simulateStep();
            System.out.println();
        }
    }
}
