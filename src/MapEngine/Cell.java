package MapEngine;

import Fauna.Animal;
import Fauna.Herbs;
import Fauna.AllHerbivores.Herbivores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cell {

    private final Coordinate coordinate;                  // координаты клетки
    private final List<Animal> animals = new ArrayList<>(); // животные в клетке
    private final List<Herbs> plants  = new ArrayList<>();  // растения в клетке

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /** Добавить животное в клетку */
    public void addAnimal(Animal animal) {
        if (animal == null) return;

        // 🔹 Считаем, сколько животных этого же вида уже в клетке
        long sameTypeCount = animals.stream()
                .filter(a -> a.getClass() == animal.getClass())
                .count();

        // 🔹 Проверяем лимит
        if (sameTypeCount >= animal.getMaxCountOnCell()) {
            // клетка переполнена этим видом — не добавляем
            return;
        }

        animals.add(animal);
        animal.setPosition(coordinate);
    }

    /** Добавить растение в клетку */
    public void addPlant(Herbs herb) {
        if (herb != null) {
            plants.add(herb);
        }
    }

    /** Дать доступ к растениям (если понадобится) */
    public List<Herbs> getPlants() {
        return plants;
    }

    /** Вернуть всех животных в клетке */
    public List<Animal> getAnimals() {
        return animals;
    }

    /** Один цикл жизни всех животных в этой клетке */
    public void liveCycle(Island island) {
        // 1️⃣ — Еда
        for (Animal animal : new ArrayList<>(animals)) {
            if (!animal.isAlive()) continue;

            if (animal instanceof Herbivores herbivore) {
                // Травоядные сначала пытаются съесть траву
                boolean ate = herbivore.tryEatPlants(plants);
                if (!ate) {
                    herbivore.eat(animals);
                }
            } else {
                animal.eat(animals);
            }
        }

        // 2️⃣ — Размножение
        List<Animal> newborns = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.isAlive()) {
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

        // 3️⃣ — Передвижение (без ConcurrentModification)
        List<Animal> toMove = new ArrayList<>();

        for (Animal animal : new ArrayList<>(animals)) {
            if (!animal.isAlive()) continue;

            Coordinate oldPos = animal.getPosition();
            animal.move(island);
            Coordinate newPos = animal.getPosition();

            // Если животное действительно переместилось
            if (oldPos.x != newPos.x || oldPos.y != newPos.y) {
                toMove.add(animal);
            }
        }

// После цикла переносим животных
        for (Animal animal : toMove) {
            animals.remove(animal); // удаляем из старой клетки
            Cell newCell = island.getCell(animal.getPosition().x, animal.getPosition().y);
            if (newCell != null) {
                newCell.addAnimal(animal);
            }
        }



        // 4️⃣ — Старение и смерть
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal a = iterator.next();
            a.liveCycle(island); // вызывает и старение, и проверку смерти
            if (!a.isAlive()) {
                iterator.remove();
            }
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
