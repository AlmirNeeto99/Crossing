package Model.Car.Positions;

public abstract class StopPositions implements CrucialPositions {

    private static final int offset = 145;

    public static final int[] stop_up = {StopPositions.up[0], StopPositions.up[1] + offset};
    public static final int[] stop_down = {StopPositions.down[0], StopPositions.down[1] - offset};
    public static final int[] stop_left = {StopPositions.left[0] + offset, StopPositions.left[1]};
    public static final int[] stop_right = {StopPositions.right[0] - offset, StopPositions.right[1]};
}
