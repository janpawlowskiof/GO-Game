package juanolek.gui.awt;
import java.awt.*;

class Pawn {

    private final int x;
    private final int y;
    private final Color color;

    public Pawn(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}

