package rougelike.screen;

import rougelike.ApplicationMain;
import rougelike.asciiPanel.AsciiPanel;
import rougelike.world.creature.Player;
import rougelike.world.weapon.Gun;

import java.awt.event.KeyEvent;
import java.util.List;

public class TradeScreen implements Screen{
    private List<String> messages;
    private Player player;
    public TradeScreen(List<String> messages,Player player) {
        this.messages = messages;
        this.player = player;
    }
    public void displayOutput(AsciiPanel terminal) {
        int i = 0;
        for(String s : messages) {
            terminal.write(s, 15, 5+i);
            i++;
        }

        String stats = String.format("%3d/%3d hp    coin %3d    %3d/%3d mana", player.hp(), player.maxHP(), player.coin(), player.mana(), player.maxMana());

        terminal.write(stats, 5, 15);
        //Backpack
        for(int j =0;j<5;j++) {
            if(player.getObjt(j)!=null) {
                char t = player.getObjt(j).objt.glyph();
                terminal.write((char)((int)t + 16),j+1,15+2);
                terminal.write(player.getObjt(j).num+"",j+1,15+3);
            }else {
//                terminal.write('*',i+1,screenHeight+2);;
            }
        }
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_Q:
               ApplicationMain.getInstance().setScreen(player.getScreen());
               break;
            case KeyEvent.VK_1:
                if(player.coin() >= 100){
                    player.minusCoin(100);
                    player.minusMana(-30);
                }
                break;
            case KeyEvent.VK_2:
                if(player.coin() >= 100){
                    player.minusCoin(100);
                    player.modifyHP(20);
                }
                break;
            case KeyEvent.VK_3:
                if(player.coin() >= 500){
                    player.minusCoin(500);
                    Gun g = new Gun(player.world(),(char)6,player);
                    player.addItem(g);
                }
                break;
            case KeyEvent.VK_4:
                break;
        }
        return this;
    }
}
