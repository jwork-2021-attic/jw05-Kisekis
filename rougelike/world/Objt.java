package rougelike.world;

import java.awt.*;

public class Objt implements Ini{
    private Creature owner;
    public void setOwner(Creature p) {owner = p;}
    public Creature getOwner() { return owner;}

    public boolean multiUse;
    private World world;

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    public void use(int d){};

    public Objt(World world, char glyph) {
        this.world = world;
        this.glyph = glyph;
    }

}
