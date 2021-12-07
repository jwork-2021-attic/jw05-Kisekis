package rougelike.world.weapon;

import rougelike.asciiPanel.AsciiPanel;
import rougelike.world.Creature;
import rougelike.world.Weapon;
import rougelike.world.World;
import rougelike.world.creature.Bullet;
import rougelike.world.creature.Player;
import rougelike.world.creatureAI.BulletAI;

import java.awt.*;

public class Gun extends Weapon {

    public Gun(World world, char glyph) {
        super(world,glyph);
        super.multiUse = true;
    }

    public Gun(World world, char glyph, Creature owner) {
        super(world,glyph);
        super.multiUse = true;
        super.setOwner(owner);
    }

    @Override
    public void use(int d) {
        World world = super.getOwner().world();
        if(super.getOwner() instanceof Player) {
            Player player = (Player) super.getOwner();
            if(player.mana()>=5) {
                player.minusMana(5);
            }else return;
        }
        Bullet bullet;
        switch (d){
            case 1:
                bullet= new Bullet(world, (char)129, 15);
                break;
            case 2:
                bullet = new Bullet(world, (char)130, 15);
                break;
            case 3:
                bullet = new Bullet(world, (char)128, 15);
                break;
            case 4:
                bullet = new Bullet(world, (char)131, 15);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + d);
        }

        bullet.setOwner(super.getOwner());
        world.add(bullet);
        bullet.setX(super.getOwner().x());
        bullet.setY(super.getOwner().y());
        BulletAI ai = new BulletAI(bullet,d);
    }
}
