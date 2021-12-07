package rougelike.world.creatureAI;

import rougelike.world.CreatureAI;

import rougelike.world.Tile;
import rougelike.world.creature.Bullet;


public class BulletAI extends CreatureAI {
    private Bullet bullet;
    private int d;

    public BulletAI(Bullet bullet,int d) {
        super(bullet);
        this.bullet = bullet;
        this.d = d;
    }

    public void onUpdate() {
        count = (count + 1)%2;
        if(count == 0) {
            if(d == 1) bullet.moveBy(0,-1);
            if(d == 2) bullet.moveBy(0,1);
            if(d == 3) bullet.moveBy(-1,0);
            if(d == 4) bullet.moveBy(1,0);
        }
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        synchronized (this) {
            if (tile.isGround()) {
                creature.setX(x);
                creature.setY(y);
            }else {
                bullet.world().remove(bullet);
            }
        }
    }
}
