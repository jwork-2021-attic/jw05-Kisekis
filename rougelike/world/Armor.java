package rougelike.world;

import java.awt.*;

public class Armor extends Objt{
    private int defence;

    public int getDefence() {
        return defence;
    }

    public Armor(World world, char glyph) {
        super(world,glyph);
    }
}
