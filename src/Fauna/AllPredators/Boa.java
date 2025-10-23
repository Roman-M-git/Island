
package Fauna.AllPredators;
import Fauna.Animal;
import MapEngine.Coordinate;

    public class Boa extends Predators {

        public Boa() {
            super("Fox", 30, 30, 4, 5);
            setMaxAge(40);
        }

        @Override
        protected double getEatChance(Animal prey) {
            return switch (prey.getName()) {
                case "Rabbit" -> 0.8;
                case "Mouse" -> 0.7;
                case "Fox" -> 0.15;
                case "Goat", "Sheep" -> 0.5;
                default -> 0.0;
            };
        }

        @Override
        protected Animal createChild() {
            Fauna.AllPredators.Boa baby = new Fauna.AllPredators.Boa();
            if (this.getPosition() != null) {
                baby.setPosition(new Coordinate(this.getPosition().x, this.getPosition().y));
            }
            return baby;
        }
    }


