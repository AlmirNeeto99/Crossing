package Util;

import Model.Path;
import Model.Status;

public abstract class Car {

    public int x; // car's X position
    public int y; // car's Y position
    private double speed = 1.0;
    public Path from;
    public Path to;

    public Status status;

    /* Move the car */
    public abstract void move();

    public abstract void turn_left();

    public abstract void turn_right();

    public void accelerate() {
        this.speed += 1.0;
    }

    public void decelerate() {
        this.speed -= 1.0;
    }

    public Path get_from() {
        return this.from;
    }

    public Path get_to() {
        return this.to;
    }

    public void set_from(Path from) {
        this.from = from;
    }

    public void set_to(Path to) {
        this.to = to;
    }

    public Status get_status() {
        return this.status;
    }
}
