package Model.Car.Util;

import Model.Car.Car;
import Model.Car.Positions.EndPositions;
import Model.Car.Positions.StartPositions;
import static Model.Car.Positions.CrossingPositions.*; //With this static i can call CrossingPositions variables without puting CrossingPositions in front of it
import Model.Car.Enums.Direction;
import Model.Car.Enums.Path;
import Model.Car.Enums.Status;
import Model.Car.Positions.StopPositions;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Random;

public abstract class HandleCarMovements {

    public static Path[] get_path() {
        Path path[] = new Path[2];
        Random rand = new Random();
        int start = rand.nextInt(4);
        switch (start) {
            case 0:
                path[0] = Path.UP;
                break;
            case 1:
                path[0] = Path.RIGHT;
                break;
            case 2:
                path[0] = Path.DOWN;
                break;
            case 3:
                path[0] = Path.LEFT;
                break;
        }
        int stop = rand.nextInt(4);
        while (start == stop) {
            stop = rand.nextInt(4);
        }
        switch (stop) {
            case 0:
                path[1] = Path.UP;
                break;
            case 1:
                path[1] = Path.RIGHT;
                break;
            case 2:
                path[1] = Path.DOWN;
                break;
            case 3:
                path[1] = Path.LEFT;
                break;
        }
        System.out.println(Arrays.toString(path));
        return path;
    }

    public static int[] get_start_position(Path where) {
        if (where == Path.UP) {
            return StartPositions.start_up;
        } else if (where == Path.DOWN) {
            return StartPositions.start_down;
        } else if (where == Path.RIGHT) {
            return StartPositions.start_right;
        }
        return StartPositions.start_left;
    }

    public static boolean check_if_reached_end(Car car) {
        Rectangle upper_limit = new Rectangle(EndPositions.end_up[0], EndPositions.end_up[1], 40, 5);
        Rectangle lower_limit = new Rectangle(EndPositions.end_down[0], EndPositions.end_down[1], 40, 5);
        Rectangle left_limit = new Rectangle(EndPositions.end_left[0], EndPositions.end_left[1], 5, 40);
        Rectangle right_limit = new Rectangle(EndPositions.end_right[0], EndPositions.end_right[1], 5, 40);

        if (car.intersects(right_limit) || car.intersects(left_limit) || car.intersects(upper_limit) || car.intersects(lower_limit)) {
            Path[] path = get_path();
            int[] start = get_start_position(path[0]);
            car.setFrom(path[0]);
            car.setTo(path[1]);
            car.x = start[0];
            car.y = start[1];
            set_car_direction(car, path[0]);
            car.setStatus(Status.STOPPED);
            return true;
        }
        return false;
    }

    public static void car_from_right(Car car) {
        switch (car.getTo()) {
            case UP:
                if (car.x == first_quadrant[0] && car.y == first_quadrant[1]) {
                    car.set_direction(Direction.UP);
                }
                break;
            case DOWN:
                if (car.x == second_quadrant[0] && car.y == second_quadrant[1]) {
                    car.set_direction(Direction.DOWN);
                }
                break;
        }
    }

    public static void car_from_left(Car car) {
        switch (car.getTo()) {
            case UP:
                if (car.x == forth_quadrant[0] && car.y == forth_quadrant[1]) {
                    car.set_direction(Direction.UP);
                }
                break;
            case DOWN:
                if (car.x == third_quadrant[0] && car.y == third_quadrant[1]) {
                    car.set_direction(Direction.DOWN);
                }
                break;
        }
    }

    public static void car_from_down(Car car) {
        switch (car.getTo()) {
            case LEFT:
                if (car.x == first_quadrant[0] && car.y == first_quadrant[1]) {
                    car.set_direction(Direction.LEFT);
                }
                break;
            case RIGHT:
                if (car.x == forth_quadrant[0] && car.y == forth_quadrant[1]) {
                    car.set_direction(Direction.RIGHT);
                    System.out.println("baixo pra direita");
                }
                break;
        }
    }

    public static void car_from_up(Car car) {
        switch (car.getTo()) {
            case LEFT:
                if (car.x == second_quadrant[0] && car.y == second_quadrant[1]) {
                    car.set_direction(Direction.LEFT);
                    System.out.println("cima pra esquerda");
                }
                break;
            case RIGHT:
                if (car.x == third_quadrant[0] && car.y == third_quadrant[1]) {
                    car.set_direction(Direction.RIGHT);
                    System.out.println("cima pra direita");
                }
                break;
        }
    }

    public static void set_car_direction(Car car, Path from) {
        switch (from) {
            case UP:
                car.set_direction(Direction.DOWN);
                break;
            case DOWN:
                car.set_direction(Direction.UP);
                break;
            case LEFT:
                car.set_direction(Direction.RIGHT);
                break;
            case RIGHT:
                car.set_direction(Direction.LEFT);
                break;
        }
    }

    public static boolean check_if_reached_stop_line(Car car) {
        Rectangle upper_limit = new Rectangle(StopPositions.stop_up[0], StopPositions.stop_up[1], 40, 5);
        Rectangle lower_limit = new Rectangle(StopPositions.stop_down[0], StopPositions.stop_down[1], 40, 5);
        Rectangle left_limit = new Rectangle(StopPositions.stop_left[0], StopPositions.stop_left[1], 5, 40);
        Rectangle right_limit = new Rectangle(StopPositions.stop_right[0], StopPositions.stop_right[1], 5, 40);

        if (car.intersects(right_limit) || car.intersects(left_limit) || car.intersects(upper_limit) || car.intersects(lower_limit)) {
            System.out.println("cruzou a linha de parar");
            return true;
        }
        return false;
    }
}
