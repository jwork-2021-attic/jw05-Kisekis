package rougelike.world.creature;

import rougelike.world.NPC;
import rougelike.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Trader extends NPC {
    public Trader(World world, char glyph) {
        super(world,glyph);
        messages.add("-------Trader-------");
        messages.add("--------Key1--------");
        messages.add("100 coin to 30 mana");
        messages.add("--------Key2--------");
        messages.add(" 100 coin to 20 hp");
        messages.add("--------Key3--------");
        messages.add(" 500 coin to a gun");
        messages.add("--------KeyQ--------");
        messages.add("  Quit the screen   ");
    }

}
