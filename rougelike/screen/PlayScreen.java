package rougelike.screen;

import rougelike.ApplicationMain;
import rougelike.world.*;
import rougelike.asciiPanel.AsciiPanel;
import rougelike.world.creature.Player;

import rougelike.world.mapgen.*;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private World[][] worlds;
    private World curWorld;
    public AsciiPanel terminal;
    private final int DIM = 5;
    private Player player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    public PlayScreen() {
        this.screenWidth = 40;
        this.screenHeight = 15;
        createWorld();
        this.messages = new ArrayList<String>();
        this.oldMessages = new ArrayList<String>();
        this.player = CreatureFactory.newPlayer(this.messages,curWorld,this);
    }

    public void worldChange(int i,int j) {
        int[] curIdx = getWorldIndex(curWorld);
        int di = i-curIdx[0];
        int dj = j-curIdx[1];
        curWorld.remove(player);
        curWorld.setPlayerIn(false);
        curWorld = worlds[i][j];
        curWorld.add(player);
        curWorld.visit();
        player.setWorld(curWorld);
        curWorld.setPlayerIn(true);
        int x = dj == -1 ? curWorld.width() - 2 : (-curWorld.width()/2)*dj + curWorld.width()/2 + 1;
        int y = di == -1 ? curWorld.width() - 2 : (-curWorld.height()/2)*di + curWorld.height()/2 + 1;
        player.teleport(x,y);
    }

    public int[] getWorldIndex(World world) {
        for(int i =0;i<DIM;i++) {
            for(int j=0;j<DIM;j++) {
                if(worlds[i][j] == world) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private void createWorld() {
        worlds = new World[DIM][DIM];
        MazeGenerator mazeGenerator = new MazeGenerator(DIM);
        mazeGenerator.generateMaze();
        int[] end = mazeGenerator.getEndPos();
        System.out.println("SYMBOLIC MAZE\n" + mazeGenerator.getSymbolicMaze());

        for(int i = 0;i < DIM;i++) {
            for(int j = 0;j < DIM;j++) {
                if(mazeGenerator.getMazeBlock(i,j) == 1) {
                    boolean[] neighbor = mazeGenerator.CountNeighbor(i,j);
                    System.out.println(i+" "+ j + neighbor[0] + neighbor[1]
                    + neighbor[2] + neighbor[3]);
                    WorldBuilder wb;
                    if(i == end[0] && j == end[1]) {
                        wb = new BossRoomBuilder(47, 47, neighbor);
                    }else {
                        wb = WorldBuilder.WorldBuilder(47, 47, neighbor, WorldType.getRandomType());
                    }
                    wb.makeDoor(neighbor);
                    worlds[i][j] = wb.build();
                }
            }

        }
        curWorld = worlds[0][0];
        curWorld.visit();
        curWorld.setPlayerIn(true);
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
//        terminal.clear();
        displayTile(terminal,left,top);
        // Show creatures
        for (Creature creature : curWorld.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top
                    && creature.y() < top + screenHeight) {

                terminal.write(creature.glyph(), creature.x() - left, creature.y() - top);

            }
        }
        for (Objt objt : curWorld.getObjects()) {
            if (objt.x() >= left && objt.x() < left + screenWidth && objt.y() >= top
                    && objt.y() < top + screenHeight) {

                terminal.write(objt.glyph(), objt.x() - left, objt.y() - top);
            }
        }



        curWorld.update();
    }

    private void displayTile(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                terminal.write(curWorld.glyph(wx, wy), x, y);
            }
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = this.screenHeight;
        if(messages.size()>5) {
            messages.clear();
        }
        for (int i = 0; i < messages.size(); i++) {
            terminal.write(messages.get(i), 1, top + i + 5);
        }
        this.oldMessages.addAll(messages);

    }

    @Override
    public void displayOutput(AsciiPanel terminal){
        // Terrain and creatures
        displayTiles(terminal, getScrollX(), getScrollY());
        // Player
        terminal.write(player.glyph(), player.x() - getScrollX(), player.y() - getScrollY());
        // Stats
        String stats = String.format("%3d/%3d hp    coin %3d    %3d/%3d mana", player.hp(), player.maxHP(), player.coin(), player.mana(), player.maxMana());

        terminal.write(stats, 1, screenHeight+1);
        //Backpack
        for(int i =0;i<5;i++) {
            if(i == player.selectedObjt) {
                terminal.write('+',i+1,screenHeight+4);
            }
            if(player.getObjt(i)!=null) {
                char t = player.getObjt(i).objt.glyph();
                terminal.write((char)((int)t + 16),i+1,screenHeight+2);
                terminal.write(player.getObjt(i).num+"",i+1,screenHeight+3);
            }else {
//                terminal.write('*',i+1,screenHeight+2);;
            }
        }

        terminal.write("MAP",screenWidth+2,0);
        for(int i =0;i<DIM;i++) {
            for(int j  = 0;j<DIM;j++) {
                if(worlds[j][i]!=null && worlds[j][i].isVisited()) {
                    terminal.write(worlds[j][i].glyph(),i+screenWidth+1,2+j);
                    if(worlds[j][i] == curWorld) {
                        terminal.write((char)1,i+screenWidth+1,2+j);
                    }

                }
            }
        }
        // Messages
        displayMessages(terminal, this.messages);

    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        messages.clear();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_1:
                player.selectedObjt = 0;
                break;
            case KeyEvent.VK_2:
                player.selectedObjt = 1;
                break;
            case KeyEvent.VK_3:
                player.selectedObjt = 2;
                break;
            case KeyEvent.VK_4:
                player.selectedObjt = 3;
                break;
            case KeyEvent.VK_5:
                player.selectedObjt = 4;
                break;
            case KeyEvent.VK_W:
                player.useObjt(player.selectedObjt,1);
                break;
            case KeyEvent.VK_A:
                player.useObjt(player.selectedObjt,3);
                break;
            case KeyEvent.VK_S:
                player.useObjt(player.selectedObjt,2);
                break;
            case KeyEvent.VK_D:
                player.useObjt(player.selectedObjt,4);
                break;

        }
        return this;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x() - screenWidth / 2, curWorld.width() - screenWidth));
    }

    public int getScrollY() {

        int a = Math.max(0, Math.min(player.y() - screenHeight / 2, curWorld.height() - screenHeight));
        return a;
    }

}
