package rougelike.world;

import java.awt.*;

public class Weapon extends Objt{
    private int attack;

    public int getAttack() {
        return attack;
    }

    public Weapon(World world, char glyph) {
        super(world,glyph);
    }
}
