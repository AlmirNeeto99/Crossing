package Model.Car;

public class StartPositions extends CrucialPositions {

    public int[] get_up() {
        return new int[]{this.up[0], this.up[1]};
    }

    public int[] get_down() {
        return new int[]{this.down[0], this.down[1]};
    }

    public int[] get_left() {
        return new int[]{this.left[0], this.left[1]};
    }

    public int[] get_right() {
        return new int[]{this.right[0], this.right[1]};
    }
}
