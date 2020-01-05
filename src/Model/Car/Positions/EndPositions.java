package Model.Car.Positions;

public abstract class EndPositions implements CrucialPositions {

    private static final int offset = 80;

    public static final int[] end_up = {EndPositions.up[0] + offset, EndPositions.up[1]};
    public static final int[] end_down = {EndPositions.down[0] - offset, EndPositions.down[1]};
    public static final int[] end_left = {EndPositions.left[0], EndPositions.left[1] - offset};
    public static final int[] end_right = {EndPositions.right[0], EndPositions.right[1] + offset};
}
