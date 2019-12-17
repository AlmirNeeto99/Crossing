package Util;

import Model.Enums.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Almir
 */
public final class PathCreator {

    public List<Path> create_path() {
        Random rand = new Random();
        /*Randomize two integers to choose a path to a car.*/
        int from = rand.nextInt(3);
        int to = rand.nextInt(3);
        /*While the two numbers are equal, randomize a new number...*/
        while (from == to) {
            to = rand.nextInt(3);
        }
        Path from_path = Path.UP, to_path = Path.UP;
        switch (from) {
            case 0:
                from_path = Path.UP;
                break;
            case 1:
                from_path = Path.RIGHT;
                break;
            case 2:
                from_path = Path.DOWN;
                break;
            case 3:
                from_path = Path.LEFT;
                break;
        }
        switch (to) {
            case 0:
                to_path = Path.UP;
                break;
            case 1:
                to_path = Path.RIGHT;
                break;
            case 2:
                to_path = Path.DOWN;
                break;
            case 3:
                to_path = Path.LEFT;
                break;
        }

        ArrayList<Path> path = new ArrayList();
        path.add(from_path);
        path.add(to_path);
        return path;
    }
}
