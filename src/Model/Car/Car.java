package Model.Car;

import Model.Car.Enums.Direction;
import Model.Car.Enums.Path;
import Model.Car.Enums.Status;
import java.awt.Color;
import java.awt.Rectangle;

public class Car extends Rectangle {

    private double speed = 20;
    private Path from;
    private Path to;
    private final Plate plate;
    private final Color color;
    private Direction direction = null;

    public Status status = Status.STOPPED;

    public Car(int x, int y, Plate plate, Color color) {
        super(x, y, 20, 20);
        this.plate = plate;
        this.color = color;
    }

    public Car(int x, int y, Path from, Path to, Plate plate, Color color) {
        super(x, y, 20, 20);
        this.from = from;
        this.to = to;
        this.plate = plate;
        this.color = color;
    }

    public void accelerate() {
        this.speed += 0.25;
    }

    public double get_speed() {
        return this.speed;
    }

    public void set_speed(double speed) {
        this.speed = speed;
    }

    public void decelerate() {
        this.speed -= 0.25;
    }

    public Path getFrom() {
        return this.from;
    }

    public Path getTo() {
        return this.to;
    }

    public void setFrom(Path from) {
        this.from = from;
    }

    public void setTo(Path to) {
        this.to = to;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Color getColor() {
        return this.color;
    }

    public void move() {
        switch (this.direction) {
            case UP:
                this.y -= this.speed;
                break;
            case DOWN:
                this.y += this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                break;
            case LEFT:
                this.x -= this.speed;
                break;
        }
    }

    public void set_direction(Direction dir) {
        this.direction = dir;
    }
}
