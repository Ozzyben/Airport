package checkIn;

import java.util.List;

public class Bag {
    private double size; //volume
    private double weight;

    public Bag(double size, double weight) {
        this.size = size;
        this.weight = weight;
    }

    //Generic getters and setters

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
