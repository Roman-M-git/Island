package MapEngine;

import Fauna.Animal;
import Fauna.Herbs;

public class Island {

    private final int width;
    private final int height;
    private final Cell[][] cells;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        // создаём клетки
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(new Coordinate(x, y));
            }
        }
    }

    /** Возвращает клетку по координатам */
    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return cells[y][x];
    }

    /** Симуляция одного шага (все клетки "живут") */
    public void simulateStep() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x].liveCycle(this);
            }
        }
    }

    /** Добавить животное в клетку по координатам */
    public void addAnimal(Animal animal, int x, int y) {
        Cell cell = getCell(x, y);
        if (cell != null) {
            cell.addAnimal(animal);
            animal.setPosition(new Coordinate(x, y)); // 🟢 фикс: обновляем позицию животного
        }
    }

    /** Добавить растение в клетку по координатам */
    public void addPlant(Herbs herb, int x, int y) {
        cells[y][x].addPlant(herb);
    }

    /** Отобразить остров в консоли */
    public void printMap() {
        System.out.println("\n===== ISLAND MAP =====");

        for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder();

            for (int x = 0; x < width; x++) {
                Cell cell = cells[y][x];

                if (!cell.getAnimals().isEmpty()) {
                    // Берем первое животное в клетке для отображения
                    String symbol = getAnimalSymbol(cell.getAnimals().get(0));
                    row.append(symbol).append(" ");
                } else if (!cell.getPlants().isEmpty()) {
                    row.append("🌿 "); // трава
                } else {
                    row.append("⬜ "); // пустая клетка
                }
            }

            System.out.println(row);
        }
    }

    /** Возвращает эмодзи по типу животного */
    private String getAnimalSymbol(Fauna.Animal animal) {
        return switch (animal.getName()) {
            case "Rabbit" -> "🐇";
            case "Fox" -> "🦊";
            case "Wolf" -> "🐺";
            case "Boa" -> "🐍";
            default -> "❓";
        };
    }


    // ✅ Добавлены геттеры для работы Animal.move()
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
