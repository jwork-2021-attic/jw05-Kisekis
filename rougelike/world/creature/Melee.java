package rougelike.world.creature;

import rougelike.world.Creature;
import rougelike.world.World;

import java.awt.*;

public class Melee extends Creature {
    public Melee(World world, char glyph, int maxHP, int attack, int defense, int visionRadius) {
        super(world,glyph,maxHP,attack,defense,visionRadius);
    }
}
