package rougelike.world;
import rougelike.ApplicationMain;
import rougelike.screen.PlayScreen;
import rougelike.screen.TradeScreen;
import rougelike.world.creature.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.removeKeyListener;

public class NPC extends Creature {
    protected List<String> messages;

    public NPC(World world, char glyph) {
        super(world, glyph, 100000, 0, 100000, 10);
        this.messages = new ArrayList<String>();
    }

    public void interact(Player player) {
        TradeScreen screen = new TradeScreen(messages,player);
        System.out.println("te");
        ApplicationMain.getInstance().setScreen(screen);
    }

}