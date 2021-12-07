package rougelike.world.creature;

import rougelike.world.Creature;
import rougelike.world.Weapon;
import rougelike.world.World;
import rougelike.world.weapon.Gun;

import java.awt.*;

public class Magician extends Creature {
    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Magician(World world, char glyph, int maxHP, int attack, int defense, int visionRadius) {
        super(world,glyph,maxHP,attack,defense,visionRadius);
        setWeapon(new Gun(this.world(),'=',this));
    }

}
