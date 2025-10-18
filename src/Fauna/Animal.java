package Fauna;

public abstract class Animal {
    // ==== Основные характеристики ====
    protected String name;                // Название животного
    protected double weight;              // Текущий вес
    protected double maxWeight;           // Максимальный вес особи
    protected int maxCountOnCell;         // Сколько животных может быть на одной клетке
    protected int speed;                  // На сколько клеток может двигаться
    protected double foodNeed;            // Сколько нужно еды для насыщения
    protected boolean alive = true;       // Жив ли

    // ==== Состояние ====
    protected double hunger;              // уровень сытости (0 = голоден)
    protected int age = 0;                // возраст
    protected int maxAge;                 // максимум лет (можно задать в наследниках)
    protected Coordinate position;        // текущие координаты на острове

    protected final Random random = new Random();

    // ==== Конструктор ====
    public Animal(String name, double maxWeight, int maxCountOnCell, int speed, double foodNeed) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.weight = maxWeight;
        this.maxCountOnCell = maxCountOnCell;
        this.speed = speed;
        this.foodNeed = foodNeed;
        this.hunger = 1.0;
    }

    // ==== Методы поведения ====

    /** Двигается в случайном направлении */
    public void move(Island island) {
        if (!alive) return;

        int dx = random.nextInt(speed * 2 + 1) - speed;
        int dy = random.nextInt(speed * 2 + 1) - speed;

        int newX = Math.max(0, Math.min(island.getWidth() - 1, position.x + dx));
        int newY = Math.max(0, Math.min(island.getHeight() - 1, position.y + dy));

        position = new Coordinate(newX, newY);
        System.out.println(name + " переместился на " + newX + "," + newY);
    }

    /** Поесть — базовый вариант (будет переопределяться у потомков) */
    public abstract void eat(List<Animal> others);

    /** Размножение — если есть пара на клетке */
    public Animal reproduce(List<Animal> sameSpecies) {
        if (!alive) return null;
        if (sameSpecies.size() < 2) return null;

        if (random.nextDouble() < 0.3) { // шанс на размножение
            Animal child = createChild();
            System.out.println(name + " размножился → " + child.getName());
            return child;
        }
        return null;
    }

    /** Умереть от старости или голода */
    public void deathFromOldAge() {
        if (age > maxAge || hunger <= 0) {
            alive = false;
            System.out.println(name + " умер");
        }
    }

    /** Проходит один "такт" жизни */
    public void liveCycle(Island island) {
        if (!alive) return;

        age++;
        hunger -= 0.1; // голод накапливается
        move(island);
        deathFromOldAge();
    }

    /** Создание детеныша (реализуется в конкретном классе) */
    protected abstract Animal createChild();

    // ==== Геттеры/сеттеры ====

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}


