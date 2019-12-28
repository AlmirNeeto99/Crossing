package Model.Car;

public class Stop_Positions extends Crucial_Positions {

    private final int offset = 145;

    public int[] get_up() {
        return new int[]{this.up[0], this.up[1] + offset};
    }

    public int[] get_down() {
        return new int[]{this.down[0], this.down[1] - offset};
    }

    public int[] get_left() {
        return new int[]{this.left[0] + offset, this.left[1]};
    }

    public int[] get_right() {
        return new int[]{this.right[0] - offset, this.right[1]};
    }
}
