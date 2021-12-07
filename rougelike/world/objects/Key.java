package rougelike.world.objects;

import rougelike.world.Objt;
import rougelike.world.World;
import rougelike.world.creature.Player;

import java.util.Random;

public class Key extends Objt {
    public Key(World world, char glyph) {
        super(world,glyph);
    }

    @Override
    public void use(int d) {

    }
}
