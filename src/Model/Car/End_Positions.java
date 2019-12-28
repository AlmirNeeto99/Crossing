package Model.Car;

public class End_Positions extends Crucial_Positions {

    private final int offset = 80;

    public int[] get_up() {
        return new int[]{this.up[0] + offset, this.up[1]};
    }

    public int[] get_down() {
        return new int[]{this.down[0] - offset, this.down[1]};
    }

    public int[] get_left() {
        return new int[]{this.left[0], this.left[1] - offset};
    }

    public int[] get_right() {
        return new int[]{this.right[0], this.right[1] + offset};
    }
}
