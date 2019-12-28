package Model.Limits;

import java.awt.Rectangle;

public final class End_Limits extends Limits {

    private final int offset = 80;

    public Rectangle get_down() {
        return new Rectangle(this.down[0] - offset, this.down[1], 40, 5);
    }

    public Rectangle get_up() {
        return new Rectangle(this.up[0] + offset, this.up[1], 40, 5);
    }

    public Rectangle get_left() {
        return new Rectangle(this.left[0], this.left[1] - offset, 5, 40);
    }

    public Rectangle get_right() {
        return new Rectangle(this.right[0], this.right[1] + offset, 5, 40);
    }
}
