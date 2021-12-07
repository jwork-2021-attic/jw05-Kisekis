package rougelike.world.creatureAI;

import rougelike.world.CreatureAI;
import rougelike.world.CreatureFactory;
import rougelike.world.creature.Melee;
import rougelike.world.creature.Player;

import java.util.Random;

public class MeleeAI extends CreatureAI {
    private Melee melee;

    private CreatureFactory factory;

    public MeleeAI(Melee melee, CreatureFactory factory) {
        super(melee);
        this.melee = melee;
        this.factory = factory;
    }

    public void onUpdate() {
        count = (count+1)%10;
        if(count == 0){
            if(melee.world().isPlayerIn()) {
                Player player = melee.world().getPlayer();
                if(melee.canSee(player.x(),player.y())) {
                    Random r = new Random();
                    if(r.nextInt(2) == 0) {
                        int dx = player.x()-melee.x();
                        melee.moveBy((int) Math.signum(dx),0);
                    }else {
                        int dy = player.y()-melee.y();
                        melee.moveBy(0,(int)Math.signum(dy));
                    }
                }
            }
        }
    }
}
