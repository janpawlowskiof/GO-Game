package juanolek.gui.awt;
import java.awt.*;

public class Pawn {

    private int x;
    private int y;
    private Color color;

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

