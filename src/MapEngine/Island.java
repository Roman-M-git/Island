package MapEngine;

import Fauna.Animal;

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

    // ✅ Добавлены геттеры для работы Animal.move()
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
