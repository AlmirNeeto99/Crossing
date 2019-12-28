package Model.Limits;

import java.awt.Rectangle;

public final class Stop_Limits extends Limits {

    private final int offset = 145;

    public Rectangle get_down() {
        return new Rectangle(this.down[0], this.down[1] - offset, 40, 5);
    }

    public Rectangle get_up() {
        return new Rectangle(this.up[0], this.up[1] + offset, 40, 5);
    }

    public Rectangle get_left() {
        return new Rectangle(this.left[0] + offset, this.left[1], 5, 40);
    }

    public Rectangle get_right() {
        return new Rectangle(this.right[0] - offset, this.right[1], 5, 40);
    }
}
