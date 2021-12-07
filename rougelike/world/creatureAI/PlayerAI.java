package rougelike.world.creatureAI;

import rougelike.world.CreatureAI;
import rougelike.world.Tile;
import rougelike.world.creature.Player;

import java.util.List;

public class PlayerAI extends CreatureAI {
    protected Player player;

    private List<String> messages;

    public PlayerAI(Player creature, List<String> messages) {
        super(creature);
        this.player = creature;
        this.messages = messages;
    }

    public void onEnter(int x, int y, Tile tile) {
        synchronized (this) {
            if (tile.isGround()) {
                player.setX(x);
                player.setY(y);
            } else if (tile.isDiggable()) {
                player.dig(x, y);
            }else if(tile.isEnterable()) {
                if(true) {
                    int bottom = player.world().height()-1;
                    int right = player.world().width()-1;
                    int[] curIdx = player.getScreen().getWorldIndex(player.world());
                    if(x == right) player.getScreen().worldChange(curIdx[0],curIdx[1]+1);
                    if(x == 0) player.getScreen().worldChange(curIdx[0],curIdx[1]-1);
                    if(y == 0) player.getScreen().worldChange(curIdx[0]-1,curIdx[1]);
                    if(y == bottom) player.getScreen().worldChange(curIdx[0]+1,curIdx[1]);
                }
            }
        }
    }

    public void onNotify(String message) {
        this.messages.add(message);
    }
}
