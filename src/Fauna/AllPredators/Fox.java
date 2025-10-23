package Fauna.AllPredators;

import Fauna.Animal;

public class Fox extends  Predators{
    public Fox() {
        super("Fox", 30, 30, 4, 5);
        setMaxAge(40);
    }

    @Override
    protected double getEatChance(Animal prey) {
        // Упрощённая таблица вероятностей
        return switch (prey.getName()) {
            case "Rabbit" -> 0.8;
            case "Mouse" -> 0.7;
            case "Goat", "Sheep" -> 0.5;
            default -> 0.0;
        };
    }

    @Override
    protected Animal createChild() {
        return new Fox();
    }
}

