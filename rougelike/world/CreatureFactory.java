package rougelike.world;

import java.util.List;

import rougelike.asciiPanel.AsciiPanel;
import rougelike.screen.PlayScreen;
import rougelike.world.creature.Magician;
import rougelike.world.creature.Melee;
import rougelike.world.creature.Player;
import rougelike.world.creature.Trader;
import rougelike.world.creatureAI.MagicianAI;
import rougelike.world.creatureAI.MeleeAI;
import rougelike.world.creatureAI.NPCAI;
import rougelike.world.creatureAI.PlayerAI;

public class CreatureFactory {

    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public static Player newPlayer(List<String> messages, World world, PlayScreen screen) {
        Player player = new Player(world, (char)1, 100, 20, 5, 100,screen);
        player.setX(1);player.setY(1);
        world.add(player);
        new PlayerAI(player, messages);
        return player;
    }

    public void newMelee() {
        Melee melee = new Melee(this.world, (char)3, 10, 10, 0, 20);
        world.addAtEmptyLocation(melee);
        MeleeAI ai = new MeleeAI(melee, this);
    }

    public void newMagician() {
        Magician magician = new Magician(this.world, (char)5, 10, 10, 0, 20);
        world.addAtEmptyLocation(magician);
        MagicianAI ai = new MagicianAI(magician,this);
    }

    public void newTrader() {
        Trader trader = new Trader(this.world, (char)2);
        world.addAtEmptyLocation(trader);
        NPCAI ai = new NPCAI(trader,this);
    }
}
