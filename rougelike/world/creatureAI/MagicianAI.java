package rougelike.world.creatureAI;

import rougelike.world.CreatureAI;
import rougelike.world.CreatureFactory;
import rougelike.world.creature.Magician;
import rougelike.world.creature.Player;

import java.util.Random;

public class MagicianAI extends CreatureAI {
    private Magician magician;
    private CreatureFactory factory;

    public MagicianAI(Magician magician, CreatureFactory factory) {
        super(magician);
        this.magician = magician;
        this.factory = factory;
    }

    public void onUpdate() {
        count = (count+1)%10;
        if(count == 0) {
            if(magician.world().isPlayerIn()) {
                Player player = magician.world().getPlayer();
                if(magician.canSee(player.x(),player.y())) {
                    Random r = new Random();
                    int dx = player.x()-magician.x();
                    int dy = player.y()-magician.y();
                    if(r.nextInt(2) == 0) {
                        magician.moveBy((int) Math.signum(dx),0);
                    }else {
                        magician.moveBy(0,(int)Math.signum(dy));
                    }
                    if(!(dx==0&&dy==0)){
                        if(dx == 0) {
                            magician.getWeapon().use(dy>0? 2:1);
                        }
                        if(dy == 0) {
                            magician.getWeapon().use(dx>0? 4:3);
                        }
                    }
                }
        }
        }
    }

}
