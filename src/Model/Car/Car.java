package Model.Car;

import Model.Enums.Path;
import Model.Enums.Status;
import java.awt.Rectangle;

public class Car extends Rectangle {

    private double speed = 1.0;
    public Path from;
    public Path to;

    public Status status = Status.STOPPED;

    public Car(int x, int y) {
        super(x, y, 20, 20);
    }

    public void accelerate() {
        this.speed += 0.25;
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
}
