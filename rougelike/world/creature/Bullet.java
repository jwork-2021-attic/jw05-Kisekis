package rougelike.world.creature;

import rougelike.world.Creature;
import rougelike.world.Objt;
import rougelike.world.World;
import rougelike.world.creatureAI.BulletAI;

import java.awt.*;

public class Bullet extends Creature {
    private static int maxHP = 1000000;
    private static int defense = 1000000;
    private static int visionRadius = 10;
    private Creature owner;
    public void setOwner(Creature owner) {
        this.owner = owner;
    }
    public Creature getOwner() {return this.owner;}

    public Bullet(World world, char glyph, int attack) {
        super(world,glyph,maxHP,attack,defense,visionRadius);
    }

    @Override
    public void moveBy(int mx, int my) {
        int x = super.x();
        int y = super.y();
        World world = super.world();
        BulletAI ai = (BulletAI) super.ai();

        if(mx == 0 && my == 0) return;
        Object other = world.getEntity(x+mx,y+my);

        if (other == null || other == this || other == owner) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else if(other instanceof Creature){

            attack((Creature) other);
            super.world().remove(this);
        }else if(other instanceof Objt) {
            super.world().remove(this);
        }
    }
}
