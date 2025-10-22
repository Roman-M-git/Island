package MapEngine;

import Fauna.Animal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cell {

    private final Coordinate coordinate;         // координаты клетки
    private final List<Animal> animals = new ArrayList<>(); // список всех животных в клетке

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /** Добавить животное в клетку */
    public void addAnimal(Animal animal) {
        if (animal != null) {
            animals.add(animal);
            animal.setPosition(coordinate);
        }
    }

    /** Вернуть всех животных в клетке */
    public List<Animal> getAnimals() {
        return animals;
    }

    /** Один цикл жизни всех животных в этой клетке */
    public void liveCycle(Island island) {
        // 1️⃣ — Еда
        for (Animal animal : new ArrayList<>(animals)) {
            if (animal.isAlive()) {
                animal.eat(animals);
            }
        }

        // 2️⃣ — Размножение
        List<Animal> newborns = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.isAlive()) {
                // ищем особей того же вида
                List<Animal> sameSpecies = animals.stream()
                        .filter(a -> a.getClass() == animal.getClass() && a.isAlive())
                        .toList();
                Animal child = animal.reproduce(sameSpecies);
                if (child != null) {
                    newborns.add(child);
                }
            }
        }
        animals.addAll(newborns);

        // 3️⃣ — Передвижение
        for (Animal animal : new ArrayList<>(animals)) {
            if (animal.isAlive()) {
                animal.move(island);
            }
        }

        // 4️⃣ — Старение и смерть
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal a = iterator.next();
            a.deathFromOldAge();
            if (!a.isAlive()) {
                iterator.remove();
            }
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
