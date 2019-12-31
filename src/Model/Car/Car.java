package Model.Car;

import Model.Enums.Path;
import Model.Enums.Status;
import java.awt.Color;
import java.awt.Rectangle;

public class Car extends Rectangle {

    private double speed = 1.0;
    private Path from;
    private Path to;
    private final Plate plate;
    private Color color;

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
    
    public Color getColor(){
        return this.color;
    }
}
